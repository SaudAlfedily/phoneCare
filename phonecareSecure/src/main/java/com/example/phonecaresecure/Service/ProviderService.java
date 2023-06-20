package com.example.phonecaresecure.Service;


import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.DTO.OfferDTO;
import com.example.phonecaresecure.DTO.ProviderDTO;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Order;
import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Repository.OfferRepository;
import com.example.phonecaresecure.Repository.OrderRepository;
import com.example.phonecaresecure.Repository.ProviderRepository;
import com.example.phonecaresecure.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProviderService {

    private final ProviderRepository providerRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;


    public List<Provider> getAllProvider() {
        return providerRepository.findAll();
    }


    //get user al info
    public Provider getProvider(Integer id) {
        Provider provider = providerRepository.findProviderById(id);
        if (provider == null) {
            throw new ApiException("provider not found");
        }

        return provider;
    }

    public void addProvider(ProviderDTO dto) {
        //User user = userRepository.findUserById(dto.getUser_id());
//        if (user == null) {
//
//            throw new ApiException("User not found");
//
//
//        }
        User user = new User(null, dto.getUsername(), dto.getPassword(), "PROVIDER",null,null);
        String hash= new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepository.save(user);
        Provider provider = new Provider(null, dto.getMail(), dto.getName(), dto.getPhone(), dto.getInfo(), 0.0, user, null,null);
        providerRepository.save(provider);
    }


    public void updateProvider(ProviderDTO dto, Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Provider oldProvider = providerRepository.findProviderById(id);
        if (oldProvider == null) {
            throw new ApiException("Provider not found");
        }
        user.setUsername(dto.getUsername());
        String hash= new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hash);
        oldProvider.setInfo(dto.getInfo());
        oldProvider.setMail(dto.getMail());
        oldProvider.setName(dto.getName());
        oldProvider.setPhone(dto.getPhone());
        userRepository.save(user);
        providerRepository.save(oldProvider);
    }

    public void deleteProvider(Integer id) {
        Provider provider = providerRepository.findProviderById(id);
        if (provider == null) {
            throw new ApiException("Provider not found");
        }

        providerRepository.delete(provider);
    }

    public void updateProviderInfo(Integer id, String info) {
        Provider provider = providerRepository.findProviderById(id);
        if (provider == null)
            throw new ApiException("Provider not found");
        provider.setInfo(info);
        providerRepository.save(provider);
    }

    public List<Order> findOrders() {
        List<Order> orders = orderRepository.findOrdersByOrderStatus("NEW");
        if (orders.isEmpty())
            throw new ApiException("no orders found, try at a later time");
        return orders;
    }

    //after acceptance by user check if the same provider can check others?!!


    public List<Offer> getAllProviderOffers(Integer providerId) {
        List<Offer> offers = offerRepository.findOffersByProviderId(providerId);
        if (offers.isEmpty() || offers == null)
            throw new ApiException("Provider has no offers");
        return offers;
    }


    public void makeOffer(Integer providerId,Integer order_id, OfferDTO offerDTO) {


        Provider provider = providerRepository.findProviderById(providerId);


        if (provider == null) {

            throw new ApiException("Provider not found");


        }
        Order order = orderRepository.findOrderById(order_id);
        if (order == null) {

            throw new ApiException("Order not found");


        }
        for (Offer o: order.getOffers()){
            if (o.getProvider().getId() == provider.getId())
                throw new ApiException("cant make more than one offer");
        }
        if (!order.getOrderStatus().equals("NEW"))
            throw new ApiException("Order has been taken");
        Offer offer = new Offer(null, offerDTO.getPrice(), "FIRST", LocalDate.now(), offerDTO.getEstemateddeadline(), null, provider, order);

        offerRepository.save(offer);


    }
    public void sendFinalOffer(Integer providerId,OfferDTO offerDTO,Integer offerId){
        Provider provider = providerRepository.findProviderById(providerId);
        Offer offer= offerRepository.findOfferById(offerId);
        if (offer==null)
            throw new ApiException("offer not found");

        if (offer.getProvider().getId()!=provider.getId()){

            throw new ApiException("unauthorized action");

        }
        if (offer.getOfferStatus().equals("PENDING")){
            offer.setPrice(offerDTO.getPrice());
            offer.setCreatedAt(LocalDate.now());
            offer.setEstemateddeadline(offerDTO.getEstemateddeadline());
            offer.setOfferStatus("FINAL");
            offerRepository.save(offer);

        }
    }

}




