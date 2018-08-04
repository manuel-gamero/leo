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

import com.mg.entities.IDevice;

/**
 * DeviceCollection generated by hbm2java
 */
@Entity
@Table(name = "mg_device_collection", schema = "bolsos")
public class DeviceCollection implements java.io.Serializable, BasicModel, IDevice {

	private static final long serialVersionUID = 4298014599479856048L;
	private int id;
	private Device device;
	private Collection collection;
	private Integer count;
	private Date lastModification;
	private Date creationDate;

	public DeviceCollection() {
	}

	public DeviceCollection(int id, Device device,
			Collection collection) {
		this.id = id;
		this.device = device;
		this.collection = collection;
	}

	public DeviceCollection(int id, Device device,
			Collection collection, Integer count, Date lastModification,
			Date creationDate) {
		this.id = id;
		this.device = device;
		this.collection = collection;
		this.count = count;
		this.lastModification = lastModification;
		this.creationDate = creationDate;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_device_collection_id")
	@SequenceGenerator(name="seq_device_collection_id", sequenceName="bolsos.seq_device_collection_id", initialValue=1, allocationSize= 1 )
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id", nullable = false)
	public Collection getCollection() {
		return this.collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modification", length = 29)
	public Date getLastModification() {
		return this.lastModification;
	}

	public void setLastModification(Date lastModification) {
		this.lastModification = lastModification;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 29)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


}
