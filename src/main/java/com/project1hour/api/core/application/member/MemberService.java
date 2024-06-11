package com.project1hour.api.core.application.member;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.implement.image.ImageProcessor;
import com.project1hour.api.core.implement.member.MemberProcessor;
import com.project1hour.api.core.implement.member.MemberReader;
import com.project1hour.api.core.implement.member.dto.NewMemberInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberReader memberReader;
    private final MemberProcessor memberProcessor;
    private final ImageProcessor imageProcessor;

    public void signUp(final Long authenticatedMemberId, final NewMemberInfo newMemberInfo) {
        Member member = memberReader.read(authenticatedMemberId);
        memberProcessor.signUpMember(member, newMemberInfo);
    }

    public boolean isDuplicate(final String nickname) {
        return memberReader.isExistsNickname(nickname);
    }

    public List<String> uploadProfileImages(final Long memberId, final List<MultipartFile> images) {
        Member member = memberReader.read(memberId);
        List<String> imageUrls = imageProcessor.uploadProfileImages(member, images);
        memberProcessor.updateNewProfileImages(member, imageUrls);

        return imageUrls;
    }
}
