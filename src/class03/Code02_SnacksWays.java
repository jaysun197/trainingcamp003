package class03;

/**
 * 一共有多少种方法？这是一个需要尝试的题目，就可以成为一个动态规划的题目
 * 解法一：
 * 从左往右尝试
 *
 */
public class Code02_SnacksWays {

	/**
	 * 解法一：
	 * 从左往右尝试
	 * 来到了第index个零食，还剩rest的空间。这时候可以选择index号零食要或者不要
	 * @param arr
	 * @param w
	 * @return
	 */
	public static int ways1(int[] arr, int w) {
		// arr[0...]
		return process(arr, 0, w);
	}

	// 从左往右的经典模型
	// 还剩的容量是rest，arr[index...]自由选择，
	// 返回选择方案
	// index ： 0～N
	// rest : 0~w
	// 只有index和rest两个变量，两个变量，一个返回值：二维dp
	public static int process(int[] arr, int index, int rest) {
		if (rest < 0) { // 没有容量了
			// -1 无方案的意思
			return -1;
		}
		// rest>=0,
		// 已经没有零食了，但是rest任然有空间。这就是一种方案
		if (index == arr.length) { // 无零食可选
			return 1;
		}
		// rest >=0
		// 有零食index
		// index号零食，要 or 不要
		// index, rest
		// (index+1, rest)
		// (index+1, rest-arr[i])
		int next1 = process(arr, index + 1, rest); // 不要
		int next2 = process(arr, index + 1, rest - arr[index]); // 要
		//next1不可能为负数，至少等于1，因为当前选择不要，后面都选择不要就至少是一种方案
		return next1 + (next2 == -1 ? 0 : next2);
	}

	/**
	 * 改成动态规划
	 * row: 来到的第几个零食，row=N时没有零食了
	 * column：剩余空间，column=w时，剩余空间满格
	 * @param arr
	 * @param w
	 * @return
	 */
	public static int ways2(int[] arr, int w) {
		int N = arr.length;
		//row: index, column: rest
		int[][] dp = new int[N + 1][w + 1];
		//index到N，就是指没有零食了，这时候rest>=0的情况下都是一种方案
		for (int j = 0; j <= w; j++) {
			dp[N][j] = 1;
		}
		//最后一行不用赋值了
		for (int i = N - 1; i >= 0; i--) {
			//都只是与下一行有关系，与本行无关
			for (int j = 0; j <= w; j++) {
				dp[i][j] = dp[i + 1][j] + ((j - arr[i] >= 0) ? dp[i + 1][j - arr[i]] : 0);
			}
		}
		return dp[0][w];
	}

	/**
	 * 第二种动态规划的方案：
	 * row：来到第几个零食，不用考虑没有零食的时候
	 * column：构成空间具体大小
	 * 值：方法数
	 * 思路： 来到第i个零食，需要凑到j个空间，就只要
	 * 在第i-1个零食的时候凑成j个空间（不要i零食） + 在第i-1个零食的时候凑成j-arr[i]个空间（要i零食，但i的空间不能比需要凑成的空间j大）
	 * 题解是来到第n-1个零食时，凑成0->w个空间的方案数之和
	 * @param arr
	 * @param w
	 * @return
	 */
	public static int ways3(int[] arr, int w) {
		int N = arr.length;
		int[][] dp = new int[N][w + 1];
		for (int i = 0; i < N; i++) {
			//需要构成空间为0的话，无论来到第几个零食，方案数都是1个，就是都不要。
			dp[i][0] = 1;
		}
		if (arr[0] <= w) {
			//来带第一个零食，方案为构成第一个零食的大小的空间，只要背包空间比第一个零食大，就只有一种方案
			dp[0][arr[0]] = 1;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= w; j++) {
				dp[i][j] = dp[i - 1][j] + ((j - arr[i]) >= 0 ? dp[i - 1][j - arr[i]] : 0);
			}
		}
		int ans = 0;
		for (int j = 0; j <= w; j++) {
			ans += dp[N - 1][j];
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] arr = { 4, 3, 2, 9 };
		int w = 8;
		System.out.println(ways1(arr, w));
		System.out.println(ways2(arr, w));
		System.out.println(ways3(arr, w));

	}

}
