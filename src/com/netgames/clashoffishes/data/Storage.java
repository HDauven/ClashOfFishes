package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.User;

import java.util.List;

/**
 * Created by bram on 1/10/15.
 */
public interface Storage {

    Administration load();

    void save(Administration admin);

}
