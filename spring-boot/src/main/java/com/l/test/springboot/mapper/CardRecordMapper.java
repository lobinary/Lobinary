package com.l.test.springboot.mapper;

import com.l.test.springboot.model.CardRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CardRecordMapper {

    @Update("create table card_record\n" +
            "(\n" +
            "  id int generated always as identity,\n" +
            "  owner int ,\n" +
            "  card_id int,\n" +
            "  used smallint,\n" +
            "  send_reason varchar(255),\n" +
            "  use_reason varchar(255),\n" +
            "  create_time timestamp,\n" +
            "  start_time timestamp,\n" +
            "  end_time timestamp,\n" +
            "  use_time timestamp\n" +
            ")")
    void create();

    @Select("select count(1) from card_record")
    Integer count();

    @Select("select cr.*,ci.name card_name from card_record cr left join card_info ci on cr.card_id = ci.id")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "card_name", property = "cardName"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "use_time", property = "useTime"),
            @Result(column = "send_reason", property = "sendReason"),
            @Result(column = "use_reason", property = "useReason")
    })
    List<CardRecord> selectAll();

    @Select("select cr.*,ci.name card_name from card_record cr left join card_info ci on cr.card_id = ci.id where cr.owner = #{owner} ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "card_name", property = "cardName"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "use_time", property = "useTime"),
            @Result(column = "send_reason", property = "sendReason"),
            @Result(column = "use_reason", property = "useReason")
    })
    List<CardRecord> selectByUserId(@Param("owner") int owner);

    @Select("select cr.*,ci.name card_name from card_record cr left join card_info ci on cr.card_id = ci.id order by cr.id asc {limit ${limit} offset ${offset}}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "card_name", property = "cardName"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "use_time", property = "useTime"),
            @Result(column = "send_reason", property = "sendReason"),
            @Result(column = "use_reason", property = "useReason")
    })
    List<CardRecord> selectSeveral(@Param("limit") int limit, @Param("offset") int offset);

    @Insert("INSERT INTO CARD_RECORD\n" +
            "(OWNER, CARD_ID, USED, SEND_REASON, USE_REASON, CREATE_TIME, START_TIME, END_TIME, USE_TIME)\n" +
            "VALUES (#{o.owner},#{o.cardId}, #{o.used},#{o.sendReason},#{o.useReason},#{o.createTime},#{o.startTime},#{o.endTime},#{o.useTime})")
    void insert(@Param("o") CardRecord obj);

    @Update("UPDATE CARD_RECORD t\n" +
            "SET t.OWNER = #{o.owner},\n" +
            "t.CARD_ID = #{o.cardId},\n" +
            "t.USED = #{o.used},\n" +
            "t.SEND_REASON = #{o.sendReason},\n" +
            "t.USE_REASON = #{o.useReason},\n" +
            "t.CREATE_TIME = #{o.createTime}\n" +
            "t.START_TIME = #{o.startTime}\n" +
            "t.END_TIME = #{o.endTime}\n" +
            "t.USE_TIME = #{o.useTime}\n" +
            "WHERE t.ID = #{o.id}")
    void update(@Param("o") CardRecord obj);

}
