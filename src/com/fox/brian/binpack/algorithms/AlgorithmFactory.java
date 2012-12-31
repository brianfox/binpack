package com.fox.brian.binpack.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fox.brian.binpack.Bin;
import com.fox.brian.binpack.algorithms.GuillotineContainer.FreeRectChoiceHeuristic;
import com.fox.brian.binpack.algorithms.GuillotineContainer.GuillotineSplitHeuristic;
import com.fox.brian.binpack.util.Rect;

public class AlgorithmFactory<T> {

	public class AlgorithmParameters {
		
		float height;
		float width;
		boolean mandatoryFit;

		public AlgorithmParameters(float width, float height, boolean mandatoryfit) {
			this.width = width;
			this.height = height;
			this.mandatoryFit = mandatoryfit;
		}

		@Override
		public String toString() {
			return String.format("H: %.2f W: %.2f A: %.2f", height,width);
		}
	}
	
	public class GuillotineParameters extends AlgorithmParameters {

		GuillotineContainer.FreeRectChoiceHeuristic rectChoice;
		GuillotineContainer.GuillotineSplitHeuristic splitChoice;
		boolean merge;
		public boolean mandatoryfit;

		public GuillotineParameters(
				float width, 
				float height,
				FreeRectChoiceHeuristic h, 
				GuillotineSplitHeuristic s,
				boolean merge,
				boolean mandatoryfit
		) {
			super(width, height, mandatoryfit);
			this.rectChoice = h;
			this.splitChoice = s;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			// todo
			return sb.toString();
		}
	}
	
	public ArrayList<Bin<T>> bestGuillotinePack(
			ArrayList<Bin<T>> bins, 
			boolean mandatoryfit
			) {
		
		if (bins == null || bins.size() == 0)
			return new ArrayList<Bin<T>>();
		GuillotineContainer<T> container = bestScoreGuillotineSlidingDimensions(bins, mandatoryfit);
		System.out.printf("%s\n", container);
		return container.bins;
	}

	
	
	private GuillotineContainer<T> bestScoreGuillotineSlidingDimensions(ArrayList<Bin<T>> bins, boolean mandatoryfit) {
		
		// This calculation is so cheap that we can bin pack with brute force in
		// regards to guillotine algorithm parameters and bin size.
		
		
		// Start with an educated guess on minimum bin packing area.  
		// That equals a square that can perfectly capture all bins.
		
		float area = 0;
		float sqlen = 0;
		for (Bin<T> b : bins) {
			area += b.getHeight() * b.getWidth();
		}
		sqlen = (float) Math.sqrt(area);

		
		// Run through every possible rectangle, scoring the algorithm
		// and making note of the parameters
		
		GuillotineContainer<T> best = null;
		
		// GuillotineContainer gc = new GuillotineContainer();
		for (float len1 = sqlen; len1 < 100 * sqlen; len1 += len1 * 0.5) {
			for (float len2 = sqlen; len2 < 10 * sqlen; len2 += len2 * 0.5) {
				GuillotineContainer<T> next = bestScoreGuillotineFixedDimensions(bins, len1, len2, mandatoryfit);
				if (next == null)
					continue;
				if (best == null) 
					best = next;
				if (next.score() > best.score())
					best = next;
				
			}
		}
		return best;
	}

	
	private GuillotineContainer<T> bestScoreGuillotineFixedDimensions(ArrayList<Bin<T>> bins, float width, float height, boolean mandatoryfit) {
		
		GuillotineContainer<T> best = null;
		
		for ( GuillotineContainer.FreeRectChoiceHeuristic h :  GuillotineContainer.FreeRectChoiceHeuristic.values()) {
			for ( GuillotineContainer.GuillotineSplitHeuristic s :  GuillotineContainer.GuillotineSplitHeuristic.values()) {
				for ( boolean merge : new boolean[]{true,false} ) {				
					GuillotineParameters p = new GuillotineParameters(width, height, h, s, merge, mandatoryfit);
					GuillotineContainer<T> next = guillotineSolution(p, bins);
					if (next == null)
						continue;
					if (best == null) 
						best = next;
					if (next.score() > best.score())
						best = next;
				}
			}
		}
		return best;
	}
	
	
	private GuillotineContainer<T> guillotineSolution(GuillotineParameters parms, List<Bin<T>> bins) {
		
		GuillotineContainer<T> container = new GuillotineContainer<T>(parms.width, parms.height);

		// Pack each rectangle (w_i, h_i) the user inputted on the command line.
		for(Bin<T> b : bins)
		{
			// Perform the packing.
			Rect packedRect = container.insert(b, parms.merge, parms.rectChoice, parms.splitChoice);
			if (parms.mandatoryfit && (packedRect.x() < 0 || packedRect.y() < 0)) {
				throw new RuntimeException("Could not pack rectangle");
			}
		}
		return container;
	}

}
