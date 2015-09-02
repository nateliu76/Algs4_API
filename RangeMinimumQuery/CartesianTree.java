/******************************************************************************
 * Compilation:  javac CartesianTree.java
 * Execution:    
 * Dependencies: Stack.java
 *  
 * A Cartesian tree data structure.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

/**
 * The CartesianTree class is a structure with mulitple applications. 
 * One of which is reducing the Range Minimum Query problem to the Lowest 
 * Common Ancestor problem
 * An Eulerian tour of the Cartesian tree reduces the Lowest Common Ancestor
 * problem to the restricted Range Minimum Query problem, which allows
 * O(n) memory and preprocessing time with O(1) lookups afterwards
 * Sequence is: 
 * 1. RMQ array
 * 2. Reduce to LCA (by using Cartesian Tree)
 * 3. Reduce to restricted RMQ (with Eulerian tour of Cartesian Tree)
 * 
 * Unlike many other tree implementations where it uses a node with pointers 
 * to children or parents, this implementation uses an arrays that store
 * the indices of the key's parent, right child, and left child
 * 
 * Each method in the API takes O(n) time since a defensive copy is made and
 * returned to the user
 * 
 * @author Nate Liu
 */
public class CartesianTree {
    
    private final int[] nums;
    
    private int[] parent;
    private int[] left;
    private int[] right;
    private int root;
    
    private int[] numsToTour;
    private int[] tourToNums;
    private int[] tour;
    
    /**
     * Builds Cartesian tree and Eulerian tour array
     * 
     * @param A the array to form the Cartesian tree with
     */
    public CartesianTree(int[] A) {
        nums = Arrays.copyOf(A, A.length);
        
        parent = new int[nums.length];
        left = new int[nums.length];
        right = new int[nums.length];
        Arrays.fill(left, -1);
        Arrays.fill(right, -1);
        
        buildCartesianTree();
        buildTourArray();
    }
    
    /**
     * Return copy of the Eulerian tour array
     * 
     * @return copy of the Eulerian tour array
     */
    public int[] tourArray() {
        return Arrays.copyOf(tour, tour.length);
    }
    
    /**
     * Return copy of index mapping from the original array to the Eulerian
     * tour array
     * 
     * @return copy of index mapping from the original array to the eulerian
     *         tour array
     */
    public int[] numsToTourIndexMapping() {
        return Arrays.copyOf(numsToTour, numsToTour.length);
    }
    
    /**
     * Return copy of index mapping from the Eulerian tour array to the 
     * original array
     * 
     * @return copy of index mapping from the Eulerian tour array to the 
     *         original array
     */
    public int[] tourToNumsIndexMapping() {
        return Arrays.copyOf(tourToNums, tourToNums.length);
    }
    
    
    // Constructs Cartesian tree by using the All nearest smaller value method,
    // see: https://en.wikipedia.org/wiki/All_nearest_smaller_values
    private void buildCartesianTree() {
        Stack<Integer> stack = new Stack<Integer>();
        int min = nums[0];
        int minIdx = 0;
        
        // insert one element per iteration, starting from the left of the 
        // array, mark its parent and children in the same iteration
        for (int i = 0; i < nums.length; i++) {
            int prev = -1;
            // pop all larger elements off the stack
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                prev = stack.pop();
            }
            // mark parent for new element
            if (!stack.isEmpty()) parent[i] = stack.peek();
            // mark parent for last popped off element
            if (prev != -1) parent[prev] = i;
            // put new element onto top of stack
            stack.push(i);
            
            // track min element index since it is the root of the tree
            if (nums[i] < min) {
                min = nums[i];
                minIdx = i;
            }
        }
        // mark root
        parent[minIdx] = -1;
        root = minIdx;
        
        // build children arrays
        for (int i = 0; i < nums.length; i++) {
            if (parent[i] == -1) continue;
            else if (parent[i] > i) left[parent[i]] = i;
            else if (parent[i] < i) right[parent[i]] = i;
        }
    }
    
    private void buildTourArray() {
        numsToTour = new int[nums.length];
        Arrays.fill(numsToTour, -1);
        int tourSize = 2 * nums.length - 1;
        tour = new int[tourSize];
        tourToNums = new int[tourSize];
        
        // Eulerian tour implemented in an iterative DFS fashion
        boolean[] leftVisited = new boolean[parent.length];
        boolean[] rightVisited = new boolean[parent.length];
        Stack<Integer> stack = new Stack<Integer>();
        int idx = 0;
        stack.push(root);
        stack.push(0);
        while (!stack.isEmpty()) {
            int depth = stack.pop();
            int pos = stack.pop();
            
            if (numsToTour[pos] == -1) numsToTour[pos] = idx;
            tour[idx] = depth;
            tourToNums[idx] = pos;
            idx++;
            
            if (left[pos] != -1 && !leftVisited[pos]) {
                stack.push(pos);
                stack.push(depth);
                stack.push(left[pos]);
                stack.push(depth + 1);
                leftVisited[pos] = true;
            }
            else if (right[pos] != -1 && !rightVisited[pos]) {
                stack.push(pos);
                stack.push(depth);
                stack.push(right[pos]);
                stack.push(depth + 1);
                rightVisited[pos] = true;
            }
        }
    }
}
