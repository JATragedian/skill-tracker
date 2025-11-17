-- ============================
-- SEED: USERS
-- ============================

INSERT INTO users (name, email, password, role) VALUES
    ('Admin', 'admin@example.com',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'ADMIN'),
    ('John Doe', 'john@example.com',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'USER'),
    ('Alice Wonder', 'alice@example.com',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'USER'),
    ('Bob Smith', 'bob@example.com',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'USER')
ON CONFLICT DO NOTHING;

-- ============================
-- SEED: CATEGORIES
-- ============================

INSERT INTO categories (name) VALUES
    ('Programming'),
    ('Soft Skills'),
    ('DevOps'),
    ('Databases'),
    ('Machine Learning')
ON CONFLICT DO NOTHING;

-- ============================
-- SEED: SKILLS
-- ============================

INSERT INTO skills (name, level, category_id) VALUES
    ('Java', 5, 1),
    ('Spring Framework', 4, 1),
    ('Kotlin', 3, 1),

    ('Communication', 4, 2),
    ('Team Leadership', 3, 2),

    ('Docker', 4, 3),
    ('Kubernetes', 2, 3),

    ('PostgreSQL', 4, 4),
    ('Redis', 2, 4),

    ('PyTorch', 1, 5)
ON CONFLICT DO NOTHING;

-- ============================
-- SEED: SKILL ASSIGNMENTS
-- ============================

INSERT INTO skill_assignments (user_id, skill_id, proficiency) VALUES
    -- Admin
    (1, 1, 5),
    (1, 2, 4),
    (1, 6, 4),

    -- John
    (2, 1, 3),
    (2, 4, 4),
    (2, 8, 3),

    -- Alice
    (3, 3, 2),
    (3, 10, 1),

    -- Bob
    (4, 5, 3),
    (4, 9, 2)
ON CONFLICT DO NOTHING;

-- ============================
-- END OF SEED
-- ============================
