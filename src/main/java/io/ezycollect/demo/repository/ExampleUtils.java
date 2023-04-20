package io.ezycollect.demo.repository;

import io.ezycollect.demo.domain.Contact;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class ExampleUtils {

    public static ExampleMatcher getExampleMatcherForContact() {
        return ExampleMatcher.matchingAny()
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("phoneNumber", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    }

    public static Example<Contact> getFullSearchExample(String filter){
        ExampleMatcher exampleMatcher = ExampleUtils.getExampleMatcherForContact();
        Contact contact = new Contact(null, filter, filter, filter);
        return Example.of(contact, exampleMatcher);
    }
}
