package class01;

public class Code04_ColorLeftRight {

	/**
	 * 使用预处理数组，这里将预处理数组优化成了一个变量，分别是left，right
	 * @param s
	 * @return
	 */
	// RGRGR -> RRRGG
	public static int minPaint(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		//右边R的数量
		int right = 0;
		for (int i = 0; i < N; i++) {
			right += str[i] == 'R' ? 1 : 0;
		}
		int ans = right; // 如果数组所有的范围，都是右侧范围，都变成G
		//左边G的数量
		int left = 0;
		//从0开始遍历，i=0时左边的G为0，右边的R为总共R数量，遇到G left++,遇到R right--
		for (int i = 0; i < N ; i++) { // 0..n-2 左侧 n-1..N-1
			left += str[i] == 'G' ? 1 : 0;
			right -= str[i] == 'R' ? 1 : 0;
			ans = Math.min(ans, left + right);
		}
		// 0...N-1 左全部 右无
//		ans = Math.min(ans, left + (str[N - 1] == 'G' ? 1 : 0));
		return ans;
	}

	public static void main(String[] args) {
		String test = "GGGGGR";
		System.out.println(minPaint(test));
	}

}
