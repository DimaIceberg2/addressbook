package com.example.adressbook.service;

import com.example.adressbook.entity.Address;
import com.example.adressbook.dto.request.AddressCreateRequest;
import com.example.adressbook.dto.response.AddressResponse;
import com.example.adressbook.mapper.AddressMapper;
import com.example.adressbook.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional(readOnly = true)
    public List<AddressResponse> findAll() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AddressResponse findById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toResponse)
                .orElse(null);
    }

    @Transactional
    public AddressResponse create(AddressCreateRequest request) {
        log.info("Creating address: {}", request);
        Address address = addressMapper.toEntity(request);
        address = addressRepository.save(address);
        return addressMapper.toResponse(address);
    }

    @Transactional
    public AddressResponse update(Long id, AddressCreateRequest request) {
        log.info("Updating address with id: {}", id);

        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));

        existingAddress.setRegion(request.region());
        existingAddress.setCity(request.city());
        existingAddress.setStreet(request.street());
        existingAddress.setBuildingNumber(request.buildingNumber());
        existingAddress.setApartmentNumber(request.apartmentNumber());

        existingAddress = addressRepository.save(existingAddress);
        return addressMapper.toResponse(existingAddress);
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting address with id: {}", id);
        addressRepository.deleteById(id);
    }
}
