package HW15.Q1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        NumberList list = new NumberList();

        Scanner input = new Scanner(System.in);
        System.out.print("enter your number: ");
        int n = input.nextInt();

        Thread oddThread = new Thread(new OddThread(n, list));
        EvenThread evenThread = new EvenThread(n, list);

        oddThread.start();
        evenThread.start();

        try {
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        list.printAll();

    }
}
