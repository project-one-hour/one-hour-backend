package com.project1hour.api.core.domain.member;

import java.util.List;

public interface MemberInterestRepository {

    void saveAll(List<MemberInterest> memberInterests);
}
