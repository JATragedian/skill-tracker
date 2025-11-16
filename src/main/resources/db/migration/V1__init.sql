-- ============= CATEGORIES =============
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- ============= SKILLS =============
CREATE TABLE skills (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    level INT NOT NULL,
    category_id BIGINT,
    CONSTRAINT fk_skills_category
        FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE SET NULL
);

-- ============= USERS =============
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- ============= SKILL ASSIGNMENTS =============
CREATE TABLE skill_assignments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    proficiency INT NOT NULL,

    CONSTRAINT fk_skillassignment_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_skillassignment_skill
        FOREIGN KEY (skill_id) REFERENCES skills(id)
        ON DELETE CASCADE
);

-- ============= Error Logs =============
CREATE TABLE error_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    reason VARCHAR(500) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);