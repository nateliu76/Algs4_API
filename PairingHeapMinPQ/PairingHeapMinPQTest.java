import junit.framework.TestCase;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class PairingHeapMinPQTest extends TestCase {
    
    public void testInsert() {
        // make sure min is always on top, and size is correct
        PairingHeapMinPQ<Integer> ph = new PairingHeapMinPQ<Integer>();
        int[] r = randomArray(1000);
        int minVal = Integer.MAX_VALUE;
        int size = 0;
        for (int i : r) {
            minVal = Math.min(minVal, i);
            ph.insert(i);
            size++;
            assertTrue(ph.min() == minVal);
            assertTrue(ph.size() == size);
        }
    }
    
    public void testMeld() {
        PairingHeapMinPQ<Integer> ph1 = new PairingHeapMinPQ<Integer>();
        PairingHeapMinPQ<Integer> ph2 = new PairingHeapMinPQ<Integer>();
        int[] r1 = randomArray(1000);
        int[] r2 = randomArray(100);
        for (int i : r1) ph1.insert(i);
        for (int i : r2) ph2.insert(i);
        int minVal = Math.min(ph1.min(), ph2.min());
        ph1.meld(ph2);
        assertTrue(ph1.size() == 1100);
        assertTrue(ph2.size() == 0);
        assertTrue(ph1.min() == minVal);
    }
    
    public void testDelMin() {
        PairingHeapMinPQ<Integer> ph = new PairingHeapMinPQ<Integer>();
        int[] r = randomArray(10000);
        for (int i : r) ph.insert(i);
        Arrays.sort(r);
        int idx = 0;
        while(!ph.isEmpty()) {
            assertTrue(r[idx] == ph.delMin());
            idx++;
        }
    }
    
    public void testIterator() {
        PairingHeapMinPQ<Integer> ph = new PairingHeapMinPQ<Integer>();
        int[] r = randomArray(10000);
        for (int i : r) ph.insert(i);
        Arrays.sort(r);
        int idx = 0;
        for (int i : ph) {
            assertTrue(r[idx] == i);
            idx++;
        }
    }
    
    public void testComparator() {
        PairingHeapMinPQ<Integer> ph = 
            new PairingHeapMinPQ<Integer>(new reverseComp());
        int[] r = randomArray(10000);
        for (int i : r) ph.insert(i);
        Arrays.sort(r);
        int idx = r.length - 1;
        for (int i : ph) {
            assertTrue(r[idx] == i);
            idx--;
        }
    }
    
    public void testToString() {
        PairingHeapMinPQ<Integer> ph = new PairingHeapMinPQ<Integer>();
        ph.insert(5);
        ph.insert(10);
        ph.insert(7);
        ph.insert(4);
        ph.insert(9);
        ph.insert(3);
        assertTrue(ph.toString().equals("3 4 9 5 7 10 "));
    }
    
    public void testSpecialCases() {
        // case where array is sorted, get tree with height == 1
        PairingHeapMinPQ<Integer> ph = new PairingHeapMinPQ<Integer>();
        int[] r = randomArray(10000);
        Arrays.sort(r);
        for (int i : r) ph.insert(i);
        int idx = 0;
        for (int i : ph) {
            assertTrue(r[idx] == i);
            idx++;
        }
        
        // case where array is sorted reversely, get vertical tree
        // aka tree with height == n
        ph.clear();
        for (int i = r.length - 1; i >= 0; i--) ph.insert(r[i]);
        idx = 0;
        for (int i : ph) {
            assertTrue(r[idx] == i);
            idx++;
        }
    }
    
    // reverse order, doesn't take care of overflow though
    private class reverseComp implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return b - a;
        }
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
