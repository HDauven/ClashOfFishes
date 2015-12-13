/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.interfaces;

import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.server.remote.IClient;

/**
 *
 * @author Christian Adkin
 */
public interface IChangeGui {
    
    void displayMessage(String message);
    
    void displayPlayer(String player);
    
    void displaySelectedCharacter(String characterName);
    
    void displayReady(boolean isReady, IClient sender);
    
    void displayGameMode(GameMode gameMode);
   
}
