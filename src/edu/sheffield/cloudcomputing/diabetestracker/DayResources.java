package edu.sheffield.cloudcomputing.diabetestracker;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/days")
public class DayResources {
	

	private DayDAO dao = DayDAO.getInstance();
	
	@GET
	@Path("/")
	@Produces({MediaType.TEXT_PLAIN})
	public Response indexGet() {
	return Response
	.status(200)
	.header("Access-Control-Allow-Origin", "*")
	.header("Access-Control-Allow-Credentials", "true")
	.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	.entity("")
	.build();
	}
	
	@GET
	@Path("list/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Day> listAll(@PathParam("name") String name) {
		return dao.listAll(name);
	}
	 
	@GET
	@Path("list/{name}/{lowBoundry}/{highBoundry}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Day> listSpecified(@PathParam("name") String name, @PathParam("lowBoundry") String low, @PathParam("highBoundry") String high) {
		return dao.listBetween(name, low, high);
	}
	
	@GET
	@Path("bloodGlucose/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public double averageBloodGlucoseAll(@PathParam("name") String name) {
		return dao.averageBloodGlucoseAll(name);
	}
	
	@GET
	@Path("bloodGlucose/{name}/{lowBoundry}/{highBoundry}")
	@Produces(MediaType.APPLICATION_JSON)
	public double averageBloodGlucoseBetween(@PathParam("name") String name, @PathParam("lowBoundry") String low, @PathParam("highBoundry") String high) {
		return dao.averageBloodGlucoseBetween(name, low, high);
	}
	
	@GET
	@Path("carbIntake/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public double averageCarbIntakeAll(@PathParam("name") String name) {
		return dao.averageCarbIntakeAll(name);
	}
	
	@GET
	@Path("carbIntake/{name}/{lowBoundry}/{highBoundry}")
	@Produces(MediaType.APPLICATION_JSON)
	public double averageCarbIntakeBetween(@PathParam("name") String name, @PathParam("lowBoundry") String low, @PathParam("highBoundry") String high) {
		return dao.averageCarbIntakeBetween(name, low, high);
	}

	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDay(@FormParam("name") String patient, @FormParam("bloodGlucose") double bloodGlucose, @FormParam("carbIntake") double carbIntake,
			@FormParam("medicationDose") double medicationDose, @FormParam("trackedDay") String date) throws URISyntaxException {
		
		Day d = new Day(0, date, bloodGlucose, carbIntake, medicationDose);
		URI uri;
		int newDayId = dao.addDay(patient, d);
		if(newDayId != -1)
			uri = new URI("/days/list/" + patient + "/" + date + "/" + date);
		else
			uri = new URI("/days/");
	
		return Response.created(uri).status(200)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.entity("")
				.build();
	}
	


	
}
