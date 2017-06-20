package com.rhotiz.jaxrs.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.rhotiz.jaxrs.service.PersonService;
@ApplicationPath("services")
public class PersonApplication extends Application {
	
	private Set<Object> singletons = new HashSet<>();
	
	public PersonApplication() {
		singletons.add(new PersonService());
	}
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
