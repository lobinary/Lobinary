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
 * This is an exception that is thrown whenever an attempt is made to delete
 * the last owner of an Access Control List.
 *
 * <p>
 *  这是每当尝试删除访问控制列表的最后所有者时抛出的异常。
 * 
 * 
 * @see java.security.acl.Owner#deleteOwner
 *
 * @author Satish Dharmaraj
 */
public class LastOwnerException extends Exception {

    private static final long serialVersionUID = -5141997548211140359L;

    /**
     * Constructs a LastOwnerException.
     * <p>
     *  构造LastOwnerException。
     */
    public LastOwnerException() {
    }
}
