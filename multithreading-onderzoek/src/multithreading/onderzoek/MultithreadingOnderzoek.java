package multithreading.onderzoek;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import timeutil.TimeStamp;
import util.UserLogging;

/**
 * Test setup for the Multi-threading research assignment.
 * @author Hein Dauven & Stef Philipsen
 */
public class MultithreadingOnderzoek
{
    private final static Scanner sc = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        startUp();
        // TODO code application logic here
    }

    /**
     *
     */
    private static void startUp() throws Exception
    {
        welcome();
        int input;
        do
        {
            while (!sc.hasNextInt())
            {
                System.out.println("Invalid input!");
                sc.next();
            }
            input = sc.nextInt();
        }
        while (input < 0);
        getUserInput(input);
    }

    /**
     *
     */
    private static void welcome()
    {
        System.out.println("Welcome to the Multithreading research application.");
        System.out.println("By Stef Philipsen & Hein Dauven\n");
        System.out.println("This program tests 5 different implementations of multithreading.");
        System.out.println("1 - A class that implements Runnable");
        System.out.println("2 - A class that extends Thread");
        System.out.println("3 - A class that is part of an ExecutorPool");
        System.out.println("4 - A class that makes use of Futures/Callable");
        System.out.println("5 - A class that makes use of the Fork/Join framework\n");
        System.out.println("Pick a number to test one of the implementations or pick 0 to run them all.");
    }

    /**
     *
     * @param input
     */
    private static void getUserInput(int input) throws Exception
    {
        switch (input)
        {
            case 0:
                System.out.println("You chose 0 - Run all 5 implementations");
                runAllImplementations();
                break;
            case 1:
                System.out.println("You chose 1 - A class that implements Runnable");
                runRunnableImplementation();
                break;
            case 2:
                System.out.println("You chose 2 - A class that extends Thread");
                runThreadImplementation();
                break;
            case 3:
                System.out.println("You chose 3 - A class that is part of an ExecutorPool");
                runExecutedImplementation();
                break;
            case 4:
                System.out.println("You chose 4 - A class that makes use of Futures/Callable");
                runCallableImplementation();
                break;
            case 5:
                System.out.println("You chose 5 - A class that makes use of the Fork/Join framework");
                // runForkJoinImplementation();
                break;
        }
    }

    /**
     *
     */
    private static void runAllImplementations()
    {
        TimeStamp stamp = new TimeStamp();
        stamp.setBegin("Begin all implementations");
        runRunnableImplementation();
        runThreadImplementation();
        // runExecutedImplementation();
        // runCallableImplementation();
        // runForkJoinImplementation();
        stamp.setEnd("End all implementations");
        System.out.println(stamp.toString());
        UserLogging.logAction("Run All", stamp.toString());
    }

    /**
     *
     */
    private static void runRunnableImplementation()
    {
        // heavy load test
        RunnableClass r1 = new RunnableClass("Runnable-1", 100000);
        r1.start();
        RunnableClass r2 = new RunnableClass("Runnable-2", 100000);
        r2.start();
        // low load test
        RunnableClass r3 = new RunnableClass("Runnable-3", 10000);
        r3.start();
        RunnableClass r4 = new RunnableClass("Runnable-4", 10000);
        r4.start();        
    }

    /**
     *
     */
    private static void runExecutedImplementation()
    {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        // heavy load test
        ExecutedClass e1 = new ExecutedClass("Executed-1", 100000);
        executor.execute(e1);
        ExecutedClass e2 = new ExecutedClass("Executed-2", 100000);
        executor.execute(e2);
        // low load test
        ExecutedClass e3 = new ExecutedClass("Executed-3", 10000);
        executor.execute(e3);
        ExecutedClass e4 = new ExecutedClass("Executed-4", 10000);
        executor.execute(e4);   
        executor.shutdown();
    }
    
    /**
     *
     */
    private static void runThreadImplementation()
    {
        // heavy load test
        ThreadClass t1 = new ThreadClass("Thread-1", 100000);
        t1.start();
        ThreadClass t2 = new ThreadClass("Thread-2", 100000);
        t2.start();
        // low load test
        ThreadClass t3 = new ThreadClass("Thread-3", 10000);
        t3.start();
        ThreadClass t4 = new ThreadClass("Thread-4", 10000);
        t4.start();        
    }

    private static void runCallableImplementation() throws Exception
    {
        CallableClass callable = new CallableClass();
        Long l = callable.call();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Long> submit = executor.submit(callable);
    }
}
