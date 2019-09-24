package com.cs5343;

/*
 * CS5343.004 - Assignment 2 - Creating BST and Deleting node using predecessor
 * */

public class BST {

    private BinarySearchTree ROOT = null;

    public static void main(String[] args) {
        BST bst = new BST();
        bst.createTree();
        System.out.println("Inorder traversal of BST before deletion: ");
        inorderTraversal(bst.ROOT);
        System.out.println();
        bst.deleteNodeWithValue(bst.ROOT, 100);
        System.out.println("Inorder traversal of BST after deletion: ");
        inorderTraversal(bst.ROOT);
        System.out.println();
    }

    // Deleting node using predecessor
    private BinarySearchTree deleteNodeWithValue(BinarySearchTree tree, int valueToBeDeleted) {
        if (tree == null) {
            return null;
        } else {
            if (valueToBeDeleted < tree.value){
                tree.left = deleteNodeWithValue(tree.left, valueToBeDeleted);
            } else if (valueToBeDeleted > tree.value){
                tree.right = deleteNodeWithValue(tree.right, valueToBeDeleted);
            } else {
                // Value found
                if (tree.left == null && tree.right == null){
                    // Case 1: Deleting leaf node
                    return null;
                } else if (tree.left == null || tree.right == null) {
                    // Case 2: Has one child
                    return tree.left == null ? tree.right : tree.left;
                } else {
                    // Case 3: Both children are present. Replace with predecessor value
                    BinarySearchTree predecessor = findPredecessor(tree.left);
                    tree.value = predecessor.value;
                    // Delete the predecessor node
                    tree.left = deleteNodeWithValue(tree.left, predecessor.value
                    );
                }
            }
            return tree;
        }
    }

    private BinarySearchTree findPredecessor(BinarySearchTree tree) {
        if (tree.right == null){
            return tree;
        }
        else {
            return findPredecessor(tree.right);
        }
    }

    private void insertNode(BinarySearchTree tree, int value){
        if(value <= tree.value) {
            if (tree.left == null) {
                tree.left = new BinarySearchTree(value, null, null, tree);
                return;
            } else {
                insertNode(tree.left, value);
            }
        } else {
            if (tree.right == null){
                tree.right = new BinarySearchTree(value, null, null, tree);
                return;
            } else {
                insertNode(tree.right, value);
            }
        }
    }

    private void createTree() {
        int[] values = new int[]
                {100, 50, 200, 150, 300, 25, 75, 12, 37, 125, 175, 250, 320, 67, 87, 94, 89, 92, 88};
        for(int v: values){
            if (ROOT == null){
                // Inserting root node
                ROOT = new BinarySearchTree(v, null, null, null);
            } else {
                insertNode(ROOT, v);
            }
        }
    }

    private static void inorderTraversal(BinarySearchTree tree){
        if (tree == null) return;
        inorderTraversal(tree.left);
        System.out.print(tree.value + " ");
        inorderTraversal(tree.right);
    }

    public class BinarySearchTree {
        private int value;
        private BinarySearchTree left;
        private BinarySearchTree right;

        public BinarySearchTree(int value, BinarySearchTree left, BinarySearchTree right, BinarySearchTree parent) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
