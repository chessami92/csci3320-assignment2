import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by: Josh
 * On: 2/17/13 12:56 PM
 */
public class TestEfficiencyTest {

    @Test
    public void randomIntegersNoRepeat() {
        final int length = 10;
        int[] randomIntegers = TestEfficiency.randomIntegersNoRepeats(length);

        assertEquals(randomIntegers.length, length);

        for (int i = 0; i < randomIntegers.length; ++i) {
            assertTrue(randomIntegers[i] >= -length);
            assertTrue(randomIntegers[i] <= length);
            for (int j = i + 1; j < randomIntegers.length; ++j) {
                assertNotEquals(randomIntegers[j], randomIntegers[i]);
            }
        }
    }

    @Test
    public void createIntegerList() {
        final int length = 10;
        int[] testIntegers = TestEfficiency.createIntegerList(-length, length);

        assertEquals(testIntegers.length, 2 * length + 1);

        for (int i = -length; i <= length; ++i) {
            assertEquals(testIntegers[i + length], i);
        }
    }
}
