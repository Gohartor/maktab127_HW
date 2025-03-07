package HW11.Q4;

public class Triple<T1, T2, T3 extends Number> {
    private T1 first;
    private T2 second;
    private T3 third;

    public Triple(T1 first, T2 second, T3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    public T3 getThird() {
        return third;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public void setThird(T3 third) {
        this.third = third;
    }

    public double sum() {
        if (first instanceof Number n1 && third instanceof Number n3) {
            return n1.doubleValue() + n3.doubleValue();
        } else {
            return 0;
        }
    }




    public static void main(String[] args) {

        Triple<Integer, String, Double> triple1 = new Triple<>(2, "triple1", 3.1);
        System.out.println("first: " + triple1.getFirst() + " /second: " + triple1.getSecond() + " /third: " + triple1.getThird());
        System.out.println("sum: " + triple1.sum());


        Triple<String, Integer, Double> triple2 = new Triple<>("triple2", 5, 1.2);
        System.out.println("first: " + triple2.getFirst() + " /Second = " + triple2.getSecond() + " /third: " + triple2.getThird());
        System.out.println("sum (error): " + triple2.sum());

    }
}
