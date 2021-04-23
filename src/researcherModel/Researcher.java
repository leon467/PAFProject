package researcherModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Researcher {
	//Method to connect to the DB
		private Connection connect() 
		{ 
		Connection con = null; 
			try
			{ 
				Class.forName("com.mysql.jdbc.Driver"); 
		 
				//Provide the correct details: DBServer/DBName, username, password 
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/pafproject", "root1", "19059396"); 
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			return con; 
		} 
		
		//Method to add projects
		public String addProjects(String name, String address, String email, String project_name, String project_desc) 
		{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
				if (con == null) 
				{
					return "Error while connecting to the database for inserting.";
				} 
				// create a prepared statement
				String query = " insert into researcher(`researcherID`,`name`,`address`,`email`,`project_name`,`project_desc`)"+" values (?, ?, ?, ?, ?, ?)"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, 0); 
				preparedStmt.setString(2, name); 
				preparedStmt.setString(3, address); 
				preparedStmt.setString(4, email); 
				preparedStmt.setString(5, project_name); 
				preparedStmt.setString(6, project_desc); 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				output = "Added successfully"; 
			} 
			catch (Exception e) 
			{ 
				output = "Error while adding the project."; 
				System.err.println(e.getMessage()); 
			} 
		 return output; 
		 }
		
		//Method to retrieve projects
		public String getProjects() { 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
				if (con == null) 
				{
					return "Error while connecting to the database for reading.";
				} 
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Researcher ID</th><th>Name</th><th>address</th><th>email</th>" +
					"<th>Project name</th>" + 
					"<th>Project Description</th>" +
					"<th>Update</th><th>Remove</th></tr>"; 
	 
				String query = "select * from researcher"; 
				Statement stmt = con.createStatement(); 
				ResultSet rs = stmt.executeQuery(query); 
				//iterate through the rows in the result set
				while (rs.next()) 
				{ 
					String researcherID = Integer.toString(rs.getInt("researcherID")); 
					String name = rs.getString("name"); 
					String address = rs.getString("address"); 
					String email = rs.getString("email"); 
					String project_name = rs.getString("project_name"); 
					String project_type = rs.getString("project_desc"); 
				
					// Add into the html table
					output += "<tr><td>" + researcherID + "</td>"; 
					output += "<td>" + name + "</td>";
					output += "<td>" + address + "</td>"; 
					output += "<td>" + email + "</td>"; 
					output += "<td>" + project_name + "</td>"; 
					output += "<td>" + project_type + "</td>"; 
				
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='user.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='userID' type='hidden' value='" + researcherID 
						+ "'>" + "</form></td></tr>"; 
				} 
				con.close(); 
				//Complete the html table
					output += "</table>"; 
			} 
			catch (Exception e) 
			{ 
				output = "Error while reading the items."; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
		 }
			
		//Method to update user details
		public String updateProject(String researcherID, String name, String address, String email, String project_name, String project_desc){ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
				if (con == null) 
				{
					return "Error while connecting to the database for updating.";
				} 
				// create a prepared statement
				String query = "UPDATE researcher SET name=?,address=?,email=?,project_name=?, project_desc=? WHERE researcherID=?"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				// binding values
				preparedStmt.setString(1, name); 
				preparedStmt.setString(2, address); 
			 	preparedStmt.setString(3, email); 
			 	preparedStmt.setString(4, project_name); 
			 	preparedStmt.setString(5, project_desc);
			 	preparedStmt.setInt(6, Integer.parseInt(researcherID)); 
			 	
			 	// execute the statement
			 	preparedStmt.execute(); 
			 	con.close(); 
			 	output = "Updated successfully"; 
			} 
			catch (Exception e) 
			{ 
				output = "Error while updating the item."; 
				System.err.println(e.getMessage()); 
			} 
		 		return output; 
		 }

		//Method to delete a user
		public String deleteProject(String researcherID) { 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
				if (con == null) 
				{
					return "Error while connecting to the database for deleting.";
				} 
				// create a prepared statement
				String query = "delete from researcher where researcherID=?"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(researcherID)); 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				output = "Deleted successfully"; 
			} 
			catch (Exception e) 
			{ 
				output = "Error while deleting the item."; 
				System.err.println(e.getMessage()); 
			} 
			return output;
		}
}
