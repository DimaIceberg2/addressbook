package com.example.adressbook.controller.web;

import com.example.adressbook.dto.request.AddressCreateRequest;
import com.example.adressbook.dto.response.AddressResponse;
import com.example.adressbook.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressWebController {
    private final AddressService addressService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        AddressCreateRequest addressRequest = new AddressCreateRequest(null, null, null, null, null);
        model.addAttribute("address", addressRequest);
        return "addresses/form";
    }

    @PostMapping("/save")
    public String saveAddress(
            @RequestParam(required = false) Long addressId,
            @RequestParam String region,
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam String buildingNumber,
            @RequestParam(required = false) Integer apartmentNumber,
            RedirectAttributes redirectAttributes) {

        try {
            AddressCreateRequest addressRequest = new AddressCreateRequest(
                    region, city, street, buildingNumber, apartmentNumber
            );

            if (addressId != null) {
                addressService.update(addressId, addressRequest);
                redirectAttributes.addFlashAttribute("success", "Адрес успешно обновлен");
            } else {
                addressService.create(addressRequest);
                redirectAttributes.addFlashAttribute("success", "Адрес успешно добавлен");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при сохранении адреса: " + e.getMessage());
        }
        return "redirect:/addresses";
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            addressService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Адрес успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении адреса: " + e.getMessage());
        }
        return "redirect:/addresses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            AddressResponse address = addressService.findById(id);
            if (address == null) {
                redirectAttributes.addFlashAttribute("error", "Адрес не найден");
                return "redirect:/addresses";
            }

            model.addAttribute("address", address);
            model.addAttribute("addressId", id);

            return "addresses/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при загрузке адреса: " + e.getMessage());
            return "redirect:/addresses";
        }
    }

    @GetMapping
    public String listAddresses(@RequestParam(required = false) String search, Model model) {
        List<AddressResponse> addresses;
        if (search != null && !search.trim().isEmpty()) {
            addresses = addressService.search(search);
            model.addAttribute("search", search);
        } else {
            addresses = addressService.findAll();
        }
        model.addAttribute("addresses", addresses);
        return "addresses/list";
    }
}
