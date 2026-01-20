package com.poc.tableentryservice.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for {@link TableEntryController}.
 * Tests the REST API endpoints with a running Quarkus application.
 */
@QuarkusTest
class TableEntryControllerTest {

    @Test
    void testCreateAndGetEntries() {
        // Create an entry
        given()
            .contentType(ContentType.JSON)
            .body("{\"numberValue\": 42, \"selectorValue\": \"Option A\", \"freeText\": \"Test text\"}")
            .when()
            .post("/api/entries")
            .then()
            .statusCode(201)
            .body("numberValue", equalTo(42))
            .body("selectorValue", equalTo("Option A"))
            .body("freeText", equalTo("Test text"));

        // Get all entries (returns PagedResponse)
        given()
            .when()
            .get("/api/entries")
            .then()
            .statusCode(200)
            .body("content.size()", greaterThan(0))
            .body("totalElements", greaterThan(0));
    }
}
