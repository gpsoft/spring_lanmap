package jp.dip.gpsoft.lanmap.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jp.dip.gpsoft.lanmap.form.UserForm;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String password;
	private boolean admin;
	@Embedded
	private Stamp stamp = new Stamp();

	public User() {
	}

	public User(UserForm form) {
		if (form.withId()) {
			id = form.getId();
		}
		name = form.getName();
		if (form.withPassword()) {
			password = form.getPassword();
		}
		admin = form.isAdmin();
	}

	public void patch(UserForm form) {
		name = form.getName();
		if (form.withPassword()) {
			password = form.getPassword();
		}
		admin = form.isAdmin();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Stamp getStamp() {
		return stamp;
	}

	public void setStamp(Stamp stamp) {
		this.stamp = stamp;
	}

}
