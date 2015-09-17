/******************************************************************************
 *  Compilation:  javac EulerianPath.java
 *  Execution:    java EulerianPath input.txt
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
 * in an undirected graph with no edge weights
 * The constructor takes time and space proportional to O(E + V), where E is the 
 * number of edges and V the number of vertices.
 * The methods hasEulerianCycle() and cycle() have O(1) time and space 
 * complexity.
 * 
 * @author Nate Liu
 */
public class EulerianPath {
    private Stack<Integer> path = null; // Eulerian path, null if no such cycle
    
    /**
     * The UndirectedEdge class represents an edge in an undirected graph, and
     * has a field specifying if that certain edge is used.
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
     * Computes Eulerian Path on the Graph G
     * 
     * @param G the undirected graph
     */
    public EulerianPath(Graph G) {
        
        // must have at least one edge
        if (G.E() == 0) return;
        
        // necessary condition: number of vertices with odd degrees == 0
        int oddDegCount = 0;
        for (int v = 0; v < G.V(); v++) 
            if (G.degree(v) % 2 != 0) oddDegCount++;
        if (oddDegCount != 0 && oddDegCount != 2) return;
        
        // create adjacency list with the private class UndirectedEdge
        Queue<UndirectedEdge>[] adj = (Queue<UndirectedEdge>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++) {
            // initialize adjacency list for vertex v
            if (adj[v] == null) adj[v] = new Queue<UndirectedEdge>();
            for (int w : G.adj(v)) {
                if (w > v) {
                    if (adj[w] == null) adj[w] = new Queue<UndirectedEdge>();
                    // add edge to adj list of v and w so the adj list of the 
                    // two vertices reference the same edge
                    UndirectedEdge ue = new UndirectedEdge(v, w);
                    adj[v].enqueue(ue);
                    adj[w].enqueue(ue);
                }
                // edge for self loop
                else if (w == v) adj[v].enqueue(new UndirectedEdge(w, w));
            }
        }
        
        // initialize stack with vertex that has an odd degree of edges
        int s = -1;
        for (int v = 0; v < G.V(); v++) {
            if (oddDegCount == 0 && G.degree(v) > 0 || 
                    G.degree(v) % 2 != 0) {
                s = v;
                break;
            }
        }
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        
        // greedily search through edges in iterative DFS style
        path = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
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
        if (path.size() != G.E() + 1)
            path = null;
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
     * Returns the sequence of vertices in the Eulerian path in a 
     * String format.
     * 
     * @return an Eulerian path in String
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
        else System.out.println("No Eulerian Path detected");
        System.out.println(ep1.toString());
    }
}
