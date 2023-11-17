CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT
);


CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    label VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE post_tag (
    post_id INT REFERENCES posts(id),
    tag_id INT REFERENCES tags(id),
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);

INSERT INTO posts (id, title, content) VALUES (1, 'Post 1', 'Content 1'), (2, 'Post 2', 'Content 2');
INSERT INTO tags (id, label) VALUES (1, 'tag1'), (2, 'tag2'), (3, 'tag3'), (4, 'tag4'), (5, 'tag5');
INSERT INTO post_tag (post_id, tag_id) VALUES (1, 1), (1, 2), (1, 3), (2, 3), (2, 4), (2, 5);
