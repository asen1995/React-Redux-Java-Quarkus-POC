package com.poc.tableentryservice;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Main entry point for the Table Entry Service application.
 * This class bootstraps the Quarkus application.
 */
@QuarkusMain
public class Application {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
