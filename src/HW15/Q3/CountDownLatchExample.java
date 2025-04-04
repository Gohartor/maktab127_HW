package HW15.Q3;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);


        new Thread(new Worker(latch, "work1")).start();
        new Thread(new Worker(latch, "work2")).start();
        new Thread(new Worker(latch, "work3")).start();

        try {
            latch.await();
            System.out.println("all works done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {
        private final CountDownLatch latch;
        private final String name;

        Worker(CountDownLatch latch, String name) {
            this.latch = latch;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " doing ...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " is done");
            latch.countDown();
        }
    }
}