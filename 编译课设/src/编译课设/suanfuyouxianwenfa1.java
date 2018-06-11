package 编译课设;

//
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
//测试数据 1 构建关系表好着
//8
//E->E+T
//E->T
//T->T*F
//T->F
//F->P^F
//F->P
//P->(E)
//P->i

//测试数据2（默认输入串为i+i#）
//6
//S->#E#
//P->i
//F->P
//T->F
//E->T
//E->E+T

@SuppressWarnings("unused")
public class suanfuyouxianwenfa1 {
	static int step = 0;// 记录第几步从第一步开始
	static int MAX = 507;
	char[][] relation = new char[MAX][MAX];// 关系表数组
	static List<Character> VT = new ArrayList<Character>();// 终结符集
	static List<WF> VN_set = new ArrayList<WF>();// 非终结符 用来分离并存储产生式左部和右部
	static Map<String, Integer> VN_dic = new HashMap<String, Integer>();// 记录VN非终结符
	static int[] used = new int[MAX];// 用来记录终结符为第几个终结符
	static String s1;// 产生式左部的引用变量
	static String s2;// 产生式右部的引用变量
	static boolean[][] F;// firstvt集
	static boolean[][] Fl;// lastvt集
	static char[][] table;// 文法优先表
	static char currentRelation;// 优先关系
	static int index = -1;// 当前符号所在下标
	static Stack stack = new Stack();//
	static Stack stackl = new Stack();
	String stackString = null;
	static String str2 = "i+i#";
	static char a;// 当前符号
	static Stack<Character> S = new Stack<Character>();

	// 查找某VN（非终结符）所在的VN_set list中所在的位置
	public static int findindexFromVN_set(char P) {
		for (int i = 0; i < VN_set.size(); i++) {
			if (VN_set.get(i).left.charAt(0) == P) {
				return i;
			}
		}
		return -1;
	}

	// 查找某VT（终结符）所在的VT list中的位置
	public static int findindexFromVT(char a) {
		return VT.indexOf(a);
	}

	// 构建firstvt用,把(P,a)推进栈//书上91页右下角程序思路
	public static void insert(char P, char a) {
		// System.out.println("P->"+P+"|"+"findindexFromVN_set(P)->"+findindexFromVN_set(P));
		// System.out.println("a->"+a+"|"+"VT.indexOf(a)->"+VT.indexOf(a));
		if (!F[findindexFromVN_set(P)][VT.indexOf(a)]) {
			F[findindexFromVN_set(P)][VT.indexOf(a)] = true;
			stack.push("" + '(' + P + ',' + a + ')');// 把(P,a)推进stack栈end
		}

	}

	// 构建lastvt集用,把(P,a)推进栈//书上91页右下角程序思路
	public static void insertl(char P, char a) {
		// System.out.println("P->"+P+"|"+"findindexFromVN_set(P)->"+findindexFromVN_set(P));
		// System.out.println("a->"+a+"|"+"VT.indexOf(a)->"+VT.indexOf(a));
		if (!Fl[findindexFromVN_set(P)][VT.indexOf(a)]) {
			Fl[findindexFromVN_set(P)][VT.indexOf(a)] = true;
			stackl.push("" + '(' + P + ',' + a + ')');// 把(P,a)推进stack栈end
		}

	}

	// 构造(非终极符)的firstvt函数//书上91页右下角程序思路
	public static void makefirst() {
		F = new boolean[VN_set.size()][VT.size()];
		// 先初始化F集
		for (int i = 0; i < VN_set.size(); i++) {
			for (int j = 0; j < VT.size(); j++) {
				F[i][j] = false;
			}
		}

		for (int i = 0; i < VN_set.size(); i++) {
			for (int j = 0; j < VN_set.get(i).right.size(); j++) {
				if (Character.isUpperCase(VN_set.get(i).left.charAt(0))
						&& !Character.isUpperCase(VN_set.get(i).right.get(j).charAt(0))) {
					insert(VN_set.get(i).left.charAt(0), VN_set.get(i).right.get(j).charAt(0));
				}
				if ((VN_set.get(i).right.get(j).length() > 2) && Character.isUpperCase(VN_set.get(i).left.charAt(0))
						&& Character.isUpperCase(VN_set.get(i).right.get(j).charAt(0))
						&& !Character.isUpperCase(VN_set.get(i).right.get(j).charAt(1))) {
					insert(VN_set.get(i).left.charAt(0), VN_set.get(i).right.get(j).charAt(1));
				}
			}
		}
		while (!stack.isEmpty()) {
			String str = (String) stack.pop();// str=(P,a);
			char Q = str.charAt(1);
			char a = str.charAt(3);
			for (int i = 0; i < VN_set.size(); i++) {
				for (int j = 0; j < VN_set.get(i).right.size(); j++) {
					if (Character.isUpperCase(VN_set.get(i).left.charAt(0))
							&& VN_set.get(i).right.get(j).charAt(0) == Q) {
						insert(VN_set.get(i).left.charAt(0), a);
					}
				}
			}
		}

		for (int i = 0; i < F.length; i++) {
			System.out.printf("%c :", VN_set.get(i).left.charAt(0));
			for (int j = 0; j < F[i].length; j++) {
				if (F[i][j] == true) {
					System.out.printf(" %c", VT.get(j));
				}
			}
			System.out.println();
		}
	}

