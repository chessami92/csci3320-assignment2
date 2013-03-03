package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 8:42 PM
 */
public interface BinarySearchTree<T extends Comparable<T>> {
    public void insert(T elelment);

    public boolean search(T element);

    public int getHeight();

    public int getSearchSteps();

    public int getRotations();

    public String getTreeType();
}
