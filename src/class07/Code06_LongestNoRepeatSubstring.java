package class07;

/**
 * 找不重复的最长子串
 * 思路：
 * 子串子数组问题，一般都是考虑以...为开头/结尾
 * 本题为动态规划
 * dp[i]:以arr[i]结尾的最长子串长度：
 * 看下上次arr[i]字符出现的位置a
 * 看下dp[i-1]的位置b
 * 位置a和b哪个离i近，取哪个,假设a近
 * dp[i] = i-a
 *
 */
public class Code06_LongestNoRepeatSubstring {

	public static int maxUnique(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		char[] chas = str.toCharArray();
		// map 替代了哈希表   假设字符的码是0~255
		int[] map = new int[256];
		for (int i = 0; i < 256; i++) {
			map[i] = -1;
		}
		int len = 0;
		int pre = -1;
		int cur = 0;
		for (int i = 0; i != chas.length; i++) {
			pre = Math.max(pre, map[chas[i]]);
			cur = i - pre;
			len = Math.max(len, cur);
			map[chas[i]] = i;
		}
		return len;
	}

	// for test
	public static String getRandomString(int len) {
		char[] str = new char[len];
		int base = 'a';
		int range = 'z' - 'a' + 1;
		for (int i = 0; i != len; i++) {
			str[i] = (char) ((int) (Math.random() * range) + base);
		}
		return String.valueOf(str);
	}

	// for test
	public static String maxUniqueString(String str) {
		if (str == null || str.equals("")) {
			return str;
		}
		char[] chas = str.toCharArray();
		int[] map = new int[256];
		for (int i = 0; i < 256; i++) {
			map[i] = -1;
		}
		int len = -1;
		int pre = -1;
		int cur = 0;
		int end = -1;
		for (int i = 0; i != chas.length; i++) {
			pre = Math.max(pre, map[chas[i]]);
			cur = i - pre;
			if (cur > len) {
				len = cur;
				end = i;
			}
			map[chas[i]] = i;
		}
		return str.substring(end - len + 1, end + 1);
	}

	public static void main(String[] args) {
		String str = getRandomString(20);
		System.out.println(str);
		System.out.println(maxUnique(str));
		System.out.println(maxUniqueString(str));
	}
}
