package class03;

/**
 * 最长子串，思路：
 * 定义一张二维表，row是str1的所有字符，column是str2的所有字符
 * dp[i][j]是指必须以(i,j)结尾的最长公共子串长度
 * 所以如果str1[i]==str2[j]，那结果就是dp[i-1][j-1]的值加1，否则结果就是0
 * 复杂度：O(M*N)
 */
public class Code05_LCSubstring {

	public static String lcst1(String str1, String str2) {
		if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
			return "";
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		int[][] dp = getdp(chs1, chs2);
		int end = 0;
		int max = 0;
		for (int i = 0; i < chs1.length; i++) {
			for (int j = 0; j < chs2.length; j++) {
				if (dp[i][j] > max) {
					end = i;
					max = dp[i][j];
				}
			}
		}
		return str1.substring(end - max + 1, end + 1);
	}

	public static int[][] getdp(char[] str1, char[] str2) {
		int[][] dp = new int[str1.length][str2.length];
		for (int i = 0; i < str1.length; i++) {
			if (str1[i] == str2[0]) {
				dp[i][0] = 1;
			}
		}
		for (int j = 1; j < str2.length; j++) {
			if (str1[0] == str2[j]) {
				dp[0][j] = 1;
			}
		}
		for (int i = 1; i < str1.length; i++) {
			for (int j = 1; j < str2.length; j++) {
				if (str1[i] == str2[j]) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
				}
			}
		}
		return dp;
	}

	/**
	 * 基于二维数组压缩空间：
	 * 定义两个变量row，col作为出发点的坐标
	 * 遍历从二维表右上角向左，再向下，来到左下角。在每个点，作为出发点向右下方
	 *
	 * 复杂度：O(M*N)
	 * 并非最优解，最优解为：后缀数组解法，复杂度为O(N+M)
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String lcst2(String s1, String s2) {
		if (s1 == null || s2 == null || s1.equals("") || s2.equals("")) {
			return "";
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int row = 0; // 出发点的行号
		int col = str2.length - 1; // 出发点的列号
		int max = 0;
		int end = 0;
		while (row < str1.length) {
			int i = row;
			int j = col;
			//len记录相同字符串的长度
			int len = 0;
			// 向右下方移动的这一轮
			while (i < str1.length && j < str2.length) {
				if (str1[i] != str2[j]) {
					len = 0;
				} else {
					len++;
				}
				// len
				if (len > max) {
					//记录最长子串在s1的位置
					end = i;
					max = len;
				}
				//每个出发点向右下方走
				i++;
				j++;
			}
			//出发点的轨迹：从二维表右上角向左，再向下，来到左下角
			if (col > 0) {
				col--;
			} else {
				row++;
			}
		}
		//在最大公共子串长度的位置，向前推len个字符，就是最长的公共子串
		return s1.substring(end - max + 1, end + 1);
	}

	public static void main(String[] args) {
		String str1 = "ABC1234567DEFG";
		String str2 = "HIJKL1234567MNOP";
		System.out.println(lcst1(str1, str2));
		System.out.println(lcst2(str1, str2));

	}

}