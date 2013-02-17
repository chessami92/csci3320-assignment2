package structure.tree;

/**
 * Created by: Josh
 * On: 2/16/13 8:42 PM
 */
public interface Tree<T extends Comparable<T>> {
    void insert(T element);
    void remove(T element);
}
