package cartService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cartModel.Cart;

@Path("/Cart") 

public class cartService {
	Cart cartObj = new Cart(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readCart() 
	{ 
		return cartObj.readCart(); 
	}

	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("category") String category, 
			@FormParam("quantity") String quantity, 
			@FormParam("itemName") String itemName) 
	{ 
			String output = cartObj.insertCartItem(category, quantity, itemName);
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
		String cartID = itemObject.get("cartID").getAsString(); 
		String category = itemObject.get("category").getAsString(); 
		String quantity = itemObject.get("quantity").getAsString(); 
		String itemName = itemObject.get("itemName").getAsString(); 
		
		String output = cartObj.updateCartItems(cartID, category, quantity, itemName);
		
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
		String cartID = doc.select("cartID").text(); 
		String output = cartObj.deleteCartItems(cartID);
		return output; 
	}
}
