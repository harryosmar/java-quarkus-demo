package org.acme.blog.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.blog.entities.Tag;

@ApplicationScoped
public class TagRepositoryPostgreSQL implements TagRepository {
    @Inject
    EntityManager entityManager;

    @Override
    public Tag getOrInsert(String label) {
        return entityManager.createQuery("SELECT t FROM Tag t WHERE t.label = :label", Tag.class)
                .setParameter("label", label)
                .getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setLabel(label);
                    entityManager.persist(newTag);
                    return newTag;
                });
    }
}
