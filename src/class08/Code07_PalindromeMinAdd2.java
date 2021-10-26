package class08;

/**
 * 给定一个字符串，如果可以在任意位置添加字符，最少添加几个能让字符串整体都是回文串。
 * 思路：该题为动态规划，范围尝试的模型
 * dp[i][j]:从i位置到j位置，补成回文需要几个字符
 * 对角线含义：0-0位置，1-1位置... 自然就是回文，所以都填0
 * 对角线往下为i>j没有意义，范围尝试i>=j
 * 第二条对角线含义：0-1位置，1-2位置... 判断str[i]==str[j]?0:1
 * 普遍位置：dp[l][r]
 * 1. l..r-1变成回文，r位置变成回文需要在最前方加上str[r],所以dp[l][r-1]+1
 * 2. l+1..r变成回文，l位置变成回文需要在最后方加上str[l],所以dp[l+1][r]+1
 * 3. l+1..r-1变成回文，条件：str[l]==str[r],所以dp[l+1][r-1]
 *	123取最小，dp[0][n-1]就是题解
 *
 * 总结
 * 范围尝试：一般需要考虑开头和结尾
 * 一个样本作行，一个样本作列：一般考虑结尾的各种情况
 *
 * 进阶：如果要将所有添加最少字符的回文串返回该怎么做？
 * 首先，将dp表生成，
 * 来到dp[0][n-1],看下这个结果是123哪个步骤得到的，
 * 如果是1，那就在结果串res开头加上str[n-1]，再去递归解决dp[0][n-2]
 * 如果是1，那就在结果串res结尾加上str[0]，再去递归解决dp[1][n-1]
 * 如果是3，那就在结果串res开头加上str[0],结尾加上str[n-1]，再去递归解决dp[1][n-2]
 * 直到来到dp[l][r] l==r
 *
 */
public class Code07_PalindromeMinAdd2 {


	public static void main(String[] args) {
		String str = "AB1CD2EFG3H43IJK2L1MN";
	}

}
