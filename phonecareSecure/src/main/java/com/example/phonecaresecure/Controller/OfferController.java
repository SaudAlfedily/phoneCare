package com.example.phonecaresecure.Controller;


import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.DTO.UpdateOfferDTO;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.User;
import com.example.phonecaresecure.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/offer")
public class OfferController {
    private final OfferService offerService;


    @GetMapping("/get")
    public ResponseEntity getAllOffers(){
        List<Offer> offerList=offerService.getAlOffers();
        return ResponseEntity.status(200).body(offerList);
    }


    @PostMapping("/add")
    public ResponseEntity addOffer(@AuthenticationPrincipal User user, @Valid @RequestBody Offer offer){
        offerService.addOffer(offer);
        return ResponseEntity.status(200).body(new ApiResponse("Offer added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOffer(@Valid @RequestBody UpdateOfferDTO offer, @PathVariable Integer id){
        offerService.updateOffer(offer,id);
        return ResponseEntity.status(200).body("Offer Updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        offerService.deleteOffer(id);
        return ResponseEntity.status(200).body("Offer deleted");

    }
@GetMapping("/cancel-offer/{offerId}")
 public ResponseEntity<String> cancelOffer(@AuthenticationPrincipal User user, @PathVariable Integer offerId){
       offerService.cancelOffer(user.getId(), offerId);
    return ResponseEntity.status(200).body("Offer deleted");

}
@GetMapping("/offer-completed/{offerId}")
    public ResponseEntity offerCompleted(@AuthenticationPrincipal User user, @PathVariable Integer offerId){
        offerService.offerCompleted(user.getId(), offerId);
    return ResponseEntity.status(200).body("Offer Completed");

}


}
