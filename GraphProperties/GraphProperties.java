import java.util.HashMap;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.NonrecursiveDFS;
import edu.princeton.cs.algs4.Queue;

public class GraphProperties {
    // this class should not be instantiated
    private GraphProperties() { }
    
    // check if subG is a subgraph of G
    public static boolean isSubgraph(Graph subG, Graph G) {
        if (subG.V() > G.V() || subG.E() > G.E()) return false;
        
        // adj list stored as an array of hash maps
        HashMap<Integer, Integer>[] edgeCount = new HashMap[G.V()];
        // add all edges to array
        for (int v = 0; v < G.V(); v++) {
            if (subG.degree(v) > G.degree(v)) return false;
            edgeCount[v] = new HashMap<Integer, Integer>();
            for (int w : G.adj(v)) {
                if (edgeCount[v].containsKey(w)) 
                    edgeCount[v].put(w, edgeCount[v].get(w) + 1);
                else
                    edgeCount[v].put(w, 1);
            }
        }
        
        // deduct all edges of P from edge count
        for (int v = 0; v < subG.V(); v++) {
            for (int w : subG.adj(v)) {
                if (edgeCount[v].containsKey(w)) {
                    int remain = edgeCount[v].get(w);
                    if (remain > 0) edgeCount[v].put(w, remain - 1);
                    else return false;
                }
                else return false;
            }
        }
        return true;
    }
    
    // check if G is a simple path
    public static boolean isSimplePath(Graph G) {
        // find starting point and do DFS
        int start = -1;
        int end = -1;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) == 1) {
                if (start == -1) start = v;
                else if (end == -1) end = v;
                else return false;
            }
            else if (G.degree(v) == 0 || G.degree(v) == 2) continue;
            else return false;
        }
        if (start == -1 || end == -1) return false;
        
        // do DFS and check if all vertices are connected to start vertex
        NonrecursiveDFS dfs = new NonrecursiveDFS(G, start);
        for (int v = 0; v < G.V(); v++) {
            if (!dfs.marked(v) && G.degree(v) != 0) return false;
        }
        return true;
    }
    
    // check if path represented by an Iterable is a subgraph of G
    public static boolean isPathSubgraph(Graph G, Iterable<Integer> path) {
        Queue<Integer> q = new Queue<Integer>();
        int maxV = 0;
        // find max vertex value to construct graph
        for (int v : path) {
            maxV = Math.max(maxV, v);
            q.enqueue(v);
        }
            
        // construct graph using iterable
        Graph P = new Graph(maxV);
        int prevV = -1;
        for (int v : q) {
            if (prevV != -1) P.addEdge(prevV, v);
            prevV = v;
        }
        
        // run is subgraph
        return isSubgraph(P, G);
    }
    
    // check if path represented by an Iterable is a subgraph of G
    public static boolean isCycleSubgraph(Graph G, Iterable<Integer> cycle) {
        Queue<Integer> q = new Queue<Integer>();
        int maxV = 0;
        int start = -1;
        // find max vertex value to construct graph
        for (int v : cycle) {
            if (start == -1) start = v;
            maxV = Math.max(maxV, v);
            q.enqueue(v);
        }
        
        // check if last vertex is same as first vertex
        if (start != q.peek()) return false;
        
        // construct graph using iterable
        Graph P = new Graph(maxV);
        int prevV = -1;
        for (int v : q) {
            if (prevV != -1) P.addEdge(prevV, v);
            prevV = v;
        }
        
        // run is subgraph
        return isSubgraph(P, G);
    }
}
