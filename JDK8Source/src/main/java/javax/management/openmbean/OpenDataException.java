/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.openmbean;

// jmx import
//
import javax.management.JMException;

/**
 * This checked exception is thrown when an <i>open type</i>, an <i>open data</i>  or an <i>open MBean metadata info</i> instance
 * could not be constructed because one or more validity constraints were not met.
 *
 *
 * <p>
 *  当未能构建<i>打开类型</i>,<i>打开数据</i>或<i>打开MBean元数据信息</i>实例时抛出此已检查异常,因为一个或多个有效性约束未得到满足。
 * 
 * 
 * @since 1.5
 */
public class OpenDataException extends JMException {

    private static final long serialVersionUID = 8346311255433349870L;

    /**
     * An OpenDataException with no detail message.
     * <p>
     *  没有详细消息的OpenDataException。
     * 
     */
    public OpenDataException() {
        super();
    }

    /**
     * An OpenDataException with a detail message.
     *
     * <p>
     *  带有详细消息的OpenDataException。
     * 
     * @param msg the detail message.
     */
    public OpenDataException(String msg) {
        super(msg);
    }

}
