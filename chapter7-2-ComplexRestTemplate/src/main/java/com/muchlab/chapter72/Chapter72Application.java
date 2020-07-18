package com.muchlab.chapter72;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootApplication
@Slf4j
public class Chapter72Application implements ApplicationRunner {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Chapter72Application.class)
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
                .fromUriString("http://localhost:8001/coffee?name={name}")
                .build("mocha");
        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<String> resp = restTemplate.exchange(requestEntity, String.class);
        log.info("Response Status:{}, Headers:{}", resp.getStatusCode(), resp.getHeaders());
        log.info("Coffee:{}", resp.getBody());

        String coffeeUri = "http://localhost:8001/coffee";
        Coffee request = Coffee.builder().name("Coffee1")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.00))
                .build();
        Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
        log.info("New Coffee:{}", response);

        ParameterizedTypeReference<List<Coffee>> ptr =
                new ParameterizedTypeReference<List<Coffee>>() {};
        ResponseEntity<List<Coffee>> list = restTemplate.exchange(coffeeUri, HttpMethod.GET, null, ptr);
        list.getBody().forEach(c->log.info("Coffee:{}", c));
//        List<LinkedHashMap> list = new ArrayList<>();
//        //如果返回的是多个对象，那使用getForObject将会默认返回LinkedHashMap类型的数据
//        list = restTemplate.getForObject(coffeeUri, list.getClass());
//        list.forEach(c->log.info("c:{}", c.toString()));
    }
}
