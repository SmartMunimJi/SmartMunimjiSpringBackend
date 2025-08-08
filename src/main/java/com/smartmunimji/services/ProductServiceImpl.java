//package com.smartmunimji.services;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.smartmunimji.daos.ProductDao;
//import com.smartmunimji.entities.Product;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//	@Autowired
//	private ProductDao productRepository;
//
//	@Override
//	public Product createProduct(Product product) {
//		return productRepository.save(product);
//	}
//
//	@Override
//	public List<Product> getAllProducts() {
//		return productRepository.findAll();
//	}
//
//	@Override
//	public List<Product> getProductsBySellerId(int sellerId) {
//		return productRepository.findBySellerId(sellerId);
//	}
//
//	@Override
//	public Product updateProduct(int id, Product updatedProduct) {
//		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
//
//		product.setSellerId(updatedProduct.getSeller());
//		product.setName(updatedProduct.getName());
//		product.setDescription(updatedProduct.getDescription());
//		product.setPrice(updatedProduct.getPrice());
//		product.setWarrantyPeriod(updatedProduct.getWarrantyPeriod());
//		product.setProductImage(updatedProduct.getProductImage());
//
//		return productRepository.save(product);
//	}
//
//	@Override
//	public void deleteProduct(int id) {
//		productRepository.deleteById(id);
//	}
//}
