package com.example.phonecaresecure;

import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.Controller.CustomerController;
import com.example.phonecaresecure.Controller.OfferController;
import com.example.phonecaresecure.Controller.ProviderController;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProviderController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProviderControllerTest {

    @MockBean
    ProviderService providerService;
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    Provider provider1,provider2,provider3;
    ApiResponse apiResponse;

    List<Provider> providers,providerList;
    User user1,user2,user3;
    List<User> users,userList;


    @BeforeEach
    void setUp() {
        user1 = new User(null, "saud", "Saud1234", "CUSTOMER", null, null);
        user2 = new User(null, "Ali", "Saud1234", "CUSTOMER", null, null);
        user3 = new User(null, "ibra", "Saud1234", "CUSTOMER", null, null);
        users= Arrays.asList(user1);
        userList=Arrays.asList(user2);

        provider1 = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw",0.0, user1, null, null);
        provider2 = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw", 0.0,user2, null, null);
        provider3 = new Provider(null, "sauda@dsd", "saud", "01114544", "wqwsqw",0.0, user3, null, null);

        providers= Arrays.asList(provider1);
        providerList=Arrays.asList(provider2);



    }

    @Test
    void GetAllProviders() throws Exception {
        Mockito.when(providerService.getAllProvider()).thenReturn(providers);
        mockMvc.perform(get("/api/v1/provider/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(provider1.getName()));
    }
}
