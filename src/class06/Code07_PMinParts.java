package class06;

/**
 * 给一个字符串str，问将str分割成若干个回文，最少可以分割成几个部分
 * 题解思路：动态规划：从左往右的尝试+范围的尝试
 * dp[i],以i位置为开头，最少能切成几个回文串
 * 1）i位置为一个回文，后面的再求解
 * 2）i->i+1位置为一个回文(如果是的话)，后面的再求解
 * ...
 * dp[N-1]=1,dp[0]就是题解
 * 复杂度：每个位置往后看N次，O(N^2),判断回文O(N)，总：O(N^3)
 *
 * 优化点：i->i+1位置为一个回文的判断，用一张isP预处理dp表，提前处理好从L...R位置是否是回文
 * isP[L][R]:从L..R位置是否是回文：str[L]==str[R] && isP[L+1][R-1]
 * 首先对角线位置表示一个字符时是否是回文，都是true
 * 然后看到isp[l][r]依赖左下角的位置，所以再右边一条对角线也需要填好
 * 第二对角线：l+1=r，仅有2个字符，只要判断str[l]==str[r]
 * 依次往右上角生成所有的数
 */
public class Code07_PMinParts {

	/**
	 * 暴力解
	 * @param s
	 * @return
	 */
	public static int minParts(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		if (s.length() == 1) {
			return 1;
		}
		return process(s,0);
	}

	public static int process(String s,int index){
		char[] str = s.toCharArray();
		int N = str.length;
		if (index==N){
			return 0;
		}
		int ans = Integer.MAX_VALUE;
		for (int end = index; end < N; end++) {
			if (isP(str,index,end)){
				ans= Math.min(ans,process(s,end+1));
			}
		}
		return ans;
	}

	public static boolean isP(char[] str,int L,int R){
		//遍历，返回是否回文
		return false;
	}

	/**
	 * 改成动态规划，并减一阶复杂度
	 * @param s
	 * @return
	 */
	public static int dp(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		if (s.length() == 1) {
			return 1;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		//提前生成一张预处理表，表示从L..R位置是否是回文
		boolean[][] isP = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			//对角线，都是一个字符
			isP[i][i] = true;
		}
		for (int i = 0; i < N - 1; i++) {
			//第二条对角线，只要看连个字符是否相等
			isP[i][i + 1] = str[i] == str[i + 1];
		}
		//
		for (int row = N - 3; row >= 0; row--) {
			for (int col = row + 2; col < N; col++) {
				//倒数第一行倒数第二行都填好了，列从第二条对角线后面一列开始
				isP[row][col] = str[row] == str[col] && isP[row + 1][col - 1];
			}
		}
		int[] dp = new int[N + 1];
		for (int i = 0; i <= N; i++) {
			dp[i] = Integer.MAX_VALUE;
		}
		dp[N] = 0;
		for (int i = N - 1; i >= 0; i--) {
			//来到i位置，表示从i位置开始，最少能切成几个回文
			for (int end = i; end < N; end++) {
				// 枚举i..end，作为一个回文，end..N再讨论
				if (isP[i][end]) {
					dp[i] = Math.min(dp[i], 1 + dp[end + 1]);
				}
			}
		}
		return dp[0];
	}

	public static void main(String[] args) {
		String test = "aba12321412321TabaKFK";
		System.out.println(dp(test));
	}

}
