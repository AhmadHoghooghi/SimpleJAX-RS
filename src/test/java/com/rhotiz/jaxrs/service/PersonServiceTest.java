package com.rhotiz.jaxrs.service;

import static org.junit.Assert.*;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

import com.rhotiz.jaxrs.domain.Person;


public class PersonServiceTest {
	private Client client;
	private static String baseURL;
	private static String postTargetURL;
	
	@BeforeClass
	public static void setVariables(){
		baseURL = "http://localhost:9090/cjxrs/services";
		postTargetURL = baseURL+"/person";
	}
	
	@Before
	public void createClient(){
		client = ClientBuilder.newClient();
	}

	@Test
	public void testPostHappyPath() {
		String messageBody = "ahmad, hoghooghi";
		Entity<String> messageBodyEntity = Entity.text(messageBody);
		
		WebTarget webTarget = client.target(postTargetURL);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.post(messageBodyEntity);
		assertThat(response.getStatusInfo().getFamily(), is(Response.Status.Family.SUCCESSFUL));
	}
	
	@Test
	public void testGetHappyPath(){
		Person person = new Person("hadi", "mansoubi");
		Response postResponse = client.target(postTargetURL).request(MediaType.TEXT_PLAIN)
			.post(Entity.text(person.getFirstName()+", "+person.getLastName()));
		String getTargetURL = postResponse.getHeaderString("Location");
		
		Response getResponse = client.target(getTargetURL).request(MediaType.TEXT_PLAIN).get();
		assertThat(getResponse.getStatusInfo().getFamily(), is(Response.Status.Family.SUCCESSFUL));
	}
	
	@Test
	public void testPutHappyPath(){
		Person person = new Person("mehdi", "elhamnia");
		Response postResponse = client.target(postTargetURL).request(MediaType.TEXT_PLAIN)
			.post(Entity.text(person.getFirstName()+", "+person.getLastName()));
		String getTargetURL = postResponse.getHeaderString("Location");
		
		person.setFirstName("sheykhmahdi");
		Response putResponse = client.target(getTargetURL).request()
				.put(Entity.text(person.getFirstName()+", "+person.getLastName()));
		assertThat(putResponse.getStatusInfo().getFamily(), is(Response.Status.Family.SUCCESSFUL));
	}
	
	@Test
	public void testDeleteHappyPath(){
		Person person = new Person("hadi","mansoubi");
		Response postResponse = client.target(postTargetURL).request(MediaType.TEXT_PLAIN)
				.post(Entity.text(person.getFirstName()+", "+person.getLastName()));
		String getTargetURL = postResponse.getHeaderString("Location");
		
		Response deleteResponse = client.target(getTargetURL).request().delete();
		assertThat(deleteResponse.getStatusInfo().getFamily(), is(Response.Status.Family.SUCCESSFUL));
	}
	
	@After
	public void closeClient(){
		client.close();
	}

}
