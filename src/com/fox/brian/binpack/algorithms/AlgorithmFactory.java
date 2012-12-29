package com.fox.brian.binpack.algorithms;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fox.brian.binpack.Bin;
import com.fox.brian.binpack.algorithms.Guillotine.FreeRectChoiceHeuristic;
import com.fox.brian.binpack.algorithms.Guillotine.GuillotineSplitHeuristic;
import com.fox.brian.binpack.util.Rect;

public class AlgorithmFactory<T> {

	public class AlgorithmParameters implements Comparable<AlgorithmParameters> {
		
		float occupancy;
		float height;
		float width;
		float area;

		public AlgorithmParameters(float occupancy, float width, float height) {
			this.occupancy = occupancy;
			this.width = width;
			this.height = height;
			area = width * height;
		}

		@Override
		public int compareTo(AlgorithmParameters a) {
			if (this.area > a.area)
				return 1;
			if (this.area < a.area)
				return -1;
			return 0;
		}
		
		@Override
		public String toString() {
			return String.format("H: %.2f W: %.2f A: %.2f", height,width,area);
		}
	}
	
	public class GuillotineParameters extends AlgorithmParameters {

		Guillotine.FreeRectChoiceHeuristic rectChoice;
		Guillotine.GuillotineSplitHeuristic splitChoice;
		boolean merge;

		public GuillotineParameters(
				float occupancy, 
				float width, 
				float height,
				FreeRectChoiceHeuristic h, 
				GuillotineSplitHeuristic s,
				boolean merge
		) {
			super(occupancy, width, height);
			this.rectChoice = h;
			this.splitChoice = s;
		}
		
		@Override
		public String toString() {
			return String.format("Occupancy: %.2f W: %.2f H: %.2f   RectH: %s SplitH: %s  M: %s ", occupancy, width, height, rectChoice, splitChoice, merge);
		}
	}
	
	public Guillotine bestGuillotinePack(
			ArrayList<Bin<T>> bins, 
			boolean mandatoryfit
			) {
		
		if (bins == null || bins.size() == 0)
			return null;
		GuillotineParameters p = bestScoreGuillotineSlidingDimensions(bins, mandatoryfit);
		Guillotine container = new Guillotine(p.width, p.height);

		System.out.printf("Best Parameters: %s\n", p);
		// Pack each rectangle (w_i, h_i) the user inputted on the command line.
		for(Bin<T> b : bins)
		{
			b.reset();
			// Read next rectangle to pack.
			float rectWidth = b.getWidth();
			float rectHeight = b.getHeight();
			// Perform the packing.
			Rect packedRect = container.insert(rectWidth, rectHeight, p.merge, p.rectChoice, p.splitChoice);
			if (mandatoryfit && (packedRect.x < 0 || packedRect.y < 0))
				return null;
			System.out.printf("Bin:  Loc=(%.2f, %.2f) H,W=%.2f,%.2f %s\n", packedRect.x, packedRect.y, b.getWidth(), b.getHeight(), b.toSummary());
			b.setLocation(packedRect.x, packedRect.y);
		}

		return container;
	}

	
	
	private GuillotineParameters bestScoreGuillotineSlidingDimensions(ArrayList<Bin<T>> bins, boolean mandatoryfit) {
		
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
		
		TreeSet<GuillotineParameters> potentials = new TreeSet<GuillotineParameters>();
		
		for (float len1 = sqlen; len1 < 10 * sqlen; len1 += len1 * 0.5) {
			for (float len2 = sqlen; len2 < 10 * sqlen; len2 += len2 * 0.5) {
				GuillotineParameters best = bestScoreGuillotineFixedDimensions(bins, len1, len2, mandatoryfit);
				if (best != null) {
					potentials.add(best);
				}
			}
		}
		return potentials.isEmpty() ? null : potentials.first();
	}

	
	private GuillotineParameters bestScoreGuillotineFixedDimensions(ArrayList<Bin<T>> bins, float width, float height, boolean mandatoryfit) {
		
		TreeSet<GuillotineParameters> attempts = new TreeSet<GuillotineParameters>();
		
		for ( Guillotine.FreeRectChoiceHeuristic h :  Guillotine.FreeRectChoiceHeuristic.values()) {
			for ( Guillotine.GuillotineSplitHeuristic s :  Guillotine.GuillotineSplitHeuristic.values()) {
				for ( boolean merge : new boolean[]{true,false} ) {				
					
					Guillotine g = pack(width, height, bins, h, s, merge, mandatoryfit);	
					if (g != null)
						attempts.add(new GuillotineParameters(g.occupancy(), width, height, h, s, merge));
				}
			}
		}
		// System.out.println("FixedDimensions: " + attempts.size());
		return attempts.isEmpty() ? null : attempts.first();
	}
	
	
	private Guillotine pack(
			float binWidth, 
			float binHeight, 
			ArrayList<Bin<T>> bins, 
			Guillotine.FreeRectChoiceHeuristic heuristic, 
			Guillotine.GuillotineSplitHeuristic splitMethod,
			boolean merge,
			boolean mandatoryfit
			) {
		
		Guillotine container = new Guillotine(binWidth, binHeight);

		// Pack each rectangle (w_i, h_i) the user inputted on the command line.
		for(Bin<T> b : bins)
		{
			b.reset();

			// Perform the packing.
			Rect packedRect = container.insert(b.getWidth(), b.getHeight(), merge, heuristic, splitMethod);
			if (mandatoryfit && (packedRect.x < 0 || packedRect.y < 0)) {
				System.err.println("Could not pack rectangle");
				return null;
			}
			if (b.getHeight() <= 0 || b.getWidth() <= 0) {
				System.err.println("Could not pack rectangle");
				return null;
			}
			b.setLocation(packedRect.x, packedRect.y);
		}
		return container;
	}

}
