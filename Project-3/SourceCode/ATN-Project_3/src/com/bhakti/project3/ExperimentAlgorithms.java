package com.bhakti.project3;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ExperimentAlgorithms 
{
	/*Given at least 5 examples; so lets take 6 examples here*/
	private static int numberOfExamples = 6;

	/*Take n random points in the plane; n >= 15; lets take n = 18 here*/
	private static int n = 18;
	public static int x,y;
	public static long Algo1Time1, Algo1Time2, Algo2Time1, Algo2Time2;
	public static IndexMinPQ<Double> pqMin;
	public static void main(String[] args) 
	{
		for(int ex=0;ex<numberOfExamples;ex++)
		{
			System.out.println("Experiment Number: "+(ex+1)+" starts here:");
			HashMap<Integer, CoordinatePoints> points = new HashMap<Integer, CoordinatePoints>();
			NumberFormat nf = NumberFormat.getInstance();

			/*Creates n number of points on a plane by generating x and y coordinates from 0-100*/
			for(int i=0;i<=n;i++) 
			{
				x = (int)(Math.random()*(100));
				y = (int)(Math.random()*(100));
				points.put(i, new CoordinatePoints(x, y));
				//System.out.println("points: "+points.get(i));
			}
			
			for(Map.Entry<Integer, CoordinatePoints> entry: points.entrySet())
			{
				int key = entry.getKey();
				CoordinatePoints value = entry.getValue();
				//System.out.println("Point number "+(key)+" is: "+"("+(value.x)+","+(value.y)+")");
			}
			
			/*Running the Local Search Heuristic Algorithm*/
			Algo1Time1 = System.currentTimeMillis();
			Algorithm2_LocalSearch.init(n, points);
			Algo1Time2 = System.currentTimeMillis();
			System.out.println("The running time of Local Search Heuristic Algorithm: "+(Algo1Time2-Algo1Time1)+" ms");
			
			//run Constructive Heuristic Algorithm
			Algo2Time1 = System.currentTimeMillis();
			Algorithm1_Constructive.init(n, points);
			Algo2Time2 = System.currentTimeMillis();
			System.out.println("The running time of Constructive Heuristic Algorithm: "+(Algo2Time2-Algo2Time1)+" ms");
			
			System.out.println("Experiment Number: "+(ex+1)+" ends here!");
		}
	}
	
}
