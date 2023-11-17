package org.acme.blog.exceptions;

import jakarta.ws.rs.core.Response;
import org.acme.blog.dtos.ErrorResponseDTO;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("post not found");
    }

    public Response response() {
        return Response.
                status(Response.Status.NOT_FOUND).
                entity(new ErrorResponseDTO(getMessage())).
                build();
    }
}
