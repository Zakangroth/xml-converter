package com.zakangroth.xmlconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class XmlConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlConverterApplication.class, args);
    }
}