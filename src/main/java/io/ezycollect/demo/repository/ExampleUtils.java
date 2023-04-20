package io.ezycollect.demo.repository;

import org.springframework.data.domain.ExampleMatcher;

public class MatcherUtils {

    public static ExampleMatcher getExampleMatcherForContact() {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("phoneNumber", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return exampleMatcher;
    }

}
