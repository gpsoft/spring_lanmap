package jp.dip.gpsoft.lanmap.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nodes")
public class Node {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "product_code")
	private String productCode;
	private String ipaddr;
	private LocalDate acquired;
	private boolean deleted;
	private Timestamp created;
	private Timestamp modified;

	@ManyToOne
	@JoinColumn(name="node_type_id")
	private NodeType nodeType;

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public String getAcquiredStr() {
		if (acquired == null) {
			return "";
		}
		return acquired.format(DateTimeFormatter.ofPattern("yyyy年MM月"));
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public LocalDate getAcquired() {
		return acquired;
	}

	public void setAcquired(LocalDate acquired) {
		this.acquired = acquired;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

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