	// 构造(非终极符)的lastvt函数//书上91页右下角程序思路
	public static void makelast() {
		Fl = new boolean[VN_set.size()][VT.size()];
		for (int i = 0; i < VN_set.size(); i++) {
			for (int j = 0; j < VT.size(); j++) {
				Fl[i][j] = false;
			}
		}

		for (int i = 0; i < VN_set.size(); i++) {
			for (int j = 0; j < VN_set.get(i).right.size(); j++) {
				if (Character.isUpperCase(VN_set.get(i).left.charAt(0)) && !Character
						.isUpperCase(VN_set.get(i).right.get(j).charAt(VN_set.get(i).right.get(j).length() - 1))) {
					insertl(VN_set.get(i).left.charAt(0),
							VN_set.get(i).right.get(j).charAt(VN_set.get(i).right.get(j).length() - 1));
				}
				if ((VN_set.get(i).right.get(j).length() > 2) && Character.isUpperCase(VN_set.get(i).left.charAt(0))
						&& Character
								.isUpperCase(VN_set.get(i).right.get(j).charAt(VN_set.get(i).right.get(j).length() - 1))
						&& !Character.isUpperCase(
								VN_set.get(i).right.get(j).charAt(VN_set.get(i).right.get(j).length() - 2))) {
					insertl(VN_set.get(i).left.charAt(0),
							VN_set.get(i).right.get(j).charAt(VN_set.get(i).right.get(j).length() - 2));
				}
			}
		}
		while (!stackl.isEmpty()) {
			String str = (String) stackl.pop();// str=(P,a);
			char Q = str.charAt(1);
			char a = str.charAt(3);
			for (int i = 0; i < VN_set.size(); i++) {
				for (int j = 0; j < VN_set.get(i).right.size(); j++) {
					if (Character.isUpperCase(VN_set.get(i).left.charAt(0))
							&& VN_set.get(i).right.get(j).charAt(VN_set.get(i).right.get(j).length() - 1) == Q) {
						insertl(VN_set.get(i).left.charAt(0), a);
					}
				}
			}
		}

		for (int i = 0; i < Fl.length; i++) {
			System.out.printf("%c :", VN_set.get(i).left.charAt(0));
			for (int j = 0; j < Fl[i].length; j++) {
				if (Fl[i][j] == true) {
					System.out.printf(" %c", VT.get(j));
				}
			}
			System.out.println();
		}
	}

