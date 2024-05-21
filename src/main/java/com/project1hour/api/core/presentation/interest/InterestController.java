package com.project1hour.api.core.presentation.interest;

import com.project1hour.api.core.application.interest.InterestService;
import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.presentation.interest.dto.InterestsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InterestController {

    private final InterestService interestService;

    @GetMapping("/interests")
    public ResponseEntity<InterestsResponse> getAll() {
        List<Interest> interests = interestService.getAll();
        InterestsResponse response = InterestsResponse.createResponse(interests);
        return ResponseEntity.ok(response);
    }
}
