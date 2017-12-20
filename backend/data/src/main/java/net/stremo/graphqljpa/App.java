package net.stremo.graphqljpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class App {

    static boolean buildData = false;

    @Autowired
    private TestDataSetup testDataSetup;

    public static void main(String[] args) {
        if(args.length > 0 && args[0].trim().equals("-buildData"))
           buildData = true;

        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void createTestData() {

        if(buildData) {
            System.out.println("Testdaten werden erzeugt");
            testDataSetup.run();
        }
    }
}
