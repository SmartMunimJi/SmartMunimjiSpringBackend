package com.smartmunimji.services;

import com.smartmunimji.daos.RegisteredProductDao;
import com.smartmunimji.daos.WarrantyClaimDao;
import com.smartmunimji.dtos.WarrantyClaimRequest;
import com.smartmunimji.entities.RegisteredProduct;
import com.smartmunimji.entities.WarrantyClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WarrantyClaimService {

    @Autowired
    private RegisteredProductDao registeredProductDao;

    @Autowired
    private WarrantyClaimDao warrantyClaimDao;

    public String claimWarranty(WarrantyClaimRequest request) {
        // 1. Fetch registered product
        RegisteredProduct registeredProduct = registeredProductDao.findById(request.getRegisteredProductId())
                .orElseThrow(() -> new RuntimeException("Registered product not found with ID: " + request.getRegisteredProductId()));

        // 2. Check warranty expiry
        if (registeredProduct.getWarrantyExpiryDate() != null &&
            registeredProduct.getWarrantyExpiryDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new RuntimeException("Warranty period has expired.");
        }

        // 3. Create new warranty claim
        WarrantyClaim claim = new WarrantyClaim();
        claim.setRegisteredProduct(registeredProduct);
        claim.setCustomer(registeredProduct.getCustomer());
        claim.setSeller(registeredProduct.getSeller());
        claim.setClaimReason(request.getReason());
        claim.setIssueDescription(request.getDescription());
        claim.setClaimStatus(WarrantyClaim.ClaimStatus.Pending); // default value, but explicit
        claim.setClaimDate(LocalDateTime.now()); // overwrites DB default, safe for app logic

        warrantyClaimDao.save(claim);

        return "Warranty claim submitted successfully.";
    }
}



//package com.smartmunimji.services;
//
//import com.smartmunimji.daos.*;
//import com.smartmunimji.dtos.WarrantyClaimRequest;
//import com.smartmunimji.entities.*;
//import com.smartmunimji.entities.WarrantyClaim.ClaimStatus;
//import com.smartmunimji.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class WarrantyClaimService {
//
//	@Autowired
//	private JwtUtil jwtUtil;
//	@Autowired
//	private CustomerDao customerDao;
//	@Autowired
//	private RegisteredProductDao registeredProductDao;
//	@Autowired
//	private WarrantyClaimDao warrantyClaimDao;
//
//	public String submitClaim(String token, WarrantyClaimRequest request) {
//		token = token.substring(7);
//		int customerId = jwtUtil.extractUserId(token);
//
//		Customer customer = customerDao.findById(customerId)
//				.orElseThrow(() -> new RuntimeException("Customer not found"));
//
//		RegisteredProduct registeredProduct = registeredProductDao.findById(request.getRegisteredProductId())
//				.orElseThrow(() -> new RuntimeException("Registered product not found"));
//
//		if (registeredProduct.getCustomer().getId() != customer.getId()) {
//			throw new RuntimeException("You are not authorized to claim this product");
//		}
//
//		WarrantyClaim claim = new WarrantyClaim();
//		claim.setCustomer(customer);
//		claim.setRegisteredProduct(registeredProduct);
//		claim.setSeller(registeredProduct.getSeller());
//		claim.setClaimReason(request.getReason());
//		claim.setClaimDate(LocalDateTime.now());
//		claim.setClaimStatus(ClaimStatus.Pending);
//		claim.setIssueDescription(request.getDescription());
//
//		warrantyClaimDao.save(claim);
//
//		return "Claim submitted successfully";
//	}
//}
