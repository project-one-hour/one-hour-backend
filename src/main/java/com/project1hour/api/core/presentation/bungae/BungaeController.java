package com.project1hour.api.core.presentation.bungae;

import com.project1hour.api.core.application.bungae.BungaeService;
import com.project1hour.api.core.domain.interest.Category;
import com.project1hour.api.core.presentation.auth.MemberOnly;
import com.project1hour.api.core.presentation.bungae.dto.CategoriesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bungaes")
@RequiredArgsConstructor
public class BungaeController {

    private final BungaeService bungaeService;

    @GetMapping("/categories")
    @MemberOnly
    public ResponseEntity<CategoriesResponse> findCategories(@RequestParam final String bungaeType) {
        List<String> names = Category.findCategoryTypeNames(bungaeType);
        CategoriesResponse response = new CategoriesResponse(names);

        return ResponseEntity.ok(response);
    }
}
