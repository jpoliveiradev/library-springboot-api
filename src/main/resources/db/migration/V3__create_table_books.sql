CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    release_year INT NOT NULL,
    quantity INT NOT NULL,
    publisher_id BIGINT NOT NULL,
    FOREIGN KEY (publisher_id) REFERENCES publishers(id)
);
