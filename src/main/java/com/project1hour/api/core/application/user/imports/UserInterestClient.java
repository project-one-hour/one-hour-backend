package com.project1hour.api.core.application.user.imports;

import java.util.Collection;

public interface UserInterestClient {

    boolean hasMissingInterestIds(Collection<Long> interestIds);
}
