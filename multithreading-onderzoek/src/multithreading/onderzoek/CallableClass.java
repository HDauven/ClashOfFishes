package multithreading.onderzoek;

import java.util.concurrent.Callable;
import timeutil.TimeStamp;
import util.UserLogging;

/**
 * CallableClass test
 *
 * @author Hein Dauven
 */
public class CallableClass implements Callable<Long>
{
    TimeStamp ts = new TimeStamp();
    private final String threadName;
    private int iterations;

    public CallableClass(String name, int iterations)
    {
        this.threadName = name;
        this.iterations = iterations;
        System.out.println("Creating " + threadName);
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    public Long call() throws Exception
    {
        int i = 0;
        ts.setBegin();
        for (i = iterations; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
        }
        ts.setEnd();
        System.out.println(ts.toString());
        UserLogging.logAction("Callable", ts.toString());
        return (long) i;
    }
}
