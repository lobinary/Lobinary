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
 * This exception is raised when, in a role info, its minimum degree is greater
 * than its maximum degree.
 *
 * <p>
 *  当在角色信息中,其最小度数大于其最大度数时,引发此异常。
 * 
 * 
 * @since 1.5
 */
public class InvalidRoleInfoException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = 7517834705158932074L;

    /**
     * Default constructor, no message put in exception.
     * <p>
     *  默认构造函数,没有消息放在异常。
     * 
     */
    public InvalidRoleInfoException() {
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
    public InvalidRoleInfoException(String message) {
        super(message);
    }
}
