package com.example.phonecaresecure.Service;

import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.DTO.UpdateOfferDTO;
import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Order;
import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Repository.CustomerRepository;
import com.example.phonecaresecure.Repository.OfferRepository;
import com.example.phonecaresecure.Repository.OrderRepository;
import com.example.phonecaresecure.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final ProviderRepository providerRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    // just CRUD
    public List<Offer> getAlOffers() {
        return offerRepository.findAll();
    }

    //best to use in provider class
    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public void updateOffer(UpdateOfferDTO offer, Integer id) {
        Offer oldOffer = offerRepository.findOfferById(id);
        if (oldOffer == null) {
            throw new ApiException("Offer not found");
        }
        if ( !oldOffer.getEstemateddeadline().isBefore(LocalDate.now()))
            throw new ApiException("deadline has not been reached");

        updateOfferStatus(id);
        oldOffer.setEstemateddeadline(offer.getEstemateddeadline());
        oldOffer.setPrice(offer.getPrice());
        oldOffer.setReason(offer.getReason());
        offerRepository.save(oldOffer);
    }

    public void deleteOffer(Integer id) {
        Offer offer = offerRepository.findOfferById(id);
        if (offer == null) {
            throw new ApiException("Offer not found");
        }

        offerRepository.delete(offer);
    }
    // end of CRUD

    public void cancelOffer(Integer providerId, Integer offerId) {
        Offer offer = offerRepository.findOfferById(offerId);
        Provider provider = providerRepository.findProviderById(providerId);
        if (offer == null)
            throw new ApiException("offer does not exist");
        if (offer.getProvider().getId() != provider.getId())
            throw new ApiException("not authorized");
        if (!offer.getOfferStatus().equals("FIRST"))
            throw new ApiException("offers cant be canceled after acceptance");
        offerRepository.delete(offer);
    }

    //************************************************************************************
    public void updateOfferStatus(Integer offerId) {
        Offer offer = offerRepository.findOfferById(offerId);
        if (offer == null)
            throw new ApiException("offer not found");
        if (offer.getOfferStatus().equals("PENDING")||offer.getOfferStatus().equals("FINAL_PENDING"))
            offer.setOfferStatus("FINAL");
        offerRepository.save(offer);
    }

    public void offerCompleted(Integer providerId, Integer offerId) {
        Offer offer = offerRepository.findOfferById(offerId);
        Provider provider = providerRepository.findProviderById(providerId);

        if (offer == null)
            throw new ApiException("offer not found");
        if (provider.getOffers().isEmpty())
            throw new ApiException(provider.getName() + " has no offers");
        if (offer.getProvider().getId() != provider.getId())
            throw new ApiException("not authorized");
        if (offer.getOfferStatus().equals("FIRST"))
            throw new ApiException("offer is still in FIRST status");
        if (offer.getOfferStatus().equals("PENDING"))
            throw new ApiException("offer is still in PENDING status");
        if (!offer.getOfferStatus().equals("FINAL_PENDING"))
            throw new ApiException("offer is not in FINAL_PENDING status");
        if (!offer.getOrder().getOrderStatus().equals("COMPLETED"))
            throw new ApiException("Order has not been completed from customer");
        offer.setOfferStatus("COMPLETED");

        Order order = orderRepository.findOrderByOffers(offer);
        Customer customer = customerRepository.findCustomerByOrdersContains(order);
        customer.setLoyaltypoints(offer.getPrice()*0.10);


        offerRepository.save(offer);
    }

    //public void

}
