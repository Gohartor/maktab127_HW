package HW15.Q1;

public class OddThread implements Runnable {
    int n;
    final NumberList list;

    OddThread(int n, NumberList list) {
        this.n = n;
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 1; i <= n; i += 2) {
            list.addNumber(i, true);
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}
