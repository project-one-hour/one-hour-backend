package com.project1hour.api.core.domain.user.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record ServiceConsent(
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        Marketing marketing,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        Notification notification
) {

    public enum Marketing {ALLOW, NOT_ALLOW}

    public enum Notification {ALLOW, NOT_ALLOW}

    public static ServiceConsent of(final boolean marketingConsentAllowed, final boolean notificationConsentAllowed) {
        Marketing marketing = Marketing.NOT_ALLOW;
        Notification notification = Notification.NOT_ALLOW;

        if (marketingConsentAllowed) {
            marketing = Marketing.ALLOW;
        }

        if (notificationConsentAllowed) {
            notification = Notification.ALLOW;
        }

        return new ServiceConsent(marketing, notification);
    }

    /**
     * TODO : 이벤트 스토밍에 정책 업데이트 해야함 <br/>
     * Policy : (new) 기기 알림 권한 동의를 해야 마케팅 정보 이용을 할 수 있다
     */
    public boolean isMarketingConsentAllowed() {
        if (!isNotificationConsentAllowed()) {
            return false;
        }
        return marketing == Marketing.ALLOW;
    }

    public boolean isNotificationConsentAllowed() {
        return notification == Notification.ALLOW;
    }
}
