package com.my.gwt.project.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Contact implements Serializable {
	private String id;
	private String name;
	private String phoneNumber;
	
	public Contact() {}
	
	public Contact(String id, String name, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return name + " " + phoneNumber;
	}
}
