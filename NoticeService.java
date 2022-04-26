package com;

import model.Notice;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;


@Path("/Notice")
public class NoticeService {

	Notice noticeObj = new Notice();
	
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readNotice()
	{ 
	    return noticeObj.readNotice(); 
	} 
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNoticeDetails(@FormParam("noticeId") String noticeId, @FormParam("title") String title, @FormParam("message") String message, @FormParam("accountNumber") String accountNumber){
		String output = noticeObj.addNoticeDetails(noticeId,title,message,accountNumber);
		return output;

	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateNoticeDetails(String noticeData)
	{
		//Convert the input string to a JSON object 
		 JsonObject noticeObject = new JsonParser().parse(noticeData).getAsJsonObject();

		//Read the values from the JSON object
		 String noticeId = noticeObject.get("noticeId").getAsString(); 
		 String title =noticeObject.get("title").getAsString(); 
		 String message = noticeObject.get("message").getAsString(); 
		 String accountNumber = noticeObject.get("accountNumber").getAsString(); 
		 
		 String output = noticeObj.updateNoticeDetails(noticeId,title,message,accountNumber);
		 return output;
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteNotice(String noticeData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(noticeData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <powId>
		 String noticeId = doc.select("noticeId").text();
	
		 String output = noticeObj.deleteNotice(noticeId);
		 
		 return output;
	}
}
