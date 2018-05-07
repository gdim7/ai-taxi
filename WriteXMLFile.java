import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class WriteXMLFile {
	
	public static void makeXML(ArrayList<ResultClass> Result, Double dist[] , HashMap<Double, ResultClass> hash1, ResultClass cl)
	{
		try{
		DocumentBuilderFactory taxiFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder taxiBuilder = taxiFactory.newDocumentBuilder();		
		
		Document tax = taxiBuilder.newDocument();
		Element firstRoot = tax.createElement("kml");
		tax.appendChild(firstRoot);
		Element rootElement = tax.createElement("Document");
		firstRoot.appendChild(rootElement);
		
		//TaxiRoutes element
		Element TaxiRoutes = tax.createElement("name");
		TaxiRoutes.appendChild(tax.createTextNode("Taxi Routes"));
		rootElement.appendChild(TaxiRoutes);
		
		//Style id="green"
		CreateStyle(rootElement, "green", "ff009900", "4", tax);
		
		//Style id="red"
		CreateStyle(rootElement, "red", "ff0000ff", "2", tax);
		
		
		CreateStyle(rootElement, "blue", "ffff0000", "2", tax);
		
		//Style id="darkred" for Client and taxi Nodes
		CreatePoint(rootElement, "darkred", "ffcc0000", tax);
		
		
		//Show Client node
		CreatePlacemarkPoint(rootElement, "Client", tax,  readCSVfile.clients, null, 0, 0);
		
		CreatePlacemarkPoint(rootElement, "Destination", tax, readCSVfile.clients, null, 0 , 1);
		
		//Show taxis nodes
		for(int i=0 ; i<readCSVfile.taxis.size();i++)
		{
			CreatePlacemarkPoint(rootElement, "Taxi Node" + i, tax, null, readCSVfile.taxis, i , 0);
		}
		
		//Show for best distances	
		for(int i=0 ; i < dist.length ; i++)
		{
			ResultClass Res = hash1.get(dist[i]);
			if (i<5){	
				CreatePlacemarkLine(rootElement, "Taxi " + i, "#green", tax, Res.getBestWay(), i);			
			}
			else
			{
				CreatePlacemarkLine(rootElement, "Taxi " + i, "#red", tax, Res.getBestWay(), i);
			}

		}
		CreatePlacemarkLine(rootElement, "Destination", "#blue", tax, cl.getBestWay(), 0);
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(tax);


		
		/*
		* The user should choose the directory and insert it here
		*/
		StreamResult result = new StreamResult(new File("/home/george/Desktop/test.kml"));



		transformer.transform(source, result);

		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
	}
	public static void CreatePlacemarkLine(Element root, String name1, String color, Document tax, LinkedList<Vertex> list, int j)
	{
		Element PlaceMark = tax.createElement("Placemark");
		root.appendChild(PlaceMark);
		
		Element Name = tax.createElement("name");
		Name.appendChild(tax.createTextNode(name1));
		PlaceMark.appendChild(Name);
		
		Element styleURL = tax.createElement("styleUrl");
		styleURL.appendChild(tax.createTextNode(color));
		PlaceMark.appendChild(styleURL);
		
		Element LineString = tax.createElement("LineString");
		PlaceMark.appendChild(LineString);
		
		Element altitude = tax.createElement("altitudeMode");
		altitude.appendChild(tax.createTextNode("relativeToGround"));
		LineString.appendChild(altitude);
		
		Element coordinates = tax.createElement("coordinates");
		int i;
		for(i=0;i<list.size() ; i++)
		{
			coordinates.appendChild(tax.createTextNode(String.valueOf(list.get(i).getNode().getXroad())+","+String.valueOf(list.get(i).getNode().getYroad())+",0 "));
		}
		LineString.appendChild(coordinates);
		
	}
	public static void CreatePlacemarkPoint(Element root, String name1, Document tax, List<clientClass> nodes, List<taxisClass> taxis, int i, int flagg)
	{
		Element PlaceMark = tax.createElement("Placemark");
		root.appendChild(PlaceMark);
		
		Element Name = tax.createElement("name");
		Name.appendChild(tax.createTextNode(name1));
		PlaceMark.appendChild(Name);
		
		Element StyleURL = tax.createElement("styleUrl");
		StyleURL.appendChild(tax.createTextNode("#darkred"));
		PlaceMark.appendChild(StyleURL);
		
		Element Point = tax.createElement("Point");
		PlaceMark.appendChild(Point);
		
		Element Altitude = tax.createElement("altitudeMode");
		Altitude.appendChild(tax.createTextNode("relativeToGround"));
		Point.appendChild(Altitude);
		
		Element Coordinates = tax.createElement("coordinates");
		
		if (taxis == null)
		{
			if (flagg == 0)
			{
				Coordinates.appendChild(tax.createTextNode(String.valueOf(nodes.get(i).getXclient())+","+String.valueOf(nodes.get(i).getYclient())+",0 "));			
			}
			else
			{
				Coordinates.appendChild(tax.createTextNode(String.valueOf(nodes.get(i).getXdest())+","+String.valueOf(nodes.get(i).getYdest())+",0 "));	
			}
		}
		else
		{
				Coordinates.appendChild(tax.createTextNode(String.valueOf(taxis.get(i).getXtaxi())+","+String.valueOf(taxis.get(i).getYtaxi())+",0 "));	
		}
		Point.appendChild(Coordinates);
	}
	public static void CreatePoint(Element root, String color, String colorID, Document tax)
	{
		Element Style = tax.createElement("Style");
		root.appendChild(Style);
		Attr attr = tax.createAttribute("id");
		attr.setValue(color);
		
		Element PointStyle = tax.createElement("Point");
		Style.appendChild(PointStyle);
		
		Element PointColor = tax.createElement("color");
		PointColor.appendChild(tax.createTextNode(colorID));
		PointStyle.appendChild(PointColor);
		
	}
	public static void CreateStyle(Element root, String color, String colorID, String wid, Document tax)
	{
		Element Style = tax.createElement("Style");
		root.appendChild(Style);
		Attr attr = tax.createAttribute("id");
		attr.setValue(color);
		Style.setAttributeNode(attr);
		
		Element LineStyle = tax.createElement("LineStyle");
		Style.appendChild(LineStyle);
		
		Element LineColor = tax.createElement("color");
		LineColor.appendChild(tax.createTextNode(colorID));
		LineStyle.appendChild(LineColor);
		
		Element LineWidth = tax.createElement("width");
		LineWidth.appendChild(tax.createTextNode(wid));
		LineStyle.appendChild(LineWidth);
		
		
		
		
	}
}