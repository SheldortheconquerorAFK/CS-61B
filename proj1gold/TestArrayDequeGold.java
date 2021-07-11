import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

/**
 * @source StudentArrayDequeLauncher.java
 */
public class TestArrayDequeGold {
    @Test
    public void testAD() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String message = "";

        // Test for addFirst
        for (int i = 0; i < 10; i += 1) {
            int random = StdRandom.uniform(100);
            sad.addFirst(random);
            ads.addFirst(random);
            message += "addFirst(" + random + ")\n";
        }
        for (int i = 0; i < 10; i += 1) {
            Integer actual = sad.get(i);
            Integer expect = ads.get(i);
            assertEquals(message, actual, expect);
        }

        // Test for addLast
        for (int i = 0; i < 10; i += 1) {
            int random = StdRandom.uniform(100);
            sad.addLast(random);
            ads.addLast(random);
            message += "addLast(" + random + ")\n";
        }
        for (int i = 0; i < 10; i += 1) {
            Integer actual = sad.get(i);
            Integer expect = ads.get(i);
            assertEquals(message, actual, expect);
        }

        // Test for removeFirst
        for (int i = 0; i < 10; i += 1) {
            Integer actual = sad.removeFirst();
            Integer expect = ads.removeFirst();
            message += "removeFirst()\n";
            assertNotNull(message, actual);
            assertEquals(message, actual, expect);
        }

        // Test for removeLast
        for (int i = 0; i < 10; i += 1) {
            Integer actual = sad.removeLast();
            Integer expect = ads.removeLast();
            message += "removeLast()\n";
            assertNotNull(message, actual);
            assertEquals(message, actual, expect);
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestArrayDequeGold.class);
    }

}
