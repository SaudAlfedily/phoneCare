package com.example.phonecaresecure.Repository;

import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer> {
    Offer findOfferById(Integer id);

    Offer findOfferByOrderId(Integer id);

    List<Offer> findOffersByOrder(Order order);

    List<Offer> findOffersByProviderId(Integer providerId);

    //??
    @Modifying
    @Transactional
    @Query("SELECT m from Offer m where m.estemateddeadline <?1")
    List<Offer> findOfferALLExpiredOffers(LocalDate date);

    Offer findOfferByOrder(Order order);



    @Modifying
    @Transactional
    @Query(value = "DELETE from Offer s where s.order=?1")
    void deleteOfferByOffer(Order order);



}
