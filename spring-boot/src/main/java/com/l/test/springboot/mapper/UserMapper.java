package com.l.test.springboot.mapper;

import com.l.test.springboot.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Update("create table user_info\n" +
            "(\n" +
            "\tid int generated always as identity,\n" +
            "\tusername varchar(50),\n" +
            "\tpassword varchar(50)\n" +
            ")")
    void create();

    @Select("select count(1) from user_info")
    Integer count();

    @Select("select * from user_info")
    List<User> selectAll();

    @Select("select * from user_info order by id asc {limit ${limit} offset ${offset}}")
    List<User> selectSeveral(@Param("limit")int limit, @Param("offset")int offset);

    @Insert("insert into user_info(username,password) values(#{o.username}, #{o.password})")
    void insert(@Param("o")User obj);

    @Delete("<script>delete from user_info where id in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(@Param("ids")List<String> ids);

    @Update("update user_info set username = #{o.username}, password = #{o.password}")
    void update(@Param("o")User obj);

}
