package com.smartmunimji.controllers;

import com.smartmunimji.dtos.ProductRegistrationRequest;
import com.smartmunimji.services.RegisterProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sm/customer")
public class RegisteredProductController {

    @Autowired
    private RegisterProductService registerProductService;

    @Value("${ecommerce.api.key}")
    private String sellerApiKey;

    @PostMapping("/register-product")
    public ResponseEntity<?> registerProduct(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProductRegistrationRequest request
    ) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String message = registerProductService.registerProduct(token, request);
            return ResponseEntity.ok().body(message);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

//package com.smartmunimji.controllers;
//
//import com.smartmunimji.dtos.ProductRegistrationRequest;
//import com.smartmunimji.services.RegisterProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/sm/customer")
//public class RegisteredProductController {
//
//	@Autowired
//	private RegisterProductService registerProductService;
//
//	@PostMapping("/register-product")
//	public ResponseEntity<?> registerProduct(@RequestHeader("Authorization") String token,
//			@RequestHeader("x-smj-auth") String authKey, @RequestBody ProductRegistrationRequest request) {
//		String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
//		String result = registerProductService.registerProduct(jwt, authKey, request);
//		return ResponseEntity.ok(result);
//	}
//}
