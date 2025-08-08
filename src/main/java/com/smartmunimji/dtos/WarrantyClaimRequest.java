package com.smartmunimji.dtos;

import lombok.Data;

@Data
public class WarrantyClaimRequest {
	private int registeredProductId;
	private String reason;
	private String description;

	public int getRegisteredProductId() {
		return registeredProductId;
	}

	public void setRegisteredProductId(int registeredProductId) {
		this.registeredProductId = registeredProductId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
