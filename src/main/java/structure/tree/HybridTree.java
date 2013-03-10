package structure.tree;

import static structure.tree.BinarySearchTreeUtils.*;

/**
 * Created by: Josh
 * On: 3/2/13 3:54 PM
 */
public class HybridTree<T extends Comparable<T>> extends SplayTree<T> {
    private int allowedImbalance = 1;   //How much imbalance is allowed.

    @Override
    public String getTreeType() {
        return "Hybrid";
    }

    /*
     * Whenever the final splay is performed in the normal splay tree, also go through
     * and balance the tree, as well as do the normal finalSplay.
     */
    @Override
    void finalSplay() {
        super.finalSplay();
        root = balance(root);
    }

    /*
     * Recursively balance the tree by balancing the left, the right, then the root.
     * This makes sure that the imbalances are taken care of when the first appear from the leaves
     * up to the root.
     * Keep track of the number of rotations performed on the tree.
     * Calculate the new height and return the node.
     */
    private BinarySearchNode<T> balance(BinarySearchNode<T> node) {
        //Reached a null, no need to balance.
        if (node == null)
            return node;

        //Balance left, then right node.
        node.left = balance(node.left);
        node.right = balance(node.right);

        //Balance the root node.
        if (height(node.left) - height(node.right) > allowedImbalance) {
            if (height(node.left.left) >= height(node.left.right)) {
                //Left child is too tall. Perform appropriate rotation to correct imbalance.
                ++rotations;
                node = rotateWithLeftChild(node);
            } else {
                rotations += 2;
                node = doubleWithLeftChild(node);
            }
        } else if (height(node.right) - height(node.left) > allowedImbalance) {
            //Right child is too tall. Perform appropriate rotation to correct imbalance.
            if (height(node.right.right) >= height(node.right.left)) {
                ++rotations;
                node = rotateWithRightChild(node);
            } else {
                rotations += 2;
                node = doubleWithRightChild(node);
            }
        }

        //Calculate the height.
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        return node;
    }
}
