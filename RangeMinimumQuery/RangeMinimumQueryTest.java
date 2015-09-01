import junit.framework.TestCase;
import java.util.Random;
import java.util.Arrays;

public class RangeMinimumQueryTest extends TestCase {
    
    public void testRMQ() {
        // test array with BLOCK_SIZE == 1
        for (int k = 0; k < 10; k++) {
            int[] nums = randomArray(7);
            RangeMinimumQuery rmq = new RangeMinimumQuery(nums);
            for (int i = 0; i < nums.length; i++) {
                for (int j = i; j < nums.length; j++) {
                    int a = rmq.minIdx(i, j);
                    int b = minIdx(nums, i, j);
                    assertEquals(nums[a], nums[b]);
                }
            }
        }
        
        // test array with BLOCK_SIZE == 2
        int[] nums1 = {2, 4, 3, 1, 6, 7, 8, 9, 1, 7};
        RangeMinimumQuery rmq1 = new RangeMinimumQuery(nums1);
        for (int i = 0; i < nums1.length; i++) {
            for (int j = i; j < nums1.length; j++) {
                int a = rmq1.minIdx(i, j);
                int b = minIdx(nums1, i, j);
                assertEquals(nums1[a], nums1[b]);
            }
        }
        
        // test array with BLOCK_SIZE == 3
        int[] nums3 = {35, 46, 43, 26, 43, 50, 35, 32, 38, 49, 49, 9, 4, 30,
            41, 11, 37, 42, 17, 24, 4, 6, 30, 32, 41, 14, 34, 23, 12, 27, 47, 
            12, 49, 22};
        RangeMinimumQuery rmq3 = new RangeMinimumQuery(nums3);
        for (int i = 0; i < nums3.length; i++) {
            for (int j = i; j < nums3.length; j++) {
                int a = rmq3.minIdx(i, j);
                int b = minIdx(nums3, i, j);
                assertEquals(nums3[a], nums3[b]);
            }
        }
        
        // test array with BLOCK_SIZE == 4
        int[] nums4 = {995, 509, 257, 957, 346, -523, 228, 690, 393, 216,
            -329, 742, 829, -355, 396, 4, 83, 667, 541, 117, 107,
            721, 673, 846, 575, 365, 770, 86, -259, 815, 11, -286, -134, 671,
            -523, -62, -201, 619, 247, 459, -862, -461, 257, -264, 178, 589,
            132, -375, 758, -143, 377, -936, -702, 161, 247, -509, 909, 403,
            368, -994, -233, 794, -594, 724, -923, 189, -643, 906, -149, -777,
            -999, 730, 634, -476, 0, -26, 789, 431, -114, -147, 410, 195, -552,
            254, 678, -894, 927, -668, 406, 425, -464, -2, 93, 342, -557, 35, 
            242, 995, -598, 587, 449, 216, -807, 431, 923, 812, -614, 532, 544,
            -931, 448, -572, 757, 519, -305, -893, -858, 869, 879, 374, 639, 
            -519, -925, 107, 114, -173, -211, 471, -948, 609, -124, 409, -426, 
            922, -969, -639, 784, -581, 379, -937, 527, -78, -202, 193, -127, 
            613, -449, -827, -449, -497, 27, 246, -421, -143, -961, -827, -752,
            -355, -177, -67, -171, 584, -40, -573, 60, 172, 541, -211, -139, 
            -743, 213, -25, 118, 430, -756, 907, -893, 242, -319, 560, 967, 
            341, -612, 447, -611, -889, 515, -325, -7, 352, 506, -694, -445, 
            472, 557, 662, 924, -245, -622, 797, 756, -730};
        RangeMinimumQuery rmq4 = new RangeMinimumQuery(nums4);
        for (int i = 0; i < nums4.length; i++) {
            for (int j = i; j < nums4.length; j++) {
                int a = rmq4.minIdx(i, j);
                int b = minIdx(nums4, i, j);
                assertEquals(nums4[a], nums4[b]);
            }
        }
        
        // test arrays with random numbers generated
        for (int k = 0; k < 5; k++) {
            int[] nums = randomArray(1200);
            RangeMinimumQuery rmq = new RangeMinimumQuery(nums);
            for (int i = 0; i < nums.length; i++) {
                for (int j = i; j < nums.length; j++) {
                    int a = rmq.minIdx(i, j);
                    int b = minIdx(nums, i, j);
                    assertEquals(nums[a], nums[b]);
                }
            }
        }
        
        for (int k = 0; k < 20; k++) {
            Random rnd = new Random();
            int len = (rnd.nextInt() % 2100 + 2100) % 2100;
            int[] nums = randomArray(len);
            RangeMinimumQuery rmq = new RangeMinimumQuery(nums);
            for (int i = 0; i < nums.length; i++) {
                for (int j = i; j < nums.length; j++) {
                    int a = rmq.minIdx(i, j);
                    int b = minIdx(nums, i, j);
                    assertEquals(nums[a], nums[b]);
                }
            }
        }
        
        // tests that targets the final incomplete block
        int[] nums6 = {995, 509, 257, 957, 346, -523, 228, 690, 393, 216,
            -329, 742, 829, -355, 396, 4, 83, 667, 541, 117, 107,
            721, 673, 846, 575, 365, 770, 86, -259, 815, 11, -286, -134, 671,
            -523, -62, -201, 619, 247, 459, -862, -461, 257, -264, 178, 589,
            132, -375, 758, -143, 377, -936, -702, 161, 247, -509, 909, 403,
            368, -994, -233, 794, -594, 724, -923, 189, -643, 906, -149, -777,
            -999, 730, 634, -476, 0, -26, 789, 431, -114, -147, 410, 195, -552,
            254, 678, -894, 927, -668, 406, 425, -464, -2, 93, 342, -557, 35, 
            242, 995, -598, 587, 449, 216, -807, 431, 923, 812, -614, 532, 544,
            -931, 448, -572, 757, 519, -305, -893, -858, 869, 879, 374, 639, 
            -519, -925, 107, 114, -173, -211, 471, -948, 609, -124, 409, -426, 
            922, -969, -639, 784, -581, 379, -937, 527, -78, -202, 193, -127, 
            613, -449, -827, -449, -497, 27, 246, -421, -143, -961, -827, -752,
            -355, -177, -67, -171, 584, -40, -573, 60, 172, 541, -211, -139, 
            -743, 213, -25, 118, 430, -756, 907, -893, 242, -319, 560, 967, 
            341, -612, 447, -611, -889, 515, -325, -7, 352, 506, -694, -445, 
            472, 557, 662, 924, -245, -622, 797, 756, -730, -1000};
        RangeMinimumQuery rmq6 = new RangeMinimumQuery(nums6);
        for (int i = 0; i < nums6.length; i++) {
            for (int j = i; j < nums6.length; j++) {
                int a = rmq6.minIdx(i, j);
                int b = minIdx(nums6, i, j);
                assertEquals(nums6[a], nums6[b]);
            }
        }
        
        for (int k = 0; k < 1; k++) {
            int[] nums = randomArray(1204);
            nums[1201] = Integer.MIN_VALUE;
            nums[1202] = Integer.MIN_VALUE + 1;
            nums[1203] = Integer.MIN_VALUE + 2;
            RangeMinimumQuery rmq = new RangeMinimumQuery(nums);
            for (int i = 0; i < nums.length; i++) {
                for (int j = i; j < nums.length; j++) {
                    int a = rmq.minIdx(i, j);
                    int b = minIdx(nums, i, j);
                    assertEquals(nums[a], nums[b]);
                }
            }
        }
        
        // test with arrays that are already sorted
        for (int k = 0; k < 3; k++) {
            int[] nums = randomArray(1200);
            Arrays.sort(nums);
            RangeMinimumQuery rmq = new RangeMinimumQuery(nums);
            for (int i = 0; i < nums.length; i++) {
                for (int j = i; j < nums.length; j++) {
                    int a = rmq.minIdx(i, j);
                    int b = minIdx(nums, i, j);
                    assertEquals(nums[a], nums[b]);
                }
            }
        }
    }
    
    
    // the way this is executed unfortunately makes the test for
    // the entire RMQ to O(n^3) in terms of timing
    // another option is to use DP to reduce it to O(n^2)
    private int minIdx(int[] nums, int i, int j) {
        if (i == j) return i;
        int minVal = nums[i];
        int minI = i;
        for (int k = i; k <= j; k++) {
            if (nums[k] < minVal) {
                minVal = nums[k];
                minI = k;
            }
        }
        return minI;
    }
    
    private int[] randomArray(int size) {
        int[] rand = new int[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i++) 
            rand[i] = rnd.nextInt();
        return rand;
    }
    
}
