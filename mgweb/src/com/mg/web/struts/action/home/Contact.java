package com.mg.web.struts.action.home;

import java.sql.Date;
import java.sql.Time;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.communication.Mail;
import com.mg.web.struts.action.BasicAction;

public class Contact extends BasicAction{

	private static Logger log = LogManager.getLogger(Contact.class);
	private static final long serialVersionUID = 1155604242419622177L;
	private String firstName;
	private String lastName;
	private String email;
	private String subject;
	private String message;
	
	@Override
	public String execute(){
		log.debug("Contact debug execute");
		if( firstName.length() > 0 && lastName.length() > 0 && email.length() > 0 && subject.length() > 0 ){
			try{
				String[] supportEmail = ServiceLocator.getService(ConfigServiceImpl.class).getMailProjectSupport();
				
				String date         = (new Date(System.currentTimeMillis())).toString();
			    String time         = (new Time(System.currentTimeMillis())).toString();
			    
			    String subjectEmail = "Contact Message : " + subject + ".";
			    String bodyEmail    =
			        "Personal Info: " + lastName + ", " + firstName + ":\n"+
			        " Email: "+ email + "\n"+
			        " at "+time+" on "+date+"\n"+
			        " " + message;
			    
			    Mail.send(supportEmail, subjectEmail, bodyEmail);
			    cleanFields();
			}
			catch (Exception e) {
				log.error(e.getMessage(), e);
				managerException(e);
				return ERROR;
			}
		}
		return SUCCESS;
	}

	private void cleanFields() {
		firstName = "";
		lastName = "";
		email = "";
		subject = "";
		message = "";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
