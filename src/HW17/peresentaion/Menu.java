package HW17.peresentaion;

import HW17.entity.Card;
import HW17.entity.Transaction;
import HW17.entity.User;
import HW17.service.CardService;
import HW17.service.TransactionService;
import HW17.service.UserService;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private final UserService userService;
    private final CardService cardService;
    private final TransactionService transactionService;
    private final Scanner scanner;
    private User currentUser;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMM");

    public Menu() {
        this.userService = new UserService();
        this.cardService = new CardService();
        this.transactionService = new TransactionService();
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
    }

    public void run() {
        while (true) {
            showMainMenu();
            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    if (currentUser != null) {
                        showUserMenu();
                    }
                    break;
                case 3:
                    exit();
                    return;
                default:
                    System.out.println("invalid choice. Please try again");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("main menu");
        System.out.println("1. register");
        System.out.println("2. login");
        System.out.println("3. exit");
    }

    private void showUserMenu() {
        while (true) {
            System.out.println("user menu");
            System.out.println("1. card operations");
            System.out.println("2. financial operations");
            System.out.println("3. Logout");

            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    showCardMenu();
                    break;
                case 2:
                    showFinancialMenu();
                    break;
                case 3:
                    currentUser = null;
                    return;
                default:
                    System.out.println("invalid choice, please try again");
            }
        }
    }

    private void showCardMenu() {
        while (true) {
            System.out.println("card operations");
            System.out.println("1. register card");
            System.out.println("2. delete card");
            System.out.println("3. show card by card number");
            System.out.println("4. show cards by bank name");
            System.out.println("5. show all Cards");
            System.out.println("6. back");

            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    registerCard();
                    break;
                case 2:
                    deleteCard();
                    break;
                case 3:
                    showCardByCardNumber();
                    break;
                case 4:
                    showCardsByBankName();
                    break;
                case 5:
                    showAllCards();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("invalid choice. please try again");
            }
        }
    }

    private void showFinancialMenu() {
        while (true) {
            System.out.println("financial operations");
            System.out.println("1. normal transfer (card to card)");
            System.out.println("2. PAYA transfer (individual)");
            System.out.println("3. PAYA transfer (batch)");
            System.out.println("4. SATNA transfer");
            System.out.println("5. back");

            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    normalTransfer();
                    break;
                case 2:
                    payaTransfer();
                    break;
                case 3:
                    payaTransferBatch();
                    break;
                case 4:
                    satnaTransfer();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("invalid choice, please try again");
            }
        }
    }

    private int getMenuChoice() {
        System.out.print("enter your choice: ");
        try {
            return scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.next();
            System.out.println("invalid input. please enter a number.");
            return -1;
        }
    }

    private void registerUser() {
        System.out.print("enter username: ");
        String username = scanner.next();
        System.out.print("enter password: ");
        String password = scanner.next();

        try {
            User user = userService.registerUser(username, password);
            System.out.println("user registered successfully. eser ID: " + user.getId());
        } catch (RuntimeException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void loginUser() {
        System.out.print("enter username: ");
        String username = scanner.next();
        System.out.print("enter password: ");
        String password = scanner.next();

        try {
            User user = userService.loginUser(username, password);
            if (user != null) {
                currentUser = user;
                System.out.println("login successful.");
            } else {
                System.out.println("invalid username or password.");
            }
        } catch (RuntimeException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void registerCard() {
        System.out.print("enter card number: ");
        String cardNumber = scanner.next();
        System.out.print("enter bank name: ");
        String bankName = scanner.next();
        System.out.print("enter expire date (yyMM): ");
        String expireDateString = scanner.next();
        System.out.print("enter CVV2: ");
        String cvv2 = scanner.next();

        try {
            YearMonth expireDate = YearMonth.parse(expireDateString, FORMATTER);
            //search DTO
            Card card = new Card();
            card.setCardNumber(cardNumber);
            card.setBankName(bankName);
            card.setExpireDate(expireDate);
            card.setCvv2(cvv2);
            card.setBalance(1000000000.0);
            card.setUser(currentUser);

            Card registeredCard = cardService.registerCard(card);
            System.out.println("card registered successfully. card ID: " + registeredCard.getId());

        } catch (RuntimeException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void deleteCard() {
        System.out.print("enter card ID to delete: ");
        try {
            int cardId = scanner.nextInt();
            cardService.deleteCard(cardId);
            System.out.println("card deleted successfully.");
        } catch (RuntimeException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void showCardByCardNumber() {
        System.out.print("enter card number to search: ");
        String cardNumber = scanner.next();
        Optional<Card> card = cardService.getCardByCardNumber(cardNumber);
        if (card.isPresent()) {
            System.out.println(card.get());
        } else {
            System.out.println("card not found.");
        }
    }

    private void showCardsByBankName() {
        System.out.print("enter bank name to search: ");
        String bankName = scanner.next();
        List<Card> cards = cardService.getCardsByBankName(bankName);
        if (!cards.isEmpty()) {
            for (Card card : cards) {
                System.out.println(card);
            }
        } else {
            System.out.println("no cards found for this bank.");
        }
    }

    private void showAllCards() {
        List<Card> cards = cardService.getAllCards();
        if (!cards.isEmpty()) {
            for (Card card : cards) {
                System.out.println(card);
            }
        } else {
            System.out.println("no cards found.");
        }
    }

    private void normalTransfer() {
        System.out.print("enter source card number: ");
        String sourceCardNumber = scanner.next();
        System.out.print("enter destination card number: ");
        String destinationCardNumber = scanner.next();
        System.out.print("enter amount to transfer: ");
        try {
            double amount = scanner.nextDouble();
            Transaction transaction = transactionService.transferNormal(sourceCardNumber, destinationCardNumber, amount);
            System.out.println("transfer successful. transaction ID: " + transaction.getId());
        } catch (RuntimeException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void payaTransfer() {
        System.out.print("enter source card number: ");
        String sourceCardNumber = scanner.next();
        System.out.print("enter destination card number: ");
        String destinationCardNumber = scanner.next();
        System.out.print("enter amount to transfer: ");
        try {
            double amount = scanner.nextDouble();
            Transaction transaction = transactionService.transferPaya(sourceCardNumber, destinationCardNumber, amount);
            System.out.println("transfer successful. transaction ID: " + transaction.getId());
        } catch (RuntimeException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void payaTransferBatch() {
        System.out.print("enter the number of transactions: ");
        try {
            int numberOfTransactions = scanner.nextInt();
            List<Transaction> transactions = new ArrayList<>();
            for (int i = 0; i < numberOfTransactions; i++) {
                System.out.println("transaction " + (i + 1) + ":");
                System.out.print("enter source card number: ");
                String sourceCardNumber = scanner.next();
                System.out.print("enter destination card number: ");
                String destinationCardNumber = scanner.next();
                System.out.print("enter amount to transfer: ");
                double amount = scanner.nextDouble();

                Transaction transaction = new Transaction();
                transaction.setSourceCardNumber(sourceCardNumber);
                transaction.setDestinationCardNumber(destinationCardNumber);
                transaction.setAmount(amount);
                transactions.add(transaction);
            }

            List<Transaction> processedTransactions = transactionService.transferPayaBatch(transactions);
            System.out.println("batch transfer successful. transactions processed: " + processedTransactions.size());
        } catch (RuntimeException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void satnaTransfer() {
        System.out.print("enter source card number: ");
        String sourceCardNumber = scanner.next();
        System.out.print("enter destination card number: ");
        String destinationCardNumber = scanner.next();
        System.out.print("enter amount to transfer: ");
        try {
            double amount = scanner.nextDouble();
            Transaction transaction = transactionService.transferSatna(sourceCardNumber, destinationCardNumber, amount);
            System.out.println("transfer successful. transaction ID: " + transaction.getId());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void exit() {
        System.out.println("exiting...");
    }

}