package com.example.phonecaresecure.Repository;

import com.example.phonecaresecure.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    User findUserById(Integer id);
    User findUserByUsername(String username);

}
