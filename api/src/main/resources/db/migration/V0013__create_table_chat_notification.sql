CREATE TABLE chat_notification (
                                      id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                      sender_id BIGINT NOT NULL,
                                      sender_name VARCHAR(255) NOT NULL
);

ALTER TABLE chat_notification ADD CONSTRAINT pk_chat_notification PRIMARY KEY (id);
ALTER TABLE chat_notification ADD CONSTRAINT fk_sender_id FOREIGN KEY (sender_id) REFERENCES usuario(id);

