/******************************************************************************
 * Compilation:  javac BinarySearch.java
 * Execution:    
 * Dependencies: 
 *
 * Binary search method with extra methods similar to those that are found in 
 * Binary search trees
 *
 ******************************************************************************/

import java.util.Comparator;

/**
 * The BinarySearch class provides a static method for binary
 * searching for a key in a sorted array of objects.
 * 
 * All operations takes logarithmic time in the worst case, and uses constant
 * space
 * Methods and the run time analysis/guarantees:
 * indexOf:      uses at most floor(lg N) + 1 key comparisons
 * firstIndexOf: uses floor(lg N) + 1 key comparisons
 * lastIndexOf:  uses floor(lg N) + 1 key comparisons
 * floor:        uses floor(lg N) + 1 key comparisons
 * ceiling:      uses floor(lg N) + 1 key comparisons
 * predecessor:  uses floor(lg N) + 1 key comparisons
 * successor:    uses floor(lg N) + 1 key comparisons
 * count:        uses floor(lg N) + 1 or 2 * floor(lg N) + 2 key comparisons
 * rank:         uses floor(lg N) + 1 key comparisons
 * contains:     uses at most floor(lg N) + 1 key comparisons
 * rangeCount:   uses 2 * floor(lg N) + 2 key comparisons
 * 
 * @author Nate Liu
 * @author Kevin Wayne
 */
public class BinarySearch {
    
    /**
     * This class should not be instantiated.
     */
    private BinarySearch() { }
    
    
    /**************************************************************************
     * Index of key
     *************************************************************************/
    
    /**
     * Returns the index of the specified key in the specified array, or -1 if
     * there is no such index.
     * If the array contains multiple entries equal to the specified key, it
     * returns the index of any such key.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return index of key in array a if present, -1 otherwise
     */
    public static <Key extends Comparable<Key>> int indexOf(Key[] a, Key key) {
        return binSearch(a, key, null);
    }
    
    /**
     * Returns the index of the specified key in the specified array, or -1 if
     * there is no such index.
     * If the array contains multiple entries equal to the specified key, it
     * returns the index of any such key.
     * The array must be sorted into ascending order according to the orderings
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return index of key in array a if present, -1 otherwise
     */
    public static <Key> int indexOf(Key[] a, Key key, Comparator<Key> comp) {
        return binSearch(a, key, comp);
    }
    
    
    /**************************************************************************
     * First and last index of key
     *************************************************************************/
    
    /**
     * Returns the first index of the specified key in the specified array, or 
     * (-(insertion point) - 1) if there is no such index.
     * The insertion point is defined as the point at which the key would be 
     * inserted into the array: the index of the first element greater than the
     * key, or a.length if all elements in the array are less than the specified
     * key. Note that this guarantees that the return value will be >= 0 if and 
     * only if the key is found.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the first index of the specified key in the specified array;
     *         (-(insertion point) - 1) if there is no such index
     */
    public static <Key extends Comparable<Key>> int firstIndexOf(Key[] a, Key key) {
        return firstIndexHelper(a, key, null);
    }
    
