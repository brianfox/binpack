package com.fox.brian.binpack;

public class Bin<T> {

	private T object;
	private float x;
	private float y;
	private float width;
	private float height;
	private String summary;
	
	public Bin(T object, float width, float height, String summary) {
		this.object = object;
		this.height = height;
		this.width = width;
		this.x = -1;
		this.y = -1;
		this.summary = summary;
	}

	public Bin(Bin<T> src, float newx, float newy) {
		this.object = src.object;
		this.height = src.height;
		this.width = src.width;
		this.x = newx;
		this.y = newy;
		this.summary = src.summary;
	}

	public Bin(Bin<T> src) {
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
