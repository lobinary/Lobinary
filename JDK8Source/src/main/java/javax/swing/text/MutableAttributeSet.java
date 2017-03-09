/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.util.Enumeration;

/**
 * A generic interface for a mutable collection of unique attributes.
 *
 * Implementations will probably want to provide a constructor of the
 * form:<tt>
 * public XXXAttributeSet(ConstAttributeSet source);</tt>
 *
 * <p>
 *  唯一属性的可变集合的通用接口。
 * 
 *  实现可能想要提供一个形式的构造函数：<tt> public XXXAttributeSet(ConstAttributeSet source); </tt>
 * 
 */
public interface MutableAttributeSet extends AttributeSet {

    /**
     * Creates a new attribute set similar to this one except that it contains
     * an attribute with the given name and value.  The object must be
     * immutable, or not mutated by any client.
     *
     * <p>
     *  创建与此类似的新属性集,但它包含具有给定名称和值的属性。对象必须是不可变的,或者不被任何客户端改变。
     * 
     * 
     * @param name the name
     * @param value the value
     */
    public void addAttribute(Object name, Object value);

    /**
     * Creates a new attribute set similar to this one except that it contains
     * the given attributes and values.
     *
     * <p>
     *  创建与此类似的新属性集,但它包含给定的属性和值。
     * 
     * 
     * @param attributes the set of attributes
     */
    public void addAttributes(AttributeSet attributes);

    /**
     * Removes an attribute with the given <code>name</code>.
     *
     * <p>
     *  删除带有给定<code>名称</code>的属性。
     * 
     * 
     * @param name the attribute name
     */
    public void removeAttribute(Object name);

    /**
     * Removes an attribute set with the given <code>names</code>.
     *
     * <p>
     *  删除具有给定<code>名称</code>的属性集。
     * 
     * 
     * @param names the set of names
     */
    public void removeAttributes(Enumeration<?> names);

    /**
     * Removes a set of attributes with the given <code>name</code>.
     *
     * <p>
     *  使用给定的<code> name </code>删除一组属性。
     * 
     * 
     * @param attributes the set of attributes
     */
    public void removeAttributes(AttributeSet attributes);

    /**
     * Sets the resolving parent.  This is the set
     * of attributes to resolve through if an attribute
     * isn't defined locally.
     *
     * <p>
     *  设置解析父代。如果属性未在本地定义,则这是要解析的属性集。
     * 
     * @param parent the parent
     */
    public void setResolveParent(AttributeSet parent);

}
