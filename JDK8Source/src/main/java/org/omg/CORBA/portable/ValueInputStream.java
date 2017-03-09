/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.omg.CORBA.portable;

/**
 * Java to IDL ptc 02-01-12 1.5.1.4
 *
 * ValueInputStream is used for implementing RMI-IIOP
 * stream format version 2.
 * <p>
 *  Java到IDL ptc 02-01-12 1.5.1.4
 * 
 *  ValueInputStream用于实现RMI-IIOP流格式版本2。
 * 
 */
public interface ValueInputStream {

    /**
     * The start_value method reads a valuetype
     * header for a nested custom valuetype and
     * increments the valuetype nesting depth.
     * <p>
     *  start_value方法读取嵌套定制值类型的值类型头,并增加值类型嵌套深度。
     * 
     */
    void start_value();

    /**
     * The end_value method reads the end tag
     * for the nested custom valuetype (after
     * skipping any data that precedes the end
     * tag) and decrements the valuetype nesting
     * depth.
     * <p>
     *  end_value方法读取嵌套自定义值类型的结束标记(在跳过结束标记之前的任何数据之后),并递减值类型嵌套深度。
     */
    void end_value();
}
