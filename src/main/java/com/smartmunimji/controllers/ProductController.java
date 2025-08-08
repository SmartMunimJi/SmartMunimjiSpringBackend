//package com.smartmunimji.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import com.smartmunimji.daos.*;
//import com.smartmunimji.dtos.ProductRegistrationRequest;
//import com.smartmunimji.entities.*;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/sm/customer")
//public class ProductController {
//
//    @Autowired
//    private CustomerDao customerDao;
//
//    @Autowired
//    private SellerDao sellerDao;
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Autowired
//    private OrderDao orderDao;
//
//    @Autowired
//    private RegisteredProductDao registeredProductDao;
//
//    @PostMapping("/register-product")
//    public ResponseEntity<String> registerProduct(@RequestBody ProductRegistrationRequest request) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated()) {
//            return ResponseEntity.status(401).body("Unauthorized");
//        }
//
//        String email = auth.getName();
//        Optional<Customer> customerOpt = customerDao.findByEmail(email);
//        if (customerOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body("Customer not found");
//        }
//        Integer customerId = customerOpt.get().getId();
//
//        // Validate
//        if (request.getOrderId() == null || request.getDateOfPurchase() == null || request.getShopName() == null || request.getProductId() == null) {
//            return ResponseEntity.badRequest().body("Missing required fields");
//        }
//
//        // Find seller
//        Optional<Seller> sellerOpt = sellerDao.findByShopname(request.getShopName());
//        if (sellerOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body("Invalid shop name");
//        }
//        Seller seller = sellerOpt.get();
//
//        // Find product
//        Optional<Product> productOpt = productDao.findById(request.getProductId());
//        if (productOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body("Invalid product ID");
//        }
//        Product product = productOpt.get();
//
//        // Check duplicate registration
//        Optional<RegisteredProduct> existing = registeredProductDao.findByOrderIdAndCustomerId(request.getOrderId(), customerId);
//        if (existing.isPresent()) {
//            return ResponseEntity.badRequest().body("Product already registered");
//        }
//
//        // Calculate warranty expiry
//        LocalDate expiry = request.getDateOfPurchase();
//        Integer months = product.getWarrantyPeriod();
//        if (months != null && months > 0) {
//            expiry = expiry.plusMonths(months);
//        }
//
//        // Save registration
//        RegisteredProduct rp = new RegisteredProduct();
//        rp.setCustomerId(customerId);
//        rp.setSellerId(seller.getId());
//        rp.setProductId(product.getId());
//        rp.setOrderId(request.getOrderId());
//        rp.setPurchaseDate(request.getDateOfPurchase());
//        rp.setWarrantyExpiryDate(expiry);
//        rp.setStatus(RegisteredProduct.Status.active);
//        rp.setProductImage(null); 
//
//        registeredProductDao.save(rp);
//        return ResponseEntity.ok("Product registered successfully");
//    }
//
//}
