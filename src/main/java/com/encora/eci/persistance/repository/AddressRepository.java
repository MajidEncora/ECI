package com.encora.eci.persistance.repository;

import com.encora.eci.persistance.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
