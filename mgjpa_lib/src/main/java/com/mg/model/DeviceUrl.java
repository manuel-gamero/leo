package com.mg.model;

// Generated Sep 18, 2016 9:01:23 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mg.datamining.interfaces.IDevice;

/**
 * DeviceUrl generated by hbm2java
 */
@Entity
@Table(name = "mg_device_url", schema = "bolsos")
public class DeviceUrl implements java.io.Serializable, BasicModel, IDevice {

	private static final long serialVersionUID = 8063704046844922420L;
	private int id;
	private Device device;
	private String url;
	private String urlFrom;
	private Boolean ajax;
	private Long duration;
	private Date actionDate;
	private Date creationDate;

	public DeviceUrl() {
	}

	public DeviceUrl(int id, Device device) {
		this.id = id;
		this.device = device;
	}

	public DeviceUrl(int id, Device device, String url, Boolean ajax,
			Long duration, Date actionDate, Date creationDate) {
		this.id = id;
		this.device = device;
		this.url = url;
		this.ajax = ajax;
		this.duration = duration;
		this.actionDate = actionDate;
		this.creationDate = creationDate;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_device_url_id")
	@SequenceGenerator(name="seq_device_url_id", sequenceName="bolsos.seq_device_url_id", initialValue=1, allocationSize= 1 )
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id", nullable = false)
	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ajax")
	public Boolean getAjax() {
		return this.ajax;
	}

	public void setAjax(Boolean ajax) {
		this.ajax = ajax;
	}

	@Column(name = "duration")
	public Long getDuration() {
		return this.duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "action_date", length = 29)
	public Date getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 29)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "url_from", length = 200)
	public String getUrlFrom() {
		return urlFrom;
	}

	public void setUrlFrom(String urlFrom) {
		this.urlFrom = urlFrom;
	}

}
