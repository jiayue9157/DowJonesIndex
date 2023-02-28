package com.yuejia.stocks.service;

import com.yuejia.stocks.model.StockEntity;
import com.yuejia.stocks.repository.StockRepository;
import com.yuejia.stocks.utils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final CSVHelper csvHelper;

    @Autowired
    public StockService(StockRepository stockRepository, CSVHelper csvHelper) {
        this.stockRepository = stockRepository;
        this.csvHelper = csvHelper;
    }

    public void bulkSave(MultipartFile file) {
        try {
            csvHelper.csvToStock(file.getInputStream(), stockRepository);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public void singleSave(StockEntity entity){
        stockRepository.save(entity);
    }

    public List<StockEntity> findAll() {
        return stockRepository.findAll();
    }

    public List<StockEntity> findAllByStock(String stock) { return stockRepository.findAllByStock(stock); }
}
