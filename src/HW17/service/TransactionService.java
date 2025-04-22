package HW17.service;

import HW17.config.ApplicationContext;
import HW17.entity.Card;
import HW17.entity.Transaction;
import HW17.entity.TransactionStatus;
import HW17.entity.TransactionType;
import HW17.repository.CardRepository;
import HW17.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    public TransactionService() {
        this.transactionRepository = ApplicationContext.getInstance().getTransactionRepository();
        this.cardRepository = ApplicationContext.getInstance().getCardRepository();
    }

    public Transaction transferNormal(String sourceCardNumber, String destinationCardNumber, double amount) {
        Optional<Card> sourceCard = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destinationCard = cardRepository.findByCardNumber(destinationCardNumber);

        //separate exception
        if (sourceCard.isEmpty() || destinationCard.isEmpty()) {
            throw new RuntimeException("invalid card number");
        }

        if (sourceCard.get().getBalance() < amount) {
            throw new RuntimeException("not enough balance");
        }
        //enum failed for this
        if (amount > 15000000) {
            throw new RuntimeException("transfer limit is 15 million tomans");
        }

        double transactionFee = calculateNormalTransferFee(amount);

        sourceCard.get().setBalance(sourceCard.get().getBalance() - amount - transactionFee);
        cardRepository.update(sourceCard.get());

        destinationCard.get().setBalance(destinationCard.get().getBalance() + amount);
        cardRepository.update(destinationCard.get());

        Transaction transaction = new Transaction();
        transaction.setSourceCardNumber(sourceCardNumber);
        transaction.setDestinationCardNumber(destinationCardNumber);
        transaction.setAmount(amount);
        transaction.setTransactionFee(transactionFee);
        transaction.setTransactionTimestamp(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.NORMAL_TRANSFER);
        transaction.setStatus(TransactionStatus.SUCCESSFUL);

        transaction = transactionRepository.create(transaction);

        return transaction;
    }

    //bank name equals is inner network
    private double calculateNormalTransferFee(double amount) {
        if (amount <= 10000000) {
            return 720;
        } else {
            return 1000 + (amount - 10000000) / 1000000 * 100;
        }
    }

    public Transaction transferPaya(String sourceCardNumber, String destinationCardNumber, double amount) {
        Optional<Card> sourceCard = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destinationCard = cardRepository.findByCardNumber(destinationCardNumber);

        if (sourceCard.isEmpty() || destinationCard.isEmpty()) {
            throw new RuntimeException("invalid card number");
        }

        if (sourceCard.get().getBalance() < amount) {
            throw new RuntimeException("not enough balance");
        }

        if (amount > 50000000) {
            throw new RuntimeException("transfer limit is 50 million tomans");
        }

        double transactionFee = amount * 0.001;

        //cardRepository -> cardService
        //update method in cardService
        sourceCard.get().setBalance(sourceCard.get().getBalance() - amount - transactionFee);
        cardRepository.update(sourceCard.get());

        destinationCard.get().setBalance(destinationCard.get().getBalance() + amount);
        cardRepository.update(destinationCard.get());

        Transaction transaction = new Transaction();
        transaction.setSourceCardNumber(sourceCardNumber);
        transaction.setDestinationCardNumber(destinationCardNumber);
        transaction.setAmount(amount);
        transaction.setTransactionFee(transactionFee);
        transaction.setTransactionTimestamp(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.PAYA_INDIVIDUAL);
        transaction.setStatus(TransactionStatus.SUCCESSFUL);

        transaction = transactionRepository.create(transaction);

        return transaction;
    }

    public List<Transaction> transferPayaBatch(List<Transaction> transactions) {
        double batchFee = calculatePayaBatchFee(transactions.size());
        for (Transaction transaction : transactions) {
            Optional<Card> sourceCard = cardRepository.findByCardNumber(transaction.getSourceCardNumber());
            Optional<Card> destinationCard = cardRepository.findByCardNumber(transaction.getDestinationCardNumber());
            //paya individual , transaction type in parameter

            //method for another
            validation(transaction, sourceCard, destinationCard);

            if (transaction.getAmount() > 50000000) {
                throw new RuntimeException("transfer limit is 15 million tomans");
            }
        }


        for (Transaction transaction : transactions) {
            Optional<Card> sourceCard = cardRepository.findByCardNumber(transaction.getSourceCardNumber());
            Optional<Card> destinationCard = cardRepository.findByCardNumber(transaction.getDestinationCardNumber());

            sourceCard.get().setBalance(sourceCard.get().getBalance() - transaction.getAmount());
            cardRepository.update(sourceCard.get());

            destinationCard.get().setBalance(destinationCard.get().getBalance() + transaction.getAmount());
            cardRepository.update(destinationCard.get());

            transaction.setTransactionFee(batchFee / transactions.size());
            transaction.setTransactionTimestamp(LocalDateTime.now());
            transaction.setTransactionType(TransactionType.PAYA_BATCH);
            transaction.setStatus(TransactionStatus.SUCCESSFUL);
            transactionRepository.create(transaction);
        }


        return transactions;
    }

    private static void validation(Transaction transaction, Optional<Card> sourceCard, Optional<Card> destinationCard) {
        if (sourceCard.isEmpty() || destinationCard.isEmpty()) {
            throw new RuntimeException("invalid card number");
        }

        if (sourceCard.get().getBalance() < transaction.getAmount()) {
            throw new RuntimeException("not enough balance");
        }
    }

    private double calculatePayaBatchFee(int transactionCount) {
        if (transactionCount <= 10) {
            return 12000;
        } else {
            return 12000 + (transactionCount - 10) * 1200;
        }
    }

    public Transaction transferSatna(String sourceCardNumber, String destinationCardNumber, double amount) {
        Optional<Card> sourceCard = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destinationCard = cardRepository.findByCardNumber(destinationCardNumber);

        if (sourceCard.isEmpty() || destinationCard.isEmpty()) {
            throw new RuntimeException("invalid card number");
        }

        if (sourceCard.get().getBalance() < amount) {
            throw new RuntimeException("not enough balance");
        }

        if (amount <= 50000000) {
            throw new RuntimeException("amount of SATNA money transfer must be more than 50 million");
        }

        if (amount > 200000000) {
            throw new RuntimeException("SATNA money transfer limit is 200 million Tomans.");
        }

        double transactionFee = amount * 0.002;


        sourceCard.get().setBalance(sourceCard.get().getBalance() - amount - transactionFee);
        cardRepository.update(sourceCard.get());

        destinationCard.get().setBalance(destinationCard.get().getBalance() + amount);
        cardRepository.update(destinationCard.get());

        Transaction transaction = new Transaction();
        transaction.setSourceCardNumber(sourceCardNumber);
        transaction.setDestinationCardNumber(destinationCardNumber);
        transaction.setAmount(amount);
        transaction.setTransactionFee(transactionFee);
        transaction.setTransactionTimestamp(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.SATNA);
        transaction.setStatus(TransactionStatus.SUCCESSFUL);

        transaction = transactionRepository.create(transaction);

        return transaction;
    }

    public List<Transaction> getTransactionsBySourceCard(String sourceCardNumber) {
        return transactionRepository.findBySourceCardNumber(sourceCardNumber);
    }

    public List<Transaction> getTransactionsByDestinationCard(String destinationCardNumber) {
        return transactionRepository.findByDestinationCardNumber(destinationCardNumber);
    }

    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByTransactionType(type);
    }

    public List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status);
    }

    public List<Transaction> getTransactionsByDate(LocalDateTime timestamp) {
        return transactionRepository.findByTransactionTimestamp(timestamp);
    }
}