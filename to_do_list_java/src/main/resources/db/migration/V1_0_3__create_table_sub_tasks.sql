CREATE TABLE sub_tasks (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL,
    app_user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    is_completed BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_task_id
        FOREIGN KEY (task_id)
        REFERENCES tasks(id)
        ON DELETE CASCADE,
    
    CONSTRAINT fk_app_user_id
        FOREIGN KEY (app_user_id)
        REFERENCES app_users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_sub_tasks_task_id 
ON sub_tasks(task_id);