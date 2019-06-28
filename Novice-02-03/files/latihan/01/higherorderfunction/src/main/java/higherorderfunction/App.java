/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package higherorderfunction;

import java.util.*;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("One");
        list.add("Abc");
        list.add("BCD");

        Collections.sort(list, (String a, String b) -> {
            return a.compareTo(b);
        });

        System.out.println(list); 

        // System.out.println(new App().getGreeting());
    }
}