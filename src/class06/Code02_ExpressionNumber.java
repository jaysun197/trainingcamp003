package class06;

/**
 * 范围尝试模型
 * 枚举所有的运算符char[i]，运算符左边是一个范围，右边是一个范围，讨论以该运算符为最后一步的情况下，共有多少种方案
 * 定义函数int f(char[] str, L, R, boolean desired) 返回方案数
 * 1. 期待true
 * a) 如果char[i] = '&'
 * ways += f(str,0,i-1,true) * f(str,i+1,n-1,true)
 * b) 如果char[i] = '|'
 * ways += f(str,0,i-1,true) * f(str,i+1,n-1,false)
 * ways += f(str,0,i-1,false) * f(str,i+1,n-1,true)
 * ways += f(str,0,i-1,true) * f(str,i+1,n-1,true)
 * c) 如果char[i] = '^'
 * ways += f(str,0,i-1,true) * f(str,i+1,n-1,false)
 * ways += f(str,0,i-1,false) * f(str,i+1,n-1,true)
 * 2. 期待false
 * a) 如果char[i] = '&'
 * ways += f(str,0,i-1,false) * f(str,i+1,n-1,true)
 * ways += f(str,0,i-1,true) * f(str,i+1,n-1,false)
 * ways += f(str,0,i-1,false) * f(str,i+1,n-1,false)
 * b) 如果char[i] = '|'
 * ways += f(str,0,i-1,false) * f(str,i+1,n-1,false)
 * c) 如果char[i] = '^'
 * ways += f(str,0,i-1,true) * f(str,i+1,n-1,true)
 * ways += f(str,0,i-1,false) * f(str,i+1,n-1,false)
 *
 * 该动态规划
 * 因为有三个可变参数，其中一个是bool值，两张表搞定，一张true表一张false表
 * 需要注意，该表中
 * 1、因为L<=R,所有一半的地方不需要填写
 * 2、因为L,R只讨论字符，所有运算符的地方不需要填写
 *
 */
public class Code02_ExpressionNumber {

	public static boolean isValid(char[] exp) {
		if ((exp.length & 1) == 0) {
			return false;
		}
		for (int i = 0; i < exp.length; i = i + 2) {
			if ((exp[i] != '1') && (exp[i] != '0')) {
				return false;
			}
		}
		for (int i = 1; i < exp.length; i = i + 2) {
			if ((exp[i] != '&') && (exp[i] != '|') && (exp[i] != '^')) {
				return false;
			}
		}
		return true;
	}

	public static int num1(String express, boolean desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		if (!isValid(exp)) {
			return 0;
		}
		return f(exp, desired, 0, exp.length - 1);
	}

	// str[L..R] 返回期待为desired的方法数
	// 潜台词：L R 必须是偶数位置
	public static int f(char[] str, boolean desired, int L, int R) {
		if (L == R) { // base case 1
			if (str[L] == '1') {
				return desired ? 1 : 0;
			} else { // '0'
				return desired ? 0 : 1;
			}
		}

		// L..R
		int res = 0;
		if (desired) { // 期待为true
			// i位置尝试L..R范围上的每一个逻辑符号，都是最后结合的
			for (int i = L + 1; i < R; i += 2) {
				// exp[i] 一定压中逻辑符号
				switch (str[i]) {
				case '&':
					res += f(str, true, L, i - 1) * f(str, true, i + 1, R);
					break;
				case '|':
					res += f(str, true, L, i - 1) * f(str, false, i + 1, R);
					res += f(str, false, L, i - 1) * f(str, true, i + 1, R);
					res += f(str, true, L, i - 1) * f(str, true, i + 1, R);
					break;
				case '^':
					res += f(str, true, L, i - 1) * f(str, false, i + 1, R);
					res += f(str, false, L, i - 1) * f(str, true, i + 1, R);
					break;
				}
			}
		} else { // 期待为false
			for (int i = L + 1; i < R; i += 2) {
				switch (str[i]) {
				case '&':
					res += f(str, false, L, i - 1) * f(str, true, i + 1, R);
					res += f(str, true, L, i - 1) * f(str, false, i + 1, R);
					res += f(str, false, L, i - 1) * f(str, false, i + 1, R);
					break;
				case '|':
					res += f(str, false, L, i - 1) * f(str, false, i + 1, R);
					break;
				case '^':
					res += f(str, true, L, i - 1) * f(str, true, i + 1, R);
					res += f(str, false, L, i - 1) * f(str, false, i + 1, R);
					break;
				}
			}
		}
		return res;
	}

