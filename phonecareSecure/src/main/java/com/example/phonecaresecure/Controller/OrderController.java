package com.example.phonecaresecure.Controller;

import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.DTO.OrderDTO;
import com.example.phonecaresecure.DTO.ReviewDTO;
import com.example.phonecaresecure.Model.Order;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity getAllOrders(){
        List<Order> OrderList=orderService.getAllOrders();
        return ResponseEntity.status(200).body(OrderList);
    }


    @PostMapping("/add/{customerId}")
    public ResponseEntity addOrder(@PathVariable Integer customerId,@Valid @RequestBody OrderDTO dto){
        orderService.addOrder(customerId,dto);
        return ResponseEntity.status(200).body(new ApiResponse("Order added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOrder(@Valid @RequestBody OrderDTO orderDTO, @PathVariable Integer id){
        orderService.updateOrder(orderDTO,id);
        return ResponseEntity.status(200).body("Order Updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOrder(@PathVariable Integer id){
        orderService.deleteOrder(id);
        return ResponseEntity.status(200).body("Order deleted");

    }
    @PutMapping("/update-desc/{order_id}/{desc}")
    public ResponseEntity<String> updateDescription(@AuthenticationPrincipal User user, @PathVariable Integer order_id, @PathVariable String desc){
        orderService.updateDescription(user.getId(),order_id,desc);
        return ResponseEntity.status(200).body("description updated");

    }
    @GetMapping("/complete-order/{orderId}")
    public ResponseEntity completeOrder(@AuthenticationPrincipal User user, @PathVariable Integer orderId,@Valid @RequestBody ReviewDTO reviewDTO){
        orderService.completeOrder(user.getId(),orderId,reviewDTO);
        return ResponseEntity.status(200).body(" Order completed");
    }
@GetMapping("/reject-offer/{orderId}")
    public ResponseEntity<String> rejectOffer(@AuthenticationPrincipal User user, @PathVariable Integer orderId){
        orderService.rejectOffer(user.getId(),orderId);

    return ResponseEntity.status(200).body(" Order rejected");
}

}
