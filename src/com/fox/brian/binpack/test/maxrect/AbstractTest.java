package com.fox.brian.binpack.test.maxrect;

import com.fox.brian.binpack.algorithms.MaxRects;
import com.fox.brian.binpack.util.Rect;

public class AbstractTest {

	
	
	public float pack(int binWidth, int binHeight, int[] vals, MaxRects.FreeRectChoiceHeuristic heuristic, boolean output) {
		
		// Create a bin to pack to, use the bin size from command line.
		if (output) 
			System.out.printf("Initializing bin to size %dx%d.\n", binWidth, binHeight);
		MaxRects bin = new MaxRects(binWidth, binHeight);

		// Pack each rectangle (w_i, h_i) the user inputted on the command line.
		for(int i = 0; i < vals.length; i += 2)
		{
			// Read next rectangle to pack.
			int rectWidth = vals[i];
			int rectHeight = vals[i+1];
			if (output)
				System.out.printf("Packing rectangle of size %dx%d: ", rectWidth, rectHeight);

			// Perform the packing.
			Rect packedRect = bin.Insert(rectWidth, rectHeight, heuristic);

			// Test success or failure.
			if (output) 
				if (packedRect.height() > 0)
					System.out.printf(
							"Packed to (x,y)=(%.2f,%.2f), (w,h)=(%.2f,%.2f). Free space left: %.2f%%\n", 
							packedRect.x(), 
							packedRect.y(), 
							packedRect.width(), 
							packedRect.height(), 
							100.f - bin.Occupancy()*100.f
					);
				else
					System.out.printf("Failed! Could not find a proper position to pack this rectangle into. Skipping this one.\n");
		}
		if (output) 
			System.out.printf("Done. All rectangles packed.\n");
		return 100.f - bin.Occupancy()*100.f;
	}

}
