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

import com.sun.corba.se.spi.monitoring.MonitoredAttributeInfo;
import java.util.*;

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
 * Monitored Attribute is the interface to represent a Monitorable
 * Attribute. Using this interface, one can get the value of the attribute
 * and set the value if it is a writeable attribute.
 * </p>
 */
public interface MonitoredAttribute {

  ///////////////////////////////////////
  // operations

/**
 * <p>
 * Gets the Monitored Attribute Info for the attribute.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  获取属性的监视属性信息。
 * </p>
 * <p>
 * 
 * 
 * @param monitoredAttributeInfo for this Monitored Attribute.
 * </p>
 */
    public MonitoredAttributeInfo getAttributeInfo();
/**
 * <p>
 * Sets the value for the Monitored Attribute if isWritable() is false, the
 * method will throw ILLEGAL Operation exception.
 *
 * Also, the type of 'value' should be same as specified in the
 * MonitoredAttributeInfo for a particular instance.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  设置监视属性的值,如果isWritable()为false,则该方法将抛出ILLEGAL操作异常。
 * 
 *  另外,"值"的类型应该与特定实例的MonitoredAttributeInfo中指定的相同。
 * </p>
 * <p>
 * 
 * 
 * @param value should be any one of the Basic Java Type Objects which are
 * Long, Double, Float, String, Integer, Short, Character, Byte.
 * </p>
 */
    public void setValue(Object value);


/**
 * <p>
 * Gets the value of the Monitored Attribute. The value can be obtained
 * from different parts of the module. User may choose to delegate the call
 * to getValue() to other variables.
 *
 * NOTE: It is important to make sure that the type of Object returned in
 * getvalue is same as the one specified in MonitoredAttributeInfo for this
 * attribute.
 * </p>
 * <p>
 *
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  获取"监视属性"的值。该值可以从模块的不同部分获得。用户可以选择将对getValue()的调用委托给其他变量。
 * 
 *  注意：确保getvalue中返回的对象类型与此属性的MonitoredAttributeInfo中指定的对象类型相同非常重要。
 * </p>
 * <p>
 * 
 * </p>
 * <p>
 * 
 * 
 * @param value is the current value for this MonitoredAttribute
 * </p>
 */
    public Object getValue();
/**
 * <p>
 * Gets the name of the Monitored Attribute.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  获取监视属性的名称。
 * </p>
 * <p>
 * 
 * 
 * @param name of this Attribute
 * </p>
 */
    public String getName();
/**
 * <p>
 * If this attribute needs to be cleared, the user needs to implement this
 * method to reset the state to initial state. If the Monitored Attribute
 * doesn't change like for example (ConnectionManager High Water Mark),
 * then clearState() is a No Op.
 * </p>
 *
 * <p>
 */
    public void clearState();

} // end MonitoredAttribute
