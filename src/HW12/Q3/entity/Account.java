package HW12.Q3.entity;

import java.time.LocalDate;
import java.util.List;

public class Account {
    private int id;
    private String name;
    private String cardNumber;
    private Integer pin;
    private Double balance = 100000.0;
    private List<LocalDate> transactionDate;
    private int wrongPassword = 3;
    private double limitTransaction = 10000000.0;
    private State state = State.DISABLE;


    public Account(int id, String name, String cardNumber, Integer pin) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public List<LocalDate> getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(List<LocalDate> transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getWrongPassword() {
        return wrongPassword;
    }

    public double getLimitTransaction() {
        return limitTransaction;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
