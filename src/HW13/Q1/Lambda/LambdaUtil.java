package HW13.Q1.Lambda;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaUtil {


    /**
     * print all elements
     */
    public static <T> void printElements(List<T> list) {
        list.forEach(System.out::println);
    }



    /**
     * filter list for condition
     */
    public static <T> List<T> filterList(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }



    /**
     * function for change list
     */
    public static <T, R> List<R> transformList(List<T> list, Function<T, R> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    /**
     * sort list
     */
    public static <T extends Comparable<T>> List<T> sortList(List<T> list) {
        return list.stream().sorted().collect(Collectors.toList());
    }


    /**
     * action for element
     */
    public static <T> void performAction(List<T> list, Consumer<T> consumer) {
        list.forEach(consumer);
    }



}