    /**
     * Returns the first index of the specified key in the specified array, or 
     * (-(insertion point) - 1) if there is no such index.
     * The insertion point is defined as the point at which the key would be 
     * inserted into the array: the index of the first element greater than the
     * key, or a.length if all elements in the array are less than the specified
     * key. Note that this guarantees that the return value will be >= 0 if and 
     * only if the key is found.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the first index of the specified key in the specified array;
     *         (-(insertion point) - 1) if there is no such index
     */
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comp) {
        return firstIndexHelper(a, key, comp);
    }
    
    /**
     * Returns the last index of the specified key in the specified array, or 
     * (-(insertion point) - 1) if there is no such index.
     * The insertion point is defined as the point at which the key would be 
     * inserted into the array: the index of the first element greater than the
     * key, or a.length if all elements in the array are less than the specified
     * key. Note that this guarantees that the return value will be >= 0 if and 
     * only if the key is found.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the last index of the specified key in the specified array;
     *         (-(insertion point) - 1) if there is no such index
     */
    public static <Key extends Comparable<Key>> int lastIndexOf(Key[] a, Key key) {
        return lastIndexHelper(a, key, null);
    }
    
    /**
     * Returns the last index of the specified key in the specified array, or 
     * (-(insertion point) - 1) if there is no such index.
     * The insertion point is defined as the point at which the key would be 
     * inserted into the array: the index of the first element greater than the
     * key, or a.length if all elements in the array are less than the specified
     * key. Note that this guarantees that the return value will be >= 0 if and 
     * only if the key is found.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the last index of the specified key in the specified array;
     *         (-(insertion point) - 1) if there is no such index
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comp) {
        return lastIndexHelper(a, key, comp);
    }
    
    
    /**************************************************************************
     * Floor and ceiling of key
     *************************************************************************/
    
    /**
     * Returns the largest index of key less than or equal to specified key in 
     * the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the largest index of key less than or equal to specified key in 
     *         the specified array;
     *         - 1 if there is no such index
     */
    public static <Key extends Comparable<Key>> int floor(Key[] a, Key key) {
        return floorHelper(a, key, null);
    }
    
    /**
     * Returns the largest index of key less than or equal to specified key in 
     * the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the largest index of key less than or equal to specified key in 
     *         the specified array;
     *         - 1 if there is no such index
     */
    public static <Key> int floor(Key[] a, Key key, Comparator<Key> comp) {
        return floorHelper(a, key, comp);
    }
    
    /**
     * Returns the smallest index of key greater than or equal to specified key
     * in the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the smallest index of key greater than or equal to specified key 
     *         in the specified array;
     *         - 1 if there is no such index
     */
    public static <Key extends Comparable<Key>> int ceiling(Key[] a, Key key) {
        return ceilingHelper(a, key, null);
    }
    
    /**
     * Returns the smallest index of key greater than or equal to specified key
     * in the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the smallest index of key greater than or equal to specified key 
     *         in the specified array;
     *         - 1 if there is no such index
     */
    public static <Key> int ceiling(Key[] a, Key key, Comparator<Key> comp) {
        return ceilingHelper(a, key, comp);
    }
    
    
    /**************************************************************************
     * Predecessor and Successor of key
     *************************************************************************/
    
    /**
     * Returns the largest index of key strictly less than specified key in 
     * the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the largest index of key strictly less than specified key in 
     *         the specified array;
     *         - 1 if there is no such index
     */
    public static <Key extends Comparable<Key>> int predecessor(Key[] a, Key key) {
        return predHelper(a, key, null);
    }
    
    /**
     * Returns the largest index of key strictly less than specified key in 
     * the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the largest index of key less than or equal to specified key in 
     *         the specified array;
     *         - 1 if there is no such index
     */
    public static <Key> int predecessor(Key[] a, Key key, Comparator<Key> comp) {
        return predHelper(a, key, comp);
    }
    
    /**
     * Returns the smallest index of key strictly greater than specified key
     * in the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the smallest index of key strictly greater than specified key 
     *         in the specified array;
     *         - 1 if there is no such index
     */
    public static <Key extends Comparable<Key>> int successor(Key[] a, Key key) {
        return succHelper(a, key, null);
    }
    
    /**
     * Returns the smallest index of key strictly greater than specified key
     * in the specified array, or - 1 if there is no such index.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the smallest index of key strictly greater than specified key 
     *         in the specified array;
     *         - 1 if there is no such index
     */
    public static <Key> int successor(Key[] a, Key key, Comparator<Key> comp) {
        return succHelper(a, key, comp);
    }
    
    
    /**************************************************************************
     * Count, rank, contains and range count
     *************************************************************************/
    
    /**
     * Returns the number of keys equal to the specified key
     * in the specified array.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the number of keys equal to the specified key in the 
     *         specified array.
     */
    public static <Key extends Comparable<Key>> int count(Key[] a, Key key) {
        return countHelper(a, key, null);
    }
    
    /**
     * Returns the number of keys equal to the specified key
     * in the specified array.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the number of keys equal to the specified key in the 
     *         specified array.
     */
    public static <Key> int count(Key[] a, Key key, Comparator<Key> comp) {
        return countHelper(a, key, comp);
    }
    
    /**
     * Returns the number of keys strictly less than the specified key
     * in the specified array.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return the number of keys strictly less than the specified key in the 
     *         specified array.
     */
    public static <Key extends Comparable<Key>> int rank(Key[] a, Key key) {
        return predecessor(a, key) + 1;
    }
    
    /**
     * Returns the number of keys strictly less than the specified key
     * in the specified array.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return the number of keys strictly less than the specified key in the 
     *         specified array.
     */
    public static <Key> int rank(Key[] a, Key key, Comparator<Key> comp) {
        return predecessor(a, key, comp) + 1;
    }
    
    /**
     * Returns true if specified key is in the specified array; false otherwise.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @return true if specified key is in the specified array; false otherwise
     */
    public static <Key extends Comparable<Key>> boolean contains(Key[] a, Key key) {
        return indexOf(a, key) != -1;
    }
    
    /**
     * Returns true if specified key is in the specified array; false otherwise.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @return true if specified key is in the specified array; false otherwise
     */
    public static <Key> boolean contains(Key[] a, Key key, Comparator<Key> comp) {
        return indexOf(a, key, comp) != -1;
    }
    
    /**
     * Returns the number of keys between key1 (inclusive) and key2 (exclusive)
     * in the specified array.
     * The array must be sorted into ascending order according to the natural
     * orderings of its entries prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @throw IllegalArgumentException if key2 is not strictly greater than key1
     * @return the number of keys between key1 (inclusive) and key2 (exclusive)
     *         in the specified array
     */
    public static <Key extends Comparable<Key>> int rangeCount(Key[] a, Key key1, Key key2) {
        if (cmp(key1, key2, null) >= 0) throw new IllegalArgumentException();
        return rangeCountHelper(a, key1, key2, null);
    }
    
    /**
     * Returns the number of keys between key1 (inclusive) and key2 (exclusive)
     * in the specified array.
     * The array must be sorted into ascending order according to the ordering
     * specified by the comparator prior to calling this method.
     * 
     * @param a the sorted array to be searched
     * @param key the search key
     * @param comp the comparator which specifies the order of objects
     * @throw IllegalArgumentException if key2 is not strictly greater than key1
     * @return the number of keys between key1 (inclusive) and key2 (exclusive)
     *         in the specified array
     */
    public static <Key> int rangeCount(Key[] a, Key key1, Key key2, Comparator<Key> comp) {
        if (cmp(key1, key2, comp) >= 0) throw new IllegalArgumentException();
        return rangeCountHelper(a, key1, key2, comp);
    }
    
    
    /**************************************************************************
     * Main methods that perform binary search
     *************************************************************************/
    
    /**
     * Returns any index of the specified key in the specified sorted array, -1
     * if the key doesn't exist in the array
     */
    private static <Key> int binSearch(Key[] a, Key key, Comparator<Key> comp) {
        int lo = 0;
        int hi = a.length - 1;
        while (hi >= lo) {
            int mid = lo + (hi - lo) / 2;
            int c = cmp(a[mid], key, comp);
            if (c > 0) hi = mid - 1;
            else if (c < 0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
    
    /**
     * Does a binary search that uses floor(lg N) + 1 key compares.
     * This method is shared among ceiling(), lastIndexOf(), and predecessor().
     * The initial values of hi/lo and index returned differ slightly, with 
     * predecessor() differing from ceiling() and lastIndexOf()
     */
    private static <Key> int binSearchCeil(Key[] a, Key key, Comparator<Key> comp, 
                                      boolean findPred) {
        // initialize variables
        int lo = -1;
        int hi = a.length - 1;
        if (findPred) {
            lo = 0;
            hi = a.length;
        }
        // binary search loop
        while (hi - lo > 1) {
            int mid = lo + (hi - lo) / 2;
            if (cmp(a[mid], key, comp) >= 0) hi = mid;
            else lo = mid;
        }
        // return desired index
        if (findPred) return lo;
        else return hi;
    }
    
    /**
     * Does a binary search that uses floor(lg N) + 1 key compares.
     * This method is shared among floor(), firstIndexOf(), and successor().
     * The initial values of hi/lo and index returned differ slightly, with 
     * successor() differing from floor() and firstIndexOf()
     */
    private static <Key> int binSearchFloor(Key[] a, Key key, Comparator<Key> comp, 
                                      boolean findSucc) {
        // initialize variables
        int lo = 0;
        int hi = a.length;
        if (findSucc) {
            lo = -1;
            hi = a.length - 1;
        }
        // binary search loop
        while (hi - lo > 1) {
            int mid = lo + (hi - lo) / 2;
            if (cmp(a[mid], key, comp) <= 0) lo = mid;
            else hi = mid;
        }
        // return desired index
        if (findSucc) return hi;
        else return lo;
    }
    
    
    /**************************************************************************
     * Helper methods for index of, first/last index, floor/ceiling, 
     * and predecessor/successor that call binary search methods
     *************************************************************************/
    
    private static <Key> int firstIndexHelper(Key[] a, Key key, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int idx = binSearchCeil(a, key, comp, false);
        
        // return index or (-(insertion point) - 1)
        int c = cmp(a[idx], key, comp);
        if (c == 0) return idx;
        else if (idx == a.length - 1 && c < 0) return -idx - 2;
        else return -idx - 1;
    }
    
    private static <Key> int lastIndexHelper(Key[] a, Key key, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int idx = binSearchFloor(a, key, comp, false);
        
        // return index or (-(insertion point) - 1)
        int c = cmp(a[idx], key, comp);
        if (c == 0) return idx;
        else if (idx == 0 && c > 0) return -1;
        else return -idx - 2;
    }
    
    private static <Key> int floorHelper(Key[] a, Key key, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int idx = binSearchFloor(a, key, comp, false);
        if (cmp(a[idx], key, comp) <= 0) return idx;
        else return -1;
    }
    
    private static <Key> int ceilingHelper(Key[] a, Key key, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int idx = binSearchCeil(a, key, comp, false);
        if (cmp(a[idx], key, comp) >= 0) return idx;
        else return -1;
    }
    
    private static <Key> int predHelper(Key[] a, Key key, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int idx = binSearchCeil(a, key, comp, true);
        if (cmp(a[idx], key, comp) < 0) return idx;
        else return -1;
    }
    
    private static <Key> int succHelper(Key[] a, Key key, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int idx = binSearchFloor(a, key, comp, true);
        if (cmp(a[idx], key, comp) > 0) return idx;
        else return -1;
    }
    
    private static <Key> int cmp(Key key1, Key key2, Comparator<Key> comp) {
        if (comp == null) return ((Comparable<Key>) key1).compareTo(key2);
        else return comp.compare(key1, key2);
    }
    
    
    /**************************************************************************
     * Helper methods for count and range count
     *************************************************************************/
    
    private static <Key> int countHelper(Key[] a, Key key, Comparator<Key> comp) {
        int first = firstIndexOf(a, key, comp);
        if (first < 0) return 0;
        int last = lastIndexOf(a, key, comp);
        return last - first + 1;
    }
    
    private static <Key> int rangeCountHelper(Key[] a, Key key1, Key key2, 
                                              Comparator<Key> comp) {
        int lo = predecessor(a, key1, comp);
        int hi = predecessor(a, key2, comp);
        return hi - lo;
    }
}
