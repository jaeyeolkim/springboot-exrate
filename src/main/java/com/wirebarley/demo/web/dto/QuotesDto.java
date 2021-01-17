package com.wirebarley.demo.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
public class QuotesDto {
    private String country; // 국가코드
    private BigDecimal exrate; // 환율
    private String exrateFormatText; // 환율(포맷을 지정하여 보여주기 위한 값)
    private String countryName; // 수취국가(국가코드)

    public QuotesDto(String country, BigDecimal exrate) {
        this.country = country.substring(3);
        this.exrate = exrate.setScale(2, RoundingMode.HALF_EVEN);
        this.exrateFormatText = getExrateFormat();

        switch (this.country) {
            case "KRW":
                this.countryName = "한국(" + this.country + ")";
                break;
            case "JPY":
                this.countryName = "일본(" + this.country + ")";
                break;
            case "PHP":
                this.countryName = "필리핀(" + this.country + ")";
                break;
            default:
                break;
        }
    }

    private String getExrateFormat(){
        DecimalFormat format = new DecimalFormat("###,###.00");
        return format.format(this.exrate);
    }
}
