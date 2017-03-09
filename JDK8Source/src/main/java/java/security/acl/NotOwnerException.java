/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.security.acl;

/**
 * This is an exception that is thrown whenever the modification of an object
 * (such as an Access Control List) is only allowed to be done by an owner of
 * the object, but the Principal attempting the modification is not an owner.
 *
 * <p>
 *  这是每当对象(例如访问控制列表)的修改只允许由对象的所有者完成时抛出的异常,但是尝试修改的主体不是所有者。
 * 
 * 
 * @author      Satish Dharmaraj
 */
public class NotOwnerException extends Exception {

    private static final long serialVersionUID = -5555597911163362399L;

    /**
     * Constructs a NotOwnerException.
     * <p>
     *  构造一个NotOwnerException。
     */
    public NotOwnerException() {
    }
}
