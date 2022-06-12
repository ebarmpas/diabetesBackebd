package edu.sheffield.cloudcomputing.diabetestracker;

import java.net.URI;
import java.net.URISyntaxException;
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
	

	private DiabetesDAO dao = DiabetesDAO.getInstance();
	
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
	@Path("list/patients")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> listPatients(){
		return dao.listPatients();
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
	public Response addDay(String x) throws URISyntaxException {
		
		String[] fields = x.split("[&=]");
		
		Day d = new Day(fields[9], Double.valueOf(fields[7]),Double.valueOf(fields[5]), Double.valueOf(fields[3]));
		URI uri;
		int newDayId = dao.addDay(fields[1], d);
		if(newDayId != -1)
			uri = new URI("/days/list/" + fields[1] + "/" + fields[7] + "/" + fields[7]);
		else
			uri = new URI("/days/");
	
		return Response.created(uri).status(200).build();
	}
	
	@POST
	@Path("patient")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPatient(String x) {
		URI uri;
		
		String[] fields = x.split("=");

		try {
			uri = new URI("/days/list/" + dao.addPatient(fields[1]));
		} catch (URISyntaxException e) {
			uri = null;
			e.printStackTrace();
		}
		return Response.created(uri).status(200).build();
	}

	@DELETE
	@Path("delete/{name}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteDay(@PathParam("name") String name, @PathParam("date") String date) {
		return dao.deleteDay(name, date);
	}

	
}
