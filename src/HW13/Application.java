package HW13;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        });


        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.decrement();
            }
        });

        Thread.currentThread().wait();
        t1.start();
        t2.start();
        Thread.currentThread().join();

        System.out.println(counter);

    }
}

class Counter {
    private int counter = 0;

    public void increment() {
        counter++;
    }

    public void decrement() {
        counter--;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "counter=" + counter +
                '}';
    }
}