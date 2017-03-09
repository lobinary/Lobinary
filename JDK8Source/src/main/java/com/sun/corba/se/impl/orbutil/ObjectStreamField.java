/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性Copyright IBM Corp. 1998 1999保留所有权利
 * 
 */

package com.sun.corba.se.impl.orbutil;

import java.lang.reflect.Field;
import java.lang.Comparable;
import java.util.Hashtable;

/**
 * This is duplicated here somewhat in haste since we can't
 * expose this class outside of the com.sun.corba.se.impl.io
 * package for security reasons.
 * <p>
 *  由于安全原因,我们无法将这个类暴露在com.sun.corba.se.impl.io包之外,所以这里有一些重复。
 * 
 */
/**
 * A description of a field in a serializable class.
 * A array of these is used to declare the persistent fields of
 * a class.
 *
 * <p>
 *  可序列化类中的字段的描述。这些数组用于声明类的持久字段。
 * 
 */
class ObjectStreamField implements Comparable {
    /**
     * Create a named field with the specified type.
     * <p>
     *  创建具有指定类型的命名字段。
     * 
     */
    ObjectStreamField(String n, Class clazz) {
        name = n;
        this.clazz = clazz;

        // Compute the typecode for easy switching
        if (clazz.isPrimitive()) {
            if (clazz == Integer.TYPE) {
                type = 'I';
            } else if (clazz == Byte.TYPE) {
                type = 'B';
            } else if (clazz == Long.TYPE) {
                type = 'J';
            } else if (clazz == Float.TYPE) {
                type = 'F';
            } else if (clazz == Double.TYPE) {
                type = 'D';
            } else if (clazz == Short.TYPE) {
                type = 'S';
            } else if (clazz == Character.TYPE) {
                type = 'C';
            } else if (clazz == Boolean.TYPE) {
                type = 'Z';
            }
        } else if (clazz.isArray()) {
            type = '[';
            typeString = ObjectStreamClass_1_3_1.getSignature(clazz);
        } else {
            type = 'L';
            typeString = ObjectStreamClass_1_3_1.getSignature(clazz);
        }

        if (typeString != null)
            signature = typeString;
        else
            signature = String.valueOf(type);

    }

    ObjectStreamField(Field field) {
        this(field.getName(), field.getType());
        this.field = field;
    }

    /**
     * Create an ObjectStreamField containing a reflected Field.
     * <p>
     *  创建一个包含反射字段的ObjectStreamField。
     * 
     */
    ObjectStreamField(String n, char t, Field f, String ts)
    {
        name = n;
        type = t;
        field = f;
        typeString = ts;

        if (typeString != null)
            signature = typeString;
        else
            signature = String.valueOf(type);

    }

    /**
     * Get the name of this field.
     * <p>
     *  获取此字段的名称。
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type of the field.
     * <p>
     *  获取字段的类型。
     * 
     */
    public Class getType() {
        if (clazz != null)
            return clazz;
        switch (type) {
        case 'B': clazz = Byte.TYPE;
            break;
        case 'C': clazz = Character.TYPE;
            break;
        case 'S': clazz = Short.TYPE;
            break;
        case 'I': clazz = Integer.TYPE;
            break;
        case 'J': clazz = Long.TYPE;
            break;
        case 'F': clazz = Float.TYPE;
            break;
        case 'D': clazz = Double.TYPE;
            break;
        case 'Z': clazz = Boolean.TYPE;
            break;
        case '[':
        case 'L':
            clazz = Object.class;
            break;
        }

        return clazz;
    }

    public char getTypeCode() {
        return type;
    }

    public String getTypeString() {
        return typeString;
    }

    Field getField() {
        return field;
    }

    void setField(Field field) {
        this.field = field;
        this.fieldID = -1;
    }

    /*
     * Default constructor creates an empty field.
     * Usually used just to get to the sort functions.
     * <p>
     *  默认构造函数创建一个空字段。通常只用于获取排序函数。
     * 
     */
    ObjectStreamField() {
    }

    /**
     * test if this field is a primitive or not.
     * <p>
     *  测试这个字段是否是原语。
     * 
     */
    public boolean isPrimitive() {
        return (type != '[' && type != 'L');
    }

    /**
     * Compare this with another ObjectStreamField.
     * return -1 if this is smaller, 0 if equal, 1 if greater
     * types that are primitives are "smaller" than objects.
     * if equal, the names are compared.
     * <p>
     *  将此与另一个ObjectStreamField进行比较。如果较小,返回-1,如果相等则返回0,如果较大的原语类型比对象"小",则返回1。如果相等,则比较名称。
     * 
     */
    public int compareTo(Object o) {
        ObjectStreamField f2 = (ObjectStreamField)o;
        boolean thisprim = (this.typeString == null);
        boolean otherprim = (f2.typeString == null);

        if (thisprim != otherprim) {
            return (thisprim ? -1 : 1);
        }
        return this.name.compareTo(f2.name);
    }

    /**
     * Compare the types of two class descriptors.
     * The match if they have the same primitive types.
     * or if they are both objects and the object types match.
     * <p>
     *  比较两个类描述符的类型。如果它们具有相同的基本类型,则为匹配。或者如果它们都是对象并且对象类型匹配。
     * 
     */
    public boolean typeEquals(ObjectStreamField other) {
        if (other == null || type != other.type)
            return false;

        /* Return true if the primitive types matched */
        if (typeString == null && other.typeString == null)
            return true;

        return ObjectStreamClass_1_3_1.compareClassNames(typeString,
                                                         other.typeString,
                                                         '/');
    }

    /* Returns the signature of the Field.
     *
     * <p>
     */
    public String getSignature() {

        return signature;

    }

    /**
     * Return a string describing this field.
     * <p>
     *  返回描述此字段的字符串。
     * 
     */
    public String toString() {
        if (typeString != null)
            return typeString + " " + name;
        else
            return type + " " + name;
    }

    public Class getClazz() {
        return clazz;
    }

    /* Returns the Field ID
     * NOT USED, since this class is used only in ObjectStreamClass_1_3_1,
     * which is used only in RepositoryId_1_3_1.
    public long getFieldID( Class cl ) {
        if (fieldID == -1) {
            if (typeString != null)
                fieldID = getFieldIDNative( cl, getName(), typeString );
            else
                fieldID = getFieldIDNative( cl, getName(), getSignature() );
        }
        return fieldID;
    }
     * <p>
     * NOT USED,因为此类仅用于ObjectStreamClass_1_3_1(仅在RepositoryId_1_3_1中使用)。
     *  public long getFieldID(Class cl){if(fieldID == -1){if(typeString！= null)fieldID = getFieldIDNative(cl,getName(),typeString); else fieldID = getFieldIDNative(cl,getName(),getSignature()); }
     *  return fieldID; }}。
     */

    private String name;                // the name of the field
    private char type;                  // type first byte of the type signature
    private Field field;                // Reflected field
    private String typeString;          // iff object, typename
    private Class clazz;                // the type of this field, if has been resolved

    // the next 3 things are RMI-IIOP specific, it can be easily
    // removed, if we can figure out all place where there are dependencies
    // to this.  Signature is esentially equal to typestring. Then
    // essentially we can use the java.io.ObjectStreamField as such.

    private String signature;   // the signature of the field
    private long fieldID = -1;
    // private static native long getFieldIDNative(Class c, String fieldName, String fieldSig);
}
