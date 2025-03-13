package HW12.Q3.repository;

import HW12.Q3.entity.Account;

import java.util.LinkedList;

public class AccountRepository {
    private final LinkedList<Account> accounts = new LinkedList<>();

    public LinkedList<Account> getAccounts() {
        return accounts;
    }

    public boolean create(Account newAccount) {
        return accounts.add(newAccount);
    }

    public void delete(Account newAccount) {
        accounts.remove(newAccount);
    }

    public void update(Account oldaccount, Account newAccount) {
        if (accounts.contains(oldaccount)) {
            accounts.set(accounts.indexOf(oldaccount), newAccount);
        }
    }

    public Account findByName (String accountName) {
        for (Account account : accounts) {
            if (account.getName().equals(accountName)) {
                return account;
            }
        }
        return null;
    }


    public Account findByCardName (String cardName) {
        for (Account account : accounts) {
            if (account.getName().equals(cardName)) {
                return account;
            }
        }
        return null;
    }
}
