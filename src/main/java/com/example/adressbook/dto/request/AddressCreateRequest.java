package com.example.adressbook.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AddressCreateRequest(
    @NotBlank(message = "Область обязательна для заполнения")
    String region,

    @NotBlank(message = "Город обязателен для заполнения")
    String city,

    @NotBlank(message = "Улица обязательная для заполнения")
    String street,

    @NotBlank(message = "Дом обязателен к заполнению")
    String buildingNumber,

    @Positive(message = "Номер квартиры должен быть положительным")
    Integer apartmentNumber
    ) {
}
