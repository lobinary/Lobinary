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
 * This exception is raised when relation id provided for a relation is already
 * used.
 *
 * <p>
 *  当为关系提供的关系id已经使用时,引发此异常。
 * 
 * 
 * @since 1.5
 */
public class InvalidRelationIdException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = -7115040321202754171L;

    /**
     * Default constructor, no message put in exception.
     * <p>
     *  默认构造函数,没有消息放在异常。
     * 
     */
    public InvalidRelationIdException() {
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
    public InvalidRelationIdException(String message) {
        super(message);
    }
}
