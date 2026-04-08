package com.example.adressbook.dto.response;

public record AddressResponse(
    Long id,
    String region,
    String city,
    String street,
    String buildingNumber,
    Integer apartmentNumber
    ) {
}
