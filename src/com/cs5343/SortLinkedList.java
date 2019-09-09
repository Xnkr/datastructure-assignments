package com.cs5343;

/*
* CS5343.004 - Assignment 1 - Sorting a singly linked list by swapping nodes
*                             in ascending order using Selection Sort
* */

import java.util.Random;

public class SortLinkedList {

    private Node HEAD = null;

    public static void main(String[] args) {
        // Test with descending to ascending
        SortLinkedList sll = new SortLinkedList();
        sll.constructUnsortedLL(20, false);
        System.out.println("Unsorted Linked List: ");
        sll.traverseAndPrint();
        sll.sortLL();
        System.out.println("Sorted Linked List : ");
        sll.traverseAndPrint();

        // Test with random order to ascending
        SortLinkedList sll2 = new SortLinkedList();
        sll2.constructUnsortedLL(20, true);
        System.out.println("Unsorted Linked List: ");
        sll2.traverseAndPrint();
        sll2.sortLL();
        System.out.println("Sorted Linked List : ");
        sll2.traverseAndPrint();
    }

    /*
     * Method to traverse the linked list and print the values
     * */
    public void traverseAndPrint() {
        StringBuilder stringBuilder = new StringBuilder();
        Node tmp = HEAD;
        while (tmp != null) {
            stringBuilder.append(tmp.value).append(" -> ");
            tmp = tmp.next;
        }
        stringBuilder.append("null");
        System.out.println(stringBuilder);
    }

    /*
     * Method to construct unsorted Linked list with n nodes
     * */
    public void constructUnsortedLL(int n, boolean isRandom) {
        Random randGen = new Random(0);
        for (int i = 0; i < n; i++) {
            Node newNode;
            if (isRandom) {
                newNode = new Node(randGen.nextInt(200), null);
            } else {
                newNode = new Node(i, null);
            }
            if (HEAD == null) {
                // First node
                HEAD = newNode;
            } else {
                // Already node exists
                newNode.next = HEAD;
                HEAD = newNode;
            }
        }
    }

    /*
     * Method to sort linked list swapping nodes using selection sort
     * */
    private void sortLL() {
        Node current = HEAD;
        Node previous = null;
        while (current != null) {
            Node smallestNode = current;
            Node prevToSmallest = null;
            Node tmp2 = current.next;
            Node previous2 = current;
            // To find the smallest node
            while (tmp2 != null) {
                if (tmp2.value < smallestNode.value) {
                    smallestNode = tmp2;
                    prevToSmallest = previous2;
                }
                previous2 = tmp2;
                tmp2 = tmp2.next;
            }
            current = swap(current, previous, smallestNode, prevToSmallest);
            previous = current;
            current = current.next;
        }
    }

    /*
     * Method to swap 2 nodes
     * */
    private Node swap(Node current, Node previous, Node smallestNode, Node prevToSmallest) {
        if (!current.equals(smallestNode)) { // Swap only if they are not the same elements
            Node temp = new Node(current);
            current.next = smallestNode.next;
            if (!temp.next.equals(smallestNode)) { // Nodes are not next to each other
                smallestNode.next = temp.next;
                prevToSmallest.next = current;
            } else {
                smallestNode.next = current;
            }
            if (current == HEAD) {
                HEAD = smallestNode;
            } else {
                previous.next = smallestNode;
            }
            return smallestNode;
        }
        return current;
    }

    /*
     * Linked list data structure
     * */
    public class Node {
        private int value;
        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(Node smallestNode) {
            this.value = smallestNode.value;
            this.next = smallestNode.next;
        }
    }

}
