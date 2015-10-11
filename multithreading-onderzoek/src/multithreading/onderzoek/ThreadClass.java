package multithreading.onderzoek;

import timeutil.TimeStamp;

/**
 * ThreadClass test
 * @author Hein Dauven
 */
public class ThreadClass extends Thread {
    private Thread t;
    private final String threadName;
    private TimeStamp ts = new TimeStamp();
    
    /**
     * 
     * @param name
     */
    public ThreadClass(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }
    
    /**
     * 
     */
    @Override
    public void run() {
        System.out.println("Running " + threadName);
        ts.setBegin();
        System.out.println("Running " + threadName);
        for (int i = 100000; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
        }
        System.out.println("Thread " + threadName + " exiting.");
        ts.setEnd();
        System.out.println(ts.toString());
    }
    
    /**
     * 
     */
    @Override
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }     
}
