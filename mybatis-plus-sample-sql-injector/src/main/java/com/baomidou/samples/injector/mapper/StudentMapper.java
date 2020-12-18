package com.baomidou.samples.injector.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.baomidou.samples.injector.base.MyBaseMapper;
import com.baomidou.samples.injector.config.MySelectProvider;
import com.baomidou.samples.injector.entity.Student;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 学生Mapper层
 *
 * @author nieqiurong 2018/8/11 20:21.
 */
@Mapper
public interface StudentMapper extends MyBaseMapper<Student> {

    @SelectProvider(value = MySelectProvider.class, method = "getSql")
    Student select(String sql);

//    @Update("<script>UPDATE STUDENT set NAME = (CASE ID <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" separator=\"\" close=\"\">  WHEN #{item.id} THEN #{item.name} ELSE NAME </foreach> END), AGE = (CASE ID <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" separator=\"\" close=\"\">  WHEN #{item.id} THEN #{item.age} ELSE AGE </foreach> END), WHERE ID in <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item.id}</foreach></script>")
    @Update("<script> update STUDENT set NAME= case ID "//
            + "<foreach item='item' index='index' collection='list'  > " //
            + " WHEN #{item.id} THEN #{item.name} "//
            + "</foreach>"//
            + " end "//
            + "</script>")
    void updateB(@Param("list") List<Student> list);
}
