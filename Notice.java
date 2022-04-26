package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notice {

	//1. Create a Connection method 
		public Connection connect() 
		{ 
		     Connection con = null; 
		 
		     try { 
		             Class.forName("com.mysql.jdbc.Driver"); 
		            
		              //Provide the correct details: DBServer/DBName, username, password 
		             con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powergridsystem","root", ""); 
		               
		             /*//For testing
		             System.out.print("Successfully connected"); */
		            	   
		     } 
		     catch(Exception e) { 
		             e.printStackTrace(); 
		     } 
		 
		     return con; 
		}
		
		
		
		//2. Create the method for read all notices of the customers
		public String readNotice() {
			
			   String output = ""; 
			   
			   try { 
			          Connection con = connect(); 
			          
			          if (con == null)  { 
			             return "Error while connecting to the database for reading."; 
			          } 
			 
			          // Prepare the html table to be displayed
			          output = "<table border='1'><tr><th>Notice ID</th>" 
			                   +"<th>Title of the Notice</th><th>Subject</th>"
			                   +"<th>Account Number</th>"
			                   + "<th>Update</th><th>Remove</th></tr>";              
			          
			          String query = "select * from notice"; 
			          Statement stmt = con.createStatement(); 
			          ResultSet rs = stmt.executeQuery(query); 
			
			          // iterate through the rows in the result set
			          while (rs.next()) { 
			        	  
			        	     String noticeId   = Integer.toString(rs.getInt("noticeId")); 
				             String title = rs.getString("title");  
				             String subject = rs.getString("message");  
				             String accountNumber = Integer.toString(rs.getInt("accountNumber")); 
				             
			 
				             // Add a row into the html table
				             output += "<tr><td>" + noticeId  + "</td>"; 
				             output += "<td>" +  title + "</td>"; 
				             output += "<td>" + subject + "</td>";
				             output += "<td>" + accountNumber + "</td>";
			 
				             //...... buttons - wrapped the update &remove button inside a form. 
					            output += "<td><input name='btnUpdate' " 
					                   + " type='button' value='Update' class='btn btn-secondary'></td>"
					                   + "<td><form method='post' action='notice.jsp'>"
					                   + "<input name='btnRemove' " 
					                   + " type='submit' value='Remove' class='btn btn-danger'>"
					                   + "<input name='itemID' type='hidden' " 
					                   + " value='" + noticeId + "'>" + "</form></td></tr>"; 
					            //.......................................................................
			         } 
			 
			          con.close(); 
			         
			          // Complete the html table
			          output += "</table>"; 
			 
			    } 
			    catch (Exception e)  { 
			             output = "Error while reading data."; 
			             System.err.println(e.getMessage()); 
			    } 
			       
			          return output; 
			}
		
		
		
		//3. Create the method to add notice details
		public String addNoticeDetails(String noticeId,String title,String message, String custNumber) {
				 
			 
		       String output = "";
				
			   try{
					  
				   Connection con = connect();
			
				   if (con == null) {
						   return "Error while connecting to the database";
				   }
				
				   else {
				
					  
					  
				   // create a prepared statement
				   String query = "insert into notice (noticeId,title,message,accountNumber)" + " values (?, ?, ?, ?)";
			
	              
				   PreparedStatement preparedStmt = con.prepareStatement(query);
			
				   // binding values
				   preparedStmt.setInt(1,0);
				   preparedStmt.setString(2, title);
				   preparedStmt.setString(3, message);
				   preparedStmt.setInt(4,Integer.parseInt(custNumber));
				
				 
					
				   //execute the statement
				   preparedStmt.execute();
				   
				   con.close();
			
				   output = "Inserted successfully";
				   }
			   }
			   catch (Exception e){
					
				    output = "Error while inserting data";
					System.err.println(e.getMessage());
			   }
			
				return output; 
	       
		}
		
		
		//4. Create a method for update notice details
		public String updateNoticeDetails(String noticeId,String title,String message, String custNumber) {
			  String output = ""; 
			
			  try
			  { 
			      Connection con = connect(); 
			     
			      if (con == null) 
			      {
			    	  return "Error while connecting to the database for updating."; 
			      } 
			
			      
			      // create a prepared statement
			      String query = "UPDATE notice SET title=?, message=?,accountNumber=? WHERE noticeId=?"; 
			
			      PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			      
			       // binding values
				  
				   //preparedStmt.setString(1, noticeId);
				   preparedStmt.setString(1, title);
				   preparedStmt.setString(2, message);
				   preparedStmt.setInt(3,Integer.parseInt(custNumber));
				   preparedStmt.setInt(4,Integer.parseInt(noticeId));
				  
			
			        // execute the statement
			        preparedStmt.execute(); 
			        con.close(); 
			        output = "Updated successfully"; 		        
			      
			 } 
			  
			 catch (Exception e) 
			 { 
			        output = "Error while updating data."; 
			        System.err.println(e.getMessage()); 
			 } 
			 
			  return output;
		}
		
		
		
		    
		//6. Create a method for deleting notice details
		public String deleteNotice(String  noticeId)
		{ 
		        String output = ""; 
		        
		        try{ 
		        
		        	    Connection con = connect(); 
		               
		        	    if (con == null) { 
		                    return "Error while connecting to the database for deleting."; 
		                } 
		        	    
		        	      // create a prepared statement
		        	      String query = "delete from notice where noticeId=?"; 
		        	      
		        	      PreparedStatement preparedStmt = con.prepareStatement(query); 
		        	    
		        	      // binding values
		        	      preparedStmt.setInt(1, Integer.parseInt(noticeId)); 
		        	     
		        	      // execute the statement
		        	      preparedStmt.execute(); 
		        	    
		        	      con.close(); 
		        	    
		        	      output = "Deleted successfully";
		        	    
		        } 
		        catch (Exception e) { 
		              
		        	     output = "Error while deleting data."; 
		                 System.err.println(e.getMessage()); 
		        }
		        
		        return output; 
		}	
		   
		
	
	
	
}
