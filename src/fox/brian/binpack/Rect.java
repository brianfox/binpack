package com.fox.brian.binpack;

public class Rect {

	/*
	class RectSize
	{
		int width;
		int height;
	};
	*/
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public Rect() {
		x = y = width = height = 0;
	}

	public Rect(Rect n) {
		this.x = n.x;
		this.y = n.y;
		this.width = n.width;
		this.height = n.height;
	}

	/** Performs a lexicographic compare on (rect short side, rect long side).
	  * If they are equal, the larger side length is used as a tie-breaker.
	  * If the rectangles are of same size, returns 0.
	  * @param a a Rect
	  * @param b another Rect
	  * @return -1 if the smaller side of a is shorter than the smaller side of b, 1 if the other way around.
	  */
	int CompareRectShortSide(Rect a, Rect b) { 
		return 0; 
	}

	/**
	 * Performs a lexicographic compare on (x, y, width, height).
	 * @param a
	 * @param b
	 * @return
	 */
	int NodeSortCmp(Rect a, Rect b) { 
		return 0; 
	}

	/**
	 * Returns true if a is contained in b.
	 * @param a
	 * @param b
	 * @return
	 */
	static boolean isContainedIn(Rect a, Rect b) { 
		return a.x >= b.x && a.y >= b.y 
				&& a.x+a.width <= b.x+b.width 
				&& a.y+a.height <= b.y+b.height;	
	}

}

