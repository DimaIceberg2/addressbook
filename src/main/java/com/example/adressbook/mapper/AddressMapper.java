package com.example.adressbook.mapper;

import com.example.adressbook.entity.Address;
import com.example.adressbook.dto.request.AddressCreateRequest;
import com.example.adressbook.dto.response.AddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressCreateRequest dto);
    AddressResponse toResponse(Address address);
}
