import junit.framework.TestCase;
import java.util.Comparator;

public class BinarySearchTest extends TestCase {
    
    int[] a1 = {10, 11};
    int[] a2 = {10};
    int[] a3 = {10, 10, 11, 13, 18, 23, 23, 48, 50, 54, 68, 77, 77, 77, 84, 98,
        98, 99};
    
    Integer[] A1 = convert(a1);
    Integer[] A2 = convert(a2);
    Integer[] A3 = convert(a3);
    Comparator<Integer> comp = new IntOrder();
    
    private class IntOrder implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return Integer.compare(a, b);
        }
    }
    
    private Integer[] convert(int[] a) {
        Integer[] A = new Integer[a.length];
        int idx = 0;
        for (int i : a) A[idx++] = Integer.valueOf(i);
        return A;
    }
    
    public void testIndexOf() {
        assertEquals(BinarySearch.indexOf(A1, 10, comp), 0);
        assertEquals(BinarySearch.indexOf(A1, 11, comp), 1);
        assertEquals(BinarySearch.indexOf(A1, 12, comp), -1);
        assertEquals(BinarySearch.indexOf(A1, 4, comp), -1);
        
        assertEquals(BinarySearch.indexOf(A2, 10, comp), 0);
        assertEquals(BinarySearch.indexOf(A2, 12, comp), -1);
        assertEquals(BinarySearch.indexOf(A2, 4, comp), -1);
        
        int n10 = BinarySearch.indexOf(A3, 10, comp);
        assertTrue(n10 >= 0 && n10 <= 1);
        assertEquals(BinarySearch.indexOf(A3, 12, comp), -1);
        assertEquals(BinarySearch.indexOf(A3, 4, comp), -1);
        
        int n77 = BinarySearch.indexOf(A3, 77, comp);
        assertTrue(n77 >= 11 && n77 <= 13);
        
        assertEquals(BinarySearch.indexOf(A1, 10), 0);
        assertEquals(BinarySearch.indexOf(A1, 11), 1);
        assertEquals(BinarySearch.indexOf(A1, 12), -1);
        assertEquals(BinarySearch.indexOf(A1, 4), -1);
        
        assertEquals(BinarySearch.indexOf(A2, 10), 0);
        assertEquals(BinarySearch.indexOf(A2, 12), -1);
        assertEquals(BinarySearch.indexOf(A2, 4), -1);
        
        int n10_1 = BinarySearch.indexOf(A3, 10);
        assertTrue(n10_1 >= 0 && n10_1 <= 1);
        assertEquals(BinarySearch.indexOf(A3, 12), -1);
        assertEquals(BinarySearch.indexOf(A3, 4), -1);
        
        int n77_1 = BinarySearch.indexOf(A3, 77);
        assertTrue(n77_1 >= 11 && n77_1 <= 13);
        
    }
    
    public void testFirstIndexOf() {
        assertEquals(BinarySearch.firstIndexOf(A1, 10, comp), 0);
        assertEquals(BinarySearch.firstIndexOf(A1, 11, comp), 1);
        assertEquals(BinarySearch.firstIndexOf(A1, 12, comp), -3);
        assertEquals(BinarySearch.firstIndexOf(A1, 4, comp), -1);
        
        assertEquals(BinarySearch.firstIndexOf(A2, 10, comp), 0);
        assertEquals(BinarySearch.firstIndexOf(A2, 12, comp), -2);
        assertEquals(BinarySearch.firstIndexOf(A2, 4, comp), -1);
        
        assertEquals(BinarySearch.firstIndexOf(A3, 10, comp), 0);
        assertEquals(BinarySearch.firstIndexOf(A3, 77, comp), 11);
        assertEquals(BinarySearch.firstIndexOf(A3, 99, comp), 17);
        
        assertEquals(BinarySearch.firstIndexOf(A1, 10), 0);
        assertEquals(BinarySearch.firstIndexOf(A1, 11), 1);
        assertEquals(BinarySearch.firstIndexOf(A1, 12), -3);
        assertEquals(BinarySearch.firstIndexOf(A1, 4), -1);
        
        assertEquals(BinarySearch.firstIndexOf(A2, 10), 0);
        assertEquals(BinarySearch.firstIndexOf(A2, 12), -2);
        assertEquals(BinarySearch.firstIndexOf(A2, 4), -1);
        
        assertEquals(BinarySearch.firstIndexOf(A3, 10), 0);
        assertEquals(BinarySearch.firstIndexOf(A3, 77), 11);
        assertEquals(BinarySearch.firstIndexOf(A3, 99), 17);
    }
    
    public void testLastIndexOf() {
        assertEquals(BinarySearch.lastIndexOf(A1, 10, comp), 0);
        assertEquals(BinarySearch.lastIndexOf(A1, 11, comp), 1);
        assertEquals(BinarySearch.lastIndexOf(A1, 12, comp), -3);
        assertEquals(BinarySearch.lastIndexOf(A1, 4, comp), -1);
        
        assertEquals(BinarySearch.lastIndexOf(A2, 10, comp), 0);
        assertEquals(BinarySearch.lastIndexOf(A2, 12, comp), -2);
        assertEquals(BinarySearch.lastIndexOf(A2, 4, comp), -1);
        
        assertEquals(BinarySearch.lastIndexOf(A3, 10, comp), 1);
        assertEquals(BinarySearch.lastIndexOf(A3, 77, comp), 13);
        assertEquals(BinarySearch.lastIndexOf(A3, 99, comp), 17);
        
        assertEquals(BinarySearch.lastIndexOf(A1, 10), 0);
        assertEquals(BinarySearch.lastIndexOf(A1, 11), 1);
        assertEquals(BinarySearch.lastIndexOf(A1, 12), -3);
        assertEquals(BinarySearch.lastIndexOf(A1, 4), -1);
        
        assertEquals(BinarySearch.lastIndexOf(A2, 10), 0);
        assertEquals(BinarySearch.lastIndexOf(A2, 12), -2);
        assertEquals(BinarySearch.lastIndexOf(A2, 4), -1);
        
        assertEquals(BinarySearch.lastIndexOf(A3, 10), 1);
        assertEquals(BinarySearch.lastIndexOf(A3, 77), 13);
        assertEquals(BinarySearch.lastIndexOf(A3, 99), 17);
    }
    
    public void testFloor() {
        assertEquals(BinarySearch.floor(A1, 10, comp), 0);
        assertEquals(BinarySearch.floor(A1, 11, comp), 1);
        assertEquals(BinarySearch.floor(A1, 12, comp), 1);
        assertEquals(BinarySearch.floor(A1, 9, comp), -1);
        
        assertEquals(BinarySearch.floor(A2, 10, comp), 0);
        assertEquals(BinarySearch.floor(A2, 9, comp), -1);
        assertEquals(BinarySearch.floor(A2, 12, comp), 0);
        
        assertEquals(BinarySearch.floor(A3, 80, comp), 13);
        
        assertEquals(BinarySearch.floor(A1, 10), 0);
        assertEquals(BinarySearch.floor(A1, 11), 1);
        assertEquals(BinarySearch.floor(A1, 12), 1);
        assertEquals(BinarySearch.floor(A1, 9), -1);
        
        assertEquals(BinarySearch.floor(A2, 10), 0);
        assertEquals(BinarySearch.floor(A2, 9), -1);
        assertEquals(BinarySearch.floor(A2, 12), 0);
        
        assertEquals(BinarySearch.floor(A3, 80), 13);
    }

    public void testCeil() {
        assertEquals(BinarySearch.ceiling(A1, 10, comp), 0);
        assertEquals(BinarySearch.ceiling(A1, 11, comp), 1);
        assertEquals(BinarySearch.ceiling(A1, 12, comp), -1);
        assertEquals(BinarySearch.ceiling(A1, 9, comp), 0);
        
        assertEquals(BinarySearch.ceiling(A2, 10, comp), 0);
        assertEquals(BinarySearch.ceiling(A2, 9, comp), 0);
        assertEquals(BinarySearch.ceiling(A2, 12, comp), -1);
        
        assertEquals(BinarySearch.ceiling(A3, 20, comp), 5);
        
        assertEquals(BinarySearch.ceiling(A1, 10), 0);
        assertEquals(BinarySearch.ceiling(A1, 11), 1);
        assertEquals(BinarySearch.ceiling(A1, 12), -1);
        assertEquals(BinarySearch.ceiling(A1, 9), 0);
        
        assertEquals(BinarySearch.ceiling(A2, 10), 0);
        assertEquals(BinarySearch.ceiling(A2, 9), 0);
        assertEquals(BinarySearch.ceiling(A2, 12), -1);
        
        assertEquals(BinarySearch.ceiling(A3, 20), 5);
    }
    
    public void testPred() {
        assertEquals(BinarySearch.predecessor(A1, 10, comp), -1);
        assertEquals(BinarySearch.predecessor(A1, 11, comp), 0);
        assertEquals(BinarySearch.predecessor(A1, 12, comp), 1);
        assertEquals(BinarySearch.predecessor(A1, 9, comp), -1);
        
        assertEquals(BinarySearch.predecessor(A3, 10, comp), -1);
        assertEquals(BinarySearch.predecessor(A3, 40, comp), 6);
        assertEquals(BinarySearch.predecessor(A3, 100, comp), 17);
        assertEquals(BinarySearch.predecessor(A3, 99, comp), 16);
        
        assertEquals(BinarySearch.predecessor(A1, 10), -1);
        assertEquals(BinarySearch.predecessor(A1, 11), 0);
        assertEquals(BinarySearch.predecessor(A1, 12), 1);
        assertEquals(BinarySearch.predecessor(A1, 9), -1);
        
        assertEquals(BinarySearch.predecessor(A3, 10), -1);
        assertEquals(BinarySearch.predecessor(A3, 40), 6);
        assertEquals(BinarySearch.predecessor(A3, 100), 17);
        assertEquals(BinarySearch.predecessor(A3, 99), 16);
    }
    
    public void testSucc() {
        assertEquals(BinarySearch.successor(A1, 10, comp), 1);
        assertEquals(BinarySearch.successor(A1, 11, comp), -1);
        assertEquals(BinarySearch.successor(A1, 12, comp), -1);
        assertEquals(BinarySearch.successor(A1, 9, comp), 0);
        
        assertEquals(BinarySearch.successor(A3, 10, comp), 2);
        assertEquals(BinarySearch.successor(A3, 68, comp), 11);
        assertEquals(BinarySearch.successor(A3, 100, comp), -1);
        assertEquals(BinarySearch.successor(A3, 99, comp), -1);
        
        assertEquals(BinarySearch.successor(A1, 10), 1);
        assertEquals(BinarySearch.successor(A1, 11), -1);
        assertEquals(BinarySearch.successor(A1, 12), -1);
        assertEquals(BinarySearch.successor(A1, 9), 0);
        
        assertEquals(BinarySearch.successor(A3, 10), 2);
        assertEquals(BinarySearch.successor(A3, 68), 11);
        assertEquals(BinarySearch.successor(A3, 100), -1);
        assertEquals(BinarySearch.successor(A3, 99), -1);
    }
    
    public void testCount() {
        assertEquals(BinarySearch.count(A1, 10, comp), 1);
        assertEquals(BinarySearch.count(A1, 11, comp), 1);
        assertEquals(BinarySearch.count(A1, 9, comp), 0);
        assertEquals(BinarySearch.count(A1, 12, comp), 0);
        
        assertEquals(BinarySearch.count(A3, 77, comp), 3);
        assertEquals(BinarySearch.count(A3, 99, comp), 1);
        assertEquals(BinarySearch.count(A3, 10, comp), 2);
        assertEquals(BinarySearch.count(A3, 33, comp), 0);
        
        assertEquals(BinarySearch.count(A1, 10), 1);
        assertEquals(BinarySearch.count(A1, 11), 1);
        assertEquals(BinarySearch.count(A1, 9), 0);
        assertEquals(BinarySearch.count(A1, 12), 0);
        
        assertEquals(BinarySearch.count(A3, 77), 3);
        assertEquals(BinarySearch.count(A3, 99), 1);
        assertEquals(BinarySearch.count(A3, 10), 2);
        assertEquals(BinarySearch.count(A3, 33), 0);
    }
    
    public void testRank() {
        assertEquals(BinarySearch.rank(A1, 10, comp), 0);
        assertEquals(BinarySearch.rank(A1, 11, comp), 1);
        assertEquals(BinarySearch.rank(A1, 12, comp), 2);
        assertEquals(BinarySearch.rank(A1, 9, comp), 0);
        
        assertEquals(BinarySearch.rank(A2, 10, comp), 0);
        assertEquals(BinarySearch.rank(A2, 11, comp), 1);
        assertEquals(BinarySearch.rank(A2, 9, comp), 0);
        
        assertEquals(BinarySearch.rank(A3, 77, comp), 11);
        assertEquals(BinarySearch.rank(A3, 10, comp), 0);
        assertEquals(BinarySearch.rank(A3, 100, comp), 18);
        assertEquals(BinarySearch.rank(A3, 51, comp), 9);
        
        assertEquals(BinarySearch.rank(A1, 10), 0);
        assertEquals(BinarySearch.rank(A1, 11), 1);
        assertEquals(BinarySearch.rank(A1, 12), 2);
        assertEquals(BinarySearch.rank(A1, 9), 0);
        
        assertEquals(BinarySearch.rank(A2, 10), 0);
        assertEquals(BinarySearch.rank(A2, 11), 1);
        assertEquals(BinarySearch.rank(A2, 9), 0);
        
        assertEquals(BinarySearch.rank(A3, 77), 11);
        assertEquals(BinarySearch.rank(A3, 10), 0);
        assertEquals(BinarySearch.rank(A3, 100), 18);
        assertEquals(BinarySearch.rank(A3, 51), 9);
    }
    
    public void testRangeCount() {
        assertEquals(BinarySearch.rangeCount(A1, 10, 11, comp), 1);
        assertEquals(BinarySearch.rangeCount(A1, 11, 12, comp), 1);
        assertEquals(BinarySearch.rangeCount(A1, 0, 99, comp), 2);
        assertEquals(BinarySearch.rangeCount(A1, 9, 10, comp), 0);
        
        assertEquals(BinarySearch.rangeCount(A3, -10, 1000, comp), 18);
        assertEquals(BinarySearch.rangeCount(A3, -10, 25, comp), 7);
        assertEquals(BinarySearch.rangeCount(A3, 70, 1000, comp), 7);
        assertEquals(BinarySearch.rangeCount(A3, 25, 70, comp), 4);
        
        assertEquals(BinarySearch.rangeCount(A1, 10, 11), 1);
        assertEquals(BinarySearch.rangeCount(A1, 11, 12), 1);
        assertEquals(BinarySearch.rangeCount(A1, 0, 99), 2);
        assertEquals(BinarySearch.rangeCount(A1, 9, 10), 0);
        
        assertEquals(BinarySearch.rangeCount(A3, -10, 1000), 18);
        assertEquals(BinarySearch.rangeCount(A3, -10, 25), 7);
        assertEquals(BinarySearch.rangeCount(A3, 70, 1000), 7);
        assertEquals(BinarySearch.rangeCount(A3, 25, 70), 4);
    }
}
