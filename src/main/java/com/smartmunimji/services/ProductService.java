package com.smartmunimji.services;

import com.smartmunimji.daos.RegisteredProductDao;
import com.smartmunimji.ecommerce.daos.ProductDao;
import com.smartmunimji.ecommerce.entities.Product;
import com.smartmunimji.entities.RegisteredProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private RegisteredProductDao registeredProductDao;

    @Autowired
    private ProductDao productDao;

    public List<Product> getProductsBySellerId(int sellerId) {
        List<RegisteredProduct> registeredProducts = registeredProductDao.findBySellerId(sellerId);

        List<Integer> productIds = registeredProducts.stream()
                                                      .map(RegisteredProduct::getProductId)
                                                      .collect(Collectors.toList());

        return productDao.findAllById(productIds);
    }
}


//package com.smartmunimji.services;
//
//import java.util.List;
//
//import com.smartmunimji.ecommerce.daos.ProductDao;
//import com.smartmunimji.ecommerce.entities.Product;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ProductService {
//
//	@Autowired
//	private ProductDao productDao;
//
//	public Optional<Product> getProductById(Integer productId) {
//		return productDao.findById(productId);
//	}
//
//	public Optional<Product> getProductByName(String name) {
//		return productDao.findByName(name);
//	}
//}
