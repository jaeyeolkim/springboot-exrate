package com.wirebarley.demo.web;

import com.wirebarley.demo.web.dto.ExrateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ExrateApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Exrate_계산() throws Exception {
        // given
        String country = "KRW"; // 수취국가
        BigDecimal exrate = new BigDecimal("1119.93"); // 환율
        int sendMoney = 1000; // 송금액(USD)

        ExrateRequestDto requestDto = ExrateRequestDto.builder()
                .country(country)
                .exrate(exrate)
                .sendMoney(sendMoney)
                .build();

        String url = "http://localhost:" + port + "/api/exrate";

        // when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void ExrateRequest_잘못된송금액요청() throws Exception {
        // given
        String country = "KRW"; // 수취국가
        BigDecimal exrate = new BigDecimal("1119.93"); // 환율
        int sendMoney = -100; // 송금액(USD)

        ExrateRequestDto requestDto = ExrateRequestDto.builder()
                .country(country)
                .exrate(exrate)
                .sendMoney(sendMoney)
                .build();

        String url = "http://localhost:" + port + "/api/exrate";

        // when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("송금액이 바르지 않습니다");
    }

    @Test
    public void ExrateRequest_송금액_Min() throws Exception {
        // given
        String country = "KRW"; // 수취국가
        BigDecimal exrate = new BigDecimal("1119.93"); // 환율
        int sendMoney = -100; // 송금액(USD)

        ExrateRequestDto requestDto = ExrateRequestDto.builder()
                .country(country)
                .exrate(exrate)
                .sendMoney(sendMoney)
                .build();

        // when
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ExrateRequestDto>> constraintViolations = validator.validate(requestDto);

        // then
        assertThat(constraintViolations)
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("0 이상이어야 합니다");
    }

    @Test
    public void ExrateRequest_송금액_Max() throws Exception {
        // given
        String country = "KRW"; // 수취국가
        BigDecimal exrate = new BigDecimal("1119.93"); // 환율
        int sendMoney = 10001; // 송금액(USD)

        ExrateRequestDto requestDto = ExrateRequestDto.builder()
                .country(country)
                .exrate(exrate)
                .sendMoney(sendMoney)
                .build();

        // when
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ExrateRequestDto>> constraintViolations = validator.validate(requestDto);

        // then
        assertThat(constraintViolations)
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("10000 이하여야 합니다");
    }

}