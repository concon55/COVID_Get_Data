package covidData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class USData {

	public static String getData() throws Exception{
		JSONParser parser = new JSONParser();
		try {        
            URL oracle = new URL("https://covidtracking.com/api/v1/us/current.json"); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
           
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {              
                JSONArray a = (JSONArray) parser.parse(inputLine);
               
                // Loop through each item
                for (Object o : a) {
                	String message="US Data\n";
                   JSONObject stateLine = (JSONObject) o;
                   long dateLong = (long)stateLine.get("date");
                   String date = dateLong+"";
                   
                   //State and Times
                   SimpleDateFormat fromUser = new SimpleDateFormat("yyyyMMdd");
                   SimpleDateFormat myFormat = new SimpleDateFormat("M/d/yyyy");

                   String reformattedStr = myFormat.format(fromUser.parse(date));
                   message+="Date: "+reformattedStr + "\n";
                   
                   //Positive Increase
                   if(stateLine.get("positiveIncrease")!=null){
                	   long posIncr = (long)stateLine.get("positiveIncrease");
                	   message+="Daily increase in positive cases: "+NumberFormat.getIntegerInstance().format(posIncr) + "\n";
                   }
                   
                   //Total Positive
                   if(stateLine.get("positive")!=null){
                	   long positive = (long)stateLine.get("positive");
                	   message+="Total cases: "+NumberFormat.getIntegerInstance().format(positive) + "\n";
                   }
                   
                   //Death Increase
                   if(stateLine.get("deathIncrease")!=null){
                	   long deathIncrease = (long)stateLine.get("deathIncrease");
                	   message+="Daily increase in deaths: "+NumberFormat.getIntegerInstance().format(deathIncrease)+ "\n";
                   }
                   
                   //Total deaths
                   if(stateLine.get("death")!=null){
                	   long deaths = (long)stateLine.get("death");
                	   message+="Total confirmed deaths: "+NumberFormat.getIntegerInstance().format(deaths)+ "\n";
                   }
                   
                   //Total recovered
                   if(stateLine.get("recovered")!=null){
                	   long recovered = (long)stateLine.get("recovered");
                	   message+="Total recovered: "+NumberFormat.getIntegerInstance().format(recovered)+ "\n";
                   }
                   
                   return message;
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return "Reply with a state abbreviation or \"US\"";  
		
	}

}
