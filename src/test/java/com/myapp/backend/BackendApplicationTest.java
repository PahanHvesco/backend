package com.myapp.backend;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class BackendApplicationTest {

    @Test
    void testMain() {
        // Проверяем, что SpringApplication.run вызывается с правильными аргументами
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            BackendApplication.main(new String[]{});
            springApplicationMockedStatic.verify(() -> SpringApplication.run(BackendApplication.class, new String[]{}));
        }
    }

    @Test
    void testMainWithCustomArgs() {
        // Проверяем, что SpringApplication.run вызывается с правильными аргументами при наличии пользовательских аргументов
        String[] args = {"--customArg=test"};
        try (MockedStatic<SpringApplication> springApplicationMockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            BackendApplication.main(args);
            springApplicationMockedStatic.verify(() -> SpringApplication.run(BackendApplication.class, args));
        }
    }
}
