package com.vai.extraction_consumer.service;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;


public class UnirestClient {
    private static final Logger logger = LoggerFactory.getLogger(UnirestClient.class);

    // Hàm GET Request với logging và đo thời gian
    public HttpResponse<JsonNode> get(String url) {
        long startTime = System.currentTimeMillis();
        try {
            logger.info("Starting GET request to URL: {}", url);

            HttpResponse<JsonNode> response = Unirest.get(url).asJson();

            long duration = System.currentTimeMillis() - startTime;
            logger.info("GET request to URL: {} completed with status: {} in {} ms", url, response.getStatus(), duration);

            return response;
        } catch (UnirestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("GET request to URL: {} failed after {} ms", url, duration, e);
            throw new RuntimeException("Error during GET request", e);
        }
    }

    // Hàm POST Request với logging và đo thời gian
    public HttpResponse<JsonNode> post(String url, String body) {
        long startTime = System.currentTimeMillis();
        try {
            logger.info("Starting POST request to URL: {}", url);
            logger.debug("Request Body: {}", body);

            HttpResponse<JsonNode> response = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(body)
                    .asJson();

            long duration = System.currentTimeMillis() - startTime;
            logger.info("POST request to URL: {} completed with status: {} in {} ms", url, response.getStatus(), duration);

            return response;
        } catch (UnirestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("POST request to URL: {} failed after {} ms", url, duration, e);
            throw new RuntimeException("Error during POST request", e);
        }
    }

    // Hàm POST Request upload file với logging và đo thời gian
    public HttpResponse<JsonNode> postFile(String url, Map<String, Object> fields) {
        long startTime = System.currentTimeMillis();
        try {
            logger.info("Starting POST request to upload file to URL: {}", url);

            // Gửi request với multipart/form-data để upload file
            HttpRequestWithBody httpRequestWithBody = Unirest.post(url);
            fields.forEach(httpRequestWithBody::field);
            HttpResponse<JsonNode> response = httpRequestWithBody.asJson();

            long duration = System.currentTimeMillis() - startTime;
            logger.info("POST file upload to URL: {} completed with status: {} in {} ms", url, response.getStatus(), duration);

            return response;
        } catch (UnirestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("POST file upload to URL: {} failed after {} ms", url, duration, e);
            throw new RuntimeException("Error during POST file upload", e);
        }
    }
}