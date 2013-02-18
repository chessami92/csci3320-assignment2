package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 8:42 PM
 */
public interface Tree<T extends Comparable<T>> {
    public void insert(T element);
    public void remove(T element);
    public boolean exists(T element);
    public int getHeight();
    public String getTreeType();
    public void clearTree();
    public int getSearchCount();
    public int getRotations();
}
