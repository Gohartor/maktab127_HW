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
        Account currentAccount = accountRepository.findByCardName(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        }
        if (amount <= 0){
            throw new InvalidAmountException("negative or zero amount");
        }
        if (currentAccount.getBalance() < amount) {
            throw new InsufficientFundsException("your balance not enough");
        }
    }


    public void deposit(String cardNumber, double amount) {
        Account currentAccount = accountRepository.findByCardName(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        } else if (amount <= 0) {
            throw new InvalidAmountException("negative or zero amount");
        } else {
            currentAccount.setBalance(currentAccount.getBalance() + amount);
        }
    }

    public boolean transfer (String from, String to, double amount) {
        Account fromAccount = accountRepository.findByCardName(from);
        Account toAccount = accountRepository.findByCardName(to);
        if (fromAccount == null || toAccount == null) {
            return false;
        } else if (fromAccount.getBalance() < amount) {
            throw new InsufficientFundsException("your balance not enough");
        } else {
            deposit(fromAccount.getCardNumber(), amount);
            withdraw(toAccount.getCardNumber(), amount);
            return true;
        }
    }

    public Double checkBalance(String cardNumber) {
        Account currentAccount = accountRepository.findByCardName(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        } else{
            return currentAccount.getBalance();
        }
    }

    public boolean resetPin(String cardNumber, Integer pin) {
        Account currentAccount = accountRepository.findByCardName(cardNumber);
        if (currentAccount == null) {
            throw new NullPointerException("account not found");
        } else if (pin <= 0) {

        }
        return false;
    }
}
