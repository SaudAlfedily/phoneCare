package com.example.phonecaresecure.Controller;

import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.ApiResponse.ApiResponse;
import com.example.phonecaresecure.DTO.ReviewDTO;
import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Model.Review;
import com.example.phonecaresecure.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    private  final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity getAllReviews(){
        List<Review> reviewList=reviewService.getAllReviews();
        return ResponseEntity.status(200).body(reviewList);
    }


    @PostMapping("/add/{provider_id}")
    public ResponseEntity addReview(@PathVariable Integer provider_id,@Valid @RequestBody ReviewDTO dto){
        reviewService.addReview(provider_id,dto);
        return ResponseEntity.status(200).body(new ApiResponse("Review added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@Valid @RequestBody ReviewDTO reviewDTO, @PathVariable Integer id){
        reviewService.updateReview(reviewDTO,id);
        return ResponseEntity.status(200).body("Review Updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id){
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body("Review deleted");

    }
@GetMapping("/get-provider/{provider_id}")
    public ResponseEntity<Object> getAllCustomersReview(@PathVariable Integer provider_id){
    List<Review> reviews=  reviewService.getAllCustomersReview(provider_id);
    return ResponseEntity.status(200).body(reviews);
}



}
