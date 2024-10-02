package com.jonichi.envelope.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public sealed class Resource<T> permits Resource.Success, Resource.Error {
    private final Object message;
    private final T data;

    public static final class Success<T> extends Resource<T> {
        public Success(Object message, T data) {
            super(message, data);
        }
    }

    public static final class Error<T> extends Resource<T> {
        public Error(Object message) {
            super(message, null);
        }
    }

}