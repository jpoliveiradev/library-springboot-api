CREATE TABLE IF NOT EXISTS rental_status (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO rental_status (id, name) VALUES
    (1, 'Pendente'), (2, 'Entregue no Prazo'), (3, 'Entregue com Atraso');
