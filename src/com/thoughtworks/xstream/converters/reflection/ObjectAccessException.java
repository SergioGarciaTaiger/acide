package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.core.BaseException;

@SuppressWarnings({"serial"})
public class ObjectAccessException extends BaseException {
    public ObjectAccessException(String message) {
        super(message);
    }

    public ObjectAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
