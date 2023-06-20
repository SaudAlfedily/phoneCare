package com.example.phonecaresecure.Service;


import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.DTO.CustomerDTO;
import com.example.phonecaresecure.DTO.OrderDTO;
import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Order;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;
    private final ReviewRepository reviewRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        return customerRepository.findCustomerById(customerId);
    }

    public void addCustomer(CustomerDTO dto) {
        //User user = userRepository.findUserById(dto.getUser_id());
//        if (user == null) {
//
//            throw new ApiException("User not found");
//
//
//        }
        User user = new User(null, dto.getUsername(), dto.getPassword(), "CUSTOMER", null, null);
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepository.save(user);
        Customer customer = new Customer(null, dto.getMail(), dto.getName(), dto.getPhone(), 0, user, null);
        customerRepository.save(customer);
    }


    public void updateCustomer(CustomerDTO dto, Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {

            throw new ApiException("User not found");


        }
        Customer oldCustomer = customerRepository.findCustomerById(id);
        if (oldCustomer == null) {

            throw new ApiException("Customer not found");


        }
        user.setUsername(dto.getUsername());
        String hash = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hash);
        oldCustomer.setMail(dto.getMail());
        oldCustomer.setName(dto.getName());
        oldCustomer.setPhone(dto.getPhone());
        userRepository.save(user);
        customerRepository.save(oldCustomer);
    }

    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new ApiException("customer not found");
        }

        customerRepository.delete(customer);
    }

    // جيب كوستمر  id من السكيرتي بدل dto
    public void makeOrder(Integer customerId, OrderDTO dto) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }

        Order order = new Order(null, dto.getPhoneinfo(), "NEW", LocalDate.now(), dto.getProblemdescription(), customer, null, null);
        orderRepository.save(order);
    }

    // from السكيرتي
    public List<Offer> getAllOffersToOrder(Integer customer_id, Integer orderId) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new ApiException("order not found");
        }
        if (customer_id != order.getCustomer().getId())
            throw new ApiException("not authorized");
        List<Offer> offers = offerRepository.findOffersByOrder(order);
        if (offers.isEmpty()) {

            throw new ApiException("no offer found");


        }
        return offers;
    }


    //after acceptance
    public String getMyOrderInfo(Integer customerId, Integer orderId) {
        Order order = orderRepository.findOrderById(orderId);
        Customer customer = customerRepository.findCustomerById(customerId);
        if (order == null)
            throw new ApiException("order not found");
        if (order.getOrderStatus().equals("NEW"))
            throw new ApiException("no offer has been accepted yet");
        if (!(order.getCustomer().getId() == customerId))
            throw new ApiException("not authorized");
        if (order.getOffers().iterator().next().getEstemateddeadline().isBefore(LocalDate.now()) && order.getOrderStatus().equals("ACCEPTED")) {
            return "Hi " + customer.getName() +
                    "\nOrder ID:" + order.getId() +
                    "\nOrder Status:" + order.getOrderStatus() +
                    "\nOrder Creation Date:" + order.getCreatedAt() +
                    "\nOrder Problem Description:" + order.getProblemdescription() +
                    "\nNotice!! the order deadline:" + order.getOffers().iterator().next().getEstemateddeadline() +
                    "\nYour provider is:" + order.getOffers().iterator().next().getProvider().getName() +
                    "\nYour provider phone is:" + order.getOffers().iterator().next().getProvider().getPhone() +
                    " has been reached, Customer can reject offer now.";
        }
        if (order.getOffers().iterator().next().getOfferStatus().equals("FINAL")) {
            return "Hi " + customer.getName() +
                    "\nOrder ID:" + order.getId() +
                    "\nOrder Status:final offer has been submitted" +
                    "\nOrder Creation Date:" + order.getCreatedAt() +
                    "\nOrder Problem Description:" + order.getProblemdescription() +
                    "\n*FINAL PRICE:" + order.getOffers().iterator().next().getPrice() +
                    "\nYour provider is:" + order.getOffers().iterator().next().getProvider().getName() +
                    "\nYour provider phone is:" + order.getOffers().iterator().next().getProvider().getPhone() +
                    "\n**NOTE THAT YOU CAN REJECT THE OFFER**";

        }
        return "Hi " + customer.getName() +
                "\nOrder ID:" + order.getId() +
                "\nOrder Status:" + order.getOrderStatus() +
                "\nOrder Creation Date:" + order.getCreatedAt() +
                "\nOrder Problem Description:" + order.getProblemdescription() +
                "\nYour provider is:" + order.getOffers().iterator().next().getProvider().getName() +
                "\nYour provider phone is:" + order.getOffers().iterator().next().getProvider().getPhone();

    }

    //we need to change offer status to accepted
    //we need to chang order status to binding
    public void acceptOffer(Integer customer_id, Integer offer_id, Integer orderId) {
        Customer customer = customerRepository.findCustomerById(customer_id);

        if (customer == null) {
            throw new ApiException("customer not found");
        }

        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new ApiException("order not found");
        }
        if (order.getCustomer().getId() != customer_id)
            throw new ApiException("not authorized");

        Offer offer = offerRepository.findOfferById(offer_id);
        if (offer == null) {

            throw new ApiException("no offer found");


        }
        if (offer.getOrder().getId() != orderId) {
            throw new ApiException("this  offer is for other order");

        }

        // first offer
        if (offer.getOfferStatus().equals("FIRST") && order.getOrderStatus().equals("NEW")) {
            order.setOrderStatus("ACCEPTED");
            offer.setOfferStatus("PENDING");


            offerRepository.save(offer);
            orderRepository.save(order);
            if (order.getOffers().size() > 1) {
                // Remove all other offers after acceptance of the first one
                for (Offer o : order.getOffers()) {
                    if (!offer.equals(o)) {
                        order.getOffers().remove(o);
                        offerRepository.delete(o);
                        orderRepository.save(order);

                    }
                }
            }


        }

//final offer
        if (offer.getOfferStatus().equals("FINAL") && order.getOrderStatus().equals("ACCEPTED")) {
            order.setOrderStatus("FINAL_ACCEPTED");//FACCEPTED mean the final offer is accepted
            offer.setOfferStatus("FINAL_PENDING");//FBNINDING mean the final offer is accepted
            orderRepository.save(order);
            offerRepository.save(offer);
        }

    }


}