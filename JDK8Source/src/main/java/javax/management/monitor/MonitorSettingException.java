/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.monitor;


/**
 * Exception thrown by the monitor when a monitor setting becomes invalid while the monitor is running.
 * <P>
 * As the monitor attributes may change at runtime, a check is performed before each observation.
 * If a monitor attribute has become invalid, a monitor setting exception is thrown.
 *
 *
 * <p>
 *  当监视器设置在监视器运行时失效时,监视器抛出异常。
 * <P>
 *  由于监视器属性可能在运行时更改,因此在每次观察之前执行检查。如果监视器属性无效,则抛出监视器设置异常。
 * 
 * 
 * @since 1.5
 */
public class MonitorSettingException extends javax.management.JMRuntimeException {

    /* Serial version */
    private static final long serialVersionUID = -8807913418190202007L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public MonitorSettingException() {
        super();
    }

    /**
     * Constructor that allows an error message to be specified.
     *
     * <p>
     *  允许指定错误消息的构造方法。
     * 
     * @param message The specific error message.
     */
    public MonitorSettingException(String message) {
        super(message);
    }
}
