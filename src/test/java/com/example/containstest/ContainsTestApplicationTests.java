package com.example.containstest;
import com.example.containstest.containsTestDemo.mapper.SampleMapper;
import com.example.containstest.containsTestDemo.pojo.Sample;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContainsTestApplication.class)
public class ContainsTestApplicationTests {
    @Autowired
    private SampleMapper sampleMapper;
    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * mybatis-plus demo
     */
    @Test
    public void TestInsert() {
        Sample sample = new Sample();
        sample.setId(22L);
        sample.setName("sdasda");
        List<Sample> samples = sampleMapper.selectList(null);
        System.out.println(samples);
        sampleMapper.insert(sample);
        assertThat(sample.getId()).isNotNull();
    }

    /**
     * 模拟声明式事务下使用编程式事务的效果
     * 效果：编程式事务正常操作，声明式不影响编程事务
     */
    @Test
    public void demo(){
        this.JDController();
    }

    @Transactional(rollbackFor = Exception.class)
    public void JDController() {
        transactionTemplate.execute(status -> {
            Sample sample = new Sample();
            sample.setId(24L);
            sample.setName("eeeeee");
            return sampleMapper.insert(sample);
        });
        throw new RuntimeException("运行异常");

    }

    /**
     * sql包下的时间日期：年-月-日
     * Java.util包下的时间日期：年-月-日 时:分:秒
     */
    @Test
    public void demo2(){
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(new Date());
    }
    /**
     * CompletableFuture处理
     * 带返回值异步请求，默认线程池
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
     * 带返回值的异步请求，可以自定义线程池
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
     */
    @Test
    public void demo3() throws ExecutionException, InterruptedException {
        //默认线程池
        CompletableFuture<Object> thing = CompletableFuture.supplyAsync(() -> {
            System.out.println("do someThing");
            return null;
        });
        System.out.println("等待子任务结束："+thing.get());
        System.out.println("===================");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(()->{
            System.out.println("do someThing executors");
            return null;
        },executorService);
        System.out.println("(自定义线程池)等待子任务结束："+thing.get());
        Thread.sleep(1000);
    }
}
