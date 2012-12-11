package com.fox.brian.binpack;

import java.util.ArrayList;

public class Rect {


	class RectSize
	{
		int width;
		int height;
	};

	

	
	int x;
	int y;
	int width;
	int height;
	
	public Rect() {
		x = y = width = height = 0;
	}

	/// Performs a lexicographic compare on (rect short side, rect long side).
	/// @return -1 if the smaller side of a is shorter than the smaller side of b, 1 if the other way around.
	///   If they are equal, the larger side length is used as a tie-breaker.
	///   If the rectangles are of same size, returns 0.
	int CompareRectShortSide(Rect a, Rect b) { return 0; }

	/// Performs a lexicographic compare on (x, y, width, height).
	int NodeSortCmp(Rect a, Rect b) { return 0; }

	/// Returns true if a is contained in b.
	boolean isContainedIn(Rect a, Rect b) { return true; }

}

