package com.fox.brian.binpack;

import java.util.ArrayList;
import java.util.List;

import com.fox.brian.binpack.algorithms.AlgorithmFactory;
import com.fox.brian.binpack.algorithms.Guillotine;
import com.fox.brian.binpack.util.Rect;

public class BinPack<T> {

	ArrayList<Bin<T>> objects;
	Guillotine binpack;
	
	public BinPack() {
		this.objects = new ArrayList<Bin<T>>();
		this.binpack = new Guillotine();
	}

	public void addObject(Bin<T> object) {
		objects.add(object);
	}

	public void addObject(T object, float width, float height, String summary) {
		Bin<T> bin = new Bin<T>(object, width, height, summary);
		objects.add(bin);
	}
	
	
	public ArrayList<Bin<T>> pack(boolean mandatoryfit) {
		AlgorithmFactory<T> f = new AlgorithmFactory<T>();
		f.bestGuillotinePack(objects, mandatoryfit);
		return objects;
	}
	
	
	/*
	public ArrayList<Bin<T>> pack2() {
		for (Bin<T> b : objects) {
			b.reset();
			Rect packedRect = binpack.insert(b.rect.width, b.rect.height, true, 
					Guillotine.FreeRectChoiceHeuristic.RectBestShortSideFit, 
					Guillotine.GuillotineSplitHeuristic.SplitLongerAxis
					);
			if (packedRect.height > 0) {
				b.rect.x = packedRect.x;
				b.rect.y = packedRect.y;
			}
			else {
				b.rect.x = -1;
				b.rect.y = -1;
			}
		}
		return objects;
	}
	*/

	public int size() {
		return objects.size();
	}
	
}
