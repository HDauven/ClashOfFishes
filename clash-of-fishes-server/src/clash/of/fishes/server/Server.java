package clash.of.fishes.server;

import clash.of.fishes.server.remote.ILobby;
import clash.of.fishes.server.remote.IServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that keeps track of all the lobbies that exist of this game.
 * @author Hein Dauven
 */
public class Server extends UnicastRemoteObject implements IServer {
    private static final long serialVersionUID = 1L;
    
    private List<ILobby> lobbies;
    
    /**
     * Constructor for the server
     * @throws RemoteException 
     */
    public Server() throws RemoteException {
        super();
        lobbies = new ArrayList<>();
    }

    @Override
    public void registerLobby(ILobby lobby) throws RemoteException {
        lobbies.add(lobby);
    }

    @Override
    public List<ILobby> listChatServers() throws RemoteException {
        return Collections.unmodifiableList(lobbies);
    }
}
