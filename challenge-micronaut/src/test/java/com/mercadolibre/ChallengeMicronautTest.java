package com.mercadolibre;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;

@MicronautTest
public class ChallengeMicronautTest {

    @Inject
    EmbeddedApplication application;

    @Test
    void testItWorks() {
        //Assertions.assertTrue(application.isRunning());
    }

}
