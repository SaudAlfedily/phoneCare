package com.example.phonecaresecure;

import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.Controller.CustomerController;
import com.example.phonecaresecure.Controller.OfferController;
import com.example.phonecaresecure.Controller.OrderController;
import com.example.phonecaresecure.Model.*;
import com.example.phonecaresecure.Service.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OrderController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class OrderControllerTest {

    @MockBean
    ProviderService providerService;
    @MockBean
    UserService userService;
    @MockBean
    CustomerService customerService;
    @MockBean
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;

    Customer customer1, customer2, customer3;
    ApiResponse apiResponse;

    List<Customer> customers, customerList;
    User user1, user2, user3;
    List<User> users, userList;

    Order order1, order2, order3;
    List<Order> orders, orderList;

    @BeforeEach
    void setUp() {
        user1 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        user2 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        user3 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        users = Arrays.asList(user1);
        userList = Arrays.asList(user2);

        customer1 = new Customer(null, "saud@gamil.com", "saud1", "1420515", 15, user1, null);
        customer2 = new Customer(null, "saud1@gamil.com", "saud2", "1420515", 15, user2, null);
        customer3 = new Customer(null, "saud2@gamil.com", "saud3", "1420515", 15, user3, null);

        customers = Arrays.asList(customer1);
        customerList = Arrays.asList(customer2);

        order1 = new Order(null, "100054184", "NEW", LocalDate.now(), "fesswewdxw", customer1, null, null);
        order2 = new Order(null, "100054184", "NEW", LocalDate.now(), "fesswewdxw", customer2, null, null);
        order3 = new Order(null, "100054184", "NEW", LocalDate.now(), "fesswewdxw", customer3, null, null);
        orders = Arrays.asList(order1);
        orderList = Arrays.asList(order2);


    }

    @Test
    void GetAllOrders() throws Exception {
        Mockito.when(orderService.getAllOrders()).thenReturn(orders);
        mockMvc.perform(get("/api/v1/order/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneinfo").value(order1.getPhoneinfo()));
    }

}
