package com.smartmunimji.services;

import java.util.List;
import java.util.Optional;

import com.smartmunimji.entities.AdminOffer;

public interface AdminOfferService {
	AdminOffer createOffer(AdminOffer offer);

	List<AdminOffer> getAllOffers();

	List<AdminOffer> getOffersBySellerId(int sellerId);

	Optional<AdminOffer> getOfferById(int id);

	AdminOffer updateOffer(int id, AdminOffer updatedOffer);

	void deleteOffer(int id);
}
