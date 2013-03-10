import structure.tree.*;

import java.util.*;

import static org.apache.commons.lang.StringUtils.rightPad;

/**
 * Created by: Josh
 * On: 2/16/13 10:55 PM
 */
public class TestEfficiency {
    private static final Random random = new Random();
    private static final int NUM_TESTS = 10;            //How many times to run the tests.
    private static final int TEST_LENGTH = 1000;        //How many integers to be inserted into the tree.
    private static final int SEARCH_LENGTH = 100;       //How many integers to search for in the tree.

    public static void main(String[] args) {
        //Statistics object used for keeping track of tree performance.
        final Map<String, Statistics> statistics = setupStatistics();

        //Perform the tests on each tree.
        for (int i = 0; i < NUM_TESTS; ++i) {
            testTrees(statistics);
        }

        //Print out table headers.
        final int columnWidth = 15;
        System.out.println(rightPad("Tree Type", columnWidth)
                + rightPad("Max Search", columnWidth)
                + rightPad("Min Search", columnWidth)
                + rightPad("Avg Search", columnWidth)
                + rightPad("Max Rotations", columnWidth)
                + rightPad("Min Rotations", columnWidth)
                + rightPad("Avg Rotations", columnWidth)
                + rightPad("Avg Height", columnWidth));

        //Print out results for each type of tree from statistics object.
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
     * Makes one of every type of tree, and makes a random array of integers with no repeats
     * and inserts these integers into every type of tree. Also makes a shorter array of integers
     * with no repeats and searches every binary tree for the elements. Updates the passed in
     * Statistics object for keeping track of all types of trees.
     */
    private static void testTrees(Map<String, Statistics> statistics) {
        //Make all types of trees, insert data, and data to search for.
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
                //Splay and Hybrid trees may update their height every search, so the statistics must be updated.
                treeStatistics.updateHeight(binarySearchTree.getHeight());
                //Update statistics with how many steps it took to search for the integer.
                treeStatistics.updateSearch(binarySearchTree.getSearchSteps());
            }

            //Update statistics with how many rotations occurred total in this tree.
            treeStatistics.updateRotations(binarySearchTree.getRotations());
        }
    }

    /*
     * Creates an entry in the map for each type of tree with a new statistics object.
     */
    private static Map<String, Statistics> setupStatistics() {
        final Map<String, Statistics> statistics = new HashMap<String, Statistics>();
        final BinarySearchTree[] binarySearchTrees = createTrees();

        for (BinarySearchTree binarySearchTree : binarySearchTrees) {
            //Put entry in the map with the tree type and a new Statistics object.
            statistics.put(binarySearchTree.getTreeType(), new Statistics());
        }

        return statistics;
    }

    /*
     * Method used to consistently create a list of trees. Should be used whenever the program needs
     * an array of all types of trees.
     */
    private static BinarySearchTree[] createTrees() {
        return new BinarySearchTree[]{new AvlTree<Integer>(), new SplayTree(), new HvlTree<Integer>(10), new HvlTree<Integer>(100), new HybridTree()};
    }

    /*
     * Generates an array of random integers from lowerBound to upperBound
     * inclusive, with no repeating values.
     * Note: upperbound - lowerBound + 1 must be greater than length.
     */
    static int[] randomIntegersNoRepeats(int length, int lowerBound, int upperBound) {
        //Check for invalid arguments that cannot create a proper sized array.
        if (upperBound - lowerBound + 1 < length)
            throw new IllegalArgumentException("Cannot create list with no repeating values. Need larger bounds.");

        //Set of valid possible integers, one entry from lower bound to upper bound
        List<Integer> selectionSet = createIntegerList(lowerBound, upperBound);

        //Array of integers to be returned, starts out blank
        int[] integers = new int[length];

        for (int i = length; i > 0; --i) {
            //Selects a random element from the current selection set.
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
        //Check that the lowerBound is less than the upperBound.
        if (upperBound < lowerBound)
            throw new IllegalArgumentException("upperBound is less than the lowerBound. Cannot populate list.");

        LinkedList<Integer> list = new LinkedList<Integer>();

        //Add in all valid values between the lower bound and the upper bound.
        for (int i = lowerBound; i <= upperBound; ++i)
            list.add(i);

        return list;
    }
}
