package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.engine.object.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bram on 1/10/15.
 */
public class Lobby {
    private List<User> players = new ArrayList<>();
    private Map<User, Boolean> playersReadyMap = new HashMap<>();
    private GameMode selectedGameMode = GameMode.EVOLVED;

    public void addPlayer(User user) {
        if (!players.contains(user)) {
            players.add(user);
        }
    }

    public void removePlayer(User user) {
        players.remove(user);
    }

    public void playerIsReady(User user, boolean ready) {
        playersReadyMap.put(user, ready);
    }

    public boolean isEveryoneReady() {
        for (User player : players) {
            if (!playersReadyMap.get(player))
                return false;
        }

        return true;
    }

    public boolean isPlayerReady(Player player) {
        return playersReadyMap.get(player);
    }

    public GameMode getSelectedGameMode() {
        return selectedGameMode;
    }

    public void setSelectedGameMode(GameMode selectedGameMode) {
        this.selectedGameMode = selectedGameMode;
    }

    public GameManager createGame() {
        return null;
    }
}
