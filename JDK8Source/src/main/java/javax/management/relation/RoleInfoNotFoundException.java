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
 * This exception is raised when there is no role info with given name in a
 * given relation type.
 *
 * <p>
 *  当在给定的关系类型中没有具有给定名称的角色信息时,引发此异常。
 * 
 * 
 * @since 1.5
 */
public class RoleInfoNotFoundException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = 4394092234999959939L;

    /**
     * Default constructor, no message put in exception.
     * <p>
     *  默认构造函数,没有消息放在异常。
     * 
     */
    public RoleInfoNotFoundException() {
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
    public RoleInfoNotFoundException(String message) {
        super(message);
    }
}
