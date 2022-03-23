package com.encora.eci.service;

import com.encora.eci.persistance.model.Address;
import com.encora.eci.persistance.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AddressServiceTest {

    @Test
    void getEmployeeAddress() {
        //Given
        int id = 1;
        AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
        AddressService addressService = new AddressService(addressRepository);
        Address address = new Address("street", "123", "33445", "Mexico", "Jalisco");

        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        //When
        Optional<Address> employeeAddress = addressService.getEmployeeAddress(id);

        //Then
        assertThat(employeeAddress).isPresent();
        assertThat(employeeAddress).get().isEqualTo(address);
    }
}