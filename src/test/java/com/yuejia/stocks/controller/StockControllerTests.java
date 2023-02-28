package com.yuejia.stocks.controller;

import com.yuejia.stocks.StockRequestBuilder;
import com.yuejia.stocks.model.StockEntity;
import com.yuejia.stocks.service.StockService;
import com.yuejia.stocks.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class StockControllerTests {

    @Autowired
    StockController controller;
    StockRequestBuilder stockRequestBuilder;
    StorageService storageService;
    StockService stockService;

    @BeforeEach
    void configureSystemUnderTest() {
        storageService = mock(StorageService.class);
        stockService = mock(StockService.class);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new StockController(storageService, stockService))
                .build();
        stockRequestBuilder = new StockRequestBuilder(mockMvc);
    }

    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void loadHomePageViewWithModelAttributes() throws Exception {
        stockRequestBuilder.homePage()
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("files", "records"));;
    }

    @Test
    public void loadHomePageViewWithFilteredModelAttributeValue() throws Exception {
        var stock = new StockEntity();
        stock.setStock("AA");
        var filteredStocks = Arrays.asList(stock);

        when(stockService.findAllByStock("AA")).thenReturn(filteredStocks);
        stockRequestBuilder.homePageWithFilter()
                .andExpect(view().name("index"))
                .andExpect(model().attribute("records", filteredStocks));
    }
}
