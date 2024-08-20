package com.project1hour.api.core.application.user.api;

import java.util.Collection;

public interface UserInterestClient {

    boolean hasMissingInterestIds(Collection<Long> interestIds);
}
