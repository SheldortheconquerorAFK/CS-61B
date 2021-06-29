import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        boolean test1 = palindrome.isPalindrome("cat");
        assertFalse(test1);

        boolean test2 = palindrome.isPalindrome("pop");
        assertTrue(test2);

        boolean test3 = palindrome.isPalindrome("a");
        assertTrue(test3);

        boolean test4 = palindrome.isPalindrome("");
        assertTrue(test4);

        boolean test5 = palindrome.isPalindrome("dagger");
        assertFalse(test5);

        boolean test6 = palindrome.isPalindrome("lollipop");
        assertFalse(test6);

        assertFalse(palindrome.isPalindrome("cat", TestOffByOne.offByOne));
        assertTrue(palindrome.isPalindrome("a", TestOffByOne.offByOne));
        assertTrue(palindrome.isPalindrome("", TestOffByOne.offByOne));
        assertTrue(palindrome.isPalindrome("flake", TestOffByOne.offByOne));

    }

}
