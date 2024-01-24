package com.assetcd.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the attr_tab database table.
 * 
 */
@Entity
@Table(name="attr_tab")
@IdClass(AttrTab.AttrTabCompositeKey.class)
@NamedQuery(name="AttrTab.findAll", query="SELECT a FROM AttrTab a")
public class AttrTab implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descr;

	@Id
	private String ntab;

	@Id
	private int ntabd;

	private int ntabl;
	
	@Column(name="USER_CODE")
	private String userCode;

	public AttrTab() {
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getNtab() {
		return this.ntab;
	}

	public void setNtab(String ntab) {
		this.ntab = ntab;
	}

	public int getNtabd() {
		return this.ntabd;
	}

	public void setNtabd(int ntabd) {
		this.ntabd = ntabd;
	}

	public int getNtabl() {
		return this.ntabl;
	}

	public void setNtabl(int ntabl) {
		this.ntabl = ntabl;
	}
	
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	
	/**
	 * 
	 * Composite primary key
	 *
	 */
	public static class AttrTabCompositeKey implements Serializable {
		private String ntab;
		private int ntabd;
		
	}

}