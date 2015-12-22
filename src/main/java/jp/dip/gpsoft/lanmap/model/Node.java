package jp.dip.gpsoft.lanmap.model;

import java.time.LocalDate;

public class Node {

	private Long id;
	private String name;
	private Long nodeTypeId;
	private String description;
	private String productName;
	private String productCode;
	private String ipaddr;
	private LocalDate acquired;
	private boolean deleted;

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

	public Long getNodeTypeId() {
		return nodeTypeId;
	}

	public void setNodeTypeId(Long nodeTypeId) {
		this.nodeTypeId = nodeTypeId;
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

}
