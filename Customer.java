package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Customer {
	
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
			
			
			
			//2. Create the method for get all the details of the customers
			public String getCustomerDetails() {
				
				   String output = ""; 
				   
				   try { 
				          Connection con = connect(); 
				          
				          if (con == null)  { 
				             return "Error while connecting to the database for reading."; 
				          } 
				 
				          // Prepare the html table to be displayed
				          output = "<table border='1'><tr><th>Account Number</th>" 
				                   +"<th>First Name</th><th>Last Name</th>"
				                   +"<th>Address</th><th>Email</th><th>Contact Number</th>"
				                   + "<th>Update</th><th>Remove</th></tr>";              
				          
				          String query = "select * from customer"; 
				          Statement stmt = con.createStatement(); 
				          ResultSet rs = stmt.executeQuery(query); 
				
				          // iterate through the rows in the result set
				          while (rs.next()) { 
				        	  
				        	     String accountNumber = Integer.toString(rs.getInt("accountNumber")); 
					             String firstName = rs.getString("firstName");  
					             String lastName = rs.getString("lastName"); 
					             String address = rs.getString("address"); 
					             String emailId = rs.getString("emailId"); 
					             String contactNumber = rs.getString("contactNumber"); 
					            
				 
					             // Add a row into the html table
					             output += "<tr><td>" + accountNumber + "</td>"; 
					             output += "<td>" + firstName + "</td>"; 
					             output += "<td>" + lastName + "</td>";
					             output += "<td>" + address + "</td>";
					             output += "<td>" + emailId  + "</td>";
					             output += "<td>" + contactNumber + "</td>";
				 
					             //...... buttons - wrapped the update &remove button inside a form. 
						            output += "<td><input name='btnUpdate' " 
						                   + " type='button' value='Update' class='btn btn-secondary'></td>"
						                   + "<td><form method='post' action='customer.jsp'>"
						                   + "<input name='btnRemove' " 
						                   + " type='submit' value='Remove' class='btn btn-danger'>"
						                   + "<input name='itemID' type='hidden' " 
						                   + " value='" + accountNumber + "'>" + "</form></td></tr>"; 
						         //.......................................................................
				         } 
				 
				          con.close(); 
				         
				          // Complete the html table
				          output += "</table>"; 
				 
				    } 
				    catch (Exception e)  { 
				             output = "Error while reading the data."; 
				             System.err.println(e.getMessage()); 
				    } 
				       
				          return output; 
				}
			    
				
				
			//3. Create the method to add customers' details
			public String addCustomerDetials(String accNo,String fName,String lName, String address, String email, String contactNo) {
					 
			       String output = "";
					
				   try{
						  
					   Connection con = connect();
				
					   if (con == null) {
							   return "Error while connecting to the database";
					   }
					
					   // create a prepared statement
					   String query = "insert into customer (accountNumber,firstName,lastName,address,emailId,contactNumber)" + " values (?, ?, ?, ?, ?,?)";
				
					   PreparedStatement preparedStmt = con.prepareStatement(query);
				
					   // binding values
					   preparedStmt.setInt(1, Integer.parseInt(accNo));
					   preparedStmt.setString(2, fName);
					   preparedStmt.setString(3, lName);
					   preparedStmt.setString(4, address);
					   preparedStmt.setString(5, email);
					   preparedStmt.setString(6, contactNo);
						
					   //execute the statement
					   preparedStmt.execute();
					   
					   con.close();
				
					   output = "Inserted successfully";
					
				   }
				   catch (Exception e){
						
					    output = "Error while inserting data";
						System.err.println(e.getMessage());
				   }
				
					return output; 

			}
			
		
			//4. Create a method for update customer details
			public String updateCustomerDetails(String accNo,String fName,String lName, String address, String email, String contactNo) {
				  String output = ""; 
				
				  try
				  { 
				      Connection con = connect(); 
				     
				      if (con == null) 
				      {
				    	  return "Error while connecting to the database for updating."; 
				      } 
				
				      // create a prepared statement
				      String query = "UPDATE customer SET firstName=?,lastName=?,address=?,emailId=?,contactNumber=? WHERE accountNumber=?"; 
				
				      PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				      // binding values
				       
				       //preparedStmt.setInt(1, Integer.parseInt(accNo));
					   preparedStmt.setString(1, fName);
					   preparedStmt.setString(2, lName);
					   preparedStmt.setString(3, address);
					   preparedStmt.setString(4, email);
					   preparedStmt.setString(5, contactNo);
					   preparedStmt.setInt(6, Integer.parseInt(accNo));
				       
				
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
			
			
			//5. Create a method for deleting customers/customer details
			public String deleteCustomer(String  accountNumber)
			{ 
			        String output = ""; 
			        
			        try{ 
			        
			        	    Connection con = connect(); 
			               
			        	    if (con == null) { 
			                    return "Error while connecting to the database for deleting."; 
			                } 
			        	    
			        	      // create a prepared statement
			        	      String query = "delete from customer where accountNumber=?"; 
			        	      
			        	      PreparedStatement preparedStmt = con.prepareStatement(query); 
			        	    
			        	      // binding values
			        	      preparedStmt.setInt(1, Integer.parseInt(accountNumber)); 
			        	     
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
