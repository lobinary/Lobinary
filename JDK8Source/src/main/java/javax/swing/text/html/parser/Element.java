/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Hashtable;
import java.util.BitSet;
import java.io.*;
import sun.awt.AppContext;

/**
 * An element as described in a DTD using the ELEMENT construct.
 * This is essential the description of a tag. It describes the
 * type, content model, attributes, attribute types etc. It is used
 * to correctly parse a document by the Parser.
 *
 * <p>
 *  使用ELEMENT结构的DTD中描述的元素。这是标签的描述的必要。它描述了类型,内容模型,属性,属性类型等。它用于由解析器正确解析文档。
 * 
 * 
 * @see DTD
 * @see AttributeList
 * @author Arthur van Hoff
 */
public final
class Element implements DTDConstants, Serializable {
    public int index;
    public String name;
    public boolean oStart;
    public boolean oEnd;
    public BitSet inclusions;
    public BitSet exclusions;
    public int type = ANY;
    public ContentModel content;
    public AttributeList atts;

    /**
     * A field to store user data. Mostly used to store
     * style sheets.
     * <p>
     *  用于存储用户数据的字段。主要用于存储样式表。
     * 
     */
    public Object data;

    Element() {
    }

    /**
     * Create a new element.
     * <p>
     *  创建一个新元素。
     * 
     */
    Element(String name, int index) {
        this.name = name;
        this.index = index;
        if (index > getMaxIndex()) {
            AppContext.getAppContext().put(MAX_INDEX_KEY, index);
        }
    }

    private static final Object MAX_INDEX_KEY = new Object();

    static int getMaxIndex() {
        Integer value = (Integer) AppContext.getAppContext().get(MAX_INDEX_KEY);
        return (value != null)
                ? value.intValue()
                : 0;
    }

    /**
     * Get the name of the element.
     * <p>
     *  获取元素的名称。
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Return true if the start tag can be omitted.
     * <p>
     *  如果可以省略开始标签,则返回true。
     * 
     */
    public boolean omitStart() {
        return oStart;
    }

    /**
     * Return true if the end tag can be omitted.
     * <p>
     *  如果结束标签可以省略,则返回true。
     * 
     */
    public boolean omitEnd() {
        return oEnd;
    }

    /**
     * Get type.
     * <p>
     *  获取类型。
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * Get content model
     * <p>
     *  获取内容模型
     * 
     */
    public ContentModel getContent() {
        return content;
    }

    /**
     * Get the attributes.
     * <p>
     *  获取属性。
     * 
     */
    public AttributeList getAttributes() {
        return atts;
    }

    /**
     * Get index.
     * <p>
     *  获取索引。
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Check if empty
     * <p>
     *  检查是否为空
     * 
     */
    public boolean isEmpty() {
        return type == EMPTY;
    }

    /**
     * Convert to a string.
     * <p>
     *  转换为字符串。
     * 
     */
    public String toString() {
        return name;
    }

    /**
     * Get an attribute by name.
     * <p>
     *  按名称获取属性。
     * 
     */
    public AttributeList getAttribute(String name) {
        for (AttributeList a = atts ; a != null ; a = a.next) {
            if (a.name.equals(name)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Get an attribute by value.
     * <p>
     *  按值获取属性。
     */
    public AttributeList getAttributeByValue(String name) {
        for (AttributeList a = atts ; a != null ; a = a.next) {
            if ((a.values != null) && a.values.contains(name)) {
                return a;
            }
        }
        return null;
    }


    static Hashtable<String, Integer> contentTypes = new Hashtable<String, Integer>();

    static {
        contentTypes.put("CDATA", Integer.valueOf(CDATA));
        contentTypes.put("RCDATA", Integer.valueOf(RCDATA));
        contentTypes.put("EMPTY", Integer.valueOf(EMPTY));
        contentTypes.put("ANY", Integer.valueOf(ANY));
    }

    public static int name2type(String nm) {
        Integer val = contentTypes.get(nm);
        return (val != null) ? val.intValue() : 0;
    }
}
