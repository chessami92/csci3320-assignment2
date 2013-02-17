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
     * Generates an array of random integers from lowerBound to upperBound
     * inclusive, with no repeating values.
     * upperbound - lowerBound + 1 must be greater than length.
     */
    static int[] randomIntegersNoRepeats(int length, int lowerBound, int upperBound) {
        if(upperBound - lowerBound + 1 < length)
            throw new IllegalArgumentException("Cannot create list with no repeating values. Need larger bounds.");

        //Set of valid possible integers
        List<Integer> selectionSet = createIntegerList(lowerBound, upperBound);

        int[] integers = new int[length];

        for (int i = length; i > 0; --i) {
            //Selects a random element from the selection set.
            int nextElement = random.nextInt(selectionSet.size());
            //Remove the selected element from the next so it isn't used again.
            integers[length - i] = selectionSet.remove(nextElement);
        }

        return integers;
    }

    /*
     * Generates a list of integers between lower bound and upper bound inclusive.
     */
    static List<Integer> createIntegerList(int lowerBound, int upperBound) {
        LinkedList<Integer> list = new LinkedList<Integer>();

        for (int i = lowerBound; i <= upperBound; ++i)
            list.add(i);

        return list;
    }
}
