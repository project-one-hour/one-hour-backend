package com.project1hour.api.global.config;

import static com.project1hour.api.global.advice.ErrorCode.CAN_NOT_EXCHANGE_OAUTH_PROFILE;

import com.project1hour.api.global.advice.InfraStructureException;
import java.io.IOException;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(final ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        throw new InfraStructureException("해당 사용자의 프로필을 요청할 수 없습니다.", CAN_NOT_EXCHANGE_OAUTH_PROFILE);
    }

    @Override
    public void handleError(final URI url, final HttpMethod method, final ClientHttpResponse response)
            throws IOException {
        log.error("Oauth Error. profileUrl = {}, statusCode = {}", url, response.getStatusCode());
        handleError(response);
    }
}
