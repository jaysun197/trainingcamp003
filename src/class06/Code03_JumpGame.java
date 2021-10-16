package class06;

/**
 * 从0开始往右跳，每步最大能跳arr[i]个长度，问几步能跳到最右边
 * 思路：动态规划，从左往右的尝试模型
 * 准备3个变量：
 * int step： 跳了多少步
 * int cur：目前能到达的最右边界
 * int next：下一步能到达的最右边界，至少比cur大1
 * 遍历数组，来到了i位置，
 * 如果i>cur,表示，需要再跳一步了，就再跳一步，cur就来到next的位置
 * 如果i<=cur,更新next就可以了
 */
public class Code03_JumpGame {

	public static int jump(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int step = 0; // 跳了多少步
		int cur = 0; // step步内，右边界
		int next = 0;// step+1步内，右边界
		for (int i = 0; i < arr.length; i++) {
			if (cur < i) {
				step++;
				cur = next;
			}
			next = Math.max(next, i + arr[i]);
		}
		return step;
	}

	public static void main(String[] args) {
		int[] arr = { 3, 2, 3, 1, 1, 4 };
		System.out.println(jump(arr));

	}

}
