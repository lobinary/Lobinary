/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.text.html.parser;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.*;

/**
 * This class defines the attributes of an SGML element
 * as described in a DTD using the ATTLIST construct.
 * An AttributeList can be obtained from the Element
 * class using the getAttributes() method.
 * <p>
 * It is actually an element in a linked list. Use the
 * getNext() method repeatedly to enumerate all the attributes
 * of an element.
 *
 * <p>
 *  此类定义了使用ATTLIST结构在DTD中描述的SGML元素的属性。 AttributeList可以使用getAttributes()方法从Element类获取。
 * <p>
 *  它实际上是链表中的一个元素。重复使用getNext()方法枚举元素的所有属性。
 * 
 * 
 * @see         Element
 * @author      Arthur Van Hoff
 *
 */
public final
class AttributeList implements DTDConstants, Serializable {
    public String name;
    public int type;
    public Vector<?> values;
    public int modifier;
    public String value;
    public AttributeList next;

    AttributeList() {
    }

    /**
     * Create an attribute list element.
     * <p>
     *  创建属性列表元素。
     * 
     */
    public AttributeList(String name) {
        this.name = name;
    }

    /**
     * Create an attribute list element.
     * <p>
     *  创建属性列表元素。
     * 
     */
    public AttributeList(String name, int type, int modifier, String value, Vector<?> values, AttributeList next) {
        this.name = name;
        this.type = type;
        this.modifier = modifier;
        this.value = value;
        this.values = values;
        this.next = next;
    }

    /**
    /* <p>
    /* 
     * @return attribute name
     */
    public String getName() {
        return name;
    }

    /**
    /* <p>
    /* 
     * @return attribute type
     * @see DTDConstants
     */
    public int getType() {
        return type;
    }

    /**
    /* <p>
    /* 
     * @return attribute modifier
     * @see DTDConstants
     */
    public int getModifier() {
        return modifier;
    }

    /**
    /* <p>
    /* 
     * @return possible attribute values
     */
    public Enumeration<?> getValues() {
        return (values != null) ? values.elements() : null;
    }

    /**
    /* <p>
    /* 
     * @return default attribute value
     */
    public String getValue() {
        return value;
    }

    /**
    /* <p>
    /* 
     * @return the next attribute in the list
     */
    public AttributeList getNext() {
        return next;
    }

    /**
    /* <p>
    /* 
     * @return string representation
     */
    public String toString() {
        return name;
    }

    /**
     * Create a hashtable of attribute types.
     * <p>
     *  创建一个哈希表的属性类型。
     */
    static Hashtable<Object, Object> attributeTypes = new Hashtable<Object, Object>();

    static void defineAttributeType(String nm, int val) {
        Integer num = Integer.valueOf(val);
        attributeTypes.put(nm, num);
        attributeTypes.put(num, nm);
    }

    static {
        defineAttributeType("CDATA", CDATA);
        defineAttributeType("ENTITY", ENTITY);
        defineAttributeType("ENTITIES", ENTITIES);
        defineAttributeType("ID", ID);
        defineAttributeType("IDREF", IDREF);
        defineAttributeType("IDREFS", IDREFS);
        defineAttributeType("NAME", NAME);
        defineAttributeType("NAMES", NAMES);
        defineAttributeType("NMTOKEN", NMTOKEN);
        defineAttributeType("NMTOKENS", NMTOKENS);
        defineAttributeType("NOTATION", NOTATION);
        defineAttributeType("NUMBER", NUMBER);
        defineAttributeType("NUMBERS", NUMBERS);
        defineAttributeType("NUTOKEN", NUTOKEN);
        defineAttributeType("NUTOKENS", NUTOKENS);

        attributeTypes.put("fixed", Integer.valueOf(FIXED));
        attributeTypes.put("required", Integer.valueOf(REQUIRED));
        attributeTypes.put("current", Integer.valueOf(CURRENT));
        attributeTypes.put("conref", Integer.valueOf(CONREF));
        attributeTypes.put("implied", Integer.valueOf(IMPLIED));
    }

    public static int name2type(String nm) {
        Integer i = (Integer)attributeTypes.get(nm);
        return (i == null) ? CDATA : i.intValue();
    }

    public static String type2name(int tp) {
        return (String)attributeTypes.get(Integer.valueOf(tp));
    }
}
