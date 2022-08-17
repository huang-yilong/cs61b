import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));  // true
        assertTrue(offByOne.equalChars('r', 'q'));  // true
        assertTrue(offByOne.equalChars('&', '%'));  // true
        assertTrue(offByOne.equalChars('B', 'A')); // false
        assertFalse(offByOne.equalChars('a', 'e')); // false
        assertFalse(offByOne.equalChars('B', 'a')); // false
        assertFalse(offByOne.equalChars('a', 'a')); // false
    }
}
