package sponsorService;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*;

import sponsorModel.Sponsor;

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Sponsor") 

public class sponsorService {
	Sponsor sp = new Sponsor(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String getSponsors() 
	{ 
		return sp.getSponsors();
	}

	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String addSponsors(@FormParam("sponsor_name") String sponsor_name, 
			@FormParam("sponsor_type") String sponsor_type, 
			@FormParam("email") String email, 
			@FormParam("phone") String phone,
			@FormParam("funding_amount") String funding_amount) 
	{ 
			String output = sp.addSponsor(sponsor_name, sponsor_type, email, phone, funding_amount);
			return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String spon) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(spon).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String sponsorID = itemObject.get("sponsorID").getAsString(); 
		String sponsor_name = itemObject.get("sponsor_name").getAsString(); 
		String sponsor_type = itemObject.get("sponsor_type").getAsString(); 
		String email = itemObject.get("email").getAsString(); 
		String phone = itemObject.get("phone").getAsString(); 
		String funding_amount = itemObject.get("funding_amount").getAsString(); 
		
		String output = sp.updateSponsoringDetails(sponsorID, sponsor_name, sponsor_type, email, phone, funding_amount); 
		
		return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteUser(String spon) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(spon, "", Parser.xmlParser()); 
 
		//Read the value from the element <itemID>
		String sponsorID = doc.select("sponsorID").text(); 
		String output = sp.cancelSponsoring(sponsorID);
		return output; 
	}
}
