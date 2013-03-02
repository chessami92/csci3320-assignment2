import structure.tree.*;

import java.util.*;

import static org.apache.commons.lang.StringUtils.rightPad;

/**
 * Created by: Josh
 * On: 2/16/13 10:55 PM
 */
public class TestEfficiency {
    private static final Random random = new Random();
    private static final int NUM_TESTS = 10;
    private static final int TEST_LENGTH = 1000;
    private static final int SEARCH_LENGTH = 100;

    public static void main(String[] args) {
        final Map<String, Statistics> statistics = setupStatistics();

        for (int i = 0; i < NUM_TESTS; ++i) {
            testTrees(statistics);
        }

        final int columnWidth = 15;

        System.out.println(rightPad("Tree Type", columnWidth)
                + rightPad("Max Search", columnWidth)
                + rightPad("Min Search", columnWidth)
                + rightPad("Avg Search", columnWidth)
                + rightPad("Max Rotations", columnWidth)
                + rightPad("Min Rotations", columnWidth)
                + rightPad("Avg Rotations", columnWidth)
                + rightPad("Avg Height", columnWidth));
        for (BinarySearchTree tree : createTrees()) {
            String treeType = tree.getTreeType();
            Statistics treeStatistics = statistics.get(treeType);
            System.out.println(rightPad(treeType, columnWidth)
                    + rightPad(String.valueOf(treeStatistics.getMaxSearch()), columnWidth)
                    + rightPad(String.valueOf(treeStatistics.getMinSearch()), columnWidth)
                    + rightPad(String.valueOf(treeStatistics.getAverageSearch()), columnWidth)
                    + rightPad(String.valueOf(treeStatistics.getMaxRotations()), columnWidth)
                    + rightPad(String.valueOf(treeStatistics.getMinRotations()), columnWidth)
                    + rightPad(String.valueOf(treeStatistics.getAverageRotations()), columnWidth)
                    + rightPad(String.valueOf(treeStatistics.getAverageHeight()), columnWidth));
        }

    }

    /*
     *
     */
    private static void testTrees(Map<String, Statistics> statistics) {
        final BinarySearchTree[] binarySearchTrees = createTrees();
        final int[] testData = randomIntegersNoRepeats(TEST_LENGTH, -TEST_LENGTH, TEST_LENGTH);
        final int[] findData = randomIntegersNoRepeats(SEARCH_LENGTH, -TEST_LENGTH, TEST_LENGTH);

        for (BinarySearchTree<Integer> binarySearchTree : binarySearchTrees) {
            Statistics treeStatistics = statistics.get(binarySearchTree.getTreeType());
            for (int i : testData) {
                binarySearchTree.insert(i);
            }

            for (int i : findData) {
                binarySearchTree.search(i);
                treeStatistics.updateSearch(binarySearchTree.getSearchCount());
            }

            treeStatistics.updateRotations(binarySearchTree.getRotations());
            treeStatistics.updateHeight(binarySearchTree.getHeight());
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
        return new BinarySearchTree[]{new AvlTree<Integer>(), new SplayTree(), new HvlTree<Integer>(10), new HvlTree<Integer>(100)};
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
