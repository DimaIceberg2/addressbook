package com.example.adressbook.controller.web;

import com.example.adressbook.dto.request.AddressCreateRequest;
import com.example.adressbook.dto.response.AddressResponse;
import com.example.adressbook.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressWebController {
    private final AddressService addressService;

    @GetMapping
    public String ListAddress(Model model) {
        List<AddressResponse> addresses = addressService.findAll();
        model.addAttribute("addresses", addresses);
        return "addresses/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        AddressCreateRequest addressRequest = new AddressCreateRequest(null, null, null, null, null);
        model.addAttribute("address", addressRequest);
        return "addresses/form";
    }

    @PostMapping("/save")
    public String saveAddress(AddressCreateRequest addressRequest, RedirectAttributes redirectAttributes) {
        try {
            addressService.create(addressRequest);
            redirectAttributes.addFlashAttribute("success", "Адрес успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении адреса: " + e.getMessage());
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
}
