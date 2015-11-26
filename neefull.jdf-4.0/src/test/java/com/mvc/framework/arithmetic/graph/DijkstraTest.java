package com.mvc.framework.arithmetic.graph;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.mvc.framework.arithmetic.Edge;
import com.mvc.framework.arithmetic.Point;

public class DijkstraTest extends TestCase {
	@Test
	public void test() {
		long initBegin = System.currentTimeMillis();
		Point A = new Point("A");
		Point B = new Point("B");
		Point C = new Point("C");
		Point D = new Point("D");
		Point E = new Point("E");
		Point F = new Point("F");
		Point G = new Point("G");
		Point H = new Point("H");
		Point I = new Point("I");
		Point J = new Point("J");
		Point K = new Point("K");
		A.setRed(true);
		A.setSource(true);

		List<Point> points = new ArrayList<Point>();
		points.add(B);
		points.add(A);
		points.add(C);
		points.add(D);
		points.add(E);
		points.add(F);
		points.add(G);
		points.add(H);
		points.add(I);
		points.add(J);
		points.add(K);

		// for (int i = 0; i < 10000; i++) {
		// Point point = new Point(""+i);
		// points.add(point);
		// }

		List<Edge> edges = new ArrayList<Edge>();
		edges.add(new Edge(A, B, 19));
		edges.add(new Edge(B, C, 25));
		edges.add(new Edge(B, D, 8));
		edges.add(new Edge(C, E, 10));
		edges.add(new Edge(D, A, 20));
		edges.add(new Edge(D, C, 4));
		edges.add(new Edge(D, E, 12));

		edges.add(new Edge(A, E, 13));
		edges.add(new Edge(B, F, 25));
		edges.add(new Edge(C, F, 2));
		edges.add(new Edge(D, F, 10));

		edges.add(new Edge(F, I, 20));
		edges.add(new Edge(F, K, 16));
		edges.add(new Edge(C, G, 5));
		edges.add(new Edge(G, I, 10));
		edges.add(new Edge(G, K, 1));
		edges.add(new Edge(G, H, 7));
		edges.add(new Edge(H, J, 20));
		edges.add(new Edge(H, K, 45));
		edges.add(new Edge(I, J, 4));
		edges.add(new Edge(J, K, 3));

		// for (int i = 0; i < points.size(); i++) {
		// for (int j = i; j < points.size()-i+1; j++) {
		// if (j<points.size()&&(j==i+1||j==10*i+1)) {
		// edges.add(new Edge(points.get(i),points.get(j),100*Math.random()));
		// }
		// }
		// }

		System.out.println(edges.size());
		long initEnd = System.currentTimeMillis();
		System.out.println("init time:" + (initEnd - initBegin) / 60);

		long computeBegin = System.currentTimeMillis();
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.setPoints(points);
		dijkstra.setEdges(edges);
		dijkstra.dijkstra(A,B);
		dijkstra.display();
		long computeEnd = System.currentTimeMillis();
		System.out.println("compute time:" + (computeEnd - computeBegin) / 1000);
	}
}
