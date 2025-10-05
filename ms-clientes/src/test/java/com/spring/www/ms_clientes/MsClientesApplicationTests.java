package com.spring.www.ms_clientes;

import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@EnabledIfEnvironmentVariable(named = "USE_TESTCONTAINERS", matches = "true")
class MsClientesApplicationTests {

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("clientes_test")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", mysql::getJdbcUrl);
        r.add("spring.datasource.username", mysql::getUsername);
        r.add("spring.datasource.password", mysql::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Test void contextLoads() {}
}
