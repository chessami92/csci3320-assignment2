import structure.tree.AvlTree;
import structure.tree.BinarySearchTree;
import structure.tree.HvlTree;
import structure.tree.Statistics;

import java.util.*;

/**
 * Created by: Josh
 * On: 2/16/13 10:55 PM
 */
public class TestEfficiency {
    private static final Random random = new Random();
    private static final int numTests = 10;
    private static final int testLength = 1000;

    public static void main(String[] args) {
        final Map<String, Statistics> statistics = setupStatistics();

        for (int i = 0; i < numTests; ++i) {
            testTrees(statistics);
        }
    }

    /*
     *
     */
    private static void testTrees(Map<String, Statistics> statistics) {
        final BinarySearchTree[] binarySearchTrees = createTrees();
        final int[] testData = randomIntegersNoRepeats(testLength, -testLength, testLength);
        final int[] findData = randomIntegersNoRepeats(100, -testLength, testLength);

        for (BinarySearchTree<Integer> binarySearchTree : binarySearchTrees) {
            for (int i : testData) {
                binarySearchTree.insert(i);
            }

            for (int i : findData) {
                if (binarySearchTree.exists(i))
                    statistics.get(binarySearchTree.getTreeType()).updateSearch(binarySearchTree.getSearchCount());
                else
                    binarySearchTree.getSearchCount();
            }

            statistics.get(binarySearchTree.getTreeType()).updateRotations(binarySearchTree.getRotations());
            statistics.get(binarySearchTree.getTreeType()).updateHeight(binarySearchTree.getHeight());
        }
    }

    /*
     * Creates an entry in the map for each type of tree with a statistics object.
     */
    private static Map<String, Statistics> setupStatistics() {
        final Map<String, Statistics> statistics = new HashMap<String, Statistics>();
        final BinarySearchTree[] binarySearchTrees = createTrees();

        for (BinarySearchTree binarySearchTree : binarySearchTrees) {
            statistics.put(binarySearchTree.getTreeType(), new Statistics());
        }

        return statistics;
    }

    /*
     * Method used to consistently create a list of trees.
     */
    private static BinarySearchTree[] createTrees() {
        return new BinarySearchTree[]{new AvlTree<Integer>(), new HvlTree<Integer>(10), new HvlTree<Integer>(100)};
    }

    /*
     * Generates an array of random integers from lowerBound to upperBound
     * inclusive, with no repeating values.
     * upperbound - lowerBound + 1 must be greater than length.
     */
    static int[] randomIntegersNoRepeats(int length, int lowerBound, int upperBound) {
        if (upperBound - lowerBound + 1 < length)
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
