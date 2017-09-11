package com.bhakti.project3;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import com.bhakti.project3.*;

public class Algorithm1_Constructive
{	
	public static void init(int n, HashMap<Integer, CoordinatePoints> points)
	{
		int[][] adjmatrix = new int[n][n];
		List<NodeEdge> edge = new ArrayList<NodeEdge>();
		
		/*Generates an empty graph for the components*/
		GenerateGraph graph = GraphProperties.generateEmptyGraph(n ,points);
		graph.setAdjMatrix(adjmatrix);
		
		/*Generates edges for points i & j*/
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(i<j)
				{
					edge.add(new NodeEdge(i, j, GraphProperties.getEdgeWeight(i, j, points)));
				}
			}
		}
		
		/*Sort the edge cost in ascending order*/
		Collections.sort(edge);

		/*Check the conditions (1),(2) and (3) of pdf i.e all the nodes are connected, degree is at least 3 and diameter is at most 4*/
		boolean isNodeConnected = new CheckConnectedNodes(graph).isNodeConnected();
		boolean isAtLeastDegreeThree = GraphProperties.isDegreeThree(graph, 3);
		boolean isAtMostDiameterFour = GraphProperties.isDiameterFour(graph, 4);
		boolean allThreeConditionsSatisfied = isNodeConnected && isAtLeastDegreeThree && isAtMostDiameterFour;
		double currCost = Double.POSITIVE_INFINITY;
		System.out.println("Constructive Current Cost [fully disconnected]: " + currCost);
		
		for (NodeEdge edg : edge)
		{
			int i = edg.getNodei();
			int j = edg.getNodej();
			graph.getAdjMatrix()[i][j] = 1;
			graph.getAdjMatrix()[j][i] = 1;

			isNodeConnected = new CheckConnectedNodes(graph).isNodeConnected();
			isAtLeastDegreeThree = GraphProperties.isDegreeThree(graph, 3);
			isAtMostDiameterFour = GraphProperties.isDiameterFour(graph, 4);
			allThreeConditionsSatisfied = isNodeConnected && isAtLeastDegreeThree && isAtMostDiameterFour;
    		
			//System.out.println("isNodeConnected: "+isNodeConnected);
			//System.out.println("isAtLeastDegreeThree: "+isAtLeastDegreeThree);
			//System.out.println("isAtMostDiameterFour: "+isAtMostDiameterFour);
			//System.out.println("allThreeConditionsSatisfied: "+allThreeConditionsSatisfied);
			
    		if(allThreeConditionsSatisfied)
    		{
    			currCost = GraphProperties.getCost(graph);
    			System.out.println("Constructive Current Cost [max] -> " + currCost);
    			break;
    		}
		}
		
		/*Track and save the edges that cannot be removed*/
		HashSet<String> unremovableEdges = new HashSet<String>();
		
		while(true)
		{
			/*Find the edge that has the maximum weight else break*/
			List<NodeEdge> edg = graph.edges();
			double wt = 0.0;
			NodeEdge heaviestEdge = null;
			for (NodeEdge ed : edg) 
			{
				if(wt < ed.getWeight() && !unremovableEdges.contains(ed.getNodei() + "" + ed.getNodej()) && !unremovableEdges.contains(ed.getNodej() + "" + ed.getNodei()))
				{
					wt = ed.getWeight();
					heaviestEdge = ed;
				}
			}
			
			if(heaviestEdge==null)
			{
				break;
			}
			graph.getAdjMatrix()[heaviestEdge.getNodei()][heaviestEdge.getNodej()] = 0;
			graph.getAdjMatrix()[heaviestEdge.getNodej()][heaviestEdge.getNodei()] = 0;
			
			/*Check if all the 3 conditions satisfy*/
			isNodeConnected = new CheckConnectedNodes(graph).isNodeConnected();
    		isAtLeastDegreeThree = GraphProperties.isDegreeThree(graph, 3);
    		isAtMostDiameterFour = GraphProperties.isDiameterFour(graph, 4);
    		allThreeConditionsSatisfied = isNodeConnected && isAtLeastDegreeThree && isAtMostDiameterFour;
    		
    		if(allThreeConditionsSatisfied)
    		{
    			//System.out.println("in here");
    			currCost = GraphProperties.getCost(graph);
    			System.out.println("Constructive Current Cost -> " + currCost);
    		}
    		else
    		{
    			graph.getAdjMatrix()[heaviestEdge.getNodei()][heaviestEdge.getNodej()] = 1;
    			graph.getAdjMatrix()[heaviestEdge.getNodej()][heaviestEdge.getNodei()] = 1;
    			unremovableEdges.add(heaviestEdge.getNodei() + "" + heaviestEdge.getNodej());
    			unremovableEdges.add(heaviestEdge.getNodej() + "" + heaviestEdge.getNodei());
    		}
		}
		
	}
}