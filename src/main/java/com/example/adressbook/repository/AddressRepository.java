package com.example.adressbook.repository;

import com.example.adressbook.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    //без оптимизации, возможно стоит добавить индексы, или возможно использовать criteria api
    @Query("SELECT a FROM Address a WHERE " +
            "LOWER(a.region) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.city) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.street) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.buildingNumber) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Address> searchAddresses(@Param("search") String search);
}
