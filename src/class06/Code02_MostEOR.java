package class06;

import java.util.HashMap;

/**
 * 求数组中，最多异或和为0的切割方案下，一共多少块的异或和为0
 * 思路：采用动态规划
 * dp[i]:如果数组长度只到i，这时候最多有多少块的异或和为0：
 * 1）i所在的那一块，异或和不为0，所以dp[i] = dp[i-1]
 * 2) i所在的那一块，异或和为0，那需要知道那一块从哪里开始，假设从pre开始。因为a^0=a;所以0->pre的异或和等于0->i的异或和
 * 假设0->i的异或和为sum，只要遍历数组，找出0到最后一个异或和为sum的位置。可以用map优化一下，key：sum，value：异或和为sum的最后一个下标
 * 这样就能快速找到pre，所以dp[i] = dp[pre] + 1 (这里的1代表pre->i的异或和为0，作为一个部分)
 * dp[i] = max('1)','2)')
 * dp[arr.length-1]就是题解
 *
 */
public class Code02_MostEOR {

	public static int mostEOR(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		//都是0表示没有异或和为0的部分
		int[] dp = new int[N]; // dp[i] = 0
		HashMap<Integer, Integer> map = new HashMap<>();
		//一个数也没有，异或和一定是0
		map.put(0, -1);
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			//来到了i位置，表示数组的长度为i
			sum ^= arr[i];
			if (map.containsKey(sum)) {
				//表示0->pre的异或和为sum，0->i的异或和也是sum
				int pre = map.get(sum);
				//pre=-1表示0->i的异或和为0，否则pre->i的异或和为0
				dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
			}
			if (i > 0) {
				//两者取最大值
				dp[i] = Math.max(dp[i - 1], dp[i]);
			}
			//更新sum的最后一个下标
			map.put(sum, i);
		}
		return dp[dp.length - 1];
	}

	// for test
	public static int comparator(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] eors = new int[arr.length];
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
			eors[i] = eor;
		}
		int[] mosts = new int[arr.length];
		mosts[0] = arr[0] == 0 ? 1 : 0;
		for (int i = 1; i < arr.length; i++) {
			mosts[i] = eors[i] == 0 ? 1 : 0;
			for (int j = 0; j < i; j++) {
				if ((eors[i] ^ eors[j]) == 0) {
					mosts[i] = Math.max(mosts[i], mosts[j] + 1);
				}
			}
			mosts[i] = Math.max(mosts[i], mosts[i - 1]);
		}
		return mosts[mosts.length - 1];
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 300;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int res = mostEOR(arr);
			int comp = comparator(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
