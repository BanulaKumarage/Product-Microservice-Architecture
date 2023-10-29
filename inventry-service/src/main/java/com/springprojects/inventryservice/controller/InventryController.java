package com.springprojects.inventryservice.controller;

import com.springprojects.inventryservice.service.InventryService;
import com.springprojects.inventryservice.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventryController {

    private final InventryService inventryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventryService.isInStock(skuCode);
    }

}
