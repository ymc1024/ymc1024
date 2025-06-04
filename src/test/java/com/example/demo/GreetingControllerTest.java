package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Greeting test case")
@WebMvcTest(GreetingController.class)
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test default greeting")
    @Test
    public void testDefaultGreeting() throws Exception {
        this.mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", is("Hello, World!")));
    }

    @DisplayName("Test greeting with query string parameter")
    @Test
    public void testGreetingWithParameter() throws Exception {
        String name = "Jason";
        this.mockMvc.perform(get("/greeting?name=" + name))
                .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", is("Hello, Jason!")));
    }

}