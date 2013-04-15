package com.fox.brian.binpack.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.fox.brian.binpack.util.Helper;
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
	
	public GuillotineContainer<T> smallestSquareGuillotine(ArrayList<Bin<T>> bins, boolean mandatoryfit) {
		// Start with an educated guess on minimum bin packing area.  
		// That equals a square that can perfectly capture all bins.
		
		float area = 0;
		for (Bin<T> b : bins) {
			float x = b.getHeight() * b.getWidth();
			area += x;

			assert !Float.isNaN(x) : "Bin doesn't have a valid area: " + b;
		}
		Helper.getGenericLogger().finest(String.format("Trying to pack %d bins with an area of %.2f", bins.size(), area));
		if (Float.isNaN(area)) {
			Helper.getGenericLogger().finest(String.format("    - Area is NaN...  How'd that happen?  Here are the bins' widths and heights:", bins.size(), area));
			for (Bin<T> b : bins)
				Helper.getGenericLogger().finest(String.format("    %.2f %.2f ... %s\n", b.getWidth(), b.getHeight(), b.toSummary()));
		}
		float lower = (float) Math.sqrt(area);
		
		// Find a container that actually works by exponentially 
		// increasing the container area.
		
		float upper = lower;
		Helper.getGenericLogger().finest(String.format("    - Found lower: %.2f", lower));
		Helper.getGenericLogger().finest(String.format("    - Searching for upper: "));
		if (upper > 0)
			while (true) {
				GuillotineContainer<T> container = bestScoreGuillotineFixedDimensions(bins, upper, upper, true);
				if (!container.hasOverflow())
					break;
				upper += upper;
			}
		Helper.getGenericLogger().finest(String.format("    - Done. Found upper: %.2f\n", upper));
		
		float lastWorking = upper;
		Helper.getGenericLogger().finest(String.format("    - Searching for ideal: "));
		// Do a binary search to figure out best length
		while ((upper / lower) > 1.05) {
			float nextlen = (upper + lower)/2;
			GuillotineContainer<T> container = bestScoreGuillotineFixedDimensions(bins, nextlen, nextlen, true);
			if (container.hasOverflow())
				lower = nextlen;
			else {
				upper = nextlen;
				lastWorking = upper;
			}
		}
		Helper.getGenericLogger().finest(String.format("    - Done.  Found ideal: %.2f\n", lastWorking));
		return bestScoreGuillotineFixedDimensions(bins, lastWorking, lastWorking, true);

	}
	
	public ArrayList<Bin<T>> bestGuillotinePack(
			ArrayList<Bin<T>> bins, 
			boolean mandatoryfit
			) {
		
		if (bins == null || bins.size() == 0)
			return new ArrayList<Bin<T>>();
		GuillotineContainer<T> container;
		container = bestScoreGuillotineSlidingDimensions(bins, mandatoryfit);
		//container = smallestSquareGuillotine(bins, mandatoryfit);
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

		GuillotineContainer<T> bestHorizontal = null;
		GuillotineContainer<T> bestVertical = null;
		
		float vert = 15.0F;
		float horz = 15.0F;
		
		while (vert > 0.1) {
			GuillotineContainer<T> next = bestScoreGuillotineFixedDimensions(bins, vert * sqlen, 100 * sqlen, mandatoryfit);
			vert -= 0.05;
			if (next == null)
				continue;
			if (bestVertical == null) 
				bestVertical = next;
			if (next.score() > bestVertical.score())
				bestVertical = next;
		}
		while (horz > 0.1) {
			GuillotineContainer<T> next = bestScoreGuillotineFixedDimensions(bins, 100 * sqlen, horz * sqlen, mandatoryfit);
			horz -= 0.05;
			if (next == null)
				continue;
			if (bestHorizontal == null) 
				bestHorizontal = next;
			if (next.score() > bestHorizontal.score())
				bestHorizontal = next;
		}
		for (float len1 = sqlen; len1 < 100 * sqlen; len1 += len1 * 0.5) {
			for (float len2 = sqlen; len2 < 100 * sqlen; len2 += len2 * 0.5) {
				GuillotineContainer<T> next = bestScoreGuillotineFixedDimensions(bins, len1, len2, mandatoryfit);
				if (next == null)
					continue;
				if (best == null) 
					best = next;
				if (next.score() > best.score())
					best = next;
				
			}
		}
		System.out.printf("H: %12.2f %12.2f (%2.0f%%)        V: %12.2f %12.2f (%2.0f%%)       B: %12.2f %12.2f (%2.0f%%)\n", 
				bestHorizontal.binWidth,
				bestHorizontal.binHeight,
				bestHorizontal.score() * 100,
				bestVertical.binWidth,
				bestVertical.binHeight,
				bestVertical.score() * 100,
				best.binWidth,
				best.binHeight,
				bestVertical.score() * 100
				);
		if (bestHorizontal.score() > best.score())
			best = bestHorizontal;
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
		
		GuillotineContainer<T> container = new GuillotineContainer<T>(parms.width, parms.height, 0, 0);  // FIXME

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
