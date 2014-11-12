package com.siliconorchard.myowncandy.mylog;

public class MyLog {
	public static boolean log = false;

	public static void error(String s) {
		if (log)
			System.err.println(s);
	}

	public static void print(String s) {
		if (log)
			System.out.print(s);
	}

	public static void println(String s) {
		if (log)
			System.out.println(s);
	}
}