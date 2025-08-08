package com.smartmunimji.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "warranty_claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyClaim {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "registered_product_id", nullable = false)
	private RegisteredProduct registeredProduct;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Seller seller;

	@Column(name = "claim_reason", columnDefinition = "text")
	private String claimReason;

	@Column(name = "issue_description", nullable = false)
	private String issueDescription;

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "claim_status", columnDefinition = "ENUM('Pending','Accepted','Rejected') DEFAULT 'Pending'")
	private ClaimStatus claimStatus = ClaimStatus.Pending;

	public enum ClaimStatus {
		Pending, Accepted, Rejected
	}

	@Column(name = "claim_date", insertable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime claimDate;

	private LocalDateTime responseDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegisteredProduct getRegisteredProduct() {
		return registeredProduct;
	}

	public void setRegisteredProduct(RegisteredProduct registeredProduct) {
		this.registeredProduct = registeredProduct;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getClaimReason() {
		return claimReason;
	}

	public void setClaimReason(String claimReason) {
		this.claimReason = claimReason;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public LocalDateTime getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(LocalDateTime claimDate) {
		this.claimDate = claimDate;
	}

	public LocalDateTime getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(LocalDateTime responseDate) {
		this.responseDate = responseDate;
	}
}
