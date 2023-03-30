package com.example.containstest;
import com.example.containstest.containsTestDemo.mapper.SampleMapper;

import com.example.containstest.containsTestDemo.pojo.Componse;
import com.example.containstest.containsTestDemo.pojo.Sample;
import com.example.containstest.containsTestDemo.service.payResultimpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


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
    public void demo3() throws Exception {
        //supplyAsyncDemo();
        CompletableFuture<Void> do_the_someThing = CompletableFuture.runAsync(() -> {
            System.out.println("do the someThing");
        });
        System.out.println(do_the_someThing.get());
    }

    private static void supplyAsyncDemo() throws InterruptedException, ExecutionException, TimeoutException {
        //默认线程池
        CompletableFuture<Object> thing = CompletableFuture.supplyAsync(() -> {
            System.out.println("do someThing");
            return null;
        });
        System.out.println("等待子任务结束："+thing.get());
        System.out.println("===================");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Object> do_someThing_executors = CompletableFuture.supplyAsync(() -> {
            System.out.println("do someThing executors");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, executorService);

        System.out.println("(自定义线程池)等待子任务结束："+do_someThing_executors.get(1, TimeUnit.SECONDS));
    }

    /**
     * 非线程安全在并行流中的问题
     */
    @Test
    public void demo4(){
        ArrayList<Long> list01 = new ArrayList<>();
        ArrayList<Long> list02 = new ArrayList<>();

        int times = 1000; // 定义元素的多少
        LongStream.rangeClosed(0, times).forEach(list01::add);
        System.out.println("串行流元素数量 = "+list01.size());

        LongStream.rangeClosed(0,times).parallel().forEach(list02::add);
        System.out.println("并行流元素数量 = "+list02.size());


    }

    /**
     * stream流
     */
    @Test
    public void demo5(){
        List<Invoice> invoices = Arrays.asList(
                new Invoice("A01", BigDecimal.valueOf(9.99)),
                new Invoice("A02", BigDecimal.valueOf(19.99)),
                new Invoice("A03", BigDecimal.valueOf(4.99))
        );
        //第一种用法
        Optional<BigDecimal> reduce1 = invoices.stream().map(Invoice::getPrice).reduce(BigDecimal::add);
        //第二种法
        BigDecimal reduce = invoices.stream().map(Invoice::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(reduce);
        System.out.println(reduce1.get());
    }

    @Test
    public void contextLoads() {
        List<String> list = new ArrayList<>();
        list.add("/image/sadas/loan");
        list.add("/image/sadas/credit");
        list.add("/image/sadas/pbc");
        list.add("/image/sadas/asdasd");
        List<demo> list1 = new ArrayList<>();
        list1.add(new demo());
        list1.add(new demo(new BigDecimal(1)));
        list1.add(new demo(new BigDecimal(3)));
        List<String> loan = list.stream().filter(x -> x.contains("loan")).collect(Collectors.toList());
        for(String a : loan){
            System.out.println(a);
        }
        Optional<BigDecimal> reduce = list1.stream().filter(x -> !Objects.isNull(x.getAmount())).map(demo::getAmount).reduce(BigDecimal::add);
        System.out.println(reduce.get());
    }

    /**
     * 测试状态取值类 allOf&anyAll
     */
    @Test
    public void allofAndAnyOf() throws Exception {
        //allGetResult();
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 任务完成");
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(2000);
                int i = 1/0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 任务完成");
            return "cf2 任务完成";
        });

        CompletableFuture<Object> cfAll = CompletableFuture.anyOf(cf1, cf2);
        System.out.println("cfAll结果->" + cfAll.get());

    }

    /**
     * allof操作
     */
    private static void allGetResult() throws Exception {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("cf1 任务完成");
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                //int a = 1/0;
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 任务完成");
            return "cf2 任务完成";
        });

        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf3 do something....");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf3 任务完成");
            return "cf3 任务完成";
        });

        CompletableFuture.allOf(cf1, cf2, cf3).get();
        System.out.println("=========>主进程");
    }

    /**
     * 测试泛型转发
     */
    @Test
    public void demo11(){
        String extracted = extracted();
    }

    private static String extracted() {
        payResultimpl payResultimpl = new payResultimpl();
        return payResultimpl.payReult().getData();
    }

    /**
     * 自定义类
     */
    class demo{
        private BigDecimal amount;

        public demo() {
        }

        public demo(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "demo{" +
                    "amount=" + amount +
                    '}';
        }
    }
}
