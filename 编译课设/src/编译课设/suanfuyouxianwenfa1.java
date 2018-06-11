package �������;

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
//�������� 1 ������ϵ�����
//8
//E->E+T
//E->T
//T->T*F
//T->F
//F->P^F
//F->P
//P->(E)
//P->i

//��������2��Ĭ�����봮Ϊi+i#��
//6
//S->#E#
//P->i
//F->P
//T->F
//E->T
//E->E+T

@SuppressWarnings("unused")
public class suanfuyouxianwenfa1 {
	static int step = 0;// ��¼�ڼ����ӵ�һ����ʼ
	static int MAX = 507;
	char[][] relation = new char[MAX][MAX];// ��ϵ������
	static List<Character> VT = new ArrayList<Character>();// �ս����
	static List<WF> VN_set = new ArrayList<WF>();// ���ս�� �������벢�洢����ʽ�󲿺��Ҳ�
	static Map<String, Integer> VN_dic = new HashMap<String, Integer>();// ��¼VN���ս��
	static int[] used = new int[MAX];// ������¼�ս��Ϊ�ڼ����ս��
	static String s1;// ����ʽ�󲿵����ñ���
	static String s2;// ����ʽ�Ҳ������ñ���
	static boolean[][] F;// firstvt��
	static boolean[][] Fl;// lastvt��
	static char[][] table;// �ķ����ȱ�
	static char currentRelation;// ���ȹ�ϵ
	static int index = -1;// ��ǰ���������±�
	static Stack stack = new Stack();//
	static Stack stackl = new Stack();
	String stackString = null;
	static String str2 = "i+i#";
	static char a;// ��ǰ����
	static Stack<Character> S = new Stack<Character>();

	// ����ĳVN�����ս�������ڵ�VN_set list�����ڵ�λ��
	public static int findindexFromVN_set(char P) {
		for (int i = 0; i < VN_set.size(); i++) {
			if (VN_set.get(i).left.charAt(0) == P) {
				return i;
			}
		}
		return -1;
	}

	// ����ĳVT���ս�������ڵ�VT list�е�λ��
	public static int findindexFromVT(char a) {
		return VT.indexOf(a);
	}

	// ����firstvt��,��(P,a)�ƽ�ջ//����91ҳ���½ǳ���˼·
	public static void insert(char P, char a) {
		// System.out.println("P->"+P+"|"+"findindexFromVN_set(P)->"+findindexFromVN_set(P));
		// System.out.println("a->"+a+"|"+"VT.indexOf(a)->"+VT.indexOf(a));
		if (!F[findindexFromVN_set(P)][VT.indexOf(a)]) {
			F[findindexFromVN_set(P)][VT.indexOf(a)] = true;
			stack.push("" + '(' + P + ',' + a + ')');// ��(P,a)�ƽ�stackջend
		}

	}

	// ����lastvt����,��(P,a)�ƽ�ջ//����91ҳ���½ǳ���˼·
	public static void insertl(char P, char a) {
		// System.out.println("P->"+P+"|"+"findindexFromVN_set(P)->"+findindexFromVN_set(P));
		// System.out.println("a->"+a+"|"+"VT.indexOf(a)->"+VT.indexOf(a));
		if (!Fl[findindexFromVN_set(P)][VT.indexOf(a)]) {
			Fl[findindexFromVN_set(P)][VT.indexOf(a)] = true;
			stackl.push("" + '(' + P + ',' + a + ')');// ��(P,a)�ƽ�stackջend
		}

	}

