package multithreading.onderzoek;

import timeutil.TimeStamp;
import util.UserLogging;

/**
 * ExecutedClass test
 * @author Hein Dauven
 */
public class ExecutedClass implements Runnable{
    private final String threadName;
    private int iterations;
    private TimeStamp ts = new TimeStamp();
    
    /**
     * 
     * @param name
     */
    public ExecutedClass(String name, int iterations) {
        this.threadName = name;
        this.iterations = iterations;
        System.out.println("Creating " + threadName);
    }
    
    /**
     * 
     */
    @Override
    public void run() {
        ts.setBegin();
        System.out.println("Running " + threadName);
        for (int i = iterations; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
        }
        System.out.println("Thread " + threadName + " exiting.");
        ts.setEnd();
        System.out.println(ts.toString());
        UserLogging.logAction(threadName, ts.toString());
    }    
}
