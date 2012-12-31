package com.fox.brian.binpack;

import java.util.ArrayList;

import com.fox.brian.binpack.algorithms.AlgorithmFactory;
import com.fox.brian.binpack.algorithms.GuillotineContainer;

public class BinPack<T> {

	ArrayList<Bin<T>> objects;
	GuillotineContainer<T> binpack;
	
	public BinPack(float binWidth, float binHeight) {
		this.objects = new ArrayList<Bin<T>>();
		this.binpack = new GuillotineContainer<T>(binWidth, binHeight);
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
		return f.bestGuillotinePack(objects, mandatoryfit);
	}
	
	
	public int size() {
		return objects.size();
	}
	
}
