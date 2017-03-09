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

package java.beans;

import java.applet.Applet;

import java.beans.beancontext.BeanContext;

/**
 * <p>
 * This interface is designed to work in collusion with java.beans.Beans.instantiate.
 * The interface is intended to provide mechanism to allow the proper
 * initialization of JavaBeans that are also Applets, during their
 * instantiation by java.beans.Beans.instantiate().
 * </p>
 *
 * <p>
 * <p>
 *  此接口设计为与java.beans.Beans.instantiate协同工作。
 * 该接口旨在提供机制,以允许对JavaBean(也是Applet)进行适当的初始化,在由java.beans.Beans.instantiate()进行实例化时。
 * </p>
 * 
 * 
 * @see java.beans.Beans#instantiate
 *
 * @since 1.2
 *
 */


public interface AppletInitializer {

    /**
     * <p>
     * If passed to the appropriate variant of java.beans.Beans.instantiate
     * this method will be called in order to associate the newly instantiated
     * Applet (JavaBean) with its AppletContext, AppletStub, and Container.
     * </p>
     * <p>
     * Conformant implementations shall:
     * <ol>
     * <li> Associate the newly instantiated Applet with the appropriate
     * AppletContext.
     *
     * <li> Instantiate an AppletStub() and associate that AppletStub with
     * the Applet via an invocation of setStub().
     *
     * <li> If BeanContext parameter is null, then it shall associate the
     * Applet with its appropriate Container by adding that Applet to its
     * Container via an invocation of add(). If the BeanContext parameter is
     * non-null, then it is the responsibility of the BeanContext to associate
     * the Applet with its Container during the subsequent invocation of its
     * addChildren() method.
     * </ol>
     *
     * <p>
     * <p>
     *  如果传递给java.beans.Beans.instantiate的适当变体,则将调用此方法以将新实例化的Applet(JavaBean)与其AppletContext,AppletStub和Cont
     * ainer相关联。
     * </p>
     * <p>
     *  一致的实现应：
     * <ol>
     *  <li>将新实例化的Applet与相应的AppletContext关联。
     * 
     *  <li>实例化AppletStub(),并通过调用setStub()将AppletStub与Applet关联。
     * 
     * @param newAppletBean  The newly instantiated JavaBean
     * @param bCtxt          The BeanContext intended for this Applet, or
     *                       null.
     */

    void initialize(Applet newAppletBean, BeanContext bCtxt);

    /**
     * <p>
     * Activate, and/or mark Applet active. Implementors of this interface
     * shall mark this Applet as active, and optionally invoke its start()
     * method.
     * </p>
     *
     * <p>
     * 
     *  <li>如果BeanContext参数为null,则它应通过调用add()将Applet添加到其容器中来将Applet与其相应的容器相关联。
     * 如果BeanContext参数是非空的,那么BeanContext的责任是在后续调用addChildren()方法时将Applet与其容器相关联。
     * </ol>
     * 
     * 
     * @param newApplet  The newly instantiated JavaBean
     */

    void activate(Applet newApplet);
}
