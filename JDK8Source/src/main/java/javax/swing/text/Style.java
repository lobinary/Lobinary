/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Component;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.util.Enumeration;
import java.util.Hashtable;



/**
 * A collection of attributes to associate with an element in a document.
 * Since these are typically used to associate character and paragraph
 * styles with the element, operations for this are provided.  Other
 * customized attributes that get associated with the element will
 * effectively be name-value pairs that live in a hierarchy and if a name
 * (key) is not found locally, the request is forwarded to the parent.
 * Commonly used attributes are separated out to facilitate alternative
 * implementations that are more efficient.
 *
 * <p>
 *  要与文档中的元素关联的属性集合。由于这些通常用于将字符和段落样式与元素相关联,因此提供了操作。
 * 与元素相关联的其他自定义属性将实际上是居住在层次结构中的名称 - 值对,并且如果在本地未找到名称(键),则将请求转发给父级。通常使用的属性被分离以便于更高效的替代实现。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface Style extends MutableAttributeSet {

    /**
     * Fetches the name of the style.   A style is not required to be named,
     * so <code>null</code> is returned if there is no name
     * associated with the style.
     *
     * <p>
     *  获取样式的名称。样式不需要命名,因此如果没有与样式相关联的名称,则会返回<code> null </code>。
     * 
     * 
     * @return the name
     */
    public String getName();

    /**
     * Adds a listener to track whenever an attribute
     * has been changed.
     *
     * <p>
     *  添加一个监听器,以便在属性更改时进行跟踪。
     * 
     * 
     * @param l the change listener
     */
    public void addChangeListener(ChangeListener l);

    /**
     * Removes a listener that was tracking attribute changes.
     *
     * <p>
     *  删除跟踪属性更改的监听器。
     * 
     * @param l the change listener
     */
    public void removeChangeListener(ChangeListener l);


}
