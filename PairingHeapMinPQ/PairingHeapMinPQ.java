/******************************************************************************
 * Compilation:  javac PairingHeapMinPQ.java
 * Execution:    
 * Dependencies: Stack.java
 *
 * A min priority queue with a Pairing Heap implementation
 * 
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * The PairingHeapMinPQ class represents a priority queue of generic keys
 * It supports the usual insert and delete-the-minimum operations, 
 * along with the merging of two heaps together.
 * It also supports methods for peeking at the minimum key, testing if the 
 * priority queue is empty, and iterating through the keys.
 * Can be used with a comparator instead of the natural order.
 * 
 * The implementation has following time complexities:
 * construction:           O(1)
 * insert(key):            O(1)
 * meld(otherPairingHeap): O(1)
 * min():                  O(1)
 * delMin():               O(logn) (amortized)
 * iterator():             O(n) for construction
 *                         O(nlogn) (amortized) for iteration through all keys
 * clear()                 O(1)
 * 
 * delete(node) and decreaseKey(node) which are normally present in Pairing 
 * heap implementations are not implemented in this version.
 * See IndexPairingHeapMinPQ.java for implementations of those methods.
 * 
 * @author Nate Liu
 * 
 * @param <Key> the generic type of key on this priority queue
 */
public class PairingHeapMinPQ<Key> implements Iterable<Key> {
    private int size;
    private Comparator<Key> comparator;
    private Node root;
    
    private class Node {
        private Key key;
        private Node child;
        private Node next;    // next sibling
        private Node prev;    // previous sibling
        
        public Node(Key k) {
            key = k;
        }
    }
    
    /**
     * Initializes an empty priority queue.
     */
    public PairingHeapMinPQ() {
        size = 0;
    }
    
    /**
     * Initializes an empty priority queue with given comparator.
     * 
     * @param comp the order to use when comparing keys
     */
    public PairingHeapMinPQ(Comparator<Key> comp) {
        comparator = comp;
        size = 0;
    }
    
    /**
     * Returns true if this priority queue is empty.
     * 
     * @return true if this priority queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns the number of keys in this priority queue.
     * 
     * @return the number of keys in this priority queue
     */
    public int size() {
        return size;
    }
    
    /**
     * Empties and reinitializes the priority queue.
     */
    public void clear() {
        root = null;
        size = 0;
    }
    
    /**
     * Returns a smallest key on this priority queue.
     * 
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return root.key;
    }
    
    /**
     * Adds a new key to the priority queue.
     * 
     * @param k the key to add to this priority queue
     */
    public void insert(Key k) {
        Node n = new Node(k);
        if (root == null) root = n;
        else root = link(root, n);
        size++;
    }
    
    /**
     * Removes and returns a smallest key on this priority queue.
     * 
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key k = root.key;
        if (size == 1) {
            clear();
            return k;
        }
        root = meldChildren(root);
        size--;
        return k;
    }
    
    // the comparison-link method to meld heaps/nodes
    private Node link(Node n1, Node n2) {
        if (n1 == null) return n2;
        else if (n2 == null) return n1;
        else if (n1 == n2) return n1;
        
        int c = compare(n1.key, n2.key);
        if (c < 0) {
            addChild(n1, n2);
            return n1;
        } 
        else {
            addChild(n2, n1);
            return n2;
        }
    }
    
    private void addChild(Node n1, Node n2) {
        n2.prev = n1;
        n2.next = n1.child;
        if (n1.child != null) n1.child.prev = n2;
        n1.child = n2;
    }
    
    private int compare(Key k1, Key k2) {
        if (comparator != null) return comparator.compare(k1, k2);
        else return ((Comparable<Key>) k1).compareTo(k2);
    }
    
    /**
     * Melds all the children/subtrees of the given Node
     * 
     * @return the node with a smallest key of the subtrees
     */
    private Node meldChildren(Node n) {
        // if no child or single child, return child
        if (n.child == null || n.child.next == null)
            return n.child;
        
        // first pass, left to right, combine every two subheaps
        Node curr = n.child;
        Node tempNext = curr.next.next;
        curr = link(curr, curr.next);
        // update pointers
        n.child = curr;
        curr.prev = n;
        curr.next = tempNext;
        if (tempNext != null) tempNext.prev = curr;
        Node tempPrev = curr;
        
        while (tempNext != null && tempNext.next != null) {
            curr = tempNext;
            tempNext = curr.next.next;
            Node newRoot = link(curr, curr.next);
            
            // update pointers
            tempPrev.next = newRoot;
            newRoot.prev = tempPrev;
            newRoot.next = tempNext;
            if (tempNext != null) tempNext.prev = newRoot;
            tempPrev = newRoot;
        }
        
        // second pass, right to left, combine subheaps into one
        Node rightMost = tempNext;
        if (tempNext == null) rightMost = tempPrev;
        while (rightMost.prev != n) {
            tempPrev = rightMost.prev.prev;
            rightMost = link(rightMost, rightMost.prev);
            rightMost.prev = tempPrev;
            rightMost.next = null;
        }
        
        // break link to old root;
        rightMost.prev = null;
        
        return rightMost;
    }
    
    /**
     * Melds/combines two Pairing heap priority queues
     */
    public void meld(PairingHeapMinPQ<Key> other) {
        root = link(root, other.root);
        size += other.size();
        other.clear();
    }
    
    
    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * in ascending order.
     * The iterator doesn't implement remove() since it's optional.
     *
     * @return an iterator that iterates over the keys in ascending order
     */
    public Iterator<Key> iterator() {
        return new PairingHeapIterator();
    }
    
    private class PairingHeapIterator implements Iterator<Key> {
        private PairingHeapMinPQ<Key> copy;
        
        public PairingHeapIterator() {
            if (comparator == null) copy = new PairingHeapMinPQ<Key>();
            else copy = new PairingHeapMinPQ<Key>(comparator);
            if (root == null) return;
            
            // uses DFS to insert all the keys of the current heap
            Stack<Node> stack = new Stack<Node>();
            copy.insert(root.key);
            if (root.child != null) stack.push(root.child);
            while (!stack.isEmpty()) {
                Node curr = stack.pop();
                copy.insert(curr.key);
                if (curr.child != null) stack.push(curr.child);
                if (curr.next != null) stack.push(curr.next);
            }
        }
        
        public boolean hasNext() { return !copy.isEmpty(); }
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
    
    /**
     * Returns a string of the keys in the priority queue in the order of an 
     * in order traversal.
     * 
     * @return a string of the keys in the priority queue in the order of an 
     *         in order traversal
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // DFS
        Stack<Node> stack = new Stack<Node>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            sb.append(curr.key + " ");
            if (curr.child != null) stack.push(curr.child);
            if (curr.next != null) stack.push(curr.next);
        }
        return sb.toString();
    }
}
