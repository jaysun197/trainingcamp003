package class04;

/**
 * 最大子矩阵的累加和
 * 如果枚举两个点构成长方形O(n^4),再求长方形的和O(N^2)，复杂度：O(N^6)
 *
 * 最优解：
 * 必须包含且仅包含第0-0行的数据，最大子矩阵累加和，这就变成了一个数组的最大子数组累加和
 * 必须包含且仅包含第0-1行的数据，最大子矩阵累加和，将0行和1行的数据纵向相加，压缩变成一行数据，这就变成了一个数组的最大子数组累加和
 * 必须包含且仅包含第0-2行的数据...
 * 必须包含且仅包含第0-N-1行的数据...
 * 必须包含且仅包含第1-1行的数据...
 * 必须包含且仅包含第1-2行的数据...
 * 必须包含且仅包含第1-N-1行的数据...
 * ...
 * 必须包含且仅包含第N-1-N-1行的数据...
 * 整个矩阵的最大子矩阵累加和一定在这其中
 * 复杂度：O(N^3)
 *
 */
public class Code07_SubMatrixMaxSum {

	public static int maxSum(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		int[] s = null;
		for (int i = 0; i != m.length; i++) { // 开始的行号i
			s = new int[m[0].length]; // 统计数组放在这里，便于统计i->n-1行长方形的数据
			for (int j = i; j != m.length; j++) { // 结束的行号j，i~j行是我讨论的范围
				cur = 0;
				for (int k = 0; k != s.length; k++) {
					//累加之前s[k]保留了i->j-1行的压缩数组
					s[k] += m[j][k];
					cur += s[k];
					max = Math.max(max, cur);
					cur = cur < 0 ? 0 : cur;
				}
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int[][] matrix = { { -90, 48, 78 }, { 64, -40, 64 }, { -81, -7, 66 } };
		System.out.println(maxSum(matrix));

	}

}
