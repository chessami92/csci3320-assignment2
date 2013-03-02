package structure.tree;

import org.apache.commons.lang.StringUtils;

/**
 * Created by: Josh
 * On: 2/16/13 8:42 PM
 */
public abstract class BinarySearchTree<T extends Comparable<T>> {
    public abstract void insert(T element);
    public abstract void remove(T element);
    public abstract boolean exists(T element);
    public abstract int getHeight();
    public abstract String getTreeType();

    private int searchCount;        //How many searches it took to find the element.
    private int rotations;          //How many rotations have occurred on the tree.

    public int getSearchCount(){
        int temp = searchCount;
        searchCount = 0;
        return temp;
    }

    public int getRotations(){
        return rotations;
    }

    int height(BinarySearchNode<T> node) {
        return node == null ? -1 : node.height;
    }

    boolean exists(T element, BinarySearchNode<T> node) {
        ++searchCount;

        //Did not find it - reached a null leaf.
        if (node == null)
            return false;

        int compareResult = element.compareTo(node.element);

        //See if we have found the element.
        if (compareResult == 0)
            return true;
        //See if it is less than current node, go left if so.
        if (compareResult < 0)
            return exists(element, node.left);
        //Not less than or equal to, go to right of node.
        return exists(element, node.right);
    }

    String toString(BinarySearchNode<T> node, int prefixTabs, String prefix) {
        StringBuffer printed = new StringBuffer();

        if (node != null) {
            printed.append(toString(node.right, prefixTabs + 1, "/-- "));
            printed.append(StringUtils.repeat("    ", prefixTabs)).append(prefix).append(node.element).append("\n");
            printed.append(toString(node.left, prefixTabs + 1, "\\-- "));
        }

        return printed.toString();
    }

    /*
     * Rotate binary tree node with a left child.
     * Updates heights, then returns the new root.
     */
    BinarySearchNode<T> rotateWithLeftChild(BinarySearchNode<T> k2) {
        ++rotations;

        BinarySearchNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /*
     * Rotate binary tree node with a right child.
     * Updates heights, then returns the new root.
     */
    BinarySearchNode<T> rotateWithRightChild(BinarySearchNode<T> k2) {
        ++rotations;

        BinarySearchNode<T> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(height(k2.right), height(k2.left)) + 1;
        k1.height = Math.max(height(k1.right), k2.height) + 1;
        return k1;
    }

    /*
     * Double rotate binary tree node: first left child with its
     * right child, then node k3 with the new left child.
     * Updates heights, then returns new root.
     */
    BinarySearchNode<T> doubleWithLeftChild(BinarySearchNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /*
     * Double rotate binary tree node: first right child with its
     * left child, then node k3 with the new right child.
     * Updates heights, then returns new root.
     */
    BinarySearchNode<T> doubleWithRightChild(BinarySearchNode<T> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }
}
