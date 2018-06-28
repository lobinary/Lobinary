package com.lobinary.工具类;

import java.io.OutputStream;
import java.io.PrintStream;

public class LUSystemOutPrintStream extends PrintStream{
	
	public static PrintStream systemOut;
	
	public LUSystemOutPrintStream(OutputStream arg0) {
		super(arg0);
	}

	public PrintStream getSystemOut() {
		return systemOut;
	}

	public void setSystemOut(PrintStream systemOut) {
		LUSystemOutPrintStream.systemOut = systemOut;
	}

	@Override
	public void print(boolean b) {
		systemOut.println(b);
		super.print(b);
	}

	@Override
	public void print(char c) {
		systemOut.println(c);
		super.print(c);
	}

	@Override
	public void print(int i) {
		systemOut.println(i);
		super.print(i);
	}

	@Override
	public void print(long l) {
		systemOut.println(l);
		super.print(l);
	}

	@Override
	public void print(float f) {
		systemOut.println(f);
		super.print(f);
	}

	@Override
	public void print(double d) {
		systemOut.println(d);
		super.print(d);
	}

	@Override
	public void print(char[] s) {
		systemOut.println(s);
		super.print(s);
	}

	@Override
	public void print(String s) {
		systemOut.println(s);
		super.print(s);
	}

	@Override
	public void print(Object obj) {
		systemOut.println(obj);
		super.print(obj);
	}

	 
}