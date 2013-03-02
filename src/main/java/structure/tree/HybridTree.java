package structure.tree;

/**
 * Created by: Josh
 * On: 3/2/13 3:54 PM
 */
public class HybridTree<T extends Comparable<T>> extends SplayTree<T> {
    private int allowedImbalance = 1;

    @Override
    public String getTreeType() {
        return "Hybrid";
    }

    @Override
    void finalSplay() {
        super.finalSplay();
        root = balance(root);
    }

    private BinarySearchNode<T> balance(BinarySearchNode<T> node) {
        if (node == null)
            return node;

        node.left = balance(node.left);
        node.right = balance(node.right);

        if (height(node.left) - height(node.right) > allowedImbalance) {
            if (height(node.left.left) >= height(node.left.right))
                node = rotateWithLeftChild(node);
            else
                node = doubleWithLeftChild(node);
        } else if (height(node.right) - height(node.left) > allowedImbalance) {
            if (height(node.right.right) >= height(node.right.left))
                node = rotateWithRightChild(node);
            else
                node = doubleWithRightChild(node);
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        return node;
    }
}
