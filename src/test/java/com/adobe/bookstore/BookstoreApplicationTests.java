package com.adobe.bookstore;

import com.adobe.bookstore.dto.CreateOrderRequest;
import com.adobe.bookstore.dto.CreateOrderResponse;
import com.adobe.bookstore.exceptions.InvalidBookNameException;
import com.adobe.bookstore.exceptions.NoStockException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.adobe.bookstore.TestHarness.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookstoreApplicationTests {

    @LocalServerPort
    int randomServerPort;

    @Test
    void givenOrderUri_WhenPOST_thenReturnOrderId() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/order";
        final URI uri = new URI(baseUrl);
        final var request = createExampleCreateOrderRequest();

        // when
        final var response = restTemplate.postForEntity(uri, request, CreateOrderResponse.class);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void givenOrderUri_WhenPOSTAndNoStock_thenReturnNoStockError() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/order";
        final URI uri = new URI(baseUrl);
        final var request = createExampleCreateOrderRequestWithoutStock();

        // when
        try {
            restTemplate.postForEntity(uri, request, CreateOrderResponse.class);
        } catch (HttpClientErrorException ex) {
            // then
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            final var bookOrder = request.getBookOrderList().get(0);
            final var expectedMessage = String.format(NoStockException.formattedMessage, bookOrder);
            assertEquals(expectedMessage, ex.getResponseBodyAsString());
        }
    }

    @Test
    void givenOrderUri_WhenPOSTInvalidBookName_thenReturnInvalidBookError() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/order";
        final URI uri = new URI(baseUrl);
        final var request = createExampleCreateOrderRequestWithWrongName();

        // when
        try {
            restTemplate.postForEntity(uri, request, CreateOrderResponse.class);
        } catch (HttpClientErrorException ex) {
            // then
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            final var bookOrder = request.getBookOrderList().get(0);
            final var expectedMessage = String.format(InvalidBookNameException.formattedMessage, bookOrder.getName());
            assertEquals(expectedMessage, ex.getResponseBodyAsString());
        }
    }

    @Test
    void givenOrderUri_WhenPOSTEmptyOrder_thenReturn400() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/order";
        final URI uri = new URI(baseUrl);
        final var request = new CreateOrderRequest();

        // when
        try {
            restTemplate.postForEntity(uri, request, CreateOrderResponse.class);
        } catch (HttpClientErrorException ex) {
            // then
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    @Test
    void givenOrderUri_WhenPOSTNegativeQuantity_thenReturn400() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/order";
        final URI uri = new URI(baseUrl);
        final var request = createExampleCreateOrderRequestWithNegative();

        // when
        try {
            restTemplate.postForEntity(uri, request, CreateOrderResponse.class);
        } catch (HttpClientErrorException ex) {
            // then
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    @Test
    void givenOrderUri_WhenGETCSV_thenReturnCSVFile() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final var parameter = "csv";
        final String baseUrl = "http://localhost:" + randomServerPort + "/order?format=" + parameter;
        final URI uri = new URI(baseUrl);

        // when
        final var response = restTemplate.getForEntity(uri, byte[].class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final var responseHeaders = response.getHeaders();
        assertEquals("text/csv", Objects.requireNonNull(responseHeaders.getContentType()).toString());
        assertEquals("orders.csv", responseHeaders.getContentDisposition().getFilename());
        assertTrue(responseHeaders.getContentDisposition().isAttachment());
        assertTrue(responseHeaders.getContentLength() > 0);
    }

    @Test
    void givenOrderUri_WhenGETJson_thenReturnJSONFile() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final var parameter = "json";
        final String baseUrl = "http://localhost:" + randomServerPort + "/order?format=" + parameter;
        final URI uri = new URI(baseUrl);

        // when
        final var response = restTemplate.getForEntity(uri, byte[].class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final var responseHeaders = response.getHeaders();
        assertEquals(MediaType.APPLICATION_JSON_VALUE, Objects.requireNonNull(responseHeaders.getContentType()).toString());
        assertEquals("orders.json", responseHeaders.getContentDisposition().getFilename());
        assertTrue(responseHeaders.getContentDisposition().isAttachment());
        assertTrue(responseHeaders.getContentLength() > 0);
    }

    @Test
    void givenOrderUri_WhenGETWithoutParameter_thenReturn400() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/order";
        final URI uri = new URI(baseUrl);

        // when
        try {
            restTemplate.getForEntity(uri, byte[].class);
        } catch (HttpClientErrorException ex) {
            // then
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }

    @Test
    void givenOrderUri_WhenGETWrongParameter_thenReturn400() throws URISyntaxException {
        // given
        final RestTemplate restTemplate = new RestTemplate();
        final var parameter = "test";
        final String baseUrl = "http://localhost:" + randomServerPort + "/order?format=" + parameter;
        final URI uri = new URI(baseUrl);

        // when
        try {
            restTemplate.getForEntity(uri, byte[].class);
        } catch (HttpClientErrorException ex) {
            // then
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }
    }
}
