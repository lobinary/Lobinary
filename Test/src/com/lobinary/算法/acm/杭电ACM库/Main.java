package com.lobinary.算法.acm.杭电ACM库;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Main {
	
	static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
	static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
	static boolean isEndFlag = false;
	
	public static void main(String[] arg) throws Exception {
		int n = getInt();
		int r = 0;
		while (isEnd()) {
			r = getInt();
			for (int i = 0; i < n-1; i++) {
				r += getInt();
			}
			out.println(r);
		}
		out.flush();
	}
	
	public static int getInt() throws IOException{
		if(!isEndFlag){
			in.nextToken();
			isEndFlag = false;
		}
		return (int) in.nval;
	}
	
	public static boolean isEnd() throws IOException{
		isEndFlag = true;
		return in.nextToken() != StreamTokenizer.TT_EOF;
	}
}
