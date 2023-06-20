package com.example.phonecaresecure.Service;
import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.DTO.ReviewDTO;
import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Order;
import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Model.Review;
import com.example.phonecaresecure.Repository.OfferRepository;
import com.example.phonecaresecure.Repository.OrderRepository;
import com.example.phonecaresecure.Repository.ProviderRepository;
import com.example.phonecaresecure.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProviderRepository providerRepository;
    private final OfferRepository offerRepository;

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }


    public void addReview(Integer orderId, ReviewDTO dto) {
        Order order=orderRepository.findOrderById(orderId);
        if(order==null){

            throw new ApiException("order not found");


        }
        if (!order.getOrderStatus().equals("COMPLETED")){

            throw new ApiException("you are not allowed to make review until order completed");

        }

        Provider provider = providerRepository.findProviderById(order.getOffers().iterator().next().getProvider().getId());
        if (provider==null)
        {

            throw new ApiException("provider not found");

        }
        Offer offer=offerRepository.findOfferByOrder(order);
        Provider provider1 =providerRepository.findProviderByOffersIsContaining(offer);
        if(!(provider1.getId()==provider.getId())){
            throw new ApiException("you did not get served by this provider");

        }

        Review review= new Review(null, dto.getReview(), dto.getStars(), order,provider);

        reviewRepository.save(review);
    }


    public void updateReview( ReviewDTO dto,Integer id) {
        Order order=orderRepository.findOrderById(id);
        if(order==null){

            throw new ApiException("order not found");


        }
        Review oldReview =reviewRepository.findReviewById(id);
        if(oldReview==null){

            throw new ApiException("Review not found");


        }
       oldReview.setReview(dto.getReview());
     oldReview.setStars(dto.getStars());
        reviewRepository.save(oldReview);
    }

    public void deleteReview(Integer id){
        Review review= reviewRepository.findReviewById(id);
        if(review==null){
            throw new ApiException("Review not found");
        }

        reviewRepository.delete(review);
    }

    public List<Review> getAllCustomersReview(Integer provider_id){
        Provider provider=providerRepository.findProviderById(provider_id);
        if (provider==null){
            throw new ApiException("provider not found");
        }
        List<Review> reviews =reviewRepository.findReviewsByProvider(provider);
        return reviews;
    }


}
