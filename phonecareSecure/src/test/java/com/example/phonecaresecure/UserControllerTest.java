package com.example.phonecaresecure;
import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.Controller.CustomerController;
import com.example.phonecaresecure.Controller.OfferController;
import com.example.phonecaresecure.Controller.UserController;
import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Model.User;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UserControllerTest {
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    User user1,user2,user3;
    ApiResponse apiResponse;

    List<User> users,userList;

    @BeforeEach
    void setUp() {
        user1 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        user2 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        user3 = new User(null, "saud", "1234", "CUSTOMER", null, null);
        users= Arrays.asList(user1);
        userList=Arrays.asList(user2);


    }


//    @Test
//    public void testDeleteUser() throws Exception{
//        mockMvc.perform(delete("/api/v1/user/delete/{id}",user1.getId()))
//                .andExpect(status().isOk());
//    }


    @Test
    public void GetAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/v1/user/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("saud"));
    }

}
