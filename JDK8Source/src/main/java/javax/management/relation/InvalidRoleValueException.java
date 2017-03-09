/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.relation;

/**
 * Role value is invalid.
 * This exception is raised when, in a role, the number of referenced MBeans
 * in given value is less than expected minimum degree, or the number of
 * referenced MBeans in provided value exceeds expected maximum degree, or
 * one referenced MBean in the value is not an Object of the MBean
 * class expected for that role, or an MBean provided for that role does not
 * exist.
 *
 * <p>
 *  角色值无效。
 * 当在角色中,给定值中引用的MBean的数量小于预期的最小度数,或者提供的值中引用的MBean的数量超过预期的最大度数,或该值中的一个引用的MBean不是对象时引发此异常的该角色所需的MBean类,或为该
 * 角色提供的MBean不存在。
 *  角色值无效。
 * 
 * 
 * @since 1.5
 */
public class InvalidRoleValueException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = -2066091747301983721L;

    /**
     * Default constructor, no message put in exception.
     * <p>
     *  默认构造函数,没有消息放在异常。
     * 
     */
    public InvalidRoleValueException() {
        super();
    }

    /**
     * Constructor with given message put in exception.
     *
     * <p>
     *  构造器与给定的消息放在异常。
     * 
     * @param message the detail message.
     */
    public InvalidRoleValueException(String message) {
        super(message);
    }
}
