package class01;

public class Code03_ParenthesesDeep {

	public static boolean isValid(char[] str) {
		if (str == null || str.length == 0) {
			return false;
		}
		int status = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ')' && str[i] != '(') {
				return false;
			}
			if (str[i] == ')' && --status < 0) {
				return false;
			}
			if (str[i] == '(') {
				status++;
			}
		}
		return status == 0;
	}

	/**
	 * 求有效括号的嵌套层数：
	 * 如
	 * （（））：2
	 * （（（）））：3
	 * @param s
	 * @return
	 */
	public static int deep(String s) {
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		int count = 0;
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				max = Math.max(max, ++count);
			} else {
				count--;
			}
		}
		return max;
	}

	/**
	 * 最长有效子串问题：依次以某个字符为开头/结尾，求可能的子串
	 * 这样思考的原因是为了使用动态规划
	 * @param s
	 * @return
	 */
	public static int maxLength(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		int[] dp = new int[str.length];
		int pre = 0;
		int ans = 0;
		// dp[0] = 0;
		for (int i = 1; i < str.length; i++) {
			if (str[i] == ')') {
				//当遇到')'时，求前面一个配对的'(',但需要跳过已经配对成功的
				pre = i - dp[i - 1] - 1; // 与str[i]配对的左括号的位置 pre
				if (pre >= 0 && str[pre] == '(') {
					//前面已经配对成功的，加上这次的一组，需要加上再前面配对成功的
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
				}
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	public static void main(String[] args) {
		String test = "((()))";
		System.out.println(deep(test));

	}

}
