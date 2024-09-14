CREATE TABLE IF NOT EXISTS organization (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    phone VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS animal (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    birth_year INTEGER,
    birth_month INTEGER,
    weight DOUBLE PRECISION,
    size VARCHAR(255),
    specie VARCHAR(255),
    race VARCHAR(255),
    sex VARCHAR(255),
    id_organization BIGINT REFERENCES organization(id)
);

CREATE TABLE IF NOT EXISTS animal_information (
    id SERIAL PRIMARY KEY,
    animal_id BIGINT REFERENCES animal(id),
    about VARCHAR(255),
    status VARCHAR(255),
    photo VARCHAR(255),
    location VARCHAR(255)
);