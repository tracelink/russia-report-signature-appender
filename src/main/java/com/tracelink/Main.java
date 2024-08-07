package com.tracelink;

import schedulers.DocumentSchedulerService;
import services.AuthService;
import utilities.StaticProperties;

public class Main {

    public static void main(String[] args) {
        AuthService authService;

        String configFile = null;

        if (args.length != 1) {
            System.out.println("Usage: java -jar YourJarFile.jar <path_to_config_file>");
            System.exit(1);
        } else {
            configFile = args[0];
        }
        StaticProperties.loadProperties(configFile);

        authService = new AuthService();
        // Initialize and start the DocumentSchedulerService
        DocumentSchedulerService documentSchedulerService = new DocumentSchedulerService(authService);
        documentSchedulerService.start();

    }
}
