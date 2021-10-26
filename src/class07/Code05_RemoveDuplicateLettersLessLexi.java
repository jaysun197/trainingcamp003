package class07;

/**
 * 在str中，每种字符都要保留一个，让最后的结果，字典序最小 ，并返回
 * str字典序：
 * 1）如果长度相等，从高位到低位比较字典序
 * 2）如果长度不等，较短的str后面补0直到等长，再比较
 * 思路：贪心算法
 * 1. 给str中的每个char建立词频统计
 * 2. 遍历str，每走过一个char，词频--，直到出现了某个词频减为了0
 * 3. 在目前走过的所有char中，挑出从前往后第一次出现的字典序最下的char，假设是a
 * 4. a左边的所有字符都删除，右边的a也删除，a自己也删除，剩下的str'继续以上流程，直到str长度变0
 *
 * 复杂度：假设str长度为k，每遍历一次str搞定一个char
 * O(K*N)
 */
public class Code05_RemoveDuplicateLettersLessLexi {

	// 在str中，每种字符都要保留一个，让最后的结果，字典序最小 ，并返回
	public static String removeDuplicateLetters1(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		//词频表，阿斯克码一共256个
		int[] map = new int[256];
		for (int i = 0; i < str.length(); i++) {
			map[str.charAt(i)]++;
		}
		int minACSIndex = 0;
		for (int i = 0; i < str.length(); i++) {
			//记录下第一次出现的最小的ask码下标
			minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
			//--，并判断，有0赶紧停
			if (--map[str.charAt(i)] == 0) {
				break;
			}
		}
		//挑出来的第一个char，加上后面的str'继续挑
		return String.valueOf(str.charAt(minACSIndex)) +
				//从第一个char右边开始，把第一个char都删掉后继续挑
				removeDuplicateLetters1(
				str.substring(minACSIndex + 1).replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
	}

	public static String removeDuplicateLetters2(String s) {
		char[] str = s.toCharArray();
		// 小写字母ascii码值范围[97~122]，所以用长度为26的数组做次数统计
		// 如果map[i] > -1，则代表ascii码值为i的字符的出现次数
		// 如果map[i] == -1，则代表ascii码值为i的字符不再考虑
		int[] map = new int[26];
		for (int i = 0; i < str.length; i++) {
			map[str[i] - 'a']++;
		}
		char[] res = new char[26];
		int index = 0;
		int L = 0;
		int R = 0;
		while (R != str.length) {
			// 如果当前字符是不再考虑的，直接跳过
			// 如果当前字符的出现次数减1之后，后面还能出现，直接跳过
			if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
				R++;
			} else { // 当前字符需要考虑并且之后不会再出现了
				// 在str[L..R]上所有需要考虑的字符中，找到ascii码最小字符的位置
				int pick = -1;
				for (int i = L; i <= R; i++) {
					if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
						pick = i;
					}
				}
				// 把ascii码最小的字符放到挑选结果中
				res[index++] = str[pick];
				// 在上一个的for循环中，str[L..R]范围上每种字符的出现次数都减少了
				// 需要把str[pick + 1..R]上每种字符的出现次数加回来
				for (int i = pick + 1; i <= R; i++) {
					if (map[str[i] - 'a'] != -1) { // 只增加以后需要考虑字符的次数
						map[str[i] - 'a']++;
					}
				}
				// 选出的ascii码最小的字符，以后不再考虑了
				map[str[pick] - 'a'] = -1;
				// 继续在str[pick + 1......]上重复这个过程
				L = pick + 1;
				R = L;
			}
		}
		return String.valueOf(res, 0, index);
	}

}
