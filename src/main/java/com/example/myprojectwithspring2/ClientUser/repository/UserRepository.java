package com.example.myprojectwithspring2.ClientUser.repository;

import com.example.myprojectwithspring2.ClientUser.entity_class.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{

}
