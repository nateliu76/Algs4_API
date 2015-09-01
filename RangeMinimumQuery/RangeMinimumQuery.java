/******************************************************************************
 * Compilation:  javac RangeMinimumQuery.java
 * Execution:    java RangeMinimumQuery input.txt
 * Dependencies: CartesianTree.java StdIn.java In.java
 * Data files:   http://algs4.cs.princeton.edu/11model/tinyT.txt
 *  
 * A data structure that allows constant time lookup for the range minimum
 * queries while using O(n) memory and preprocessing time.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;

/**
 * The RangeMinimumQuery class represents a data structure that supports
 * queries for finding the index of the range minimum.
 * 
 * Each lookup takes O(1) time, while the preprocessing of the original 
 * array takes O(n) time and space.
 * This is done by making a few reductions:
 * 1. Reduce problem to LCA (by using a Cartesian Tree).
 * 2. Reduce LCA problem to restricted RMQ (with Eulerian tour of the Cartesian Tree).
 * 
 * Due to the characteristics of the restricted RMQ, we can build a sparse
 * table and a lookup table in O(n) time and space.
 * The tables support constant time queries of the range minimum.
 * 
 * @author Nate Liu
 */
public class RangeMinimumQuery {
    
    private final int[] nums;
    private final int[] numsToTour;
    private final int[] tourToNums;
    private final int[] tour;
    
    private int[] blockMinVal;
    private int[] blockMinIdx;
    private int[] blockToLT;
    
    private int[][] ST;      // sparse table
    private int[][][] LT;    // lookup table
    private int BLOCK_SIZE;
    
    /**
     * Obtains the Eulerian tour array from the CartesianTree class, and uses
     * it to build the Sparse table and lookup table.
     * 
     * @param A the array to perform Range minimum queries on
     */
    public RangeMinimumQuery(int[] A) {
        nums = Arrays.copyOf(A, A.length);
        
        CartesianTree cTree = new CartesianTree(nums);
        numsToTour = cTree.numsToTourIndexMapping();
        tourToNums = cTree.tourToNumsIndexMapping();
        tour = cTree.tourArray();
        
        storeMinOfBlocks();
        buildSparseTable();
        buildLookupTable();
        generateBlockKeys();
    }
    
    // Divides the Eulerian tour array into blocks of size 1/2*log(n)
    // stores the min of each block and its index for later use when building
    // sparse table
    private void storeMinOfBlocks() {
        BLOCK_SIZE = log2(tour.length) / 2;
        if (BLOCK_SIZE < 1) BLOCK_SIZE = 1;
        int len = tour.length / BLOCK_SIZE;
        blockMinVal = new int[len];
        blockMinIdx = new int[len];
        
        for (int k = 0, j = 0; j < len; k += BLOCK_SIZE, j++) {
            int min = tour[k];
            int minIdx = k;
            for (int i = k; i < k + BLOCK_SIZE; i++) {
                if (tour[i] < min) {
                    min = tour[i];
                    minIdx = i;
                }
            }
            blockMinVal[j] = min;
            blockMinIdx[j] = minIdx;
        }
    }
    
    /**
     * Uses dynamic programming to build sparse table
     * ST[i][j] stores the index of the range minimum that starts at i and 
     * has range 2^(j)
     * Ex: ST[3][5] stores index of range minimum that starts at 3 and ends
     * at 3 + 2^5 = 35
     * 
     * Recurrence is the argmin of the new range is the argmin of the two
     * smaller ranges (that are half the new range's size)
     * ST[i][j] = argmin(ST[i][j - 1], ST[i + 2^(j - 1)][j - 1])
     * 
     * Sparse table has dimensions:
     * (# of blocks) * log(# of blocks)
     * Therefore it takes up space:
     * = O(2n / log(n)) * O(log(1 / 2 * log(n)))
     * = O(n)
     */
    private void buildSparseTable() {
        int maxLen = log2(blockMinIdx.length);
        ST = new int[blockMinIdx.length][maxLen + 1];
        
        // initialize mins of intervals with length of 1
        for (int i = 0; i < ST.length; i++) 
            ST[i][0] = i;
        
        for (int j = 1, k = 2; j < ST[0].length; j++, k <<= 1) {
            for (int i = 0; i + k <= ST.length; i++) {
                int m = i + (k >> 1);
                if (blockMinVal[ST[i][j - 1]] <= blockMinVal[ST[m][j - 1]])
                    ST[i][j] = ST[i][j - 1];
                else
                    ST[i][j] = ST[m][j - 1];
            }
        }
    }
    
