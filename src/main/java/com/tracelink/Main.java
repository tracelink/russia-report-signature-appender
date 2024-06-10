package com.tracelink;

import schedulers.DocumentSchedulerService;
import services.AuthService;
import utilities.StaticProperties;

public class Main {

    private static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args) {
        AuthService authService;

        boolean isTest = Boolean.parseBoolean(System.getenv("isTest"));
        String configFile;

        if (args.length != 1 && !isTest) {
            System.out.println("Usage: java -jar YourJarFile.jar <path_to_config_file>");
            System.exit(1);
            configFile = args[0];
        }else{
            configFile = "/Users/vsharma/Downloads/appTest/application.config";
        }

        StaticProperties.loadProperties(configFile);

        authService = new AuthService();

        // Initialize and start the DocumentSchedulerService
        DocumentSchedulerService documentSchedulerService = new DocumentSchedulerService(authService);
        documentSchedulerService.start();

        }
    }
