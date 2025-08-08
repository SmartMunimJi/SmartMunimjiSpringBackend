//package com.smartmunimji.services;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.smartmunimji.daos.RegisteredProductDao;
//import com.smartmunimji.entities.RegisteredProduct;
//
//@Service
//public class RegisteredProductServiceImpl implements RegisteredProductService {
//
//    @Autowired
//    private RegisteredProductDao registeredProductRepository;
//
//    @Override
//    public RegisteredProduct registerProduct(RegisteredProduct product) {
//        return registeredProductRepository.save(product);
//    }
//
//    @Override
//    public List<RegisteredProduct> getAllRegisteredProducts() {
//        return registeredProductRepository.findAll();
//    }
//
//    @Override
//    public List<RegisteredProduct> getRegisteredProductsByCustomerId(int customerId) {
//        return registeredProductRepository.findByCustomerId(customerId);
//    }
//
//    @Override
//    public List<RegisteredProduct> getRegisteredProductsBySellerId(int sellerId) {
//        return registeredProductRepository.findBySellerId(sellerId);
//    }
//    
//    @Override
//    public RegisteredProduct updateRegisteredProduct(int id, RegisteredProduct updated) {
//        RegisteredProduct existing = registeredProductRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("Registered product not found"));
//
//        existing.setOrderId(updated.getOrderId());
//        existing.setPurchaseDate(updated.getPurchaseDate());
//        existing.setWarrantyExpiryDate(updated.getWarrantyExpiryDate());
//        existing.setStatus(updated.getStatus());
//
//        return registeredProductRepository.save(existing);
//    }
//
//    @Override
//    public void deleteRegisteredProduct(int id) {
//    	registeredProductRepository.deleteById(id);
//    }
//}
