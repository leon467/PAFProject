package researcherService;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

import researcherModel.Researcher;

@Path("/Researcher")

public class researcherService {
	Researcher r = new Researcher(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String getProject() 
	{ 
		return r.getProjects();
	}

	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String addProject(@FormParam("name") String name, 
			@FormParam("address") String address, 
			@FormParam("email") String email, 
			@FormParam("project_name") String project_name,
			@FormParam("project_desc") String project_desc) 
	{ 
			String output = r.addProjects(name, address, email, project_name, project_desc);
			return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String proj) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(proj).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String researcherID = itemObject.get("researcherID").getAsString(); 
		String name = itemObject.get("name").getAsString(); 
		String address = itemObject.get("address").getAsString(); 
		String email = itemObject.get("email").getAsString(); 
		String project_name = itemObject.get("project_name").getAsString(); 
		String project_desc = itemObject.get("project_desc").getAsString(); 
		
		String output = r.updateProject(researcherID, name, address, email, project_name, project_desc);
		
		return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteUser(String pro) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(pro, "", Parser.xmlParser()); 
 
		//Read the value from the element <itemID>
		String researcherID = doc.select("researcherID").text(); 
		String output = r.deleteProject(researcherID);
		return output; 
	}
}
