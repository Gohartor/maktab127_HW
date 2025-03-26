package HW13.Q1;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class LambdaUtil {


    public static <T> T findFirst(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }


    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        return list.stream()
                .map(function)
                .toList();
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .toList();
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer firstEven = findFirst(numbers, n -> n % 2 == 0);
        System.out.println("first even: " + firstEven);

        List<Integer> squared = map(numbers, n -> n * n);
        System.out.println("squared: " + squared);

        List<Integer> evenNumbers = filter(numbers, n -> n % 2 == 0);
        System.out.println("all even numbers: " + evenNumbers);
    }
}
