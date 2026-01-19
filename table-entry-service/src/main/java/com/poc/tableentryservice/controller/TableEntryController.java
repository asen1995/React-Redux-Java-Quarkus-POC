package com.poc.tableentryservice.controller;

import com.poc.tableentryservice.entity.TableEntry;
import com.poc.tableentryservice.service.TableEntryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;
import java.util.List;

@Path("/api/entries")
public class TableEntryController {

    private final TableEntryService service;

    public TableEntryController(TableEntryService service) {
        this.service = service;
    }

    @GET
    public List<TableEntry> getAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@RestPath Long id) {
        return service.findById(id)
                .map(entry -> Response.ok(entry).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response create(TableEntry entry) {
        TableEntry created = service.create(entry);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@RestPath Long id, TableEntry entry) {
        return service.update(id, entry)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@RestPath Long id) {
        if (service.delete(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
