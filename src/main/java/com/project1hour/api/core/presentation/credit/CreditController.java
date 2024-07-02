package com.project1hour.api.core.presentation.credit;

import com.project1hour.api.core.application.credit.CreditService;
import com.project1hour.api.core.presentation.auth.Login;
import com.project1hour.api.core.presentation.auth.MemberOnly;
import com.project1hour.api.core.presentation.credit.dto.CreditCheckRequest;
import com.project1hour.api.core.presentation.credit.dto.CreditCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credits")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping("/check")
    @MemberOnly
    public ResponseEntity<CreditCheckResponse> checkCredit(@Login final Long memberId,
                                                           @RequestBody final CreditCheckRequest request) {
        boolean result = creditService.canUseCredit(memberId, request.creditCount());
        CreditCheckResponse response = new CreditCheckResponse(result);

        return ResponseEntity.ok(response);
    }
}
