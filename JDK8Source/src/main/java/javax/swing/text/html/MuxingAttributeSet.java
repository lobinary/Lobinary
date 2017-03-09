/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text.html;

import javax.swing.text.*;
import java.io.Serializable;
import java.util.*;

/**
 * An implementation of <code>AttributeSet</code> that can multiplex
 * across a set of <code>AttributeSet</code>s.
 *
 * <p>
 *  可以跨一组<code> AttributeSet </code>进行多路复用的<code> AttributeSet </code>的实现。
 * 
 */
class MuxingAttributeSet implements AttributeSet, Serializable {
    /**
     * Creates a <code>MuxingAttributeSet</code> with the passed in
     * attributes.
     * <p>
     *  使用传入的属性创建<code> MuxingAttributeSet </code>。
     * 
     */
    public MuxingAttributeSet(AttributeSet[] attrs) {
        this.attrs = attrs;
    }

    /**
     * Creates an empty <code>MuxingAttributeSet</code>. This is intended for
     * use by subclasses only, and it is also intended that subclasses will
     * set the constituent <code>AttributeSet</code>s before invoking any
     * of the <code>AttributeSet</code> methods.
     * <p>
     *  创建一个空的<code> MuxingAttributeSet </code>。
     * 这仅供子类使用,并且还希望子类在调用任何<code> AttributeSet </code>方法之前设置组成<code> AttributeSet </code>。
     * 
     */
    protected MuxingAttributeSet() {
    }

    /**
     * Directly sets the <code>AttributeSet</code>s that comprise this
     * <code>MuxingAttributeSet</code>.
     * <p>
     *  直接设置构成此<code> MuxingAttributeSet </code>的<code> AttributeSet </code>。
     * 
     */
    protected synchronized void setAttributes(AttributeSet[] attrs) {
        this.attrs = attrs;
    }

    /**
     * Returns the <code>AttributeSet</code>s multiplexing too. When the
     * <code>AttributeSet</code>s need to be referenced, this should be called.
     * <p>
     *  返回<code> AttributeSet </code>的复用。当需要引用<code> AttributeSet </code>时,应该调用它。
     * 
     */
    protected synchronized AttributeSet[] getAttributes() {
        return attrs;
    }

    /**
     * Inserts <code>as</code> at <code>index</code>. This assumes
     * the value of <code>index</code> is between 0 and attrs.length,
     * inclusive.
     * <p>
     *  在<code> index </code>处插入<code>为</code>。这假定<code> index </code>的值在0和attrs.length(含)之间。
     * 
     */
    protected synchronized void insertAttributeSetAt(AttributeSet as,
                                                     int index) {
        int numAttrs = attrs.length;
        AttributeSet newAttrs[] = new AttributeSet[numAttrs + 1];
        if (index < numAttrs) {
            if (index > 0) {
                System.arraycopy(attrs, 0, newAttrs, 0, index);
                System.arraycopy(attrs, index, newAttrs, index + 1,
                                 numAttrs - index);
            }
            else {
                System.arraycopy(attrs, 0, newAttrs, 1, numAttrs);
            }
        }
        else {
            System.arraycopy(attrs, 0, newAttrs, 0, numAttrs);
        }
        newAttrs[index] = as;
        attrs = newAttrs;
    }

    /**
     * Removes the AttributeSet at <code>index</code>. This assumes
     * the value of <code>index</code> is greater than or equal to 0,
     * and less than attrs.length.
     * <p>
     *  删除<code> index </code>下的AttributeSet。这假设<code> index </code>的值大于或等于0,小于attrs.length。
     * 
     */
    protected synchronized void removeAttributeSetAt(int index) {
        int numAttrs = attrs.length;
        AttributeSet[] newAttrs = new AttributeSet[numAttrs - 1];
        if (numAttrs > 0) {
            if (index == 0) {
                // FIRST
                System.arraycopy(attrs, 1, newAttrs, 0, numAttrs - 1);
            }
            else if (index < (numAttrs - 1)) {
                // MIDDLE
                System.arraycopy(attrs, 0, newAttrs, 0, index);
                System.arraycopy(attrs, index + 1, newAttrs, index,
                                 numAttrs - index - 1);
            }
            else {
                // END
                System.arraycopy(attrs, 0, newAttrs, 0, numAttrs - 1);
            }
        }
        attrs = newAttrs;
    }

    //  --- AttributeSet methods ----------------------------

    /**
     * Gets the number of attributes that are defined.
     *
     * <p>
     *  获取定义的属性数。
     * 
     * 
     * @return the number of attributes
     * @see AttributeSet#getAttributeCount
     */
    public int getAttributeCount() {
        AttributeSet[] as = getAttributes();
        int n = 0;
        for (int i = 0; i < as.length; i++) {
            n += as[i].getAttributeCount();
        }
        return n;
    }

