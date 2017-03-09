/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
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

/*
/* <p>
 */
package com.sun.corba.se.impl.orbutil;

import java.io.*;
import java.util.Hashtable;

/**
 * Since ObjectOutputStream.PutField methods specify no exceptions,
 * we are not checking for null parameters on put methods.
 * <p>
 *  由于ObjectOutputStream.PutField方法没有指定异常,因此我们不会在put方法上检查null参数。
 * 
 */
class LegacyHookPutFields extends ObjectOutputStream.PutField
{
    private Hashtable fields = new Hashtable();

    /**
     * Put the value of the named boolean field into the persistent field.
     * <p>
     *  将指定的布尔字段的值放入持久字段。
     * 
     */
    public void put(String name, boolean value){
        fields.put(name, new Boolean(value));
    }

    /**
     * Put the value of the named char field into the persistent fields.
     * <p>
     *  将命名的char字段的值放入持久字段。
     * 
     */
    public void put(String name, char value){
        fields.put(name, new Character(value));
    }

    /**
     * Put the value of the named byte field into the persistent fields.
     * <p>
     *  将命名的字节字段的值放入持久字段。
     * 
     */
    public void put(String name, byte value){
        fields.put(name, new Byte(value));
    }

    /**
     * Put the value of the named short field into the persistent fields.
     * <p>
     *  将命名的短字段的值放入持久字段。
     * 
     */
    public void put(String name, short value){
        fields.put(name, new Short(value));
    }

    /**
     * Put the value of the named int field into the persistent fields.
     * <p>
     *  将命名的int字段的值放入持久字段。
     * 
     */
    public void put(String name, int value){
        fields.put(name, new Integer(value));
    }

    /**
     * Put the value of the named long field into the persistent fields.
     * <p>
     *  将命名的长字段的值放入持久字段。
     * 
     */
    public void put(String name, long value){
        fields.put(name, new Long(value));
    }

    /**
     * Put the value of the named float field into the persistent fields.
     *
     * <p>
     *  将命名的float字段的值放入持久字段。
     * 
     */
    public void put(String name, float value){
        fields.put(name, new Float(value));
    }

    /**
     * Put the value of the named double field into the persistent field.
     * <p>
     *  将命名的double字段的值放入持久字段。
     * 
     */
    public void put(String name, double value){
        fields.put(name, new Double(value));
    }

    /**
     * Put the value of the named Object field into the persistent field.
     * <p>
     *  将命名对象字段的值放入持久字段。
     * 
     */
    public void put(String name, Object value){
        fields.put(name, value);
    }

    /**
     * Write the data and fields to the specified ObjectOutput stream.
     * <p>
     *  将数据和字段写入指定的ObjectOutput流。
     */
    public void write(ObjectOutput out) throws IOException {
        out.writeObject(fields);
    }
}
