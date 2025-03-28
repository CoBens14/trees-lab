package edu.grinnell.csc207.trees;

import java.util.List;
import java.util.ArrayList;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() { }

    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in the tree
     */
    public int size() {
        return sizeH(root);
    }

    private Node<T> insertH(T value, Node<T> root) {
        if (root == null) {
            return new Node<T>(value);
        } else {
            if (value.compareTo(root.value) < 0) {
                root.left = insertH(value, root.left);
            } else {
                root.right = insertH(value, root.right);
            }
            return root;
        }
    }

    /**
     * @param value the value to add to the tree
     */
    public void insert(T value) {
        root = insertH(value, root);
    }

    ///// Part 1: Traversals
    /// 

    public void inOrderH(Node<T> cur, List<T> list) {
        if (cur != null) {
            inOrderH(cur.left, list);
            list.add(cur.value);
            inOrderH(cur.right, list);
        }
    }
    /**
     * 
     * 
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> list = new ArrayList<>();
        inOrderH(root, list);
        return list;
    }

    public void preOrderH(Node<T> cur, List<T> list) {
        if (cur != null) {
            list.add(cur.value);
            preOrderH(cur.left, list);
            preOrderH(cur.right, list);
        }
    }

    /**
     * Look at cur, left tree, right tree
     * 
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        List<T> list = new ArrayList<>();
        preOrderH(root, list);
        return list;
    }

    public void postOrderH(Node<T> cur, List<T> list) {
        if (cur != null) {
            postOrderH(cur.left, list);
            postOrderH(cur.right, list);
            list.add(cur.value);
        }
    }

    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> list = new ArrayList<>();
        postOrderH(root, list);
        return list;
    }

    ///// Part 2: Contains


    public boolean containsH(Node<T> cur, T value) {
        boolean b;
        if (cur == null) {
            b = false;
        }   else {
            if(cur.value.compareTo(value) == 0) {
               b = true;
            } else {
            b = containsH(cur.left, value) || containsH(cur.right, value);
        }
        }
        return b;
    }

    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        return containsH(root, value);
    }

    ///// Part 3: Pretty Printing

    /**
     * @return a string representation of the tree obtained via an pre-order traversal in the
     *         form: "[v0, v1, ..., vn]"
     */
    public String toStringPreorder() {
        List<T> list = toListPreorder();
        StringBuffer buf = new StringBuffer("[");
        buf.append(list.get(0));

        for(int i = 1; i < list.size(); i++ ) {
            buf.append(", ");
            buf.append(list.get(i));
        }

        buf.append("]");
        return buf.toString();
    }

    ///// Part 4: Deletion
  
    /*
     * The three cases of deletion are:
     * 1. left null
     * 2. right null
     * 3. neither null
     */

     public Node<T> deleteH(Node<T> cur, T value) {
        Node<T> nodeT = null;
        if (cur.left == null && cur.right == null) {
            nodeT = null;
        } else {
            if (cur.left != null) {
                nodeT = deleteH(cur.left, value);
            }
            if (nodeT == null && cur.right != null) {
                nodeT = deleteH(cur.right, value);
            }
        }
        return nodeT;
    }

    public void appendRight(Node<T> valueNode) {

    }

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code> found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        if (root != null) {
            Node<T> valueNode = deleteH(root, value);
            s
        }
    }
}
