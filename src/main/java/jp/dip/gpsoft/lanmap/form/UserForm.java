package jp.dip.gpsoft.lanmap.form;

import org.hibernate.validator.constraints.NotBlank;

import jp.dip.gpsoft.lanmap.model.User;
import jp.dip.gpsoft.lanmap.validation.UniqueUsername;

@UniqueUsername
public class UserForm {
	private Long id = null;
	@NotBlank
	private String name = "";
	private String password = "";
	private String passwordRepeated = "";
	private boolean admin;

	public void setupForEdit(User user) {
		id = user.getId();
		name = user.getName();
		admin = user.isAdmin();
	}

	public boolean withId() {
		return id != null;
	}

	public boolean withPassword() {
		return !password.isEmpty();
	}

	public String toString() {
		return String.format("UserForm: id=%s, name=%s, pw=%s, pw2=%s, admin=%s", id, name, password, passwordRepeated,
				admin ? "YES" : "NO");
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

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
