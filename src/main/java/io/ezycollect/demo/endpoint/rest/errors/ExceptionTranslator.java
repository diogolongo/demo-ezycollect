package io.ezycollect.demo.endpoint.rest.errors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.net.URI;
import java.util.Optional;

@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {
    @Override
    public ProblemBuilder prepare(final Throwable throwable, final StatusType status, final URI type) {
        return Problem
                .builder()
                .withType(type)
                .withTitle(status.getReasonPhrase())
                .withStatus(status)
                .withDetail(throwable.getMessage())
                .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                );
    }
}
