CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    task_list_id BIGINT NOT NULL,
    app_user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    is_completed BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_task_list_id
        FOREIGN KEY (task_list_id)
        REFERENCES task_lists(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_app_user_id
        FOREIGN KEY (app_user_id)
        REFERENCES app_users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_tasks_task_list_id 
ON tasks(task_list_id);