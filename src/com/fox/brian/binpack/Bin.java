package com.fox.brian.binpack;

public class Bin<T> {

	private T object;
	private float x;
	private float y;
	private float width;
	private float height;
	private String summary;
	
	public Bin(T object, float width, float height, String summary) {
		if (Float.isNaN(width) || Float.isNaN(height))
			System.err.println("Bad constructor call on Bin (1)");
		this.object = object;
		this.height = height;
		this.width = width;
		this.x = -1;
		this.y = -1;
		this.summary = summary;
	}

	public Bin(Bin<T> src, float newx, float newy) {
		if (Float.isNaN(width) || Float.isNaN(height))
			System.err.println("Bad constructor call on Bin (2)");
		this.object = src.object;
		this.height = src.height;
		this.width = src.width;
		this.x = newx;
		this.y = newy;
		this.summary = src.summary;
	}

	public Bin(Bin<T> src) {
		if (Float.isNaN(width) || Float.isNaN(height))
			System.err.println("Bad constructor call on Bin (3)");
		this.object = src.object;
		this.height = src.height;
		this.width = src.width;
		this.x = src.x;
		this.y = src.y;
		this.summary = src.summary;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public T getObject() {
		return object;
	}

	public Object toSummary() {
		return summary;
	}

}
