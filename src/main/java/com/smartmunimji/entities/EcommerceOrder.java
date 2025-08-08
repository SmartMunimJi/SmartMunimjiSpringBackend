package com.smartmunimji.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class EcommerceOrder {

    @Id
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    // --- Getters and Setters ---

    public String getProductName() {
        return productName;
    }

    public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

