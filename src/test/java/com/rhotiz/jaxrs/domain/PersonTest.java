package com.rhotiz.jaxrs.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testFromForTwoPartString() {
		String personIdentifier = "ahmad, hoghooghi";
		Person ahmadHoghooghi = Person.from(personIdentifier);
		assertEquals("ahmad", ahmadHoghooghi.getFirstName());
		assertEquals("hoghooghi", ahmadHoghooghi.getLastName());
	}
	
	@Test
	public void testFromForTreePartString() {
		String personIdentifier = "1, ahmad, hoghooghi";
		Person ahmadHoghooghi = Person.from(personIdentifier);
		assertEquals(1,ahmadHoghooghi.getId());
		assertEquals("ahmad", ahmadHoghooghi.getFirstName());
		assertEquals("hoghooghi", ahmadHoghooghi.getLastName());
	}
	
	@Test
	public void testEqualityOfPersons(){
		Person ahmad1 = new Person("ahmad", "hoghooghi");
		Person ahmad2 = new Person("ahmad","hoghooghi");
		assertEquals(ahmad1, ahmad2);
	}
	
	@Test
	public void testUnEqualityOfPersons(){
		Person ahmad1 = new Person("ahmad", "hoghooghi");
		Person ahmad2 = new Person("ahmad","elahi");
		assertNotEquals(ahmad1, ahmad2);
	}
	
	@Test
	public void equalityOfHashCode(){
		Person ahmad1 = new Person("ahmad", "hoghooghi");
		Person ahmad2 = new Person("ahmad","hoghooghi");
		assertEquals(ahmad1.hashCode(), ahmad2.hashCode());
	}
	
	@Test
	public void nonEqualityOfHashCode(){
		Person ahmad1 = new Person("ahmad", "hoghooghi");
		Person ahmad2 = new Person("ahmad","elahi");
		assertNotEquals(ahmad1.hashCode(), ahmad2.hashCode());
	}


}
