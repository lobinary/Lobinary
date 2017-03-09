/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;


// java import
import java.io.Serializable;


/**
 * Represents an MBean attribute by associating its name with its value.
 * The MBean server and other objects use this class to get and set attributes values.
 *
 * <p>
 *  通过将MBean的名称与其值相关联来表示MBean属性。 MBean服务器和其他对象使用此类来获取和设置属性值。
 * 
 * 
 * @since 1.5
 */
public class Attribute implements Serializable   {

    /* Serial version */
    private static final long serialVersionUID = 2484220110589082382L;

    /**
    /* <p>
    /* 
     * @serial Attribute name.
     */
    private String name;

    /**
    /* <p>
    /* 
     * @serial Attribute value
     */
    private Object value= null;


    /**
     * Constructs an Attribute object which associates the given attribute name with the given value.
     *
     * <p>
     *  构造一个将给定属性名称与给定值相关联的属性对象。
     * 
     * 
     * @param name A String containing the name of the attribute to be created. Cannot be null.
     * @param value The Object which is assigned to the attribute. This object must be of the same type as the attribute.
     *
     */
    public Attribute(String name, Object value) {

        if (name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null "));
        }

        this.name = name;
        this.value = value;
    }


    /**
     * Returns a String containing the  name of the attribute.
     *
     * <p>
     *  返回一个包含属性名称的字符串。
     * 
     * 
     * @return the name of the attribute.
     */
    public String getName()  {
        return name;
    }

    /**
     * Returns an Object that is the value of this attribute.
     *
     * <p>
     *  返回作为此属性值的对象。
     * 
     * 
     * @return the value of the attribute.
     */
    public Object getValue()  {
        return value;
    }

    /**
     * Compares the current Attribute Object with another Attribute Object.
     *
     * <p>
     *  将当前属性对象与另一个属性对象进行比较。
     * 
     * 
     * @param object  The Attribute that the current Attribute is to be compared with.
     *
     * @return  True if the two Attribute objects are equal, otherwise false.
     */


    public boolean equals(Object object)  {
        if (!(object instanceof Attribute)) {
            return false;
        }
        Attribute val = (Attribute) object;

        if (value == null) {
            if (val.getValue() == null) {
                return name.equals(val.getName());
            } else {
                return false;
            }
        }

        return ((name.equals(val.getName())) &&
                (value.equals(val.getValue())));
    }

    /**
     * Returns a hash code value for this attribute.
     *
     * <p>
     *  返回此属性的哈希码值。
     * 
     * 
     * @return a hash code value for this attribute.
     */
    public int hashCode() {
        return name.hashCode() ^ (value == null ? 0 : value.hashCode());
    }

    /**
     * Returns a String object representing this Attribute's value. The format of this
     * string is not specified, but users can expect that two Attributes return the
     * same string if and only if they are equal.
     * <p>
     *  返回表示此属性值的String对象。此字符串的格式未指定,但用户可以预期两个Attributes返回相同的字符串,当且仅当它们相等。
     */
    public String toString() {
        return getName() + " = " + getValue();
    }
 }
