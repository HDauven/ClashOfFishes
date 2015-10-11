package multithreading.onderzoek;

/**
 * ThreadClass test
 * @author Hein Dauven
 */
public class ThreadClass extends Thread {
    private Thread t;
    private final String threadName;
    
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
    @Override
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }     
}
