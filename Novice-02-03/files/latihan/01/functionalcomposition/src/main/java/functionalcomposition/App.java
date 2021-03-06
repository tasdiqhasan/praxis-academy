/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package functionalcomposition;

import java.util.function.Predicate;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

        Predicate<String> startsWithA = (text) -> text.startsWith("A");
        Predicate<String> endsWithX   = (text) -> text.endsWith("x");

        Predicate<String> composed = startsWithA.and(endsWithX);

        String input = "A hardworking person must relax";
        boolean result = composed.test(input);
        System.out.println(result);
    }
}
