package com.fox.brian.binpack;

import java.util.ArrayList;

class DisjointRectCollection {
	ArrayList<Rect> rects;

	boolean add(Rect r)
	{
		// Degenerate rectangles are ignored.
		if (r.width == 0 || r.height == 0)
			return true;

		if (!disjoint(r))
			return false;
		rects.add(r);
		return true;
	}

	void Clear()
	{
		rects.clear();
	}

	boolean disjoint(Rect r) 
	{
		// Degenerate rectangles are ignored.
		if (r.width == 0 || r.height == 0)
			return true;

		for(int i = 0; i < rects.size(); ++i)
			if (!disjoint(rects.get(i), r))
				return false;
		return true;
	}

	static boolean disjoint(Rect a, Rect b)
	{
		if (a.x + a.width <= b.x ||
			b.x + b.width <= a.x ||
			a.y + a.height <= b.y ||
			b.y + b.height <= a.y)
			return true;
		return false;
	}
}