package org.acme.blog.services;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.blog.dtos.PostRequestDTO;
import org.acme.blog.entities.Post;
import org.acme.blog.entities.Tag;
import org.acme.blog.exceptions.PostNotFoundException;
import org.acme.blog.repositories.PostRepository;
import org.acme.blog.repositories.TagRepository;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class BlogService {
    @Inject
    PostRepository postRepository;
    @Inject
    TagRepository tagRepository;

    public Post detailPost(int id) {
        return postRepository.getById(id);
    }

    public Post createPost(PostRequestDTO request) {
        Post post = transformRequestDtoToEntity(request);
        post.setTags(manageTags(request));

        return postRepository.create(post);
    }

    public Post updatePost(PostRequestDTO request) {
        Post currentPost = detailPost(request.getId());
        if (currentPost == null) {
            throw new PostNotFoundException();
        }

        Post post = transformRequestDtoToEntity(request);
        post.setId(request.getId());
        post.setTags(manageTags(request));


        return postRepository.update(post);
    }

    private Post transformRequestDtoToEntity(PostRequestDTO request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return post;
    }

    private Set<Tag> manageTags(PostRequestDTO request) {
        String[] tagLabels = request.manageTags();

        Set<Tag> tags = new HashSet<>();
        for (String tagLabel : tagLabels) {
            Tag tag = tagRepository.getOrInsert(tagLabel);
            tags.add(tag);
        }

        return tags;
    }
}
