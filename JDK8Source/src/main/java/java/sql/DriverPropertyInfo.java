/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

/**
 * <p>Driver properties for making a connection. The
 * <code>DriverPropertyInfo</code> class is of interest only to advanced programmers
 * who need to interact with a Driver via the method
 * <code>getDriverProperties</code> to discover
 * and supply properties for connections.
 * <p>
 *  <p>用于建立连接的驱动程序属性。
 *  <code> DriverPropertyInfo </code>类仅对需要通过方法<code> getDriverProperties </code>与驱动程序交互的高级程序员感兴趣,以发现并提供连
 * 接的属性。
 *  <p>用于建立连接的驱动程序属性。
 * 
 */

public class DriverPropertyInfo {

    /**
     * Constructs a <code>DriverPropertyInfo</code> object with a  given
     * name and value.  The <code>description</code> and <code>choices</code>
     * are initialized to <code>null</code> and <code>required</code> is initialized
     * to <code>false</code>.
     *
     * <p>
     *  构造具有给定名称和值的<code> DriverPropertyInfo </code>对象。
     *  <code>描述</code>和<code>选择</code>初始化为<code> null </code>,<code>必需</code>初始化为<code> false </code>。
     * 
     * 
     * @param name the name of the property
     * @param value the current value, which may be null
     */
    public DriverPropertyInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * The name of the property.
     * <p>
     *  属性的名称。
     * 
     */
    public String name;

    /**
     * A brief description of the property, which may be null.
     * <p>
     *  属性的简短描述,可以为null。
     * 
     */
    public String description = null;

    /**
     * The <code>required</code> field is <code>true</code> if a value must be
         * supplied for this property
     * during <code>Driver.connect</code> and <code>false</code> otherwise.
     * <p>
     *  如果在<code> Driver.connect </code>和<code> false </code>期间必须为此属性提供值,则<code> required </code>字段为<code> t
     * rue </code>。
     * 
     */
    public boolean required = false;

    /**
     * The <code>value</code> field specifies the current value of
         * the property, based on a combination of the information
         * supplied to the method <code>getPropertyInfo</code>, the
     * Java environment, and the driver-supplied default values.  This field
     * may be null if no value is known.
     * <p>
     *  <code> value </code>字段基于提供给方法<code> getPropertyInfo </code>,Java环境和驱动程序提供的默认值的信息的组合来指定属性的当前值。
     * 如果没有值已知,则此字段可以为null。
     * 
     */
    public String value = null;

    /**
     * An array of possible values if the value for the field
         * <code>DriverPropertyInfo.value</code> may be selected
         * from a particular set of values; otherwise null.
         * <p>
     */
    public String[] choices = null;
}
