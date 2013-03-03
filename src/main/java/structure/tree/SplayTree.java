package structure.tree;

import static structure.tree.BinarySearchTreeUtils.*;

/**
 * Created by: Josh
 * On: 3/2/13 10:49 AM
 */
public class SplayTree<T extends Comparable<T>> implements BinarySearchTree<T> {
    private static final int NO_PATH = -1;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int LEFT_LEFT = 2;
    private static final int LEFT_RIGHT = 3;
    private static final int RIGHT_LEFT = 4;
    private static final int RIGHT_RIGHT = 5;
    private int path = NO_PATH;

    BinarySearchNode<T> root;
    private int searchSteps;
    int rotations;

    @Override
    public void insert(T element) {
        root = insert(element, root);
        finalSplay();
    }

    @Override
    public boolean search(T element) {
        try {
            root = search(element, root);
            finalSplay();
            return true;
        } catch (NoSuchFieldError e) {
            return false;
        }
    }

    @Override
    public int getHeight() {
        return height(root);
    }

    @Override
    public int getSearchSteps() {
        int temp = searchSteps;
        searchSteps = 0;
        return temp;
    }

    @Override
    public int getRotations() {
        return rotations;
    }

    @Override
    public String getTreeType() {
        return "Splay";
    }

    private BinarySearchNode<T> insert(T element, BinarySearchNode<T> node) {
        if (node == null)
            return new BinarySearchNode<T>(element);

        int compareResult = element.compareTo(node.element);

        if (compareResult < 0) {
            node.left = insert(element, node.left);
            return updatePathAndSplay(LEFT, node);
        } else if (compareResult > 0) {
            node.right = insert(element, node.right);
            return updatePathAndSplay(RIGHT, node);
        } else { //Otherwise it is a duplicate, do not insert, but splay node
            return node;
        }
    }

    private BinarySearchNode<T> search(T element, BinarySearchNode<T> node) throws NoSuchFieldError {
        ++searchSteps;

        if (node == null)
            throw new NoSuchFieldError("Cannot find " + element);

        int compareResult = element.compareTo(node.element);
        if (compareResult < 0) {
            node.left = search(element, node.left);
            return updatePathAndSplay(LEFT, node);
        } else if (compareResult > 0) {
            node.right = search(element, node.right);
            return updatePathAndSplay(RIGHT, node);
        } else { //Found the node - splay it to the top
            return node;
        }
    }

    /*
     * Updates path with current direction.
     * Once two directions are stored in the current path, it then splays and returns
     * the resulting node (the inserted or searched node).
     */
    private BinarySearchNode<T> updatePathAndSplay(int direction, BinarySearchNode<T> node) {
        //First, update the path variable as necessary.
        switch (path) {
            case NO_PATH:
                path = direction;
                return node;
            case LEFT:
                path = (direction == LEFT) ? LEFT_LEFT : RIGHT_LEFT;
                break;
            case RIGHT:
                path = (direction == LEFT) ? LEFT_RIGHT : RIGHT_RIGHT;
                break;
        }

        //Since the first switch did not return, we have two steps since the node to splay.
        //Splay the node according to the path. Two rotations will always be done in this step.
        rotations += 2;
        switch (path) {
            case LEFT_LEFT:
                path = NO_PATH;
                return zigZigWithLeftChild(node);
            case LEFT_RIGHT:
                path = NO_PATH;
                return doubleWithLeftChild(node);
            case RIGHT_LEFT:
                path = NO_PATH;
                return doubleWithRightChild(node);
            default: //RIGHT_RIGHT
                path = NO_PATH;
                return zigZigWithRightChild(node);
        }
    }

    void finalSplay() {
        //Check if one last rotation is needed to bring the node to the root.
        switch (path) {
            case LEFT:
                ++rotations;
                root = rotateWithLeftChild(root);
                break;
            case RIGHT:
                ++rotations;
                root = rotateWithRightChild(root);
                break;
        }
        path = NO_PATH;
    }
}
