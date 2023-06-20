package com.example.phonecaresecure.Repository;

import com.example.phonecaresecure.Model.Offer;
import com.example.phonecaresecure.Model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProviderRepository extends JpaRepository<Provider,Integer> {
     Provider findProviderById(Integer id);

     Provider findProviderByOffersIsContaining(Offer offer);

}

