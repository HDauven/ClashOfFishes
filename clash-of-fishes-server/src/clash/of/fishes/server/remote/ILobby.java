package clash.of.fishes.server.remote;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface that makes the defined methods available for users of the ILobby.
 * @author Hein
 */
public interface ILobby {
    /**
     * Method that checks whether an username is available or not.
     * @param username the username to check
     * @return returns true when the username is available, returns false if the username is in use
     * @throws RemoteException 
     */
    boolean clientNameFree(String username)
            throws RemoteException;
    
    /**
     * Method that registers an IClient to the ILobby.
     * @param client The IClient that is going to be added to the ILobby.
     * @throws RemoteException 
     */
    void registerChatClient(IClient client) 
            throws RemoteException;
    
    /**
     * Method that broadcasts a message to all the IClients.
     * @param message The message that is meant for all the IClients.
     * @throws RemoteException 
     */
    void broadcastMessage(IMessage message) 
            throws RemoteException;
    
    /**
     * Method that gets all the messages from the IServer that were sent prior
     * to the IClient being connected to the IServer.
     * @return returns a list of messages to the IClient.
     * @throws RemoteException 
     */
    List<IMessage> getMessages()
            throws RemoteException;
}    
