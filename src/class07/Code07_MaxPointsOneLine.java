package class07;

import java.util.HashMap;
import java.util.Map;

/**
 * 求数组最多共线的点的个数
 * 思路：
 * 遍历点集合，来到了i位置,假设point[i]=a
 * 这时候点集合中的任意一点b与a有4种情况
 * 1）b与a重合
 * 2）b与a共x
 * 3）b与a共y
 * 4）b与a的斜率为k
 * 遍历点集合，收集这4中情况的点数，
 * 2、3、4的情况最最大值，再加上1，就是题解
 *
 * 优化
 * 来到a点后，a点之前点不用看了，因为之前的点，在之前遍历到它的时候已经求过一次
 *
 * 难点：如何表示斜率：
 * 方法一：将斜率写成"x_y"的格式，比如-1/3就是"-1_3"
 * 方法二：将△x，△y作为两个key，对应一个value，value就是△x，△y相同的次数
 */
public class Code07_MaxPointsOneLine {

	public static class Point {
		public int x;
		public int y;

		Point() {
			x = 0;
			y = 0;
		}

		Point(int a, int b) {
			x = a;
			y = b;
		}
	}

	public static int maxPoints(Point[] points) {
		if (points == null) {
			return 0;
		}
		if (points.length <= 2) {
			return points.length;
		}
		// key : 分子  value : 分母表
		Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
		int result = 0;
		for (int i = 0; i < points.length; i++) {
			map.clear();
			int samePosition = 1;
			int sameX = 0;
			int sameY = 0;
			int line = 0;
			for (int j = i + 1; j < points.length; j++) {
				int x = points[j].x - points[i].x;
				int y = points[j].y - points[i].y;
				if (x == 0 && y == 0) {
					samePosition++;
				} else if (x == 0) {
					sameX++;
				} else if (y == 0) {
					sameY++;
				} else {
					//求最大公约数
					int gcd = gcd(x, y);
					//约掉最大公约数
					x /= gcd;
					y /= gcd;
					//建立x，y的统计
					if (!map.containsKey(x)) {
						map.put(x, new HashMap<Integer, Integer>());
					}
					if (!map.get(x).containsKey(y)) {
						map.get(x).put(y, 0);
					}
					map.get(x).put(y, map.get(x).get(y) + 1);
					//这个line是斜率下共线的求最大值
					line = Math.max(line, map.get(x).get(y));
				}
			}
			//斜率，共y，共x，求最大值，加上共点的
			result = Math.max(result, Math.max(Math.max(sameX, sameY), line) + samePosition);
		}
		return result;
	}

	// 保证初始调用的时候，a和b不等于0
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

}
