
package com.fortinet.fcasb.watcher.alert.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 6506900627161601225L;
	@Column
	protected long created;
	@Column(name = "last_updated")
	protected long lastUpdated;

	@PrePersist
	protected void onCreate() {
		created = System.currentTimeMillis();
		lastUpdated = System.currentTimeMillis();
	}

	@PreUpdate
	protected void onUpdate() {
		lastUpdated = System.currentTimeMillis();
	}

	public long getCreated() {
		return created;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

}
