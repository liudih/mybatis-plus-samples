package com.baomidou.samples.injector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.samples.injector.entity.Student;
import com.baomidou.samples.injector.mapper.StudentMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义注入测试
 *
 * @author nieqiurong 2018/8/11 20:34.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class InjectorTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    public void test() {
        log.error("--------------------------------------insert-------------------------------------------------------");
        List<Long> ids = Lists.newArrayList();
        for (int i = 0; i < 2; i++) {
            Student student = new Student("小明" + i + "号", i);
            studentMapper.insert(student);
            ids.add(student.getId());
        }
        log.error("--------------------------------------insertBatchSomeColumn-------------------------------------------------------");
        List<Student> ss = Lists.newArrayList();
        for (int i = 2; i < 20; i++) {
            Student student = new Student("小明" + i + "号", i);
            ss.add(student);
        }
        studentMapper.insertBatchSomeColumn(ss);
        ids.addAll(ss.stream().map(Student::getId).collect(Collectors.toList()));

        Student select = studentMapper.select("select * from student where id = " + ids.get(0));
        System.out.println(select);

        log.error("--------------------------------------222222-------------------------------------------------------");
        List<Student> list = studentMapper.queryChain().eq("name", "小明1号").list();
        System.out.println(list.get(0).toString());


        log.error("--------------------------------------deleteAll-------------------------------------------------------");
        studentMapper.deleteAll();
    }


    @Test
    public void testInsertTime(){

        long t0 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Student student = new Student("小明" + i + "号", i);
            studentMapper.insert(student);
        }
        log.info(">>>>time={}ms", (System.currentTimeMillis() - t0));
    }

    @Test
    public void testInsertTime2(){

        List<Student> alist = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Student student = new Student("小明" + i + "号", null);
            alist.add(student);
        }
        studentMapper.insertBatchSomeColumn(alist);

        long t0 = System.currentTimeMillis();
        studentMapper.updateBatch(alist);

        log.info(">>>>time={}ms", (System.currentTimeMillis() - t0));

    }
}
