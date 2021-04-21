package userModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
	//Method to connect to the DB
	private Connection connect() 
	{ 
	Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
	 
			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pafproject", "root", "leonsilva19"); 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return con; 
	} 
	
	//Method to add new users
	public String insertUser(String firstName, String lastName, String address, String type) 
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
			String query = " insert into user(`userID`,`firstName`,`lastName`,`address`,`type`)"+" values (?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, firstName); 
			preparedStmt.setString(3, lastName); 
			preparedStmt.setString(4, address); 
			preparedStmt.setString(5, type); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "Inserted successfully"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while inserting the item."; 
			System.err.println(e.getMessage()); 
		} 
	 return output; 
	 }
	
	//Method to retrieve users
	public String readUsers() { 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for reading.";
			} 
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>User ID</th><th>First Name</th><th>Last Name</th>" +
				"<th>Address</th>" + 
				"<th>Type</th>" +
				"<th>Update</th><th>Remove</th></tr>"; 
 
			String query = "select * from user"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			//iterate through the rows in the result set
			while (rs.next()) 
			{ 
				String userID = Integer.toString(rs.getInt("userID")); 
				String firstName = rs.getString("firstName"); 
				String lastName = rs.getString("lastName"); 
				String address = rs.getString("address"); 
				String type = rs.getString("type"); 
			
				// Add into the html table
				output += "<tr><td>" + userID + "</td>"; 
				output += "<td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>"; 
				output += "<td>" + address + "</td>"; 
				output += "<td>" + type + "</td>"; 
			
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
					+ "<td><form method='post' action='user.jsp'>"
					+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
					+ "<input name='userID' type='hidden' value='" + userID 
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
	public String updateUser(String userID, String firstName, String lastName, String address, String type){ 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for updating.";
			} 
			// create a prepared statement
			String query = "UPDATE user SET firstName=?,lastName=?,address=?,type=? WHERE userID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			// binding values
			preparedStmt.setString(1, firstName); 
			preparedStmt.setString(2, lastName); 
		 	preparedStmt.setString(3, address); 
		 	preparedStmt.setString(4, type); 
		 	preparedStmt.setInt(5, Integer.parseInt(userID)); 
		 	
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
	public String deleteUser(String userID) { 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for deleting.";
			} 
			// create a prepared statement
			String query = "delete from user where userID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID)); 
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
