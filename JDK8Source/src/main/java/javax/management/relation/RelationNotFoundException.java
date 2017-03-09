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
 * This exception is raised when there is no relation for a given relation id
 * in a Relation Service.
 *
 * <p>
 *  当在关系服务中没有给定关系id的关系时,引发此异常。
 * 
 * 
 * @since 1.5
 */
public class RelationNotFoundException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = -3793951411158559116L;

    /**
     * Default constructor, no message put in exception.
     * <p>
     *  默认构造函数,没有消息放在异常。
     * 
     */
    public RelationNotFoundException() {
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
    public RelationNotFoundException(String message) {
        super(message);
    }
}
