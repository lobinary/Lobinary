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
 *  A Convenient class provided to help users extend and implement only
 *  getValue(), if there is no need to clear the state and the attribute is not
 *  writable.
 *
 * </p>
 */
public abstract class MonitoredAttributeBase implements MonitoredAttribute {
    String name;
    MonitoredAttributeInfo attributeInfo;
    /**
     * Constructor.
     * <p>
     *  构造函数。
     * 
     */
    public MonitoredAttributeBase( String name, MonitoredAttributeInfo info ) {
        this.name = name;
        this.attributeInfo = info;
    }


    /**
     * A Package Private Constructor for internal use only.
     * <p>
     *  Package Private Constructor仅供内部使用。
     * 
     */
    MonitoredAttributeBase( String name ) {
        this.name = name;
    }


    /**
     * A Package Private convenience method for setting MonitoredAttributeInfo
     * for this Monitored Attribute.
     * <p>
     *  包用于为此监视属性设置MonitoredAttributeInfo的私人便捷方法。
     * 
     */
    void setMonitoredAttributeInfo( MonitoredAttributeInfo info ) {
        this.attributeInfo = info;
    }

    /**
     *  If the concrete class decides not to provide the implementation of this
     *  method, then it's OK. Some of the  examples where we may decide to not
     *  provide the implementation is the connection state. Irrespective of
     *  the call to clearState, the connection state will be showing the
     *  currect state of the connection.
     *  NOTE: This method is only used to clear the Monitored Attribute state,
     *  not the real state of the system itself.
     * <p>
     *  如果具体类决定不提供这种方法的实现,那就没关系。我们可能决定不提供实现的一些示例是连接状态。不考虑对clearState的调用,连接状态将显示连接的正确状态。
     * 注意：此方法仅用于清除监视属性状态,而不是系统本身的实际状态。
     * 
     */
    public void clearState( ) {
    }

    /**
     *  This method should be implemented by the concrete class.
     * <p>
     *  这个方法应该由具体类实现。
     * 
     */
    public abstract Object getValue( );

    /**
     *  This method should be implemented by the concrete class only if the
     *  attribute is writable. If the attribute is not writable and if this
     *  method called, it will result in an IllegalStateException.
     * <p>
     *  此方法应该由具体类实现,只有该属性是可写的。如果属性不可写,并且此方法调用,它将导致IllegalStateException。
     * 
     */
    public void setValue( Object value ) {
        if( !attributeInfo.isWritable() ) {
            throw new IllegalStateException(
                "The Attribute " + name + " is not Writable..." );
        }
        throw new IllegalStateException(
            "The method implementation is not provided for the attribute " +
            name );
    }


    /**
     *  Gets the MonitoredAttributeInfo for the attribute.
     * <p>
     *  获取属性的MonitoredAttributeInfo。
     * 
     */
    public MonitoredAttributeInfo getAttributeInfo( ) {
        return attributeInfo;
    }

    /**
     * Gets the name of the attribute.
     * <p>
     *  获取属性的名称。
     */
    public String getName( ) {
        return name;
    }
} // end MonitoredAttributeBase
