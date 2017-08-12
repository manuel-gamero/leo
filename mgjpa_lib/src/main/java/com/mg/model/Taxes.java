package com.mg.model;

// Generated 07-may-2015 22:26:15 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MgTaxes generated by hbm2java
 */
@Entity
@Table(name = "mg_taxes", schema = "bolsos")
public class Taxes implements java.io.Serializable, BasicModel {

	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String description;
	private BigDecimal tax;
	private Date creationDate;

	public Taxes() {
	}

	public Taxes(int id, String code, String description, BigDecimal tax) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.tax = tax;
	}

	public Taxes(int id, String code, String description, BigDecimal tax,
			Date creationDate) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.tax = tax;
		this.creationDate = creationDate;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_taxes_id")
	@SequenceGenerator(name="seq_taxes_id", sequenceName="bolsos.seq_taxes_id", initialValue=1, allocationSize= 1 )
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "description", nullable = false, length = 225)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "tax", nullable = false, precision = 10, scale = 2)
	public BigDecimal getTax() {
		return this.tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
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

}
