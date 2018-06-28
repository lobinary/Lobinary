package com.lobinary.算法.acm.北大ACM库;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.lobinary.算法.acm.AU;

public class Q_1002_电话号码问题_487_3279 {

	public static void main(String[] args) {
		 AU.check();
	}

	public static void run(Scanner i) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int size = Integer.parseInt(i.nextLine());
		for (int j = 0; j < size; j++) {
			String s = i.nextLine();
			char[] ca = s.toCharArray();
			StringBuilder sb = new StringBuilder();
			for (int k = 0; k < ca.length; k++) {
				if(sb.length()==3){
					sb.append("-");
				}
				char c = ca[k];
				if (c >= 48 && c <= 57) {
					sb.append((int)(c - 48));
				} else if (c >= 65 && c <= 90) {
					sb.append(get(c));
				}
			}
			if(map.containsKey(sb.toString())){
				map.put(sb.toString(), map.get(sb.toString())+1);
			}else{
				map.put(sb.toString(), 1);
			}
		}

		Object[] aa = map.keySet().toArray();
		Arrays.sort(aa);
		boolean 无重复数据 = true;
		for (Object key:aa) {
			if(map.get(key)>1){
				无重复数据 = false;
				System.out.println(key + " " + map.get(key));
			}
		}
		if(无重复数据){
			System.out.println("No duplicates.");
		}
	}

	public static int get(char c) {
		switch (c) {
		case 'A':
			return 2;
		case 'B':
			return 2;
		case 'C':
			return 2;
		case 'D':
			return 3;
		case 'E':
			return 3;
		case 'F':
			return 3;
		case 'G':
			return 4;
		case 'H':
			return 4;
		case 'I':
			return 4;
		case 'J':
			return 5;
		case 'K':
			return 5;
		case 'L':
			return 5;
		case 'M':
			return 6;
		case 'N':
			return 6;
		case 'O':
			return 6;
		case 'P':
			return 7;
		case 'R':
			return 7;
		case 'S':
			return 7;
		case 'T':
			return 8;
		case 'U':
			return 8;
		case 'V':
			return 8;
		case 'W':
			return 9;
		case 'X':
			return 9;
		case 'Y':
			return 9;

		default:
			return 0;
		}
	}

	/**
	 * 该解决方案摘自网络
	 * 
	 * @param in
	 */
	public static void tt(Scanner in) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		int col = in.nextInt();
		for (int i = 0; i < col; i++) {
			// 每读一次的处理
			StringBuffer sb = new StringBuffer();
			String next = in.next();
			int len = next.length();
			for (int j = 0; j < len; j++) {
				char nc = next.charAt(j);
				if (nc != '-') {
					int gm = 0;
					if (nc >= '0' && nc <= '9')
						gm = (nc - 48);
					else
						gm = getNum(nc);
					sb.append(gm);
				}
			}
			String key = sb.toString();
			if (hm.containsKey(key)) {
				hm.put(key, hm.get(key) + 1);
			} else {
				hm.put(key, 1);
			}
			sb.setLength(0);
		}
		Object[] keys = hm.keySet().toArray();
		Arrays.sort(keys);
		boolean b = false;
		for (Object k : keys) {
			String kk = (String) k;
			if (hm.get(kk) > 1) {
				b = true;
				System.out.println(kk.substring(0, 3) + "-" + kk.substring(3) + " " + hm.get(kk));
			}
		}
		if (!b) {
			System.out.println("No duplicates.");
		}
	}

	private static int getNum(char c) {
		if (c == 'A' || c == 'B' || c == 'C')
			return 2;
		else if (c == 'D' || c == 'E' || c == 'F')
			return 3;
		else if (c == 'G' || c == 'H' || c == 'I')
			return 4;
		else if (c == 'J' || c == 'K' || c == 'L')
			return 5;
		else if (c == 'M' || c == 'N' || c == 'O')
			return 6;
		else if (c == 'P' || c == 'R' || c == 'S')
			return 7;
		else if (c == 'T' || c == 'U' || c == 'V')
			return 8;
		else
			return 9;
	}
}
