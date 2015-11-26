package com.mvc.framework.arithmetic;

import java.util.List;


public class ShortestPath {
	private Point source;
	private Point end;
	private List<Point> points;
	private double length;

	public ShortestPath(Point source,Point end,List<Point> points,Double length){
		this.source = source;
		this.end = end;
		this.points = points;
		this.length = length;
	}

	public Point getSource() {
		return source;
	}
	public void setSource(Point source) {
		this.source = source;
	}
	public Point getEnd() {
		return end;
	}
	public void setEnd(Point end) {
		this.end = end;
	}
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
}