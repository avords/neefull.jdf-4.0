package com.mvc.framework.arithmetic.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mvc.framework.arithmetic.Edge;
import com.mvc.framework.arithmetic.Point;
import com.mvc.framework.arithmetic.ShortestPath;


public class Dijkstra {
	private List<Point> points;
	private List<Edge> edges = new ArrayList<Edge>();
	// Point is the end of the Path
	private Map<Point, ShortestPath> pathMap = new HashMap<Point, ShortestPath>();
	// Point is the start of all Edge in the List
	private Map<Point, List<Edge>> edgesMapByStart = new HashMap<Point, List<Edge>>();
	// Point is the end of all Edge in the List
	private Map<Point, List<Edge>> edgesMapByEnd = new HashMap<Point, List<Edge>>();
	//Blue dot set:1.Path.length!=INFINITY,2.resetPath 
	private Set<Point> bluePoints = new HashSet<Point>();
	private Point currentPoint;
	private final double INFINITY = 999999;
	private double startToCurrent;

	public void init(Point start) {
		start.setSource(true);
		start.setRed(true);
		Point source = start;
		for (Point point : points) {
			List<Point> redPoints = new ArrayList<Point>();
			redPoints.add(source);
			ShortestPath path = new ShortestPath(source, point, redPoints, INFINITY);
			pathMap.put(point, path);
		}
		for (Edge edge : edges) {
			Point s = edge.getStart();
			Point e = edge.getEnd();
			if (source.equals(s)) {
				pathMap.get(e).setLength(edge.getLength());
				bluePoints.add(e);
			}
			if (edgesMapByStart.get(s) == null) {
				edgesMapByStart.put(s, new ArrayList<Edge>());
				edgesMapByStart.get(s).add(edge);
			} else {
				edgesMapByStart.get(s).add(edge);
			}
			if (edgesMapByEnd.get(e) == null) {
				edgesMapByEnd.put(e, new ArrayList<Edge>());
				edgesMapByEnd.get(e).add(edge);
			} else {
				edgesMapByEnd.get(e).add(edge);
			}
		}
	}

	public void dijkstra() {
		dijkstra(null, null);
	}

	public void dijkstra(Point start, Point end) {
		if(start==null){
			start = points.get(0);
		}
		if(end==null){
			end = points.get(points.size()-1);
		}
		init(start);
		while (bluePoints.size() > 0) {
			Point point = getMin();
			if (point == null) {
				continue;
			}
			double minDist = pathMap.get(point).getLength();
			if (minDist == INFINITY) {
				//System.out.println("有无法到达的顶点");
			} else {
				currentPoint = point;
				point.setRed(true);
				List<Edge> edges = edgesMapByStart.get(point);
				if (edges != null) {
					for (Edge edge : edges) {
						if (!edge.getEnd().isRed()) {
							bluePoints.add(edge.getEnd());
						}
					}
				}
				bluePoints.remove(point);
				if (end != null && end.equals(currentPoint)) {
					return;
				}
				pathMap.get(point).getPoints().add(currentPoint);
				startToCurrent = minDist;
			}
			resetPaths();
		}
	}

	private void resetPaths() {
		Iterator<Point> it = bluePoints.iterator();
		while (it.hasNext()) {
			Point bluePoint = it.next();
			List<Edge> edges = edgesMapByEnd.get(bluePoint);
			for (Edge edge : edges) {
				if (edge.getEnd().isRed()) {
					continue;
				}
				ShortestPath path = pathMap.get(edge.getEnd());
				if (edge.getStart().equals(currentPoint)&& edge.getEnd().equals(path.getEnd())) {
					double currentToFringe = edge.getLength();
					double startToFringe = startToCurrent + currentToFringe;
					double pathLength = path.getLength();
					if (startToFringe < pathLength) {
						List<Point> points = pathMap.get(currentPoint).getPoints();
						List<Point> copyPoints = new ArrayList<Point>();
						for (Point point : points) {
							copyPoints.add(point);
						}
						path.setPoints(copyPoints);
						path.setLength(startToFringe);
					}
				}
			}
		}
	}

	public void display() {
		for (Point point : pathMap.keySet()) {
			ShortestPath path = pathMap.get(point);
			System.out.print(path.getSource().getX() + "-->" + path.getEnd().getX() + ":");
			for (Point point2 : path.getPoints()) {
				System.out.print(point2.getX() + "  ");
			}
			System.out.print(path.getLength());
		}
	}

	private Point getMin() {
		double minDist = INFINITY;
		Point point = null;
		for (Point bluePoint : bluePoints) {
			ShortestPath path = pathMap.get(bluePoint);
			if (!path.getEnd().isRed() && path.getLength() < minDist) {
				minDist = path.getLength();
				point = bluePoint;
			}

		}
		return point;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public Map<Point, ShortestPath> getPathMap() {
		return pathMap;
	}

	public void setPathMap(Map<Point, ShortestPath> pathMap) {
		this.pathMap = pathMap;
	}

	public Map<Point, List<Edge>> getEdgesMapByStart() {
		return edgesMapByStart;
	}

	public void setEdgesMapByStart(Map<Point, List<Edge>> edgesMapByStart) {
		this.edgesMapByStart = edgesMapByStart;
	}

	public Map<Point, List<Edge>> getEdgesMapByEnd() {
		return edgesMapByEnd;
	}

	public void setEdgesMapByEnd(Map<Point, List<Edge>> edgesMapByEnd) {
		this.edgesMapByEnd = edgesMapByEnd;
	}

	public Set<Point> getBluePoints() {
		return bluePoints;
	}

	public void setBluePoints(Set<Point> bluePoints) {
		this.bluePoints = bluePoints;
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	public double getStartToCurrent() {
		return startToCurrent;
	}

	public void setStartToCurrent(double startToCurrent) {
		this.startToCurrent = startToCurrent;
	}
}
