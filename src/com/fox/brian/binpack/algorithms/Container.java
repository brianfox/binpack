package com.fox.brian.binpack.algorithms;

import java.util.ArrayList;

import com.fox.brian.binpack.Bin;
import com.fox.brian.binpack.util.Helper;

public abstract class Container<T> {
	
	protected float binWidth;
	protected float binHeight;
	protected ArrayList<Bin<T>> bins;
	protected boolean overflow = false;
	protected boolean allowRotation = false;
	
	public boolean hasOverflow() {
		return overflow;
	}
	
	public float score() {
		
		if (overflow)
			return Float.NEGATIVE_INFINITY;
		
		float maxx = 0; 
		float maxy = 0;
		float usedArea = 0;
		for (Bin<T> b : bins) {
			if (b.getX() < 0 || b.getY() < 0)
				return 0;
			maxx = Helper.max(maxx, b.getX() + b.getWidth());
			maxy = Helper.max(maxy, b.getY() + b.getHeight());
			usedArea += b.getWidth() * b.getHeight();
		}
		return usedArea / (maxx * maxy);
	};
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Bins: %4d Score: %.5f", bins.size(), score()));
		for (Bin<T> b : bins)
			sb.append(String.format("\n    Location: (%8.2f, %8.2f)   Width: %8.2f   Height: %8.2f %-50s", b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.toSummary()));
		return sb.toString();
	}

}
