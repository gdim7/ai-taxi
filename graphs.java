class Vertex
{
	/*
	 * Vertex is class that contains the node and it's heuristic 
	 * distance from goal node
	 */

	
	/*
	 * @node = the node that is assigned 
	 */
	nodesClass node;
	
	/*
	 * @heuristicValue = the euclidean distance from the current Vertex 
	 * to the goal vertex
	 */
	double heuristicValue;

	public Vertex(nodesClass nd, double hV) 
	{
		super();
		this.node = nd;
		this.heuristicValue = hV;
	}
	
	
	/*
	 * returns the node that is reflected from this Vertex
	 */
	public nodesClass getNode()
	{
		return this.node;
	}
	
	
	/*
	 * @changeHV changes the current heuristic distance
	 * with a given one
	 */
	public void changeHV(double hv)
	{
		this.heuristicValue = hv;
	}
	
	/*
	 * @getHeuristicValue returns the heuristicValue
	 * of this Vertex
	 */
	public double getHeuristicValue()
	{
		return heuristicValue;
	}
}



