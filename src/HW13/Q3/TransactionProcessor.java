package HW13.Q3;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionProcessor {

    public static Map<String, Double> processTransactions(List<Transaction> transactions, LocalDate startDate, LocalDate endDate, double threshold) {
        return transactions.stream()
                .filter(transaction -> !transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate))
                .collect(Collectors.groupingBy(Transaction::getUserId, Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > threshold)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static void main(String[] args) {

        List<Transaction> transactions = List.of(
                new Transaction("1", "user1", 100.0, LocalDate.of(2025, 10, 1)),
                new Transaction("2", "user2", 200.0, LocalDate.of(2025, 10, 5)),
                new Transaction("3", "user1", 150.0, LocalDate.of(2025, 10, 10)),
                new Transaction("4", "user3", 50.0, LocalDate.of(2025, 10, 15)),
                new Transaction("5", "user2", 300.0, LocalDate.of(2025, 10, 20))
        );

        LocalDate startDate = LocalDate.of(2025, 10, 1);
        LocalDate endDate = LocalDate.of(2025, 10, 20);
        double threshold = 250.0;

        Map<String, Double> result = processTransactions(transactions, startDate, endDate, threshold);
        System.out.println(result);
    }
}
