package com.example.phonecaresecure.Controller;

import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.DTO.CustomerDTO;
import com.example.phonecaresecure.DTO.OrderDTO;
import com.example.phonecaresecure.Model.Customer;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getAllCustomers(){
        List<Customer> CustomerList=customerService.getAllCustomer();
        return ResponseEntity.status(200).body(CustomerList);
    }


    @PostMapping("/add")
    public ResponseEntity addCustomer(@Valid @RequestBody CustomerDTO dto){
        customerService.addCustomer(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Customer added"));
    }

    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @Valid @RequestBody CustomerDTO customerDTO){
        customerService.updateCustomer(customerDTO, user.getId());
        return ResponseEntity.status(200).body("Customer Updated");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user){
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(200).body("Customer deleted");

    }

    @PostMapping("/makeorder")
    public ResponseEntity makeOrder(@AuthenticationPrincipal User user,@Valid @RequestBody OrderDTO dto){
        customerService.makeOrder(user.getId(),dto);
        return ResponseEntity.status(200).body("order made");
    }

@GetMapping("/get-all-offer/{orderId}")
  public ResponseEntity  getAllOffersToOrder(@AuthenticationPrincipal User user,@PathVariable Integer orderId){
        List<Offer> offers=customerService.getAllOffersToOrder(user.getId(), orderId);
    return ResponseEntity.status(200).body(offers);
}



@PutMapping("/acceptOffer/{offer_id}/{orderId}")
    public ResponseEntity<String> acceptOffer(@AuthenticationPrincipal User user,@PathVariable Integer offer_id,@PathVariable Integer orderId){
        customerService.acceptOffer(user.getId(), offer_id,orderId);
    return ResponseEntity.status(200).body("offer accepted");
}

@GetMapping("/get-id")
    public ResponseEntity getCustomerById(@AuthenticationPrincipal User user){
      Customer customer=  customerService.getCustomerById(user.getId());
    return ResponseEntity.status(200).body(customer);

}
@GetMapping("/get-info/{orderId}")
    public ResponseEntity getMyOrderInfo(@AuthenticationPrincipal User user, @PathVariable Integer orderId){
       String info= customerService.getMyOrderInfo(user.getId(),orderId);
    return ResponseEntity.status(200).body(info);
}





}
