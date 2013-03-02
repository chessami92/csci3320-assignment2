package structure.tree;

/**
 * Created by: Josh
 * On: 3/2/13 10:49 AM
 */
public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    private static final int NO_PATH = -1;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int LEFT_LEFT = 2;
    private static final int LEFT_RIGHT = 3;
    private static final int RIGHT_LEFT = 4;
    private static final int RIGHT_RIGHT = 5;
    private int path = NO_PATH;

    @Override
    public String getTreeType() {
        return "Splay";
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

    public void insert(T element) {
        root = insert(element, root);
        finalSplay();
    }

    BinarySearchNode<T> insert(T element, BinarySearchNode<T> node) {
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

    BinarySearchNode<T> search(T element, BinarySearchNode<T> node) throws NoSuchFieldError {
        ++searchCount;

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
        //Splay the node according to the path.
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

    private void finalSplay() {
        //Check if one last rotation is needed to bring the node to the root.
        switch (path) {
            case LEFT:
                root = rotateWithLeftChild(root);
                break;
            case RIGHT:
                root = rotateWithRightChild(root);
                break;
        }
        path = NO_PATH;
    }

    private BinarySearchNode<T> zigZigWithLeftChild(BinarySearchNode<T> g) {
        rotations += 2;

        BinarySearchNode<T> p = g.left;
        BinarySearchNode<T> x = p.left;

        g.left = p.right;
        p.left = x.right;
        p.right = g;
        x.right = p;

        g.height = Math.max(height(g.right), height(g.left)) + 1;
        p.height = Math.max(height(p.right), height(p.left)) + 1;
        x.height = Math.max(height(x.right), height(x.left)) + 1;

        return x;
    }

    private BinarySearchNode<T> zigZigWithRightChild(BinarySearchNode<T> g) {
        rotations += 2;

        BinarySearchNode<T> p = g.right;
        BinarySearchNode<T> x = p.right;

        g.right = p.left;
        p.right = x.left;
        p.left = g;
        x.left = p;

        g.height = Math.max(height(g.right), height(g.left)) + 1;
        p.height = Math.max(height(p.right), height(p.left)) + 1;
        x.height = Math.max(height(x.right), height(x.left)) + 1;

        return x;
    }
}
