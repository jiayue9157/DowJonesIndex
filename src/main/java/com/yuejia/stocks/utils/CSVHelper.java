package com.yuejia.stocks.utils;

import com.yuejia.stocks.model.StockEntity;
import com.yuejia.stocks.repository.StockRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHelper {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<StockEntity> csvToStock(InputStream is, StockRepository stockRepository) {
        try (var fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             var csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            var csvRecords = csvParser.getRecords();
            var stocks = new ArrayList<StockEntity>();

            StockEntity stock;
            for (var csvRecord : csvRecords) {
                stock = new StockEntity();
                //TODO: invalid/duplicate check
                stock.setQuarter(csvRecord.get("quarter"));
                stock.setStock(csvRecord.get("stock"));
                stock.setDate(csvRecord.get("date"));
                stock.setOpen(csvRecord.get("open"));
                stock.setHigh(csvRecord.get("high"));
                stock.setLow(csvRecord.get("low"));
                stock.setClose(csvRecord.get("close"));
                stock.setVolume(csvRecord.get("volume"));
                stock.setPercentChangePrice(csvRecord.get("percent_change_price"));
                stock.setPercentChangeVolumeOverLastWeek(csvRecord.get("percent_change_volume_over_last_wk"));
                stock.setPreviousWeeksVolume(csvRecord.get("previous_weeks_volume"));
                stock.setNextWeeksOpen(csvRecord.get("next_weeks_open"));
                stock.setNextWeeksClose(csvRecord.get("next_weeks_close"));
                stock.setPercentChangeNextWeeksPrice(csvRecord.get("percent_change_next_weeks_price"));
                stock.setDaysToNextDividend(csvRecord.get("days_to_next_dividend"));
                stock.setPercentReturnNextDividend(csvRecord.get("percent_return_next_dividend"));
                stocks.add(stock);
                stockRepository.save(stock);
            }
            return stocks;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
