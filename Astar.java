import java.io.IOException;
import java.text.DecimalFormat;
import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Astar
{
	/*
	 * 	The Astar starts from the function findpath and returns a ResultClass in which we have
	 * 	the best Road and the best distance.
	 */

	
	
	/*
	 * lowestFNeighbor returns the Vertex from openSet with lowest F(from fScore)
	 */
	public static  Vertex lowestFNeighbor(ArrayList<Vertex> set, Map<Vertex, Double> f)
		{		
			int pointer = 0;
			double temp1 = f.get(set.get(0));
			for (int i = 1; i < set.size(); i++)
			{
				double temp = f.get(set.get(i));
				if(Double.compare(temp1, temp) > 0)
				{
					temp1 = temp;
					pointer = i;
				}
			}
			return set.get(pointer);
		}
	
	 
	 /* reconstructs the optimal path from the initial node to the
	  * goal node, by always adding the node from which each node
	  * from the optimal path came from, starting from the goal node. */
	public static LinkedList<Vertex> reconstructPath(Map<Vertex, Vertex> cf, Vertex cur)
	{
		LinkedList<Vertex> totalPath = new LinkedList<Vertex>();
		totalPath.add(cur);
		while(cf.containsKey(cur))
		{
			cur = cf.get(cur);
			totalPath.add(cur);
			
		}
		
		/* the list that is created by always adding the node from which
    	 * each node came from starting from the goal node produces the
    	 * desired list reversed, so we need to reverse it */
		Collections.reverse(totalPath);
		return totalPath;
	}
	
	/*
	 * 	In findpath:
	 * 	@closedSet = list that contains Vertex already visited
	 * 	@openSet = list that contains Vertex to be examined
	 * 	@cameFrom = HashMap that contains key=Vertex and Value=Vertex
	 * 				and defines for each key-Vertex which value-Vertex it
	 * 				can be reached from
	 * 	@gScore = HashMap that contains key=Vertex and Value=double
	 * 			and defines the cost of getting from the start Vertex
	 * 			to that Vertex-key
	 * 	@fScore = HashMap that contains key=vertex and Value=double
	 * 			and defines for each key-Vertex,the total cost of 
	 * 			getting from the start node to the goal	by passing
     * 			by that node. That value is partly known, partly heuristic
	 */	
	
	public static ResultClass findpath(Vertex start, Vertex end, HashMap<Long, Vertex> nodeMap) throws JIPSyntaxErrorException, IOException
	{
	
		JIPEngine jip = TaxiCheck.jip;
		JIPTermParser parser = jip.getTermParser();
		JIPQuery jipQuery; 
		JIPTerm term;
		ArrayList<Vertex> closedSet = new ArrayList<Vertex>();
		ArrayList<Vertex> openSet = new ArrayList<Vertex>();
		openSet.add(start);
		HashMap<Vertex, Vertex> cameFrom = new HashMap<Vertex, Vertex>();
		HashMap<Vertex, Double> gScore = new HashMap<Vertex, Double>();
		gScore.put(start, 0.0);
		HashMap<Vertex, Double> fScore = new HashMap<Vertex, Double>();
		fScore.put(start, start.getHeuristicValue());

		while(openSet.size() != 0)
		{
			/*
			 * the next Vertex to be examined from the openSet is
			 * the one with the lowest F(from fScore)
			 */
			Vertex current = lowestFNeighbor(openSet, fScore);
			if((Double.compare(current.node.getXroad(), end.node.getXroad()) == 0) && (Double.compare(current.node.getYroad(), end.node.getYroad()) == 0 ))
			{
				/*
				 * the best distance is the F value in fScore for the current Vertex if we subtract it's heuristic value
				 */
				ResultClass Result1 = new ResultClass(reconstructPath(cameFrom, current), fScore.get(current) - current.getHeuristicValue());
				return Result1;
			}
			openSet.remove(current);
			closedSet.add(current);
			
			ArrayList<Long> currNeigh = new ArrayList<Long>();
			jipQuery = jip.openSynchronousQuery(parser.parseTerm("canMoveFromTo(" + current.node.getIDnode() + ",Y)."));
			term = jipQuery.nextSolution();
			while(term != null)
			{
				double temp = Double.valueOf(term.getVariablesTable().get("Y").toString());
				DecimalFormat formatLong = new DecimalFormat("#0");
				currNeigh.add(Long.parseLong(formatLong.format(temp)));
				term = jipQuery.nextSolution();
			}

			for(int i = 0; i < currNeigh.size(); i++)
			{
				Vertex neighbor = nodeMap.get(currNeigh.get(i));
				if(closedSet.contains(neighbor))
				{
					continue;
				}
				double x1 = current.getNode().getXroad();
				double y1 = current.getNode().getYroad();
				double x2 = neighbor.getNode().getXroad();
				double y2 = neighbor.getNode().getYroad();
				double distance = findClosestTaxi.findEuclDistance(x1, x2, y1, y2);
				int roadID = neighbor.getNode().getIDroad();
				jipQuery = jip.openSynchronousQuery(parser.parseTerm("totalCost(" + roadID +",Y)."));
				term = jipQuery.nextSolution();
				double tempCost = Integer.valueOf(term.getVariablesTable().get("Y").toString());
				double fCost = new Double(gScore.get(current) + distance);
				if(!(openSet.contains(neighbor)))
				{
					openSet.add(neighbor);
				}
				else if(!(Double.compare(fCost, gScore.get(neighbor)) < 0))
				{
					continue;
				}
				cameFrom.put(neighbor, current);
				gScore.put(neighbor, fCost);
				fScore.put(neighbor, fCost + neighbor.getHeuristicValue() + tempCost*neighbor.getHeuristicValue()/1000);
			}
				
		}
		ResultClass Result2 = new ResultClass(reconstructPath(cameFrom, start), 0.0); 
		return Result2;

	}
	
}