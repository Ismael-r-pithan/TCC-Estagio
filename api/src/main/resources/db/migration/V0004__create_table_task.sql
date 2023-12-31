CREATE TABLE task (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(512) NOT NULL,
    scheduled_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    recurring BOOLEAN NOT NULL DEFAULT false,
    visibility VARCHAR(255) NOT NULL DEFAULT 'PUBLICO',
    category VARCHAR(255) NOT NULL,
    priority VARCHAR(255) NOT NULL,
    autor_id BIGINT NOT NULL,

    finished_date DATE
);


ALTER TABLE task ADD CONSTRAINT ck_visibilidade CHECK(visibility in ('PUBLICO', 'PRIVADO', 'AMIGOS'));
ALTER TABLE task ADD CONSTRAINT pk_post PRIMARY KEY (id);
ALTER TABLE task ADD CONSTRAINT fk_user FOREIGN KEY (autor_id) REFERENCES usuario(id);
ALTER TABLE task ADD CONSTRAINT ck_category CHECK(category in ('LIMPEZA', 'HIGIENE', 'ESTUDO', 'LAZER', 'SAUDE', 'TRABALHO'));
ALTER TABLE task ADD CONSTRAINT ck_priority CHECK(priority in ('URGENTE', 'IMPORTANTE', 'NORMAL'));

