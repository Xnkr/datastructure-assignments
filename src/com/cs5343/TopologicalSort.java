package com.cs5343;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/*
 * CS5343.004 - Assignment 5 - Topological sort
 */
public class TopologicalSort {

    private static Stack<GraphVertex> topologicalOrdering = new Stack<>();

    private static List<GraphVertex> makeGraph(boolean isCyclic) {
        GraphVertex A = new GraphVertex("A");
        GraphVertex B = new GraphVertex("B");
        GraphVertex C = new GraphVertex("C");
        GraphVertex D = new GraphVertex("D");
        GraphVertex E = new GraphVertex("E");
        GraphVertex F = new GraphVertex("F");
        GraphVertex G = new GraphVertex("G");
        GraphVertex H = new GraphVertex("H");
        GraphVertex I = new GraphVertex("I");
        GraphVertex J = new GraphVertex("J");
        GraphVertex K = new GraphVertex("K");

        A.addAdjacentVertex(B);
        A.addAdjacentVertex(F);
        B.addAdjacentVertex(C);
        B.addAdjacentVertex(D);
        B.addAdjacentVertex(F);
        C.addAdjacentVertex(E);
        E.addAdjacentVertex(D);
        F.addAdjacentVertex(G);
        G.addAdjacentVertex(I);
        H.addAdjacentVertex(D);
        H.addAdjacentVertex(I);
        I.addAdjacentVertex(D);
        I.addAdjacentVertex(K);
        J.addAdjacentVertex(H);
        if (!isCyclic){
            J.addAdjacentVertex(K);
        } else {
            K.addAdjacentVertex(J);
        }

        return new ArrayList<>(Arrays.asList(A, B, C, D, E, F, G, H, I, J, K));
    }

    private static void printVertices(List<GraphVertex> vertices) {
        int edges = 0;
        System.out.println("Adjacency Lists: ");
        for (GraphVertex vertex : vertices) {
            System.out.println(vertex + " -> " + vertex.getAdjacencyListAsStr());
            edges += vertex.getAdjacencyList().size();
        }
        System.out.println(String.format("Number of Vertices: %d, Number of Edges: %d", vertices.size(), edges));
    }

    private static void DFSTopoSort(GraphVertex u) throws CyclicGraphException {
        u.setVisited();
        for (GraphEdge edge: u.getAdjacencyList()){
            GraphVertex v = edge.getEnd();
            if (v.isVisited() && !v.isDone()){
                throw new CyclicGraphException(u, v);
            }
            if (!v.isVisited()) {
                try {
                    DFSTopoSort(v);
                } catch (CyclicGraphException e) {
                    e.addCycleVertex(u);
                    throw e;
                }
            }
        }
        u.setDone();
        topologicalOrdering.push(u);
    }

    public static void main(String[] args) {
        System.out.println("== Topological Sorting: DFS ==");
        System.out.println("Directed Acyclic Graph -");
        List<GraphVertex> dag = makeGraph(false);
        printVertices(dag);
        topologicalSort(dag);
        System.out.println();
        System.out.println("Directed Cyclic Graph -");
        List<GraphVertex> dcg = makeGraph(true);
        printVertices(dcg);
        topologicalSort(dcg);
    }

    private static void topologicalSort(List<GraphVertex> dag) {
        topologicalOrdering.clear();
        try {
            for(GraphVertex vertex: dag){
                if (!vertex.isVisited()) {
                    DFSTopoSort(vertex);
                }
            }
        } catch (CyclicGraphException e) {
            System.out.print("Cycle detected - ");
            System.out.println(e.getCycle());
            System.out.print("Partial ");
        }
        System.out.println("Topological Ordering - ");
        while(!topologicalOrdering.empty()){
            System.out.print(topologicalOrdering.pop() + " ");
        }
        System.out.println();
    }
}

// Implementation of Vertex
class GraphVertex {
    private String vertexName;
    private List<GraphEdge> adjacencyList;
    private boolean visited;
    private boolean done;

    GraphVertex(String name) {
        this.vertexName = name;
        this.adjacencyList = new ArrayList<>();
        this.visited = false;
        this.done = false;
    }

    void addAdjacentVertex(GraphVertex adjacentVertex) {
        this.adjacencyList.add(new GraphEdge(this, adjacentVertex));
    }

    List<GraphEdge> getAdjacencyList() {
        return adjacencyList;
    }

    List<String> getAdjacencyListAsStr() {
        List<String> adjacencyList = new ArrayList<>();
        for (GraphEdge edge: this.adjacencyList) {
            adjacencyList.add(edge.getEnd().toString());
        }
        return adjacencyList;
    }

    @Override
    public String toString() {
        return this.vertexName;
    }

    boolean isVisited() {
        return visited;
    }

    void setVisited() {
        this.visited = true;
    }

    boolean isDone() {
        return done;
    }

    void setDone() {
        this.done = true;
    }

}

// Implementation of Edge
class GraphEdge {
    private GraphVertex start;
    private GraphVertex end;

    GraphEdge(GraphVertex start, GraphVertex end) {
        this.start = start;
        this.end = end;
    }

    GraphVertex getEnd() {
        return end;
    }
}

class CyclicGraphException extends Exception {
    private Stack<GraphVertex> verticesInCycle;

    CyclicGraphException(GraphVertex u, GraphVertex v){
        this.verticesInCycle = new Stack<>();
        this.verticesInCycle.push(v);
        this.verticesInCycle.push(u);
    }

    void addCycleVertex(GraphVertex vertex){
        this.verticesInCycle.push(vertex);
    }

    String getCycle() {
        StringBuilder cycle = new StringBuilder();
        while(!this.verticesInCycle.empty()){
            cycle.append(this.verticesInCycle.pop()).append(" -> ");
        }
        return cycle.toString();
    }
}