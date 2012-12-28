package com.fox.brian.binpack.test.guillotine;

import org.junit.Test;

import com.fox.brian.binpack.GuillotineBinPack;

public class TrivialTest extends AbstractTest {

	@Test
	public void usageTest() {
		// Usage Example: GuillotineBinPackTest 256 256 30 20 50 20 10 80 90 20

		float[] vals = new float[]{30, 20, 50, 20, 10, 80, 90, 20 };
		pack(
				256.0F, 
				256.0F, 
				vals, 
				GuillotineBinPack.FreeRectChoiceHeuristic.RectBestShortSideFit, 
				GuillotineBinPack.GuillotineSplitHeuristic.SplitLongerAxis,
				true
				);
	}

	@Test
	public void incrementalTest() {
		float[] vals = new float[100];
		for (int i = 0; i < 100; i++) 
			vals[i] = i;
		
		pack(
				256.0F, 
				256.0F, 
				vals, 
				GuillotineBinPack.FreeRectChoiceHeuristic.RectBestShortSideFit, 
				GuillotineBinPack.GuillotineSplitHeuristic.SplitLongerAxis,
				true
				);
	}
	
	@Test
	public void decrementalTest() {
		float[] vals = new float[100];
		for (int i = 0; i < 100; i++) 
			vals[i] = 99 - i;
		
		// Usage Example: GuillotineBinPackTest 256 256 30 20 50 20 10 80 90 20
		pack(
				256.0F, 
				256.0F, 
				vals, 
				GuillotineBinPack.FreeRectChoiceHeuristic.RectBestShortSideFit, 
				GuillotineBinPack.GuillotineSplitHeuristic.SplitLongerAxis,
				true
				);	
	}

	
	@Test
	public void wavyTest() {
		float[] vals = new float[100];
		for (int i = 0; i < 100; i++) 
			vals[i] = (i%2 == 0) ? i : 99 - i;
		
		pack(
			256.0F, 
			256.0F, 
			vals, 
			GuillotineBinPack.FreeRectChoiceHeuristic.RectBestShortSideFit, 
			GuillotineBinPack.GuillotineSplitHeuristic.SplitLongerAxis,
			true
			);	
	}


	@Test
	public void bigTest() {
		float[] vals = new float[100];
		for (int i = 0; i < 100; i++) 
			vals[i] = (i%2 == 0) ? i : 999 - i;
		
		pack(
				256.0F, 
				256.0F, 
				vals, 
				GuillotineBinPack.FreeRectChoiceHeuristic.RectBestShortSideFit, 
				GuillotineBinPack.GuillotineSplitHeuristic.SplitLongerAxis,
				true
				);	
		
	}



}
