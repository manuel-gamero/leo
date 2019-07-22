package com.mg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass 
public class BasicAudit{

	protected int id;
	private String username;
	private String location;
	private String sessionGuid;
	private String requestGuid;
	private Date creationDate;
	private String actionUser;
	private String outcome;
	private String message;
	private String serverName;
	private String browser;
	private String url;
	private Integer callDuration;
	
	//public abstract int getId();
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "username", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "location", length = 64)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "session_guid", length = 32)
	public String getSessionGuid() {
		return this.sessionGuid;
	}

	public void setSessionGuid(String sessionGuid) {
		this.sessionGuid = sessionGuid;
	}

	@Column(name = "request_guid", length = 32)
	public String getRequestGuid() {
		return this.requestGuid;
	}

	public void setRequestGuid(String requestGuid) {
		this.requestGuid = requestGuid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 29)
	public Date getCreationDate() {
		if(this.creationDate == null){
			return new Date();
		}
		else{
			return this.creationDate;
		}
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "action_user", length = 128)
	public String getActionUser() {
		return this.actionUser;
	}

	public void setActionUser(String actionUser) {
		this.actionUser = actionUser;
	}

	@Column(name = "outcome", length = 128)
	public String getOutcome() {
		return this.outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	@Column(name = "message", length = 2000)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "server_name", length = 40)
	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Column(name = "browser")
	public String getBrowser() {
		return this.browser;
	}

	public void setBrowser(String browser) {
		String newBrowser = browser;
		if (browser != null && browser.length() > 250){
			newBrowser = browser.substring(0, 250);
		}
		this.browser = newBrowser;
	}

	@Column(name = "url", length = 2000)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "call_duration")
	public Integer getCallDuration() {
		return this.callDuration;
	}

	public void setCallDuration(Integer callDuration) {
		this.callDuration = callDuration;
	}
	
	@Override
	public String toString(){
		String info = "\r\n" + " id: " + id;
		info = info + "\r\n" + " username: " + username;
		info = info + "\r\n" + " location: " + location;
		info = info + "\r\n" + " sessionGuid: " + sessionGuid;
		info = info + "\r\n" + " requestGuid: " + requestGuid;
		info = info + "\r\n" + " actionUser: " + actionUser;
		info = info + "\r\n" + " outcome: " + outcome;
		info = info + "\r\n" + " message: " + message;
		info = info + "\r\n" + " serverName: " + serverName;
		info = info + "\r\n" + " url: " + url;
		return info;
	}

}
