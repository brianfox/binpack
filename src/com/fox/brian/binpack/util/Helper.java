package com.fox.brian.binpack.util;

public class Helper {
	public static float abs(float x) {
		return x < 0 ? -x : x;
	}
	
	public static float min(float x, float y) {
		return x < y ? x : y;
	}

	public static float max(float x, float y) {
		return x < y ? y : x;
	}
	
}
