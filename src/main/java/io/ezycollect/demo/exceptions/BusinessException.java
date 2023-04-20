package io.ezycollect.demo.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.StatusType;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class BusinessException extends AbstractThrowableProblem {
    public BusinessException(String message, StatusType statusType, String detail) {
        super(null, message, statusType, detail);
    }
}
