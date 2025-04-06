package org.energycompany.common.annotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.energycompany.AuthenticationServiceApplication;
import org.energycompany.common.initializers.WireMockInitializer;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Tag("integration")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AuthenticationServiceApplication.class}
)
@ContextConfiguration(initializers = {
        WireMockInitializer.class
})
@ActiveProfiles({"local", "test"})
@AutoConfigureMockMvc
@Commit
public abstract class IntegrationTest {

    @Autowired
    protected ObjectMapper objectMapper;
}
