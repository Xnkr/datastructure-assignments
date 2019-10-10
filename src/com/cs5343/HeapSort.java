package com.cs5343;

/*
* CS5343.004 - Assignment 3 - Heapify and Heap sort
* Initial tree:
*                                                                5
                                               /                               \
                               2                                                               6
                       /               \                                                               \
               4                               1                                                               7
           /                               /                                                                       \
       13                               11                                                                               8
         \                               \                                                                           /   \
           14                               12                                                                       9       10
          /                                 /   \                                                                     /         \
         19                                 16   17                                                                   15           18
        /                                                                                                                           \
        20                                                                                                                          90
*
* */

import java.util.*;

public class HeapSort {

    private static int[] tree = new int[]{127,
            5,
            2, 6,
            4, 1, -1, 7,
            13, -1, 11, -1, -1, -1, -1, 8,
            -1, 14, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, 9, 10,
            -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, 16, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 15, -1, -1, 18,
            -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 90
    };

    // Building max heap using floyds algorithm
    private static int[] heapify(int[] tree) {
        int[] heap = makeCompleteTree(tree);
        int lastParent = heap[0] / 2;
        while (lastParent > 0){
            int max = getMaxChild(heap, lastParent);
            compareAndSwap(heap, lastParent, max);
            percolateDown(heap, max);
            lastParent--;
        }
        return heap;
    }

    private static int getMaxChild(int[] heap, int parent) {
        int leftChild = parent * 2;
        int rightChild = leftChild + 1;
        int max = leftChild;
        if (rightChild <= heap[0] && heap[rightChild] > heap[leftChild]){
            max = rightChild;
        }
        return max;
    }

    private static boolean compareAndSwap(int[] heap, int parent, int maxChild) {
        if (heap[parent] < heap[maxChild]){
            int temp = heap[maxChild];
            heap[maxChild] = heap[parent];
            heap[parent] = temp;
            return true;
        }
        return false;
    }

    private static void percolateDown(int[] heap, int idx) {
        int max;
        boolean swap = true;
        while (idx * 2 <= heap[0] && swap){
            max = getMaxChild(heap, idx);
            swap = compareAndSwap(heap, idx, max);
            idx = max;
        }
    }

    private static int[] makeCompleteTree(int[] tree) {
        List<Integer> completeTree = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();
        int i = 1;
        queue.add(i);
        while (!queue.isEmpty()){
            i = queue.remove();
            completeTree.add(tree[i]);
            int leftChild = i * 2;
            int rightChild = leftChild + 1;
            if (leftChild < tree.length && tree[leftChild] != -1){
                queue.add(leftChild);
            }
            if (rightChild < tree.length && tree[rightChild] != -1) {
                queue.add(rightChild);
            }
        }
        completeTree.add(0, completeTree.size());
        int[] heap = new int[completeTree.size()];
        for (int j = 0; j < heap.length; j++) {
            heap[j] = completeTree.get(j);
        }
        return heap;
    }

    public static void main(String[] args) {
        System.out.println("Initial Array: ");
        System.out.println(Arrays.toString(tree));
        int[] maxHeap = heapify(tree);
        System.out.println("Heapified Array: " + verifyHeapProperty(maxHeap));
        System.out.println(Arrays.toString(maxHeap));
        heapSort(maxHeap);
        System.out.println("Heap sorted Array: ");
        System.out.println(Arrays.toString(maxHeap));
    }

    // Checks whether parent is greater than it's children
    private static boolean verifyHeapProperty(int[] maxHeap) {
        int parent = 1;
        while (parent * 2 <= maxHeap[0]){
            int leftChild = parent * 2;
            int rightChild = leftChild + 1;
            if (maxHeap[leftChild] > maxHeap[parent]){
                return false;
            }
            if (rightChild <= maxHeap[0] && maxHeap[rightChild] > maxHeap[parent]){
                return false;
            }
            parent++;
        }
        return true;
    }

    // Sort using Heap sort
    private static void heapSort(int[] maxHeap) {
        while (maxHeap[0] > 0){
            int pop = maxHeap[1]; // Pop the max element
            maxHeap[1] = maxHeap[maxHeap[0]]; // Move last child to root
            maxHeap[maxHeap[0]] = pop; // Add max element to sorted list
            maxHeap[0]--; // Reduce heap size
            percolateDown(maxHeap, 1);
        }
    }
}
