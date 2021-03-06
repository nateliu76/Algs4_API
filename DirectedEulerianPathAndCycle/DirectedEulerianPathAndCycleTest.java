import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;

import junit.framework.TestCase;

public class DirectedEulerianPathAndCycleTest extends TestCase {
    
    
    // To test Eulerian path and cycle, we need to check:
    // 1. if # of visits == # of edges
    // 2. if an edge exists for all the visits made between vertices
    // 3. check if correctly detected Eulerian Cycle vs Path
    // to test 1 and 2, an easy way to test it would be to make an adj matrix
    // for the undirected graph and the graph generated by following the 
    // eulerian path
    public void testPath() {
        
        // eulerian path test cases
        assertTrue(checkPath("smallPathG1.txt", true, false));
        assertTrue(checkPath("smallPathG2.txt", true, false));
        assertTrue(checkPath("smallPathG3.txt", true, false));
        // eulerian cycle test cases
        assertTrue(checkPath("smallCycleG1.txt", true, true));
        assertTrue(checkPath("smallCycleG2.txt", true, true));
        // non eulerian path test case
        assertTrue(checkPath("pathExtraEdge.txt", false, false));
    }
    
    // makes graph from file and returns Eulerian path
    private boolean checkPath(String filename, boolean hasPath, 
                              boolean hasCycle) {
        In in = new In(filename);
        Digraph G = new Digraph(in);
        in.close();
        
        DirectedEulerianPathAndCycle ep = new DirectedEulerianPathAndCycle(G);
        if (hasPath != ep.isEulerianPath() || hasCycle != ep.isEulerianCycle())
            return false;
        // return if this is a failing case
        if (!hasPath && !hasCycle) return true;
        
        // make adj matrix of G
        int[][] matrix = new int[G.V()][G.V()];
        for (int i = 0; i < G.V(); i++) {
            for (int j : G.adj(i)) {
                matrix[i][j]++;
            }
        }
        
        // deduct the edges in Eulerian path
        int prev = -1;
        for (int v : ep.path()) {
            if (prev == -1) {
                prev = v;
                continue;
            }
            matrix[prev][v]--;
            prev = v;
        }
        
        // check if the edges match
        for (int i = 0; i < G.V(); i++) {
            for (int j = 0; j < G.V(); j++) {
                if (matrix[i][j] != 0) return false;
            }
        }
        
        return true;
    }
}
