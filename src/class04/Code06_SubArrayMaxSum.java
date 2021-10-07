package class04;

/**
 * 可以用假设答案发，来设计流程
 * 假设i->k位置是最大子数组和。那么x->i-1位置的累加和一定是负数，i->y(y<k)位置的累加和一定是正数
 * 所以设计变量curr，收集当前的累加和，每收集一个数，就更新一下sum。如果curr<0了，这一段的收集就放弃了，将curr置零，从下面一个数开始继续收集
 */
public class Code06_SubArrayMaxSum {

	public static int maxSum(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int i = 0; i < arr.length; i++) {
			cur += arr[i];
			max = Math.max(max, cur);
			cur = cur < 0 ? 0 : cur;
		}
		return max;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr1 = { -2, -3, -5, 40, -10, -10, 100, 1 };
		System.out.println(maxSum(arr1));

		int[] arr2 = { -2, -3, -5, 0, 1, 2, -1 };
		System.out.println(maxSum(arr2));

		int[] arr3 = { -2, -3, -5, -1 };
		System.out.println(maxSum(arr3));

	}

}
