package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.blog.dtos.DetailPostResponseDTO;
import org.acme.blog.dtos.PostRequestDTO;
import org.acme.blog.entities.Post;
import org.acme.blog.exceptions.PostNotFoundException;
import org.acme.blog.services.BlogService;

import java.util.Set;

@Path("/posts")
public class PostResource {

    @Inject
    BlogService blogService;

    @GET
    @Path("/{id}")
    public Response detailPost(@PathParam("id") int postId) {
        Post post = blogService.detailPost(postId);
        if (post == null) {
            return new PostNotFoundException().response();
        }
        return Response.
                status(Response.Status.OK).
                entity(new DetailPostResponseDTO(post)).
                build();
    }

    @POST
    @Transactional
    public Response createPost(@Valid PostRequestDTO request) {
        try {
            Post post = blogService.createPost(request);

            return Response
                    .status(Response.Status.OK)
                    .entity(new DetailPostResponseDTO(post))
                    .build();
        } catch (ConstraintViolationException e) {
            return responseErrValidation(e);
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updatePost(@PathParam("id") int postId, @Valid PostRequestDTO request) {
        try {
            request.setId(postId);
            Post post = blogService.updatePost(request);

            return Response
                    .status(Response.Status.OK)
                    .entity(new DetailPostResponseDTO(post))
                    .build();
        } catch (ConstraintViolationException e) {
            return responseErrValidation(e);
        } catch (PostNotFoundException e) {
            return e.response();
        }
    }

    private Response responseErrValidation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String errorMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Validation failed");

        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
