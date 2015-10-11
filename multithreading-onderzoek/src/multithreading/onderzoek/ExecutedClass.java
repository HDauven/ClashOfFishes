package multithreading.onderzoek;

/**
 * ExecutedClass test
 * @author Hein Dauven
 */
public class ExecutedClass implements Runnable{
    private final String threadName;
    
    /**
     * 
     * @param name
     */
    public ExecutedClass(String name) {
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
}
