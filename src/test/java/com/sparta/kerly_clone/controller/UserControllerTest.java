package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.requestDto.SignupRequestDto;
import com.sparta.kerly_clone.security.JwtTokenProvider;
import com.sparta.kerly_clone.security.WebSecurityConfig;
import com.sparta.kerly_clone.service.UserService;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Nested
    @DisplayName("????????? ?????? ??????")
    class DupCheck {
        @Test
        @DisplayName("????????? ???????????? ????????? ??????")
        void ?????????????????????() throws Exception {
            //given
            String email = "user@mail.com";
            MultiValueMap<String, String> getSignupForm = new LinkedMultiValueMap<>();
            getSignupForm.add("email", email);

            when(userService.checkDupEmail(email))
                    .thenReturn(true);
            //when
            MvcResult mvcResult = mvc.perform(get("/user/register")
                            .params(getSignupForm)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print())
                    .andReturn();
            //then
            JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertThat(jsonObject.getString("message")).isEqualTo("??????????????? ??????????????????.");
        }

        @Test
        @DisplayName("????????? ?????? ?????? ??????")
        void ???????????????() throws Exception {
            //given
            String email = "user@mail.com";
            MultiValueMap<String, String> getSignupForm = new LinkedMultiValueMap<>();
            getSignupForm.add("email", email);

            when(userService.checkDupEmail(email))
                    .thenReturn(false);
            //when
            MvcResult mvcResult = mvc.perform(get("/user/register")
                            .params(getSignupForm)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print())
                    .andReturn();

            //then
            JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertThat(jsonObject.getString("message")).isEqualTo("????????? ??? ?????? ??????????????????.");
        }
    }

    @Nested
    @DisplayName("????????????")
    class SignUp {
        @Test
        @DisplayName("???????????????")
        void ???????????????() throws Exception {
            //given
            String email = "user@mail.com";
            String username = "user";
            String password = "password486";

            SignupRequestDto signupRequestDto = new SignupRequestDto(email, password, username);
            JSONObject requestJSON = new JSONObject(signupRequestDto);

            given(userService.registerUser(email, password, username))
                    .willReturn(true);
            //when
            MvcResult mvcResult = mvc.perform(post("/user/register")
                            .content(requestJSON.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print())
                    .andReturn();

            //then
            JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertThat(jsonObject.getString("message")).isEqualTo("??????????????? ???????????????????????????.");
        }
    }
}