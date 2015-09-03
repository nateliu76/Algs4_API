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
        assertEquals(BinarySearch.indexOf(10, A1, comp), 0);
        assertEquals(BinarySearch.indexOf(11, A1, comp), 1);
        assertEquals(BinarySearch.indexOf(12, A1, comp), -1);
        assertEquals(BinarySearch.indexOf(4, A1, comp), -1);
        
        assertEquals(BinarySearch.indexOf(10, A2, comp), 0);
        assertEquals(BinarySearch.indexOf(12, A2, comp), -1);
        assertEquals(BinarySearch.indexOf(4, A2, comp), -1);
        
        int n10 = BinarySearch.indexOf(10, A3, comp);
        assertTrue(n10 >= 0 && n10 <= 1);
        assertEquals(BinarySearch.indexOf(12, A3, comp), -1);
        assertEquals(BinarySearch.indexOf(4, A3, comp), -1);
        
        int n77 = BinarySearch.indexOf(77, A3, comp);
        assertTrue(n77 >= 11 && n77 <= 13);
        
    }
    
    public void testFirstIndexOf() {
        assertEquals(BinarySearch.firstIndexOf(10, A1, comp), 0);
        assertEquals(BinarySearch.firstIndexOf(11, A1, comp), 1);
        assertEquals(BinarySearch.firstIndexOf(12, A1, comp), -1);
        assertEquals(BinarySearch.firstIndexOf(4, A1, comp), -1);
        
        assertEquals(BinarySearch.firstIndexOf(10, A3, comp), 0);
        assertEquals(BinarySearch.firstIndexOf(77, A3, comp), 11);
        assertEquals(BinarySearch.firstIndexOf(99, A3, comp), 17);
    }
    
    public void testLastIndexOf() {
        // normal test cases
        assertEquals(BinarySearch.lastIndexOf(10, A1, comp), 0);
        assertEquals(BinarySearch.lastIndexOf(11, A1, comp), 1);
        assertEquals(BinarySearch.lastIndexOf(12, A1, comp), -1);
        assertEquals(BinarySearch.lastIndexOf(4, A1, comp), -1);
        
        // in larger arrays
        assertEquals(BinarySearch.lastIndexOf(10, A3, comp), 1);
        assertEquals(BinarySearch.lastIndexOf(77, A3, comp), 13);
        assertEquals(BinarySearch.lastIndexOf(99, A3, comp), 17);
    }
    
    public void testFloor() {
        assertEquals(BinarySearch.floor(10, A1, comp), 0);
        assertEquals(BinarySearch.floor(11, A1, comp), 1);
        assertEquals(BinarySearch.floor(12, A1, comp), 1);
        assertEquals(BinarySearch.floor(9, A1, comp), -1);
        
        assertEquals(BinarySearch.floor(10, A2, comp), 0);
        assertEquals(BinarySearch.floor(9, A2, comp), -1);
        assertEquals(BinarySearch.floor(12, A2, comp), 0);
        
        assertEquals(BinarySearch.floor(80, A3, comp), 13);
    }

    public void testCeil() {
        assertEquals(BinarySearch.ceil(10, A1, comp), 0);
        assertEquals(BinarySearch.ceil(11, A1, comp), 1);
        assertEquals(BinarySearch.ceil(12, A1, comp), -1);
        assertEquals(BinarySearch.ceil(9, A1, comp), 0);
        
        assertEquals(BinarySearch.ceil(10, A2, comp), 0);
        assertEquals(BinarySearch.ceil(9, A2, comp), 0);
        assertEquals(BinarySearch.ceil(12, A2, comp), -1);
        
        assertEquals(BinarySearch.ceil(20, A3, comp), 5);
    }
    
    public void testPred() {
        assertEquals(BinarySearch.pred(10, A1, comp), -1);
        assertEquals(BinarySearch.pred(11, A1, comp), 0);
        assertEquals(BinarySearch.pred(12, A1, comp), 1);
        assertEquals(BinarySearch.pred(9, A1, comp), -1);
        
        assertEquals(BinarySearch.pred(10, A3, comp), -1);
        assertEquals(BinarySearch.pred(40, A3, comp), 6);
        assertEquals(BinarySearch.pred(100, A3, comp), 17);
        assertEquals(BinarySearch.pred(99, A3, comp), 16);
    }
    
    public void testSucc() {
        assertEquals(BinarySearch.succ(10, A1, comp), 1);
        assertEquals(BinarySearch.succ(11, A1, comp), -1);
        assertEquals(BinarySearch.succ(12, A1, comp), -1);
        assertEquals(BinarySearch.succ(9, A1, comp), 0);
        
        assertEquals(BinarySearch.succ(10, A3, comp), 2);
        assertEquals(BinarySearch.succ(68, A3, comp), 11);
        assertEquals(BinarySearch.succ(100, A3, comp), -1);
        assertEquals(BinarySearch.succ(99, A3, comp), -1);
    }
    
    public void testCount() {
        assertEquals(BinarySearch.count(10, A1, comp), 1);
        assertEquals(BinarySearch.count(11, A1, comp), 1);
        assertEquals(BinarySearch.count(9, A1, comp), 0);
        assertEquals(BinarySearch.count(12, A1, comp), 0);
        
        assertEquals(BinarySearch.count(77, A3, comp), 3);
        assertEquals(BinarySearch.count(99, A3, comp), 1);
        assertEquals(BinarySearch.count(10, A3, comp), 2);
        assertEquals(BinarySearch.count(33, A3, comp), 0);
    }
    
    public void testRank() {
        assertEquals(BinarySearch.rank(10, A1, comp), 0);
        assertEquals(BinarySearch.rank(11, A1, comp), 1);
        assertEquals(BinarySearch.rank(12, A1, comp), 2);
        assertEquals(BinarySearch.rank(9, A1, comp), 0);
        
        assertEquals(BinarySearch.rank(10, A2, comp), 0);
        assertEquals(BinarySearch.rank(11, A2, comp), 1);
        assertEquals(BinarySearch.rank(9, A2, comp), 0);
        
        assertEquals(BinarySearch.rank(77, A3, comp), 11);
        assertEquals(BinarySearch.rank(10, A3, comp), 0);
        assertEquals(BinarySearch.rank(100, A3, comp), 18);
        assertEquals(BinarySearch.rank(51, A3, comp), 9);
    }
    
    public void testRangeCount() {
        assertEquals(BinarySearch.rangeCount(10, 11, A1, comp), 1);
        assertEquals(BinarySearch.rangeCount(11, 12, A1, comp), 1);
        assertEquals(BinarySearch.rangeCount(0, 99, A1, comp), 2);
        assertEquals(BinarySearch.rangeCount(9, 10, A1, comp), 0);
        
        assertEquals(BinarySearch.rangeCount(-10, 1000, A3, comp), 18);
        assertEquals(BinarySearch.rangeCount(-10, 25, A3, comp), 7);
        assertEquals(BinarySearch.rangeCount(70, 1000, A3, comp), 7);
        assertEquals(BinarySearch.rangeCount(25, 70, A3, comp), 4);
    }
}
