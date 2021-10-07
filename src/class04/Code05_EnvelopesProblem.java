package class04;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 信封嵌套问题：
 * 最长递增子序列的应用
 * 将所有的信封(长，宽)按长递增，长一样的时候宽递减排序
 * 排好以后，将所有的宽都拿出来组成数组arr
 * 因为长已经从小到大或者相等排好了，只要保证宽严格递增，就能求出最多能嵌套多少层
 * arr的最长递增子序列就是题解
 */
public class Code05_EnvelopesProblem {

	public static class Envelope {
		public int l;
		public int h;

		public Envelope(int weight, int hight) {
			l = weight;
			h = hight;
		}
	}

	public static class EnvelopeComparator implements Comparator<Envelope> {
		@Override
		public int compare(Envelope o1, Envelope o2) {
			return o1.l != o2.l ? o1.l - o2.l : o2.h - o1.h;
		}
	}

	public static Envelope[] getSortedEnvelopes(int[][] matrix) {
		Envelope[] res = new Envelope[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			res[i] = new Envelope(matrix[i][0], matrix[i][1]);
		}
		Arrays.sort(res, new EnvelopeComparator());
		return res;
	}

	public static int maxEnvelopes(int[][] matrix) {
		Envelope[] envelopes = getSortedEnvelopes(matrix);
		int[] ends = new int[matrix.length];
		ends[0] = envelopes[0].h;
		int right = 0;
		int l = 0;
		int r = 0;
		int m = 0;
		for (int i = 1; i < envelopes.length; i++) {
			l = 0;
			r = right;
			while (l <= r) {
				m = (l + r) / 2;
				if (envelopes[i].h > ends[m]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			right = Math.max(right, l);
			ends[l] = envelopes[i].h;
		}
		return right + 1;
	}

	public static void main(String[] args) {
		int[][] test = { { 3, 4 }, { 2, 3 }, { 4, 5 }, { 1, 3 }, { 2, 2 }, { 3, 6 }, { 1, 2 }, { 3, 2 }, { 2, 4 } };
		System.out.println(maxEnvelopes(test));
	}
}
