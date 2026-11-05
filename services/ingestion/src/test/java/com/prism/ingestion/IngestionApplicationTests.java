package com.prism.ingestion;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfig.class)
class IngestionApplicationTests {

    @Test
    void contextLoads() {
        // Passes if the app starts, connects to Postgres, and Flyway runs.
    }
}
