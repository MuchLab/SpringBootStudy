package com.muchlab.chapter81;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication
@Slf4j
public class Chapter81Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Chapter81Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Flux 表示的是包含 0 到 N 个元素的异步序列。
        System.out.println("==========Flux=========");
        //通过下面的这些静态方法可以生成简单地Flux序列
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS), Duration.of(1, ChronoUnit.SECONDS)).subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        System.out.println("========Generate&Create========");
        //如果要生成地Flux序列比较复杂，建议使用generate和create
        Flux.generate(sink->{
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);
        //generate方法只支持同步，而且在一次调用中只能产生一个元素
        //所以在generate中不能多次地调用next
        Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink)->{
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size()==10){
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
        //create()方法与 generate()方法的不同之处在于所使用的是
        // FluxSink 对象。FluxSink 支持同步和异步的消息产生，
        // 并且可以在一次调用中产生多个元素。
        Flux.create(fluxSink -> {
            int value;
            for (int i = 0; i < 10; i++) {
                value = random.nextInt(100);
                fluxSink.next(value);
            }
            fluxSink.complete();
        }).subscribe(System.out::println);
        //Mono 表示的是包含 0 或者 1 个元素的异步序列。
        System.out.println("==========Mono=========");
        Mono.fromSupplier(()->"Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(monoSink -> monoSink.success("Hello")).subscribe(System.out::println);

        System.out.println("==========Buffer==========");
        //buffer的作用是把当前流中的元素收集到集合中
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.range(1, 10).bufferUntil(integer -> integer%2==0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(integer -> integer%2==0).subscribe(System.out::println);
        System.out.println("==========Filter==========");
        //对流中包含的元素进行过滤，只留下满足 Predicate 指定条件的元素
        Flux.range(1, 10).filter(integer -> integer%2==0).subscribe(System.out::println);
        System.out.println("==========Window==========");
        //window 操作符的作用类似于 buffer,所不同的是 window 操作符是把当前流中的
        // 元素收集到另外的 Flux 序列中，因此返回值类型是 Flux<flux>
        Flux.range(1, 100).window(20).subscribe(System.out::println);
        System.out.println("==========ZipWith==========");
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2)->String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
        System.out.println("==========Take==========");
        //take 系列操作符用来从当前流中提取元素。提取的方式可以有很多种。
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
        System.out.println("==========Reduce==========");
        //reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作
        Flux.range(1, 100).reduce((x, y)->x+y).subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(()->100, (x, y)->x+y).subscribe(System.out::println);

    }
}
