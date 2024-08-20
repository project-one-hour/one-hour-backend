package com.project1hour.api.core.domain.user.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServiceConsentTest {

    @Test
    void boolean_타입으로_서비스_이용_동의를_만들_수_있다() {
        //given
        boolean allowedMarketing = true;
        boolean allowedNotification = true;

        //when
        ServiceConsent serviceConsent = ServiceConsent.of()
                .marketingConsentAllowed(allowedMarketing)
                .notificationConsentAllowed(allowedNotification)
                .build();

        //then
        assertAll(
                () -> assertThat(serviceConsent.marketing()).isEqualTo(ServiceConsent.Marketing.ALLOW),
                () -> assertThat(serviceConsent.notification()).isEqualTo(ServiceConsent.Notification.ALLOW)
        );
    }

    @Test
    void 알림_이용_동의_확인을_할_수_있다() {
        //when
        ServiceConsent serviceConsent = ServiceConsent.of()
                .notificationConsentAllowed(true)
                .build();

        //then
        assertThat(serviceConsent.isNotificationConsentAllowed()).isTrue();
    }

    @Test
    void 마케팅_이용_동의_확인을_할_수_있다() {
        //when
        ServiceConsent serviceConsent = ServiceConsent.of()
                .notificationConsentAllowed(true)
                .marketingConsentAllowed(true)
                .build();

        //then
        assertThat(serviceConsent.isMarketingConsentAllowed()).isTrue();
    }

    @Test
    void 마케팅_이용_동의를_하더라도_알림_이용_동의_허용을_하지_않으면_마케팅_이용을_할_수_없다() {
        //when
        ServiceConsent serviceConsent = ServiceConsent.of()
                .marketingConsentAllowed(true)
                .notificationConsentAllowed(false)
                .build();

        //given
        assertThat(serviceConsent.isMarketingConsentAllowed()).isFalse();
    }
}
