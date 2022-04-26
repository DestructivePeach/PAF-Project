package com;

import model.Customer;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/Customer")
public class CustomerService {

	Customer customerObj = new Customer();
	
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	{ 
	    return customerObj.getCustomerDetails(); 
	} 
	
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addCustomerDetials(@FormParam("accountNumber") String accountNumber, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("address") String address,@FormParam("emailId") String emailId,@FormParam("contactNumber") String contactNumber){
		String output = customerObj.addCustomerDetials(accountNumber,firstName,lastName,address,emailId,contactNumber);
		return output;

	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateCustomerDetails(String customerData)
	{
		//Convert the input string to a JSON object 
		 JsonObject customerObject = new JsonParser().parse(customerData).getAsJsonObject();

		//Read the values from the JSON object
		 String accountNumber = customerObject.get("accountNumber").getAsString(); 
		 String firstName =customerObject.get("firstName").getAsString(); 
		 String lastName = customerObject.get("lastName").getAsString(); 
		 String address = customerObject.get("address").getAsString(); 
		 String emailId = customerObject.get("emailId").getAsString(); 
		 String contactNumber = customerObject.get("contactNumber").getAsString(); 
		 
		 
		 
		 String output = customerObj.updateCustomerDetails(accountNumber,firstName,lastName,address,emailId,contactNumber);
		 return output;
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteCustomer(String customerData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(customerData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <powId>
		 String accountNumber = doc.select("accountNumber").text();
	
		 String output = customerObj.deleteCustomer(accountNumber);
		 
		 return output;
	}
}
