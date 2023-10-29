package com.springprojects.inventryservice.service;

import com.springprojects.inventryservice.repository.InventryRepository;
import com.springprojects.inventryservice.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventryService {

    private final InventryRepository inventryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventry ->
                        InventoryResponse.builder()
                                .skuCode(inventry.getSkuCode())
                                .isInStock(inventry.getQuantity() > 0)
                                .build()
                ).toList();
    }
}