package com.hosuk.api.restdoc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hosuk.api.restdoc.controller.HomeController;
import com.hosuk.api.restdoc.dto.ReqDto;
import com.hosuk.api.restdoc.dto.ResDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@AutoConfigureRestDocs
public class HomeControllerTest {

    //build.gradle에 set('snippetsDir', file("build/generated-snippets")) 에 설정한 output 경로를 그대로 맞춰줘야
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private RestDocumentationResultHandler document;

    @Before
    public void setup() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document)     //모든 mockMvc에 해당 document가 적용된다.
                .build();
    }

    @Test
    public void testHome() throws Exception {
        //given
        ReqDto reqDto = ReqDto.builder().id("hosuk").pwd("hosuk123").msg("안녕하세요 저는 이호석입니다.").build();
        ResDto resDto = ResDto.builder().resultCd("0000").resultMsg("success").build();

        //when
        ResultActions resultActions = this.mockMvc.perform(
                post("/home").content(objectMapper.writeValueAsString(reqDto))
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(resDto)))
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("아이디").optional(),
                                fieldWithPath("pwd").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("msg").type(JsonFieldType.STRING).description("메세지")
                        ),
                        responseFields(
                                fieldWithPath("resultCd").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("resultMsg").type(JsonFieldType.STRING).description("결과메세지")
                        )
                ));
    }

    @Test
    public void testTest() throws Exception {
        //given
        ReqDto reqDto = ReqDto.builder().id("hosuk").pwd("hosuk123").msg("안녕하세요 저는 이호석입니다.").build();

        //when
        ResultActions resultActions = this.mockMvc.perform(
                post("/test").content(objectMapper.writeValueAsString(reqDto))
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("아이디").optional(),
                                fieldWithPath("pwd").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("msg").type(JsonFieldType.STRING).description("메세지")
                        ),
                        responseFields(
                                fieldWithPath("resultCd").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("resultMsg").type(JsonFieldType.STRING).description("결과메세지")
                        )
                ));
    }
}