	// ����(���ռ���)��firstvt����//����91ҳ���½ǳ���˼·
	public static void makefirst() {
		F = new boolean[VN_set.size()][VT.size()];
		// �ȳ�ʼ��F��
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

	// ����(���ռ���)��lastvt����//����91ҳ���½ǳ���˼·
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

	// �����ķ����ȱ���//����92ҳ���Ͻǳ���˼·
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
		System.out.println("*********************������ȹ�ϵ��***************************");
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

	// �ƽ���Լ����//����93ҳ���Ͻǳ���˼·
	public static void move_reduction(String str) {// str="i+i#"
		char Q;// ����ָ���ս��
		int k = 0;// һ��ʼ��k=0,����ͨ��stack.get(k);���ҵ���Ӧλ�õ�Ԫ��
		int j = 0;// j����ָ�����ȼ�С���ս��Q���ս��
		char c; // ���浯�������ַ�
		String s = "";// �����������Լ�Ĵ�
		S.push('#');
		do {
			index++;// ��ǰ���ź���
			a = str.charAt(index);
			if (findindexFromVT((char) S.peek()) != -1) {// �ж�ջ���Ƿ����ս��
				j = k;
			} else {
				j = k - 1;
			}
			while (table[findindexFromVT(S.get(j))][findindexFromVT(a)] == '>') {
				currentRelation = table[findindexFromVT(S.get(j))][findindexFromVT(a)];
				do {
					Q = S.get(j);// Qָ��j��ǰ��ָ����ս�� jָ����ջ����Զ�������ȹ�ϵ>��ǰ���ŵ��ս��
					if (findindexFromVT(S.get(j - 1)) != -1) {
						j = j - 1;

					} else {
						j = j - 2;
					} // jָ�����ȼ�С��ջ���ս��Q���ս�����˳�
				} while (table[findindexFromVT(S.get(j))][findindexFromVT(Q)] != '<');//
				// ��Լǰ��ӡ˵����Լ
				step++;
				System.out.printf("%2s |%-5s%5s%10s%8s", step, printfStatck(), currentRelation, a, residue(str2));
				// ��S[j+1]��������S[k]��ԼΪĳ��N
				// ͨ����ջ������S[k]��S[j+1]��s��
				int key = k;// key��k�ƶ���j
				s = "";
				do {
					c = S.pop();
					s = c + s;
					key--;
				} while (key != j);

				int flag = 0;
				// ��S[j+1]��������S[k]��ԼΪĳ��N
				// ����93ҳָ��'N'��ָ����һ������ʽ���󲿷��ţ��β���ʽ���Ҳ���S[j+1]������S[k]��������һһ��Ӧ�Ĺ�ϵ��
				// ��������,�ս�����ս�������ս���Է��ս�������Ҷ�Ӧ���ս����ͬ��

				for (int i = 0; i < VN_set.size(); i++) {
					for (int j1 = 0; j1 < VN_set.get(i).right.size(); j1++) {
						char left = VN_set.get(i).left.charAt(0);
						String right = VN_set.get(i).right.get(j1);
						if (Character.isUpperCase(left) && right.equals(s)) {

							S.push(left);
							System.out.println("   ��Լ��" + left + "->" + right);
							s = "";// ���
							flag = 1;
							break;
						} // ����һ��
						else if (Character.isUpperCase(left) && check(right, s)) {
							S.push(left);
							System.out.println("   ��Լ��" + left + "->" + right);
							s = "";// ���
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
				S.push(a);// ѹջ
				System.out.printf("%10s", "�ƽ�����" + a + "�ƽ�");
				System.out.println();

			} else {
				System.out.println("�����ˣ�");
			}

		} while (a != '#');

	}

	// ��������ַ����Ƿ񳤶���ͬ
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

	// main����**
	public static void main(String[] args) {
		// ��ʼ��used(��¼VT�ı�)����ֵΪ-1 ��ʾ���Ӧ��ֵû�г��ֹ�
		initused();
		VT.clear();
		String n;// ͳ���ķ��ĸ���
		String s = null;// ������ʱ�Դ洢��ǰ������ķ�
		Scanner reader = new Scanner(System.in);
		n = reader.nextLine();
		for (int i = 0; i < Integer.valueOf(n); i++) {
			s = reader.nextLine();
			// ���뵱ǰ����Ĳ���ʽ
			int i1 = s.lastIndexOf('-');
			s1 = s.substring(0, i1);// ����ʽ��
			s2 = s.substring(i1 + 2);// ����ʽ�Ҳ�
			if (!VN_dic.containsKey(s1)) {
				VN_set.add(new WF(s1.toString()));//
				VN_dic.put(s1.toString(), VN_set.size() - 1);// ��VN_dic��¼���ս��
			}
			int x = VN_dic.get(s1);// ��VN_dic�в��ҵ�ǰ����Ĳ���ʽ�󲿶�Ӧ��λ��
			VN_set.get(x).insert(s2);// �ڶ�Ӧ�Ĳ���ʽ����VN_set�б����Ӧ����ʽ�󲿵��Ҳ�
			for (int k = 0; k < s2.length(); k++) {
				if (!Character.isUpperCase(s2.charAt(k))) {
					if (used[s2.charAt(k)] > -1)// ��ʼ��use[][]�е�ֵΪ-1,�������-1����ַ�����¼��
						continue;
					VT.add(s2.charAt(k));
					used[s2.charAt(k)] = VT.size() - 1;// ĳvt��һ���������Ϊ0
				}
			}

		}
		System.out.println("**********VT���Ĵ�СΪ:****************");
		System.out.println(VT.size());
		System.out.println("*********VT��**************");
		for (int i = 0; i < VT.size(); i++) {
			System.out.printf("%c ", VT.get(i));
		}
		System.out.println();
		for (int i = 0; i < VT.size(); i++) {
			System.out.printf("%d ", used[VT.get(i)]);
		}
		System.out.println();
		System.out.println("************VN��********************");
		for (int i = 0; i < VN_set.size(); i++) {
			System.out.printf("%s ", VN_set.get(i).left);
		}
		System.out.println();
		for (int i = 0; i < VN_set.size(); i++) {
			System.out.printf("%d ", VN_dic.get(VN_set.get(i).left));
		}
		System.out.println();
		System.out.println("**********����ʽ*****************");
		for (int i = 0; i < VN_set.size(); i++) {
			VN_set.get(i).print();
		}
		System.out.println("***********Firstvt��*********************");
		makefirst();
		System.out.println("***********Lastvt��*********************");
		makelast();
		make_table();
		printstep();
		move_reduction(str2);
		System.out.printf("��Լ������    Ĭ�����봮Ϊ:" + "'" + "%s" + "'" + " ������ȷ�����Լ������ջ��Ԫ��Ϊ:" + "'" + "%s" + "'", str2,
				printfStatck());
	}

	// ��ӡչʾ��Լ��۸�ĸ�ʽ
	private static void printstep() {
		// TODO Auto-generated method stub
		System.out.printf("%2s|%-10s%10s%10s%10s%10s", "����", "ջ��", "���ȹ�ϵ", "��ǰ����", "ʣ�����", "����");
		System.out.println();
	}

	// ��ʼ��use��ʹ���е�ֵ��ʼ��Ϊ-1
	private static void initused() {
		// TODO Auto-generated method stub
		for (int i = 0; i < used.length; i++) {
			used[i] = -1;
		}
	}

	// ʣ���������
	public static String residue(String str) {
		String s = str.substring(index + 1);
		return s;
	}

	/**
	 * ���ϱ�����ʽ(���˳�򣺴�ջ�׵�ջ��)
	 * 
	 * @return
	 */
	public static String printfStatck() {
		// System.out.print("��ǰջ:");
		String s = "";
		for (Character x : S) {
			// System.out.print(x);
			s = s + x;
		}
		return s;

	}

}