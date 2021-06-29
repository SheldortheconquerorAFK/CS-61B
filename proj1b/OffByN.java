public class OffByN implements CharacterComparator {

    int num;

    OffByN(int N) {
        num = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == num || y - x == num) {
            return true;
        } else {
            return false;
        }
    }
}


