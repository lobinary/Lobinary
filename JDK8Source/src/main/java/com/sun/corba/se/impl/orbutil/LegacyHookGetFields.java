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

class LegacyHookGetFields extends ObjectInputStream.GetField {
    private Hashtable fields = null;

    LegacyHookGetFields(Hashtable fields){
        this.fields = fields;
    }

    /**
     * Get the ObjectStreamClass that describes the fields in the stream.
     * <p>
     *  获取描述流中字段的ObjectStreamClass。
     * 
     */
    public java.io.ObjectStreamClass getObjectStreamClass() {
        return null;
    }

    /**
     * Return true if the named field is defaulted and has no value
     * in this stream.
     * <p>
     *  如果命名字段是默认值,并且在此流中没有值,则返回true。
     * 
     */
    public boolean defaulted(String name)
        throws IOException, IllegalArgumentException  {
        return (!fields.containsKey(name));
    }

    /**
     * Get the value of the named boolean field from the persistent field.
     * <p>
     *  从持久字段获取指定的布尔字段的值。
     * 
     */
    public boolean get(String name, boolean defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return ((Boolean)fields.get(name)).booleanValue();
    }

    /**
     * Get the value of the named char field from the persistent fields.
     * <p>
     *  从持久字段获取命名的char字段的值。
     * 
     */
    public char get(String name, char defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return ((Character)fields.get(name)).charValue();

    }

    /**
     * Get the value of the named byte field from the persistent fields.
     * <p>
     *  从持久字段获取命名的字节字段的值。
     * 
     */
    public byte get(String name, byte defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return ((Byte)fields.get(name)).byteValue();

    }

    /**
     * Get the value of the named short field from the persistent fields.
     * <p>
     *  从持久字段获取命名的短字段的值。
     * 
     */
    public short get(String name, short defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return ((Short)fields.get(name)).shortValue();

    }

    /**
     * Get the value of the named int field from the persistent fields.
     * <p>
     *  从持久字段获取命名的int字段的值。
     * 
     */
    public int get(String name, int defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return ((Integer)fields.get(name)).intValue();

    }

    /**
     * Get the value of the named long field from the persistent fields.
     * <p>
     *  从持久字段获取指定的长字段的值。
     * 
     */
    public long get(String name, long defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return ((Long)fields.get(name)).longValue();

    }

    /**
     * Get the value of the named float field from the persistent fields.
     * <p>
     *  从持久字段获取命名的float字段的值。
     * 
     */
    public float get(String name, float defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return ((Float)fields.get(name)).floatValue();

    }

    /**
     * Get the value of the named double field from the persistent field.
     * <p>
     *  从持久字段获取指定的double字段的值。
     * 
     */
    public double get(String name, double defvalue)
        throws IOException, IllegalArgumentException  {
        if (defaulted(name))
            return defvalue;
        else return ((Double)fields.get(name)).doubleValue();

    }

    /**
     * Get the value of the named Object field from the persistent field.
     * <p>
     *  从持久字段获取命名对象字段的值。
     */
    public Object get(String name, Object defvalue)
        throws IOException, IllegalArgumentException {
        if (defaulted(name))
            return defvalue;
        else return fields.get(name);

    }

    public String toString(){
        return fields.toString();
    }
}
