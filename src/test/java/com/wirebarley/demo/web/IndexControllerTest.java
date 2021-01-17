package com.wirebarley.demo.web;

import com.wirebarley.demo.web.dto.CurrencyLayerDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩확인() {
        // when
        String body = this.restTemplate.getForObject("/", String.class);

        // then
        assertThat(body).contains("환율 계산");
    }

    @Test
    public void 실시간환율API호출() throws Exception {
        // given
        String url = "http://www.apilayer.net/api/live?access_key=ee50cd7cc73c9b7a7bb3d9617cfb6b9c";
//        String url = "http://api.currencylayer.com/live?access_key=202068f55509800ad8e4051e0e36df3f&format=1";

        // when
        ResponseEntity<CurrencyLayerDto> responseEntity = restTemplate.getForEntity(url, CurrencyLayerDto.class);
        CurrencyLayerDto currencyLayer = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(currencyLayer.getSuccess()).isEqualTo("true");
        assertThat(currencyLayer.getQuotes().get("USDKRW")).isNotNull();
        assertThat(currencyLayer.getQuotes().get("USDJPY")).isNotNull();
        assertThat(currencyLayer.getQuotes().get("USDPHP")).isNotNull();
    }
}