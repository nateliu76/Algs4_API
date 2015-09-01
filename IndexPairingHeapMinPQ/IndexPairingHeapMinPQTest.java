import junit.framework.TestCase;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

//import edu.princeton.cs.algs4.Out;

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
    
    public void testIncreaseKey() {}
    
    public void testDecreaseKey() {}
    
    // test Dijkstra's SP algorithm with index pairing heap as a integration test
    public void testDijkstra() {}
    
    // make random arrays and validate testing
    private int[] randomArray(int size) {
        int[] rand = new int[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i++) 
            rand[i] = rnd.nextInt() % 100000;
        return rand;
    }
}
