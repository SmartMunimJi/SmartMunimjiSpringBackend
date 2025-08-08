package com.smartmunimji.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {
	private Integer sellerId;
	private String name;
	private String description;
	private BigDecimal price;
	private Integer warrantyPeriod;
	private String base64ProductImage;

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
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

	public String getBase64ProductImage() {
		return base64ProductImage;
	}

	public void setBase64ProductImage(String base64ProductImage) {
		this.base64ProductImage = base64ProductImage;
	}
}
