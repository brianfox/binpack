package com.fox.brian.binpack;

public class BinPack {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int argc = args.length;
		if (argc < 5 || argc % 2 != 0)
		{
			System.out.printf("Usage: MaxRectsBinPackTest binWidth binHeight w_0 h_0 w_1 h_1 w_2 h_2 ... w_n h_n\n");
			System.out.printf("       where binWidth and binHeight define the size of the bin.\n");
			System.out.printf("       w_i is the width of the i'th rectangle to pack, and h_i the height.\n");
			System.out.printf("Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20\n");
			System.exit(0);
		}

		// Create a bin to pack to, use the bin size from command line.
		MaxRectsBinPack bin;
		int binWidth = Integer.parseInt(args[0]);
		int binHeight = Integer.parseInt(args[1]);
		System.out.printf("Initializing bin to size %dx%d.\n", binWidth, binHeight);
		bin = new MaxRectsBinPack(binWidth, binHeight);
		
		// Pack each rectangle (w_i, h_i) the user inputted on the command line.
		for(int i = 2; i < argc; i += 2)
		{
			// Read next rectangle to pack.
			int rectWidth = Integer.parseInt(args[i]);
			int rectHeight = Integer.parseInt(args[i+1]);
			System.out.printf("Packing rectangle of size %dx%d: ", rectWidth, rectHeight);

			// Perform the packing.
			MaxRectsBinPack.FreeRectChoiceHeuristic heuristic = MaxRectsBinPack.FreeRectChoiceHeuristic.RectBestShortSideFit; // This can be changed individually even for each rectangle packed.
			Rect packedRect = bin.Insert(rectWidth, rectHeight, heuristic);

			// Test success or failure.
			if (packedRect.height > 0)
				System.out.printf("Packed to (x,y)=(%d,%d), (w,h)=(%d,%d). Free space left: %.2f%%\n", packedRect.x, packedRect.y, packedRect.width, packedRect.height, 100.f - bin.Occupancy()*100.f);
			else
				System.out.printf("Failed! Could not find a proper position to pack this rectangle into. Skipping this one.\n");
		}
		System.out.printf("Done. All rectangles packed.\n");

	}

}


