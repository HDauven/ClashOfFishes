package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.Administration;
import java.io.IOException;


/**
 * Storage
 * Interface that functions as mediator between the various
 * types of storage methods available.
 * 
 * @author Hein Dauven
 * @version 1.0
 */
public interface Storage {
    /**
     * @return administration is initialized with the data stored at the 
     * configured location
     * @throws IOException
     */
    Administration load() throws IOException;

    /**
     * administration is stored at the configured location
     * @param admin
     * @throws IOException 
     */
    void save(Administration admin) throws IOException;

}
