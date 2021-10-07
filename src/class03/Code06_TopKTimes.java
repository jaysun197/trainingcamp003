package class03;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * 词频求topK，思路：
 * 先用hash表建立词频
 * 再遍历hash表将每个词，按词频经过与堆顶比较，放入size=topK的小根堆中。
 *
 * 优化：
 * 不用堆，变成一个从无序的数组中寻找第k,k-1,k-2...1小的问题。使用改写的快排寻找.
 * 复杂度：O(N)
 */
public class Code06_TopKTimes {

	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.times - o2.times;
		}

	}

	public static void printTopKAndRank(String[] arr, int topK) {
		if (arr == null || arr.length == 0 || topK < 1 || topK > arr.length) {
			return;
		}
		HashMap<String, Integer> map = new HashMap<>();
		for (String str : arr) {
			if (!map.containsKey(str)) {
				map.put(str, 1);
			} else {
				map.put(str, map.get(str) + 1);
			}
		}
//		topK = Math.min(arr.length, topK);
		PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
		for (Entry<String, Integer> entry : map.entrySet()) {
			Node cur = new Node(entry.getKey(), entry.getValue());
			if (heap.size() < topK) {
				//堆没满，直接进
				heap.add(cur);
			} else {
				//满了，就比较一下堆顶再进
				if (heap.peek().times < cur.times) {
					heap.poll();
					heap.add(cur);
				}
			}
		}
		while (!heap.isEmpty()) {
			System.out.println(heap.poll().str);
		}
	}

	public static String[] generateRandomArray(int len, int max) {
		String[] res = new String[len];
		for (int i = 0; i != len; i++) {
			res[i] = String.valueOf((int) (Math.random() * (max + 1)));
		}
		return res;
	}

	public static void printArray(String[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		String[] arr1 = { "A", "B", "A", "C", "A", "C", "B", "B", "K" };
		printTopKAndRank(arr1, 2);

		String[] arr2 = generateRandomArray(50, 10);
		int topK = 3;
		printArray(arr2);
		printTopKAndRank(arr2, topK);

	}
}