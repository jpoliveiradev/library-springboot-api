CREATE TABLE rentals (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    customer_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    rental_date DATE NOT NULL,
    forecast_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
