package class01;

public class Code02_NeedParentheses {

	/**
	 * 用一个变量来统计 '('=1 ; ')'=-1
	 * 如果为负数，则肯定多了')'
	 * @param s
	 * @return
	 */
	public static boolean valid(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		for (int i = 0; i < str.length; i++) {
			count += str[i] == '(' ? 1 : -1;
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	/**
	 * 用一个变量来统计 '('=1 ; ')'=-1
	 * 用另一个变量统计当多出 ）时需要的（ 数量
	 * @param s
	 * @return
	 */
	public static int needParentheses(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		int need = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				count++;
			} else { // 遇到的是')'
				if (count == 0) {
					need++;
				} else {
					count--;
				}
			}
		}
		return count + need;
	}

}
