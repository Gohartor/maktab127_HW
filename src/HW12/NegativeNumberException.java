package HW12;

import java.util.Scanner;

public class NegativeNumberException extends Exception {


    NegativeNumberException(String message) {
        super(message);
    }


    static void add (int number) throws NegativeNumberException {
        if (number >= 0) {
            System.out.println(number);
        }
        else {
            throw new NegativeNumberException("negative number");
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("your number: ");

        try{
            add(scanner.nextInt());
        }
        catch (NegativeNumberException nne){
            System.out.println(nne.getMessage());
        }
        finally{
            System.out.println("finally");
        }

    }


}