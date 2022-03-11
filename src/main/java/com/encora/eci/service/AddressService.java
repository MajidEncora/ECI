package com.encora.eci.service;

import com.encora.eci.persistance.model.Address;
import com.encora.eci.persistance.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private static final Logger log = LoggerFactory.getLogger(PositionService.class);
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<Address> getEmployeeAddress(Integer id){
        return addressRepository.findById(id);
    }
}
