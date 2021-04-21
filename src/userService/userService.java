package userService;

import userModel.User; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/User") 

public class userService {
	User userObj = new User(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	{ 
		return userObj.readUsers(); 
	}

	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("firstName") String firstName, 
			@FormParam("lastName") String lastName, 
			@FormParam("address") String address, 
			@FormParam("type") String type) 
	{ 
			String output = userObj.insertUser(firstName, lastName, address, type); 
			return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String userID = itemObject.get("userID").getAsString(); 
		String firstName = itemObject.get("firstName").getAsString(); 
		String lastName = itemObject.get("lastName").getAsString(); 
		String address = itemObject.get("address").getAsString(); 
		String type = itemObject.get("type").getAsString(); 
		
		String output = userObj.updateUser(userID, firstName, lastName, address, type); 
		
		return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteUser(String itemData) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
 
		//Read the value from the element <itemID>
		String userID = doc.select("userID").text(); 
		String output = userObj.deleteUser(userID); 
		return output; 
	}
}
