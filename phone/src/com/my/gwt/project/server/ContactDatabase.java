package com.my.gwt.project.server;

import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

public class ContactDatabase {

	  /**
	   * Information about a contact.
	   */
	  public static class ContactInfo implements Comparable<ContactInfo> {

	    /**
	     * The key provider that provides the unique ID of a contact.
	     */
	    public static final ProvidesKey<ContactInfo> KEY_PROVIDER = new ProvidesKey<ContactInfo>() {
	      @Override
	      public Object getKey(ContactInfo item) {
	        return item == null ? null : item.getId();
	      }
	    };

	    private static int nextId = 0;

	    private String address;
	    private int age;
	    private Date birthday;
	    private String firstName;
	    private final int id;
	    private String lastName;

	    public ContactInfo() {
	      this.id = nextId;
	      nextId++;
	    }

	    @Override
	    public int compareTo(ContactInfo o) {
	      return (o == null || o.firstName == null) ? -1 : -o.firstName.compareTo(firstName);
	    }

	    @Override
	    public boolean equals(Object o) {
	      if (o instanceof ContactInfo) {
	        return id == ((ContactInfo) o).id;
	      }
	      return false;
	    }

//	    /**
//	     * @return the contact's address
//	     */
//	    public String getAddress() {
//	      return address;
//	    }

//	    /**
//	     * @return the contact's age
//	     */
//	    public int getAge() {
//	      return age;
//	    }

//	    /**
//	     * @return the contact's birthday
//	     */
//	    public Date getBirthday() {
//	      return birthday;
//	    }

	    /**
	     * @return the category of the conteact
	     */
//	    public Category getCategory() {
//	      return category;
//	    }

	    /**
	     * @return the contact's firstName
	     */
	    public String getFirstName() {
	      return firstName;
	    }

	    /**
	     * @return the contact's full name
	     */
	    public final String getFullName() {
	      return firstName + " " + lastName;
	    }

	    /**
	     * @return the unique ID of the contact
	     */
	    public int getId() {
	      return this.id;
	    }

	    /**
	     * @return the contact's lastName
	     */
	    public String getLastName() {
	      return lastName;
	    }

	    @Override
	    public int hashCode() {
	      return id;
	    }

//	    /**
//	     * Set the contact's address.
//	     * 
//	     * @param address the address
//	     */
//	    public void setAddress(String address) {
//	      this.address = address;
//	    }

	  }
	  
	  
}