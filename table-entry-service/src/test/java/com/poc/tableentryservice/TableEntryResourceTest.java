package com.poc.tableentryservice;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class TableEntryResourceTest {

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

        // Get all entries
        given()
            .when()
            .get("/api/entries")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }
}
