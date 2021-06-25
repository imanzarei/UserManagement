package com.surena.UserManagement.repository;

import com.surena.UserManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM User u WHERE u.username = :username")
    int deleteByUsername(@Param("username") String username);

     User findByUsernameAndPassword(String username,String password);
     User findByUsername(String username);

}
