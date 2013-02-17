package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 10:55 PM
 */
public class AvlTree<T extends Comparable<T>> extends HvlTree {
    /*
     * Constructor - Creates an HVL tree with allowed imbalance of 1.
     */
    AvlTree() {
        super(1);
    }
}
