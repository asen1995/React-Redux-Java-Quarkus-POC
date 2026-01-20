package com.poc.tableentryservice.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Exception thrown when a table entry is not found.
 * Returns HTTP 404 Not Found.
 */
public class EntryNotFoundException extends WebApplicationException {

    public EntryNotFoundException(Long id) {
        super("Entry not found with id: " + id, Response.Status.NOT_FOUND);
    }
}
