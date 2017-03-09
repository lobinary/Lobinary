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
 * Java to IDL ptc 02-01-12 1.5.1.3
 *
 * ValueOutputStream is used for implementing RMI-IIOP
 * stream format version 2.
 * <p>
 *  Java到IDL ptc 02-01-12 1.5.1.3
 * 
 *  ValueOutputStream用于实现RMI-IIOP流格式版本2。
 * 
 */
public interface ValueOutputStream {
    /**
     * The start_value method ends any currently open chunk,
     * writes a valuetype header for a nested custom valuetype
     * (with a null codebase and the specified repository ID),
     * and increments the valuetype nesting depth.
     * <p>
     *  start_value方法结束任何当前打开的块,为嵌套定制值类型(具有空代码库和指定的存储库ID)写入值类型头,并增加值类型嵌套深度。
     * 
     */
    void start_value(java.lang.String rep_id);

    /**
     * The end_value method ends any currently open chunk,
     * writes the end tag for the nested custom valuetype,
     * and decrements the valuetype nesting depth.
     * <p>
     *  end_value方法结束任何当前打开的块,写入嵌套定制值类型的结束标签,并递减值类型嵌套深度。
     */
    void end_value();
}
