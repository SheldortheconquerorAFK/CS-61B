public class OffByN implements CharacterComparator {

    int num;

    OffByN(int N) {
        num = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == num || y - x == num;
    }
}


