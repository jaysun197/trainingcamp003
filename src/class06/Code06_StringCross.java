package class06;

/**
 * 判断str3是否是str1、str2的交错组成
 *
 * 思路；这是一个二维表的动态规划：一个样本作行一个样本作列
 * dp[i][j]: str1取i长度，str2取j长度，能否交错组成str3的前i+j长度
 * 第一行、第一列可以直接比对得到
 * 对于普遍位置dp[i][j]可能性
 * 1. str1来结尾：str[i-1]==str3[i+j-1]
 * 看dp[i-1][j]即dp上一个位置的值==true
 * 2. str2来结尾：str[j-1]==str3[i+j-1]
 * 看dp[i][j-1]即dp前一个位置的值==true
 * 1或者2有一个位true，dp[i][j]就是true
 * dp[str1.length][str2.length]就是题解
 */
public class Code06_StringCross {

	public static boolean isCross1(String s1, String s2, String ai) {
		if (s1 == null || s2 == null || ai == null) {
			return false;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		char[] aim = ai.toCharArray();
		//s1s2长度加起来一定等于s3
		if (aim.length != str1.length + str2.length) {
			return false;
		}
		boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
		// s1s2s3都取0长度，一定相等
		dp[0][0] = true;
		for (int i = 1; i <= str1.length; i++) {
			//只用s1
			if (str1[i - 1] != aim[i - 1]) {
				//之前只要相等都是true
				break;
			}
			dp[i][0] = true;
		}
		for (int j = 1; j <= str2.length; j++) {
			//只用s2
			if (str2[j - 1] != aim[j - 1]) {
				break;
			}
			dp[0][j] = true;
		}
		for (int i = 1; i <= str1.length; i++) {
			for (int j = 1; j <= str2.length; j++) {
				
				if (
						//s1最后一个字符来结尾
						(str1[i - 1] == aim[i + j - 1] && dp[i - 1][j])
						
						||
						//s2最后一个字符来结尾
						(str2[j - 1] == aim[i + j - 1] && dp[i][j - 1])
						
						
				) {
					
					
					dp[i][j] = true;
					
					
				}
				
				
				
			}
		}
		return dp[str1.length][str2.length];
	}

	public static boolean isCross2(String str1, String str2, String aim) {
		if (str1 == null || str2 == null || aim == null) {
			return false;
		}
		char[] ch1 = str1.toCharArray();
		char[] ch2 = str2.toCharArray();
		char[] chaim = aim.toCharArray();
		if (chaim.length != ch1.length + ch2.length) {
			return false;
		}
		char[] longs = ch1.length >= ch2.length ? ch1 : ch2;
		char[] shorts = ch1.length < ch2.length ? ch1 : ch2;
		boolean[] dp = new boolean[shorts.length + 1];
		dp[0] = true;
		for (int i = 1; i <= shorts.length; i++) {
			if (shorts[i - 1] != chaim[i - 1]) {
				break;
			}
			dp[i] = true;
		}
		for (int i = 1; i <= longs.length; i++) {
			dp[0] = dp[0] && longs[i - 1] == chaim[i - 1];
			for (int j = 1; j <= shorts.length; j++) {
				if ((longs[i - 1] == chaim[i + j - 1] && dp[j]) || (shorts[j - 1] == chaim[i + j - 1] && dp[j - 1])) {
					dp[j] = true;
				} else {
					dp[j] = false;
				}
			}
		}
		return dp[shorts.length];
	}

	public static void main(String[] args) {
		String str1 = "1234";
		String str2 = "abcd";
		String aim = "1a23bcd4";
		System.out.println(isCross1(str1, str2, aim));
		System.out.println(isCross2(str1, str2, aim));

	}

}
