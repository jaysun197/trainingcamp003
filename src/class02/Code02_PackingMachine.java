package class02;

public class Code02_PackingMachine {

	/**
	 * 贪心算法，背诵即可：
	 * 设来到的i位置，左侧已有的物品数-左侧实际需要的物品数为a，右侧已有的物品数-右侧实际需要的物品数为b
	 * 当a>0,b>0;a<0,b>0;a>0,b<0时，需要的轮数为max(|a|,|b|)
	 * 当a<0,b<0时，需要的轮数为|a|+|b|
	 * 依次遍历每个位置，求全局最大轮数，就是题目的解。
	 *
	 * 需要一个预处理数组：前缀和数组，可以用一个变量代替
	 * @param arr
	 * @return
	 */
	public static int MinOps(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int size = arr.length;
		//准备一个总和
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += arr[i];
		}
		//不能够均分，肯定无解
		if (sum % size != 0) {
			return -1;
		}
		int avg = sum / size;
		//遍历的时候累加i位置的值，可以得到i位置的前缀和
		int leftSum = 0;
		int ans = 0;
		// 每个位置都求各自的
		for (int i = 0; i < arr.length; i++) {
			// i号机器，是中间机器，左(0~i-1) i 右(i+1~N-1)
			// 负 需要输入     正需要输出 
			int leftRest = leftSum - i * avg; // a-b
			// 负 需要输入     正需要输出 
			// c - d
			// sum - leftSum - arr[i]：i位置右边的前缀和
			int rightRest =  (sum - leftSum - arr[i]) -  (size - i - 1) * avg; 
			if (leftRest < 0 && rightRest < 0) {
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			} else {
				ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			}
			leftSum += arr[i];
		}
		return ans;
	}

}
