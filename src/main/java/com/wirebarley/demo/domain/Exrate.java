package com.wirebarley.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
@NoArgsConstructor
public class Exrate {
    private String country; // 수취국가
    private BigDecimal exrate; // 환율
    private int sendMoney; // 송금액(USD)

    private BigDecimal exrateSendMoney; // 수취금액(국가/USD)
    private String message; // 응답 메시지

    @Builder
    public Exrate(String country, BigDecimal exrate, int sendMoney) {
        this.country = country;
        this.exrate = exrate;
        this.sendMoney = sendMoney;
    }

    // 수취금액 계산
    public void saveExrateSendMoney() {
        this.exrateSendMoney = this.exrate.multiply(BigDecimal.valueOf(this.sendMoney));
        this.message = "수취금액은 " + getExrateSendMoneyFormat() + country + " 입니다.";
    }

    private String getExrateSendMoneyFormat() {
        DecimalFormat format = new DecimalFormat("###,###.00");
        return format.format(exrateSendMoney);
    }
}