package creatures;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c1 = new Clorus(2);
        Clorus c2 = c1.replicate();
        assertEquals(c1.energy(), c2.energy(), 0.01);
        assertNotSame(c1, c2);
    }
}
