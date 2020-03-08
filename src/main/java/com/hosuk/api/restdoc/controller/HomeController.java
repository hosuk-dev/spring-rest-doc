package com.hosuk.api.restdoc.controller;

import com.hosuk.api.restdoc.dto.ReqDto;
import com.hosuk.api.restdoc.dto.ResDto;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @PostMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity home(@RequestBody ReqDto reqDto) {
        log.info(reqDto.toString());

        ResDto resDto = ResDto.builder().resultCd("0000").resultMsg("success").build();
        return (ResponseEntity) ResponseEntity.ok().body(resDto);
    }

    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test(@RequestBody ReqDto reqDto) {
        log.info(reqDto.toString());

        ResDto resDto = ResDto.builder().resultCd("0000").resultMsg("성공").build();
        return (ResponseEntity) ResponseEntity.ok().body(resDto);
    }
}
