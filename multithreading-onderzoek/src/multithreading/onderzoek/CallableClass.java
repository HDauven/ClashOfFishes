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
    private final int iterations;

    public CallableClass(String name, int iterations)
    {
        this.threadName = name;
        this.iterations = iterations;
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    public Long call() throws Exception
    {
        int a = 0;
        ts.setBegin();
        for (int i = 0; i < iterations; i++)
        {
            System.out.println("Thread: " + threadName + ", " + i);
            a = i;
        }
        ts.setEnd();
        System.out.println(ts.toString());
        UserLogging.logAction("Callable", ts.toString());
        return (long) a;
    }
}
