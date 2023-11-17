package org.acme.blog.repositories;

import org.acme.blog.entities.Tag;

public interface TagRepository {
    Tag getOrInsert(String label);
}


