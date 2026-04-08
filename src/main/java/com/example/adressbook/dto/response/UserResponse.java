package com.example.adressbook.dto.response;

import java.time.LocalDate;

public record UserResponse(
    Long id,
    String name,
    String secondName,
    String patronymic,
    String phoneNumber,
    String emailAddress,
    LocalDate birthDate,
    AddressResponse address
) {

}
