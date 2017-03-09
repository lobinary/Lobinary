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
 * This exception is raised when a role in a relation does not exist, or is not
 * readable, or is not settable.
 *
 * <p>
 *  当关系中的角色不存在,或不可读或不可设置时,引发此异常。
 * 
 * 
 * @since 1.5
 */
public class RoleNotFoundException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = -2986406101364031481L;

    /**
     * Default constructor, no message put in exception.
     * <p>
     *  默认构造函数,没有消息放在异常。
     * 
     */
    public RoleNotFoundException() {
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
    public RoleNotFoundException(String message) {
        super(message);
    }
}
