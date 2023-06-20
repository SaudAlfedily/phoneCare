package com.example.phonecaresecure.Repository;

import com.example.phonecaresecure.Model.Provider;
import com.example.phonecaresecure.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findReviewById(Integer id);

    List<Review> findReviewsByProvider(Provider provider);
}