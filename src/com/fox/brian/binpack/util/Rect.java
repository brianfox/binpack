package com.fox.brian.binpack.util;

public final class Rect {

	private float x;
	private float y;
	private float width;
	private float height;
	
	public Rect() {
		x = y = width = height = 0;
	}

	public Rect(Rect n) {
		this.x = n.x;
		this.y = n.y;
		this.width = n.width;
		this.height = n.height;
	}
	
	public Rect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	
	public float x() { return x; }
	public float y() { return y; }
	public float height() { return height; }
	public float width() { return width; }
	
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

