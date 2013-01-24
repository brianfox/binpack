package com.fox.brian.binpack.util;

import java.util.logging.Logger;

public class Helper {
	
	private static Logger logger;
	
	public static Logger getGenericLogger() {
		if (logger == null) {
			logger = Logger.getLogger("com.fox.brian.binpack");
		}
		return logger;
	}

	
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
