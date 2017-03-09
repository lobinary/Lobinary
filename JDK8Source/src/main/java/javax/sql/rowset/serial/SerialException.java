/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset.serial;

import java.sql.SQLException;

/**
 * Indicates and an error with the serialization or de-serialization of
 * SQL types such as <code>BLOB, CLOB, STRUCT or ARRAY</code> in
 * addition to SQL types such as <code>DATALINK and JAVAOBJECT</code>
 *
 * <p>
 *  除了诸如<code> DATALINK和JAVAOBJECT </code>之类的SQL类型,还指示和除去诸如<code> BLOB,CLOB,STRUCT或ARRAY </code>等SQL类型的序
 * 列化或反序列化的错误。
 * 
 */
public class SerialException extends java.sql.SQLException {

    /**
     * Creates a new <code>SerialException</code> without a
     * message.
     * <p>
     *  创建新的<code> SerialException </code>而不显示消息。
     * 
     */
     public SerialException() {
     }

    /**
     * Creates a new <code>SerialException</code> with the
     * specified message.
     *
     * <p>
     *  使用指定的消息创建新的<code> SerialException </code>。
     * 
     * @param msg the detail message
     */
    public SerialException(String msg) {
        super(msg);
    }

    static final long serialVersionUID = -489794565168592690L;
}
