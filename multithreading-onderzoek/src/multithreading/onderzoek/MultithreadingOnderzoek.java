package multithreading.onderzoek;

import java.util.Scanner;

/**
 * Test setup for the Multi-threading research assignment.
 * @author Hein Dauven & Stef Philips
 */
public class MultithreadingOnderzoek {
    private final static Scanner sc = new Scanner(System.in);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        startUp();
        // TODO code application logic here
    }
    
    /**
     * 
     */
    private static void startUp() {
        welcome();
        int input;
        do {
            while (!sc.hasNextInt()){
                System.out.println("Invalid input!");
                sc.next();
            }
            input = sc.nextInt();
        } while (input < 0);
        getUserInput(input);
    }
    
    /**
     * 
     */
    private static void welcome() {
        System.out.println("Welcome to the Multithreading research application.");
        System.out.println("By Stef Philips & Hein Dauven\n");
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
    private static void getUserInput(int input) {
        switch (input) {
            case 0:
                System.out.println("You chose 0 - Run all 5 implementations");
                // runAllImplementations();
                break;
            case 1:
                System.out.println("You chose 1 - A class that implements Runnable");
                // runRunnableImplementation();
                break;
            case 2:
                System.out.println("You chose 2 - A class that extends Thread");
                // runThreadImplementation();
                break;
            case 3: 
                System.out.println("You chose 3 - A class that is part of an ExecutorPool");
                // runExecutedImplementation();
                break;
            case 4: 
                System.out.println("You chose 4 - A class that makes use of Futures/Callable");
                // runCallableImplementation();
                break;
            case 5:
                System.out.println("You chose 5 - A class that makes use of the Fork/Join framework");
                // runForkJoinImplementation();
                break;
        }
    }
}
