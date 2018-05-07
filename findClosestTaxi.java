/*
 * @author Dimitrakopoulos Giorgos, Karavasilis Nikos
 * @AM					  03113211, 03113012
 * @Semester					7
 * @Date					January 2017
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.ugos.jiprolog.engine.JIPSyntaxErrorException;


public class findClosestTaxi
{
	
	/*
	 * @findEuclDistance calculates the euclidean distance between two points
	 * @x1 = X coordinate of the first Point
	 * @x2 = X coordinate of the second Point
	 * @y1 = Y coordinate of the first Point
	 * @y2 = Y coordinate of the second Point
	 */
	public static double findEuclDistance(double x1, double x2, double y1, double y2)
	{
		double temp1 = Math.pow(x1 - x2, 2);
		double temp2 = Math.pow(y1 - y2, 2);
		return Math.sqrt(temp1 + temp2);
	}
	
	
	/*
	 * this function is used in order to change each euclidean distance we find to the
	 * kilometer scale.Basically we pass the coordinates from two points of the map
	 * and the function returns the distance scaled to kilometers.
	 * @ lat1 = X coordinate of the first point
	 * @ lng1 = Y coordinate of the first point
	 * @ lat2 = X coordinate of the second point
	 * @ lng2 = Y coordinate of the second point 
	 */
	 public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		    double earthRadius = 6371; //Kmeters
		    double dLat = Math.toRadians(lat2-lat1);
		    double dLng = Math.toRadians(lng2-lng1);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
		               Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    double dist = (earthRadius * c);

		    return dist;
	}
	

	/*
	 * main gets the given argument and produces the result
	 */
	public static void main(String[] args) throws JIPSyntaxErrorException, IOException
	{
		if (args.length > 2)
		{
			/*
			 * @args[0] = csv file for client
			 * @args[1] = csv file for taxis
			 * @args[2] = csv file for nodes
			 * @args[3] = csv file for lines
			 * @args[4] = csv file for traffic
			 */
			String file1 = new String(args[0]);
			String file2 = new String(args[1]);
			String file3 = new String(args[2]);
			String file4 = new String(args[3]);
			String file5 = new String(args[4]);
			/*
			 * we use the readCSVfile void to produce the lists 
			 * for client,taxis and nodes
			 */
			readCSVfile.clientsFile(file1);
			readCSVfile.taxisFile(file2);
			readCSVfile.nodesFile(file3);
			readCSVfile.linesFile(file4);
			readCSVfile.trafficFile(file5);
			
			/*
			 * @ lineData = HashMap with key = the id of the road and value = the lineClass containing all the knowledge
			 * 				about a specific road.
			 * @ trafficData = HashMap with key = the id of the road given in the traffic.csv file and 
			 * 				value = the trafficClass containing all the knowledge about the road stored in the key 
			 * Basically, we create these two HashMaps so that we could get any lineClass and trafficClass when we have
			 * the id of the road.As a result we make the program faster.
			 */
			
			HashMap<Integer, linesClass> lineData = new HashMap<Integer, linesClass>();
			HashMap<Integer, trafficClass> trafficData = new HashMap<Integer, trafficClass>();
			
			
			for(int i=0; i < readCSVfile.lines.size(); i++)
			{
				lineData.put(readCSVfile.lines.get(i).getIdLine(), readCSVfile.lines.get(i));
				trafficData.put(readCSVfile.traffic.get(i).getIdTraffic(), readCSVfile.traffic.get(i));
			}
			
			/*
			 * Create a new file with all our data in Prolog, so we can use them later from the A* class to calculate
			 * new heuristic Values and join the correct nodes.
			 * 
			 */
			try{
				
				/*
				 * create the prolog file and give it the name : prologFile.pl
				 */
			    PrintWriter writer = new PrintWriter("prologFile.pl", "UTF-8");
			    
			    writer.println();
			    
			    int iD = readCSVfile.nodes.get(0).getIDroad();
			    
			    
			    for (int i = 0; i < readCSVfile.nodes.size(); i++)
			    {
			    	writer.println("belongsTo(" + readCSVfile.nodes.get(i).getIDnode() + "," + readCSVfile.nodes.get(i).getIDroad() + ").");
			    }
			    writer.println();
			    for (int i = 1; i < readCSVfile.nodes.size(); i++)
			    {
			    	if (readCSVfile.nodes.get(i).getIDroad() == iD)
			    	{
			    		if((lineData.get(readCSVfile.nodes.get(i).getIDroad()).getOneway() == null)
			    				|| (lineData.get(readCSVfile.nodes.get(i).getIDroad()).getOneway().compareTo("yes") == 0))
			    		{
			    			writer.println("nextTo(" + readCSVfile.nodes.get(i-1).getIDnode() + "," + readCSVfile.nodes.get(i).getIDnode() + ").");
			    		}
			    		
			    		else if(//(lineData.get(readCSVfile.nodes.get(i).getIDroad()).getOneway() != null)
			    				  (lineData.get(readCSVfile.nodes.get(i).getIDroad()).getOneway().compareTo("-1") == 0))
			    		{
			    			writer.println("nextTo(" + readCSVfile.nodes.get(i).getIDnode() + "," + readCSVfile.nodes.get(i-1).getIDnode() + ").");
			    		}
			    		else
			    		{
			    			writer.println("nextTo(" + readCSVfile.nodes.get(i-1).getIDnode() + "," + readCSVfile.nodes.get(i).getIDnode() + ").");
			    			writer.println("nextTo(" + readCSVfile.nodes.get(i).getIDnode() + "," + readCSVfile.nodes.get(i-1).getIDnode() + ").");
			    		}
			    	}
			    	else
			    	{
			    		iD = readCSVfile.nodes.get(i).getIDroad();
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {			    	
			    	if(readCSVfile.lines.get(i).getHighway() == null)
			    	{
			    		writer.println("highway(" + readCSVfile.lines.get(i).getIdLine() + ",none)." );
			    	}
			    	else
			    	{
			    		writer.println("highway(" + readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getHighway() + ")." );
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getLanes() ==  -1)
			    	{
			    		writer.println("lanes(" + readCSVfile.lines.get(i).getIdLine() + ",1).");
			    	}
			    	else
			    	{
			    		writer.println("lanes(" + readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getLanes() + ").");
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getMaxspeed() ==  -1)
			    	{
			    		writer.println("maxspeed(" + readCSVfile.lines.get(i).getIdLine() + ",50).");
			    	}
			    	else
			    	{
			    		writer.println("maxspeed(" + readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getMaxspeed() + ").");
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getRailWay() == null)
			    	{
			    		writer.println("railway(" +  readCSVfile.lines.get(i).getIdLine() + ",no).");
			    	}
			    	else
			    	{
			    		writer.println("railway(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getRailWay() + ").");
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getAccess() == null)
			    	{
			    		writer.println("access(" +  readCSVfile.lines.get(i).getIdLine() + ",yes).");
			    	}
			    	else
			    	{
			    		writer.println("access(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getAccess() + ").");
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getBoundary() == null)
			    	{
			    		writer.println("boundary(" + readCSVfile.lines.get(i).getIdLine() + ",yes).");
			    	}
			    	else
			    	{
			    		writer.println("boundary(" + readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getBoundary() + ").");
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getNatural() == null)
			    	{
			    		writer.println("natural(" +  readCSVfile.lines.get(i).getIdLine() + ",no).") ;
			    	}
			    	else
			    	{
			    		writer.println("natural(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getNatural() + ").") ;
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getBarrier() == null)
			    	{
			    		writer.println("barrier(" +  readCSVfile.lines.get(i).getIdLine() + ",no).") ;
			    	}
			    	else
			    	{
			    		writer.println("barrier(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getBarrier() + ").") ;
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getTunnel() == null)
			    	{
			    		writer.println("tunnel(" +  readCSVfile.lines.get(i).getIdLine() + ",yes).") ;
			    	}
			    	else
			    	{
			    		writer.println("tunnel(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getTunnel() + ").") ;
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getBridge() == null)
			    	{
			    		writer.println("bridge(" +  readCSVfile.lines.get(i).getIdLine() + ",no).") ;
			    	}
			    	else
			    	{
			    		writer.println("bridge(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getBridge() + ").") ;
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getWaterway() == null)
			    	{
			    		writer.println("waterway(" +  readCSVfile.lines.get(i).getIdLine() + ",no).") ;
			    	}
			    	else
			    	{
			    		writer.println("waterway(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getWaterway() + ").") ;
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getBusway() != null)
			    	{
			    		writer.println("busway(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getBusway() + ").") ;
			    	}
			    	else
			    	{
			    		writer.println("busway(" +  readCSVfile.lines.get(i).getIdLine() + ",no).") ;
			    	}
			    }
			    writer.println();
			    for (int i = 0; i < readCSVfile.lines.size(); i++)
			    {
			    	if(readCSVfile.lines.get(i).getToll() ==  null )
			    	{
			    		writer.println("toll(" +  readCSVfile.lines.get(i).getIdLine() + ",no).") ;
			    	}
			    	else
			    	{
			    		writer.println("toll(" +  readCSVfile.lines.get(i).getIdLine() + "," + readCSVfile.lines.get(i).getToll() + ").") ;
			    	}
			    }	 	
			    writer.println();
			    for(int i =0; i<readCSVfile.taxis.size() ; i++)
			    {
			    	writer.println("available(" + readCSVfile.taxis.get(i).getIDtaxi() + "," + readCSVfile.taxis.get(i).getAvailable() + ").");
			    }
			    writer.println();
			    for(int i =0; i<readCSVfile.taxis.size() ; i++)
			    {
			    	String[] tokens = readCSVfile.taxis.get(i).getCapacity().split("-");
			    	
			    	writer.println("capacity(" + readCSVfile.taxis.get(i).getIDtaxi() + "," + tokens[1] + ").");
			    }
			    writer.println();
			    for(int i =0; i<readCSVfile.taxis.size() ; i++)
			    {
			    	String[] tokens1 = readCSVfile.taxis.get(i).getTaxiLanguages().split(Pattern.quote("|"));
			    	
			    	for(int j=0 ; j< tokens1.length ; j++ )
			    	{
			    		writer.println("languageTaxi(" + readCSVfile.taxis.get(i).getIDtaxi() + "," + tokens1[j] + ").");
			    	}
			    }
			    writer.println();
			    for(int i =0; i<readCSVfile.taxis.size() ; i++)
			    {
			    	writer.println("rating(" + readCSVfile.taxis.get(i).getIDtaxi() + "," + readCSVfile.taxis.get(i).getRating() + ").");
			    }
			    writer.println();
			    for(int i =0; i<readCSVfile.taxis.size() ; i++)
			    {
			    	writer.println("longDistance(" + readCSVfile.taxis.get(i).getIDtaxi() + "," + readCSVfile.taxis.get(i).getLongDistance() + ").");
			    }
			    writer.println();
			    for(int i =0; i<readCSVfile.taxis.size() ; i++)
			    {
			    	String[] tokens2 = readCSVfile.taxis.get(i).getType().split("\\s+");
			    	writer.println("taxiType(" + readCSVfile.taxis.get(i).getIDtaxi() + "," + tokens2[0] + ").");	
			    }
			    writer.println();
			    for(int i = 0; i < readCSVfile.traffic.size(); i++)
			    {
			    	if (readCSVfile.traffic.get(i).getInfoTraffic() != null)
			    	{
			    		String[] tokens3 = readCSVfile.traffic.get(i).getInfoTraffic().split(":|-|=|\\|");
			    		if(tokens3.length > 14)
			    		for (int j = 0; j < tokens3.length; j += 5)
			    		{
			    			writer.println("trafficInfo(" + readCSVfile.traffic.get(i).getIdTraffic() + "," + tokens3[j] + "," + tokens3[j+4] + ").");
			    		}
			    	}
			    	else
			    	{
			    		writer.println("trafficInfo(" + readCSVfile.traffic.get(i).getIdTraffic() + ",00,no).");
			    	}
			    }
			    writer.println();
			    writer.println("clientHrs(client," + readCSVfile.clients.get(0).getClientHrs() +").");
			    writer.println();
			   	writer.println("clientMins(client," + readCSVfile.clients.get(0).getClientMins() +").");
			   	writer.println();
				String[] tokens2 = readCSVfile.clients.get(0).getLanguage().split(Pattern.quote("|"));
		    	for(int j=0 ; j< tokens2.length ; j++ )
		    	{
		    		writer.println("languageClient(client," + tokens2[j] + ").");
		    	}	
		    	writer.println();
		    	writer.println("clientPersons(client," + readCSVfile.clients.get(0).getPersons() +").");
		    	writer.println();
		    	writer.println("clientLuggage(client," + readCSVfile.clients.get(0).getLuggage() +").");
			    writer.close();
			} catch (IOException e) {
			   System.out.println("Error in creating file");
			}
			
			System.out.println("nodes are "+ readCSVfile.nodes.size()+" lines are " + readCSVfile.lines.size()+" traffic are "+readCSVfile.traffic.size());
			System.out.println();
			/*
			 * We find the closest node,let it be A, to the client  
			 * in order to create Vertexes later with heuristic value
			 * from A
			 * @xx1 = X coordinate of the current node examined
			 * @xx2 = X coordinate of the client
			 * @yy1 = Y coordinate of the current node examined
			 * @yy2 = Y coordinate of the client
			 * @mink = lowest distance from client 
			 * @that = points to the node from nodes-list that
			 * 			reflects the lowest distance from the client
			 */
			double xx1 = readCSVfile.nodes.get(0).getXroad();
			double xx2 = readCSVfile.clients.get(0).getXclient();
			double yy1 = readCSVfile.nodes.get(0).getYroad();
			double yy2 = readCSVfile.clients.get(0).getYclient();
			double mink = findEuclDistance(xx1, xx2, yy1, yy2);
			int that = 0;
			for(int i=1;i<readCSVfile.nodes.size();i++){
				xx1 = readCSVfile.nodes.get(i).getXroad();
				yy1 = readCSVfile.nodes.get(i).getYroad();
				double min2 = findEuclDistance(xx1, xx2, yy1, yy2);
				if (Double.compare(mink, min2)>0){
					mink = min2;
					that = i;
				}
			}
			
			
			/*
			 * We find the closest node,let it be B, to the destination 
			 * in order to create Vertexes later with heuristic value
			 * from B and as start point the client coordinates
			 * @xx1 = X coordinate of the current node examined
			 * @xx2 = X coordinate of the client
			 * @yy1 = Y coordinate of the current node examined
			 * @yy2 = Y coordinate of the client
			 * @mink = lowest distance from client 
			 * @that1 = points to the node from nodes-list that
			 * 			reflects the lowest distance from the destination
			 */
			xx1 = readCSVfile.nodes.get(0).getXroad();
			xx2 = readCSVfile.clients.get(0).getXdest();
			yy1 = readCSVfile.nodes.get(0).getYroad();
			yy2 = readCSVfile.clients.get(0).getYdest();
			mink = findEuclDistance(xx1, xx2, yy1, yy2);
			int that1 = 0;
			for(int i=1; i<readCSVfile.nodes.size(); i++){
				xx1 = readCSVfile.nodes.get(i).getXroad();
				yy1 = readCSVfile.nodes.get(i).getYroad();
				double min2 = findEuclDistance(xx1, xx2, yy1, yy2);
				if (Double.compare(mink, min2)>0){
					mink = min2;
					that1 = i;
				}
			}
			
			/*
			 * Compiling the 2 prolog files is a very expencive procedure,
			 * so we do it one time in the begining of our main class.
			 */
			
			TaxiCheck.compileProlog();
			/*
			 * We create Vertexes(class) in which we store the heuristic value of
			 * all points of the map from the destination node that we specified above.
			 * That node is specified with the variable that1
			 * 
			 */
			Vertex[] graph2 = new Vertex[readCSVfile.nodes.size()];
			double x22 = readCSVfile.clients.get(0).getXdest();
			double y22 = readCSVfile.clients.get(0).getYdest();
			
			for (int i = 0; i < readCSVfile.nodes.size(); i++)
			{
				double x11 = readCSVfile.nodes.get(i).getXroad();
				double y11 = readCSVfile.nodes.get(i).getYroad();
				double hV = findEuclDistance(x11, x22, y11, y22);
				graph2[i] = new Vertex(readCSVfile.nodes.get(i), hV);
			}
			
			/* @nodeMapNew = HashMap with key = the id of every node
			 * 				and value = Vertex that holds the nodesClass
			 * 				that reflects that node
			 * This HashMap is used in the A star algorithm.Basically for each node we find
			 * the id of it's neighbors-nodes.We use this HashMap to get the Vertex in which each neighbor 
			 * belong.
			 */
			HashMap<Long, Vertex> nodeMapNew = new HashMap<Long, Vertex>();
			
			for (int i = 0; i < readCSVfile.nodes.size(); i++)
			{
				nodeMapNew.put(readCSVfile.nodes.get(i).getIDnode(), graph2[i]);
			}
			
			/*
			 * hashData = HashMap with key = the nodesClass that reflects a specific node
			 * 			and value = the Vertex with attribute that specific nodesClass
			 * Basically this HashMap and the previous one could be combined in order to use less memory
			 * but during the creation of the program the previous one was first created to be used in A star class
			 * and the idea of combining these two hashmaps whould demand many changes increasing the possibility of malfunctions.
			 */
			HashMap<nodesClass, Vertex > hashDataNew = new HashMap<nodesClass, Vertex>();
			for(int i=0; i<readCSVfile.nodes.size(); i++)
			{
				hashDataNew.put(readCSVfile.nodes.get(i), graph2[i]);
			}
			
			
			/*
			 * @ NewStart = the client node specified by variable that
			 * @ NewEnd = the destination node specified by variable that1
			 */
			Vertex NewStart = hashDataNew.get(readCSVfile.nodes.get(that));
			Vertex NewEnd = hashDataNew.get(readCSVfile.nodes.get(that1));
			NewEnd.changeHV(0.0);
			
			
			/*
			 * We run the A star in order to find the best way to get to the destination
			 * from the client.Lets assume that the overall distance is A kilometers
			 * The reason we do this first instead of finding the best way for each taxi to
			 * get to the client, is because we want to find if the distance A is bigger or 
			 * else than 30.As a result we are able to know if taxi is able to cover or not 
			 * a 30 kilometer distance.This helps to get create the list with the 5 best taxis.
			 */
			ResultClass finalClient = Astar.findpath(NewStart, NewEnd, nodeMapNew);
			
			/*
			 * The following lines of code reflect the same process we used 
			 * in order to create the Vertexes of graph2.This time we want to create Vertexes
			 * with heuristic value being the distance of each node from the cliend
			 * For each node we create the Vertex that stores it.
			 * @x1 = X coordinate of the current node examined
			 * @x2 = X coordinate of the node closest to client
			 * @y1 = Y coordinate of the current node examined
			 * @y2 = Y coordinate of the node closest to client
			 * @heuristicValue = the euclidean distance between the current node and 
			 * 					the node closest to client
			 * @distance = the euclidean distance between two nodes in a row
			 */
			Vertex[] graph = new Vertex[readCSVfile.nodes.size()];
			
			for(int i = 0; i<readCSVfile.nodes.size(); i++)
			{
				double x1 = readCSVfile.nodes.get(i).getXroad();
				double x2 = readCSVfile.nodes.get(that).getXroad();
				double y1 = readCSVfile.nodes.get(i).getYroad();
				double y2 = readCSVfile.nodes.get(that).getYroad();
				double heuristicValue = findEuclDistance(x1, x2, y1, y2);
				graph[i] = new Vertex(readCSVfile.nodes.get(i), heuristicValue);//, null, null);
			}
			
			
			HashMap<Long, Vertex> nodeMap = new HashMap<Long, Vertex>();
			
			for (int i = 0; i < readCSVfile.nodes.size(); i++)
			{
				nodeMap.put(readCSVfile.nodes.get(i).getIDnode(), graph[i]);
			}

			HashMap<nodesClass, Vertex > hashData = new HashMap<nodesClass, Vertex>();
			for(int i=0; i<readCSVfile.nodes.size(); i++)
			{
				hashData.put(readCSVfile.nodes.get(i), graph[i]);
			}
			
			Vertex end = hashData.get(readCSVfile.nodes.get(that));
			end.changeHV(0.0);
			/*
			 * @finalDistances = list that contains all the ResultClass elements that the Astar returns for
			 * 					each taxi
			 */
			
			ArrayList<ResultClass> finalDistances = new ArrayList<ResultClass>();
			
			double totalDistance = 0.0;
			
			double x1 = finalClient.getBestWay().get(0).node.getXroad();
			double y1 = finalClient.getBestWay().get(0).node.getYroad();
			for (int i = 1; i < finalClient.getBestWay().size(); i++)
			{
				double x2 = finalClient.getBestWay().get(i).node.getXroad();
				double y2 = finalClient.getBestWay().get(i).node.getYroad();
				double temp = distFrom(x1, y1, x2, y2);
				totalDistance += temp;
				x1 = x2;
				y1 = y2;
			}
			
			ArrayList<taxisClass> TaxiID = new ArrayList<taxisClass>();
			
			/*
			 * For each taxi we find the closest taxi and then if the taxi attributes
			 * are ideal in order for the taxi to be selected by the client, we run the
			 * A star to identify the best way to get to the client
			 */
			for (int i = 0; i < readCSVfile.taxis.size(); i++)
			{
				double xTaxi = readCSVfile.taxis.get(i).getXtaxi();
				double yTaxi = readCSVfile.taxis.get(i).getYtaxi();
				double xNode = readCSVfile.nodes.get(0).getXroad();
				double yNode = readCSVfile.nodes.get(0).getYroad();
				int index = 0;
				double minDistance = findEuclDistance(xTaxi, xNode, yTaxi, yNode);
				for (int j = 1; j < readCSVfile.nodes.size(); j++)
				{
					xNode = readCSVfile.nodes.get(j).getXroad();
					yNode = readCSVfile.nodes.get(j).getYroad();
					double minDistance1 = findEuclDistance(xTaxi, xNode, yTaxi, yNode);
					if((Double.compare(minDistance, minDistance1)) > 0)
					{
						minDistance = minDistance1;
						index = j;
					}
				}
				Vertex start = hashData.get(readCSVfile.nodes.get(index));
				int checker = TaxiCheck.taxiCheck(readCSVfile.taxis.get(i), totalDistance);
				if(checker == 5)
				{;
					ResultClass finaldist = Astar.findpath(start, end, nodeMap);
					finalDistances.add(finaldist);
					TaxiID.add(readCSVfile.taxis.get(i));
				}
			}
			
			Double distances[] = new Double[finalDistances.size()];
			
			HashMap<Double, ResultClass> finalHash = new HashMap<Double, ResultClass>();
			HashMap<Double, taxisClass> finalTaxi = new HashMap<Double, taxisClass>();
			for (int i =0 ; i< finalDistances.size() ; i++)
			{
				finalHash.put(finalDistances.get(i).getBestDistance(), finalDistances.get(i));
			}
			
			for (int i = 0; i < TaxiID.size(); i++)
			{
				finalTaxi.put(finalDistances.get(i).getBestDistance(), TaxiID.get(i));
			}
			
			for(int i=0 ; i< finalDistances.size() ; i++)
			{
				distances[i] = (finalDistances.get(i).getBestDistance());
			}
			
			Arrays.sort(distances);
			System.out.println("Five Best Taxis Are:");
			for (int i=0 ; i< 5 ; i++)
			{
				ResultClass temp = finalHash.get(distances[i]);
				totalDistance = 0.0;
				x1 = temp.getBestWay().get(0).node.getXroad();
				y1 = temp.getBestWay().get(0).node.getYroad();
				for (int j = 1; j < temp.getBestWay().size(); j++)
				{
					double x2 = temp.getBestWay().get(j).node.getXroad();
					double y2 = temp.getBestWay().get(j).node.getYroad();
					double temp1 = distFrom(x1, y1, x2, y2);
					totalDistance += temp1;
					x1 = x2;
					y1 = y2;
				}
				
				taxisClass tempTaxi = finalTaxi.get(distances[i]);
				
				System.out.println("ID: " + tempTaxi.getIDtaxi() + "  Distance: " + totalDistance + " Rating: " + tempTaxi.getRating());
			}
			
			
			
			/*
			 * We create the kml file.
			 */
			WriteXMLFile.makeXML(finalDistances, distances, finalHash, finalClient);
		
		}
	}
}