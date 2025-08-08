package com.smartmunimji.dtos;

import com.smartmunimji.entities.WarrantyClaim;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WarrantyClaimResponseDto {
	private Integer claimId;
	private Integer productId;
	private String issueDescription;
	private WarrantyClaim.ClaimStatus status;
	private LocalDateTime createdAt;
	private String message;

	public Integer getClaimId() {
		return claimId;
	}

	public void setClaimId(Integer claimId) {
		this.claimId = claimId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public WarrantyClaim.ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(WarrantyClaim.ClaimStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
