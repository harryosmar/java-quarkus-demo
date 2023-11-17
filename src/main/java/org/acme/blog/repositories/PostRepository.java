package org.acme.blog.repositories;

import org.acme.blog.entities.Post;

public interface PostRepository {
    Post getById(int id);
    Post create(Post post);

    Post update(Post post);
}


