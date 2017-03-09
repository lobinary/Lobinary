/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.corba.se.spi.monitoring;


import com.sun.corba.se.spi.monitoring.MonitoredAttribute;
import java.util.*;
import java.util.Collection;

/**
 * <p>
 *
 * <p>
 * <p>
 * 
 * 
 * @author Hemanth Puttaswamy
 * </p>
 * <p>
 * Monitored Object provides an Hierarchichal view of the ORB Monitoring
 * System. It can contain multiple children and a single parent. Each
 * Monitored Object may also contain Multiple Monitored Attributes.
 * </p>
 */
public interface MonitoredObject {

  ///////////////////////////////////////
  // operations
/**
 * <p>
 * Gets the name of this MonitoredObject
 * </p><p>
 *
 * <p>
 * <p>
 *  获取此监控对象的名称</p> <p>
 * 
 * 
 * @return a String with name of this Monitored Object
 * </p>
 */
    public String getName();
/**
 * <p>
 * Gets the description of MonitoredObject
 * </p><p>
 *
 * <p>
 * <p>
 *  获取MonitoredObject的描述</p> <p>
 * 
 * 
 * @return a String with Monitored Object Description.
 * </p>
 */
    public String getDescription();
/**
 * <p>
 * This method will add a child Monitored Object to this Monitored Object.
 * </p>
 * <p>
 * </p>
 * <p>
 * <p>
 *  此方法将向此受监控对象添加子监控对象。
 * </p>
 * <p>
 * </p>
 */
    public void addChild( MonitoredObject m );
/**
 * <p>
 * This method will remove child Monitored Object identified by the given name
 * </p>
 * <p>
 * <p>
 * <p>
 *  此方法将删除由给定名称标识的子监视对象
 * </p>
 * <p>
 * 
 * @param name of the ChildMonitored Object
 * </p>
 */
    public void removeChild( String name );

/**
 * <p>
 * Gets the child MonitoredObject associated with this MonitoredObject
 * instance using name as the key. The name should be fully qualified name
 * like orb.connectionmanager
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  使用名称作为键,获取与此MonitoredObject实例关联的子MonitoredObject。名称应该是完全限定名,如orb.connectionmanager
 * </p>
 * <p>
 * 
 * 
 * @return a MonitoredObject identified by the given name
 * </p>
 * <p>
 * @param name of the ChildMonitored Object
 * </p>
 */
    public MonitoredObject getChild(String name);
/**
 * <p>
 * Gets all the Children registered under this instance of Monitored
 * Object.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  获取在此监视对象实例下注册的所有子项。
 * </p>
 * <p>
 * 
 * 
 * @return Collection of immediate Children associated with this MonitoredObject.
 * </p>
 */
    public Collection getChildren();
/**
 * <p>
 * Sets the parent for this Monitored Object.
 * </p>
 * <p>
 * </p>
 * <p>
 * <p>
 *  设置此受监控对象的父代。
 * </p>
 * <p>
 * </p>
 */
    public void setParent( MonitoredObject m );
/**
 * <p>
 * There will be only one parent for an instance of MontoredObject, this
 * call gets parent and returns null if the Monitored Object is the root.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  MontoredObject的实例只有一个父对象,此调用将获取父对象,并且如果受监控对象是根,则返回null。
 * </p>
 * <p>
 * 
 * 
 * @return a MonitoredObject which is a Parent of this Monitored Object instance
 * </p>
 */
    public MonitoredObject getParent();

/**
 * <p>
 * Adds the attribute with the given name.
 * </p>
 * <p>
 *
 * </p>
 * <p>
 * <p>
 * <p>
 *  添加具有给定名称的属性。
 * </p>
 * <p>
 * 
 * </p>
 * <p>
 * 
 * @param value is the MonitoredAttribute which will be set as one of the
 * attribute of this MonitoredObject.
 * </p>
 */
    public void addAttribute(MonitoredAttribute value);
/**
 * <p>
 * Removes the attribute with the given name.
 * </p>
 * <p>
 *
 * </p>
 * <p>
 * <p>
 * <p>
 *  删除具有给定名称的属性。
 * </p>
 * <p>
 * 
 * </p>
 * <p>
 * 
 * @param name is the MonitoredAttribute name
 * </p>
 */
    public void removeAttribute(String name);

/**
 * <p>
 * Gets the Monitored Object registered by the given name
 * </p>
 *
 * <p>
 * <p>
 * <p>
 *  获取由给定名称注册的监视对象
 * </p>
 * 
 * <p>
 * 
 * @return a MonitoredAttribute identified by the given name
 * </p>
 * <p>
 * @param name of the attribute
 * </p>
 */
    public MonitoredAttribute getAttribute(String name);
/**
 * <p>
 * Gets all the Monitored Attributes for this Monitored Objects. It doesn't
 * include the Child Monitored Object, that needs to be traversed using
 * getChild() or getChildren() call.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  获取此受监控对象的所有受监视属性。它不包括子监视对象,需要使用getChild()或getChildren()调用遍历。
 * </p>
 * <p>
 * 
 * 
 * @return Collection of all the Attributes for this MonitoredObject
 * </p>
 */
    public Collection getAttributes();
/**
 * <p>
 * Clears the state of all the Monitored Attributes associated with the
 * Monitored Object. It will also clear the state on all it's child
 * Monitored Object. The call to clearState will be initiated from
 * CORBAMBean.startMonitoring() call.
 * </p>
 *
 * <p>
 */
    public void clearState();

} // end MonitoredObject
