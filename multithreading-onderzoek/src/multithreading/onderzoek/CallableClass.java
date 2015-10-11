package multithreading.onderzoek;

import java.util.concurrent.Callable;
import timeutil.TimeStamp;

/**
 * CallableClass test
 * @author Hein Dauven
 */
public class CallableClass implements Callable<Long> {
    TimeStamp ts = new TimeStamp();
    /**
     * 
     * @return
     * @throws Exception 
     */
    @Override
    public Long call() throws Exception {
        int a = 0;
        ts.setBegin();
        for (int i = 0; i < 10000; i++)
        {
            System.out.println("Hallo");
            a = i;
        }
        ts.setEnd();
        System.out.println(ts.toString());
        return (long) a;
    }   
}
