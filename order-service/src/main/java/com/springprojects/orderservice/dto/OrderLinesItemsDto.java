package com.springprojects.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLinesItemsDto {
    private long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;
}
