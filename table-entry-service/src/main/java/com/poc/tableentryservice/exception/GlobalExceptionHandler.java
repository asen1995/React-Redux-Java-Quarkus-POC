package com.poc.tableentryservice.exception;

import com.poc.tableentryservice.dto.ErrorResponse;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

/**
 * Global exception handler that catches all unhandled exceptions
 * and returns a consistent error response format.
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {
        String path = uriInfo != null ? uriInfo.getPath() : "unknown";

        // Handle JAX-RS WebApplicationException (includes our EntryNotFoundException)
        if (exception instanceof WebApplicationException webEx) {
            Response response = webEx.getResponse();
            int status = response.getStatus();
            String statusName = Response.Status.fromStatusCode(status).getReasonPhrase();

            LOG.warnf("WebApplicationException at %s: %s", path, exception.getMessage());

            ErrorResponse error = ErrorResponse.of(
                    status,
                    statusName,
                    exception.getMessage(),
                    path
            );
            return Response.status(status).entity(error).build();
        }

        // Handle all other unexpected exceptions
        LOG.errorf(exception, "Unexpected exception at %s", path);

        ErrorResponse error = ErrorResponse.of(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                path
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
