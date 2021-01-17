package com.wirebarley.demo.web;

import com.wirebarley.demo.domain.Exrate;
import com.wirebarley.demo.web.dto.ExrateRequestDto;
import com.wirebarley.demo.web.dto.ExrateResponseDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ExrateApiController {

    @PostMapping("/api/exrate")
    public ExrateResponseDto exrate(@Valid @RequestBody ExrateRequestDto requestDto, BindingResult bindingResult) {
        Exrate exrate = requestDto.toEntity();
        if (bindingResult.hasErrors()) {
            return new ExrateResponseDto("송금액이 바르지 않습니다");
        }

        // 수취금액 계산
        exrate.saveExrateSendMoney();
        return new ExrateResponseDto(exrate);
    }
}
