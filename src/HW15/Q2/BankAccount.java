package HW15.Q2;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class BankAccount {
    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        lock.writeLock().lock();

        balance += amount;
        System.out.println(Thread.currentThread().getName() + " - New Balance: " + balance);
        lock.writeLock().unlock();

    }

    public void withdraw(double amount) {
        lock.writeLock().lock();

        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " - New Balance: " + balance);
        } else {
            System.out.println("Insufficient funds for withdrawal of: " + amount);
        }

        lock.writeLock().unlock();

    }

    public void printBalance() {
        lock.readLock().lock();
        System.out.println("Balance: " + balance);
        lock.readLock().unlock();

    }
}
