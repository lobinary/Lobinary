/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.beans.beancontext;

import java.beans.DesignMode;
import java.beans.Visibility;

import java.io.InputStream;
import java.io.IOException;

import java.net.URL;

import java.util.Collection;
import java.util.Locale;

/**
 * <p>
 * The BeanContext acts a logical hierarchical container for JavaBeans.
 * </p>
 *
 * <p>
 * <p>
 *  BeanContext作为JavaBeans的逻辑层次结构容器。
 * </p>
 * 
 * 
 * @author Laurence P. G. Cable
 * @since 1.2
 *
 * @see java.beans.Beans
 * @see java.beans.beancontext.BeanContextChild
 * @see java.beans.beancontext.BeanContextMembershipListener
 * @see java.beans.PropertyChangeEvent
 * @see java.beans.DesignMode
 * @see java.beans.Visibility
 * @see java.util.Collection
 */

@SuppressWarnings("rawtypes")
public interface BeanContext extends BeanContextChild, Collection, DesignMode, Visibility {

    /**
     * Instantiate the javaBean named as a
     * child of this <code>BeanContext</code>.
     * The implementation of the JavaBean is
     * derived from the value of the beanName parameter,
     * and is defined by the
     * <code>java.beans.Beans.instantiate()</code> method.
     *
     * <p>
     *  实例化名为<code> BeanContext </code>的子对象的javaBean。
     *  JavaBean的实现派生自beanName参数的值,并由<code> java.beans.Beans.instantiate()</code>方法定义。
     * 
     * 
     * @return a javaBean named as a child of this
     * <code>BeanContext</code>
     * @param beanName The name of the JavaBean to instantiate
     * as a child of this <code>BeanContext</code>
     * @throws IOException if an IO problem occurs
     * @throws ClassNotFoundException if the class identified
     * by the beanName parameter is not found
     */
    Object instantiateChild(String beanName) throws IOException, ClassNotFoundException;

    /**
     * Analagous to <code>java.lang.ClassLoader.getResourceAsStream()</code>,
     * this method allows a <code>BeanContext</code> implementation
     * to interpose behavior between the child <code>Component</code>
     * and underlying <code>ClassLoader</code>.
     *
     * <p>
     *  与<code> java.lang.ClassLoader.getResourceAsStream()</code>类似,此方法允许<code> BeanContext </code>实现在子<code>
     *  Component </code>和底层<code>之间插入行为> ClassLoader </code>。
     * 
     * 
     * @param name the resource name
     * @param bcc the specified child
     * @return an <code>InputStream</code> for reading the resource,
     * or <code>null</code> if the resource could not
     * be found.
     * @throws IllegalArgumentException if
     * the resource is not valid
     */
    InputStream getResourceAsStream(String name, BeanContextChild bcc) throws IllegalArgumentException;

    /**
     * Analagous to <code>java.lang.ClassLoader.getResource()</code>, this
     * method allows a <code>BeanContext</code> implementation to interpose
     * behavior between the child <code>Component</code>
     * and underlying <code>ClassLoader</code>.
     *
     * <p>
     *  类似于<code> java.lang.ClassLoader.getResource()</code>,此方法允许<code> BeanContext </code>实现介入子<code> Comp
     * onent </code> > ClassLoader </code>。
     * 
     * 
     * @param name the resource name
     * @param bcc the specified child
     * @return a <code>URL</code> for the named
     * resource for the specified child
     * @throws IllegalArgumentException
     * if the resource is not valid
     */
    URL getResource(String name, BeanContextChild bcc) throws IllegalArgumentException;

     /**
      * Adds the specified <code>BeanContextMembershipListener</code>
      * to receive <code>BeanContextMembershipEvents</code> from
      * this <code>BeanContext</code> whenever it adds
      * or removes a child <code>Component</code>(s).
      *
      * <p>
      *  添加指定的<code> BeanContextMembershipListener </code>,以在每次添加或删除子<code> Component </code>时从<code> BeanCon
      * text </code>接收<code> BeanContextMembershipEvents </code>。
      * 
      * 
      * @param bcml the BeanContextMembershipListener to be added
      */
    void addBeanContextMembershipListener(BeanContextMembershipListener bcml);

     /**
      * Removes the specified <code>BeanContextMembershipListener</code>
      * so that it no longer receives <code>BeanContextMembershipEvent</code>s
      * when the child <code>Component</code>(s) are added or removed.
      *
      * <p>
      *  删除指定的<code> BeanContextMembershipListener </code>,以便在添加或删除子<code> Component </code>时,不再接收<code> Bean
      * ContextMembershipEvent </code>。
      * 
      * 
      * @param bcml the <code>BeanContextMembershipListener</code>
      * to be removed
      */
    void removeBeanContextMembershipListener(BeanContextMembershipListener bcml);

    /**
     * This global lock is used by both <code>BeanContext</code>
     * and <code>BeanContextServices</code> implementors
     * to serialize changes in a <code>BeanContext</code>
     * hierarchy and any service requests etc.
     * <p>
     * 这个全局锁被<code> BeanContext </code>和<code> BeanContextServices </code>实现者用于序列化<code> BeanContext </code>
     * 层次结构和任何服务请求等中的更改。
     */
    public static final Object globalHierarchyLock = new Object();
}
