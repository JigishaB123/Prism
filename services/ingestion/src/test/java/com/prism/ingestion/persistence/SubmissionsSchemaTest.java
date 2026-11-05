package com.prism.ingestion.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prism.ingestion.TestcontainersConfig;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Checks the V1 migration directly with SQL. There is no JPA entity yet, so these tests pin
 * down what the database itself guarantees, independent of any Java code.
 */
@SpringBootTest
@Import(TestcontainersConfig.class)
class SubmissionsSchemaTest {

    private static final String INSERT = """
            INSERT INTO submissions (id, source, title, content_type, content, status)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void validRowIsStoredWithDefaultTimestamps() {
        UUID id = UUID.randomUUID();

        jdbc.update(INSERT, id, "web", "Hello", "text/plain", "Some content", "RECEIVED");

        OffsetDateTime createdAt = jdbc.queryForObject(
                "SELECT created_at FROM submissions WHERE id = ?", OffsetDateTime.class, id);
        assertThat(createdAt).isNotNull();
    }

    @Test
    void unknownStatusIsRejected() {
        assertThatThrownBy(() -> jdbc.update(
                INSERT, UUID.randomUUID(), "web", null, "text/plain", "content", "BOGUS"))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("submissions_status_chk");
    }

    @Test
    void missingContentIsRejected() {
        assertThatThrownBy(() -> jdbc.update(
                INSERT, UUID.randomUUID(), "web", null, "text/plain", null, "RECEIVED"))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
