import static java.lang.Character.*;

public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        if ((isUpperCase(x) && toLowerCase(x) == y) || (isUpperCase(y) && toLowerCase(y) == x)) {
            return true;
        }
        return x - y == 1 || y - x == 1;
    }

}
