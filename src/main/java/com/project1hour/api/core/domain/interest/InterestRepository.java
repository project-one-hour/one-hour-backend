package com.project1hour.api.core.domain.interest;

import java.util.List;

public interface InterestRepository {

    List<Interest> getAllByName(List<String> interestsName);
}
