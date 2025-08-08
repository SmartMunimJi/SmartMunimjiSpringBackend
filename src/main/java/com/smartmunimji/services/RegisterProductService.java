package com.smartmunimji.services;

import com.smartmunimji.daos.CustomerDao;
import com.smartmunimji.daos.RegisteredProductDao;
import com.smartmunimji.daos.SellerDao;
import com.smartmunimji.dtos.ProductRegistrationRequest;
import com.smartmunimji.entities.Customer;
import com.smartmunimji.entities.RegisteredProduct;
import com.smartmunimji.entities.RegisteredProduct.RegistrationStatus;
import com.smartmunimji.entities.Seller;
import com.smartmunimji.ecommerce.daos.OrderDao;
import com.smartmunimji.ecommerce.entities.Order;
import com.smartmunimji.exceptions.CustomException;
import com.smartmunimji.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RegisterProductService {

	@Autowired
	private RegisteredProductDao registeredProductDao;

	@Autowired
	private OrderDao ecommerceOrderDao;

	// No longer need to autowire a ProductDao for ecommerce_db as sellerId comes
	// from the request

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private SellerDao sellerDao;

	@Transactional("primaryTransactionManager")
	public String registerProduct(String customerToken, ProductRegistrationRequest request) {
		// Step 1: Extract customer email from JWT and find customer from the
		// project_work DB
		String customerEmail = jwtUtil.extractUsername(customerToken);
		Customer customer = customerDao.findByEmail(customerEmail)
				.orElseThrow(() -> new CustomException("Customer not found with email: " + customerEmail));

		// Step 2: Find the Order from the 'ecommerce_db' for validation
		Optional<Order> orderOptional = ecommerceOrderDao.findByOrderIdAndDateOfPurchase(request.getOrderId(),
				request.getPurchaseDate());

		if (orderOptional.isEmpty()) {
			throw new CustomException(
					"Order not found or purchase date mismatch for order ID: " + request.getOrderId());
		}
		Order order = orderOptional.get();

		// Step 3: Check if already registered
		boolean alreadyRegistered = registeredProductDao.existsByCustomerAndOrderId(customer, request.getOrderId());
		if (alreadyRegistered) {
			throw new CustomException("This product is already registered with this order ID for this customer.");
		}

		// Step 4: Find the Seller entity from the project_work DB using the sellerId
		// from the request
		if (request.getSellerId() == null) {
			throw new CustomException("Seller ID is required for registration.");
		}
		Seller seller = sellerDao.findById(request.getSellerId()).orElseThrow(
				() -> new CustomException("Seller not found in project_work DB with ID: " + request.getSellerId()));

		// Step 5: Create a new RegisteredProduct entity
		RegisteredProduct registeredProduct = new RegisteredProduct();

		registeredProduct.setCustomer(customer);
		registeredProduct.setSeller(seller);
		registeredProduct.setOrderId(order.getOrderId());
		registeredProduct.setProductId(order.getProductId());
		registeredProduct.setPurchaseDate(order.getDateOfPurchase());
		registeredProduct.setRegistrationDate(LocalDate.now());
		registeredProduct.setStatus(RegistrationStatus.active);

		// Step 6: Calculate Warranty Expiry Date (defaulting to 12 months for this
		// example)
		registeredProduct.setWarrantyExpiryDate(order.getDateOfPurchase().plusMonths(12));

		// Step 7: Save the new entity
		registeredProductDao.save(registeredProduct);

		return "Product registered successfully with ID: " + registeredProduct.getId();
	}
}

//package com.smartmunimji.services;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.smartmunimji.daos.CustomerDao;
//import com.smartmunimji.daos.ProductDao;
//import com.smartmunimji.daos.RegisteredProductDao;
//import com.smartmunimji.dtos.ProductRegistrationRequest;
//import com.smartmunimji.entities.Customer;
//import com.smartmunimji.entities.Product;
//import com.smartmunimji.entities.RegisteredProduct;
//import com.smartmunimji.entities.RegisteredProduct.RegistrationStatus;
//import com.smartmunimji.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class RegisterProductService {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private CustomerDao customerDao;
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Autowired
//    private RegisteredProductDao registeredProductDao;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public String registerProduct(String token, String authKey, ProductRegistrationRequest request) {
//        // Step 1: Extract email from JWT
//        String customerEmail = jwtUtil.extractUsername(token);
//        Customer customer = customerDao.findByEmail(customerEmail)
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        // Step 2: Call Express API and validate ownership
//        JsonNode order = fetchOrderFromExpressApi(request.getOrderId(), authKey, token);
//
//        String orderEmail = order.get("email").asText();
//        if (!orderEmail.equalsIgnoreCase(customerEmail)) {
//            throw new RuntimeException("Order does not belong to authenticated customer");
//        }
//
//        // Step 3: Get Product from your DB
//        Integer productId = order.get("product_id").asInt();
//        Optional<Product> productOpt = productDao.findById(productId);
//        if (productOpt.isEmpty()) {
//            throw new RuntimeException("Product not found in local DB");
//        }
//
//        // Step 4: Save registered product
//        RegisteredProduct registeredProduct = new RegisteredProduct();
//        registeredProduct.setCustomer(customer);
//        registeredProduct.setProduct(productOpt.get());
//        registeredProduct.setPurchaseDate(request.getPurchaseDate());
//        registeredProduct.setStatus(RegistrationStatus.active);
//        registeredProduct.setRegistrationDate(LocalDateTime.now());
//
//        registeredProductDao.save(registeredProduct);
//
//        return "Product registered successfully";
//    }
//
//    // ✅ FIXED: Added Authorization header with Bearer <token>
//    public JsonNode fetchOrderFromExpressApi(Integer orderId, String authKey, String jwtToken) {
//        String url = "http://localhost:4000/order/" + orderId;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("x-smj-auth", authKey);
//        headers.set("Authorization", "Bearer " + jwtToken);  // ✅ Pass JWT to Express
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<JsonNode> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                entity,
//                JsonNode.class
//        );
//
//        return response.getBody();
//    }
//}

