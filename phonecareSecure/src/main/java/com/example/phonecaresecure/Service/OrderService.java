package com.example.phonecaresecure.Service;

import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.DTO.OrderDTO;
import com.example.phonecaresecure.DTO.ReviewDTO;
import com.example.phonecaresecure.Model.*;
import com.example.phonecaresecure.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OfferRepository offerRepository;
    private final ReviewRepository reviewRepository;
    private final ProviderRepository providerRepository;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public void addOrder(Integer customerId,OrderDTO dto) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {

            throw new ApiException("customer not found");


        }

        Order order = new Order(null, dto.getPhoneinfo(), "NEW", LocalDate.now(), dto.getProblemdescription(), customer, null, null);
        orderRepository.save(order);
    }


    public void updateOrder(OrderDTO dto, Integer id) {
        Order oldOrder = orderRepository.findOrderById(id);
        if (oldOrder == null) {

            throw new ApiException("Order not found");


        }
        // i changed the dto

        oldOrder.setPhoneinfo(dto.getPhoneinfo());
        oldOrder.setProblemdescription(dto.getProblemdescription());

        orderRepository.save(oldOrder);
    }

    public void deleteOrder(Integer id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null) {
            throw new ApiException("order not found");
        }

        orderRepository.delete(order);
    }

    //مع السكيرتي نسحبه اتو الايدي من اليوزر نجيب الاوردر حقه لانه 1-1
    // ممكن نسوي dto ونسحبها ريكوست بودي بدل باث فاربل
    public void updateDescription(Integer customerId,Integer order_id, String desc) {
        Order oldorder = orderRepository.findOrderById(order_id);
        if (oldorder == null) {
            throw new ApiException("order not found");
        }
        if (!oldorder.getOrderStatus().equals("NEW")) {
            throw new ApiException("order is in progress" + oldorder.getOrderStatus());

        }
        if (customerId != oldorder.getCustomer().getId())
            throw new ApiException("not authorized");
        oldorder.setProblemdescription(desc);
        orderRepository.save(oldorder);
    }

    public void completeOrder(Integer customerId, Integer orderId, ReviewDTO reviewDTO) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("order not found");
        if (order.getCustomer().getId() != customerId)
            throw new ApiException("order not found");
        if (!order.getOrderStatus().equals("FINAL_ACCEPTED"))
            throw new ApiException("order is in " + order.getOrderStatus());
        if (!order.getOffers().iterator().next().getOfferStatus().equals("FINAL_PENDING"))
            throw new ApiException("offer is in " + order.getOffers().iterator().next().getOfferStatus());
        order.setOrderStatus("COMPLETED");
        orderRepository.save(order);
        Provider provider = order.getOffers().iterator().next().getProvider();
        //////////// ****************************************************
        Review review = new Review(null, reviewDTO.getReview(), reviewDTO.getStars(), order, provider);
        reviewRepository.save(review);
        calculateRate(provider.getId());

    }
    public void calculateRate(Integer providerId) {
        Provider provider = providerRepository.findProviderById(providerId);
        if (provider == null) {
            throw new ApiException("provider not found");
        }

        List<Review> reviews = reviewRepository.findReviewsByProvider(provider);
        if (reviews.isEmpty()) {
            throw new ApiException("no review  found");

        }
        Double totalRate = 0.0;
        for (Review review : reviews) {
            totalRate = totalRate + review.getStars();
        }
        totalRate = (totalRate / reviews.size()) ;
        provider.setTotalRate(totalRate);
        providerRepository.save(provider);

    }


    public void rejectOffer(Integer customerId, Integer orderId) {
        Order order = orderRepository.findOrderById(orderId);
        Offer offer = offerRepository.findOfferByOrder(order);
        if (order == null)
            throw new ApiException("order not found");
        if (order.getCustomer().getId() != customerId)
            throw new ApiException("not Authorized");
        if (offer.getOfferStatus().equals("FINAL_ACCEPTED"))
            throw new ApiException("Customer cant reject offer after accepting the final offer");
        if (!offer.getOfferStatus().equals("FINAL"))
            throw new ApiException("Customer can only reject after final offer is received");
        order.setOrderStatus("NEW");
        order.setOffers(null);
        offerRepository.delete(offer);
        orderRepository.save(order);
    }

}


