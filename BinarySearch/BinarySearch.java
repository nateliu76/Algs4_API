import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;
import java.util.Comparator;

/**
 *  The BinarySearch class provides a static method for binary
 *  searching for an integer in a sorted array of integers.
 *  All operations takes logarithmic time in the worst case
 */
public class BinarySearch {

    /**
     * This class should not be instantiated.
     */
    private BinarySearch() { }
    
    // basically the same as lastIndexOf
    public static int indexOf(int key, int[] a) {
        int lo = 0;
        int hi = a.length;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            int c = Integer.compare(a[mid], key);
            if (c > 0) hi = mid;
            else lo = mid;
        }
        if (Integer.compare(a[lo], key) == 0) return lo;
        else return -1;
    }
    
    // return the smallest index i such a[i] equals key
    // use pred + 1 instead?
    public static int firstIndexOf(int key, int[] a) {
        if (a.length == 0) return -1;
        int lo = -1;
        int hi = a.length - 1;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            int c = Integer.compare(a[mid], key);
            if (c >= 0) hi = mid;
            else lo = mid;
        }
        if (Integer.compare(a[hi], key) == 0) return hi;
        else return -1;
    }
    
    // return the largest index i such a[i] equals key
    // use succ - 1 instead?
    public static int lastIndexOf(int key, int[] a) {
        if (a.length == 0) return -1;
        int lo = 0;
        int hi = a.length;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            int c = Integer.compare(a[mid], key);
            if (c <= 0) lo = mid;
            else hi = mid;
        }
        if (Integer.compare(a[lo], key) == 0) return lo;
        else return -1;
    }
    
    // index of largest key less than or equal to a given key
    // use succ - 1
    public static int floor(int key, int[] a) {
        if (a.length == 0) return -1;
        int lo = 0;
        int hi = a.length;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            int c = Integer.compare(a[mid], key);
            if (c <= 0) lo = mid;
            else hi = mid;
        }
        if (Integer.compare(a[lo], key) <= 0) return lo;
        else return -1;
    }
    
    // index of smallest key greater than or equal to a given key
    // use pred + 1
    public static int ceil(int key, int[] a) {
        if (a.length == 0) return -1;
        int lo = -1;
        int hi = a.length - 1;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            int c = Integer.compare(a[mid], key);
            if (c >= 0) hi = mid;
            else lo = mid;
        }
        if (Integer.compare(a[hi], key) >= 0) return hi;
        else return -1;
    }
    
    // index of largest key strictly less than a given key
    public static int pred(int key, int[] a) {
        if (a.length == 0) return -1;
        int lo = 0;
        int hi = a.length;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            int c = Integer.compare(a[mid], key);
            if (c < 0) lo = mid;
            else hi = mid;
        }
        if (Integer.compare(a[lo], key) < 0) return lo;
        else return -1;
    }
    
    // index of smallest key strictly larger than a given key
    public static int succ(int key, int[] a) {
        if (a.length == 0) return -1;
        int lo = -1; 
        int hi = a.length - 1;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            int c = Integer.compare(a[mid], key);
            if (c > 0) hi = mid;
            else lo = mid;
        }
        if (Integer.compare(a[hi], key) > 0) return hi;
        else return -1;
    }
    
    // number of entries equal to a given key
    public static int count(int key, int[] a) {
        int first = firstIndexOf(key, a);
        if (first == -1) return 0;
        int last = lastIndexOf(key, a);
        return last - first + 1;
    }
    
    // number of keys strictly less than a given key
    public static int rank(int key, int[] a) {
        return pred(key, a) + 1;
    }
    
    // is the given key in the array
    public static boolean contains(int key, int[] a) {
        return indexOf(key, a) != -1;
    }
    
    // number of keys between key1 (inclusive) and key2 (exclusive)
    public static int rangeCount(int key1, int key2, int[] a) {
        if (key1 >= key2) throw new IllegalArgumentException();
        int lo = pred(key1, a);
        int hi = pred(key2, a);
        return hi - lo;
    }
    
    /**
     * Reads in a sequence of integers from the whitelist file, specified as
     * a command-line argument. Reads in integers from standard input and
     * prints to standard output those integers that do *not* appear in the file.
     */
    public static void main(String[] args) {
        // read the integers from a file
        In in = new In(args[0]);
        int[] arr = in.readAllInts();
        Arrays.sort(arr);
        // read integer key from standard input
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            System.out.println(indexOf(key, arr));
            System.out.println("first: "+firstIndexOf(key, arr));
            System.out.println("last: "+lastIndexOf(key, arr));
            System.out.println("floor: "+floor(key, arr));
            System.out.println("ceil: "+ceil(key, arr));
            System.out.println("pred: "+pred(key, arr));
            System.out.println("succ: "+succ(key, arr));
            System.out.println("count: "+count(key, arr));
            System.out.println("rank: "+rank(key, arr));
        }
    }
}
