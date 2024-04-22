package com.project1hour.api.member.application;

import com.project1hour.api.member.domain.CommandMemberRepository;
import com.project1hour.api.member.domain.QueryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final CommandMemberRepository commandMemberRepository;
    private final QueryMemberRepository queryMemberRepository;
}
