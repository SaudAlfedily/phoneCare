package com.example.phonecaresecure;

import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.Controller.CustomerController;
import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Service.CustomerService;
import com.example.phonecaresecure.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;
    Customer customer1,customer2,customer3;
    ApiResponse apiResponse;

    List<Customer> customers,customerList;
    User user1,user2,user3;
    List<User> users,userList;


    @BeforeEach
    void setUp() {
        user1 = new User(null, "saud", "Saud1234", "CUSTOMER", null, null);
        user2 = new User(null, "ali", "Saud1234", "CUSTOMER", null, null);
        user3 = new User(null, "ibra", "Saud1234", "CUSTOMER", null, null);
        users= Arrays.asList(user1);
        userList=Arrays.asList(user2);

        customer1 = new Customer(null, "saud@gamil.com", "saud1", "1420515", 15, user1, null);
        customer2 = new Customer(null, "saud1@gamil.com", "saud2", "1420515", 15, user2, null);
        customer3 = new Customer(null, "saud2@gamil.com", "saud3", "1420515", 15, user3, null);

        customers= Arrays.asList(customer1);
        customerList=Arrays.asList(customer2);


    }

    @Test
    void GetAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomer()).thenReturn(customers);
        mockMvc.perform(get("/api/v1/customer/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mail").value(customer1.getMail()));
    }


//    @Test
//    public void testAddCustomer() throws  Exception {
//        mockMvc.perform(post("/api/v1/customer/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content( new ObjectMapper().writeValueAsString(customer1)))
//                .andExpect(status().isOk());
//
//    }


}