//package com.smartmunimji.services;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.smartmunimji.daos.CustomerDao;
//import com.smartmunimji.daos.ProductDao;
//import com.smartmunimji.daos.RegisteredProductDao;
//import com.smartmunimji.dtos.OrderDto;
//import com.smartmunimji.dtos.ProductRegistrationRequest;
//import com.smartmunimji.entities.Customer;
//import com.smartmunimji.entities.Product;
//import com.smartmunimji.entities.RegisteredProduct;
//import com.smartmunimji.entities.RegisteredProduct.RegistrationStatus;
//import com.smartmunimji.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@Service
//public class RegisterProductService {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private CustomerDao customerDao;
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Autowired
//    private RegisteredProductDao registeredProductDao;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public String registerProduct(String token, String authKey, ProductRegistrationRequest request) {
//        String customerEmail = jwtUtil.extractUsername(token);
//        Customer customer = customerDao.findByEmail(customerEmail)
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        JsonNode order = fetchOrderDetailsFromExpress(request.getOrderId(), authKey);
//
//        // Validate order belongs to logged-in customer
//        String orderEmail = order.get("email").asText();
//        if (!orderEmail.equalsIgnoreCase(customerEmail)) {
//            throw new RuntimeException("Order does not belong to authenticated customer");
//        }
//
//        Integer productId = order.get("product_id").asInt();
//        Optional<Product> productOpt = productDao.findById(productId);
//        if (productOpt.isEmpty()) {
//            throw new RuntimeException("Product not found in local DB");
//        }
//
//        RegisteredProduct registeredProduct = new RegisteredProduct();
//        registeredProduct.setCustomer(customer);
//        registeredProduct.setProduct(productOpt.get());
//        registeredProduct.setPurchaseDate(request.getPurchaseDate());
//        registeredProduct.setStatus(RegistrationStatus.active);
//
//        registeredProductDao.save(registeredProduct);
//
//        return "Product registered successfully";
//    }
//    
//  
//
//    public JsonNode fetchOrderDetailsFromExpress(Integer orderId, String authKey) {
//        String expressApiUrl = "https://e-commerce-production-4390.up.railway.app/order/" + orderId;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("x-smj-auth", authKey);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<JsonNode> response = restTemplate.exchange(
//                expressApiUrl,
//                HttpMethod.GET,
//                entity,
//                JsonNode.class
//        );
//
//        return response.getBody();
//    }
//    } 

//    public OrderDto fetchOrderDetailsFromExpress(Integer orderId, String token) {
//        String url = "https://e-commerce-production-4390.up.railway.app/api/orders/" + orderId;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token); // ✅ Set JWT token for external API
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<OrderDto> response = restTemplate.exchange(
//            url,
//            HttpMethod.GET,
//            entity,
//            OrderDto.class
//        );
//
//        return response.getBody();
//    }

//}

//package com.smartmunimji.services;
//
//import com.smartmunimji.daos.*;
//import com.smartmunimji.dtos.ProductRegistrationRequest;
//import com.smartmunimji.entities.*;
//import com.smartmunimji.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class RegisterProductService {
//
//	@Autowired
//	private JwtUtil jwtUtil;
//	@Autowired
//	private CustomerDao customerDao;
//	@Autowired
//	private SellerDao sellerDao;
//	@Autowired
//	private ProductDao productDao;
//	@Autowired
//	private RegisteredProductDao registeredProductDao;
//
//	public String registerProduct(String token, ProductRegistrationRequest request) {
//		token = token.substring(7); // Remove "Bearer "
//		int customerId = jwtUtil.extractUserId(token);
//
//		Customer customer = customerDao.findById(customerId)
//				.orElseThrow(() -> new RuntimeException("Customer not found"));
//
//		Product product = productDao.findById(request.getProductId())
//				.orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));
//
//		Seller seller = sellerDao.findBySellername(request.getSellerName())
//				.orElseThrow(() -> new RuntimeException("Seller not found with name: " + request.getSellerName()));
//
//		RegisteredProduct rp = new RegisteredProduct();
//		rp.setCustomer(customer);
//		rp.setProduct(product);
//		rp.setSeller(product.getSeller());
//		rp.setOrderId(request.getOrderId());
//		rp.setPurchaseDate(request.getPurchaseDate());
//		rp.setRegistrationDate(LocalDateTime.now());
//		rp.setStatus(RegisteredProduct.RegistrationStatus.active);
//		rp.setWarrantyExpiryDate(request.getPurchaseDate().plusMonths(product.getWarrantyPeriod()));
//
//		registeredProductDao.save(rp);
//
//		return "Product registered successfully";
//	}
//}
