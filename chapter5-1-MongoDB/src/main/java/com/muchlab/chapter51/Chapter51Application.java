package com.muchlab.chapter51;

import com.mongodb.client.result.UpdateResult;
import com.muchlab.chapter51.converter.MoneyReadConverter;
import com.muchlab.chapter51.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootApplication
public class Chapter51Application implements ApplicationRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args)  {
        SpringApplication.run(Chapter51Application.class, args);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //创建
        Coffee espresso = Coffee.builder().
                name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        Coffee saved = mongoTemplate.save(espresso);
        log.info("Coffee:{}", saved);

        //查找
        Query query = new Query();
        Criteria where = Criteria.where("name").is("espresso");
        query.addCriteria(where);
        Coffee coffee = mongoTemplate.findOne(
                query, Coffee.class);
        //在这里可以写成这种形式
//        Coffee coffee1 = mongoTemplate.findOne(
//                Query.query(Criteria.where("name").is("espresso")), Coffee.class);

//        log.info("Find {} Coffee", list.size());
        log.info("FindOne:{}", coffee);

        //修改
        Thread.sleep(1000);
        UpdateResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("espresso")),
                new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30))
                        .currentDate("updateTime"), Coffee.class);
        log.info("Update Result:{}", result.getModifiedCount());
        Coffee updateOne = mongoTemplate.findById(coffee.getId(), Coffee.class);
        log.info("Update Result:{}", updateOne);

        //删除
        mongoTemplate.remove(updateOne);
    }
}
