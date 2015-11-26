package com.mvc.framework.arithmetic;


/**
 *Edge
 */
public class Edge {
	private Point start;
	private Point end;
	private double length;

	public Edge(Point start,Point end,double length) {
		this.start = start;
		this.end = end;
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end.getName() == null) ? 0 : end.getName().hashCode());
		result = prime * result + ((start.getName() == null) ? 0 : start.getName().hashCode());
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
		Edge other = (Edge) obj;
		if (end.getName() == null) {
			if (other.end.getName() != null)
				return false;
		} else if (!end.getName().equals(other.end.getName()))
			return false;
		if (start.getName() == null) {
			if (other.start.getName() != null)
				return false;
		} else if (!start.getName().equals(other.start.getName()))
			return false;
		return true;
	}


	public Point getStart() {
		return start;
	}
	public void setStart(Point start) {
		this.start = start;
	}
	public Point getEnd() {
		return end;
	}
	public void setEnd(Point end) {
		this.end = end;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
}
