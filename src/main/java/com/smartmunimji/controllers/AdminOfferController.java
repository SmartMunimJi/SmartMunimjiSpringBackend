//package com.smartmunimji.controllers;
//
//import com.smartmunimji.entities.AdminOffer;
//import com.smartmunimji.services.AdminOfferService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/sm/seller/offers")
//public class AdminOfferController {
//
//	@Autowired
//	private AdminOfferService adminOfferService;
//
//	@PostMapping
//	public ResponseEntity<AdminOffer> createOffer(@RequestBody AdminOffer offer) {
//		AdminOffer createdOffer = adminOfferService.createOffer(offer);
//		return ResponseEntity.ok(createdOffer);
//	}
//
//	@GetMapping
//	public ResponseEntity<List<AdminOffer>> getAllOffers() {
//		List<AdminOffer> offers = adminOfferService.getAllOffers();
//		return ResponseEntity.ok(offers);
//	}
//
//	@GetMapping("/seller/{sellerId}")
//	public ResponseEntity<List<AdminOffer>> getOffersBySellerId(@PathVariable int sellerId) {
//		List<AdminOffer> offers = adminOfferService.getOffersBySellerId(sellerId);
//		return ResponseEntity.ok(offers);
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<AdminOffer> updateOffer(@PathVariable int id, @RequestBody AdminOffer offer) {
//		AdminOffer updatedOffer = adminOfferService.updateOffer(id, offer);
//		return ResponseEntity.ok(updatedOffer);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteOffer(@PathVariable int id) {
//		adminOfferService.deleteOffer(id);
//		return ResponseEntity.noContent().build();
//	}
//}
