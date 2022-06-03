package edu.sheffield.cloudcomputing.diabetestracker;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	
	//DAO class creates and returns its single instance
	//This instance is then used by all APIs below to call the appropriate DAO methods
	private DayDAO dao = DayDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "{\"test\":true}";
	}
	
	//API to list all products
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
//	@GET
//	@Path("/day/{patient}/{day}")
//	public Day
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response add(String patient, Day day) throws URISyntaxException {
//		int newDayId = dao.addDay(patient, day);
//		URI uri = new URI("/days/ + newDayId);
//	
//		return Response.created(uri).build();
//	}
//	
//	//API to get  product
//	@GET
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response get(@PathParam("id") int id) {
//		Day day = null;
//		try {
//			day = dao.get(id);
//		} catch (SQLException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (day != null) {
//
//			return Response
//					.ok(day, MediaType.APPLICATION_JSON)
//					.build();
//		} else {
//			//status(Response.Status.NOT_FOUND) returns a new ResponseBuilder object with the supplied status
//			return Response.status(Response.Status.NOT_FOUND).build();
//		}
//	}

	
}
