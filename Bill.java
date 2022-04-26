package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bill {

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
	
	
	//2. Create the method for get all the bill details of the customers
	public String getBillDetails() {
		
		   String output = ""; 
		   
		   try { 
		          Connection con = connect(); 
		          
		          if (con == null)  { 
		             return "Error while connecting to the database for reading."; 
		          } 
		 
		          // Prepare the html table to be displayed
		          output = "<table border='1'><tr><th>Bill ID</th>" 
		                   +"<th>Year</th><th>Month</th>"
		                   +"<th>Total Units</th><th>Power Consumption ID</th><th>Due Amount</th>"
		                   +"<th>Bill Amount</th><th>Customer Account Number</th>"
		                   + "<th>Update</th><th>Remove</th></tr>";              
		          
		          String query = "select * from monthly_bill"; 
		          Statement stmt = con.createStatement(); 
		          ResultSet rs = stmt.executeQuery(query); 
		
		          // iterate through the rows in the result set
		          while (rs.next()) { 
		        	  
		        	     String billId  = Integer.toString(rs.getInt("billId")); 
			             String year = rs.getString("year");  
			             String month = rs.getString("month"); 
			             String totalUnits = Integer.toString(rs.getInt("totalUnits")); 
			             String powId  = Integer.toString(rs.getInt("powId")); 
			             String dueAmount = Double.toString(rs.getDouble("dueAmount")); 
			             String billAmount =Double.toString(rs.getDouble("billAmount")); 
			             String accountNumber = Integer.toString(rs.getInt("accountNumber")); 
			             
		 
			             // Add a row into the html table
			             output += "<tr><td>" + billId + "</td>"; 
			             output += "<td>" + year + "</td>"; 
			             output += "<td>" + month + "</td>"; 
			             output += "<td>" + totalUnits + "</td>";
			             output += "<td>" + powId + "</td>";
			             output += "<td>" + dueAmount + "</td>";
			             output += "<td>" + billAmount + "</td>";
			             output += "<td>" + accountNumber + "</td>";
		 
			             //...... buttons - wrapped the update &remove button inside a form. 
				            output += "<td><input name='btnUpdate' " 
				                   + " type='button' value='Update' class='btn btn-secondary'></td>"
				                   + "<td><form method='post' action='bill.jsp'>"
				                   + "<input name='btnRemove' " 
				                   + " type='submit' value='Remove' class='btn btn-danger'>"
				                   + "<input name='itemID' type='hidden' " 
				                   + " value='" + billId + "'>" + "</form></td></tr>"; 
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
	
	
	
	    
	//3. Create the method to add bill details
	public String addBillDetails(String billId,String year,String month, String totUnits, String pcID, String due, String billAmount,String cusAccNo) {
			 
		 
	       String output = "";
			
		   try{
				  
			   Connection con = connect();
		
			   if (con == null) {
					   return "Error while connecting to the database";
			   }
			
			   else {
			
				  
				  
			   // create a prepared statement
			   String query = "insert into monthly_bill (billId,year,month,totalUnits,powId,dueAmount,billAmount,accountNumber)" + " values (?, ?, ?, ?, ?,?,?,?)";
		
              
			   PreparedStatement preparedStmt = con.prepareStatement(query);
		
			   // binding values
			   preparedStmt.setInt(1,0);
			   preparedStmt.setString(2, year);
			   preparedStmt.setString(3, month);
			   preparedStmt.setInt(4,Integer.parseInt(totUnits));
			   preparedStmt.setInt(5,Integer.parseInt(pcID) );
			   preparedStmt.setDouble(6, Double.parseDouble(due));
			   
			   double bill;
			   
			   if(Integer.parseInt(pcID) == 1) {
				    bill = Integer.parseInt(totUnits) *15.00 + Double.parseDouble(due);    
				    preparedStmt.setDouble(7,bill);
				    
			   }
			   
			   else if(Integer.parseInt(pcID) == 2) {
				    bill = Integer.parseInt(totUnits) *30.00 + Double.parseDouble(due);   
				    preparedStmt.setDouble(7,bill);
			   }
			   
			   else if(Integer.parseInt(pcID) == 3) {
				    bill = Integer.parseInt(totUnits) *40.00 + Double.parseDouble(due);   
				    preparedStmt.setDouble(7,bill);
			   }
			   
			   else if(Integer.parseInt(pcID) == 4) {
				    bill = Integer.parseInt(totUnits) *65.00 + Double.parseDouble(due); 
				    preparedStmt.setDouble(7,bill);
			   }
			   
			   preparedStmt.setInt(8, Integer.parseInt(cusAccNo));
				
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
	
	
	
	//4. Create a method for update bill details
	public String updateBillDetails(String billId,String year,String month,String totUnits, String pcID, String due, String billAmount,String cusAccNo) {
		  String output = ""; 
		
		  try
		  { 
		      Connection con = connect(); 
		     
		      if (con == null) 
		      {
		    	  return "Error while connecting to the database for updating."; 
		      } 
		
		      
		      // create a prepared statement
		      String query = "UPDATE monthly_bill SET year=?, month=?,totalUnits=?,powId=?,dueAmount=?, billAmount=?,accountNumber=? WHERE billId=?"; 
		
		      PreparedStatement preparedStmt = con.prepareStatement(query); 
		
		      
		      // binding values
			  
			   preparedStmt.setString(1, year);
			   preparedStmt.setString(2, month);
			   preparedStmt.setInt(3,Integer.parseInt(totUnits));
			   preparedStmt.setInt(4,Integer.parseInt(pcID) );
			   preparedStmt.setDouble(5, Double.parseDouble(due));
			  
			
			   
			   
               double bill;
			   
			   if(Integer.parseInt(pcID) == 1) {
				    bill = Integer.parseInt(totUnits) *15.00 + Double.parseDouble(due);    
				    preparedStmt.setDouble(6,bill);
				    
			   }
			   
			   else if(Integer.parseInt(pcID) == 2) {
				    bill = Integer.parseInt(totUnits) *30.00 + Double.parseDouble(due);   
				    preparedStmt.setDouble(6,bill);
			   }
			   
			   else if(Integer.parseInt(pcID) == 3) {
				    bill = Integer.parseInt(totUnits) *40.00 + Double.parseDouble(due);   
				    preparedStmt.setDouble(6,bill);
			   }
			   
			   else if(Integer.parseInt(pcID) == 4) {
				    bill = Integer.parseInt(totUnits) *60.00 + Double.parseDouble(due); 
				    preparedStmt.setDouble(6,bill);
			   }
			  
			   preparedStmt.setInt(7, Integer.parseInt(cusAccNo));
			   
			   preparedStmt.setInt(8, Integer.parseInt(billId));
		       
		
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
	
	
	
	    
	//6. Create a method for deleting bill details
	public String deleteBill(String  billId)
	{ 
	        String output = ""; 
	        
	        try{ 
	        
	        	    Connection con = connect(); 
	               
	        	    if (con == null) { 
	                    return "Error while connecting to the database for deleting."; 
	                } 
	        	    
	        	      // create a prepared statement
	        	      String query = "delete from monthly_bill where billId=?"; 
	        	      
	        	      PreparedStatement preparedStmt = con.prepareStatement(query); 
	        	    
	        	      // binding values
	        	      preparedStmt.setInt(1, Integer.parseInt(billId)); 
	        	     
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
