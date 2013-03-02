package structure.tree;

import org.apache.commons.lang.StringUtils;

/**
 * Created by: Josh
 * On: 2/16/13 8:42 PM
 */
public abstract class BinarySearchTree<T extends Comparable<T>> {
    BinarySearchNode<T> root;        //The base of the tree.
    int searchCount;        //How many searches it took to find the element.
    int rotations;          //How many rotations have occurred on the tree.

    public abstract boolean search(T element);

    public abstract String getTreeType();

    abstract BinarySearchNode<T> insert(T element, BinarySearchNode<T> node);

    public int getHeight() {
        return height(root);
    }

    /*
     * Inserts the passed element into the root.
     */
    public void insert(T element) {
        root = insert(element, root);
    }

    public int getSearchCount() {
        int temp = searchCount;
        searchCount = 0;
        return temp;
    }

    public int getRotations() {
        return rotations;
    }

    public String toString() {
        return toString(root, 0, "---");
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

    int height(BinarySearchNode<T> node) {
        return node == null ? -1 : node.height;
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
