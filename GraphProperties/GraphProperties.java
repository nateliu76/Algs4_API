import java.util.HashMap;
import java.util.HashSet;

import edu.princeton.cs.algs4.Graph;

public class GraphProperties {
    // this class should not be instantiated
    private GraphProperties() { }
    
    public static boolean isSubGraph(Graph subG, Graph G) {
        if (subG.V() > G.V() || subG.E() > G.E()) return false;
        
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
    
    public static boolean isSimplePath(Graph G) {
        // find starting point and do DFS
        return true;
    }
    
    public static boolean isSimplePath(Graph G, int s, int t) {
        // start from s and do DFS until t is reached
        HashSet<Integer> visited = new HashSet<Integer>();
        int v = s;
        while (v != t) {
            if (G.degree(v)
        }
        return true;
    }
}
