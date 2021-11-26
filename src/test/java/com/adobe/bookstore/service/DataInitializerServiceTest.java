package com.adobe.bookstore.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataInitializerServiceTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private DataInitializerService sut;

    @Test
    void run_returnNothing_whenNoErrors() {
        // given
        final var input = Strings.EMPTY;

        // when
        sut.run(input);

        // then
        verify(stockService).save(any());
    }
}