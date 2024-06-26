package com.project1hour.api.core.documentation.errorcode;

import com.project1hour.api.global.advice.ErrorCode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;

public class ErrorCodeFieldsSnippet extends TemplatedSnippet {

    public ErrorCodeFieldsSnippet(final String snippetName, final String templateName) {
        super(snippetName, templateName, null);
    }

    @Override
    protected Map<String, Object> createModel(final Operation operation) {
        Map<String, Object> fields = new HashMap<>();
        List<Map<String, String>> errorCodeValues = createErrorCodeValues();
        fields.put("fields", errorCodeValues);

        return fields;
    }

    private List<Map<String, String>> createErrorCodeValues() {
        return Arrays.stream(ErrorCode.values())
                .map(errorCode -> Map.of(
                        "path", errorCode.getErrorCode(),
                        "description", errorCode.getDescription(),
                        "type", "String"))
                .toList();
    }
}
