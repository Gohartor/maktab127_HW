package HW12.Q3.service;

import HW12.Q3.entity.Account;
import HW12.Q3.exception.InsufficientFundsException;
import HW12.Q3.exception.InvalidAmountException;
import HW12.Q3.exception.UnauthorizedAccessException;
import HW12.Q3.repository.AccountRepository;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean register(Account account) {
        return accountRepository.create(account);
    }

    public boolean login(String name, Integer pin) {
        Account currentAccount = accountRepository.findByName(name);
        if (currentAccount == null) {
            return false;
        }
        if (currentAccount.getPin().equals(pin)) {
            return true;
        } else {
            throw new UnauthorizedAccessException("invalid credentials");
        }
    }

    public void withdraw(String cardNumber, double amount) {
        Account currentAccount = accountRepository.findByCardNumber(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        } else if (amount <= 0){
            throw new InvalidAmountException("negative or zero amount");
        } else if (currentAccount.getBalance() < amount) {
            throw new InsufficientFundsException("your balance not enough");
        } else {
            currentAccount.setBalance(currentAccount.getBalance() - amount);
        }
    }


    public void deposit(String cardNumber, double amount) {
        Account currentAccount = accountRepository.findByCardNumber(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        } else if (amount <= 0) {
            throw new InvalidAmountException("negative or zero amount");
        } else {
            currentAccount.setBalance(currentAccount.getBalance() + amount);
        }
    }

    public boolean transfer (String from, String to, double amount) {
        Account fromAccount = accountRepository.findByCardNumber(from);
        Account toAccount = accountRepository.findByCardNumber(to);
        if (fromAccount == null || toAccount == null) {
            return false;
        } else if (fromAccount.getBalance() < amount) {
            throw new InsufficientFundsException("your balance not enough");
        } else {
            withdraw(fromAccount.getCardNumber(), amount);
            deposit(toAccount.getCardNumber(), amount);
            return true;
        }
    }

    public void checkBalance(String cardNumber) {
        Account currentAccount = accountRepository.findByCardNumber(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        } else{
            System.out.println(currentAccount.getBalance());
        }
    }

    public boolean resetPin(String cardNumber, Integer pin) {
        Account currentAccount = accountRepository.findByCardNumber(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        } else if (pin <= 0) {
            throw new InvalidAmountException("negative or zero amount");
        } else {
            currentAccount.setPin(pin);
        }
        return true;
    }
}