    /**
     * Builds lookup table
     * While the sparse table stores the argmin for each entire block, the 
     * lookup table stores the argmin for each range within a block
     * 
     * Since the difference of consecutive integers is restricted to +1/-1,
     * there are 2^BLOCK_SIZE possible types of sequences (if only considering
     * the relationship between the consecutive elements and not the absolute
     * value)
     * 
     * The lookup table stores all the possible sequences and ranges for 
     * constant time queries
     * 
     * The lookup table has dimensions:
     * (# of possible sequences) * (possible indices) * (possible indices)
     * Therefore it takes up space
     * = O(2^(BLOCK_SIZE)) * O(BLOCK_SIZE^2)
     * = O(2^(1 / 2 * log(n)) * O((1 / 2 * log(n))^2)
     * = O(sqrt(n) * log(n)^2)
     * = o(n)
     */
    private void buildLookupTable() {
        if (BLOCK_SIZE == 1) return;
        
        // len is equivalent to sqrt(n) or 2 ^ (1 / 2 * log(n))
        int len = 1 << (BLOCK_SIZE - 1);
        LT = new int[len + 1][BLOCK_SIZE][BLOCK_SIZE];
        
        // Sequence represented as the binary form of an integer, starting 
        // from the LSB
        // 1 represents +1 (ascend), 0 represents -1 (descend)
        // Ex: 1 = 1011 (base 2) = ascend ascend descend ascend
        // and corresponds to example sequence {0, 1, 2, 1, 2}
        for (int i = 0; i < len; i++) {
            // a sequence is generated to help find the argmin
            int[] height = generateHeight(i);
            
            for (int j = 0; j < BLOCK_SIZE; j++) {
                for (int k = j; k < BLOCK_SIZE; k++) {
                    if (j == k)
                        LT[i][j][k] = j;
                    else if (height[k] < height[LT[i][j][k - 1]]) 
                        LT[i][j][k] = k;
                    else
                        LT[i][j][k] = LT[i][j][k - 1];
                }
            }
        }
        
        // generate lookup table for last block that's smaller than BLOCK_SIZE
        if (tour.length % BLOCK_SIZE != 0) {
            int last = len;
            int start = tour.length - tour.length % BLOCK_SIZE;
            for (int j = 0; j + start < tour.length; j++) {
                for (int k = j; k + start < tour.length; k++) {
                    if (j == k)
                        LT[last][j][k] = j;
                    else if (tour[k + start] < tour[LT[last][j][k - 1] + start])
                        LT[last][j][k] = k;
                    else
                        LT[last][j][k] = LT[last][j][k - 1];
                }
            }
        }
    }
    
    // Generates the key to the lookup table for each block
    private void generateBlockKeys() {
        if (BLOCK_SIZE == 1) return;
        
        blockToLT = new int[blockMinIdx.length + 1];
        for (int i = 0, k = 0; k < blockToLT.length - 1; i += BLOCK_SIZE, k++) {
            int key = 0;
            for (int j = i + 1; j < i + BLOCK_SIZE; j++) {
                // compare to previous and make int
                if (tour[j] > tour[j - 1]) 
                    key += (1 << (j - i - 1));
            }
            blockToLT[k] = key;
        }
        // key for last (incomplete) block
        blockToLT[blockToLT.length - 1] = LT.length - 1;
    }
    
    // Generates an array with values based on the integer's binary sequence
    // 1 represents +1 (ascend), 0 represents -1 (descend)
    // The LSB corresponds to height[0] and height[1]'s relationship
    // The MSB corresponds to the relationship of the last two values in the array
    private int[] generateHeight(int n) {
        int[] height = new int[BLOCK_SIZE];
        for (int i = 1; i < BLOCK_SIZE; i++) {
            boolean ascend = ((1 << (i - 1)) & n) > 0;
            if (ascend) height[i] = height[i - 1] + 1;
            else height[i] = height[i - 1] - 1;
        }
        return height;
    }
    
    // Computes floor of log base 2
    private int log2(int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }
    
