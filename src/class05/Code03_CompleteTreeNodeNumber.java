package class05;

/**
 * 求该树的最大深度，即左节点到底的深度，为h
 * 假设来到了x节点，求x节点的最大深度为m，x节点的后继节点深度为n，
 * 如果m=n, x节点左树为满二叉树，高度为h-m，节点个数为2^(h-m)+1；继续来到右树的头结点x'，递归求个数
 * 如果m<n, x节点右树为满二叉树，高度为h-m-1，节点个数为2^(h-m-1)+1；继续来到左树的头结点x''，递归求个数
 * 头结点带入求值为题解
 *
 * 复杂度：
 * 对于每节点，看了它一侧的高度，但每个结点的高度是下降的
 * 所以O(N)= H+H-1+H-2+...+1=O(h^2) = O((logN)^2)
 */
public class Code03_CompleteTreeNodeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 请保证head为头的树，是完全二叉树
	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}

	// node在第level层，h是总的深度（h永远不变，全局变量
	// 以node为头的完全二叉树，节点个数是多少
	public static int bs(Node node, int Level, int h) {
		if (Level == h) {
			//如果结点在树最深的地方，它就只有一个结点
			return 1;
		}
		if (mostLeftLevel(node.right, Level + 1) == h) {
			//右树的高度等于总深度，左树节点数直接求，右树再进一次递归
			return (1 << (h - Level)) + bs(node.right, Level + 1, h);
		} else {
			//否则右树一定是满的，直接求，左树递归
			return (1 << (h - Level - 1)) + bs(node.left, Level + 1, h);
		}
	}

	// 如果node在第level层，
	// 求以node为头的子树，最大深度是多少
	// node为头的子树，一定是完全二叉树
	public static int mostLeftLevel(Node node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		System.out.println(nodeNum(head));

	}

}
