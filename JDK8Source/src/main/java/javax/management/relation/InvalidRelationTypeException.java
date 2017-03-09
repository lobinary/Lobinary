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
 * Invalid relation type.
 * This exception is raised when, in a relation type, there is already a
 * relation type with that name, or the same name has been used for two
 * different role infos, or no role info provided, or one null role info
 * provided.
 *
 * <p>
 *  关系类型无效。当在关系类型中已经存在具有该名称的关系类型,或者已经为两个不同的角色信息使用相同的名称,或者没有提供角色信息或者提供一个空角色信息时,引发此异常。
 * 
 * 
 * @since 1.5
 */
public class InvalidRelationTypeException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = 3007446608299169961L;

    /**
     * Default constructor, no message put in exception.
     * <p>
     *  默认构造函数,没有消息放在异常。
     * 
     */
    public InvalidRelationTypeException() {
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
    public InvalidRelationTypeException(String message) {
        super(message);
    }
}
