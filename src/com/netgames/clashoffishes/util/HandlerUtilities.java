/*
 * Copyright (c) 2015, 2016, Clash of Fishes and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Further input required:
 */
package com.netgames.clashoffishes.util;

import com.netgames.clashoffishes.Administration;
import javafx.stage.Window;

/**
 * Holds Handler Utility methods.
 * @author Hein Dauven
 */
public class HandlerUtilities {
    /**
     * Method that should be used to deal with Java RMI disconnects caused by
     * the connection with the remote lobby being broken
     */
    public static void disconnectionHandler(Window window,String menuName, String menuTitle) {
        if (Administration.get().getLobby() != null) {
            Administration.get().resetLobby();
            Administration.get().resetClient();
            GuiUtilities.buildStage(window, menuName, GuiUtilities.getMainMenusTitle());
            System.out.println("The connection with the lobby has been lost.");
            System.out.println("User should be more clearly informed?");
        }
    }
}
