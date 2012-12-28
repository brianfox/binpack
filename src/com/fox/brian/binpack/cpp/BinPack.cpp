#include "MaxRectsBinPack.h"


void run(int binWidth, int binHeight, int vals[], int c) {

	// Create a bin to pack to, use the bin size from command line.
	MaxRectsBinPack bin;
	printf("Initializing bin to size %dx%d.\n", binWidth, binHeight);
	bin.Init(binWidth, binHeight);


	// Pack each rectangle (w_i, h_i) the user inputted on the command line.
	for(int i = 0; i < c; i += 2)
	{
		// Read next rectangle to pack.
		int rectWidth = vals[i];
		int rectHeight = vals[i+1];
		printf("Packing rectangle of size %dx%d: ", rectWidth, rectHeight);

		// Perform the packing.
		MaxRectsBinPack::FreeRectChoiceHeuristic heuristic = MaxRectsBinPack::RectBestShortSideFit; // This can be changed individually even for each rectangle packed.
		Rect packedRect = bin.Insert(rectWidth, rectHeight, heuristic);

		// Test success or failure.
		if (packedRect.height > 0)
			printf("Packed to (x,y)=(%d,%d), (w,h)=(%d,%d). Free space left: %.2f%%\n", packedRect.x, packedRect.y, packedRect.width, packedRect.height, 100.f - bin.Occupancy()*100.f);
		else
			printf("Failed! Could not find a proper position to pack this rectangle into. Skipping this one.\n");
	}
	printf("Done. All rectangles packed.\n");

}


void test1()
{
	// Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20

	int binWidth = 256;
	int binHeight = 256;
	int vals[] = { 30, 20, 50, 20, 10, 80, 90, 20 };

	run(binWidth, binHeight, vals, 8);
}

void test2()
{
	// Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20

	int binWidth = 256;
	int binHeight = 256;
	int vals[100];
	for (int i=0; i < 100; i++)
		vals[i] = i;
	run(binWidth, binHeight, vals, 100);
}

int main(int argc, char **argv)
{
	test1();
	test2();
}
