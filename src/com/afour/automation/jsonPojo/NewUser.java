package com.afour.automation.jsonPojo;

import java.util.HashMap;

public class NewUser {
	private String email;
	private String password ;
	private String company;
	private String phone;
	private String firstName ;
	private String lastName;
	private String address;

	public NewUser(HashMap<String,String> testData){
		setEmail( testData.get("email").toString());
		setPassword(testData.get("password").toString());
        setCompany(testData.get("company").toString());
        setPhone(testData.get("phone").toString());
        setFirstName(testData.get("firstName").toString());
        setLastName(testData.get("lastName").toString());
        setAddress(testData.get("address").toString());
	}
	
	
	public void setEmail(String newEmail){
		this.email = newEmail;
		
	}
	
	public void setPassword (String newPassword){
		this.password = newPassword;
	}
	
	public void setCompany(String newCompany){
		this.company = newCompany;
	}
	
	public void setPhone(String newPhone){
		this.phone = newPhone;
	}
	
	public void setFirstName(String newFirstName){
		this.firstName = newFirstName;
	}
	
	public void setLastName(String newLastName){
		this.lastName = newLastName;
	}
	
	public void setAddress(String newAddress){
		this.address = newAddress;
	}
	

}
