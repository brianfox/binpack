package com.fox.brian.binpack.porthacks;

/**
 * A quick and dirty way to pass input/output integer parameters to 
 * functions.  This accommodates C++ constructs like:
 * 
 * void myFunc(int &myreturn) {
 *     *myreturn = 1;
 * }
 * 
 */
public class outInt {
	public int val;
}
