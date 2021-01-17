package com.wirebarley.demo.web;

import com.wirebarley.demo.web.dto.CurrencyLayerDto;
import com.wirebarley.demo.web.dto.QuotesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        final String url = "http://api.currencylayer.com/live?access_key=202068f55509800ad8e4051e0e36df3f&format=1";
//        final String url = "http://www.apilayer.net/api/live?access_key=ee50cd7cc73c9b7a7bb3d9617cfb6b9c";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrencyLayerDto> responseEntity = restTemplate.getForEntity(url, CurrencyLayerDto.class);
        CurrencyLayerDto currencyLayer = responseEntity.getBody();

        model.addAttribute("currencyLayer", this.getQuotes(currencyLayer));
        return "index";
    }

    private List<QuotesDto> getQuotes(CurrencyLayerDto currencyLayer) {
        Map<String, BigDecimal> quotes = currencyLayer.getQuotes();
        return quotes.entrySet()
                .stream()
                .filter(map -> "USDKRW".equals(map.getKey()) || "USDJPY".equals(map.getKey()) || "USDPHP".equals(map.getKey()))
                .map(p -> new QuotesDto(p.getKey(), p.getValue()))
                .collect(Collectors.toList());
    }
}
