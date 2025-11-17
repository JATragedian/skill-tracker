-- ============================
-- SEED: USERS
-- ============================

INSERT INTO users (id, name, email, password) VALUES
    (1, 'Admin', 'admin@example.com',
        '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy'),
    (2, 'John Doe', 'john@example.com',
        '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy'),
    (3, 'Alice Wonder', 'alice@example.com',
        '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy'),
    (4, 'Bob Smith', 'bob@example.com',
        '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy')
ON CONFLICT DO NOTHING;

-- ============================
-- SEED: CATEGORIES
-- ============================

INSERT INTO categories (id, name) VALUES
    (1, 'Programming'),
    (2, 'Soft Skills'),
    (3, 'DevOps'),
    (4, 'Databases'),
    (5, 'Machine Learning')
ON CONFLICT DO NOTHING;

-- ============================
-- SEED: SKILLS
-- ============================

INSERT INTO skills (id, name, level, category_id) VALUES
    (1, 'Java', 5, 1),
    (2, 'Spring Framework', 4, 1),
    (3, 'Kotlin', 3, 1),

    (4, 'Communication', 4, 2),
    (5, 'Team Leadership', 3, 2),

    (6, 'Docker', 4, 3),
    (7, 'Kubernetes', 2, 3),

    (8, 'PostgreSQL', 4, 4),
    (9, 'Redis', 2, 4),

    (10, 'PyTorch', 1, 5)
ON CONFLICT DO NOTHING;

-- ============================
-- SEED: SKILL ASSIGNMENTS
-- ============================

INSERT INTO skill_assignments (id, user_id, skill_id, proficiency) VALUES
    -- Admin
    (1, 1, 1, 5),
    (2, 1, 2, 4),
    (3, 1, 6, 4),

    -- John
    (4, 2, 1, 3),
    (5, 2, 4, 4),
    (6, 2, 8, 3),

    -- Alice
    (7, 3, 3, 2),
    (8, 3, 10, 1),

    -- Bob
    (9, 4, 5, 3),
    (10, 4, 9, 2)
ON CONFLICT DO NOTHING;

-- ============================
-- END OF SEED
-- ============================
