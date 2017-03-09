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
 * Monitored AttributeInfo contains the meta information of the Monitored
 * Attribute.
 * </p>
 */
public interface MonitoredAttributeInfo {

  ///////////////////////////////////////
  // operations

/**
 * <p>
 * If the Attribute is writable from ASAdmin then isWritable() will return
 * true.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  如果属性是可写从ASAdmin然后isWritable()将返回true。
 * </p>
 * <p>
 * 
 * 
 * @return a boolean with true or false
 * </p>
 */
    public boolean isWritable();
/**
 * <p>
 * isStatistic() is true if the attribute is presented as a Statistic.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  如果属性显示为统计量,isStatistic()为true。
 * </p>
 * <p>
 * 
 * 
 * @return a boolean with true or false
 * </p>
 */
    public boolean isStatistic();
/**
 * <p>
 * Class Type: We will allow only basic class types: 1)Boolean 2)Integer
 * 3)Byte 4)Long 5)Float 6)Double 7)String 8)Character
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  类型：我们只允许基类类型：1)Boolean 2)Integer 3)Byte 4)Long 5)Float 6)Double 7)String 8)Cha​​racter
 * </p>
 * <p>
 * 
 * 
 * @return a Class Type
 * </p>
 */
    public Class type();
/**
 * <p>
 * Get's the description for the Monitored Attribute.
 * </p>
 * <p>
 *
 * <p>
 * <p>
 *  获取监视属性的描述。
 * </p>
 * 
 * @return a String with description
 * </p>
 */
    public String getDescription();

} // end MonitoredAttributeInfo
