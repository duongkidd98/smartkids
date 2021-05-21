package com.smartosc.training.repositories;

import com.smartosc.training.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "SELECT a FROM Address a INNER JOIN a.users u WHERE u.userId = :userId")
    List<Address> findByUsers_UserId(@Param("userId") Integer userId);
}
