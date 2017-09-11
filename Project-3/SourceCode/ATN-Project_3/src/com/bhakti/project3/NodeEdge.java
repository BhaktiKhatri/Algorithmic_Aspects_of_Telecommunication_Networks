package com.bhakti.project3;

//Creates an edge between two nodes
public class NodeEdge implements Comparable<NodeEdge>
{
	private int nodei;
	private int nodej;
	private double weight;

	public NodeEdge(int i, int j, double wt) 
	{
		this.nodei = i;
		this.nodej = j;
		this.weight = wt;
	}
	
	public void setNodei(int i) 
	{
		this.nodei = i;
	}

	public void setNodej(int j) 
	{
		this.nodej = j;
	}
	
	public void setWeight(double wt) 
	{
		this.weight = wt;
	}
	
	public int getNodei()
	{
		return nodei;
	}
	
	public int getNodej() 
	{
		return nodej;
	}
	
	public double getWeight() 
	{
		return weight;
	}

	public int other(int i) 
	{
		if(i==this.nodei)
		{
			return this.nodej;
		}
		else if(i==this.nodej)
		{
			return this.nodei;
		}
		else
		{
			return -1;
		}
	}

	//Compares the edge weight of two nodes
	@Override
	public int compareTo(NodeEdge e) 
	{
		if(this.getWeight() > e.getWeight())
		{
			return +1;
		}
		else if(this.getWeight() == e.getWeight())
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

}
