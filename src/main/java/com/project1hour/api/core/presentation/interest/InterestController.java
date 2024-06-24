package com.project1hour.api.core.presentation.interest;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.presentation.interest.dto.InterestsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InterestController {

    @GetMapping("/interests")
    public ResponseEntity<InterestsResponse> getAll() {
        List<String> interestNames = Interest.findAll();
        InterestsResponse response = new InterestsResponse(interestNames);

        return ResponseEntity.ok(response);
    }
}
