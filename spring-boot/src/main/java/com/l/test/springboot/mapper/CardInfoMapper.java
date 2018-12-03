package com.l.test.springboot.mapper;

import com.l.test.springboot.model.CardInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CardInfoMapper {

    @Update("create table CARD_INFO\n" +
            "(\n" +
            "\tid int generated always as identity,\n" +
            "\tname varchar(50),\n" +
            "\trules varchar(255),\n" +
            "\tproductorId int,\n" +
            "\tcardStatus varchar(20),\n" +
            "\tbackgroundColor varchar(50)\n" +
            ")")
    void create();

    @Select("select count(1) from card_info")
    Integer count();

    @Select("select * from card_info")
    List<CardInfo> selectAll();

    @Select("select * from card_info order by id asc {limit ${limit} offset ${offset}}")
    List<CardInfo> selectSeveral(@Param("limit") int limit, @Param("offset") int offset);

    @Insert("INSERT INTO \n" +
            "CARD_INFO (NAME, RULES, PRODUCTORID, CARDSTATUS, BACKGROUNDCOLOR)\n" +
            "VALUES (#{o.name},#{o.rules}, #{o.productorId},#{o.cardStatus},#{o.backgroundColor})")
    void insert(@Param("o") CardInfo obj);

    @Update("UPDATE CARD_INFO t\n" +
            "SET t.NAME = #{o.name},\n" +
            "t.RULES = #{o.rules},\n" +
            "t.PRODUCTORID = #{o.productorId},\n" +
            "t.CARDSTATUS = #{o.cardStatus},\n" +
            "t.BACKGROUNDCOLOR = #{o.backgroundColor}\n" +
            "WHERE t.ID = #{o.id}")
    void update(@Param("o") CardInfo obj);

}
