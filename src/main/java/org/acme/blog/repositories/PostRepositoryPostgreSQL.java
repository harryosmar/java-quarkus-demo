package org.acme.blog.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.blog.entities.Post;

@ApplicationScoped
public class PostRepositoryPostgreSQL implements PostRepository {
    @Inject
    EntityManager entityManager;

    @Override
    public Post getById(int id) {
        return entityManager.find(Post.class, id);
    }

    @Override
    public Post create(Post post) {
        // Persist the post with associated tags
        entityManager.persist(post);

        return post;
    }

    @Override
    public Post update(Post post) {
        // Persist the post with associated tags
        entityManager.merge(post);

        return post;
    }
}
