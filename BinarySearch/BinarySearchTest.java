import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class BinarySearchTest extends TestCase {
    // small arrays to test edge cases and boundary conditions
    int[] a1 = {10, 11};
    int[] a2 = {10};
    
    // a more common sorted array to test functionality
    int[] a5 = {10, 10, 11, 13, 18, 23, 23, 48, 50, 54, 68, 77, 77, 77, 84, 98,
        98, 99};
    
    public void testIndexOf() {
        assertEquals(BinarySearch.indexOf(10, a1), 0);
        assertEquals(BinarySearch.indexOf(12, a1), -1);
        assertEquals(BinarySearch.indexOf(4, a1), -1);
        
        assertEquals(BinarySearch.indexOf(10, a2), 0);
        assertEquals(BinarySearch.indexOf(12, a2), -1);
        assertEquals(BinarySearch.indexOf(4, a2), -1);
        
        int n10 = BinarySearch.indexOf(10, a5);
        assertTrue(n10 >= 0 && n10 <= 1);
        assertEquals(BinarySearch.indexOf(12, a5), -1);
        assertEquals(BinarySearch.indexOf(4, a5), -1);
        
        int n77 = BinarySearch.indexOf(77, a5);
        assertTrue(n77 >= 11 && n77 <= 13);
        
    }
    
    public void testFirstIndexOf() {
        assertEquals(BinarySearch.firstIndexOf(10, a1), 0);
        assertEquals(BinarySearch.firstIndexOf(11, a1), 1);
        assertEquals(BinarySearch.firstIndexOf(12, a1), -1);
        assertEquals(BinarySearch.firstIndexOf(4, a1), -1);
        
        assertEquals(BinarySearch.firstIndexOf(10, a5), 0);
        assertEquals(BinarySearch.firstIndexOf(77, a5), 11);
        assertEquals(BinarySearch.firstIndexOf(99, a5), 17);
    }
    
    public void testLastIndexOf() {
        // normal test cases
        assertEquals(BinarySearch.lastIndexOf(10, a1), 0);
        assertEquals(BinarySearch.lastIndexOf(11, a1), 1);
        assertEquals(BinarySearch.lastIndexOf(12, a1), -1);
        assertEquals(BinarySearch.lastIndexOf(4, a1), -1);
        
        // in larger arrays
        assertEquals(BinarySearch.lastIndexOf(10, a5), 1);
        assertEquals(BinarySearch.lastIndexOf(77, a5), 13);
        assertEquals(BinarySearch.lastIndexOf(99, a5), 17);
    }
    
    public void testFloor() {
        assertEquals(BinarySearch.floor(10, a1), 0);
        assertEquals(BinarySearch.floor(11, a1), 1);
        assertEquals(BinarySearch.floor(12, a1), 1);
        assertEquals(BinarySearch.floor(9, a1), -1);
        
        assertEquals(BinarySearch.floor(10, a2), 0);
        assertEquals(BinarySearch.floor(9, a2), -1);
        assertEquals(BinarySearch.floor(12, a2), 0);
        
        assertEquals(BinarySearch.floor(80, a5), 13);
    }
    
    public void testCeil() {
        assertEquals(BinarySearch.ceil(10, a1), 0);
        assertEquals(BinarySearch.ceil(11, a1), 1);
        assertEquals(BinarySearch.ceil(12, a1), -1);
        assertEquals(BinarySearch.ceil(9, a1), 0);
        
        assertEquals(BinarySearch.ceil(10, a2), 0);
        assertEquals(BinarySearch.ceil(9, a2), 0);
        assertEquals(BinarySearch.ceil(12, a2), -1);
        
        assertEquals(BinarySearch.ceil(20, a5), 5);
    }
    
    public void testPred() {
        assertEquals(BinarySearch.pred(10, a1), -1);
        assertEquals(BinarySearch.pred(11, a1), 0);
        assertEquals(BinarySearch.pred(12, a1), 1);
        assertEquals(BinarySearch.pred(9, a1), -1);
        
        assertEquals(BinarySearch.pred(10, a5), -1);
        assertEquals(BinarySearch.pred(40, a5), 6);
        assertEquals(BinarySearch.pred(100, a5), 17);
        assertEquals(BinarySearch.pred(99, a5), 16);
    }
    
    public void testSucc() {
        assertEquals(BinarySearch.succ(10, a1), 1);
        assertEquals(BinarySearch.succ(11, a1), -1);
        assertEquals(BinarySearch.succ(12, a1), -1);
        assertEquals(BinarySearch.succ(9, a1), 0);
        
        assertEquals(BinarySearch.succ(10, a5), 2);
        assertEquals(BinarySearch.succ(68, a5), 11);
        assertEquals(BinarySearch.succ(100, a5), -1);
        assertEquals(BinarySearch.succ(99, a5), -1);
    }
    
    public void testCount() {
        assertEquals(BinarySearch.count(10, a1), 1);
        assertEquals(BinarySearch.count(11, a1), 1);
        assertEquals(BinarySearch.count(9, a1), 0);
        assertEquals(BinarySearch.count(12, a1), 0);
        
        assertEquals(BinarySearch.count(77, a5), 3);
        assertEquals(BinarySearch.count(99, a5), 1);
        assertEquals(BinarySearch.count(10, a5), 2);
        assertEquals(BinarySearch.count(33, a5), 0);
    }
    
    public void testRank() {
        assertEquals(BinarySearch.rank(10, a1), 0);
        assertEquals(BinarySearch.rank(11, a1), 1);
        assertEquals(BinarySearch.rank(12, a1), 2);
        assertEquals(BinarySearch.rank(9, a1), 0);
        
        assertEquals(BinarySearch.rank(10, a2), 0);
        assertEquals(BinarySearch.rank(11, a2), 1);
        assertEquals(BinarySearch.rank(9, a2), 0);
        
        assertEquals(BinarySearch.rank(77, a5), 11);
        assertEquals(BinarySearch.rank(10, a5), 0);
        assertEquals(BinarySearch.rank(100, a5), 18);
        assertEquals(BinarySearch.rank(51, a5), 9);
    }
    
    public void testRangeCount() {
        assertEquals(BinarySearch.rangeCount(10, 11, a1), 1);
        assertEquals(BinarySearch.rangeCount(11, 12, a1), 1);
        assertEquals(BinarySearch.rangeCount(0, 99, a1), 2);
        assertEquals(BinarySearch.rangeCount(9, 10, a1), 0);
        
        assertEquals(BinarySearch.rangeCount(-10, 1000, a5), 18);
        assertEquals(BinarySearch.rangeCount(-10, 25, a5), 7);
        assertEquals(BinarySearch.rangeCount(70, 1000, a5), 7);
        assertEquals(BinarySearch.rangeCount(25, 70, a5), 4);
    }
}
