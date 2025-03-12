package HW11.Q2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Box<T> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

class GenericTest {
    public static void main(String[] args) {

        Box<Number> numberBox = new Box<>();
        numberBox.setItem(10);

//        Box<? extends Number> wildcardBox = numberBox;
//
//        // The following line will cause a compilation error:
//        wildcardBox.setItem(5.5); // Double is also a Number

        // **** chon ? amade hatman Double nist momkene Integer bashad
        Box<Number> wildcardBox = numberBox;
        wildcardBox.setItem(5.5);


        List<Box<? super Integer>> list = new ArrayList<>();
        list.add(new Box<Number>()); // This is valid
        list.add(new Box<Object>()); // This would also be valid

        // The following line will cause a compilation error:
        // Box<Integer> intBox = list.get(0);

        // **** chon generic box malom nist nemitonim az class khasi estefade konim
         Box<?> intBox = list.get(0);


        // The following line will cause a compilation error:
        // Integer intBox = intBox.getItem();

        // **** chon getitem maloom nist ke che jensi hast baraye hamin bayad dakhel var ya object berizim
         var intBox1 = intBox.getItem();
         Object intBox2 = intBox.getItem();


         Map<String, List<String>> listMap = new HashMap<String, List<String>>();
         var listMap2 = new HashMap<String, List<String>>();

         //golang
         //listMap3 := new HashMap<String, List<String>>();

         //var keyword hast --> baraye datatype estefade mishavad
    }
}

