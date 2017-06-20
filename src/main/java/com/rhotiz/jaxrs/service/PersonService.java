package com.rhotiz.jaxrs.service;

import java.io.BufferedReader;
import java.io.Reader;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rhotiz.jaxrs.domain.Person;

@Path("person")
public class PersonService {
	private Map<Integer, Person> personDB = new  ConcurrentHashMap<>();
	private AtomicInteger idCounter = new AtomicInteger(0);
	
	@POST
	@Consumes("text/plain")
	public Response createPerson(Reader messageBody) throws Exception{
			Person person = readPersonFromMessegeBody(messageBody);
			int keyOfPersonInDB = addToDatabase(person); 	
			return Response.created(URI.create("person/"+keyOfPersonInDB)).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response readPerson(@PathParam("id") int id){
		Person person = personDB.get(id);
		if (person==null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return Response.status(Response.Status.OK).entity(person.toString()).build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void updatePerson(@PathParam("id") int id, Reader messageBody) throws Exception{
		Person current = personDB.get(id);
		Person update = readPersonFromMessegeBody(messageBody);
		
		if (current == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			update.setId(id);
			personDB.replace(id, update);
		}
		
	}
	
	@DELETE
	@Path("{id}")
	public void deletePerson(@PathParam("id") int id){
		Person person = personDB.get(id);
		if (person == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			
			personDB.remove(id);
		}
		
	}
	
	
	private int addToDatabase(Person person){
		if (personDB.containsValue(person)) {
			int personId=0;
			for (int id : personDB.keySet()) {
				if (personDB.get(id).equals(person)) {
					personId=id;
				}
			}
			person.setId(personId);
			return personId;
		}else{
			person.setId(idCounter.incrementAndGet());
			personDB.put(person.getId(), person);
			return person.getId();
		}
		
		
	}
	
	private Person readPersonFromMessegeBody(Reader messageBody) throws Exception{
		Person person;
		BufferedReader bufferedReader = new BufferedReader(messageBody);
		String personString = "";
		String line = bufferedReader.readLine();
			while (line != null) {
				personString = personString + line;
				line=bufferedReader.readLine();
			}
			
			try{
				person = Person.from(personString);
			}catch (Exception e) {
				throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
			}
			return person;
	}
}
