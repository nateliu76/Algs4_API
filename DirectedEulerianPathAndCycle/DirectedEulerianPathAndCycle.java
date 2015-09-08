/******************************************************************************
  * Compilation:  javac DirectedEulerianPath.java
  * Execution:    java DirectedEulerianPath V E
  * Dependencies: Digraph.java Stack.java StdOut.java StdRandom.java
  *
  * Finds an Eulerian path in a digraph, if one exists.
  * Runs in O(E + V) time.
  *
  ******************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * The DirectedEulerianPath class represents a data type for finding
 * the Eulerian path or cycle in a digraph.
 * The constructor runs in O(E + V) time, and takes O(E) space, where
 * E is the number of edges and V the number of vertices
 * 
 * All other methods have O(1) space and time complexity
 * 
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Nate Liu
 */
public class DirectedEulerianPathAndCycle {
    private Stack<Integer> path = new Stack<Integer>();
    private boolean isEulerianPath = true;
    private boolean isEulerianCycle = true;
    
    /**
     * Computes Eulerian Path or Cycle on the Digraph G
     * 
     * @param G the digraph
     */
    public DirectedEulerianPathAndCycle(Digraph G) {
        
        // need out degree of vertices to find starting point
        // use G.reverse() to obtain out degrees
        Digraph Grev = G.reverse();
        
        // create local view of adjacency lists
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        int unevenDegCount = 0;
        int s = -1;
        for (int v = 0; v < G.V(); v++) {
            adj[v] = G.adj(v).iterator();
            // choose vertex with higher out degree than in degree as
            // source, if such vertex doesn't exist, choose vertex 
            // with nonzero outdegree as source
            if (s == -1 && adj[v].hasNext()) s = v;
            if (G.outdegree(v) != Grev.outdegree(v)) {
                unevenDegCount++;
                if (G.outdegree(v) > Grev.outdegree(v)) 
                    s = v;
            }
        }
        
        if (unevenDegCount != 0 && unevenDegCount != 2) {
            isEulerianPath = false;
            isEulerianCycle = false;
        }
        else {
            isEulerianPath = true;
            isEulerianCycle = unevenDegCount == 0;
            
            // greedily add to cycle, depth-first search style
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(s);
            while (!stack.isEmpty()) {
                int v = stack.pop();
                while (adj[v].hasNext()) {
                    stack.push(v);
                    v = adj[v].next();
                }
                // push vertex with no more available edges to path
                path.push(v);
            }
            
            // check if all edges have been used
            for (int v = 0; v < G.V(); v++) {
                if (adj[v].hasNext()) {
                    isEulerianPath = false;
                    isEulerianCycle = false;
                    break;
                }
            }
        }
    }
    
    /**
     * Returns the sequence of vertices in the Eulerian path
     * 
     * @return an Eulerian path
     *         null if no such path
     */
    public Iterable<Integer> path() {
        if (!isEulerianPath) return null;
        return path;
    }
    
    /**
     * Returns the sequence of vertices in the Eulerian cycle
     * 
     * @return an Eulerian cycle
     *         null if no such path
     */
    public Iterable<Integer> cycle() {
        if (!isEulerianCycle) return null;
        return path;
    }
    
    /**
     * Returns if the digraph has an Eulerian path
     * 
     * @return true if an Eulerian path exists
     */
    public boolean isEulerianPath() {
        return isEulerianPath;
    }
    
    /**
     * Returns if the digraph has an Eulerian cycle
     * 
     * @return true if an Eulerian cycle exists
     */
    public boolean isEulerianCycle() {
        return isEulerianCycle;
    }
    
    // graph generator that tries to generate Eulerian Cycle
    public static void main(String[] args) {
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        
        // random graph of V vertices and approximately E edges
        // with indegree[v] = outdegree[v] for all vertices
        Digraph G = new Digraph(V);
        int[] indegree  = new int[V];
        int[] outdegree = new int[V];
        int deficit = 0;
        for (int i = 0; i < E - deficit/2; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            // if (v == w) { i--; continue; }
            G.addEdge(v, w);
            if (outdegree[v] >= indegree[v]) deficit++;
            else                             deficit--;
            outdegree[v]++;
            if (indegree[w] >= outdegree[w]) deficit++;
            else                             deficit--;
            indegree[w]++;
        }
        
        while (deficit > 0) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            if (v == w) continue;
            if (outdegree[v] >= indegree[v])  continue;
            if (indegree[w]  >= outdegree[w]) continue;
            G.addEdge(v, w);
            if (outdegree[v] >= indegree[v]) deficit++;
            else                             deficit--;
            outdegree[v]++;
            if (indegree[w] >= outdegree[w]) deficit++;
            else                             deficit--;
            indegree[w]++;
        }
        
        StdOut.println(G);
        DirectedEulerianPathAndCycle euler = new DirectedEulerianPathAndCycle(G);
        if (euler.isEulerianPath()) {
            for (int v : euler.path()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("Not eulerian");
        }
    }
}
