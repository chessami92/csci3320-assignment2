package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 8:40 PM
 */
public class BinarySearchNode<T extends Comparable<T>> {
    T element;                  //The data for the node.
    BinarySearchNode<T> left;   //Left child.
    BinarySearchNode<T> right;  //Right child.
    int height;                 //Height of the node.

    /*
     * Constructors - can specify children or not.
     * Defaults to null children (Leaf node).
     */
    BinarySearchNode(T element) {
        this(element, null, null);
    }

    BinarySearchNode(T element, BinarySearchNode<T> left, BinarySearchNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }
}