	// 构造文法优先表函数//书上92页左上角程序思路
	public static void make_table() {
		table = new char[VT.size()][VT.size()];
		for (int i = 0; i < VN_set.size(); i++) {
			for (int j = 0; j < VN_set.get(i).right.size(); j++) {
				String right = VN_set.get(i).right.get(j);
				for (int t = 0; t < VN_set.get(i).right.get(j).length() - 1; t++) {
					char c1 = right.charAt(t);
					char c2 = right.charAt(t + 1);
					if (!Character.isUpperCase(c1) && !Character.isUpperCase(c2)) {
						table[findindexFromVT(c1)][findindexFromVT(c2)] = '=';
					}
					if ((t < (VN_set.get(i).right.get(j).length() - 2)) && !Character.isUpperCase(c1)
							&& Character.isUpperCase(c2) && !Character.isUpperCase(right.charAt(t + 2))) {
						table[findindexFromVT(c1)][findindexFromVT(right.charAt(t + 2))] = '=';
					}
					if (!Character.isUpperCase(c1) && Character.isUpperCase(c2)) {
						for (int i1 = 0; i1 < F[findindexFromVN_set(c2)].length; i1++) {
							if (F[findindexFromVN_set(c2)][i1] == true) {
								table[findindexFromVT(c1)][i1] = '<';
							}

						}
					}
					if (Character.isUpperCase(c1) && !Character.isUpperCase(c2)) {
						for (int i1 = 0; i1 < Fl[findindexFromVN_set(c1)].length; i1++) {
							if (Fl[findindexFromVN_set(c1)][i1] == true) {
								table[i1][findindexFromVT(c2)] = '>';
							}
						}
					}
				}
			}
		}
		System.out.println("*********************算符优先关系表***************************");
		System.out.println("\n--------------------------------------------------------");

		for (int i = -1; i < VT.size(); i++) {

			for (int j = -1; j < VT.size(); j++) {
				if (i == -1 && j == -1) {
					System.out.printf("|    |");
					continue;
				}
				if (i == -1) {
					System.out.printf("%4c  |", VT.get(j));
				} else {
					if (j == -1) {
						System.out.printf("|%4c |", VT.get(i));
						continue;
					}
					System.out.printf("%4c  |", table[i][j]);
				}

			}
			System.out.println("\n--------------------------------------------------------");
		}

	}

	// 移进规约函数//书上93页右上角程序思路
	public static void move_reduction(String str) {// str="i+i#"
		char Q;// 用来指向终结符
		int k = 0;// 一开始让k=0,可以通过stack.get(k);查找到对应位置的元素
		int j = 0;// j用来指向优先级小于终结符Q的终结符
		char c; // 保存弹出来的字符
		String s = "";// 用来保存待归约的串
		S.push('#');
		do {
			index++;// 当前符号后移
			a = str.charAt(index);
			if (findindexFromVT((char) S.peek()) != -1) {// 判断栈顶是否是终结符
				j = k;
			} else {
				j = k - 1;
			}
			while (table[findindexFromVT(S.get(j))][findindexFromVT(a)] == '>') {
				currentRelation = table[findindexFromVT(S.get(j))][findindexFromVT(a)];
				do {
					Q = S.get(j);// Q指向j当前所指向的终结符 j指向离栈顶最远的且优先关系>当前符号的终结符
					if (findindexFromVT(S.get(j - 1)) != -1) {
						j = j - 1;

					} else {
						j = j - 2;
					} // j指向优先级小于栈顶终结符Q的终结符则退出
				} while (table[findindexFromVT(S.get(j))][findindexFromVT(Q)] != '<');//
				// 归约前打印说明归约
				step++;
				System.out.printf("%2s |%-5s%5s%10s%8s", step, printfStatck(), currentRelation, a, residue(str2));
				// 把S[j+1]。。。。S[k]规约为某个N
				// 通过出栈，提炼S[k]到S[j+1]到s中
				int key = k;// key从k移动到j
				s = "";
				do {
					c = S.pop();
					s = c + s;
					key--;
				} while (key != j);

				int flag = 0;
				// 把S[j+1]。。。。S[k]规约为某个N
				// 书上93页指出'N'是指那样一个产生式的左部符号，次产生式的右部和S[j+1]。。。S[k]构成如下一一对应的关系：
				// 自左至右,终结符对终结符，非终结符对非终结符，而且对应的终结符相同。

				for (int i = 0; i < VN_set.size(); i++) {
					for (int j1 = 0; j1 < VN_set.get(i).right.size(); j1++) {
						char left = VN_set.get(i).left.charAt(0);
						String right = VN_set.get(i).right.get(j1);
						if (Character.isUpperCase(left) && right.equals(s)) {

							S.push(left);
							System.out.println("   归约：" + left + "->" + right);
							s = "";// 清空
							flag = 1;
							break;
						} // 长度一样
						else if (Character.isUpperCase(left) && check(right, s)) {
							S.push(left);
							System.out.println("   归约：" + left + "->" + right);
							s = "";// 清空
							flag = 1;
							break;
						}
					}
					if (flag == 1) {
						break;
					}
				}

			}

			if (table[findindexFromVT(S.get(j))][findindexFromVT(a)] == '<'
					|| table[findindexFromVT(S.get(j))][findindexFromVT(a)] == '=') {
				currentRelation = table[findindexFromVT(S.get(j))][findindexFromVT(a)];
				k = k + 1;
				step++;
				System.out.printf("%2s |%-5s%5s%10s%8s", step, printfStatck(), currentRelation, a, residue(str2));
				S.push(a);// 压栈
				System.out.printf("%10s", "移进：把" + a + "移进");
				System.out.println();

			} else {
				System.out.println("出错了！");
			}

		} while (a != '#');

	}

