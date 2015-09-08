/******************************************************************************
 *  Compilation:  javac EulerianPathAndCycle.java
 *  Execution:    java EulerianPathAndCycle input.txt
 *  Dependencies: Graph.java Stack.java Edge.java
 *
 *  Finds Eulerian Path or Cycle in an undirected graph
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;

import java.util.Iterator;
import java.util.ArrayList;

/**
 * The EulerianPath class represents a data type for finding the Eulerian path 
 * or cycle in an undirected graph with no edge weights
 * The constructor takes time and space proportional to O(E + V), where E is the 
 * number of edges and V the number of vertices.
 * All other methods have O(1) time and space complexity
 * 
 * @author Nate Liu
 */
public class EulerianPathAndCycle {
    
    // constructs an adjacency list using the custom private class 
    // UndirectedEdge for finding Eulerian path/cycle with the Graph API
    private ArrayList<UndirectedEdge>[] adj;
    private Iterator<UndirectedEdge>[] adjIter;
    
    private Stack<Integer> path = new Stack<Integer>();
    private boolean isEulerianPath;
    private boolean isEulerianCycle;
    
    /**
     * The UndirectedEdge class represents an edge in an undirected graph, and
     * has a field specifying if that certain edge is used
     */
    private class UndirectedEdge {
        private int v1;
        private int v2;
        private boolean isUsed;
        
        public UndirectedEdge(int a, int b) {
            v1 = a;
            v2 = b;
            isUsed = false;
        }
        
        // returns other vertex
        public int other(int w) {
            if (w == v1) return v2;
            else if (w == v2) return v1;
            else return -1;
        }
    }
    
    /**
     * Computes Eulerian Path and/or Cycle on the Graph G
     * 
     * @param G the undirected graph
     */
    public EulerianPathAndCycle(Graph G) {
        
        // the adjacency list is reconstructed using the private class 
        // UndirectedEdge to avoid traversing the same edge twice
        adj = (ArrayList<UndirectedEdge>[]) new ArrayList[G.V()];
        
        // once the adjacency list is constructed, we will use iterators to
        // iterate over the edges
        adjIter = (Iterator<UndirectedEdge>[]) new Iterator[G.V()];
        
        int oddDegCount = 0;
        int s = -1;
        for (int v = 0; v < G.V(); v++) {
            // initialize adjacency list for vertex v
            if (adj[v] == null) adj[v] = new ArrayList<UndirectedEdge>();
            for (int i : G.adj(v)) {
                if (i > v) {
                    UndirectedEdge ue = new UndirectedEdge(v, i);
                    adj[v].add(ue);
                    if (adj[i] == null) adj[i] = new ArrayList<UndirectedEdge>();
                    // add the same edge to other vertex so reference the two
                    // vertices reference the same edge
                    adj[i].add(ue);
                }
                else if (i == v) adj[v].add(new UndirectedEdge(i, i));
            }
            // convert to iterator form for traversal convenience
            adjIter[v] = adj[v].iterator();
            
            // set starting point as vertex with odd degree or vertex with
            // nonzero degree if the previous doesn't exist
            if (s == -1 && G.degree(v) != 0) s = v;
            if (G.degree(v) % 2 != 0) {
                oddDegCount++;
                s = v;
            }
        }
        
        if (oddDegCount != 0 && oddDegCount != 2) {
            isEulerianPath = false;
            isEulerianCycle = false;
        } 
        else {
            isEulerianCycle = oddDegCount == 0;
            isEulerianPath = true;
            
            // DFS
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(s);
            while (!stack.isEmpty()) {
                int v = stack.pop();
                // greedily search through edges
                while (adjIter[v].hasNext()) {
                    int w = getNextEdge(v);
                    if (w == -1) break;
                    stack.push(v);
                    v = w;
                }
                // push vertex with no more edges available to path
                path.push(v);
            }
            
            // check if all edges are used
            for (int v = 0; v < G.V(); v++) {
                if (adjIter[v].hasNext()) {
                    isEulerianPath = false;
                    isEulerianCycle = false;
                    break;
                }
            }
        }
    }
    
    
    /**
     * Returns vertex that is connected to current vertex v via v's next
     * unused edge.
     * If all edges are used or nonexistant, return -1
     */
    private int getNextEdge(int v) {
        int w = -1;
        // skip edges that are used
        while (adjIter[v].hasNext() && w == -1) {
            UndirectedEdge ue = adjIter[v].next();
            if (!ue.isUsed) {
                ue.isUsed = true;
                w = ue.other(v);
            }
        }
        return w;
    }
    
    /**
     * Returns if the undirected graph has an Eulerian path.
     * 
     * @return true if an Eulerian path exists; false otherwise
     */
    public boolean isEulerianPath() {
        return isEulerianPath;
    }
    
    
    /**
     * Returns if the undirected graph has an Eulerian cycle.
     * 
     * @return true if an Eulerian cycle exists; false otherwise
     */
    public boolean isEulerianCycle() {
        return isEulerianCycle;
    }
    
    
    /**
     * Returns the sequence of vertices in the Eulerian path.
     * 
     * @return an Eulerian path
     *         null if no such path
     */
    public Iterable<Integer> path() {
        if (!isEulerianPath) return null;
        return path;
    }
    
    /**
     * Returns the sequence of vertices in the Eulerian cycle.
     * 
     * @return an Eulerian cycle
     *         null if no such cycle
     */
    public Iterable<Integer> cycle() {
        if (!isEulerianCycle) return null;
        return path;
    }
    
    
    /**
     * Returns the sequence of vertices in the Eulerian path/cycle in a 
     * String format
     * 
     * @return an Eulerian path/cycle in String
     *         null String if no such path
     */
    public String toString() {
        if (!isEulerianPath) return "";
        return path.toString();
    }
    
    /**
     * Prints out the Eulerian path/cycle if it exists
     */
    public static void main(String[] args) {
        In in1 = new In(args[0]);
        Graph G1 = new Graph(in1);
        EulerianPathAndCycle ep1 = new EulerianPathAndCycle(G1);
        if (ep1.isEulerianCycle) System.out.println("Eulerian Cycle detected:");
        else if (ep1.isEulerianPath) System.out.println("Eulerian Path detected:");
        else System.out.println("No Eulerian Path or Cycle detected");
        System.out.println(ep1.toString());
    }
}
