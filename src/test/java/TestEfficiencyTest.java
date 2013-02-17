import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by: Josh
 * On: 2/17/13 12:56 PM
 */
public class TestEfficiencyTest {

    @Test
    public void randomIntegersNoRepeat() {
        final int length = 10;
        int[] randomIntegers = TestEfficiency.randomIntegersNoRepeats(length, -length, length);

        assertEquals(randomIntegers.length, length);

        for (int i = 0; i < randomIntegers.length; ++i) {
            assertTrue(randomIntegers[i] >= -length);
            assertTrue(randomIntegers[i] <= length);
            for (int j = i + 1; j < randomIntegers.length; ++j) {
                assertNotEquals(randomIntegers[j], randomIntegers[i]);
            }
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void randomIntegersNoRepeat_incorrectBounds() {
        final int length = 20;
        TestEfficiency.randomIntegersNoRepeats(length, 2, length);
    }

    @Test
    public void createIntegerList() {
        final int length = 10;
        List testIntegers = TestEfficiency.createIntegerList(-length, length);

        assertEquals(testIntegers.size(), 2 * length + 1);

        for (int i = -length; i <= length; ++i) {
            assertEquals(testIntegers.get(i + length), i);
        }
    }
}
