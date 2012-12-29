package com.fox.brian.binpack;

import com.fox.brian.binpack.util.Rect;

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

	public void setX1(float x) {
		this.x = x;
	}


	public void setY2(float y) {
		this.y = y;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Object toSummary() {
		return summary;
	}

	public void reset() {
		this.x = this.y = 0;
		
	}

}
