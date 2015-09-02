import junit.framework.TestCase;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

import edu.princeton.cs.algs4.*;

public class IndexPairingHeapMinPQTest extends TestCase {
    
    public void testInsert() {
        // make sure min is always on top, and size is correct
        IndexPairingHeapMinPQ<Integer> ph = new IndexPairingHeapMinPQ<Integer>(1000);
        int[] r = randomArray(1000);
        int minVal = Integer.MAX_VALUE;
        int size = 0;
        int idx = 0;
        for (int i : r) {
            minVal = Math.min(minVal, i);
            ph.insert(idx, i);
            size++;
            idx++;
            assertTrue(ph.minKey() == minVal);
            assertTrue(ph.size() == size);
        }
    }
    
    public void testDelMin() {
        IndexPairingHeapMinPQ<Integer> ph = new IndexPairingHeapMinPQ<Integer>(10000);
        int[] r = randomArray(10000);
        for (int i = 0; i < 10000; i++) ph.insert(i, r[i]);
        Arrays.sort(r);
        int idx = 0;
        while(!ph.isEmpty()) {
            assertTrue(r[idx] == ph.minKey());
            ph.delMin();
            idx++;
        }
    }
    
    public void testIterator() {
        IndexPairingHeapMinPQ<Integer> ph = new IndexPairingHeapMinPQ<Integer>(10000);
        int[] r = randomArray(10000);
        for (int i = 0; i < 10000; i++) ph.insert(i, r[i]);
        Arrays.sort(r);
        int idx = 0;
        for (int i : ph) {
            assertTrue(r[idx] == ph.keyOf(i));
            idx++;
        }
    }
    
    // delete one element, iterate over remaining and make sure we don't 
    // find the same one again, while making sure all others are intact
    // and in order
    public void testDelete() {
        int size = 10000;
        IndexPairingHeapMinPQ<Integer> ph = new IndexPairingHeapMinPQ<Integer>(size);
        int[] r = randomArray(size);
        for (int i = 0; i < size; i++) ph.insert(i, r[i]);
        
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i : ph) al.add(i);
        Random rnd = new Random();
        HashSet<Integer> hs = new HashSet<Integer>();
        
        // randomly delete a few keys and check ph each time
        for (int x = 0; x < 30; x++) {
            // generate random number
            int rn = Math.abs(rnd.nextInt()) % size;
            while (hs.contains(rn)) rn = Math.abs(rnd.nextInt()) % size;
            hs.add(rn);
            ph.delete(rn);
            
            // check if element is deleted and if invariant is maintained
            // (keys in ascending order)
            int prev = Integer.MIN_VALUE;
            Iterator<Integer> iter = ph.iterator();
            for (int i = 0; i < size; i++) {
                if (hs.contains(al.get(i))) continue;
                int j = iter.next();
                assertTrue(ph.keyOf(al.get(i)).equals(ph.keyOf(j)));
                assertTrue(prev <= ph.keyOf(j));
                prev = ph.keyOf(j);
            }
        }
    }
    
    public void testIncreaseKey() {
        int size = 10000;
        IndexPairingHeapMinPQ<Integer> ph = new IndexPairingHeapMinPQ<Integer>(size);
        int[] r = randomArray(size);
        for (int i = 0; i < size; i++) ph.insert(i, r[i]);
        // randomly increase some keys and check
        // 1. if keys are correctly increased
        // 2. if order of min heap is still preserved
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int idx = Math.abs(rnd.nextInt()) % size;
            int oldVal = ph.keyOf(idx);
            int increase = Math.abs(rnd.nextInt()) % 10000;
            ph.increaseKey(idx, ph.keyOf(idx) + increase);
            assertTrue(ph.keyOf(idx) == oldVal + increase);
        }
        
        int prev = Integer.MIN_VALUE;
        for (int i : ph) {
            assertTrue(prev <= ph.keyOf(i));
            prev = ph.keyOf(i);
        }
    }
    
    public void testDecreaseKey() {
        int size = 10000;
        IndexPairingHeapMinPQ<Integer> ph = new IndexPairingHeapMinPQ<Integer>(size);
        int[] r = randomArray(size);
        for (int i = 0; i < size; i++) ph.insert(i, r[i]);
        // randomly decrease a few keys and check if
        // 1. keys are correctly changed
        // 2. order of min heap is preserved
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int idx = Math.abs(rnd.nextInt()) % size;
            int oldVal = ph.keyOf(idx);
            int decr = Math.abs(rnd.nextInt()) % 10000;
            ph.decreaseKey(idx, ph.keyOf(idx) - decr);
            assertTrue(ph.keyOf(idx) == oldVal - decr);
        }
        
        int prev = Integer.MIN_VALUE;
        for (int i : ph) {
            assertTrue(prev <= ph.keyOf(i));
            prev = ph.keyOf(i);
        }
    }
    
    // test Dijkstra's SP algorithm with index pairing heap as a integration test
    public void testDijkstra() {
        assertTrue(testSPWithFile("tinyEWD.txt"));
        assertTrue(testSPWithFile("mediumEWD.txt"));
    }
    
    public void testMST() {
        assertTrue(testMSTWithFile("tinyEWG.txt"));
        assertTrue(testMSTWithFile("mediumEWG.txt"));
    }
    
    private boolean testSPWithFile(String file) {
        // make directed graph
        In in1 = new In(file);
        EdgeWeightedDigraph G1 = new EdgeWeightedDigraph(in1);
        in1.close();
        
        // iterate over all vertices as source and compare distances between 
        // DijkstraSP (golden output) and DijkstraUndirectedSP
        for (int s = 0; s < G1.V(); s++) {
            DijkstraSP sp = new DijkstraSP(G1, s);
            DijkstraSPwPairingHeap spPH = new DijkstraSPwPairingHeap(G1, s);
            // compare all the distances from source to vertex
            for (int v = 0; v < G1.V(); v++) {
                if (sp.distTo(v) != spPH.distTo(v)) 
                    return false;
            }
        }
        return true;        
    }
    
    private boolean testMSTWithFile(String file) {
        // make graph
        In in1 = new In(file);
        EdgeWeightedGraph G1 = new EdgeWeightedGraph(in1);
        in1.close();
        
        // compare MST's, should be same
        PrimMST mst = new PrimMST(G1);
        PrimMSTwPairingHeap mstPH = new PrimMSTwPairingHeap(G1);
        
        if (mst.weight() != mstPH.weight()) return false;
        
        // edges should be the same
        ArrayList<Edge> al = new ArrayList<Edge>();
        for (Edge e : mst.edges()) al.add(e);
        int idx = 0;
        for (Edge e1 : mst.edges()) {
            Edge e2 = al.get(idx);
            if (e1.either() != e2.either()) return false;
            if (e1.other(e1.either()) != e2.other(e2.either())) return false;
            if (e1.weight() != e2.weight()) return false;
            idx++;
        }
        return true;        
    }
    
    // make random arrays and validate testing
    private int[] randomArray(int size) {
        int[] rand = new int[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i++) 
            rand[i] = rnd.nextInt() % 100000;
        return rand;
    }
}
