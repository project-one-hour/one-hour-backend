package com.project1hour.api.core.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MarketingInfoStatusTest {

    @Test
    void allowMarketingInfo가_true라면_마케팅_정보_수신을_동의한다() {
        // when
        MarketingInfoStatus marketingInfoStatus = MarketingInfoStatus.selectStatus(true);

        // then
        assertThat(marketingInfoStatus).isEqualTo(MarketingInfoStatus.ALLOW);
    }

    @Test
    void allowMarketingInfo가_false라면_마케팅_정보_수신을_거절한다() {
        // when
        MarketingInfoStatus marketingInfoStatus = MarketingInfoStatus.selectStatus(false);

        // then
        assertThat(marketingInfoStatus).isEqualTo(MarketingInfoStatus.NOT_ALLOW);
    }
}
