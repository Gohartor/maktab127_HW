package HW15.Q1;


public class EvenThread extends Thread {
    final NumberList list;
    int n;

    EvenThread(int n, NumberList list) {
        this.n = n;
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i <= n; i += 2) {
            list.addNumber(i, false);
        }
    }
}
