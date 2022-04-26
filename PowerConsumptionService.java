package com;

import model.PowerConsumption;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/PowerConsumption") 
public class PowerConsumptionService {
	
		PowerConsumption powerCons = new PowerConsumption(); 
		
		
		@GET
		@Path("/") 
		@Produces(MediaType.TEXT_HTML) 
		public String readPowerConsumption() 
		{ 
		    return powerCons.readPowerConsumption(); 
		} 
		
		@POST
		@Path("/") 
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertPowerConsumption(@FormParam("powId") String powId, @FormParam("unitDescription") String unitDescription, @FormParam("unitPrice") String unitPrice){
			String output = powerCons.insertPowerConsumption(powId,unitDescription,unitPrice);
			return output;

		}
		
		
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		public String updatePowerConsumption(String pcData)
		{
			//Convert the input string to a JSON object 
			 JsonObject pcObject = new JsonParser().parse(pcData).getAsJsonObject();
			 
			//Read the values from the JSON object
			 String powId = pcObject.get("powId").getAsString(); 
			 String unitDescription = pcObject.get("unitDescription").getAsString(); 
			 String unitPrice = pcObject.get("unitPrice").getAsString(); 
			 
			 
			 String output = powerCons.updatePowerConsumption(powId,unitDescription,unitPrice); 
			 return output;
		}
		
		
		
		
		@DELETE
		@Path("/") 
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN) 
		public String deletePowerConsumption(String pcData) 
		{ 
			//Convert the input string to an XML document
			 Document doc = Jsoup.parse(pcData, "", Parser.xmlParser()); 
			 
			//Read the value from the element <powId>
			 String powId = doc.select("powId").text();
		
			 String output = powerCons.deletePowerConsumption(powId);
			 
			 return output;
		}


}
