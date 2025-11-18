-- ============================
-- SEED: USERS
-- ============================

MERGE INTO users (email, name, password, role)
KEY (email)
VALUES
    ('admin@example.com', 'Admin',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'ADMIN'),

    ('john@example.com', 'John Doe',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'USER'),

    ('alice@example.com', 'Alice Wonder',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'USER'),

    ('bob@example.com', 'Bob Smith',
     '$2a$10$MxuQf3.TE4qlvdeKJ27Yz.y4wDnT74FTJJ7ub7Ilq0xNwlzJ9ehLy', 'USER');

-- ============================
-- SEED: CATEGORIES
-- ============================

MERGE INTO categories (name)
KEY (name)
VALUES
    ('Programming'),
    ('Soft Skills'),
    ('DevOps'),
    ('Databases'),
    ('Machine Learning');

-- ============================
-- SEED: SKILLS
-- ============================

MERGE INTO skills (name, level, category_id)
KEY (name)
VALUES
    ('Java', 5, 1),
    ('Spring Framework', 4, 1),
    ('Kotlin', 3, 1),

    ('Communication', 4, 2),
    ('Team Leadership', 3, 2),

    ('Docker', 4, 3),
    ('Kubernetes', 2, 3),

    ('PostgreSQL', 4, 4),
    ('Redis', 2, 4),

    ('PyTorch', 1, 5);

-- ============================
-- SEED: SKILL ASSIGNMENTS
-- ============================

MERGE INTO skill_assignments (user_id, skill_id, proficiency)
KEY (user_id, skill_id)
VALUES
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
    (4, 9, 2);

-- ============================
-- END OF SEED
-- ============================
