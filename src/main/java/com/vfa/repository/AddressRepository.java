package com.vfa.repository;

import com.vfa.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address findByBuilding(String email);

    int countByStreet(String firstName);

    @Query("SELECT a FROM Address a WHERE a.id = ?1")
    Optional<Address> getByAddressId(int id);

    @Query("SELECT a FROM Address a WHERE a.city = ?1")
    Optional<List<Address>> getByAddressCity(String name);
}
