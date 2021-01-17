package com.wirebarley.demo.web.dto;

import com.wirebarley.demo.domain.Exrate;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExrateResponseDto {

    private BigDecimal exrateSendMoney; // 수취금액(국가/USD)
    private String message; // 응답 메시지

    public ExrateResponseDto(String message) {
        this.message = message;
    }

    public ExrateResponseDto(Exrate entity) {
        this.exrateSendMoney = entity.getExrateSendMoney();
        this.message = entity.getMessage();
    }
}
