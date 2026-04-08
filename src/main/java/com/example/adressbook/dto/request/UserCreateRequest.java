package com.example.adressbook.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
public record UserCreateRequest(
    @NotBlank(message = "Имя обязательно для заполнения")
    @Size(min = 2, message = "Имя должно содержать более 1 символа")
    String name,

    String secondName,

    String patronymic,

    @NotBlank(message = "Телефон обязателен для заполнения")
    @Pattern(regexp = "^\\+?\\d{10,15}?", message = "Телефон должен содрежать только цифры")
    String phoneNumber,

    @NotBlank(message = "Email обязательен для заполнения")
    @Email(message = "Введите корректный email адрес")
    String emailAddress,

    @NotNull(message = "Дата рождения обязательна для заполнения")
    @Past(message = "Дата рождения должна быть в прошеднем времени")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate,

    @Valid
    AddressCreateRequest address
) {

}
