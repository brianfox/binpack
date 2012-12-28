package com.fox.brian.binpack.test.guillotine;

import com.fox.brian.binpack.algorithms.Guillotine;
import com.fox.brian.binpack.util.Rect;

public class AbstractTest {

	protected boolean merge = false;
	
	
	
	public float pack(
			float binWidth, 
			float binHeight, 
			float[] vals, 
			Guillotine.FreeRectChoiceHeuristic heuristic, 
			Guillotine.GuillotineSplitHeuristic splitMethod, 
			boolean output
			) {
		
		// Create a bin to pack to, use the bin size from command line.
		if (output) 
			System.out.printf("Initializing bin to size %.2fx%.2f.\n", binWidth, binHeight);
		Guillotine bin = new Guillotine(binWidth, binHeight);

		// Pack each rectangle (w_i, h_i) the user inputted on the command line.
		for(int i = 0; i < vals.length; i += 2)
		{
			// Read next rectangle to pack.
			float rectWidth = vals[i];
			float rectHeight = vals[i+1];
			if (output)
				System.out.printf("Packing rectangle of size %.2fx%.2f: ", rectWidth, rectHeight);

			// Perform the packing.
			Rect packedRect = bin.insert(rectWidth, rectHeight, merge, heuristic, splitMethod);

			// Test success or failure.
			if (output) 
				if (packedRect.height() > 0)
					System.out.printf(
							"Packed to (x,y)=(%.2f,%.2f), (w,h)=(%.2f,%.2f). Free space left: %.2f%%\n", 
							packedRect.x(), 
							packedRect.y(), 
							packedRect.width(), 
							packedRect.height(), 
							100.f - bin.occupancy()*100.f
					);
				else
					System.out.printf("Failed! Could not find a proper position to pack this rectangle into. Skipping this one.\n");
		}
		if (output) 
			System.out.printf("Done. All rectangles packed.\n");
		return 100.f - bin.occupancy()*100.f;
	}

}
