package com.rhotiz.jaxrs.domain;

public class Person {
	private int id;
	private String firstName;
	private String lastName;
	
	
	
	public Person() {
	}
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public static Person valueOf(String firstLastName) throws RuntimeException{
		String[] nameArray=firstLastName.split(",");
		Person result= null;
		if (!((nameArray.length == 2) || (nameArray.length ==3))) {
			throw new RuntimeException("Bad String for Person definition");
		}
		
		for (String string : nameArray) {
			if (string.trim().equals("")) {
				throw new RuntimeException("one token of person identifier is empty");
			}
		}
		
		result = new Person();
		if (nameArray.length==3) {
			result.setId(Integer.parseInt(nameArray[0].trim()));
			result.setFirstName(nameArray[1].trim());
			result.setLastName(nameArray[2].trim());
		} else {
			result.setFirstName(nameArray[0].trim());
			result.setLastName(nameArray[1].trim());
		}
		return result;
	}
	@Override
	public String toString() {
		if (this.getId() == 0) {
			return firstName+", "+lastName;
		} else {
			return firstName+", "+lastName;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (this==obj) {
			return true;
		}
		if (obj==null) {
			return false;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		if (!this.getFirstName().equals(other.getFirstName())) {
			return false;
		}
		if (!this.getLastName().equals(other.getLastName())) {
			return false;
		}
		return true;
	}
	
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
	
	
	
}
