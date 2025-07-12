CREATE TABLE task_lists (
    id BIGSERIAL PRIMARY KEY,
    app_user_id BIGINT NOT NULL ,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_completed BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_app_user_id
        FOREIGN KEY (app_user_id)
        REFERENCES app_users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_task_lists_app_user_id 
ON task_lists(app_user_id);