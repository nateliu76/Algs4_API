import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.In;

import junit.framework.TestCase;

public class DijkstraUndirectedSPTest extends TestCase {
    
    public void testBoth() {
        assertTrue(testWithFile("tinyEWD.txt"));
        assertTrue(testWithFile("mediumEWD.txt"));
    }
    
    // uses DijkstraSP.java as the solution and compares DijkstraUndirectedSP's
    // values against it
    // an undirected graph is simulated by adding an opposite directed edge for
    // each directed edge already in the directed graph
    private boolean testWithFile(String file) {
        // make directed graph
        In in1 = new In(file);
        EdgeWeightedDigraph G1 = new EdgeWeightedDigraph(in1);
        in1.close();
        // add an extra edge for each edge that exists to simulate undirected
        // graph
        for (DirectedEdge e : G1.edges()) {
            G1.addEdge(new DirectedEdge(e.to(), e.from(), e.weight()));
        }
        
        // make graphs and run dijkstra's SP algorithm for undirected graph
        In in2 = new In(file);
        EdgeWeightedGraph G2 = new EdgeWeightedGraph(in2);        
        in2.close();
        
        // iterate over all vertices as source and compare distances between 
        // DijkstraSP (golden output) and DijkstraUndirectedSP
        for (int s = 0; s < G2.V(); s++) {
            DijkstraSP sp = new DijkstraSP(G1, s);
            DijkstraUndirectedSP usp = new DijkstraUndirectedSP(G2, s);
            // compare all the distances from source to vertex
            for (int v = 0; v < G2.V(); v++) {
                if (sp.distTo(v) != usp.distTo(v)) 
                    return false;
            }
        }
        return true;        
    }
    
}
