package com;


import model.Bill;


//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;


@Path("/Bill")
public class BillService {

		Bill billObj = new Bill();
		
		
		@GET
		@Path("/") 
		@Produces(MediaType.TEXT_HTML) 
		public String getBillDetails()
		{ 
		    return billObj.getBillDetails(); 
		} 
		
		
		@POST
		@Path("/") 
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String addBillDetails(@FormParam("billId") String billId, @FormParam("year") String year, @FormParam("month") String month, @FormParam("totalUnits") String totalUnits,@FormParam("powId") String powId,@FormParam("dueAmount") String dueAmount,@FormParam("billAmount") String billAmount, @FormParam("accountNumber") String accountNumber){
			String output = billObj.addBillDetails(billId,year,month,totalUnits,powId,dueAmount,billAmount,accountNumber);
			return output;

		}
		
		
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		public String updateBillDetails(String billData)
		{
			//Convert the input string to a JSON object 
			 JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject();

			//Read the values from the JSON object
			 String billId = billObject.get("billId").getAsString(); 
			 String year =billObject.get("year").getAsString(); 
			 String month = billObject.get("month").getAsString(); 
			 String totalUnits = billObject.get("totalUnits").getAsString(); 
			 String powId = billObject.get("powId").getAsString(); 
			 String dueAmount = billObject.get("dueAmount").getAsString(); 
			 String billAmount = billObject.get("billAmount").getAsString(); 
			 String accountNumber = billObject.get("accountNumber").getAsString(); 
			 
			 
			 
			 String output = billObj.updateBillDetails(billId,year,month,totalUnits,powId,dueAmount,billAmount,accountNumber);
			 return output;
		}
		
		
		
		@DELETE
		@Path("/") 
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN) 
		public String deleteBill(String billData) 
		{ 
			//Convert the input string to an XML document
			 Document doc = Jsoup.parse(billData, "", Parser.xmlParser()); 
			 
			//Read the value from the element <powId>
			 String billId = doc.select("billId").text();
		
			 String output = billObj.deleteBill(billId);
			 
			 return output;
		}
	
	
	
}
