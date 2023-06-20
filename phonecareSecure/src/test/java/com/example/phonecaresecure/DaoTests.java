package com.example.phonecaresecure;

import com.example.phonecaresecure.Model.*;
import com.example.phonecaresecure.Repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import java.time.LocalDate;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoTests {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProviderRepository providerRepository;

    User user,user1;
    Customer customer;
    Order order1, order2, order3;
    Provider provider;
    Set<Order> orders;
    Review review;

    @BeforeEach
    void setUp() {
        user = new User(null, "saud", "Saud1234", "CUSTOMER", null, null);
        user1 = new User(null, "Ali", "Saud1234", "CUSTOMER", null, null);
        provider = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw",0.0, user, null, null);
        customer = new Customer(null, "saud@gamil.com", "saud", "1420515", 15, user1, null);
        order1=new Order(null,"100054184","NEW", LocalDate.now(),"fesswewdxw",customer,null,null);
        review=new Review(null,"qwqsqwsqs",5,order1,provider);



    }
//    @Test
//    public void findAllByMyUserTesting(){
//        orderRepository.save(order1);
//        orderRepository.save(order2);
//        orderRepository.save(order3);
//        List<Order> order= orderRepository.findOrderById(myUser);
//        Assertions.assertThat(todos.get(0).getMyUser().getId()).isEqualTo(myUser.getId());
//    }

    @Test
    public void findUserByIdTEST() {
        userRepository.save(user);
        User usert = userRepository.findUserById(user.getId());
        Assertions.assertThat(usert).isEqualTo(user);
    }

    @Test
    public void findProviderByIdTEST() {
        providerRepository.save(provider);
        Provider provider1 = providerRepository.findProviderById(provider.getId());
        Assertions.assertThat(provider1).isEqualTo(provider);
    }

    @Test
    public void findCustomerByIdTEST() {
        customerRepository.save(customer);
        Customer customer1 = customerRepository.findCustomerById(customer.getId());
        Assertions.assertThat(customer1).isEqualTo(customer);
    }


    @Test
    public void findOrderByIdTEST() {
        customerRepository.save(customer);
        orderRepository.save(order1);
        Order order2 = orderRepository.findOrderById(order1.getId());
        Assertions.assertThat(order2).isEqualTo(order1);
    }

    @Test
    public void findReviewByIdTEST() {
        customerRepository.save(customer);
        providerRepository.save(provider);
        orderRepository.save(order1);
        reviewRepository.save(review);
        Review review1 = reviewRepository.findReviewById(review.getId());
        Assertions.assertThat(review1).isEqualTo(review);
    }




}




