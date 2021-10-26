package class07;

import java.util.HashMap;

/**
 * 一个数组中，如果两个数的最小公共因子大于1，则认为这两个数相通，求有多少个独立的域
 * 思路：并查集
 *
 * 方法一、
 * 对每个数，依次遍历，来到i位置，遍历i..N-1，将所有的属于一个集合的都并起来了，最终求并查集有多少个集合
 * O(N^2)
 *
 * 方法二、
 * 如果arr中数不是很大，可以对每个数建一个质数因子的表，
 * key：因子
 * value：下标
 * 遍历arr，分析出质数因子，再从表中找到一个下标，加入它的集合
 * 复杂度
 * 分析质数因子为O(K),K为数值，总的复杂度为O(N*K)
 *
 * 方法三
 * 基于方法二可以再优化
 * 对于数字a不是分析质数因子，而且是分析1-根号下a为开头的所有因子，这些因子作为a的代表因子放入表中
 * 复杂度：O(N^根号下K)
 *
 *
 * 求最大公约数方法,要求背诵
 * int gcd(int a,int b){
 *     return b==0?a:gcd(b,a%b);
 * }
 */
// 本题为leetcode原题
// 测试链接：https://leetcode.com/problems/largest-component-size-by-common-factor/
// 方法1会超时，但是方法2直接通过
public class Code04_LargestComponentSizebyCommonFactor {

	//方法一
	public static int largestComponentSize1(int[] arr) {
		int N = arr.length;
		UnionFind set = new UnionFind(N);
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (gcd(arr[i], arr[j]) != 1) {
					set.union(i, j);
				}
			}
		}
		return set.maxSize();
	}

	//方法三
	public static int largestComponentSize2(int[] arr) {
		int N = arr.length;
		UnionFind unionFind = new UnionFind(N);
		HashMap<Integer, Integer> fatorsMap = new HashMap<>();
		for (int i = 0; i < N; i++) {
			int num = arr[i];
			int limit = (int) Math.sqrt(num);
			//从1开始试，一直到根号下arr[i]
			for (int j = 1; j <= limit; j++) {
				if (num % j == 0) {
					//表示j是num的一个因子
					if (j != 1) {
						//不是1，因为题目要求因子不能是1
						if (!fatorsMap.containsKey(j)) {
							//将arr[i]的所有代表因子加入表中
							fatorsMap.put(j, i);
						} else {
							//j是之前已经出现过的一个数的因子，把这两个数合并
							unionFind.union(fatorsMap.get(j), i);
						}
					}
					//这是相对的另一个因子
					int other = num / j;
					if (other != 1) {
						if (!fatorsMap.containsKey(other)) {
							fatorsMap.put(other, i);
						} else {
							unionFind.union(fatorsMap.get(other), i);
						}
					}
				}
			}
		}
		return unionFind.maxSize();
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	public static class UnionFind {
		private int[] parents;
		private int[] sizes;
		private int[] help;

		public UnionFind(int N) {
			parents = new int[N];
			sizes = new int[N];
			help = new int[N];
			for (int i = 0; i < N; i++) {
				parents[i] = i;
				sizes[i] = 1;
			}
		}

		public int maxSize() {
			int ans = 0;
			for (int size : sizes) {
				ans = Math.max(ans, size);
			}
			return ans;
		}

		private int find(int i) {
			int hi = 0;
			while (i != parents[i]) {
				help[hi++] = i;
				i = parents[i];
			}
			for (hi--; hi >= 0; hi--) {
				parents[help[hi]] = i;
			}
			return i;
		}

		public void union(int i, int j) {
			int f1 = find(i);
			int f2 = find(j);
			if (f1 != f2) {
				int big = sizes[f1] >= sizes[f2] ? f1 : f1;
				int small = big == f1 ? f2 : f1;
				parents[small] = big;
				sizes[big] = sizes[f1] + sizes[f2];
			}
		}
	}

}