	// 检查两个字符串是否长度相同
	private static boolean check(String right, String s3) {
		if (right.length() != s3.length()) {
			return false;
		}

		// TODO Auto-generated method stub
		for (int i = 0; i < right.length(); i++) {
			if (Character.isUpperCase(right.charAt(i)) != Character.isUpperCase(s3.charAt(i)))
				return false;

		}
		return true;

	}

	// main方法**
	public static void main(String[] args) {
		// 初始化used(记录VT的表)赋初值为-1 表示其对应的值没有出现过
		initused();
		VT.clear();
		String n;// 统计文法的个数
		String s = null;// 用来暂时性存储当前输入的文法
		Scanner reader = new Scanner(System.in);
		n = reader.nextLine();
		for (int i = 0; i < Integer.valueOf(n); i++) {
			s = reader.nextLine();
			// 分离当前输入的产生式
			int i1 = s.lastIndexOf('-');
			s1 = s.substring(0, i1);// 产生式左部
			s2 = s.substring(i1 + 2);// 产生式右部
			if (!VN_dic.containsKey(s1)) {
				VN_set.add(new WF(s1.toString()));//
				VN_dic.put(s1.toString(), VN_set.size() - 1);// 用VN_dic记录非终结符
			}
			int x = VN_dic.get(s1);// 在VN_dic中查找当前输入的产生式左部对应的位置
			VN_set.get(x).insert(s2);// 在对应的产生式集合VN_set中保存对应产生式左部的右部
			for (int k = 0; k < s2.length(); k++) {
				if (!Character.isUpperCase(s2.charAt(k))) {
					if (used[s2.charAt(k)] > -1)// 初始化use[][]中的值为-1,如果大于-1则该字符被记录过
						continue;
					VT.add(s2.charAt(k));
					used[s2.charAt(k)] = VT.size() - 1;// 某vt第一个出现则记为0
				}
			}

		}
		System.out.println("**********VT集的大小为:****************");
		System.out.println(VT.size());
		System.out.println("*********VT集**************");
		for (int i = 0; i < VT.size(); i++) {
			System.out.printf("%c ", VT.get(i));
		}
		System.out.println();
		for (int i = 0; i < VT.size(); i++) {
			System.out.printf("%d ", used[VT.get(i)]);
		}
		System.out.println();
		System.out.println("************VN集********************");
		for (int i = 0; i < VN_set.size(); i++) {
			System.out.printf("%s ", VN_set.get(i).left);
		}
		System.out.println();
		for (int i = 0; i < VN_set.size(); i++) {
			System.out.printf("%d ", VN_dic.get(VN_set.get(i).left));
		}
		System.out.println();
		System.out.println("**********产生式*****************");
		for (int i = 0; i < VN_set.size(); i++) {
			VN_set.get(i).print();
		}
		System.out.println("***********Firstvt集*********************");
		makefirst();
		System.out.println("***********Lastvt集*********************");
		makelast();
		make_table();
		printstep();
		move_reduction(str2);
		System.out.printf("归约结束！    默认输入串为:" + "'" + "%s" + "'" + " 算符优先分析规约后最后的栈中元素为:" + "'" + "%s" + "'", str2,
				printfStatck());
	}

	// 打印展示归约步鄹的格式
	private static void printstep() {
		// TODO Auto-generated method stub
		System.out.printf("%2s|%-10s%10s%10s%10s%10s", "步骤", "栈中", "优先关系", "当前符号", "剩余符号", "动作");
		System.out.println();
	}

	// 初始化use表使表中的值初始化为-1
	private static void initused() {
		// TODO Auto-generated method stub
		for (int i = 0; i < used.length; i++) {
			used[i] = -1;
		}
	}

	// 剩余输入符号
	public static String residue(String str) {
		String s = str.substring(index + 1);
		return s;
	}

	/**
	 * 集合遍历方式(输出顺序：从栈底到栈顶)
	 * 
	 * @return
	 */
	public static String printfStatck() {
		// System.out.print("当前栈:");
		String s = "";
		for (Character x : S) {
			// System.out.print(x);
			s = s + x;
		}
		return s;

	}

}