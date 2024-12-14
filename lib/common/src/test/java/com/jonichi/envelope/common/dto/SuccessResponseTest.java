package com.jonichi.envelope.common.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SuccessResponseTest {

    @Test
    public void builderShouldCreateTheCorrectObject() throws Exception {
        // given
        int code = 200;
        String message = "Success";
        String data = "Sample data";

        // when
        ApiResponse<String> response = SuccessResponse.<String>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getCode()).isEqualTo(code);
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getData()).isEqualTo(data);
        assertThat(response.getTimestamp()).isNotNull();
    }

    @Test
    public void builderShouldThrowExceptionForUnsupportedMethods() throws Exception {
        // given
        int code = 200;
        String message = "Success";
        String data = "Sample data";

        // when
        ApiResponse<String> response = SuccessResponse.<String>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();

        // then
        assertThatThrownBy(response::getError)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Error field is not supported");
        assertThatThrownBy(response::getErrorCode)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("ErrorCode field is not supported");
    }

}