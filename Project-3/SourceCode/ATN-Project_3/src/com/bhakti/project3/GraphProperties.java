package com.bhakti.project3;
import java.util.HashMap;
import com.bhakti.project3.*;

public class GraphProperties 
{
	/*Checks if the diameter of the graph is at most 4*/
	
	public static boolean isDiameterFour(GenerateGraph graph, int hops) 
	{
		GenerateGraph graphWithEqualDist = new GenerateGraph(graph.nVertices(), null);
		int[][] adj = new int[graph.nVertices()][graph.nVertices()];
		for(int i=0;i<graph.nVertices();i++)
		{
			for(int j=0;j<graph.nVertices();j++)
			{
				if(graph.getAdjMatrix()[i][j]==1)
					{
						adj[i][j] = 1;
					}
			}
		}
		graphWithEqualDist.setAdjMatrix(adj);
		
		for (int i = 0; i < graphWithEqualDist.nVertices(); i++)
		{
			CalculateDijkstraShortestPath sp = new CalculateDijkstraShortestPath(graphWithEqualDist, i);
			for (int j = 0; j < graphWithEqualDist.nVertices(); j++)
			{
				double d = sp.distBtwn(j);
				//System.out.println("Distance between " + i + " & " + j + ": " + d);
				if (d > 4.00)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	/*Checks if each node is connected to at least 3 other nodes*/
	public static boolean isDegreeThree(GenerateGraph g, int n) 
	{
		for (int i=0;i<g.nVertices();i++) 
		{
			if (g.degree(i)<3)
			{
				return false;
			}
		}
		return true;
	}
	
	/*Generates a connected graph for the given nodes and points*/
	public static GenerateGraph generateFullyConnectedGraph(int n, HashMap<Integer, CoordinatePoints> points) 
	{
		GenerateGraph graph = new GenerateGraph(n, points);
		int[][] adj = new int[n][n];
		for(int i=0;i<adj.length;i++)
		{
			for(int j=0;j<adj.length;j++)
			{
				if(i!=j)
				{
					adj[i][j] = 1;
				}
			}
		}
		graph.setAdjMatrix(adj);
		return graph;
	}

	/*Gives the cost of the graph*/
	public static double getCost(GenerateGraph graph) 
	{
		double wt = 0.0;
		for(NodeEdge e: graph.edges())
		{
			wt = wt + e.getWeight();
		}
		return wt;
	}

	/*Generates an empty graph for the given nodes and points*/
	public static GenerateGraph generateEmptyGraph(int n, HashMap<Integer, CoordinatePoints> points) 
	{
		GenerateGraph graph = new GenerateGraph(n, points);
		int[][] adj = new int[n][n];
		graph.setAdjMatrix(adj);
		return graph;
	}
	
	/*Get the edge weight between points*/
	public static Double getEdgeWeight(int i, int j, HashMap<Integer, CoordinatePoints> points)
	{
		CoordinatePoints p1 = points.get(i);
		CoordinatePoints p2 = points.get(j);
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
}