package structure.tree;

import org.apache.commons.lang.StringUtils;

/**
 * Created by: Josh
 * On: 3/2/13 4:11 PM
 */
public class BinarySearchTreeUtils {

    public static void main(String[] args) {
        BinarySearchNode<Integer> root = new BinarySearchNode<Integer>(1);
        root.left = new BinarySearchNode<Integer>(0);
        root.right = new BinarySearchNode<Integer>(2);
        System.out.println(toString(root, 0, "-- "));

        root = rotateWithLeftChild(root);
        System.out.println(toString(root, 0, "-- "));
    }

    public static int height(BinarySearchNode node) {
        return node == null ? -1 : node.height;
    }

    public static String toString(BinarySearchNode node, int prefixTabs, String prefix) {
        StringBuffer printed = new StringBuffer();

        if (node != null) {
            printed.append(toString(node.right, prefixTabs + 1, "/-- "));
            printed.append(StringUtils.repeat("    ", prefixTabs)).append(prefix).append(node.element).append("\n");
            printed.append(toString(node.left, prefixTabs + 1, "\\-- "));
        }

        return printed.toString();
    }

    /*
     * Rotate binary tree node with a left child.
     * Updates heights, then returns the new root.
    */
    public static BinarySearchNode rotateWithLeftChild(BinarySearchNode k2) {
        BinarySearchNode k1 = k2.left;

        k2.left = k1.right;
        k1.right = k2;

        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;

        return k1;
    }

    /*
     * Rotate binary tree node with a right child.
     * Updates heights, then returns the new root.
    */
    public static BinarySearchNode rotateWithRightChild(BinarySearchNode k2) {
        BinarySearchNode k1 = k2.right;

        k2.right = k1.left;
        k1.left = k2;

        k2.height = Math.max(height(k2.right), height(k2.left)) + 1;
        k1.height = Math.max(height(k1.right), k2.height) + 1;

        return k1;
    }

    /*
     * Double rotate binary tree node: first left child with its
     * right child, then node k3 with the new left child.
     * Updates heights, then returns new root.
    */
    public static BinarySearchNode doubleWithLeftChild(BinarySearchNode k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /*
     * Double rotate binary tree node: first right child with its
     * left child, then node k3 with the new right child.
     * Updates heights, then returns new root.
    */
    public static BinarySearchNode doubleWithRightChild(BinarySearchNode k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    public static BinarySearchNode zigZigWithLeftChild(BinarySearchNode g) {
        BinarySearchNode p = g.left;
        BinarySearchNode x = p.left;

        g.left = p.right;
        p.left = x.right;
        p.right = g;
        x.right = p;

        g.height = Math.max(height(g.right), height(g.left)) + 1;
        p.height = Math.max(height(p.right), height(p.left)) + 1;
        x.height = Math.max(height(x.right), height(x.left)) + 1;

        return x;
    }

    public static BinarySearchNode zigZigWithRightChild(BinarySearchNode g) {
        BinarySearchNode p = g.right;
        BinarySearchNode x = p.right;

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
