package com.adobe.bookstore.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adobe.bookstore.TestHarness.createExampleFlattenedOrderList;
import static com.adobe.bookstore.TestHarness.createExampleUnflattenedOrderList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderUtilsTest {

    @Test
    void flatten_returnsFlattenedOrder() {
        // given
        var input = createExampleUnflattenedOrderList();
        var expectedOutput = createExampleFlattenedOrderList();

        // when
        var outcome = OrderUtils.flatten(input);

        // then
        assertEquals(expectedOutput, outcome);
    }
}