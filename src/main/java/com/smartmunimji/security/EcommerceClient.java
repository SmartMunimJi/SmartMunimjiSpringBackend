package com.smartmunimji.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartmunimji.dtos.ProductDetailsDto;
import com.smartmunimji.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;

@Component
public class EcommerceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ecommerce.api.url}")
    private String validatePurchaseUrl;

    @Value("${ecommerce.api.key}")
    private String sellerApiKey;

    public ProductDetailsDto fetchProductDetailsFromSeller(String orderId, LocalDate purchaseDate, String phone, String jwtToken) {
        // Step 1: Create the HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", sellerApiKey);
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Step 2: Create the request body
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("order_id", orderId);
        body.put("date_of_purchase", purchaseDate.toString());
        body.put("phone", phone);

        // Step 3: Create the HttpEntity by combining headers and body
        HttpEntity<JsonNode> entity = new HttpEntity<>(body, headers);

        // Step 4: Make the API call using the entity
        ResponseEntity<JsonNode> response = restTemplate.exchange(validatePurchaseUrl, HttpMethod.POST, entity, JsonNode.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new CustomException("Failed to verify product with seller: " + response.getStatusCode());
        }

        JsonNode responseData = response.getBody().get("data");
        if (responseData == null) {
            throw new CustomException("Invalid response from seller: 'data' field missing");
        }
        
        JsonNode productData = responseData.get("product");
        if (productData == null) {
            throw new CustomException("Invalid response from seller: 'product' field missing in data");
        }

        // Map the JSON response to your DTO
        ProductDetailsDto dto = new ProductDetailsDto();
        dto.setProductName(productData.get("product_name").asText());
        dto.setSellerId(productData.get("seller_id").asInt());
        dto.setWarrantyPeriod(productData.get("warranty_period").asInt());
        dto.setPhone(productData.get("phone").asText());
        
        return dto;
    }
}