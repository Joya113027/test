package mav;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class App {
	
	static int i = 1;
	static int k = 0;
	static String[] header;
	
	
	static String[] permissFormats = new String[]{"MM/dd/yyyy","yyyy-MM-dd","dd/MM/yyyy",
            "dd-MM-yyyy","MM-dd-yyyy","dd-MM-yyyy"};
	
	 //Date Check
	 public static boolean isDateValid(String dateValue)
	    {
	        boolean returnVal = false;
	        for (k = 0; k < permissFormats.length; k++) 
	        {
	            try
	            {
	                SimpleDateFormat sdfObj = new SimpleDateFormat(permissFormats[k]);
	                sdfObj.setLenient(false);
	                sdfObj.parse(dateValue);
	                returnVal = true;
	                break;
	            }
	            catch(ParseException e)
	            {
	            	break;
	            }
	        }
	        return returnVal;
	    }
	
	//Num Check
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	//Format Date
	public static LocalDate Date(String inDate, String formatDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        LocalDate localDate = LocalDate.parse(inDate, formatter);
        return localDate;
    }
	
	
	
	
	public static void main(String[] args) {
		
    	
    	Path path = Paths.get("C:/Users/User/Desktop/data.csv");
    	File pathOut = new File("C:/Users/User/Desktop/data.json");
    	
    	//PrintWriter pw = new PrintWriter(pathOut);
    	
    	try (Stream<String> lines = Files.lines(path))
    		{
    			PrintWriter pw = new PrintWriter(pathOut);
    			lines.forEach(line -> 
    		{
    			if(i==1) 
    			{
    				header = line.split(",");
    				//System.out.println("[");
    				pw.write("[\n");
    				i=2;
    			} else 
    				{
    					String[] data = line.split(",");
    					if(i==2) i=3;
    					else
    						//System.out.println(",");
    						pw.append(",\n");
    					//System.out.println("{");
    					pw.append("{\n");
    					for (int j =0; j<data.length; j++)
    					{
    						if (j==data.length-1)
    						{
    							if(isNumeric(data[j]))
    								//System.out.println("\"" + header[j] + "\":" + " " + data[j]);
    								pw.append("\"" + header[j] + "\":" + " " + data[j] + "\n");
    							else if(isDateValid(data[j]))
    								//System.out.println("\"" + header[j] + "\":" + " \"" + Date(data[j],permissFormats[k]) + "\"");
    								pw.append("\"" + header[j] + "\":" + " \"" + Date(data[j],permissFormats[k]) + "\"" + "\n");
    							else
    								//System.out.println("\"" + header[j] + "\":" + " \"" + data[j] + "\"");
    								pw.append("\"" + header[j] + "\":" + " \"" + data[j] + "\"" + "\n");
    						}
    					
    						else {
    							if(isNumeric(data[j]))
    								//System.out.println("\"" + header[j] + "\":" + " " + data[j] + ",");
    								pw.append("\"" + header[j] + "\":" + " " + data[j] + "\n");
    							else if(isDateValid(data[j]))
    								//System.out.println("\"" + header[j] + "\":" + " \"" + Date(data[j],permissFormats[k]) + "\",");
    								pw.append("\"" + header[j] + "\":" + " \"" + Date(data[j],permissFormats[k]) + "\"" + "\n");
    							else
    								//System.out.println("\"" + header[j] + "\":" + " \"" + data[j] + "\",");
    								pw.append("\"" + header[j] + "\":" + " \"" + data[j] + "\"" + "\n");
    							  }
    					}
    					//System.out.print("}");
    					pw.append("}");
    					
    				} 
    		});
    	//System.out.println("\n]");
    	pw.append("\n]");
    	pw.close();
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
}