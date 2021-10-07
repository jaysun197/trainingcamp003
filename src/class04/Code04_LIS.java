package class04;

/**
 * 最长递增子序列问题（严格递增）
 * 如[3,1,4,2,3] -> "123"
 */
public class Code04_LIS {

	/**
	 * 解法一：
	 * 遍历arr，来到i位置的时候，计算以arr[i]结尾的最长递增子序列，计入结果数组dp[i]中
	 * @param arr
	 * @return
	 */
	public static int[] lis1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] dp = getdp1(arr);
		return generateLIS(arr, dp);
	}

	//复杂度：O(N^2)
	public static int[] getdp1(int[] arr) {
		int[] dp = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			//以arr[i]结尾的最长递增子序列，至少为1
			dp[i] = 1;
			//遍历已经看过数字
			for (int j = 0; j < i; j++) {
				if (arr[i] > arr[j]) {
					//如果当前位置的数，比前面一个位置的数字arr[j]大，那该位置的最长子序列最少为j位置的最长子序列+1，
					//但也有可能i位置之前已经计算过一次特别长的子序列，比较取max
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
		}
		return dp;
	}

	public static int[] generateLIS(int[] arr, int[] dp) {
		int len = 0;
		int index = 0;
		for (int i = 0; i < dp.length; i++) {
			if (dp[i] > len) {
				len = dp[i];
				index = i;
			}
		}
		int[] lis = new int[len];
		lis[--len] = arr[index];
		for (int i = index; i >= 0; i--) {
			if (arr[i] < arr[index] && dp[i] == dp[index] - 1) {
				lis[--len] = arr[i];
				index = i;
			}
		}
		return lis;
	}

	/**
	 * 解法二
	 * 采用一个预处理数组 ends[] 含义：
	 * ends[i]: 所有最长递增子序列长度为i+1的最小结尾
	 * 遍历arr，来到i位置，到ends数组中找比arr[i]大最左的位置，比如j，
	 * 找到了更新ends[j]=arr[i],没找到，就在ends[]增加一个值为arr[i]
	 * dp[i] = j+1
	 * @param arr
	 * @return
	 */
	public static int[] lis2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] dp = getdp2(arr);
		return generateLIS(arr, dp);
	}

	public static int[] getdp2(int[] arr) {
		int[] dp = new int[arr.length];
		int[] ends = new int[arr.length];
		//最长子递增序列长度为1，首次的最小值就是arr的第一个数
		ends[0] = arr[0];
		dp[0] = 1;
		//right记录ends[]扩充到了那个位置
		int right = 0; // 0....right   right往右无效
		int l = 0;
		int r = 0;
		int m = 0;
		for (int i = 1; i < arr.length; i++) {
			l = 0;
			r = right;
			//ends[]中查找大于arr[i]最左的数
			while (l <= r) {
				m = (l + r) / 2;
				if (arr[i] > ends[m]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			// 没找到的话，l=right+1，扩充一下right
			// 找到了的话，l一定小于right
			right = Math.max(right, l);
			//更新ends[]
			ends[l] = arr[i];
			//最长子递增序列为l+1的最小结尾为arr[i],那以arr[i]结尾的最长递增子序列dp[i]=l+1
			dp[i] = l + 1;
		}
		return dp;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr = { 2, 1, 5, 3, 6, 4, 8, 9, 7 };
		printArray(arr);
		printArray(lis1(arr));
		printArray(lis2(arr));

	}
}