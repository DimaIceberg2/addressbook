package com.example.adressbook.controller.web;

import com.example.adressbook.dto.request.AddressCreateRequest;
import com.example.adressbook.dto.request.UserCreateRequest;
import com.example.adressbook.dto.response.AddressResponse;
import com.example.adressbook.dto.response.UserResponse;
import com.example.adressbook.service.AddressService;
import com.example.adressbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserWebController {
    private final UserService userService;
    private final AddressService addressService;

    @GetMapping
    public String listUsers(Model model) {
        List<UserResponse> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        UserCreateRequest userRequest = new UserCreateRequest(null, null, null, null, null, null, null);
        model.addAttribute("user", userRequest);

        List<AddressResponse> addresses = addressService.findAll();
        model.addAttribute("addresses", addresses);

        return "users/form";
    }

    @PostMapping("/save")
    public String saveUser(
            @RequestParam(required = false) Long userId,
            @RequestParam String name,
            @RequestParam(required = false) String secondName,
            @RequestParam(required = false) String patronymic,
            @RequestParam String phoneNumber,
            @RequestParam String emailAddress,
            @RequestParam String birthDate,
            @RequestParam(required = false) Long addressId,
            RedirectAttributes redirectAttributes) {

        try {
            LocalDate birthDateParsed = LocalDate.parse(birthDate);

            AddressCreateRequest addressRequest = null;
            if (addressId != null) {
                AddressResponse address = addressService.findById(addressId);
                if (address != null) {
                    addressRequest = new AddressCreateRequest(
                            address.region(),
                            address.city(),
                            address.street(),
                            address.buildingNumber(),
                            address.apartmentNumber()
                    );
                }
            }

            UserCreateRequest userRequest = new UserCreateRequest(
                    name, secondName, patronymic, phoneNumber, emailAddress, birthDateParsed, addressRequest
            );

            if (userId == null) {
                userService.create(userRequest);
                redirectAttributes.addFlashAttribute("success", "Пользователь успешно добавлен");
            }
            else {
                userService.update(userId, userRequest);
                redirectAttributes.addFlashAttribute("success", "Пользователь успешно обновлен");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении пользователя: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении пользователя: " + e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserResponse user = userService.findById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
                return "redirect:/users";
            }

            List<AddressResponse> addresses = addressService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("addresses", addresses);
            model.addAttribute("userId", id);

            return "users/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при загрузке пользователя: " + e.getMessage());
            return "redirect:/users";
        }
    }
}