    /**
     * Checks whether a given attribute is defined.
     * This will convert the key over to CSS if the
     * key is a StyleConstants key that has a CSS
     * mapping.
     *
     * <p>
     *  检查是否定义了给定属性。如果键是具有CSS映射的StyleConstants键,这将把键转换为CSS。
     * 
     * 
     * @param key the attribute key
     * @return true if the attribute is defined
     * @see AttributeSet#isDefined
     */
    public boolean isDefined(Object key) {
        AttributeSet[] as = getAttributes();
        for (int i = 0; i < as.length; i++) {
            if (as[i].isDefined(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether two attribute sets are equal.
     *
     * <p>
     *  检查两个属性集是否相等。
     * 
     * 
     * @param attr the attribute set to check against
     * @return true if the same
     * @see AttributeSet#isEqual
     */
    public boolean isEqual(AttributeSet attr) {
        return ((getAttributeCount() == attr.getAttributeCount()) &&
                containsAttributes(attr));
    }

    /**
     * Copies a set of attributes.
     *
     * <p>
     *  复制一组属性。
     * 
     * 
     * @return the copy
     * @see AttributeSet#copyAttributes
     */
    public AttributeSet copyAttributes() {
        AttributeSet[] as = getAttributes();
        MutableAttributeSet a = new SimpleAttributeSet();
        int n = 0;
        for (int i = as.length - 1; i >= 0; i--) {
            a.addAttributes(as[i]);
        }
        return a;
    }

    /**
     * Gets the value of an attribute.  If the requested
     * attribute is a StyleConstants attribute that has
     * a CSS mapping, the request will be converted.
     *
     * <p>
     * 获取属性的值。如果请求的属性是具有CSS映射的StyleConstants属性,请求将被转换。
     * 
     * 
     * @param key the attribute name
     * @return the attribute value
     * @see AttributeSet#getAttribute
     */
    public Object getAttribute(Object key) {
        AttributeSet[] as = getAttributes();
        int n = as.length;
        for (int i = 0; i < n; i++) {
            Object o = as[i].getAttribute(key);
            if (o != null) {
                return o;
            }
        }
        return null;
    }

    /**
     * Gets the names of all attributes.
     *
     * <p>
     *  获取所有属性的名称。
     * 
     * 
     * @return the attribute names
     * @see AttributeSet#getAttributeNames
     */
    public Enumeration getAttributeNames() {
        return new MuxingAttributeNameEnumeration();
    }

    /**
     * Checks whether a given attribute name/value is defined.
     *
     * <p>
     *  检查是否定义了给定的属性名称/值。
     * 
     * 
     * @param name the attribute name
     * @param value the attribute value
     * @return true if the name/value is defined
     * @see AttributeSet#containsAttribute
     */
    public boolean containsAttribute(Object name, Object value) {
        return value.equals(getAttribute(name));
    }

    /**
     * Checks whether the attribute set contains all of
     * the given attributes.
     *
     * <p>
     *  检查属性集是否包含所有给定的属性。
     * 
     * 
     * @param attrs the attributes to check
     * @return true if the element contains all the attributes
     * @see AttributeSet#containsAttributes
     */
    public boolean containsAttributes(AttributeSet attrs) {
        boolean result = true;

        Enumeration names = attrs.getAttributeNames();
        while (result && names.hasMoreElements()) {
            Object name = names.nextElement();
            result = attrs.getAttribute(name).equals(getAttribute(name));
        }

        return result;
    }

    /**
     * Returns null, subclasses may wish to do something more
     * intelligent with this.
     * <p>
     *  返回null,子类可能希望做一些更聪明的用这个。
     * 
     */
    public AttributeSet getResolveParent() {
        return null;
    }

    /**
     * The <code>AttributeSet</code>s that make up the resulting
     * <code>AttributeSet</code>.
     * <p>
     *  组成<code> AttributeSet </code>的<code> AttributeSet </code>。
     * 
     */
    private AttributeSet[] attrs;


    /**
     * An Enumeration of the Attribute names in a MuxingAttributeSet.
     * This may return the same name more than once.
     * <p>
     *  在MuxingAttributeSet中的属性名称的枚举。这可能会多次返回相同的名称。
     */
    private class MuxingAttributeNameEnumeration implements Enumeration {

        MuxingAttributeNameEnumeration() {
            updateEnum();
        }

        public boolean hasMoreElements() {
            if (currentEnum == null) {
                return false;
            }
            return currentEnum.hasMoreElements();
        }

        public Object nextElement() {
            if (currentEnum == null) {
                throw new NoSuchElementException("No more names");
            }
            Object retObject = currentEnum.nextElement();
            if (!currentEnum.hasMoreElements()) {
                updateEnum();
            }
            return retObject;
        }

        void updateEnum() {
            AttributeSet[] as = getAttributes();
            currentEnum = null;
            while (currentEnum == null && attrIndex < as.length) {
                currentEnum = as[attrIndex++].getAttributeNames();
                if (!currentEnum.hasMoreElements()) {
                    currentEnum = null;
                }
            }
        }


        /** Index into attrs the current Enumeration came from. */
        private int attrIndex;
        /** Enumeration from attrs. */
        private Enumeration currentEnum;
    }
}
