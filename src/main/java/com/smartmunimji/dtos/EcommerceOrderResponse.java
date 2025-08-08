package com.smartmunimji.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EcommerceOrderResponse {
	private String orderId;
	private Long userId;
	private String email;
	private ProductData product;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ProductData getProduct() {
		return product;
	}

	public void setProduct(ProductData product) {
		this.product = product;
	}

	@Data
	public static class ProductData {
		private Long id;
		private String name;
		private String description;
		private BigDecimal price;
		private Integer warrantyPeriod;
		private String image;

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

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getWarrantyPeriod() {
			return warrantyPeriod;
		}

		public void setWarrantyPeriod(Integer warrantyPeriod) {
			this.warrantyPeriod = warrantyPeriod;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}
	}
}
