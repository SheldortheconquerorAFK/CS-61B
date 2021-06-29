public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int len = word.length();
        ArrayDeque<Character> p = new ArrayDeque<>();

        for (int i = 0; i < len; i++) {
            p.addLast(word.charAt(i));
        }

        return p;
    }

    public boolean isPalindrome(String word) {
        int len = word.length();
        ArrayDeque<Character> p = new ArrayDeque<>();

        for (int i = 0; i < len; i++) {
            p.addLast(word.charAt(i));
        }
        return isPalindrome(p);
    }

    private boolean isPalindrome(Deque<Character> p) {

        if (p.size() == 0 || p.size() == 1){
            return true;
        }

        char firstChar = p.removeFirst();
        char lastChar = p.removeLast();

        if (firstChar == lastChar) {
            return isPalindrome(p);
        }

        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int len = word.length();
        if (len == 0 || len == 1) {
            return true;
        }

        if (cc.equalChars(word.charAt(0), word.charAt(len - 1))) {
            return isPalindrome(word.substring(1, len - 1), cc);
        }

        return false;
    }

}
