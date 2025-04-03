package HW15.Q2;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        BankAccount account = new BankAccount(1000);

        Thread atm = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(5);
            }
        });

        Thread pos = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                account.withdraw(10);
            }
        });

        Thread mobileBank = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                account.printBalance();
            }
        });

        atm.start();
        mobileBank.start();
        pos.start();

        atm.join();
        mobileBank.join();
        pos.join();

    }
}

