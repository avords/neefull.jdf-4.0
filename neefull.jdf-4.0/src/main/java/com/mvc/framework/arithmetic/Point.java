package com.mvc.framework.arithmetic;

/**
 * Point
 */
public class Point {
	private String name;
	private double x = 0;
	private double y = 0;
	private int lu = 0;
	private boolean isCross;
	//If short path set marked as red
	private boolean isRed;
	private boolean isSource;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Point(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getLu() {
		return lu;
	}
	public void setLu(int lu) {
		this.lu = lu;
	}
	public boolean isCross() {
		return isCross;
	}
	public void setCross(boolean isCross) {
		this.isCross = isCross;
	}
	public boolean isRed() {
		return isRed;
	}
	public void setRed(boolean isRed) {
		this.isRed = isRed;
	}
	public boolean isSource() {
		return isSource;
	}
	public void setSource(boolean isSource) {
		this.isSource = isSource;
	}
}
