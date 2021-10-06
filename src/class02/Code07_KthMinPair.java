package class02;

import java.util.Arrays;
import java.util.Comparator;

/**
 *思路：
 * 假定该数组arr是有序的，长度为n，需要找第k小的二元组(x,y)。
 * arr[0]构成了n个元组，arr[1]构成了n个元组...直到arr[i]，arr[i]就是要找的x，x=arr[i]=arr[(k-1)/n]，记为M。
 * 遍历数组，找到小于M的数字为a个，等于M的数为b个，因为第一个数用小于M的数组成的元组一定小于(M,y)
 * 那就知道所有的元组中小于(M,y)的元组个数为a*n个，于是该问题就变成了需要找到第k-a*n（记为S）小的元组(M,y)
 * 因为等于M的数有b个，arr[0]构成b个元组，arr[1]构成b个元组...直到arr[j]，arr[j]就要要找的y，y=arr[j]=arr[(S-1)/b]，即y=arr[(k-a*n)/b]
 *
 * 优化：
 * 因为需要假定数组有序，排序提高了复杂度。但真的需要排序吗？
 * 只是需要找到第arr[i],arr[j]，就是arr中第i和第j位置的数（如果排好序的话），这就是一个求无序arr中第k小的数。
 * 最快的是模拟快排：O(n)或者bfprt：O(n)
 */
public class Code07_KthMinPair {

	public static class Pair {
		public int x;
		public int y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static class PairComparator implements Comparator<Pair> {

		@Override
		public int compare(Pair arg0, Pair arg1) {
			return arg0.x != arg1.x ? arg0.x - arg1.x : arg0.y - arg1.y;
		}

	}

	// O(N^2 * log (N^2))的复杂度，你肯定过不了
	// 返回的int[] 长度是2，{3,1} int[2] = [3,1]
	public static int[] kthMinPair1(int[] arr, int k) {
		int N = arr.length;
		if (k > N * N) {
			return null;
		}
		Pair[] pairs = new Pair[N * N];
		int index = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				pairs[index++] = new Pair(arr[i], arr[j]);
			}
		}
		Arrays.sort(pairs, new PairComparator());
		return new int[] { pairs[k - 1].x, pairs[k - 1].y };
	}

	// O(N*logN)的复杂度，你肯定过了
	public static int[] kthMinPair2(int[] arr, int k) {
		int N = arr.length;
		if (k > N * N) {
			return null;
		}
		// O(N*logN)
		Arrays.sort(arr);
		// 第K小的数值对，第一维数字，是什么 是arr中
		int fristNum = arr[(k - 1) / N];
		int lessFristNumSize = 0;// 数出比fristNum小的数有几个
		int fristNumSize = 0; // 数出==fristNum的数有几个
		// <= fristNum
		for (int i = 0; i < N && arr[i] <= fristNum; i++) {
			if (arr[i] < fristNum) {
				lessFristNumSize++;
			} else {
				fristNumSize++;
			}
		}
		int rest = k - (lessFristNumSize * N);
		return new int[] { fristNum, arr[(rest - 1) / fristNumSize] };
	}

	// O(N)的复杂度，你肯定蒙了
	public static int[] kthMinPair3(int[] arr, int k) {
		int N = arr.length;
		if (k > N * N) {
			return null;
		}
		// 在无序数组中，找到第K小的数，返回值
		// 第K小，以1作为开始
		int fristNum = getMinKth(arr, (k - 1) / N);
		int lessFristNumSize = 0;
		int fristNumSize = 0;
		for (int i = 0; i < N; i++) {
			if (arr[i] < fristNum) {
				lessFristNumSize++;
			}
			if (arr[i] == fristNum) {
				fristNumSize++;
			}
		}
		int rest = k - (lessFristNumSize * N);
		return new int[] { fristNum, getMinKth(arr, (rest - 1) / fristNumSize) };
	}

	// 改写快排，时间复杂度O(N)
	// 在无序数组arr中，找到，如果排序的话，arr[index]的数是什么？
	public static int getMinKth(int[] arr, int index) {
		int L = 0;
		int R = arr.length - 1;
		int pivot = 0;
		int[] range = null;
		while (L < R) {
			pivot = arr[L + (int) (Math.random() * (R - L + 1))];
			range = partition(arr, L, R, pivot);
			if (index < range[0]) {
				R = range[0] - 1;
			} else if (index > range[1]) {
				L = range[1] + 1;
			} else {
				return pivot;
			}
		}
		return arr[L];
	}

	public static int[] partition(int[] arr, int L, int R, int pivot) {
		int less = L - 1;
		int more = R + 1;
		int cur = L;
		while (cur < more) {
			if (arr[cur] < pivot) {
				swap(arr, ++less, cur++);
			} else if (arr[cur] > pivot) {
				swap(arr, cur, --more);
			} else {
				cur++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 为了测试，生成值也随机，长度也随机的随机数组
	public static int[] getRandomArray(int max, int len) {
		int[] arr = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
		}
		return arr;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// 随机测试了百万组，保证三种方法都是对的
	public static void main(String[] args) {
		int max = 100;
		int len = 30;
		int testTimes = 100000;
		System.out.println("test bagin, test times : " + testTimes);
		for (int i = 0; i < testTimes; i++) {
			int[] arr = getRandomArray(max, len);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int N = arr.length * arr.length;
			int k = (int) (Math.random() * N) + 1;
			int[] ans1 = kthMinPair1(arr1, k);
			int[] ans2 = kthMinPair2(arr2, k);
			int[] ans3 = kthMinPair3(arr3, k);
			if (ans1[0] != ans2[0] || ans2[0] != ans3[0] || ans1[1] != ans2[1] || ans2[1] != ans3[1]) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
