package ru.element.lab.dedup;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.List;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;

@ContextConfiguration(initializers = {AbstractIntegrationTest.Initializer.class})
public class AbstractIntegrationTest {

    @ClassRule
    public static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.1.1"));

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            try {
                // kafka
                kafka.start();
                TestPropertyValues.of(
                        //kafka
                        "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers()
                ).applyTo(configurableApplicationContext.getEnvironment());
                createTopics();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void createTopics() {
        List<NewTopic> newTopics = Collections.singletonList(new NewTopic("raw-emd", 1, (short) 1));
        try (AdminClient admin = AdminClient.create(
                Collections.singletonMap(BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers()))) {
            admin.createTopics(newTopics);
        }
    }
}
