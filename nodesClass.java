public class nodesClass
{
	/* @ xRoad = X coordinate of the node */
	private double xRoad;
	
	/* @ yRoad = Y coordinate of the node */
	private double yRoad;
	
	/* @ idRoad = id of the road in which the node is */
	private int idRoad;
	
	private long idNode;
	
	/* @ nameRoad = name of the road in which the node is */
	private String nameRoad;

	/*	constructor of a nodesClass */
	public nodesClass(double x, double y, int id, long idN, String name)
	{
		super();
		this.xRoad = x;
		this.yRoad = y;
		this.idRoad = id;
		this.idNode = idN;
		this.nameRoad = name;
	} 

	/* returns the X coordinate of the node */
	public double getXroad()
	{
		return this.xRoad;
	}

	/* returns the Y coordinate of the node */
	public double getYroad()
	{
		return this.yRoad;
	}

	/* returns the id of the road in which the node is */
	public int getIDroad()
	{
		return this.idRoad;
	}
	
	public long getIDnode()
	{
		return this.idNode;
	}

	/* returns the name of the road in which the node is */
	public String getNAMEroad()
	{
		return this.nameRoad;
	}
	
	/* changes the X coordinate of the node 
	 * according to a given value */
	public void setXroad(double x)
	{
		this.xRoad = x;
	}

	/* changes the Y coordinate of the client 
	 * according to a given value */
	public void setYroad(double y)
	{
		this.yRoad = y;
	}

	/* changes the id of the road in which 
	 * the node is according to a given value */
	public void setIDroad(int id)
	{
		this.idRoad = id;
	}

	/* changes the name of the road in which 
	 * the node is according to a given value */
	public void setNAMEroad(String name)
	{
		this.nameRoad = name;
	}
	
	
}