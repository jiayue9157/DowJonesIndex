package com.yuejia.stocks.service;

import com.yuejia.stocks.model.StockEntity;
import com.yuejia.stocks.repository.StockRepository;
import com.yuejia.stocks.utils.CSVHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StockServiceTests {

    StockService stockService;

    @Mock
    StockRepository stockRepository;

    @Mock
    CSVHelper csvHelper;

    @BeforeEach
    public void init() {
        stockService = new StockService(stockRepository, csvHelper);
    }

    @Test
    public void findAll(){
        var stockA = new StockEntity();
        stockA.setStock("AA");
        var stockB = new StockEntity();
        stockB.setStock("BB");

        when(stockRepository.findAll()).thenReturn(Arrays.asList(stockA, stockB));
        var result = stockService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void findAllByStock(){
        var stock = new StockEntity();
        stock.setStock("AA");

        when(stockRepository.findAllByStock("AA")).thenReturn(Arrays.asList(stock));
        var result = stockService.findAllByStock("AA");
        assertNotNull(result);
        assertEquals(stock, result.get(0));
    }
}
