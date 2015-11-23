package clash.of.fishes.server.remote;

import java.io.Serializable;

/**
 * Contains the implementation details for a message that is to sent by an IClient
 * and shared by the ILobby.
 * @author Hein Dauven
 */
public interface IMessage extends Serializable {
    /**
     * Method that gets the text contained within a message.
     * @return the text of a message
     */
    public String getText();
    
    /**
     * Method that sets the text contained within a message. 
     * @param text sets the text of a message
     */
    public void setText(String text);
    
    /**
     * Method that gets the username of the user that sent the message.
     * @return username of the sender
     */
    public String getUsername();
    
    /**
     * Method that sets the username of the user that sent the message.
     * @param username of the sender
     */
    public void setUsername(String username);
    
    @Override
    public String toString();
}
