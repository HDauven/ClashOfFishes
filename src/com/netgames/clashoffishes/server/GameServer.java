package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.GameState;
import com.netgames.clashoffishes.engine.object.GameObject;
import com.netgames.clashoffishes.engine.object.events.EnergyDrink;
import com.netgames.clashoffishes.engine.object.events.FishHook;
import com.netgames.clashoffishes.engine.object.events.ObjectType;
import com.netgames.clashoffishes.engine.object.events.Seaweed;
import com.netgames.clashoffishes.server.remote.IGameClient;
import com.netgames.clashoffishes.server.remote.IGameServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;

/**
 *
 * @author Stef Philipsen
 * @author Hein Dauven
 */
public class GameServer extends UnicastRemoteObject implements IGameServer {

    private transient final Lobby lobby;
    private ExecutorService executor;
    private List<IGameClient> clients;

    GameManager gameManager = new GameManager();

    AnimationTimer timer;

    private int nxtObjectID = 1;
    long prev = System.nanoTime();
    long pauseTime = 0;
    long elapsed;

    /**
     * Constructor for the server
     *
     * @param lobby
     * @throws RemoteException
     */
    public GameServer(Lobby lobby) throws RemoteException {
        super();
        this.lobby = lobby;

        clients = new ArrayList<>();
        executor = Executors.newFixedThreadPool(4);
    }

    @Override
    public void registerClient(IGameClient client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void updateMove(double speed, String key, boolean isPressed, double x, double y, int playerID) throws RemoteException {
        for (IGameClient client : clients) {
            if (client.getPlayerID() != playerID) {
                executor.execute(() -> {
                    try {
                        client.updateMove(speed, key, isPressed, x, y, playerID);
                    } catch (RemoteException ex) {

                    }
                });
            }
        }
    }

    @Override
    public void updateSpeed(double speed, int playerID, boolean reverseMovement) throws RemoteException {
        for (IGameClient client : clients) {
            if (client.getPlayerID() != playerID) {
                executor.execute(() -> {
                    try {
                        client.updateSpeed(speed, playerID, reverseMovement);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void collision(int playerID, int objectID, int playerScore) throws RemoteException {
        for (IGameClient client : clients) {
            if (client.getPlayerID() != playerID) {
                executor.execute(() -> {
                    try {
                        client.collisionUpdate(playerID, objectID, playerScore);
                    } catch (RemoteException ex) {

                    }
                });
            }
        }
    }

    @Override
    public void stateChanged(GameState newState) throws RemoteException {
        if (newState.equals(GameState.RUNNING)) {
            timer.start();
        } else {
            timer.stop();
        }

        for (IGameClient client : clients) {
            client.changeGameState(newState);
        }
    }

    @Override
    public void start() throws RemoteException {
        boolean characterSelected = true;
        for (IGameClient client : this.clients) {
            System.out.println(client.getCharacterName());
            if (client.getCharacterName().equals("None")) {
                System.out.println("Niet iedereen heeft een character geselecteerd.");
                characterSelected = false;
            }
        }
        if (characterSelected) {
            for (IGameClient gameClient : this.clients) {
                gameClient.startGame();
            }
            this.createRandomObjects();
            gameManager.setGameState(GameState.RUNNING);
        }
    }

    private void createRandomObjects() {
        int NANO_TO_SECOND = 1_000_000_000;
        timer = new AnimationTimer() {
            long prev = System.nanoTime();

            @Override
            public void handle(long now) {
                if (gameManager.getGameState() == GameState.RUNNING) {
                    long elapsed = now - prev;
                    int randInt = (int) (Math.random() * 1_000 + 1); // moet 10_000 zijn, 1_000 is om te testen
                    //System.out.println(elapsed);
                    ObjectType type = null;
                    if ((elapsed / NANO_TO_SECOND) > randInt) {
                        GameObject object = gameManager.addRandomObject(nxtObjectID++);
                        for (IGameClient client : clients) {
                            try {
                                if (object instanceof EnergyDrink) {
                                    type = ObjectType.ENERGYDRINK;
                                }
                                if (object instanceof Seaweed) {
                                    type = ObjectType.SEAWEED;
                                }
                                if (object instanceof FishHook) {
                                    type = ObjectType.FISHHOOK;
                                }

                                client.objectCreation(object.getID(), (int) object.getiX(), (int) object.getiY(), type);
                            } catch (RemoteException ex) {
                                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        prev = System.nanoTime();
                    }
                }
            }
        };
        timer.start();
    }

    @Override
    public List<IGameClient> getClients() throws RemoteException {
        return this.clients;
    }

    @Override
    public void deleteObject(int id) throws RemoteException {
        for (IGameClient client : this.clients) {
            client.receiveObjectDeletion(id);

        }
    }

}
