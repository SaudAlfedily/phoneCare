package com.example.phonecaresecure;



import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.Controller.CustomerController;
import com.example.phonecaresecure.Controller.OfferController;
import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Service.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OfferController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class OfferControllerTest {

    @MockBean
    ProviderService providerService;
    @MockBean
    UserService userService;
    @MockBean
    CustomerService customerService;
    @MockBean
    OrderService orderService;
    @MockBean
    OfferService offerService;

    @Autowired
    MockMvc mockMvc;

    Provider provider1, provider2, provider3;
    ApiResponse apiResponse;

    List<Provider> providers, providerList;
    User user1, user2, user3;
    List<User> users, userList;

    Offer offer1, offer2, offer3;
    List<Offer> offers, offerList;


    @BeforeEach
    void setUp() {
        user1 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        user2 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        user3 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        users = Arrays.asList(user1);
        userList = Arrays.asList(user2);

        provider1 = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw", user1, null, null);
        provider2 = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw", user2, null, null);
        provider3 = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw", user3, null, null);

        providers = Arrays.asList(provider1);
        providerList = Arrays.asList(provider2);

        offer1 = new Offer(null, 50, "FIRST", LocalDate.now(), LocalDate.now(), "dwdwwd", provider1, null);
        offer2 = new Offer(null, 50, "FIRST", LocalDate.now(), LocalDate.now(), "dwdwwd", provider2, null);
        offer3 = new Offer(null, 50, "FIRST", LocalDate.now(), LocalDate.now(), "dwdwwd", provider3, null);
        offers = Arrays.asList(offer1);
        offerList=Arrays.asList(offer2);
    }

    @Test
    void GetAllOrders() throws Exception {
        Mockito.when(offerService.getAlOffers()).thenReturn(offers);
        mockMvc.perform(get("/api/v1/offer/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reason").value(offer1.getReason()));
    }

}
