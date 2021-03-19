package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(nativeQuery=true , value="select id from user where email = :searchEmail and password = :searchPassword")
    public List<Integer> findUser(@Param("searchEmail") String searchEmail, @Param("searchPassword") String searchPassword);
}
