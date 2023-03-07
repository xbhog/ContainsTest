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
import static org.assertj.core.api.Assertions.in;

import java.util.List;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContainsTestApplication.class)
public class ContainsTestApplicationTests {
    @Autowired
    private SampleMapper sampleMapper;
    @Resource
    private TransactionTemplate transactionTemplate;

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
}