    /**
     * Returns the index of the range minimum specified by a and b (inclusive).
     * a <= index of min value <= b.
     * 
     * @param a the start of the range (inclusive)
     * @param b the end of the range (inclusive)
     * @return the index of the range minimum specified by a, b
     * @throws IllegalArgumentException if indices are out of range or in 
     *         wrong order
     */
    public int minIdx(int a, int b) {
        if (a < 0 || b >= nums.length || b < a) 
            throw new IllegalArgumentException("invalid index/indices");
        if (a == b) return a;
        
        int start = Math.min(numsToTour[a], numsToTour[b]);
        int end = Math.max(numsToTour[a], numsToTour[b]);
        
        // find corresponding blocks for ST lookup
        int startBlock = start / BLOCK_SIZE;
        int endBlock = end / BLOCK_SIZE;
        
        // if range is in same block, one lookup in LT is enough
        if (endBlock == startBlock) {
            int j = start % BLOCK_SIZE;
            int k = end % BLOCK_SIZE;
            int idx = LT[blockToLT[endBlock]][j][k] + endBlock * BLOCK_SIZE;
            return tourToNums[idx];
        }
        
        // searches argmin of middle blocks
        int midMinIdx = minIdxMidBlocks(startBlock, endBlock, start, end);
        int midMinVal = Integer.MAX_VALUE;
        if (midMinIdx != -1) midMinVal = tour[midMinIdx];
        
        // searches argmin of left side of middle blocks
        int leftMinIdx = minIdxLeftSide(startBlock, start);
        int leftMinVal = Integer.MAX_VALUE;
        if (leftMinIdx != -1) leftMinVal = tour[leftMinIdx];
        
        // searches argmin of right side of middle blocks
        int rightMinIdx = minIdxRightSide(endBlock, end);
        int rightMinVal = Integer.MAX_VALUE;
        if (rightMinIdx != -1) rightMinVal = tour[rightMinIdx];
        
        // finds argmin of the three
        int minIdx;
        if (leftMinVal <= rightMinVal && leftMinVal <= midMinVal) 
            minIdx = leftMinIdx;
        else if (rightMinVal <= leftMinVal && rightMinVal <= midMinVal) 
            minIdx = rightMinIdx;
        else
            minIdx = midMinIdx;
        
        return tourToNums[minIdx];
    }
    
    // Find argmin from sparse table
    private int minIdxMidBlocks(int startBlock, int endBlock, 
                                int start, int end) {
        int minIdx = -1;
        int startST = startBlock;
        if (start % BLOCK_SIZE != 0) startST++;
        int endST = endBlock;
        if (end % BLOCK_SIZE != BLOCK_SIZE - 1) endST--;
        
        if (startST <= endST) {
            int range = 0;
            if (startST != endST) range = log2(endST - startST);
            
            int start1 = startST;
            int idx1 = blockMinIdx[ST[start1][range]];
            int start2 = endST - ((1 << range) - 1);
            int idx2 = blockMinIdx[ST[start2][range]];
            
            if (tour[idx1] > tour[idx2]) minIdx = idx2;
            else minIdx = idx1;
        }
        return minIdx;
    }
    
    // Find argmin from lookup table
    private int minIdxLeftSide(int startBlock, int start) {
        int leftIdx = -1;
        if (start % BLOCK_SIZE != 0) {
            int j = start % BLOCK_SIZE;
            int k = BLOCK_SIZE - 1;
            leftIdx = LT[blockToLT[startBlock]][j][k] + startBlock * BLOCK_SIZE;
        }
        return leftIdx;
    }
    
    // Find argmin from lookup table
    private int minIdxRightSide(int endBlock, int end) {
        int rightIdx = -1;
        if (end % BLOCK_SIZE != BLOCK_SIZE - 1) {
            int j = 0;
            int k = end % BLOCK_SIZE;
            rightIdx = LT[blockToLT[endBlock]][j][k] + endBlock * BLOCK_SIZE;
        }
        return rightIdx;
    }
    
    /**
     * Allows user to query index of range minimum from a text file with 
     * integers
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] arr = in.readAllInts();
        RangeMinimumQuery rmq = new RangeMinimumQuery(arr);
        System.out.println("valid index range: " + 0 + " to " + (arr.length - 1));
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            int idx = rmq.minIdx(i, j);
            System.out.println("index: "+ idx + " value: "+arr[idx]);
        }
    }
}
