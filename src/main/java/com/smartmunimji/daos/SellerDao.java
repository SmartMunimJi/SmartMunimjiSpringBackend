package com.smartmunimji.daos;

import com.smartmunimji.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerDao extends JpaRepository<Seller, Integer> {
	Optional<Seller> findBySellersemail(String sellersemail);

	Optional<Seller> findByShopname(String shopname);

	Optional<Seller> findBySellername(String sellername);
}
