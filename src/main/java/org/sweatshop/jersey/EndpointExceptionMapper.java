package org.sweatshop.jersey;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.sweatshop.api.EndpointException;

@Provider
public class EndpointExceptionMapper implements ExceptionMapper<EndpointException> {
    public Response toResponse(EndpointException exception) {
        return Response.status(exception.getResponseCode())
                .entity(exception.getResponseMap())
                .type("application/problem+json")
                .build();
    }
}