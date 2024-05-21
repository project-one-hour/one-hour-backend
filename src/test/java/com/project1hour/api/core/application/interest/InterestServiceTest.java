package com.project1hour.api.core.application.interest;

import static org.assertj.core.api.Assertions.assertThat;

import com.project1hour.api.core.domain.interest.Interest;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InterestServiceTest {

    @Autowired
    private InterestService interestService;

    @Nested
    class getAll_메소드는 {

        @Test
        void 모든_관심사를_조회한다() {
            // when
            List<Interest> interests = interestService.getAll();

            // then
            assertThat(interests).isNotEmpty();
        }
    }
}
