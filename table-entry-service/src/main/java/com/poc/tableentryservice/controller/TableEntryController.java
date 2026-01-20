package com.poc.tableentryservice.controller;

import com.poc.tableentryservice.aspect.Logged;
import com.poc.tableentryservice.aspect.Timed;
import com.poc.tableentryservice.dto.CreateTableEntryDto;
import com.poc.tableentryservice.dto.PagedResponse;
import com.poc.tableentryservice.dto.TableEntryDto;
import com.poc.tableentryservice.service.TableEntryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

/**
 * REST controller for managing table entries.
 * Provides endpoints for CRUD operations on table entries.
 */
@Path("/api/entries")
@Logged
@Timed
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
     * Retrieves table entries with pagination and sorting.
     *
     * @param page          the page number (0-indexed, default 0)
     * @param size          the page size (default 10, max 50)
     * @param sortBy        the field to sort by (default: createdAt)
     * @param sortDirection the sort direction: "asc" or "desc" (default: desc)
     * @return paginated list of entries
     */
    @GET
    public PagedResponse<TableEntryDto> getAll(
            @RestQuery @DefaultValue("0") int page,
            @RestQuery @DefaultValue("10") int size,
            @RestQuery @DefaultValue("createdAt") String sortBy,
            @RestQuery @DefaultValue("desc") String sortDirection) {
        return service.findAll(page, size, sortBy, sortDirection);
    }

    /**
     * Retrieves a table entry by its ID.
     *
     * @param id the entry ID
     * @return the entry, or 404 Not Found if not exists
     */
    @GET
    @Path("/{id}")
    public TableEntryDto getById(@RestPath Long id) {
        return service.findById(id);
    }

    /**
     * Creates a new table entry.
     *
     * @param dto the entry data to create
     * @return 201 Created with the new entry
     */
    @POST
    public Response create(CreateTableEntryDto dto) {
        TableEntryDto created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * Updates an existing table entry.
     *
     * @param id  the ID of the entry to update
     * @param dto the new entry data
     * @return the updated entry, or 404 Not Found if not exists
     */
    @PUT
    @Path("/{id}")
    public TableEntryDto update(@RestPath Long id, CreateTableEntryDto dto) {
        return service.update(id, dto);
    }

    /**
     * Deletes a table entry by its ID.
     *
     * @param id the ID of the entry to delete
     * @return 204 No Content if deleted, or 404 Not Found if not exists
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@RestPath Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
