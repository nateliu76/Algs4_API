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
import java.util.HashMap;

/**
 * The EulerianPath class represents a data type for finding the Eulerian path 
 * or cycle in an undirected graph with no edge weights
 * The constructor takes time and space proportional to O(E), where E is the 
 * number of edges.
 * All methods have O(1) time and space complexity
 * 
 * @author Nate Liu
 */
public class EulerianPath {
    
    private Stack<Integer> path = new Stack<Integer>();
    private boolean hasEulerianPath;
    private boolean hasEulerianCycle;
    private Iterator<Integer>[] adj;
    private HashMap<String, Integer> visited;
    
    /**
     * Computes Eulerian Path or Cycle on the Graph G
     * 
     * @param G the undirected graph
     */
    public EulerianPath(Graph G) {
        
        // Because the Graph API uses the adjacency list implementation, 
        // each edge is stored twice. 
        // Ex: once for v -> w and once for w -> v
        // To avoid using the same edge twice, each time an edge v -> w is 
        // used, we increment the count for w -> v so that next time when 
        // we are about to use w -> v, we can see that we need to skip it. 
        // We decrement the count w -> v after it is skipped
        // In other words visited stores the amount of skips for the 
        // certain edge
        // This is implemented with a hash table
        // The key is a string made by appending the vertices using "_" as
        // a seperator
        visited = new HashMap<String, Integer>();
        adj = (Iterator<Integer>[]) new Iterator[G.V()];
        
        // search through edges and find how many have odd degrees
        int oddDegCount = 0;
        int s = 0;
        for (int v = 0; v < G.V(); v++) {
            adj[v] = G.adj(v).iterator();
            if (G.degree(v) % 2 != 0) {
                oddDegCount++;
                // set edge with odd degree as starting point
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
                while (adj[v].hasNext()) {
                    int w = getNextEdge(v);
                    if (w == -1) break;
                    else {
                        stack.push(v);
                        String e = makeKey(w, v);
                        if (!visited.containsKey(e)) visited.put(e, 1);
                        else visited.put(e, visited.get(e) + 1);
                        v = w;
                    }
                }
                // push vertex with no more edges available to path
                path.push(v);
            }
            
            // check if all edges are used
            for (int v = 0; v < G.V(); v++) {
                if (adj[v].hasNext()) {
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
        int w = adj[v].next();
        // skip edges that are used
        String e = makeKey(v, w);
        while (visited.containsKey(e) && visited.get(e) > 0) {
            visited.put(e, visited.get(e) - 1);
            if (!adj[v].hasNext()) return -1;
            else w = adj[v].next();
            e = makeKey(v, w);
        }
        return w;
    }
    
    // makes the key of the hashmap
    private String makeKey(int v, int w) {
        return v + "_" + w;
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
