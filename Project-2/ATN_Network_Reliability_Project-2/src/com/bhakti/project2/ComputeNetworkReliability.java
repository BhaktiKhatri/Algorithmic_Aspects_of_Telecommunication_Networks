package com.bhakti.project2;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class ComputeNetworkReliability 
{
		public static double linkrel; 
		public static int graphMatrix[][]; 
		public static int mapMatrixLink[][];	
		public static double networkReliability;
	
		public static void main(String[] args)
		{
			/*p- reliability of every link; k- number of combinations*/
			double p; 
			int k=0; 
			DecimalFormat df = new DecimalFormat("#.##");
			
			for(p=0.05;p<1.05;p+= 0.05) 
			{
				System.out.println("Link reliability p: "+Double.valueOf(df.format(p))+" has network reliability: "+computePKReliability(p,k));
			}
			
			for(k=0;k<21;k++)
			{
				double reliability = 0;
				for(int j=0;j<100;j++)
				{
					reliability = reliability +computePKReliability(0.9, k);
				}
				reliability = reliability/100;
				System.out.println("k: "+k+" has reliability: "+reliability);
			}
		}
	
		/* To compute reliability for value of p (reliability of every link) and k (number of combinations) */
		public static double computePKReliability(double p, int k)
		{
			ArrayList<Integer> randomStates= new ArrayList<Integer>();
			double reliability=0;
	
			for(int j = 0; j < k; j++) 
			{
				Random rnum = new Random();
				int randomnum = rnum.nextInt(1024);
				   
				while(randomStates.contains(randomnum))
				{
				   randomnum = rnum.nextInt(1024);
				}
				randomStates.add(randomnum);
			}		
			
			for(int i=0;i<=1023;i++) 
			{
				buildMatrix();
				linkrel=p;
				String state = String.format("%10s", Integer.toBinaryString(i)).replace(" ","0");
				
				for(int s=0;s<10;s++)
				{
					if (state.charAt(s) =='1')
					{
						graphMatrix[mapMatrixLink[s][0]][mapMatrixLink[s][1]]= 0;
						graphMatrix[mapMatrixLink[s][1]][mapMatrixLink[s][0]]= 0;
					}
				}
	
				if(randomStates.contains(i))
				{
					if(!checkGraphConnection())
					{
						reliability = reliability + computeGraphReliability(linkrel);
					}
				}
				else
				{
					if(checkGraphConnection())
					{
						reliability = reliability + computeGraphReliability(linkrel);
					}
				}
			}
			return reliability;
		}
		
		//To build a graph matrix
		public static void buildMatrix()
		{
			linkrel = 0;
			int key = 0;
			networkReliability = 1;
			//given n=5 nodes for complete undirected graph
			graphMatrix= new int[5][5];
			/* no of edges- [n * (n-1)]/2 so initialize matrix with 10 edges and 2 nodes */
			mapMatrixLink = new int[10][2];
			
			for(int i=0;i<=4;i++)
			{
				for(int j=0;j<=4;j++)
				{
					if (i!=j) 
					{
						if(i>j) 
						{
							mapMatrixLink[key][0]= i;
							mapMatrixLink[key][1]= j;
							key++;
						}
						graphMatrix[i][j]=1;
					}
					else
					{
						graphMatrix[i][j]=0;
					}
				}
			}
		}
	
		//To compute graph reliability
		public static double computeGraphReliability(double p)
		{
			for(int i=0;i<=4;i++)
			{
				for(int j=0;j<=4;j++)
				{
					if (i>j)
					{
						if (graphMatrix[i][j]==1) 
						{
							networkReliability = networkReliability * p;
						}
						else 
						{
							networkReliability = networkReliability * (1 - p);
						}
					}
				}
			}
			return networkReliability;
		}
	
		//To compute DFS for all n=5 nodes
		public static void depthFirstSearch(int i, boolean visitedNode[])
		{
			visitedNode[i] = true;
			for(int n=0;n<=4;n++)
			{
				if(graphMatrix[i][n] == 1)
					if(!visitedNode[n])
						depthFirstSearch(n,visitedNode);
			}
		}
		
		//To check if graph is connected
		public static boolean checkGraphConnection()
		{
			boolean connectedFlag = true;
			boolean visitedNode[] = new boolean[5];
			
			for(int i=0;i<=4;i++)
				visitedNode[i] = false;
			
			depthFirstSearch(0,visitedNode);		
			
			for(int i=0;i<=4;i++)
				if(!visitedNode[i])
					connectedFlag = false;

			return connectedFlag;
		}
}