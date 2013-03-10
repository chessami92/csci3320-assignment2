package structure.tree;

import static structure.tree.BinarySearchTreeUtils.*;

/**
 * Created by: Josh
 * On: 2/16/13 10:09 PM
 */
public class HvlTree<T extends Comparable<T>> implements BinarySearchTree<T> {
    private BinarySearchNode<T> root;
    private int searchSteps;
    private int rotations;
    private int allowedImbalance;   //How much imbalance is allowed.

    /*
     * Constructor - specify the allowed imbalance.
     */
    public HvlTree(int allowedImbalance) {
        if (allowedImbalance > 0)
            this.allowedImbalance = allowedImbalance;
        else
            this.allowedImbalance = 1;
    }
    /*
     * Inserts the passed element into the root.
     */
    @Override
    public void insert(T element) {
        root = insert(element, root);
    }

    @Override
    public boolean search(T element) {
        return search(element, root);
    }

    @Override
    public int getHeight() {
        return height(root);
    }

    @Override
    public int getSearchSteps() {
        int temp = searchSteps;
        searchSteps = 0;
        return temp;
    }

    @Override
    public int getRotations() {
        return rotations;
    }

    @Override
    public String getTreeType() {
        return "HVL" + allowedImbalance;
    }


    private boolean search(T element, BinarySearchNode<T> node) {
        ++searchSteps;

        //Did not find it  reached a null leaf.
        if (node == null)
            return false;

        int compareResult = element.compareTo(node.element);

        //See if we have found the element.
        if (compareResult == 0)
            return true;
        //See if it is less than current node, go left if so.
        if (compareResult < 0)
            return search(element, node.left);
        //Not less than or equal to, go to right of node.
        return search(element, node.right);
    }

    private BinarySearchNode<T> insert(T element, BinarySearchNode<T> node) {
        if (node == null)
            return new BinarySearchNode<T>(element);

        int compareResult = element.compareTo(node.element);

        if (compareResult < 0)
            node.left = insert(element, node.left);
        else if (compareResult > 0)
            node.right = insert(element, node.right);
        //Otherwise it is a duplicate, do not insert.

        return balance(node);
    }

    private BinarySearchNode<T> balance(BinarySearchNode<T> node) {
        if (node == null)
            return node;

        if (height(node.left) - height(node.right) > allowedImbalance) {
            if (height(node.left.left) >= height(node.left.right)) {
                ++rotations;
                node = rotateWithLeftChild(node);
            } else {
                rotations += 2;
                node = doubleWithLeftChild(node);
            }
        } else if (height(node.right) - height(node.left) > allowedImbalance) {
            if (height(node.right.right) >= height(node.right.left)) {
                ++rotations;
                node = rotateWithRightChild(node);
            } else {
                rotations += 2;
                node = doubleWithRightChild(node);
            }
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        return node;
    }
}
