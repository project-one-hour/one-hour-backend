package com.project1hour.api.core.infrastructure.auth.kakao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.core.domain.auth.SocialInfo;
import com.project1hour.api.core.domain.member.Provider;
import com.project1hour.api.global.advice.InfraStructureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KakaoOauthClientTest {

    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KakaoOauthClient kakaoOauthClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @DisplayName("입력한 providerInput값이 kakao라면 참을 반환한다.")
    void isSupport() {
        // given
        String providerInput = "kaKao";

        // when
        boolean isSupport = kakaoOauthClient.isSupport(providerInput);

        // then
        assertThat(isSupport).isTrue();
    }

    @Nested
    class requestSocialProfileByToken_메소드는 {

        @Nested
        class 잘못된_accessToken으로_Kakao_프로필_정보를_요청하면 {

            @BeforeEach
            void setUp() {
                mockRestServiceServer.expect(requestTo("https://kapi.kakao.com/v2/user/me"))
                        .andExpect(method(HttpMethod.GET))
                        .andRespond(withStatus(HttpStatus.UNAUTHORIZED)
                                .contentType(MediaType.APPLICATION_JSON));
            }

            @Test
            void 예외를_발생시킨다() {
                // expect
                assertThatThrownBy(() -> kakaoOauthClient.requestSocialProfileByToken("token"))
                        .isInstanceOf(InfraStructureException.class)
                        .hasMessage("해당 사용자의 프로필을 요청할 수 없습니다.");
                mockRestServiceServer.verify();
            }
        }

        @Nested
        class 인증된_accessToken으로_Kakao_프로필_정보를_요청하면 {

            @BeforeEach
            void setUp() throws JsonProcessingException {
                SocialInfo socialInfo = new KakaoSocialInfo("123456789", "kakao@email.com");
                mockRestServiceServer.expect(requestTo("https://kapi.kakao.com/v2/user/me"))
                        .andExpect(method(HttpMethod.GET))
                        .andRespond(withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objectMapper.writeValueAsString(socialInfo)));
            }

            @Test
            void Kakao_프로필_정보를_반환한다() {
                // when
                SocialInfo socialInfo = kakaoOauthClient.requestSocialProfileByToken("token");

                // then
                assertAll(
                        () -> assertThat(socialInfo.getProvider()).isEqualTo(Provider.KAKAO),
                        () -> assertThat(socialInfo.getProviderId()).isEqualTo("123456789"),
                        () -> assertThat(socialInfo.getEmail()).isEqualTo("kakao@email.com")
                );
                mockRestServiceServer.verify();
            }
        }
    }
}
