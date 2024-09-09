package com.project1hour.api.core.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.project1hour.api.core.domain.user.value.AuthProvider;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AuthTest {

    @Test
    void AuthProvider가_동일한지_isProviderEqual_메서드로_확인할_수_있다() {
        //given
        Auth auth = Auth.builder().provider(AuthProvider.KAKAO).build();
        AuthProvider provider = AuthProvider.APPLE;

        //expect
        assertThat(auth.getProvider()).isEqualTo(AuthProvider.KAKAO);
    }
}
