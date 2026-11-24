package com.ravicode.employee.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogFactory {
    private LogFactory() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
}
