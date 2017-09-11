package com.bhakti.project3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.bhakti.project3.*;

public class Algorithm2_LocalSearch 
{
	
	public static void init(int n, HashMap<Integer, CoordinatePoints> points)
	{
		/*Generate a fully connected graph and check if it satisfies all the 3 conditions*/
		GenerateGraph graph = GraphProperties.generateFullyConnectedGraph(n, points);

		boolean isNodeConnected = new CheckConnectedNodes(graph).isNodeConnected();
		boolean isAtLeastDegreeThree = GraphProperties.isDegreeThree(graph, 3);
		boolean isAtMostDiameterFour = GraphProperties.isDiameterFour(graph, 4);
		boolean allThreeConditionsSatisfied = isNodeConnected && isAtLeastDegreeThree && isAtMostDiameterFour;
		
		HashSet<String> unremovableEdges = new HashSet<String>();
		double currentCost = GraphProperties.getCost(graph);
		System.out.println("Local Search Current Cost [fully connected] -> " + currentCost);
		
		while(true)
		{
			/*Finds an edge with the largest weight and remove it else break*/
			List<NodeEdge> edges = graph.edges();
			double weight = 0.0;
			NodeEdge heaviestEdge = null;
			for (NodeEdge edge : edges) 
			{
				if(weight < edge.getWeight() && !unremovableEdges.contains(edge.getNodei() + "" + edge.getNodej()) && !unremovableEdges.contains(edge.getNodej() + "" + edge.getNodei()))
				{
					weight = edge.getWeight();
					heaviestEdge = edge;
				}
			}
			
			if(heaviestEdge==null)
			{
				break;
			}
			
			graph.getAdjMatrix()[heaviestEdge.getNodei()][heaviestEdge.getNodej()] = 0;
			graph.getAdjMatrix()[heaviestEdge.getNodej()][heaviestEdge.getNodei()] = 0;
			
			isNodeConnected = new CheckConnectedNodes(graph).isNodeConnected();
			isAtLeastDegreeThree = GraphProperties.isDegreeThree(graph, 3);
			isAtMostDiameterFour = GraphProperties.isDiameterFour(graph, 4);
			allThreeConditionsSatisfied = isNodeConnected && isAtLeastDegreeThree && isAtMostDiameterFour;
			
			if(allThreeConditionsSatisfied)
			{
    			currentCost = GraphProperties.getCost(graph);
    			System.out.println("Local Search Current Cost -> " + currentCost);
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