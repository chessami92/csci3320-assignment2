package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 10:09 PM
 */
public class HvlTree<T extends Comparable<T>> {
    private int allowedImbalance;   //How much imbalance is allowed.
    private HvlNode<T> root;        //The base of the tree.

    /*
     * Constructor - specify the allowed imbalance.
     */
    HvlTree(int allowedImbalance) {
        if (allowedImbalance > 0)
            this.allowedImbalance = allowedImbalance;
        else
            this.allowedImbalance = 1;
    }

    private HvlNode<T> insert(T element, HvlNode<T> node) {
        if (node == null)
            return new HvlNode<T>(element);

        int compareResult = element.compareTo(node.element);

        if (compareResult < 0)
            node.left = insert(element, node.left);
        else if (compareResult > 0)
            node.right = insert(element, node.right);
        //Otherwise it is a duplicate, do not insert.

        return balance(node);
    }

    private HvlNode<T> balance(HvlNode<T> node) {
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

        node.setHeight(Math.max(height(node.left), height(node.right)) + 1);

        return node;
    }

    private HvlNode<T> remove(T element, HvlNode<T> node) {
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

    private int height(HvlNode<T> node) {
        return node == null ? -1 : node.getHeight();
    }

    private T findMin(HvlNode<T> node) {
        if (node.left == null)
            return node.element;
        else
            return findMin(node.left);
    }

    /*
     * Rotate binary tree node with a left child.
     * Updates heights, then returns the new root.
     */
    private HvlNode<T> rotateWithLeftChild(HvlNode<T> k2) {
        HvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.setHeight(Math.max(height(k2.left), height(k2.right)) + 1);
        k1.setHeight(Math.max(height(k1.left), k2.getHeight()) + 1);
        return k1;
    }

    /*
     * Rotate binary tree node with a right child.
     * Updates heights, then returns the new root.
     */
    private HvlNode<T> rotateWithRightChild(HvlNode<T> k2) {
        HvlNode<T> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.setHeight(Math.max(height(k2.right), height(k2.left)) + 1);
        k1.setHeight(Math.max(height(k1.right), k2.getHeight()) + 1);
        return k1;
    }

    /*
     * Double rotate binary tree node: first left child with its
     * right child, then node k3 with the new left child.
     * Updates heights, then returns new root.
     */
    private HvlNode<T> doubleWithLeftChild(HvlNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /*
     * Double rotate binary tree node: first right child with its
     * left child, then node k3 with the new right child.
     * Updates heights, then returns new root.
     */
    private HvlNode<T> doubleWithRightChild(HvlNode<T> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

}
