package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 8:40 PM
 */
public class HvlNode<T extends Comparable<T>> implements Tree {
    T element;              //The data for the node.
    HvlNode<T> left;                //Left child.
    HvlNode<T> right;               //Right child.
    private int height;             //Height of the tree

    /*
     * Constructors - can specify children or not.
     * Defaults to null children (Leaf node).
     */
    HvlNode(T element) {
        this(element, null, null);
    }

    HvlNode(T element, HvlNode<T> left, HvlNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height){
        if(height > 0)
            this.height = height;
        else
            this.height = 0;
    }
}
