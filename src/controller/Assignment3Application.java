package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "controller",
        "config",
        "database",
        "development",
        "mapper",
        "repository",
        "service",
        "thread"
})
public class Assignment3Application {

    public static void main(String[] args) {
        SpringApplication.run(Assignment3Application.class,args);
    }
}