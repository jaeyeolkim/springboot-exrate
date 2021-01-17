package com.wirebarley.demo.web.dto;

import com.wirebarley.demo.domain.Exrate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ExrateRequestDto {
    private String country; // 수취국가
    private BigDecimal exrate; // 환율

    @NotNull
    @Min(1)
    @Max(10000)
    private int sendMoney; // 송금액(USD)

    @Builder
    public ExrateRequestDto(String country, BigDecimal exrate, int sendMoney) {
        this.country = country;
        this.exrate = exrate;
        this.sendMoney = sendMoney;
    }

    public Exrate toEntity() {
        return Exrate.builder()
                .country(country)
                .exrate(exrate)
                .sendMoney(sendMoney)
                .build();
    }

}
