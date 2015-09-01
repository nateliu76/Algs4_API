import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexPairingHeapMinPQ<Key extends Comparable<Key>> implements 
                                                          Iterable<Integer> {
    private int size;
    private int maxN;
    private Node root;
    private Node[] nodes; // allows constant time access to Nodes via index
    
    public class Node {
        private Key key;
        private int idx;
        private Node child;
        private Node next;    // next sibling
        private Node prev;    // previous sibling
        
        public Node(int i, Key k) {
            idx = i;
            key = k;
        }
    }
    
    // constructors
    public IndexPairingHeapMinPQ(int N) {
        if (N < 0) throw new IllegalArgumentException();
        maxN = N;
        nodes = new IndexPairingHeapMinPQ.Node[maxN];
        size = 0;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public boolean contains(int i) {
        return nodes[i] != null;
    }
    
    public int size() {
        return size;
    }
    
    public void clear() {
        nodes = new IndexPairingHeapMinPQ.Node[maxN];
        root = null;
        size = 0;
    }
    
    public void insert(int i, Key k) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        
        Node n = new Node(i, k);
        nodes[i] = n;
        if (root == null) root = n;
        else root = link(root, n);
        size++;
    }
    
    public int minIndex() {
        return root.idx;
    }
    
    public Key minKey() {
        return root.key;
    }
    
    public int delMin() {
        if (isEmpty()) throw new NullPointerException();
        int i = root.idx;
        if (size == 1) {
            clear();
            return i;
        }
        root = meldChildren(root);
        size--;
        nodes[i] = null;
        return i;
    }
    
    public Key keyOf(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        return nodes[i].key;
    }
    
    public void changeKey(int i, Key k) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int c = k.compareTo(nodes[i].key);
        if (c > 0) increaseKey(i, k);
        else if (c < 0) decreaseKey(i, k);
    }
    
    public void decreaseKey(int i, Key k) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int c = k.compareTo(nodes[i].key);
        if (c <= 0) throw new IllegalArgumentException();
        
        detach(nodes[i]);
        nodes[i].key = k;
        root = link(root, nodes[i]);
    }
    
    public void increaseKey(int i, Key k) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        // change the value of the node and meld its children and insert
        nodes[i].key = k;
        Node childrenRoot = meldChildren(nodes[i]);
        nodes[i].child = null;
        root = link(root, childrenRoot);
    }
    
    public void delete(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (i == root.idx) {
            delMin();
            return;
        }
        detach(nodes[i]);
        Node childrenRoot = meldChildren(nodes[i]);
        nodes[i] = null;
        root = link(root, childrenRoot);
    }
    
    // the comparison link method to meld heaps/nodes
    private Node link(Node n1, Node n2) {
        if (n1 == null) return n2;
        else if (n2 == null) return n1;
        else if (n1 == n2) return n1;
        
        int c = n1.key.compareTo(n2.key);
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
    
    private Node meldChildren(Node n) {
        // if no child or single child, return child
        if (n.child == null || n.child.next == null)
            return n.child;
        
        // first pass, left to right, combine every two subheaps
        Node curr = n.child;
        Node tempNext = curr.next.next;
        curr = link(curr, curr.next);
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
        Node rightMost = tempNext == null ? tempPrev : tempNext;
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
    
    
    private boolean isLeftChild(Node n) {
        if (n == null || n.prev == null) return false;
        return n.prev.child == n;
    }
    
    // remove node from its siblings (or parent) to form seperate heap
    private void detach(Node n) {
        if (n == root) return;
        if (isLeftChild(n)) {
            Node parent = n.prev;
            parent.child = n.next;
            if (n.next != null) n.next.prev = parent;
        } 
        else {
            Node previous = n.prev;
            previous.next = n.next;
            if (n.next != null) n.next.prev = previous;
        }
        n.next = null;
        n.prev = null;
    }
    
    
    public void meld(IndexPairingHeapMinPQ<Key> other) {
        root = link(root, other.root);
        size += other.size();
        other.clear();
    }
    
    
    // returns iterator that iterates over elements in sorted order
    public Iterator<Integer> iterator() {
        return new PairingHeapIterator();
    }
    
    // time to construct iterator is O(n) since n insertions are done
    private class PairingHeapIterator implements Iterator<Integer> {
        private IndexPairingHeapMinPQ<Key> copy;
        
        public PairingHeapIterator() {
            copy = new IndexPairingHeapMinPQ<Key>(maxN);
            if (root == null) return;
            
            for (int i = 0; i < maxN; i++) {
                if (nodes[i] != null) copy.insert(i, nodes[i].key);
            }
        }
        
        public boolean hasNext() { return !copy.isEmpty(); }
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // pre-order traversal
        Stack<Node> stack = new Stack<Node>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            sb.append(curr.idx + " ");
            if (curr.child != null) stack.push(curr.child);
            if (curr.next != null) stack.push(curr.next);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    }
}
