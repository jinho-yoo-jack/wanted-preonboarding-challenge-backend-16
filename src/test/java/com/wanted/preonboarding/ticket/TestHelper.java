package com.wanted.preonboarding.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DynamicTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

@Component
public class TestHelper {

    @Autowired
    private MockMvc mockMvc;

    public DynamicTest testApiEndpoint(String testName, RequestBuilder requestBuilder, ResultMatcher... matchers) {
        return DynamicTest.dynamicTest(testName, () -> {
            ResultActions result = mockMvc.perform(requestBuilder);
            for (ResultMatcher matcher : matchers) {
                result.andExpect(matcher);
            }
        });
    }

    public static String convertToJSONString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
