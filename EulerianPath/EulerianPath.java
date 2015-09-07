/******************************************************************************
 *  Compilation:  javac EulerianPath.java
 *  Execution:    java EulerianPath input.txt
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
 * All methods have O(1) time and space complexity
 * 
 * @author Nate Liu
 */
public class EulerianPath {
    
    private Stack<Integer> path = new Stack<Integer>();
    private boolean hasEulerianPath;
    private boolean hasEulerianCycle;
    private ArrayList<UndirectedEdge>[] adj;
    private Iterator<UndirectedEdge>[] adjIter;
    
    private class UndirectedEdge {
        private int v1;
        private int v2;
        private boolean isUsed;
        
        public UndirectedEdge(int a, int b) {
            v1 = a;
            v2 = b;
            isUsed = false;
        }
        
        public int other(int w) {
            if (w == v1) return v2;
            else if (w == v2) return v1;
            else return -1;
        }
    }
    
    /**
     * Computes Eulerian Path or Cycle on the Graph G
     * 
     * @param G the undirected graph
     */
    public EulerianPath(Graph G) {
        
        adj = (ArrayList<UndirectedEdge>[]) new ArrayList[G.V()];
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
                    adj[i].add(ue);
                }
                else if (i == v) adj[v].add(new UndirectedEdge(i, i));
            }
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
            hasEulerianPath = false;
            hasEulerianCycle = false;
        } 
        else {
            hasEulerianCycle = oddDegCount == 0;
            hasEulerianPath = true;
            
            // DFS
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(s);
            while (!stack.isEmpty()) {
                int v = stack.pop();
                // greedily search through edges
                while (adjIter[v].hasNext()) {
                    int w = getNextEdge(v);
                    if (w == -1) break;
                    else {
                        stack.push(v);
                        v = w;
                    }
                }
                // push vertex with no more edges available to path
                path.push(v);
            }
            
            // check if all edges are used
            for (int v = 0; v < G.V(); v++) {
                if (adjIter[v].hasNext()) {
                    hasEulerianPath = false;
                    hasEulerianCycle = false;
                    break;
                }
            }
        }
    }
    
    
    // searches and returns next unused edge
    // if no valid edges are present, return -1
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
     * Returns if the undirected graph has an Eulerian path
     * 
     * @return true if an Eulerian path exists
     */
    public boolean hasEulerianPath() {
        return hasEulerianPath;
    }
    
    
    /**
     * Returns if the undirected graph has an Eulerian cycle
     * 
     * @return true if an Eulerian cycle exists
     */
    public boolean hasEulerianCycle() {
        return hasEulerianCycle;
    }
    
    
    /**
     * Returns the sequence of vertices in the Eulerian path/cycle
     * 
     * @return an Eulerian path/cycle
     *         null if no such path
     */
    public Iterable<Integer> path() {
        if (!hasEulerianPath) return null;
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
        if (!hasEulerianPath) return "";
        return path.toString();
    }
    
    /**
     * Prints out the Eulerian path/cycle if it exists
     */
    public static void main(String[] args) {
        In in1 = new In(args[0]);
        Graph G1 = new Graph(in1);
        EulerianPath ep1 = new EulerianPath(G1);
        if (ep1.hasEulerianCycle) System.out.println("Eulerian Cycle detected:");
        else if (ep1.hasEulerianPath) System.out.println("Eulerian Path detected:");
        else System.out.println("No Eulerian Path or Cycle detected");
        System.out.println(ep1.toString());
    }
}
