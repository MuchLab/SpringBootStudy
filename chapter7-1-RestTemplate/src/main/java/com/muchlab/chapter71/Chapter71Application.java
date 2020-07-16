package com.muchlab.chapter71;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@SpringBootApplication
@Slf4j
public class Chapter71Application implements ApplicationRunner {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Chapter71Application.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8001/coffee/{id}")
                .build(1);
        ResponseEntity<Coffee> coffee = restTemplate.getForEntity(uri, Coffee.class);
        log.info("Response Status:{}, Response Headers:{}",
                coffee.getStatusCode(), coffee.getHeaders());
        log.info("Coffee:{}", coffee.getBody());

        String coffeeUri = "http://localhost:8001/coffee";
        Coffee request = Coffee.builder()
                .name("Americano")
                .price(BigDecimal.valueOf(25.00))
                .build();
        Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
        log.info("New Coffee:{}", response);

        String s = restTemplate.getForObject(coffeeUri, String.class);
        log.info("String:{}", s);
    }
}
