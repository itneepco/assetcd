package com.assetcd.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the reports_nm database table.
 * 
 */
@Entity
@Table(name="reports_nm")
@NamedQuery(name="ReportsNm.findAll", query="SELECT r FROM ReportsNm r")
public class ReportsNm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="REP_CODE")
	private String repCode;

	@Column(name="REP_NM")
	private String repNm;

	public ReportsNm() {
	}

	public String getRepCode() {
		return this.repCode;
	}

	public void setRepCode(String repCode) {
		this.repCode = repCode;
	}

	public String getRepNm() {
		return this.repNm;
	}

	public void setRepNm(String repNm) {
		this.repNm = repNm;
	}

}