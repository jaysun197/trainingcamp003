package class06;

/**
 * 数组中最大子数组异或和
 * 思路：异或运算不具备单调性，要求最大子数组，只能枚举出所有的子数组，将每个子数组的异或和作对比，收集最大的
 * 解法一：
 * 如果枚举出所有的子数组O(N^2),再将子数组的值异或起来O(N)，复杂度就是O(N^3)。
 * 这里可以用预处理数组，将复杂度降到O(N^2):
 * 准备一个前缀异或和数组eor
 * eor[i]:0->i位置的数异或和
 * 遍历arr数组，来到了j位置，即枚举以j位置结尾的所有子数组，
 * 即遍历0->j，来到了其中的i位置，含义：从i到j位置的子数组，求该子数组的异或和：eor[j]^eor[i](0->i位置的数跟自己异或变为0，剩下i->j位置的数)
 * 收集结果求最大值
 *
 * 解法二：
 * 思路：遍历arr数组，来到了j位置，接下来需要枚举以j位置结尾的所有子数组，假设，现在拿到了eor[j]的二进制结果，
 * 现在求eor[0]->eor[j]中与eor[j]异或起来最大的数，规则就是要一个数，符号位与eor[j]相同，其他的相反，
 * 得到的结果就是符号位尽量为0，数字位尽量为1，这样的结果就是最大。这需要从第一个数往后作决策，最符合的结构，就是前缀树
 * 前缀树提供两个方法：
 * add：将一个数字加进来，形成一个两条路*32长度的树结构
 * maxXor：给一个数，返回前缀树中最佳决策下的数与之异或起来的值
 * 复杂度：O(32*N)=O(N)
 */
public class Code01_MaxEOR {

	// O(N^2)
	public static int maxXorSubarray1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 准备一个前缀异或和数组eor
		// eor[i] = arr[0...i]的异或结果
		int[] eor = new int[arr.length];
		eor[0] = arr[0];
		// 生成eor数组，eor[i]代表arr[0..i]的异或和
		for (int i = 1; i < arr.length; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		int max = Integer.MIN_VALUE;
		//枚举所有的子数组，以j结尾，以i开头
		for (int j = 0; j < arr.length; j++) {
			for (int i = 0; i <= j; i++) { // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
				//开头是0的话，子数组异或和就是eor[j]
				max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
			}
		}
		return max;
	}

	// 前缀树的节点类型，每个节点向下只可能有走向0或1的路
	// node.nexts[0] == null 0方向没路
	// node.nexts[0] != null 0方向有路
	// 前缀树只有两条路：0，1
	public static class Node {
		public Node[] nexts = new Node[2];
	}

	// 基于本题，定制前缀树的实现
	public static class NumTrie {
		// 头节点
		public Node head = new Node();

		// 把某个数字newNum加入到这棵前缀树里
		// num是一个32位的整数，所以加入的过程一共走32步
		public void add(int newNum) {
			Node cur = head;
			//树的深度；32
			for (int move = 31; move >= 0; move--) {
				// 从高位到低位，取出每一位的状态，如果当前状态是0，
				// nexts[0] 有值
				// ，如果当前状态是1
				// nexts[1] 有值
				int path = ((newNum >> move) & 1);
				// 无路新建、有路复用
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				cur = cur.nexts[path];
			}
		}

		// 该结构之前收集了一票数字，并且建好了前缀树
		// sum,和 谁 ^ 最大的结果（把结果返回）
		public int maxXor(int sum) {
			Node cur = head;
			int res = 0;
			for (int move = 31; move >= 0; move--) {
				//当前位置
				int path = (sum >> move) & 1;
				// 期待的路：如果最高位期待当前位置相同的路，数字位期待不同的路
				int best = move == 31 ? path : (path ^ 1);
				// 实际走的路：如果有期待的路，就走期待的路，没有就走现有的路
				best = cur.nexts[best] != null ? best : (best ^ 1);
				// (path ^ best) 当前位位异或完的结果
				// 当前位的数和期待的数异或起来，放到该位上，32次每次决策一个位上的数，将数都|起来，形成一个32位的结果
				res |= (path ^ best) << move;
				cur = cur.nexts[best];
			}
			return res;
		}
	}

	public static int maxXorSubarray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int eor = 0; // 0..i 异或和
		// 前缀树 -> numTrie
		NumTrie numTrie = new NumTrie();
		numTrie.add(0); // 一个数也没有的时候，异或和是0
		//枚举以i位置结尾的子数组
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i]; // eor -> 0..i异或和
			// X, 0~0 , 0~1, .., 0~i-1
			// eor[i]和之前eor[?]（以及0）哪个异或起来最大：eor[?->i]，必须以i结尾的最大异或和
			max = Math.max(max, numTrie.maxXor(eor));
			numTrie.add(eor);
		}
		return max;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 30;
		int maxValue = 50;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int comp = maxXorSubarray1(arr);
			int res = maxXorSubarray2(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
		//
		// // int[] arr = generateRandomArray(6, maxValue);
		// int[] arr = { 3, -28, -29, 2};
		//
		// for (int i = 0; i < arr.length; i++) {
		// System.out.println(arr[i] + " ");
		// }
		// System.out.println("=========");
		// System.out.println(maxXorSubarray(arr));
		// System.out.println((int) (-28 ^ -29));

	}
}
