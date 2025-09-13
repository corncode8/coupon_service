package com.payments;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
class DemoApplicationTests {


    @TestConfiguration(proxyBeanMethods = false)
    static class LocalDevTestcontainersConfig{

        @Bean
        @ServiceConnection
        public MySQLContainer mySQLContainer() {
            return new MySQLContainer("mysql:8.0.33")
                    .withUsername("admin")
                    .withPassword("1234")
                    .withDatabaseName("testdb");
        }
    }

}
