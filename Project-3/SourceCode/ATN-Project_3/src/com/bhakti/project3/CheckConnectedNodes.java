package com.bhakti.project3;

public class CheckConnectedNodes
{
	private boolean[] markVertex;
	private int[] id;
	private int[] size;
	private int countConnections;

	/*Checks a vertex is connected to how many other vertices*/
	public CheckConnectedNodes(GenerateGraph g) 
	{
		markVertex = new boolean[g.nVertices()];
		id = new int[g.nVertices()];
		size = new int[g.nVertices()];
		for (int i = 0; i < g.nVertices(); i++) 
		{
			if (!markVertex[i]) 
			{
				DFS(g, i);
				countConnections++;
			}
		}
	}

	/*Uses DFS to find the connections*/
	public void DFS(GenerateGraph g, int i) 
	{
		markVertex[i] = true;
		id[i] = countConnections;
		size[countConnections]++;
		for (int j : g.adjacent(i)) 
		{
			if (!markVertex[j]) 
			{
				DFS(g, j);
			}
		}
	}

	/*Checks if all the nodes are connected in the graph*/
	public boolean isNodeConnected() 
	{
		if (countConnections == 1)
		{
			return true;
		}
		else
		{
			return false;
		}			
	}
	
	public int id(int i) 
	{
		return id[i];
	}

	public int size(int i) 
	{
		return size[id[i]];
	}

	public int countConnections() 
	{
		return countConnections;
	}

}