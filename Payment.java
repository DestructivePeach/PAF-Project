package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

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
		
		
		
		//2. Create the method for get all the payment details of the customers
		public String getPaymentDetails() {
			
			   String output = ""; 
			   
			   try { 
			          Connection con = connect(); 
			          
			          if (con == null)  { 
			             return "Error while connecting to the database for reading."; 
			          } 
			 
			          // Prepare the html table to be displayed
			          output = "<table border='1'><tr><th>Payment ID</th>" 
			                   +"<th>Bill ID</th><th>Card Holder Name</th>"
			                   +"<th>Card Type</th><th>Bank</th><th>Total Amount</th>"
			                   +"<th>Status</th>"
			                   + "<th>Update</th><th>Remove</th></tr>";              
			          
			          String query = "select * from payment"; 
			          Statement stmt = con.createStatement(); 
			          ResultSet rs = stmt.executeQuery(query); 
			
			          // iterate through the rows in the result set
			          while (rs.next()) { 
			        	  
			        	     String paymentId  = Integer.toString(rs.getInt("paymentId")); 
				             String billId = Integer.toString(rs.getInt("billId")); 
				             String cardHolderName = rs.getString("cardHolderName"); 
				             String cardType = rs.getString("cardType"); 
				             String bank = rs.getString("bank"); 
				             String payAmount = Double.toString(rs.getDouble("payAmount")); 
				             String status =rs.getString("status"); 
				              
				             
			 
				             // Add a row into the html table
				             output += "<tr><td>" + paymentId + "</td>"; 
				             output += "<td>" + billId + "</td>"; 
				             output += "<td>" + cardHolderName + "</td>"; 
				             output += "<td>" + cardType + "</td>";
				             output += "<td>" + bank + "</td>";
				             output += "<td>" + payAmount + "</td>";
				             output += "<td>" + status + "</td>";
				             
			 
				             //...... buttons - wrapped the update &remove button inside a form. 
					            output += "<td><input name='btnUpdate' " 
					                   + " type='button' value='Update' class='btn btn-secondary'></td>"
					                   + "<td><form method='post' action='payment.jsp'>"
					                   + "<input name='btnRemove' " 
					                   + " type='submit' value='Remove' class='btn btn-danger'>"
					                   + "<input name='itemID' type='hidden' " 
					                   + " value='" + paymentId + "'>" + "</form></td></tr>"; 
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

		
		
		//3. Create the method to add payment details
		public String addPaymentDetails(String payId, String billId,String name,String cardType, String bank, String amount, String status) {
				 
			 
		       String output = "";
				
			   try{
					  
				   Connection con = connect();
			
				   if (con == null) {
						   return "Error while connecting to the database";
				   }
				
				   else {
				
					  
					  
				   // create a prepared statement
				   String query = "insert into payment(paymentId,billId,cardHolderName,cardType,bank,payAmount,status)" + " values (?, ?, ?, ?, ?,?,?)";
			
	              
				   PreparedStatement preparedStmt = con.prepareStatement(query);
			
				   // binding values
				   preparedStmt.setInt(1,0);
				   preparedStmt.setInt(2, Integer.parseInt(billId));
				   preparedStmt.setString(3, name);
				   preparedStmt.setString(4, cardType);
				   preparedStmt.setString(5, bank);
				   preparedStmt.setDouble(6, Double.parseDouble(amount));
				   preparedStmt.setString(7, status);
				   
				   
				   //execute the statement
				   preparedStmt.execute();
				   
				   con.close();
			
				   output = "Inserted successfully";
				   }
			   }
			   catch (Exception e){
					
				    output = "Error while inserting data.";
					System.err.println(e.getMessage());
			   }
			
				return output; 
	       
		}
		
		
		
		//4. Create a method for update payment details
		public String updatePaymentDetails(String payId, String billId,String name,String cardType, String bank, String amount, String status) {
			  String output = ""; 
			
			  try
			  { 
			      Connection con = connect(); 
			     
			      if (con == null) 
			      {
			    	  return "Error while connecting to the database for updating."; 
			      } 
			
			      
			      // create a prepared statement
			      String query = "UPDATE payment SET billId=?, cardHolderName=?,cardType=?,bank=?,payAmount=?,status=?  WHERE paymentId=?"; 
			
			      PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			      
			      // binding values
				  
				   preparedStmt.setInt(1, Integer.parseInt(billId));
				   preparedStmt.setString(2, name);
				   preparedStmt.setString(3, cardType);
				   preparedStmt.setString(4, bank);
				   preparedStmt.setDouble(5,Double.parseDouble(amount));
				   preparedStmt.setString(6, status);
	  
				   preparedStmt.setInt(7, Integer.parseInt(payId));
			       
			
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
		
		
		
		//5. Create a method for deleting payment details
		public String deletePayment(String  paymentId)
		{ 
		        String output = ""; 
		        
		        try{ 
		        
		        	    Connection con = connect(); 
		               
		        	    if (con == null) { 
		                    return "Error while connecting to the database for deleting."; 
		                } 
		        	    
		        	      // create a prepared statement
		        	      String query = "delete from payment where paymentId=?"; 
		        	      
		        	      PreparedStatement preparedStmt = con.prepareStatement(query); 
		        	    
		        	      // binding values
		        	      preparedStmt.setInt(1, Integer.parseInt(paymentId)); 
		        	     
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
