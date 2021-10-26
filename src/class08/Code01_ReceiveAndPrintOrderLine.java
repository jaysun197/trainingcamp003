package class08;

import java.util.HashMap;

/**
 * 接受非连续的信息，串成连续的信息后打印
 * 思路：该题较为简单
 * 准备两个map，key：编号，value：信息
 * head记录开头节点，tail记录结尾节点。
 * 接受了一个节点编号为i，将i封装成单链表节点node放入head和tail中
 * 查看tail中有没有i-1节点，它的next指向i，i-1从tail中删掉，且i从head中删掉
 * 查看head中有没有i+1节点，作为i的next，i+1从head中删掉，且i从tail中删掉
 *
 */
public class Code01_ReceiveAndPrintOrderLine {

	public static class Node {
		public String info;
		public Node next;

		public Node(String str) {
			info = str;
		}
	}

	public static class MessageBox {
		private HashMap<Integer, Node> headMap;
		private HashMap<Integer, Node> tailMap;
		//在等哪个编号的节点，等到以后，就可以打印一串了
		private int waitPoint;

		public MessageBox() {
			headMap = new HashMap<Integer, Node>();
			tailMap = new HashMap<Integer, Node>();
			//刚开始就在等1号
			waitPoint = 1;
		}

		// 消息的编号，info消息的内容, 消息一定从1开始
		public void receive(int num, String info) {
			if (num < 1) {
				return;
			}
			Node cur = new Node(info);
			// num~num
			headMap.put(num, cur);
			tailMap.put(num, cur);
			// 建立了num~num这个连续区间的头和尾
			// 查询有没有某个连续区间以num-1结尾
			if (tailMap.containsKey(num - 1)) {
				tailMap.get(num - 1).next = cur;
				tailMap.remove(num - 1);
				headMap.remove(num);
			}
			// 查询有没有某个连续区间以num+1开头的
			if (headMap.containsKey(num + 1)) {
				cur.next = headMap.get(num + 1);
				tailMap.remove(num);
				headMap.remove(num + 1);
			}
			if (num == waitPoint) {
				print();
			}
		}

		private void print() {
			Node node = headMap.get(waitPoint);
			//删掉头
			headMap.remove(waitPoint);
			while (node != null) {
				System.out.print(node.info + " ");
				node = node.next;
				//最终跳出循环时，waitPoint就是下个等待的节点编号
				waitPoint++;
			}
			//删掉尾
			tailMap.remove(waitPoint-1);
			System.out.println();
		}

	}

	public static void main(String[] args) {
		// MessageBox only receive 1~N
		MessageBox box = new MessageBox();

		box.receive(2,"B"); // - 2"
		box.receive(1,"A"); // 1 2 -> print, trigger is 1

		box.receive(4,"D"); // - 4
		box.receive(5,"E"); // - 4 5
		box.receive(7,"G"); // - 4 5 - 7
		box.receive(8,"H"); // - 4 5 - 7 8
		box.receive(6,"F"); // - 4 5 6 7 8
		box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3

		box.receive(9,"I"); // 9 -> print, trigger is 9

		box.receive(10,"J"); // 10 -> print, trigger is 10

		box.receive(12,"L"); // - 12
		box.receive(13,"M"); // - 12 13
		box.receive(11,"K"); // 11 12 13 -> print, trigger is 11

	}
}
