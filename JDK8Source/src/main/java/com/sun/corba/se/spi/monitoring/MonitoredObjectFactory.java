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
 *
 * MonitoredObject Factory to create Monitored Object.
 * </p>
 */
public interface MonitoredObjectFactory {
    /**
     *  A Simple Factory Method to create the Monitored Object. The name
     *  should be the leaf level name.
     * <p>
     *  用于创建受监控对象的简单工厂方法。名称应该是叶级别名称。
     */
    MonitoredObject createMonitoredObject( String name, String description );
}
