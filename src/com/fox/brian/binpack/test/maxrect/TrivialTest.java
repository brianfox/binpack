package com.fox.brian.binpack.test.maxrect;

import org.junit.Test;

import com.fox.brian.binpack.MaxRectsBinPack;
import com.fox.brian.binpack.Rect;

public class TrivialTest {

	@Test
	public void usageTest() {
		// Usage Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20
		pack(256, 256, new int[]{30, 20, 50, 20, 10, 80, 90, 20 });
	}

	@Test
	public void incrementalTest() {
		int[] vals = new int[100];
		for (int i = 0; i < 100; i++) 
			vals[i] = i;
		
		// Usage Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20
		pack(256, 256, vals);
	}
	
	@Test
	public void decrementalTest() {
		int[] vals = new int[100];
		for (int i = 0; i < 100; i++) 
			vals[i] = 99 - i;
		
		// Usage Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20
		pack(256, 256, vals);
	}

	
	@Test
	public void wavyTest() {
		int[] vals = new int[100];
		for (int i = 0; i < 100; i++) {
			vals[i] = (i%2 == 0) ? i : 99 - i;
		}
		
		// Usage Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20
		pack(256, 256, vals);
	}


	@Test
	public void bigTest() {
		int[] vals = new int[1000];
		for (int i = 0; i < 1000; i++) {
			vals[i] = (i%2 == 0) ? i : 999 - i;
		}
		// Usage Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20
		pack(2560, 2560, vals, true);
	}


	public void pack(int binWidth, int binHeight, int[] vals) {
		pack(binWidth, binHeight, vals, true);
	}

	public void pack(int binWidth, int binHeight, int[] vals, boolean output) {
		
		// Create a bin to pack to, use the bin size from command line.
		System.out.printf("Initializing bin to size %dx%d.\n", binWidth, binHeight);
		MaxRectsBinPack bin = new MaxRectsBinPack(binWidth, binHeight);

		// Pack each rectangle (w_i, h_i) the user inputted on the command line.
		for(int i = 0; i < vals.length; i += 2)
		{
			// Read next rectangle to pack.
			int rectWidth = vals[i];
			int rectHeight = vals[i+1];
			if (output)
				System.out.printf("Packing rectangle of size %dx%d: ", rectWidth, rectHeight);

			// Perform the packing.
			MaxRectsBinPack.FreeRectChoiceHeuristic heuristic = MaxRectsBinPack.FreeRectChoiceHeuristic.RectBestShortSideFit; // This can be changed individually even for each rectangle packed.
			Rect packedRect = bin.Insert(rectWidth, rectHeight, heuristic);

			// Test success or failure.
			if (output) 
				if (packedRect.height > 0)
					System.out.printf(
							"Packed to (x,y)=(%.2f,%.2f), (w,h)=(%.2f,%.2f). Free space left: %.2f%%\n", 
							packedRect.x, 
							packedRect.y, 
							packedRect.width, 
							packedRect.height, 
							100.f - bin.Occupancy()*100.f
					);
				else
					System.out.printf("Failed! Could not find a proper position to pack this rectangle into. Skipping this one.\n");
		}
		System.out.printf("Done. All rectangles packed.\n");
		
	}

}
