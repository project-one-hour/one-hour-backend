package com.project1hour.api.core.application.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.core.domain.member.Authority;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberRepository;
import com.project1hour.api.core.domain.member.SignUpStatus;
import com.project1hour.api.core.domain.member.profileinfo.Birthday;
import com.project1hour.api.core.domain.member.profileinfo.Gender;
import com.project1hour.api.core.domain.member.profileinfo.Mbti;
import com.project1hour.api.core.domain.member.profileinfo.Nickname;
import com.project1hour.api.core.implement.member.dto.NewMemberInfo;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    class signUp_메소드는 {

        @Nested
        class 정상적인_요청이_들어오면 {

            private Long memberId;

            @BeforeEach
            void setUp() {
                Member member = Member.createJustAuthenticatedMember();
                memberRepository.save(member);
                memberId = member.getId();
            }

            @Test
            void 회원가입을_성공한다() {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        "닉네임",
                        "MALE",
                        LocalDate.of(1998, 10, 8),
                        "ENTJ",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // when
                assertThatNoException().isThrownBy(() -> memberService.signUp(memberId, newMemberInfo));
            }
        }

        @Nested
        class 닉네임이_형식에_일치하지_않는다면 {

            private Long memberId;

            @BeforeEach
            void setUp() {
                Member member = Member.createJustAuthenticatedMember();
                memberRepository.save(member);
                memberId = member.getId();
            }

            @ParameterizedTest
            @ValueSource(strings = {"ㅇㄱ", "`-", "`하이하이", "`=rkdc"})
            void 예외가_발생한다(String nickname) {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        nickname,
                        "MALE",
                        LocalDate.of(1998, 10, 8),
                        "ENTJ",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // expect
                assertThatThrownBy(() -> memberService.signUp(memberId, newMemberInfo))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage("닉네임의 형식이 잘못됐습니다. 현재닉네임 = " + nickname);
            }
        }

        @Nested
        class 닉네임이_2글자_이상_8글자_이하가_아니라면 {

            private Long memberId;

            @BeforeEach
            void setUp() {
                Member member = Member.createJustAuthenticatedMember();
                memberRepository.save(member);
                memberId = member.getId();
            }

            @ParameterizedTest
            @ValueSource(strings = {"짧", "이건이름이너무길어요"})
            void 예외가_발생한다(String nickname) {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        nickname,
                        "MALE",
                        LocalDate.of(1998, 10, 8),
                        "ENTJ",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // expect
                assertThatThrownBy(() -> memberService.signUp(memberId, newMemberInfo))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage("닉네임은 2글자 이상 8글자 이하여야 합니다. 현재길이 = " + nickname.length());
            }
        }

        @Nested
        class 가입시_법정_성인_연령이_아니라면 {

            private static LocalDate MINIMUM_ADULT_BIRTH = LocalDate.of(
                    LocalDate.now().minusYears(9).getYear(),
                    LocalDate.MAX.getMonth(),
                    LocalDate.MAX.getDayOfMonth()
            );

            private Long memberId;

            @BeforeEach
            void setUp() {
                Member member = Member.createJustAuthenticatedMember();
                memberRepository.save(member);
                memberId = member.getId();
            }

            @Test
            void 예외가_발생한다() {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        "닉네임",
                        "MALE",
                        MINIMUM_ADULT_BIRTH.plusDays(1),
                        "ENTJ",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // expect
                assertThatThrownBy(() -> memberService.signUp(memberId, newMemberInfo))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage(String.format("성인만 가입할 수 있습니다. 최소년생 = %d", MINIMUM_ADULT_BIRTH.getYear()));
            }
        }

        @Nested
        class id가_존재하지_않는_사용자로_회원가입을_시도하면 {

            @Test
            void 예외가_발생한다() {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        "닉네임",
                        "MALE",
                        LocalDate.of(1998, 10, 8),
                        "ENTJ",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // expect
                assertThatThrownBy(() -> memberService.signUp(11111111L, newMemberInfo))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 사용자 입니다. id = 11111111");
            }
        }

        @Nested
        class 존재하지_않는_성별로_회원가입을_시도하면 {

            private Long memberId;

            @BeforeEach
            void setUp() {
                Member member = Member.createJustAuthenticatedMember();
                memberRepository.save(member);
                memberId = member.getId();
            }

            @Test
            void 예외가_발생한다() {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        "닉네임",
                        "남자",
                        LocalDate.of(1998, 10, 8),
                        "ENTJ",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // expect
                assertThatThrownBy(() -> memberService.signUp(memberId, newMemberInfo))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage("일치하는 성별을 찾을 수 없습니다. 입력성별 = 남자");
            }
        }

        @Nested
        class 존재하지_않는_MBTI로_회원가입을_시도하면 {

            private Long memberId;

            @BeforeEach
            void setUp() {
                Member member = Member.createJustAuthenticatedMember();
                memberRepository.save(member);
                memberId = member.getId();
            }

            @Test
            void 예외가_발생한다() {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        "닉네임",
                        "MALE",
                        LocalDate.of(1998, 10, 8),
                        "cute",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // expect
                assertThatThrownBy(() -> memberService.signUp(memberId, newMemberInfo))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage("CUTE와 일치하는 MBTI를 찾을 수 없습니다.");
            }
        }

        @Nested
        class 이미_가입한_사용자라면 {

            private Long memberId;

            @BeforeEach
            void setUp() {
                Member member = Member.builder()
                        .nickname(new Nickname("닉네임"))
                        .gender(Gender.MALE)
                        .birthday(new Birthday(LocalDate.of(1998, 10, 8)))
                        .mbti(Mbti.ENTJ)
                        .authority(Authority.MEMBER)
                        .signUpStatus(SignUpStatus.SIGNED_UP)
                        .build();
                memberRepository.save(member);
                memberId = member.getId();
            }

            @Test
            void 예외가_발생한다() {
                // given
                NewMemberInfo newMemberInfo = new NewMemberInfo(
                        "닉네임",
                        "MALE",
                        LocalDate.of(1998, 10, 8),
                        "ENTJ",
                        List.of("맛집탐방", "카페투어", "디저트", "와인/바", "여행"),
                        true
                );

                // expect
                assertThatThrownBy(() -> memberService.signUp(memberId, newMemberInfo))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage("이미 가입한 사용자 입니다.");
            }
        }
    }

    @Nested
    class isDuplicate_메소드는 {

        @Nested
        class 해당_닉네임을_이미_사용하는_사용자가_존재하면 {

            @BeforeEach
            void setUp() {
                Member member = Member.builder()
                        .nickname(new Nickname("닉네임"))
                        .build();

                memberRepository.save(member);
            }

            @Test
            void true를_반환한다() {
                // when
                boolean isDuplicate = memberService.isDuplicate("닉네임");

                // then
                assertThat(isDuplicate).isTrue();
            }
        }

        @Nested
        class 해당_닉네임을_사용하는_사용자가_없다면 {

            @Test
            void false를_반환한다() {
                // when
                boolean isDuplicate = memberService.isDuplicate("닉네임");

                // then
                assertThat(isDuplicate).isFalse();
            }
        }
    }
}
