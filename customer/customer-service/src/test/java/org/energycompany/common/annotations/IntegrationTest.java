package org.energycompany.common.annotations;

import org.energycompany.CustomerServiceApplication;
import org.energycompany.common.initializers.WireMockInitializer;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Tag("integration")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CustomerServiceApplication.class}
)
@ContextConfiguration(initializers = {
        WireMockInitializer.class
})
@ActiveProfiles({"local", "test"})
@AutoConfigureMockMvc
@Commit
public abstract class IntegrationTest {
}
