package io.enlightendev.awsbasics;

import io.enlightendev.awsbasics.domain.Greeting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class AwsBasicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsBasicsApplication.class, args);
    }
}

