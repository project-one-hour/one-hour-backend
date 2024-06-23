package com.project1hour.api.core.domain.credit;

import static org.assertj.core.api.Assertions.assertThat;

import com.project1hour.api.core.domain.member.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreditTest {

    @Test
    void 웰컴_크레딧을_받을_수_있다() {
        // given
        Member member = Member.builder()
                .build();
        // when
        Credit welcomeCredit = Credit.createWelcomeCredit(member);

        // then
        assertThat(welcomeCredit.getCreditCount()).isEqualTo(6);
    }
}
