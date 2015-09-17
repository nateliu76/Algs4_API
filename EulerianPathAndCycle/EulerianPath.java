/******************************************************************************
 *  Compilation:  javac EulerianPathAndCycle.java
 *  Execution:    java EulerianPathAndCycle input.txt
 *  Dependencies: Graph.java Stack.java Queue.java
 *
 *  Finds Eulerian Path in an undirected graph
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;


/**
 * The EulerianPath class represents a data type for finding the Eulerian path 
 * or cycle in an undirected graph with no edge weights
 * The constructor takes time and space proportional to O(E + V), where E is the 
 * number of edges and V the number of vertices.
 * All other methods have O(1) time and space complexity
 * 
 * @author Nate Liu
 */
public class EulerianPath {
    private Stack<Integer> path = null;
    
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
    public EulerianPath(Graph G) {
        
        // the adjacency list is reconstructed using the private class 
        // UndirectedEdge to avoid traversing the same edge twice
        Queue<UndirectedEdge>[] adj = (Queue<UndirectedEdge>[]) new Queue[G.V()];
        
        int oddDegCount = 0;
        int s = -1;
        for (int v = 0; v < G.V(); v++) {
            // initialize adjacency list for vertex v
            if (adj[v] == null) adj[v] = new Queue<UndirectedEdge>();
            for (int i : G.adj(v)) {
                if (i > v) {
                    UndirectedEdge ue = new UndirectedEdge(v, i);
                    adj[v].enqueue(ue);
                    // add the same edge to other vertex so reference the two
                    // vertices reference the same edge
                    if (adj[i] == null) adj[i] = new Queue<UndirectedEdge>();
                    adj[i].enqueue(ue);
                }
                else if (i == v) adj[v].enqueue(new UndirectedEdge(i, i));
            }
            
            // set starting point as vertex with odd degree or vertex with
            // nonzero degree if the previous doesn't exist
            if (s == -1 && G.degree(v) != 0) s = v;
            if (G.degree(v) % 2 != 0) {
                oddDegCount++;
                s = v;
            }
        }
        
        if (oddDegCount == 0 || oddDegCount == 2) {
            // DFS
            path = new Stack<Integer>();
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(s);
            while (!stack.isEmpty()) {
                int v = stack.pop();
                // greedily search through edges
                while (!adj[v].isEmpty()) {
                    // find next unused edge of v
                    UndirectedEdge edge = null;
                    while (!adj[v].isEmpty()) {
                        UndirectedEdge nextEdge = adj[v].dequeue();
                        if (!nextEdge.isUsed) {
                            nextEdge.isUsed = true;
                            edge = nextEdge;
                            break;
                        }
                    }
                    if (edge == null) break;
                    stack.push(v);
                    v = edge.other(v);
                }
                // push vertex with no more edges available to path
                path.push(v);
            }
            
            // check if all edges are used
            for (int v = 0; v < G.V(); v++) {
                if (!adj[v].isEmpty()) {
                    path = null;
                    break;
                }
            }
        }
    }
    
    
    /**
     * Returns if the undirected graph has an Eulerian path.
     * 
     * @return true if an Eulerian path exists; false otherwise
     */
    public boolean hasEulerianPath() {
        return path != null;
    }
    
    
    /**
     * Returns the sequence of vertices in the Eulerian path.
     * 
     * @return an Eulerian path
     *         null if no such path
     */
    public Iterable<Integer> path() {
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
        return path.toString();
    }
    
    /**
     * Prints out the Eulerian path if it exists
     */
    public static void main(String[] args) {
        In in1 = new In(args[0]);
        Graph G1 = new Graph(in1);
        EulerianPath ep1 = new EulerianPath(G1);
        if (ep1.hasEulerianPath()) System.out.println("Eulerian Path detected:");
        else System.out.println("No Eulerian Path or Cycle detected");
        System.out.println(ep1.toString());
    }
}
