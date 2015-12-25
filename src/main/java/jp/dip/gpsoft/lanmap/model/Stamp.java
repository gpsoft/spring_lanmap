package jp.dip.gpsoft.lanmap.model;

import java.sql.Timestamp;

import javax.persistence.Embeddable;

@Embeddable
public class Stamp {
	private Timestamp created;
	private Timestamp modified;

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
}
