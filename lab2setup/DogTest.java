import static org.junit.Assert.*;
import org.junit.Test;

public class DogTest {
    @Test
    private void testSmall() {
        Dog d = new Dog(3);
        assertEquals("yip", d.noise());
    }

    @Test
    private void testLarge() {
        Dog d = new Dog(20);
        assertEquals("bark", d.noise());
    }
}
