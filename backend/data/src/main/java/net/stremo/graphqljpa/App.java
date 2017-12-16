package net.stremo.graphqljpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class App {

    @Autowired
    private TestDataSetup testDataSetup;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void createTestData() {

//        testDataSetup.run();
    }
}
