package class02;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 二维蓄水的问题
 * 解法：
 * 准备一个二维标记数组flag
 * 准备一个变量为全局max
 * 将数组arr中四周的数放入小根堆heap中，放入后标记为true
 * 弹出堆顶x，更新max，将x四周的数放入heap中，放入的时候结算该位置的水量为max(0,max-该位置的值)，放入后标记为true
 * 继续弹出堆顶y，周而复始，直到heap空了
 *
 * 总结：
 * 1. 从四周开始，找到一个薄弱点a，记为max，结算与a相连的低洼所有区域湖1；然后在找下一个薄弱点b，结算与b相连的低洼所有区域湖2
 * 湖2一定比湖1的底部更高，继续找到湖3...
 * 2. 一维蓄水寻找薄弱点用两个变量就可以了，因为是从两边往中间；二维蓄水寻找薄弱点就需要用堆，因为是从四周往中心
 * 3. 复杂度：遍历了所有的数的时候外加一个堆的复杂度：O(N*M*log(heap的平均size，记为K，最大为N*M))
 */
public class Code05_TrappingRainWaterII {

	public static class Node {
		public int value;
		public int row;
		public int col;

		public Node(int v, int r, int c) {
			value = v;
			row = r;
			col = c;
		}

	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.value - o2.value;
		}

	}

	public static int trapRainWater(int[][] heightMap) {
		if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
			return 0;
		}
		int N = heightMap.length;
		int M = heightMap[0].length;
		// isEnter[i][j] == true  之前进过
		//  isEnter[i][j] == false 之前没进过
		boolean[][] isEnter = new boolean[N][M];
		// 小根堆
		PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
		for (int col = 0; col < M - 1; col++) {
			isEnter[0][col] = true;
			heap.add(new Node(heightMap[0][col], 0, col));
		}
		for (int row = 0; row < N - 1; row++) {
			isEnter[row][M - 1] = true;
			heap.add(new Node(heightMap[row][M - 1], row, M - 1));
		}
		for (int col = M - 1; col > 0; col--) {
			isEnter[N - 1][col] = true;
			heap.add(new Node(heightMap[N - 1][col], N - 1, col));
		}
		for (int row = N - 1; row > 0; row--) {
			isEnter[row][0] = true;
			heap.add(new Node(heightMap[row][0], row, 0));
		}
		
		
		
		
		int water = 0; // 每个位置的水量，累加到water上去
		int max = 0; // 每个node在弹出的时候，如果value更大，更新max，否则max的值维持不变
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			max = Math.max(max, cur.value);
			int r = cur.row;
			int c = cur.col;
			if (r > 0 && !isEnter[r - 1][c]) { // 如果有上面的位置并且上面位置没进过堆
				water += Math.max(0, max - heightMap[r - 1][c]);
				isEnter[r - 1][c] = true;
				heap.add(new Node(heightMap[r - 1][c], r - 1, c));
			}
			if (r < N - 1 && !isEnter[r + 1][c]) {
				water += Math.max(0, max - heightMap[r + 1][c]);
				isEnter[r + 1][c] = true;
				heap.add(new Node(heightMap[r + 1][c], r + 1, c));
			}
			if (c > 0 && !isEnter[r][c - 1]) {
				water += Math.max(0, max - heightMap[r][c - 1]);
				isEnter[r][c - 1] = true;
				heap.add(new Node(heightMap[r][c - 1], r, c - 1));
			}
			if (c < M - 1 && !isEnter[r][c + 1]) {
				water += Math.max(0, max - heightMap[r][c + 1]);
				isEnter[r][c + 1] = true;
				heap.add(new Node(heightMap[r][c + 1], r, c + 1));
			}
		}
		return water;
	}

}
