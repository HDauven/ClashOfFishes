package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.engine.GameState;
import com.netgames.clashoffishes.engine.object.events.ObjectType;
import com.netgames.clashoffishes.server.remote.IGameClient;
import com.netgames.clashoffishes.server.remote.IGameServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author Stef Philipsen
 * @author Hein Dauven
 */
public class GameClient extends UnicastRemoteObject implements IGameClient {

    private String username;
    private String characterName;
    private int mapseed;
    private int playerID;
    private GameMode gameMode;

    private IGameServer gameServer;
    private GameManager gameManager;

    public GameClient(String username, String characterName, int mapseed, int playerID, IGameServer gameServer, GameMode gameMode) throws RemoteException {
        this.username = username;
        this.characterName = characterName;
        this.mapseed = mapseed;
        this.playerID = playerID;
        this.gameMode = gameMode;

        this.gameServer = gameServer;

        gameServer.registerClient(this);
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public void startGame() throws RemoteException {
        this.gameManager = new GameManager(this.characterName, this.mapseed, this.playerID, this.gameMode, true);

        Platform.runLater(() -> {
            this.gameManager.start(new Stage());
        });
    }

    @Override
    public void updateMove(double speed, String key, boolean pressed, double x, double y, int playerID) {
        this.gameManager.getPlayers().get(playerID).updateSpeed(speed);
        this.gameManager.getPlayers().get(playerID).setiX(x);
        this.gameManager.getPlayers().get(playerID).setiY(y);
        if (key.equals("UP")) {
            this.gameManager.getPlayers().get(playerID).setUp(pressed);
        } else if (key.equals("DOWN")) {
            this.gameManager.getPlayers().get(playerID).setDown(pressed);
        } else if (key.equals("LEFT")) {
            this.gameManager.getPlayers().get(playerID).setLeft(pressed);
        } else if (key.equals("RIGHT")) {
            this.gameManager.getPlayers().get(playerID).setRight(pressed);
        }
    }

    @Override
    public void updateSpeed(double speed, int playerID) {
        this.gameManager.getPlayers().get(playerID).updateSpeed(speed);
    }

    @Override
    public void collisionUpdate(int id, int objectId) throws RemoteException {
        this.gameManager.getObjectManager().addToRemovedObjects(this.gameManager.getObjectManager().getObject(objectId));
    }

    @Override
    public void objectCreation(int id, int x, int y, ObjectType objectType) throws RemoteException {
        this.gameManager.createObject(id, x, y, objectType);
    }

    @Override
    public String getCharacterName() throws RemoteException {
        return this.characterName;
    }

    @Override
    public int getPlayerID() throws RemoteException {
        return this.playerID;
    }

    @Override
    public void changeGameState(GameState gameState) throws RemoteException {
        this.gameManager.setGameState(gameState);
        if (this.gameManager.getGameState().equals(GameState.RUNNING)) {
            this.gameManager.getGameLoop().start();
        } else {
            this.gameManager.getGameLoop().stop();
        }
    }

    @Override
    public void receiveObjectDeletion(int id) throws RemoteException {
        this.gameManager.removeObject(id);
    }
}
