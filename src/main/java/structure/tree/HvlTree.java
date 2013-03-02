package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 10:09 PM
 */
public class HvlTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    private int allowedImbalance;   //How much imbalance is allowed.
    private BinarySearchNode<T> root;        //The base of the tree.

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
    public void remove(T element) {
        root = remove(element, root);
    }

    @Override
    public boolean exists(T element) {
        return exists(element, root);
    }

    @Override
    public int getHeight() {
        return root.height;
    }

    @Override
    public String getTreeType() {
        return "HVL" + allowedImbalance;
    }

    @Override
    public String toString() {
        return toString(root, 0, "---");
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
            if (height(node.left.left) >= height(node.left.right))
                node = rotateWithLeftChild(node);
            else
                node = doubleWithLeftChild(node);
        } else if (height(node.right) - height(node.left) > allowedImbalance) {
            if (height(node.right.right) >= height(node.right.left))
                node = rotateWithRightChild(node);
            else
                node = doubleWithRightChild(node);
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        return node;
    }

    private BinarySearchNode<T> remove(T element, BinarySearchNode<T> node) {
        if (node == null)
            return node;

        int compareResult = element.compareTo(node.element);

        if (compareResult < 0)
            node.left = remove(element, node.left);
        else if (compareResult > 0)
            node.right = remove(element, node.right);
        else if (node.left != null && node.right != null) { //Has two children
            node.element = findMin(node.right);
            node.right = remove(node.element, node.right);
        }

        return balance(node);
    }

    private T findMin(BinarySearchNode<T> node) {
        if (node.left == null)
            return node.element;
        else
            return findMin(node.left);
    }
}
