package org.acme.blog.dtos;

import org.acme.blog.entities.Post;
import org.acme.blog.entities.Tag;

public class DetailPostResponseDTO {
    private int id;
    private String title;
    private String content;
    private String[] tags;

    public DetailPostResponseDTO(Post post) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        tags = post.getTags().stream()
                .map(Tag::getLabel)
                .toArray(String[]::new);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String[] getTags() {
        return tags;
    }

    public int getId() {
        return id;
    }
}
