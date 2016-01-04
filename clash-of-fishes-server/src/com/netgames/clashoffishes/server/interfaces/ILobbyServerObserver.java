package com.netgames.clashoffishes.server.interfaces;

import com.netgames.clashoffishes.server.remote.ILobby;

/**
 * Interface that defines methods for an object to inform its listeners to
 * update their UI.
 *
 * @author Hein Dauven
 */
public interface ILobbyServerObserver {

    void updateMessage(String message);

    void updateLobby(ILobby lobby);
}
