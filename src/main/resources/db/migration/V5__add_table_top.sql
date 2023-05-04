CREATE TABLE Top (
                     id SERIAL PRIMARY KEY,
                     country VARCHAR(255),
                     email VARCHAR(255),
                     is_deleted BOOLEAN,
                     name VARCHAR(255),
                     passport_id INTEGER,
                     gender VARCHAR
);