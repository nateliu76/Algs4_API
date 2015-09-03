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
    public static <Key> int indexOf(Key key, Key[] a, Comparator<Key> comp) {
        int lo = 0;
        int hi = a.length;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            if (comp.compare(a[mid], key) > 0) hi = mid;
            else lo = mid;
        }
        if (comp.compare(a[lo], key) == 0) return lo;
        else return -1;
    }
    
    // return the smallest index i such a[i] equals key
    // firstIndexOf reduces to checking if a[pred + 1] == key
    public static <Key> int firstIndexOf(Key key, Key[] a, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        if (comp.compare(a[0], key) == 0) return 0;
        int pred = pred(key, a, comp);
        if (pred == -1 || pred == a.length - 1 
                || comp.compare(a[pred + 1], key) != 0) return -1;
        else return pred + 1;
    }
    
    // return the largest index i such a[i] equals key
    // lastIndexOf reduces to checking if a[succ - 1] == key
    public static <Key> int lastIndexOf(Key key, Key[] a, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        if (a[a.length - 1] == key) return a.length - 1;
        int succ = succ(key, a, comp);
        if (succ <= 0 || comp.compare(a[succ - 1], key) != 0) return -1;
        else return succ - 1;
    }
    
    // index of largest key less than or equal to a given key
    // floor reduces to succ - 1
    public static <Key> int floor(Key key, Key[] a, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        if (comp.compare(a[a.length - 1], key) <= 0) return a.length - 1;
        int succ = succ(key, a, comp);
        if (succ <= 0) return -1;
        else return succ - 1;
    }
    
    // index of smallest key greater than or equal to a given key
    // ceiling reduces to pred + 1
    public static <Key> int ceil(Key key, Key[] a, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        if (comp.compare(a[0], key) >= 0) return 0;
        int pred = pred(key, a, comp);
        if (pred == a.length - 1) return -1;
        else return pred + 1;
    }
    
    // index of largest key strictly less than a given key
    public static <Key> int pred(Key key, Key[] a, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int lo = 0;
        int hi = a.length;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            if (comp.compare(a[mid], key) < 0) lo = mid;
            else hi = mid;
        }
        if (comp.compare(a[lo], key) < 0) return lo;
        else return -1;
    }
    
    // index of smallest key strictly larger than a given key
    public static <Key> int succ(Key key, Key[] a, Comparator<Key> comp) {
        if (a.length == 0) return -1;
        int lo = -1; 
        int hi = a.length - 1;
        while (hi > lo + 1) {
            int mid = lo + (hi - lo) / 2;
            if (comp.compare(a[mid], key) > 0) hi = mid;
            else lo = mid;
        }
        if (comp.compare(a[hi], key) > 0) return hi;
        else return -1;
    }
    
    // number of entries equal to a given key
    public static <Key> int count(Key key, Key[] a, Comparator<Key> comp) {
        int first = firstIndexOf(key, a, comp);
        if (first == -1) return 0;
        int last = lastIndexOf(key, a, comp);
        return last - first + 1;
    }
    
    // number of keys strictly less than a given key
    public static <Key> int rank(Key key, Key[] a, Comparator<Key> comp) {
        return pred(key, a, comp) + 1;
    }
    
    // is the given key in the array
    public static <Key> boolean contains(Key key, Key[] a, Comparator<Key> comp) {
        return indexOf(key, a, comp) != -1;
    }
    
    // number of keys between key1 (inclusive) and key2 (exclusive)
    public static <Key> int rangeCount(Key key1, Key key2, Key[] a, Comparator<Key> comp) {
        if (comp.compare(key1, key2) >= 0) throw new IllegalArgumentException();
        int lo = pred(key1, a, comp);
        int hi = pred(key2, a, comp);
        return hi - lo;
    }
}
