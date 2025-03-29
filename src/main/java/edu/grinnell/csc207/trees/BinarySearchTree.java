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
         * @param left  the left child of the node
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
    public BinarySearchTree() {
    }

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

    /**
     * Recursive helper function for returning values in order
     * 
     * @param cur the current node
     * @param list the list adding node values to
     */
    public void inOrderH(Node<T> cur, List<T> list) {
        if (cur != null) {
            inOrderH(cur.left, list);
            list.add(cur.value);
            inOrderH(cur.right, list);
        }
    }

    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> list = new ArrayList<>();
        inOrderH(root, list);
        return list;
    }

    /**
     * @param cur the current node
     * @param list the list adding values to 
     */
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

    /**
     * @param cur the current node
     * @param list the list adding values to 
     */
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

    /**
     * @param cur the current node
     * @param value the value being searched for
     * @return true if contains value, false otherwise
     */
    public boolean containsH(Node<T> cur, T value) {
        boolean b;
        if (cur == null) {
            b = false;
        } else {
            if (cur.value.compareTo(value) == 0) {
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
     * @return a string representation of the tree obtained via an pre-order
     *         traversal in the
     *         form: "[v0, v1, ..., vn]"
     */
    public String toStringPreorder() {
        List<T> list = toListPreorder();
        StringBuffer buf = new StringBuffer("[");
        buf.append(list.get(0));

        for (int i = 1; i < list.size(); i++) {
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

     /**
      * Finds the parent of the value indicated
      *
      * @param cur the current node
      * @param value the value being searched for
      * @return returns the parent node of node containing value or null if value not found
      */
    public Node<T> findValue(Node<T> cur, T value) {
        Node<T> nodeT = null;
        if (cur.left == null && cur.right == null) {
            nodeT = null;
        } else if ((cur.left != null && cur.left.value.compareTo(value) == 0) 
            || (cur.right != null && cur.right.value.compareTo(value) == 0)) {
            nodeT = cur;
        } else {
            if (cur.value.compareTo(value) > 0) {
                nodeT = findValue(cur.left, value);
            } else {
                nodeT = findValue(cur.right, value);
            }
        }
        return nodeT;
    }

    /**
     * Returns the parent of furthest right node
     * 
     * @param node the root of the tree
     * @return the parent of far right node
     */
    private Node<T> findFarRightParent(Node<T> node) {
        if (node.right.right == null) {
            return node;
        } else {
            return findFarRightParent(node.right);
        }
    }

    /**
     * Finds the parent of the furthest left node
     * 
     * @param node the root of tree
     * @return parent of far left node
     */
    private Node<T> findFarLeftParent(Node<T> node) {
        if (node.left.left == null) {
            return node;
        } else {
            return findFarLeftParent(node.left);
        }
    }

    /**
     * Replaces root being deleted 
     */
    private void rootReplacement() {
        if (root.left == null) {
            root = root.right; 
        } else if (root.right == null) {
            root = root.left;
        } else {
            Node<T> replaceRootParent = findFarLeftParent(root.right);
            System.out.println(replaceRootParent.value);
            replaceRootParent.left.right = root.right;
            replaceRootParent.left.left = root.left;
            root = replaceRootParent.left;
            replaceRootParent.left = null;
        }
    }

    /**
     * @param node the node being searched
     * @param dir 0 being left, 1 being right, 2 meaning root is being replaced
     * @return the type of deletion that will need completed
     */
    private int findDeleteReplacement(Node<T> node, int dir) {
        if (dir == 0) {
            if (node.left == null) {
                return 0;
            } else if (node.right == null) {
                return 1;
            } else if (node.left.right == null) {
                return 2;
            } else {
                return 3;
            }
        } else {
            if (node.left == null) {
                return 0;
            } else if (node.right == null) {
                return 1;
            } else if (node.right.left == null) {
                node = node.right;
                return 2;
            } else {
                node = node.left;
                return 3;
            }
        }
    }

    /**
     * Removes the value from tree
     * 
     * @param parentNode the parentNode of value
     * @param value the value being removed
     */
    private void remove(Node<T> parentNode, T value) {

        Node<T> newRootNodeParent;
        Node<T> removeNode;

        if (parentNode.right != null && parentNode.right.value.compareTo(value) == 0) {
            newRootNodeParent = parentNode.right;
            removeNode = parentNode.right;
            int typeOfNewRoot = findDeleteReplacement(newRootNodeParent, 0);

            if (typeOfNewRoot == 0) {
                parentNode.right = removeNode.left;
            } else if (typeOfNewRoot == 1) {
                parentNode.right = removeNode.right;
            } else if (typeOfNewRoot == 2) {
                parentNode.right = newRootNodeParent.left;
                parentNode.right.right = removeNode.right;
                newRootNodeParent.left = null;
            } else {
                newRootNodeParent = findFarRightParent(newRootNodeParent.left);
                parentNode.right = newRootNodeParent.right;
                parentNode.right.left = removeNode.left;
                parentNode.right.right = removeNode.right;
                newRootNodeParent.right = null;
            }
        } else {
            newRootNodeParent = parentNode.left;
            removeNode = parentNode.left;
            int typeOfNewRoot = findDeleteReplacement(newRootNodeParent, 1);
            if (typeOfNewRoot == 0) {
                parentNode.left = removeNode.right;
            } else if (typeOfNewRoot == 1) {
                parentNode.left = removeNode.left;
            } else if (typeOfNewRoot == 2) {
                parentNode.left = newRootNodeParent.right;
                parentNode.left.left = removeNode.left;
                newRootNodeParent.right = null;
            } else {
                newRootNodeParent = findFarLeftParent(newRootNodeParent.left);
                parentNode.right = newRootNodeParent;
                newRootNodeParent.left = removeNode.left;
                newRootNodeParent.right = removeNode.right;
                newRootNodeParent.left = null;
            }
        }
    }

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code>
     * found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        if (root != null) {
            if (root.value.compareTo(value) != 0) {
                Node<T> parentNode = findValue(root, value);
                if (parentNode != null) {
                    remove(parentNode, value);
                }
            } else {
                rootReplacement();
            }
        }
    } 
}
