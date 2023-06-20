package com.example.phonecaresecure;

import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.Controller.CustomerController;
import com.example.phonecaresecure.Controller.OfferController;
import com.example.phonecaresecure.Model.*;
import com.example.phonecaresecure.Repository.*;
import com.example.phonecaresecure.Service.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    @InjectMocks
    UserService userService;
    @InjectMocks
    ProviderService providerService;
    @InjectMocks
    CustomerService customerService;
    @InjectMocks
    OrderService orderService;
    @InjectMocks
    ReviewService reviewService;
    @Mock
    UserRepository userRepository;
    @Mock
    ProviderRepository providerRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    OfferRepository offerRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    ReviewRepository reviewRepository;

    User user1, user2, user3;
    List<User> users;
    Provider provider1, provider2, provider3;
    List<Provider> providers;

    Customer customer1, customer2, customer3;
    List<Customer> customers;

    Order order1, order2, order3;
    List<Order> orders;
    Review review1,review2,review3;
    List<Review>reviews;

    @BeforeEach
    void setUp() {

        user1 = new User(null, "saud", "Saud1234", "CUSTOMER", null, null);
        user2 = new User(null, "Ali", "Saud1234", "CUSTOMER", null, null);
        user3 = new User(null, "moh", "Saud1234", "CUSTOMER", null, null);
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        provider1 = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw",0.0, user1, null, null);
        provider2 = new Provider(null, "sauda@dsd", "Ali", "01114544", "wqwsqw", 0.0,user2, null, null);
        provider3 = new Provider(null, "sauda@dsd", "ibra", "01114544", "wqwsqw", 0.0,user3, null, null);
        providers = new ArrayList<>();
        providers.add(provider1);
        providers.add(provider2);
        providers.add(provider3);

        customer1 = new Customer(null, "saud@gamil.com", "saud", "1420515", 15, user1, null);
        customer2 = new Customer(null, "saud@gamil.com", "ali", "1420515", 15, user2, null);
        customer3 = new Customer(null, "saud@gamil.com", "ibra", "1420515", 15, user3, null);
        customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

        order1 = new Order(null, "100054184", "NEW", LocalDate.now(), "fesswewdxw", customer1, null, null);
        order2 = new Order(null, "100054184", "NEW", LocalDate.now(), "fesswewdxw", customer2, null, null);
        order3 = new Order(null, "100054184", "NEW", LocalDate.now(), "fesswewdxw", customer3, null, null);
        orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        review1=new Review(null,"qwqsqwsqs",5,order1,provider1);
        review2=new Review(null,"qwqsqwsqs",5,order1,provider2);
        review3=new Review(null,"qwqsqwsqs",5,order1,provider3);
        reviews= new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);




    }


    @Test
    public void getAllUserTest() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> userList = userService.getAllUsers();
        Assertions.assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void getAllProvidersTest() {
        when(providerRepository.findAll()).thenReturn(providers);
        List<Provider> providerList = providerService.getAllProvider();
        Assertions.assertEquals(3, providerList.size());
        verify(providerRepository, times(1)).findAll();

    }

    @Test
    public void getAllCustomersTest() {
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> customerList = customerService.getAllCustomer();
        Assertions.assertEquals(3, customerList.size());
        verify(customerRepository, times(1)).findAll();

    }

    @Test
    public void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(orders);
        List<Order> orderList = orderService.getAllOrders();
        Assertions.assertEquals(3, orderList.size());
        verify(orderRepository, times(1)).findAll();

    }

    @Test
    public void getAllReviewsTest() {
        when(reviewRepository.findAll()).thenReturn(reviews);
        List<Review> reviewList = reviewService.getAllReviews();
        Assertions.assertEquals(3, reviewList.size());
        verify(reviewRepository, times(1)).findAll();

    }


}
