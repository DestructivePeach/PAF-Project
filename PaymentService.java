package com;

import model.Payment;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



@Path("/Payment")
public class PaymentService {

	Payment paymentObj = new Payment();
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String getPaymentDetails()
	{ 
	    return paymentObj.getPaymentDetails(); 
	} 
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addPaymentDetails(@FormParam("paymentId") String paymentId, @FormParam("billId") String billId, @FormParam("cardHolderName") String cardHolderName, @FormParam("cardType") String cardType,@FormParam("bank") String bank,@FormParam("payAmount") String payAmount,@FormParam("status") String status){
		String output = paymentObj.addPaymentDetails(paymentId,billId,cardHolderName,cardType,bank,payAmount,status);
		return output;

	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatePaymentDetails(String payData)
	{
		//Convert the input string to a JSON object 
		 JsonObject payObject = new JsonParser().parse(payData).getAsJsonObject();

		//Read the values from the JSON object
		 String paymentId = payObject.get("paymentId").getAsString(); 
		 String billId =payObject.get("billId").getAsString(); 
		 String cardHolderName = payObject.get("cardHolderName").getAsString();
		 String cardType = payObject.get("cardType").getAsString(); 
		 String bank = payObject.get("bank").getAsString(); 
		 String payAmount = payObject.get("payAmount").getAsString();
		 String status = payObject.get("status").getAsString(); 
		
		 
		 
		 String output = paymentObj.updatePaymentDetails(paymentId,billId,cardHolderName,cardType,bank,payAmount,status);
		 return output;
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN) 
	public String deletePayment(String payData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(payData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <powId>
		 String paymentId = doc.select("paymentId").text();
	
		 String output = paymentObj.deletePayment(paymentId);
		 
		 return output;
	}
	
	
}

