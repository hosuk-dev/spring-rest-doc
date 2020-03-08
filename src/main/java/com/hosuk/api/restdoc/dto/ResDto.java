package com.hosuk.api.restdoc.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ResDto {

    private String resultCd;
    private String resultMsg;

}
