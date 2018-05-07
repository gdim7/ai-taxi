public class linesClass {
	
	/*
	 * linesClass contains the id of the line all the attributes of the line
	 */
	private int idLine;
	
	private String highway;
	
	private String nameLine;
	
	private String oneway;
	
	private String lit;
	
	private int lanes;
	
	private int maxspeed;
	
	private String railway;
	
	private String boundary;
	
	private String access;
	
	private String natural;
	
	private String barrier;
	
	private String tunnel;
	
	private String bridge;
	
	private String incline;
	
	private String waterway;
	
	private String busway;
	
	private String toll;
	/*
	 * constructor of the linesClass
	 */
	public linesClass(int id, String h, String name, String one, 
			String lit, int lanes, int max,String rail,String bound, String acc,String nat, 
			String barr, String tun, String br, String water, String bu, String toll)
	{
		super();
		this.idLine = id;
		this.highway = h;
		this.nameLine = name;
		this.oneway = one;
		this.lit = lit;
		this.lanes = lanes;
		this.maxspeed = max;
		this.railway = rail;
		this.boundary = bound;
		this.access = acc;
		this.natural = nat;
		this.barrier = barr;
		this.tunnel = tun;
		this.bridge = br;
		//this.incline = inc;
		this.waterway = water;
		this.busway = bu;
		this.toll = toll;
	}
	
	public String getWaterway()
	{
		return this.waterway;
	}
	
	public String getNatural()
	{
		return this.natural;
	}
	
	public String getBoundary()
	{
		return this.boundary;
	}
	
	public String getRailWay()
	{
		return this.railway;
	}
	
	public String getBridge()
	{
		return this.bridge;
	}
	
	public String getToll()
	{
		return this.toll;
	}
	
	public String getBusway()
	{
		return this.busway;
	}
	
	public String getIncline()
	{
		return this.incline;
	}
	
	public String getTunnel()
	{
		return this.tunnel;
	}
	
	public String getBarrier()
	{
		return this.barrier;
	}
	
	public String getAccess()
	{
		return this.access;
	}
	
	public int getMaxspeed()
	{
		return this.maxspeed;
	}
	
	public int getLanes()
	{
		return this.lanes;
	}
	
	public String getLit()
	{
		return this.lit;
	}
	
	public String getOneway()
	{
		return this.oneway;
	}
	
	public String getNameLine()
	{
		return this.nameLine;
	}
	
	public String getHighway()
	{
		return this.highway;
	}
	
	public int getIdLine()
	{
		return this.idLine;
	}
}