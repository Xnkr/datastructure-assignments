package com.cs5343;
/*
 * CS5343.004 - Assignment 4 - Dijkstra's Algorithm to find shortest path
 */
import java.util.*;

public class DijkstraShortestPath {

    public static void main(String[] args) {
        System.out.println(":: Dijkstra's Shortest Path Algorithm ::");
        System.out.println("Graph -");
        List<Vertex> vertices = makeGraph();
        printVertices(vertices);
        System.out.println("\nCalculating shortest path starting with Vertex A");
        Vertex A = vertices.get(0);
        calculateShortestPath(A);
        System.out.println("Shortest Path Tree - ");
        printShortestPathTree(A, vertices);
    }

    private static List<Vertex> makeGraph() {
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");
        Vertex E = new Vertex("E");
        Vertex F = new Vertex("F");
        Vertex G = new Vertex("G");
        Vertex H = new Vertex("H");
        Vertex I = new Vertex("I");
        Vertex J = new Vertex("J");

        A.addAdjacentVertex(B, 9);
        A.addAdjacentVertex(D, 14);
        A.addAdjacentVertex(E, 15);
        B.addAdjacentVertex(C, 24);
        C.addAdjacentVertex(F, 2);
        C.addAdjacentVertex(H, 19);
        C.addAdjacentVertex(I, 9);
        D.addAdjacentVertex(C, 18);
        D.addAdjacentVertex(E, 5);
        D.addAdjacentVertex(J, 2);
        D.addAdjacentVertex(F, 30);
        E.addAdjacentVertex(H, 44);
        E.addAdjacentVertex(F, 20);
        F.addAdjacentVertex(G, 11);
        F.addAdjacentVertex(H, 16);
        G.addAdjacentVertex(C, 6);
        G.addAdjacentVertex(H, 6);
        H.addAdjacentVertex(J, 1);
        I.addAdjacentVertex(H, 17);
        J.addAdjacentVertex(I, 10);

        return new ArrayList<>(Arrays.asList(A, B, C, D, E, F, G, H, I, J));
    }

    private static void printVertices(List<Vertex> vertices) {
        String template = "%s to %s : Distance = %d";
        int edges = 0;
        for (Vertex vertex : vertices) {
            for (Edge edge : vertex.getAdjacencyList()) {
                System.out.println(String.format(template, edge.getStart(), edge.getEnd(), edge.getWeight()));
                edges += 1;
            }
        }
        System.out.println(String.format("Number of Vertices: %d, Number of Edges: %d", vertices.size(), edges));
    }

    private static void calculateShortestPath(Vertex init) {
        init.setDistance(0); //Distance of initial vertex is 0
        init.setParent(null);
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        Set<Vertex> visited = new HashSet<>();
        queue.add(init);
        visited.add(init);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            for (Edge edge : vertex.getAdjacencyList()) {
                Vertex adjacentVertex = edge.getEnd();
                if (!visited.contains(adjacentVertex)) {
                    Integer distance = vertex.getDistance() + edge.getWeight();
                    if (distance < adjacentVertex.getDistance()) {
                        adjacentVertex.setDistance(distance);
                        adjacentVertex.setParent(vertex);
                        queue.add(adjacentVertex);
                    }
                }
            }
            visited.add(vertex);
        }
    }

    private static void printShortestPathTree(Vertex init, List<Vertex> vertices) {
        String template = "%s -> ";
        for (Vertex destVertex : vertices) {
            if (destVertex.equals(init)) {
                // No need to print start vertex
                continue;
            }
            List<Vertex> shortestPath = new ArrayList<>();
            Vertex temp = destVertex;
            while (temp != null) {
                shortestPath.add(temp);
                temp = temp.getParent();
            }
            for (int i = shortestPath.size() - 1; i >= 0; i--) {
                Vertex vertex = shortestPath.get(i);
                if (i != 0) {
                    System.out.print(String.format(template, vertex.toString()));
                } else {
                    System.out.println(String.format("%s Distance: %d", vertex.toString(), destVertex.getDistance()));
                }
            }
        }
    }
}

// Implementation of Vertex
class Vertex implements Comparable<Vertex> {
    private String vertexName;
    // Distance from source vertex, initially infinity
    private Integer distance;
    // To store all edges incident from Vertex
    private List<Edge> adjacencyList;
    // Parent of current vertex in the shortest path tree
    private Vertex parent;

    Vertex(String name) {
        this.vertexName = name;
        this.distance = Integer.MAX_VALUE;
        this.adjacencyList = new ArrayList<>();
    }

    Vertex getParent() {
        return parent;
    }

    void setParent(Vertex parent) {
        this.parent = parent;
    }

    Integer getDistance() {
        return distance;
    }

    void setDistance(Integer distance) {
        this.distance = distance;
    }

    void addAdjacentVertex(Vertex adjacentVertex, Integer weight) {
        this.adjacencyList.add(new Edge(this, adjacentVertex, weight));
    }

    List<Edge> getAdjacencyList() {
        return adjacencyList;
    }

    @Override
    public int compareTo(Vertex o) {
        return this.distance - o.getDistance();
    }

    @Override
    public String toString() {
        return this.vertexName;
    }
}

class Edge {
    private Vertex start;
    private Vertex end;
    private Integer weight;

    Edge(Vertex start, Vertex end, Integer weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    Vertex getStart() {
        return start;
    }

    Vertex getEnd() {
        return end;
    }

    Integer getWeight() {
        return weight;
    }
}
