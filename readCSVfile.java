import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readCSVfile
{
	/*	client is a list in which we store all the clients(clientClass type) */
	public static List<clientClass> clients = new ArrayList<>();

	/*	We first read the first line that contains no information and
	 * 	then for each line we create a new clientClass that stores the X,Y coordinates and the id of
	 * 	the X,Y coordinates and the id of the client.
	 * 	the clientClass is then added to the client list*/
	public static void clientsFile(String fileName)
	{
		BufferedReader fileReader = null;
		try 
		{
			String line = "";
		
			fileReader = new BufferedReader(new FileReader(fileName));
		
			fileReader.readLine();

			while ((line = fileReader.readLine()) != null) 
			{
             	   String[] tokens = line.split(",");
             	   String[] time = tokens[4].split(":");
             	   
             	  /*	@tokens[0] = X of the client
             	    * 	@tokens[1] = Y of the client */

                	 if (tokens.length > 0) 
                 	{
                 		clientClass client = new clientClass(
                 				Double.parseDouble(tokens[0]), 
                 				Double.parseDouble(tokens[1]), 
                 				Double.parseDouble(tokens[2]),
                 				Double.parseDouble(tokens[3]),
                 				Integer.parseInt(time[0]),
                 				Integer.parseInt(time[1]),
                 				Integer.parseInt(tokens[5]),
                 				tokens[6],
                 				Integer.parseInt(tokens[7]));
                 		clients.add(client);
                 	}
       		}
		}

       	catch (Exception e) 
       	{
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
			    fileReader.close();
            } 
            catch (IOException e) 
            {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
	}



	/*	taxis is a list in which we store all the taxis(taxisClass type) */
	public static List<taxisClass> taxis = new ArrayList<>();

	/*	We first read the first line that contains no information and
	 * 	then for each line we create a new taxisClass that 
	 * 	stores the X,Y coordinates and the id of the taxi.
	 * 	Each taxiClass is then added to the taxis list  */
	public static void taxisFile(String fileName)
	{
		BufferedReader fileReader = null;
		try 
		{
			String line = "";
		
			fileReader = new BufferedReader(new FileReader(fileName));
		
			fileReader.readLine();

			while ((line = fileReader.readLine()) != null) 
			{
             	   String[] tokens = line.split(",");
             	   
             	   /*	@tokens[0] = X of the taxi
             	    * 	@tokens[1] = Y of the taxi
             	    *	@tokens[2] = id of the taxi */

                	 if (tokens.length > 0) 
                 	{
                 		taxisClass taxi = new taxisClass(
                 				Double.parseDouble(tokens[0]), 
                 				Double.parseDouble(tokens[1]), 
                 				Integer.parseInt(tokens[2]),
                 				tokens[3],
                 				tokens[4],
                 				tokens[5],
                 				Double.parseDouble(tokens[6]),
                 				tokens[7],
                 				tokens[8]);
                 		taxis.add(taxi);
                 	}
       		}
		}

       	catch (Exception e) 
       	{
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
			    fileReader.close();
            } 
            catch (IOException e) 
            {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
	}


	/*	nodes is a list in which we store all the points-nodes(nodesClass type) */
	public static List<nodesClass> nodes = new ArrayList<>();

	/*	We first read the first line that contains no information and
	 * 	then for each line we create a new nodesClass that 
	 * 	stores the X,Y coordinates,the id of the road and the name if it exists.
	 * 	Each nodesClass is then added to the nodes list  */
	public static void nodesFile(String fileName)
	{
		BufferedReader fileReader = null;
		try 
		{
				/* 	Some coordinates from the nodes.csv file have the same X,Y and 
     	   	 	* 	the same id road.So we read the second line outside the while loop  
     	   	 	* 	and we compare each lines's X,Y and id with the previous one's in order 
     	   	 	* 	to avoid the problem.
     	   	 	* 	@ver1 = X from the previous step
     	   	 	* 	@ver2 = Y from the previous step
     	   	 	* 	@ver3 = id	from the previous step
     	   	 	*	@tokens[0] = X of the node
				* 	@tokens[1] = Y of the node
             	*	@tokens[2] = id of the road
             	*	@tokens[3] = name of the road if it exists
     	   	 	*/
				String line = "";
				fileReader = new BufferedReader(new FileReader(fileName));
				fileReader.readLine();
				line = fileReader.readLine();	
				String[] tokens1 = line.split(",");
				Double ver1 = Double.parseDouble(tokens1[0]);
				Double ver2 = Double.parseDouble(tokens1[1]);
				int ver3 = Integer.parseInt(tokens1[2]);
				
				/*	if tokens.length >3 then the name of the road also exists */
				if (tokens1.length > 4)
				{
					nodesClass node = new nodesClass(
							Double.parseDouble(tokens1[0]), 
							Double.parseDouble(tokens1[1]), 
							Integer.parseInt(tokens1[2]),
							Integer.parseInt(tokens1[3]),
							tokens1[4]);
					nodes.add(node);
				}
				/*	if  2<tokens.length <=3 then we have the X,Y coordinates
				 * 	and the id of the road */
				else if (tokens1.length > 3)
				{
					nodesClass node = new nodesClass(
							Double.parseDouble(tokens1[0]), 
							Double.parseDouble(tokens1[1]), 
							Integer.parseInt(tokens1[2]), 
							Integer.parseInt(tokens1[3]),
							null);
					nodes.add(node);
				}
			while ((line = fileReader.readLine()) != null) 
			{
             	   	
					String[] tokens = line.split(",");
             	   	Double var1 = Double.parseDouble(tokens[0]);
             	   	Double var2 = Double.parseDouble(tokens[1]);
             	   	int var3 = Integer.parseInt(tokens[2]);
             	   	if((Double.compare(var1, ver1) == 0) && (Double.compare(var2, ver2) == 0) && (var3 == ver3))
             	   	{
             	   		ver1 = var1;
             	   		ver2 = var2;
             	   		ver3 = var3;
             	   	}
             	   	else{
             	   		if (tokens.length > 4) 
             	   		{	
             	   			nodesClass node = new nodesClass(
             	   					Double.parseDouble(tokens[0]), 
             	   					Double.parseDouble(tokens[1]), 
             	   					Integer.parseInt(tokens[2]), 
             	   					Long.parseLong(tokens[3]),
             	   					tokens[4]);
             	   			nodes.add(node);
             	   		}
             	   		else if (tokens.length >3)
             	   		{
             	   			nodesClass node = new nodesClass(
             	   					Double.parseDouble(tokens[0]), 
             	   					Double.parseDouble(tokens[1]), 
             	   					Integer.parseInt(tokens[2]), 
             	   					Long.parseLong(tokens[3]),
             	   					null);
             	   			nodes.add(node);
             	   		}
             	   		ver1 = var1;
             	   		ver2 = var2;
             	   		ver3 = var3;
             	   	}
       		}
		}

       	catch (Exception e) 
       	{
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
			    fileReader.close();
            } 
            catch (IOException e) 
            {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
	}
	
	/* linesClass = class in which we store all the atrributes of each line */
	public static List<linesClass> lines = new ArrayList<>();

	public static void linesFile(String fileName)
	{
		BufferedReader fileReader = null;
		try 
		{
			String line = "";
		
			fileReader = new BufferedReader(new FileReader(fileName));
		
			fileReader.readLine();

			while ((line = fileReader.readLine()) != null) 
			{
				   String[] tokens = line.split(",");
             	   //System.out.println(tokens.length + " ");
                	if (tokens.length > 0) 
                 	{
                 		linesClass line1 = new linesClass(
                 				Integer.parseInt(tokens[0]), 
                 				tokens.length > 1 ?(tokens[1].isEmpty() ? null : tokens[1]) : null, 
                 				tokens.length > 2 ? (tokens[2].isEmpty() ? null : tokens[2]) : null,
                 				tokens.length > 3 ? (tokens[3].isEmpty() ? null : tokens[3]) : null,
                 				tokens.length > 4 ? (tokens[4].isEmpty() ? null : tokens[4]) : null,
                 				tokens.length > 5 ? (tokens[5].isEmpty() ? -1 : Integer.parseInt(tokens[5])) : -1,
                 				tokens.length > 6 ? (tokens[6].isEmpty() ? -1 : Integer.parseInt(tokens[6])) : -1,
                 				tokens.length > 7 ? (tokens[7].isEmpty() ? null : tokens[7]) : null,
                 				tokens.length > 8 ? ((tokens[8].isEmpty() ? null : tokens[8])) : null,
                 				tokens.length > 9 ? (tokens[9].isEmpty() ? null : tokens[9]) : null,
                 				tokens.length > 10 ?(tokens[10].isEmpty() ? null : tokens[10]) : null,
                 				tokens.length > 11 ?(tokens[11].isEmpty() ? null : tokens[11]) : null,
                 				tokens.length > 12 ? (tokens[12].isEmpty() ? null : tokens[12]) : null,
                 				tokens.length > 13 ? (tokens[13].isEmpty() ? null : tokens[13]) : null,
                // 				tokens.length > 14 ? (tokens[14].isEmpty() ? null : tokens[14]) : null,
                 				tokens.length > 15 ? (tokens[15].isEmpty() ? null : tokens[15]) : null,
                 				tokens.length > 16 ? (tokens[16].isEmpty() ? null : tokens[16]) : null,
                 				tokens.length > 17 ? (tokens[17].isEmpty() ? null : tokens[17]) : null);
                 		lines.add(line1);
                 	}
       		}
		}

       	catch (Exception e) 
       	{
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
			    fileReader.close();
            } 
            catch (IOException e) 
            {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
	}
	
	/*trafficClass = class in which we store all the traffic attributes of the given line id */
	public static List<trafficClass> traffic = new ArrayList<>();

	public static void trafficFile(String fileName)
	{
		BufferedReader fileReader = null;
		try 
		{
			String line = "";
		
			fileReader = new BufferedReader(new FileReader(fileName));
		
			fileReader.readLine();

			while ((line = fileReader.readLine()) != null) 
			{
             	   String[] tokens = line.split(",");

                	if (tokens.length > 0) 
                 	{
                 		trafficClass traffic1 = new trafficClass(
                 				Integer.parseInt(tokens[0]), 
                 				tokens.length > 1 ? tokens[1] : null, 
                 				tokens.length > 2 ? tokens[2] : null);
                 		traffic.add(traffic1);
                 	}
       		}
		}

       	catch (Exception e) 
       	{
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
			    fileReader.close();
            } 
            catch (IOException e) 
            {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
	}
	
}