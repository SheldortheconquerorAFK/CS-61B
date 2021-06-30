import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    CharacterComparator offByN0 = new OffByN(0);
    CharacterComparator offByN1 = new OffByN(1);
    CharacterComparator offByN5 = new OffByN(5);

    @Test
    public void testEqualChars() {
        assertTrue(offByN0.equalChars('a', 'a'));
        assertFalse(offByN0.equalChars('a', 'b'));
        assertTrue(offByN1.equalChars('a', 'b'));
        assertFalse(offByN1.equalChars('a', 'f'));
        assertTrue(offByN5.equalChars('a', 'f'));
        assertFalse(offByN5.equalChars('a', 'a'));
    }
}
