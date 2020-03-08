package com.hosuk.api.restdoc.dto;

import lombok.*;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class ReqDto {

    private String id;
    private String pwd;
    private String msg;
}
