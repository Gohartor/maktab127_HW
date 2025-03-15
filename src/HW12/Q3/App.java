package HW12.Q3;

import HW12.Q3.entity.Account;
import HW12.Q3.repository.AccountRepository;
import HW12.Q3.service.AccountService;

public class App {
    public static void main(String[] args) {
        Account account1 = new Account(1, "ali", "5022", 1234);
        Account account2 = new Account(2, "naqi", "6037", 1322);
        Account account3 = new Account(3, "taqi", "9917", 1367);
        Account account4 = new Account(4, "jafar", "4223", 1365);
        Account account5 = new Account(5, "javad", "9988", 1380);


        AccountRepository accountRepository = new AccountRepository();
        accountRepository.create(account1);
        accountRepository.create(account2);
        accountRepository.create(account3);
        accountRepository.create(account4);
        accountRepository.create(account5);


        //repository layer *******************************
        System.out.println("-----------------------------");
        System.out.println("print all");
        accountRepository.printAll();


        System.out.println("-----------------------------");
        System.out.println("find by name");
        System.out.println(accountRepository.findByName("ali").toString());


        System.out.println("-----------------------------");
        System.out.println("find by card number");
        System.out.println(accountRepository.findByCardNumber("6037").toString());


        System.out.println("-----------------------------");
        System.out.println("delete and print all");
        accountRepository.delete(account5);
        accountRepository.printAll();


        System.out.println("-----------------------------");
        System.out.println("update javad card number");
        Account accountNew = new Account(4, "javad", "0000", 1380);
        accountRepository.update(account4, accountNew);
        accountRepository.printAll();




        //service layer *******************************
        AccountService accountService = new AccountService(accountRepository);


        System.out.println("-----------------------------");
        System.out.println("withdraw");
        accountService.withdraw("5022", 50000);
        System.out.println(accountRepository.findByCardNumber("5022").toString());


        System.out.println("-----------------------------");
        System.out.println("deposit");
        accountService.deposit("5022", 500000);
        System.out.println(accountRepository.findByCardNumber("5022").toString());


        System.out.println("-----------------------------");
        System.out.println("transfer");
        accountService.transfer("6037","5022", 50000);
        System.out.println(accountRepository.findByCardNumber("6037").toString());
        System.out.println(accountRepository.findByCardNumber("5022").toString());

        System.out.println("-----------------------------");
        System.out.println("check balance for 5022");
        accountService.checkBalance("5022");


        System.out.println("-----------------------------");
        System.out.println("reset pin");
        accountService.resetPin("5022", 4321);
        System.out.println(accountRepository.findByCardNumber("5022").getPin());





    }
}
