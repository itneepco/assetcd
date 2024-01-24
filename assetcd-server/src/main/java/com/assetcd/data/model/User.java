package com.assetcd.data.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="confirmation_code")
	private String confirmationCode;

	private byte confirmed;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="deleted_at")
	private Timestamp deletedAt;

	private String email;

	@Column(name="first_name")
	private String firstName;

	private String image;

	@Column(name="last_name")
	private String lastName;

	@JsonIgnore
	private String password;

	@Column(name="remember_token")
	private String rememberToken;

	private byte status;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	@Column(name="user_code")
	private String userCode;

	@Column(name="user_proj_code")
	private String userProjCode;

	@Column(name="user_type")
	private String userType;

	@Column(name="user_user_code")
	private String userUserCode;

	//bi-directional many-to-many association to Role
	//@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="role_user"
		, joinColumns={
			@JoinColumn(name="user_id", referencedColumnName="id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id", referencedColumnName="id")
			}
		)
	private List<Role> roles;
	
	//bi-directional many-to-one association to UsersProject
	@OneToMany(mappedBy="user")
	private List<UsersProject> usersProjects;

	public User() {
	}
	
	public User(User user) {
		super();
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.userCode = user.getUserCode();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}


	public String getConfirmationCode() {
		return this.confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public byte getConfirmed() {
		return this.confirmed;
	}

	public void setConfirmed(byte confirmed) {
		this.confirmed = confirmed;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getDeletedAt() {
		return this.deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRememberToken() {
		return this.rememberToken;
	}

	public void setRememberToken(String rememberToken) {
		this.rememberToken = rememberToken;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserProjCode() {
		return this.userProjCode;
	}

	public void setUserProjCode(String userProjCode) {
		this.userProjCode = userProjCode;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserUserCode() {
		return this.userUserCode;
	}

	public void setUserUserCode(String userUserCode) {
		this.userUserCode = userUserCode;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<UsersProject> getUsersProjects() {
		return this.usersProjects;
	}

	public void setUsersProjects(List<UsersProject> usersProjects) {
		this.usersProjects = usersProjects;
	}

	public UsersProject addUsersProject(UsersProject usersProject) {
		getUsersProjects().add(usersProject);
		usersProject.setUser(this);

		return usersProject;
	}

	public UsersProject removeUsersProject(UsersProject usersProject) {
		getUsersProjects().remove(usersProject);
		usersProject.setUser(null);

		return usersProject;
	}



}