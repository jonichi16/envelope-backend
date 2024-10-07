package com.jonichi.envelope.common.response;

import java.util.List;

public record FieldError(String field, List<String> errorMessages) {
}
