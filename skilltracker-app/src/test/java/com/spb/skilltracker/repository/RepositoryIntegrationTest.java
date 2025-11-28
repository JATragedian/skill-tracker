package com.spb.skilltracker.repository;

import com.spb.skilltracker.entity.CategoryEntity;
import com.spb.skilltracker.entity.SkillAssignmentEntity;
import com.spb.skilltracker.entity.SkillEntity;
import com.spb.skilltracker.entity.user.UserEntity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RepositoryIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("skilltracker")
            .withUsername("tester")
            .withPassword("tester");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration,classpath:db/data-common");
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillAssignmentRepository skillAssignmentRepository;

    @Test
    void flyway_should_load_seed_data() {

        // When
        List<CategoryEntity> categories = categoryRepository.findAll();
        List<SkillEntity> skills = skillRepository.findAll();
        List<UserEntity> users = userRepository.findAll();

        // Then
        assertAll(
                () -> assertFalse(categories.isEmpty(), "Categories should be seeded"),
                () -> assertFalse(skills.isEmpty(), "Skills should be seeded"),
                () -> assertFalse(users.isEmpty(), "Users should be seeded")
        );
    }

    @Test
    void repositories_should_save_and_fetch_assignments() {

        // Given
        UserEntity user = userRepository.findByEmail("john@example.com").orElseThrow();
        CategoryEntity category = categoryRepository.findAll().getFirst();
        SkillEntity skill = skillRepository.save(new SkillEntity("GraphQL", 2, category));

        // When
        SkillAssignmentEntity assignment = skillAssignmentRepository.save(new SkillAssignmentEntity(user, skill, 9));
        List<SkillAssignmentEntity> assignments = skillAssignmentRepository.findByUserId(user.getId());

        // Then
        assertAll(
                () -> assertNotNull(assignment.getId(), "Assignment should have id"),
                () -> assertTrue(assignments.stream().anyMatch(it -> skill.equals(it.getSkill())), "Assignment should be retrievable"),
                () -> assertEquals(9, assignment.getProficiency(), "Proficiency should be persisted")
        );
    }
}
