package com.muchlab.chapter61;

import com.muchlab.chapter61.foo.FooConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Slf4j
public class Chapter61Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Chapter61Application.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        //创建一个基于FooConfig配置的应用上下文，该配置是使用注解来标志的
        AnnotationConfigApplicationContext fooContext = new AnnotationConfigApplicationContext(FooConfig.class);
        //创建一个基于BarConfig配置的应用上下文，该配置是使用xml来定义的，并把fooContext设置为自己的父上下文
        //子上下文可转到父上下文中找bean，但反过来则不行
        ClassPathXmlApplicationContext barContext = new ClassPathXmlApplicationContext(
                new String[]{"applicationContext.xml"},
                fooContext);

        //从上下文中获取Bean
        TestBean fooBean = fooContext.getBean("testBeanX", TestBean.class);
        fooBean.hello();

        log.info("============");

        //若barConfig没有开启aop的自动代理，则在子上下文中找到的bean不会被增强
        TestBean barBean1 = barContext.getBean("testBeanX", TestBean.class);
        barBean1.hello();

        //当在barContext中找不到testBeanX的bean时，会转到其父上下文中寻找该bean，若找到则使用
        //所以，该bean是在父上下文找到的，则该bean会被增强
        TestBean barBean2 = barContext.getBean("testBeanY", TestBean.class);
        barBean2.hello();

        log.info("============");

        TestBean catBean1 = barContext.getBean("testBeanZ", TestBean.class);
        catBean1.hello();

//        TestBean catBean2 = fooContext.getBean("testBeanZ", TestBean.class);
//        catBean2.hello();

    }
}
