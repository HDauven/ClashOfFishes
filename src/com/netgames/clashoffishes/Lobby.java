package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.GameMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bram on 1/10/15.
 */
public class Lobby {
    private List<User> users = new ArrayList<>();
    private Map<User, Boolean> usersReadyMap = new HashMap<>();
    private GameMode gameMode = GameMode.EVOLVED;

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void userIsReady(User user, boolean ready) {
        usersReadyMap.put(user, ready);
    }

    public boolean isEveryoneReady() {
        for (User user : users) {
            if (!usersReadyMap.get(user))
                return false;
        }

        return true;
    }

    public boolean isUserReady(User user) {
        return usersReadyMap.get(user);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameManager createGame() {
        return null;
    }

    public List<User> getUsers()
    {
        return Collections.unmodifiableList(users);
    }
}
