package HW15.Q1;

import java.util.*;

public class NumberList {
    final static List<Number> numbers = new ArrayList<>();
    static boolean isOddTurn = false;

    public synchronized void addNumber(int num, boolean isOdd) {

        while (isOddTurn != isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        numbers.add(num);
        isOddTurn = !isOddTurn;
        notify();
    }


    public void printAll() {
        System.out.println(Arrays.toString(numbers.toArray()));
//        if (numbers.isEmpty()) {
//            System.out.println("numberList is empty");
//        }
//       numbers.forEach(System.out::print);
    }
}
