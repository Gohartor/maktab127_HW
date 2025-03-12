package HW11.Q3;

public class Container<T1, T2> {
    private T1 first;
    private T2 second;

    public void addFirst(T1 first) {
        this.first = first;
    }

    public void addSecond(T2 second) {
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }


    public Container<T2, T1> swap(Container<T1, T2> container) {

        T1 tempFirst = container.getFirst();
        T2 tempSecond = container.getSecond();

        Container<T2, T1> newContainer = new Container<>();

        newContainer.addFirst(tempSecond);
        newContainer.addSecond(tempFirst);
        return newContainer;
    }

    public Container<T2, T1> swapNew() {

        Container<T2, T1> newContainer = new Container<>();

        newContainer.addFirst(second);
        newContainer.addSecond(first);
        return newContainer;
    }


    public static void main(String[] args) {

        Container<Integer, String> container = new Container<>();

        container.addFirst(5);
        container.addSecond("alooo");


        System.out.println("first: " + container.getFirst());
        System.out.println("second: " + container.getSecond());

        System.out.println("first after swap: " + container.swapNew().getFirst());
        System.out.println("second after swap: " + container.swapNew().getSecond());


        // 01 -> swapNew
        //Container<String, Integer> containerSwap = container.swap();

        // 02 -> static
        //Container.swap(container);


        //Container<String, Integer> stringIntegerContainer = container.swapNew();


        //System.out.println("First: " + containerSwap.getFirst());
        //System.out.println("Second: " + containerSwap.getSecond());
    }
}
