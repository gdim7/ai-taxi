import java.util.LinkedList;

public class ResultClass {
	
	/*
	 * ResultClass is a class that is generated from the Astar for each taxi
	 */
	
	/*
	 * @BestWay = list thats contains all the nodes from the start
	 * 				node of the given taxi to the client 
	 */
	private LinkedList<Vertex> BestWay = new LinkedList<Vertex>();
	
	/*
	 * @BestDistance = double variable that reflects the distance for the
	 * 				given BestWay list
	 */
	private double BestDistance;
	
	/*
	 * constructor of the ResultClass
	 */
	public ResultClass(LinkedList<Vertex> LnkList, double BstDis )
	{
		super();
		this.BestDistance = BstDis;
		this.BestWay = LnkList;
	
		
	}
	
	/*
	 * @makeList sets the current BestWay list to be equal 
	 * with a given list
	 */
	public void makeList(LinkedList<Vertex> LnkList)
	{
		this.BestWay = LnkList;
	}
	
	
	/*
	 *@makeDist sets the current BestDIstance to be equal with
	 *a given distance 
	 */
	public void makeDist(double BstDis)
	{
		this.BestDistance = BstDis;
	}
	
	
	/*
	 * @getBestWay returns the current BestWay list
	 */
	public LinkedList<Vertex> getBestWay()
	{
		return this.BestWay;
	}
	
	
	/*
	 * @getBestDistance returns the current BestDistance double Variable
	 */
	public double getBestDistance()
	{
		return this.BestDistance;
	}
}