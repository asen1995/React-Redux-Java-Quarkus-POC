package com.poc.tableentryservice.controller;

import com.poc.tableentryservice.entity.TableEntry;
import com.poc.tableentryservice.service.TableEntryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;
import java.util.List;

/**
 * REST controller for managing table entries.
 * Provides endpoints for CRUD operations on {@link TableEntry} entities.
 */
@Path("/api/entries")
public class TableEntryController {

    private final TableEntryService service;

    /**
     * Constructs the controller with the required service.
     *
     * @param service the table entry service
     */
    public TableEntryController(TableEntryService service) {
        this.service = service;
    }

    /**
     * Retrieves all table entries.
     *
     * @return list of all entries
     */
    @GET
    public List<TableEntry> getAll() {
        return service.findAll();
    }

    /**
     * Retrieves a table entry by its ID.
     *
     * @param id the entry ID
     * @return 200 OK with the entry, or 404 Not Found
     */
    @GET
    @Path("/{id}")
    public Response getById(@RestPath Long id) {
        return service.findById(id)
                .map(entry -> Response.ok(entry).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Creates a new table entry.
     *
     * @param entry the entry to create
     * @return 201 Created with the new entry
     */
    @POST
    public Response create(TableEntry entry) {
        TableEntry created = service.create(entry);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * Updates an existing table entry.
     *
     * @param id    the ID of the entry to update
     * @param entry the new entry data
     * @return 200 OK with the updated entry, or 404 Not Found
     */
    @PUT
    @Path("/{id}")
    public Response update(@RestPath Long id, TableEntry entry) {
        return service.update(id, entry)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Deletes a table entry by its ID.
     *
     * @param id the ID of the entry to delete
     * @return 204 No Content if deleted, or 404 Not Found
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@RestPath Long id) {
        if (service.delete(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
