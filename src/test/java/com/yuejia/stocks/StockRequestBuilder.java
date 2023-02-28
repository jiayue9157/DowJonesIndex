package com.yuejia.stocks;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class StockRequestBuilder {
    private final MockMvc mockMvc;

    public StockRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions homePage() throws Exception {
        return mockMvc.perform(get("/"));
    }

    public ResultActions homePageWithFilter() throws Exception {
        return mockMvc.perform(get("/?stock=AA"));
    }
}
