package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 8:42 PM
 * Interface for all types of trees to implement.
 */
public interface BinarySearchTree<T extends Comparable<T>> {
    //Add the new element to the tree where it belongs.
    public void insert(T element);

    //Search the tree for a given element.
    public boolean search(T element);

    //Get the total height of the tree starting at the root.
    public int getHeight();

    //Get how many steps the search count took. Also reset the current search count.
    public int getSearchSteps();

    //Get how many single rotations have occurred in the tree.
    public int getRotations();

    //Return a human-readable name for the tree.
    public String getTreeType();
}
