package com.wirebarley.demo.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyLayerDto {
    private String success;
    private String terms;
    private String privacy;
    private Long timestamp;
    private String source;
    private Map<String, BigDecimal> quotes = new HashMap<>();
}