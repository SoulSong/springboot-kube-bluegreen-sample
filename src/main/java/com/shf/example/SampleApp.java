package com.shf.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author songhaifeng
 */
@SpringBootApplication
public class SampleApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SampleApp.class).web(WebApplicationType.SERVLET).build().run(args);
    }

}
