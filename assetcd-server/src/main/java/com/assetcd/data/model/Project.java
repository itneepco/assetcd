package com.assetcd.data.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the projects database table.
 * 
 */
@Entity
@Table(name="projects")
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deleted_at")
	private Date deletedAt;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name="PROJ_CODE")
	private String projCode;

	@Column(name="PROJ_DESC")
	private String projDesc;

	@Column(name="PROJ_LOC")
	private String projLoc;

	private String status;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;
	
	//bi-directional many-to-one association to UsersProject
	@OneToMany(mappedBy="project")
	private List<UsersProject> usersProjects;

	public Project() {
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return this.deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjCode() {
		return this.projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getProjDesc() {
		return this.projDesc;
	}

	public void setProjDesc(String projDesc) {
		this.projDesc = projDesc;
	}

	public String getProjLoc() {
		return this.projLoc;
	}

	public void setProjLoc(String projLoc) {
		this.projLoc = projLoc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public List<UsersProject> getUsersProjects() {
		return this.usersProjects;
	}

	public void setUsersProjects(List<UsersProject> usersProjects) {
		this.usersProjects = usersProjects;
	}

	public UsersProject addUsersProject(UsersProject usersProject) {
		getUsersProjects().add(usersProject);
		usersProject.setProject(this);

		return usersProject;
	}

	public UsersProject removeUsersProject(UsersProject usersProject) {
		getUsersProjects().remove(usersProject);
		usersProject.setProject(null);

		return usersProject;
	}

}