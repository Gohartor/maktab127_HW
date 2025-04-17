package HW17.repository;

import HW17.entity.Transaction;
import HW17.entity.TransactionStatus;
import HW17.entity.TransactionType;
import HW17.repository.base.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    List<Transaction> findBySourceCardNumber(String sourceCardNumber);

    List<Transaction> findByDestinationCardNumber(String destinationCardNumber);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByStatus(TransactionStatus status);

    List<Transaction> findByTransactionTimestamp(LocalDateTime transactionTimestamp);
}