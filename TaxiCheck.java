import java.io.IOException;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

public class TaxiCheck {
	
	static JIPEngine jip = new JIPEngine();
	
	/*
	 * Consult File we created with prolog arguments.
	 */
	
	public static void compileProlog() throws JIPSyntaxErrorException, IOException
	{
		jip.consultFile("prologFile1.pl");
	}

	/*
	 * Check if a taxi is capable for picking up the client
	 * Return values:
	 * 5 -> capable
	 * !5 -> incapable
	 */
	
	public static int taxiCheck(taxisClass taxi, double dist) throws JIPSyntaxErrorException, IOException
	{
		JIPTermParser parser = jip.getTermParser();	
		JIPQuery jipQuery;
		JIPQuery jipQuery1;
		JIPTerm term;
		JIPTerm term1;
		
		String y = "yes";
		int counter = 0;
		int temp = 0;
		/*
		 * First check, ask Prolog if Taxi with id X is capable, increase our counter if it is.
		 */
		jipQuery = jip.openSynchronousQuery(parser.parseTerm("available(" + taxi.getIDtaxi() + "," + y + ")."));
		if (jipQuery.nextSolution() != null) {
				counter++;
		}
		
		/*
		 * Second check, ask Prolog what languages does the client and the taxi driver know.
		 * Compare them. If there is at least one common language, increase our counter
		 */
		jipQuery = jip.openSynchronousQuery(parser.parseTerm("languageClient(client,Y)."));
		term = jipQuery.nextSolution();
		while(term != null)
		{
			jipQuery1 = jip.openSynchronousQuery(parser.parseTerm("languageTaxi(" + taxi.getIDtaxi() + ",Z)."));
			term1 = jipQuery1.nextSolution();
			while(term1 != null)
			{
				if (term.getVariablesTable().get("Y").toString().equals(term1.getVariablesTable().get("Z").toString()))
				{
					temp = 1;
				}
				term1 = jipQuery1.nextSolution();
			}
			term = jipQuery.nextSolution();
		}
		counter += temp;
		
		/*
		 * Third check. Ask Prolog for the capacity of the cab, and the number of persons who want to get in.
		 * Increase our counter if capacity >= number of persons.
		 */
		jipQuery = jip.openSynchronousQuery(parser.parseTerm("capacity(" + taxi.getIDtaxi() + ",Y)."));
		term = jipQuery.nextSolution();
		jipQuery1 = jip.openSynchronousQuery(parser.parseTerm("clientPersons(client,Z)."));
		term1 = jipQuery1.nextSolution();
		
		if((Integer.valueOf(term.getVariablesTable().get("Y").toString()) >= Integer.valueOf(term1.getVariablesTable().get("Z").toString())))
		{
			counter++;
		}
		
		/*
		 * Fourth check. Check taxi type and clients luggage.
		 * We make the assumption that large or minivan cars can take up to 4 luggages
		 * and compact or subcompact cars can take up to 2
		 * If it does, increase our counter
		 */
		jipQuery = jip.openSynchronousQuery(parser.parseTerm("taxiType(" + taxi.getIDtaxi() + ",Y)."));
		term = jipQuery.nextSolution();
		jipQuery1 = jip.openSynchronousQuery(parser.parseTerm("clientLuggage(client,Z)."));
		term1 = jipQuery1.nextSolution();
		
		if((term.getVariablesTable().get("Y").toString().equals("large") || term.getVariablesTable().get("Y").toString().equals("minivan")) && (Integer.valueOf(term1.getVariablesTable().get("Z").toString()) < 5))
		{
			counter++;
		}
		else if((term.getVariablesTable().get("Y").toString().equals("compact") || term.getVariablesTable().get("Y").toString().equals("subcompact")) && (Integer.valueOf(term1.getVariablesTable().get("Z").toString()) < 3) )
		{
			counter++;
		}
		
		
		/*
		 * Last check. Check the total distance that A* calculated, and Distance type of the taxi
		 * Increase counter if distance is over 30km and taxi type is suitable for long rides,
		 * or if distance is less than 30km.
		 */
		
		jipQuery = jip.openSynchronousQuery(parser.parseTerm("longDistance(" + taxi.getIDtaxi() + ",Y)."));
		term = jipQuery.nextSolution();
		
		if((term.getVariablesTable().get("Y").toString().equals("yes")) || (dist < 30.0))
		{
			counter++;
		}
		
		/*
		 * Return our counter to the main class
		 */
		return counter;
	}
}