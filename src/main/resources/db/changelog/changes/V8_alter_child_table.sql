ALTER TABLE child
DROP
CONSTRAINT fk_parent_id;
ALTER TABLE child
    ADD CONSTRAINT fk_parent_id FOREIGN KEY (parent_id) REFERENCES parent (id);