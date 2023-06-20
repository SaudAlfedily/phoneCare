package com.example.phonecaresecure.Repository;

import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findCustomerById(Integer id);
    Customer findCustomerByOrdersContains(Order order);
}
