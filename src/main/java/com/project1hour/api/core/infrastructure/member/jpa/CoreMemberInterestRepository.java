package com.project1hour.api.core.infrastructure.member.jpa;

import com.project1hour.api.core.domain.member.MemberInterest;
import com.project1hour.api.core.domain.member.MemberInterestRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreMemberInterestRepository implements MemberInterestRepository {

    private final JpaMemberInterestRepository jpaMemberInterestRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(final List<MemberInterest> memberInterests) {
        String sql = "INSERT INTO member_interest (member_id, interest_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(
                sql,
                memberInterests,
                memberInterests.size(),
                (ps, memberInterest) -> {
                    ps.setLong(1, memberInterest.getMember().getId());
                    ps.setLong(2, memberInterest.getInterest().getId());
                }
        );
    }
}
