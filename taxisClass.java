public class taxisClass
{
	/* @ xTaxi = X coordinate of the taxi*/
	private double xTaxi;
	
	/* @ yTaxi = Y coordinate of the taxi*/
	private double yTaxi;
	
	/* @ idTaxi = id of the taxi*/
	private int idTaxi;

	private String available;
	
	private String capacity;
	
	private String languagesTaxi;
	
	private double rating;
	
	private String long_distance;
	
	private String type;
	
	
	
	/*	constructor of a taxisClass */
	public taxisClass(double x, double y, int id, String a, String c, String lang, double rat, String dist, String type)
	{
		super();
		this.xTaxi = x;
		this.yTaxi = y;
		this.idTaxi = id;
		this.available = a;
		this.capacity = c;
		this.languagesTaxi = lang;
		this.rating = rat;
		this.long_distance = dist;
		this.type = type;
	}

	/* returns the X coordinate of the taxi  */
	public double getXtaxi()
	{
		return xTaxi;
	}

	/* returns the Y coordinate of the taxi  */
	public double getYtaxi()
	{
		return yTaxi;
	}

	/* returns the id of the taxi  */
	public int getIDtaxi()
	{
		return idTaxi;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getLongDistance()
	{
		return long_distance;
	}
	
	public double getRating()
	{
		return rating;
	}
	
	public String getTaxiLanguages()
	{
		return languagesTaxi;
	}
	
	public String getCapacity()
	{
		return capacity;
	}
	
	public String getAvailable()
	{
		return available;
	}
}