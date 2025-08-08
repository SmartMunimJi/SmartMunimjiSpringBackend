//package com.smartmunimji.services;
//
//import com.smartmunimji.daos.AdminOfferRepository;
//import com.smartmunimji.entities.AdminOffer;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AdminOfferServiceImpl implements AdminOfferService {
//
//	@Autowired
//	private AdminOfferRepository offerRepository;
//
//	@Override
//	public AdminOffer createOffer(AdminOffer offer) {
//		return offerRepository.save(offer);
//	}
//
//	@Override
//	public List<AdminOffer> getAllOffers() {
//		return offerRepository.findAll();
//	}
//
//	@Override
//	public List<AdminOffer> getOffersBySellerId(int sellerId) {
//		return offerRepository.findBySellerId(sellerId);
//	}
//
//	@Override
//	public Optional<AdminOffer> getOfferById(int id) {
//		return offerRepository.findById(id);
//	}
//
//	@Override
//	public AdminOffer updateOffer(int id, AdminOffer updatedOffer) {
//		AdminOffer offer = offerRepository.findById(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
//
//		offer.setSellerId(updatedOffer.getSellerId());
//		offer.setOfferTitle(updatedOffer.getOfferTitle());
//		offer.setOfferDescription(updatedOffer.getOfferDescription());
//		offer.setStartDate(updatedOffer.getStartDate());
//		offer.setEndDate(updatedOffer.getEndDate());
//
//		return offerRepository.save(offer);
//	}
//
//	@Override
//	public void deleteOffer(int id) {
//		if (!offerRepository.existsById(id)) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
//		}
//		offerRepository.deleteById(id);
//	}
//}
