package com.fox.brian.binpack.util;

public class Rect {

	/*
	class RectSize
	{
		int width;
		int height;
	};
	*/
	
	public float x;
	public float y;
	public float width;
	public float height;
	
	public Rect() {
		x = y = width = height = 0;
	}

	public Rect(Rect n) {
		this.x = n.x;
		this.y = n.y;
		this.width = n.width;
		this.height = n.height;
	}

	
	/**
	 * Returns true if a is contained in b.
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isContainedIn(Rect a, Rect b) { 
		return a.x >= b.x && a.y >= b.y 
				&& a.x+a.width <= b.x+b.width 
				&& a.y+a.height <= b.y+b.height;	
	}

}

