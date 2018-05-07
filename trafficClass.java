public class trafficClass 
{
	private int idTraffic;
	
	private String nameTraffic;
	
	private String infoTraffic;
	
	public trafficClass(int id, String name, String info)
	{
		super();
		this.idTraffic = id;
		this.nameTraffic = name;
		this.infoTraffic = info;
	}
	
	public String getInfoTraffic()
	{
		return this.infoTraffic;
	}
	
	public String getNameTraffic()
	{
		return this.nameTraffic;
	}
	
	public int getIdTraffic()
	{
		return this.idTraffic;
	}
}