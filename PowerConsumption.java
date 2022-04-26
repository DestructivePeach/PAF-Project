package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PowerConsumption {


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
		
		
		//2. Create the method for reading Power Consumption Description Units with Price
	    public String readPowerConsumption()
		{ 
		    String output = ""; 
		   
		    try { 
		          Connection con = connect(); 
		          
		          if (con == null)  { 
		             return "Error while connecting to the database for reading."; 
		          } 
		 
		          // Prepare the html table to be displayed
		          output = "<table border='1'><tr><th>Power Consumption ID</th>" 
		                   +"<th>Description</th><th>Charge Per Unit</th>"
		                   + "<th>Update</th><th>Remove</th></tr>";              
		          
		          String query = "select * from power_consumption"; 
		          Statement stmt = con.createStatement(); 
		          ResultSet rs = stmt.executeQuery(query); 
		
		          // iterate through the rows in the result set
		          while (rs.next()) { 
		        	  
		        	     String powId = Integer.toString(rs.getInt("powId")); 
			             String unitDescription = rs.getString("unitDescription");  
			             String unitPrice = Double.toString(rs.getDouble("unitPrice")); 
		 
			             // Add a row into the html table
			             output += "<tr><td>" + powId + "</td>"; 
			             output += "<td>" + unitDescription + "</td>"; 
			             output += "<td>" + unitPrice + "</td>";
		 
			             //...... buttons - wrapped the update &remove button inside a form. 
				            output += "<td><input name='btnUpdate' " 
				                   + " type='button' value='Update' class='btn btn-secondary'></td>"
				                   + "<td><form method='post' action='powerConsumption.jsp'>"
				                   + "<input name='btnRemove' " 
				                   + " type='submit' value='Remove' class='btn btn-danger'>"
				                   + "<input name='itemID' type='hidden' " 
				                   + " value='" + powId + "'>" + "</form></td></tr>"; 
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
	    
	    
	    
	  
	    //3. Create a method for inserting Power Consumption Description Units with Price
		public String insertPowerConsumption(String ID, String description, String price)
		{ 
			 
	       String output = "";
			
		   try{
				  
			    Connection con = connect();
		
			    if (con == null) {
					   return "Error while connecting to the database";
			    }
			
			    // create a prepared statement
			    String query = "insert into power_consumption (powId,unitDescription,unitPrice)" + " values (?, ?, ?)";
		
			   PreparedStatement preparedStmt = con.prepareStatement(query);
		
			   // binding values
			   preparedStmt.setInt(1, Integer.parseInt(ID));
			   preparedStmt.setString(2, description);
			   preparedStmt.setDouble(3, Double.parseDouble(price));
				
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
		
		
		
		//4. Create a method for updating Power Consumption Description Units with Price
		public String updatePowerConsumption(String ID, String description, String price) {
			  String output = ""; 
			
			  try
			  { 
			      Connection con = connect(); 
			     
			      if (con == null) 
			      {
			    	  return "Error while connecting to the database for updating."; 
			      } 
			
			      // create a prepared statement
			      String query = "UPDATE power_consumption SET unitDescription=?,unitPrice=? WHERE powId=?"; 
			
			      PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			      // binding values
			      //preparedStmt.setInt(1, Integer.parseInt(ID));
				  preparedStmt.setString(1, description);
				  preparedStmt.setDouble(2, Double.parseDouble(price));
				  preparedStmt.setInt(3, Integer.parseInt(ID));
			       
			
			        // execute the statement
			        preparedStmt.execute(); 
			        con.close(); 
			        output = "Updated successfully"; 
			 } 
			 catch (Exception e) 
			 { 
			        output = "Error while updating data"; 
			        System.err.println(e.getMessage()); 
			 } 
			 
			  return output;
		}
		
		
		
		
		//5. Create a method for deleting Power Consumption Description Units with Price
		public String deletePowerConsumption(String powId)
		{ 
		        String output = ""; 
		        
		        try{ 
		        
		        	    Connection con = connect(); 
		               
		        	    if (con == null) { 
		                    return "Error while connecting to the database for deleting."; 
		                } 
		        	    
		        	      // create a prepared statement
		        	      String query = "delete from power_consumption where powId=?"; 
		        	      
		        	      PreparedStatement preparedStmt = con.prepareStatement(query); 
		        	    
		        	      // binding values
		        	      preparedStmt.setInt(1, Integer.parseInt(powId)); 
		        	     
		        	      // execute the statement
		        	      preparedStmt.execute(); 
		        	    
		        	      con.close(); 
		        	    
		        	      output = "Deleted successfully";
		        	    
		        } 
		        catch (Exception e) { 
		              
		        	     output = "Error while deleting data"; 
		                 System.err.println(e.getMessage()); 
		        }
		        
		        return output; 
		}
		

}
