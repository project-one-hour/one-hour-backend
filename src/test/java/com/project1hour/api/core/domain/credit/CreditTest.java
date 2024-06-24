package com.project1hour.api.core.domain.credit;

import static org.assertj.core.api.Assertions.assertThat;

import com.project1hour.api.core.domain.member.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreditTest {

    @Test
    void 웰컴_재화를_받을_수_있다() {
        // given
        Member member = Member.builder()
                .build();
        // when
        Credit welcomeCredit = Credit.createWelcomeCredit(member);

        // then
        assertThat(welcomeCredit.getCreditCount()).isEqualTo(6);
    }

    @Test
    void hasMoreCredit_메소드는_기존_재화_개수보다_크다면_false를_반환한다() {
        // given
        Credit credit = Credit.builder()
                .creditCount(5)
                .build();

        // when
        boolean hasMoreCredit = credit.hasMoreCredit(6);

        // then
        assertThat(hasMoreCredit).isFalse();
    }

    @Test
    void hasMoreCredit메소드는_기존_재화_개수보다_작거나_같다면_true를_반환한다() {
        // given
        Credit credit = Credit.builder()
                .creditCount(5)
                .build();

        // when
        boolean hasMoreCredit = credit.hasMoreCredit(5);

        // then
        assertThat(hasMoreCredit).isTrue();
    }
}
