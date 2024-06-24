package com.project1hour.api.core.application.credit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.project1hour.api.core.domain.auth.AuthenticationContext;
import com.project1hour.api.core.domain.credit.Credit;
import com.project1hour.api.core.domain.credit.CreditRepository;
import com.project1hour.api.core.domain.member.Authority;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberRepository;
import com.project1hour.api.core.domain.member.SignUpStatus;
import com.project1hour.api.core.domain.member.profileinfo.Gender;
import com.project1hour.api.core.domain.member.profileinfo.Nickname;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = NONE)
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreditServiceTest {

    @Autowired
    private CreditService creditService;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private AuthenticationContext authenticationContext;

    @Nested
    class canUseCredit_메소드는 {

        private Long memberId;

        @BeforeEach
        void setUp() {
            Member member = Member.builder()
                    .nickname(new Nickname("닉네임"))
                    .gender(Gender.MALE)
                    .authority(Authority.MEMBER)
                    .signUpStatus(SignUpStatus.SIGNED_UP)
                    .build();
            memberRepository.save(member);
            Credit credit = Credit.createWelcomeCredit(member);

            creditRepository.save(credit);
            memberId = member.getId();
        }

        @Nested
        class 사용자가_가진_재화보다_많은_재화가_들어오면 {

            @Test
            void false를_반환한다() {
                // when
                boolean result = creditService.canUseCredit(memberId, 7);

                // then
                assertThat(result).isFalse();
            }
        }

        @Nested
        class 사용자가_가진_재화보다_작거나_같은_재화가_들어오면 {

            @Test
            void true를_반환한다() {
                // when
                boolean result = creditService.canUseCredit(memberId, 6);

                // then
                assertThat(result).isTrue();
            }
        }
    }
}