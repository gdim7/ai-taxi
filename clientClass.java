public class clientClass
{
	/* @ xClient = X coordinate of the client*/
	private double xClient;
	
	/* @ xClient = Y coordinate of the client*/
	private double	yClient;
	/*
	 * @ xDest = is the X coordiante of the destination
	 */
	private double xDest;
	
	/*
	 * @ yDest = Y coordinate of the destination
	 */
	private double yDest;
	
	/*
	 * @ hrs = the hours of the specific time we call the taxi app
	 */
	private int hrs;
	
	/*
	 * @ mins = the minutes of the specific time we call the taxi
	 */
	
	private int mins;
	
	/*
	 * @ persons = the number of persons that will be carried within the taxi
	 */
	private int persons;
	
	/*
	 * @ language = the language/es we speak 
	 */
	
	private String language;
	
	
	/*
	 * @ luggage = the number of luggages that we may carry 
	 */
	private int luggage;
	
	/*	constructor of a clientClass */
	public clientClass(double x, double y, double xD, double yD, int h, int m, int p, String lang, int lug)
	{
		super();
		this.xClient = x;
		this.yClient = y;
		this.xDest = xD;
		this.yDest = yD;
		this.hrs = h;
		this.mins = m;
		this.language = lang;
		this.luggage = lug;
	}
	
	/* returns the X coordinate of the client  */
	public double getXclient()
	{
		return xClient;
	}
	
	/* returns the Y coordinate of the client  */
	public double getYclient()
	{
		return yClient;
	}
	
	/* returns the X coordinate of the destination  */
	public double getXdest()
	{
		return xDest;
	}
	
	/* returns the Y coordinate of the destination  */
	public double getYdest()
	{
		return yDest;
	}
	
	
	/* returns the hours of the time that we call the taxi app  */
	public int getClientHrs()
	{
		return hrs;
	}
	
	/* returns the minutes of the time that we call the taxi app  */
	public int getClientMins()
	{
		return mins;
	}
	
	/* returns the number of persons that will be carried within/by the taxi  */
	public int getPersons()
	{
		return persons;
	}
	/* returns the language/es that we speak*/
	
	public String getLanguage()
	{
		return language;
	}
	
	
	/* returns the number of luggages that we wish to bring */
	public int getLuggage()
	{
		return luggage;
	}
	
	/* changes the X coordinate of the client 
	 * according to a given value */
	public void setXclient(double x)
	{
		this.xClient = x;
	}
	
	/* changes the Y coordinate of the client
	 * according to a given value  */
	public void setYclient(double y)
	{
		this.yClient = y;
	}
}