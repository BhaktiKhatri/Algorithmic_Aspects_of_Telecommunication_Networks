package com.bhakti.project3;

import java.util.Vector;
import java.util.stream.Collectors;

public class CalculateDijkstraShortestPath
{
	private NodeEdge[] edgeBtwn;
	private double[] distBtwn;
	private IndexMinPQ<Double> pqMin;
	public int count=0;
	
	public double distBtwn(int i) 
	{
		return distBtwn[i];
	}

	/*Checks the path between edges*/
	public boolean hasPathTo(int i) 
	{
		return distBtwn[i] < Double.POSITIVE_INFINITY;
	}

	/*Finds the Dijkstra's shortest path*/
	public CalculateDijkstraShortestPath(GenerateGraph gg, int dt)
	{
		count++;
		distBtwn = new double[gg.nVertices()];
		edgeBtwn = new NodeEdge[gg.nVertices()];

		for (int i=0;i<distBtwn.length;i++) 
		{
			distBtwn[i]=Double.POSITIVE_INFINITY;
		}
		
		distBtwn[dt] = 0.0;
		pqMin = new IndexMinPQ<Double>(gg.nVertices());
		pqMin.insert(dt, distBtwn[dt]);
		
		while (!pqMin.isEmpty())
		{
			int i = pqMin.delMin();
			
			//System.out.println("Adjacent edges of" + i + " are: " + gg.adjacentEdges(i).stream().map(NodeEdge::getNodej).collect(Collectors.toList()));
			
			for (NodeEdge e : gg.adjacentEdges(i)) 
			{
				relaxAndUpdate(e, i);
			}
		}
	}

	/*Relax an edge and updates its corresponding vertex*/
	private void relaxAndUpdate(NodeEdge e, int i) 
	{
		int j = e.other(i);
		if (distBtwn[j] > distBtwn[i] + e.getWeight())
		{
			distBtwn[j] = distBtwn[i] + e.getWeight();
			edgeBtwn[j] = e;
			if (pqMin.contains(j)) 
			{
				pqMin.decreaseKey(j, distBtwn[j]);
			}
			else 
			{
				pqMin.insert(j, distBtwn[j]);
			}
		}
	}
	
}
