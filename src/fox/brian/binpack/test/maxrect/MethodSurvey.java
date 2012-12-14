package com.fox.brian.binpack.test.maxrect;

import org.junit.Test;
import com.fox.brian.binpack.MaxRectsBinPack;

public class MethodSurvey extends AbstractTest {


	@Test
	public void incrementalTest() {

		for ( MaxRectsBinPack.FreeRectChoiceHeuristic h :  MaxRectsBinPack.FreeRectChoiceHeuristic.values()) {
			int[] vals = new int[100];
			for (int i = 0; i < 100; i++) 
				vals[i] = i;
			
			// Usage Example: MaxRectsBinPackTest 256 256 30 20 50 20 10 80 90 20
			float cap = pack(256, 256, vals, h, false);
			System.out.printf("Heuristic: %-20s   Remaining Capacity: %.2f%%\n", h.name(),cap);
		}
	}
}
