package com.project1hour.api.core.domain.member;

import java.util.List;

public interface MemberInterestRepository {

    List<MemberInterest> saveAll(List<MemberInterest> memberInterests);
}
