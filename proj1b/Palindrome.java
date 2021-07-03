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

        if (p.size() == 0 || p.size() == 1) {
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

    public static int[] flatten(int[][] x) {
        int totalLength = 0;

        for (int[] p: x) {
            totalLength += p.length;
        }

        int[] a = new int[totalLength];
        int aIndex = 0;

        for (int[] p: x) {
            for (int p0: p) {
                a[aIndex] = p0;
                aIndex += 1;
            }
        }

        return a;
    }

    public void skippify() {
        IntList p = this;
        int n = 1;
        while (p != null) {
            IntList next = p.rest;
            for (int index = 0; index < n; index += 1) {
                next = p.rest;
                next = next.rest;
            }


        }
    }

    public static void removeDuplicates(IntList p) {
        if (p == null) {
            return;
        }
        IntList current = p.rest;
        IntList previous = p;
        while (current != null) {
            if (current.first == previous.first) {
                previous.rest = current.rest;
            } else {
                previous = current;
            }
            current = current.rest;

        }
    }

}
