package com.example.phonecaresecure.Controller;

import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.DTO.OfferDTO;
import com.example.phonecaresecure.DTO.ProviderDTO;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Order;
import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/provider")
public class ProviderController {
    private final ProviderService providerService;

    @GetMapping("/get")
    public ResponseEntity getAllProviders(){
        List<Provider> providerList=providerService.getAllProvider();
        return ResponseEntity.status(200).body(providerList);
    }

    @GetMapping("/get-id")
    public ResponseEntity getProvider(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(providerService.getProvider(user.getId()));
    }


    @PostMapping("/add")
    public ResponseEntity addProvider(@Valid @RequestBody ProviderDTO dto){
        providerService.addProvider(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Provider added"));
    }

    @PutMapping("/update")
    public ResponseEntity updateProvider(@AuthenticationPrincipal User user,@Valid @RequestBody ProviderDTO providerDTO){
        providerService.updateProvider(providerDTO, user.getId());
        return ResponseEntity.status(200).body("Provider Updated");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User user){
        providerService.deleteProvider(user.getId());
        return ResponseEntity.status(200).body("Provider deleted");

    }
    //after security no need fot path variable
    @PostMapping("/make-over/{order_id}")
    public ResponseEntity makeOffer(@AuthenticationPrincipal User user,@PathVariable Integer order_id, @RequestBody @Valid OfferDTO offerDTO){

        providerService.makeOffer(user.getId(),order_id,offerDTO);
        return ResponseEntity.status(200).body("offer added");
    }
@PutMapping("/update-provider-info/{info}")
    public ResponseEntity<String> updateProviderInfo(@AuthenticationPrincipal User user, @PathVariable String info){
        providerService.updateProviderInfo(user.getId(), info);
    return ResponseEntity.status(200).body("Info updated");

}
@GetMapping("/get-orders")
    public ResponseEntity findOrders(@AuthenticationPrincipal User user){

    List<Order> orders= providerService.findOrders();
    return ResponseEntity.status(200).body(orders);

    }


@GetMapping("/get-offers")
    public ResponseEntity getAllProviderOffers(@AuthenticationPrincipal User user){
    List<Offer> offers=providerService.getAllProviderOffers(user.getId());
    return ResponseEntity.status(200).body(offers);
}

@PostMapping("/send-final/{offerId}")
public ResponseEntity sendFinalOffer(@AuthenticationPrincipal User user,@RequestBody OfferDTO offerDTO,@PathVariable Integer offerId){

        providerService.sendFinalOffer(user.getId(),offerDTO,offerId);
        return ResponseEntity.status(200).body("final offer send");
}

}
