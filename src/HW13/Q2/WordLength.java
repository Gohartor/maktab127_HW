package HW13.Q2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WordLength {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, List<String>> wordLength = new HashMap<>();

        while (true) {
            System.out.print("enter word: ");
            String word = scanner.nextLine();

            int length = word.length();
            wordLength.computeIfAbsent(length, k -> new ArrayList<>()).add(word);

            System.out.println("current: " + wordLength);

            if(word.equals("exit") || word.isEmpty()){
                break;
            }
        }
        scanner.close();
    }
}

