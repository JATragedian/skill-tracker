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
        ON DELETE SET NULL,

    CONSTRAINT chk_skill_level CHECK (level >= 0 AND level <= 100)
);

-- ============= USERS =============
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    CONSTRAINT chk_user_role CHECK (role IN ('USER', 'ADMIN'))
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
        ON DELETE CASCADE,

    CONSTRAINT uq_user_skill UNIQUE (user_id, skill_id),

    CONSTRAINT chk_proficiency CHECK (proficiency >= 0 AND proficiency <= 100)
);

-- ============= Error Logs =============
CREATE TABLE error_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    reason VARCHAR(500) NOT NULL,
    timestamp TIMESTAMP NOT NULL,

    CONSTRAINT fk_errorlogs_user
         FOREIGN KEY (user_id) REFERENCES users(id)
         ON DELETE CASCADE,

    CONSTRAINT fk_errorlogs_skill
         FOREIGN KEY (skill_id) REFERENCES skills(id)
         ON DELETE CASCADE
);