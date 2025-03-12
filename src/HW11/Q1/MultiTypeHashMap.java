package HW11.Q1;

import java.util.HashMap;

public class MultiTypeHashMap<K> extends HashMap<K, Object> {

    public <V> V get(K key, Class<V> valueType) {
        var value = super.get(key);
        if (valueType.isInstance(value)) {
//            return (value)valueType;  ----->  in model cast eshtebah ast
            return valueType.cast(value);
        }
            return null;

    }


    public static void main(String[] args) {

            MultiTypeHashMap<String> bookInfo = new MultiTypeHashMap<>();

            bookInfo.put("title", "book1");
            bookInfo.put("author", "author1");
            bookInfo.put("year", 2020);
            bookInfo.put("price", 98.5);

            String title = bookInfo.get("title", String.class);
            String author = bookInfo.get("author", String.class);
            Integer year = bookInfo.get("year", Integer.class);
            Double price = bookInfo.get("price", Double.class);

            Integer price1 = bookInfo.get("price", Integer.class);
            String price2 = bookInfo.get("price", String.class);



            System.out.println("title: " + title);
            System.out.println("author: " + author);
            System.out.println("year: " + year);
            System.out.println("price: " + price);

            System.out.println("price1: " + price1);
            System.out.println("price2: " + price2);


    }
}
