package class03;

public class Code03_MinPathSum {

	/**
	 * dp表含义：
	 * 来到(i,j)位置时，最小路径和可能是从(i-1,j)来的，也可能是从(i,j-1)来的，就将上面两个位置的值，取一个最小的，加上自己
	 * 就是来到自己的最小路径和
	 * @param m
	 * @return
	 */
	public static int minPathSum1(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int row = m.length;
		int col = m[0].length;
		int[][] dp = new int[row][col];
		dp[0][0] = m[0][0];
		for (int i = 1; i < row; i++) {
			//第一列只可能从上一个的数据过来
			dp[i][0] = dp[i - 1][0] + m[i][0];
		}
		for (int j = 1; j < col; j++) {
			//第一行只可能从左一个的数据过来
			dp[0][j] = dp[0][j - 1] + m[0][j];
		}
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
			}
		}
		return dp[row - 1][col - 1];
	}

	public static int minPathSum2(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int more = Math.max(m.length, m[0].length);
		int less = Math.min(m.length, m[0].length);
		boolean rowmore = more == m.length;
		int[] arr = new int[less];
		arr[0] = m[0][0];
		for (int i = 1; i < less; i++) {
			arr[i] = arr[i - 1] + (rowmore ? m[0][i] : m[i][0]);
		}
		for (int i = 1; i < more; i++) {
			arr[0] = arr[0] + (rowmore ? m[i][0] : m[0][i]);
			for (int j = 1; j < less; j++) {
				arr[j] = Math.min(arr[j - 1], arr[j])
						+ (rowmore ? m[i][j] : m[j][i]);
			}
		}
		return arr[less - 1];
	}
	
	
	public static int minPathSum3(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int[] dp = new int[m[0].length];
		int N = m.length;
		int M = m[0].length;
		dp[0] = m[0][0];
		for(int col = 1; col <M; col++) {
			dp[col] = dp[col-1] + m[0][col];
		}
		for(int row = 1; row < N; row++) {
			dp[0] = dp[0] + m[row][0];	
			for(int col = 1;col <M; col++ ) {
				dp[col] = Math.min(dp[col-1], dp[col]) + m[row][col];
			}
		}
		return dp[M-1];
	}
	
	
	

	// for test
	public static int[][] generateRandomMatrix(int rowSize, int colSize) {
		if (rowSize < 0 || colSize < 0) {
			return null;
		}
		int[][] result = new int[rowSize][colSize];
		for (int i = 0; i != result.length; i++) {
			for (int j = 0; j != result[0].length; j++) {
				result[i][j] = (int) (Math.random() * 10);
			}
		}
		return result;
	}

	// for test
	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i != matrix.length; i++) {
			for (int j = 0; j != matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// int[][] m = generateRandomMatrix(3, 4);
		int[][] m = { { 1, 3, 5, 9 }, { 8, 1, 3, 4 }, { 5, 0, 6, 1 },
				{ 8, 8, 4, 0 } };
		printMatrix(m);
		System.out.println(minPathSum1(m));
		System.out.println(minPathSum2(m));
		System.out.println(minPathSum3(m));

	}
}