	public static int dpLive(String express, boolean desired) {
		char[] str = express.toCharArray();
		int N = str.length;
		int[][] tMap = new int[N][N];
		int[][] fMap = new int[N][N];
		for (int i = 0; i < N; i += 2) {
			//base case，都是对角线的位置
			tMap[i][i] = str[i] == '1' ? 1 : 0;
			fMap[i][i] = str[i] == '0' ? 1 : 0;
		}
		//n-1，只有一个值就是row=col=n-1的位置已经填过了，n-2符号位，不讨论
		for (int row = N - 3; row >= 0; row -= 2) {//步长为2
			//col=row位置已经填好，col=row+1符号位不讨论
			for (int col = row + 2; col < N; col += 2) {//步长为2
				// row..col tMap fMap
				// 从最后一行开始，填好后往上填写，row+1是因为这里需要讨论运算符
				for (int i = row + 1; i < col; i += 2) {//步长为2
					switch (str[i]) {
					case '&':
						tMap[row][col] += tMap[row][i - 1] * tMap[i + 1][col];
						break;
					case '|':
						tMap[row][col] += tMap[row][i - 1] * fMap[i + 1][col];
						tMap[row][col] += fMap[row][i - 1] * tMap[i + 1][col];
						tMap[row][col] += tMap[row][i - 1] * tMap[i + 1][col];
						break;
					case '^':
						tMap[row][col] += tMap[row][i - 1] * fMap[i + 1][col];
						tMap[row][col] += fMap[row][i - 1] * tMap[i + 1][col];
						break;
					}
					switch (str[i]) {
					case '&':
						fMap[row][col] += fMap[row][i - 1] * tMap[i + 1][col];
						fMap[row][col] += tMap[row][i - 1] * fMap[i + 1][col];
						fMap[row][col] += fMap[row][i - 1] * fMap[i + 1][col];
						break;
					case '|':
						fMap[row][col] += fMap[row][i - 1] * fMap[i + 1][col];
						break;
					case '^':
						fMap[row][col] += tMap[row][i - 1] * tMap[i + 1][col];
						fMap[row][col] += fMap[row][i - 1] * fMap[i + 1][col];
						break;
					}	
			    }
		    }
		}
		return desired ? tMap[0][N-1]  : fMap[0][N-1];	
	}

	public static int dp(String s, boolean d) {
		char[] str = s.toCharArray();
		int N = str.length;
		int[][] tMap = new int[N][N];
		int[][] fMap = new int[N][N];
		for (int i = 0; i < N; i += 2) {
			tMap[i][i] = str[i] == '1' ? 1 : 0;
			fMap[i][i] = str[i] == '0' ? 1 : 0;
		}
		for (int row = N - 3; row >= 0; row = row - 2) {
			for (int col = row + 2; col < N; col = col + 2) {
				// row..col tMap fMap
				for (int i = row + 1; i < col; i += 2) {
					switch (str[i]) {
					case '&':
						tMap[row][col] += tMap[row][i - 1] * tMap[i + 1][col];
						break;
					case '|':
						tMap[row][col] += tMap[row][i - 1] * fMap[i + 1][col];
						tMap[row][col] += fMap[row][i - 1] * tMap[i + 1][col];
						tMap[row][col] += tMap[row][i - 1] * tMap[i + 1][col];
						break;
					case '^':
						tMap[row][col] += tMap[row][i - 1] * fMap[i + 1][col];
						tMap[row][col] += fMap[row][i - 1] * tMap[i + 1][col];
						break;
					}
					switch (str[i]) {
					case '&':
						fMap[row][col] += fMap[row][i - 1] * tMap[i + 1][col];
						fMap[row][col] += tMap[row][i - 1] * fMap[i + 1][col];
						fMap[row][col] += fMap[row][i - 1] * fMap[i + 1][col];
						break;
					case '|':
						fMap[row][col] += fMap[row][i - 1] * fMap[i + 1][col];
						break;
					case '^':
						fMap[row][col] += tMap[row][i - 1] * tMap[i + 1][col];
						fMap[row][col] += fMap[row][i - 1] * fMap[i + 1][col];
						break;
					}
				}
			}
		}
		return d ? tMap[0][N - 1] : fMap[0][N - 1];
	}

	public static int num2(String express, boolean desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		if (!isValid(exp)) {
			return 0;
		}
		int[][] t = new int[exp.length][exp.length];
		int[][] f = new int[exp.length][exp.length];
		t[0][0] = exp[0] == '0' ? 0 : 1;
		f[0][0] = exp[0] == '1' ? 0 : 1;
		for (int i = 2; i < exp.length; i += 2) {
			t[i][i] = exp[i] == '0' ? 0 : 1;
			f[i][i] = exp[i] == '1' ? 0 : 1;
			for (int j = i - 2; j >= 0; j -= 2) {
				for (int k = j; k < i; k += 2) {
					if (exp[k + 1] == '&') {
						t[j][i] += t[j][k] * t[k + 2][i];
						f[j][i] += (f[j][k] + t[j][k]) * f[k + 2][i] + f[j][k] * t[k + 2][i];
					} else if (exp[k + 1] == '|') {
						t[j][i] += (f[j][k] + t[j][k]) * t[k + 2][i] + t[j][k] * f[k + 2][i];
						f[j][i] += f[j][k] * f[k + 2][i];
					} else {
						t[j][i] += f[j][k] * t[k + 2][i] + t[j][k] * f[k + 2][i];
						f[j][i] += f[j][k] * f[k + 2][i] + t[j][k] * t[k + 2][i];
					}
				}
			}
		}
		return desired ? t[0][t.length - 1] : f[0][f.length - 1];
	}

	public static void main(String[] args) {
		String express = "1^0&0|1&1^0&0^1|0|1&1";
		boolean desired = true;
		System.out.println(num1(express, desired));
		System.out.println(num2(express, desired));
		System.out.println(dp(express, desired));
		System.out.println(dpLive(express, desired));
	}

}
