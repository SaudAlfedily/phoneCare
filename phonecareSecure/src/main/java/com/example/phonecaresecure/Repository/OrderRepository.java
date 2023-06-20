package com.example.phonecaresecure.Repository;

import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findOrderById(Integer id);
    Order findOrderByOffers(Offer offer);
    Order findOrdersByCustomer(Customer customer);

    List<Order> findOrdersByOrderStatus(String status);

    Order findOrderByOffersContaining(Offer offer);

}
