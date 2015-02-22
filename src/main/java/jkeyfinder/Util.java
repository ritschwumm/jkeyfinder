package jkeyfinder;

import static java.lang.Math.*;

public final class Util {
	private Util() {}
	
	public static int divUp(int a, int b) {
		return (a + b - 1) / b;
	}
	
	public static int modPos(int a, int b) {
		final int c	= a % b;
		return (b < 0 && c > 0 || b > 0 && c < 0) ? c + b : c;
	}
	
	public static float blackmanWindow(int position, int size) {
		return (float)(0.42 - (0.5 * cos((2 * PI * position) / (size - 1))) + (0.08 * cos((4 * PI * position) / (size - 1))));
	}
	
	/*
	public static float hammingWindow(int position, int size) {
		return (float)(0.54 - (0.46 * cos((2 * PI * position) / (size - 1))));
	}
	
	public static float hannWindow(int position, int size) {
		return (float)(0.5 * (1.0 - cos((2 * PI * position) / (size - 1))));
	}
	*/
	
	public static double complexLength(double real, double imag) {
		return sqrt((real * real) + (imag * imag));
	}
	
	public static double pow2(double it) {
		return Math.pow(2, it);
	}
}
