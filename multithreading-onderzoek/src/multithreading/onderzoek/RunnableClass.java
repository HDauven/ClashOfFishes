package multithreading.onderzoek;

/**
 * RunnableClass test
 * @author Hein Dauven
 */
public class RunnableClass implements Runnable {
    private Thread t;
    private final String threadName;

    
    /**
     * 
     * @param name
     */
    public RunnableClass(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }
    
    /**
     * 
     */
    @Override
    public void run() {
        System.out.println("Running " + threadName);
        try {
            for (int i = 100000; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a second
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interruped");
        }
        System.out.println("Thread " + threadName + " exiting.");
    }
    
    /**
     * 
     */
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }   
}
