package com.adobe.bookstore.service;

import com.adobe.bookstore.domain.BookStock;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service to initialize database with books stock
 */
@Service
@Log4j2
public class DataInitializerService implements CommandLineRunner {

    @Autowired
    StockService stockService;

    /**
     * Read a JSON and save data in database.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<BookStock>> typeReference = new TypeReference<List<BookStock>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/stock.json");
        try {
            List<BookStock> bookStocks = mapper.readValue(inputStream, typeReference);
            stockService.save(bookStocks);
            log.info("Books stock has been uploaded.");
        } catch (IOException e) {
            log.error("Unable to save stock: " + e.getMessage());
        }
    }
}
