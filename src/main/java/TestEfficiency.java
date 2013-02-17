import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by: Josh
 * On: 2/16/13 10:55 PM
 */
public class TestEfficiency {
    private static Random random = new Random();

    public static void main(String[] args) {

    }

    /*
     * Generates an array of random integers from -length to +length
     * inclusive, with no repeating values.
     */
    static int[] randomIntegersNoRepeats(int length) {
        //Set of valid possible integers
        List<Integer> selectionSet = createIntegerList(-length, length);

        int[] integers = new int[length];

        for (int i = length; i > 0; --i) {
            //Selects a random element from the selection set.
            int nextElement = random.nextInt(selectionSet.size());
            //Removes the selected element from the next so it isn't used again.
            integers[length - i] = selectionSet.remove(nextElement);
        }

        return integers;
    }

    /*
     * Generates a list of integers between lower bound
     * and upper bound inclusive.
     */
    static List<Integer> createIntegerList(int lowerBound, int upperBound) {
        LinkedList<Integer> list = new LinkedList<Integer>();

        for (int i = lowerBound; i <= upperBound; ++i)
            list.add(i);

        return list;
    }
}
