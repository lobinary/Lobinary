/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

/**
 * <p> This interface represents a guard, which is an object that is used
 * to protect access to another object.
 *
 * <p>This interface contains a single method, {@code checkGuard},
 * with a single {@code object} argument. {@code checkGuard} is
 * invoked (by the GuardedObject {@code getObject} method)
 * to determine whether or not to allow access to the object.
 *
 * <p>
 *  <p>此接口代表防护,它是用于保护对另一个对象的访问的对象。
 * 
 *  <p>此接口包含一个{@code checkGuard}方法,只有一个{@code object}参数。
 *  {@code checkGuard}被调用(由GuardedObject {@code getObject}方法)以确定是否允许对对象的访问。
 * 
 * @see GuardedObject
 *
 * @author Roland Schemers
 * @author Li Gong
 */

public interface Guard {

    /**
     * Determines whether or not to allow access to the guarded object
     * {@code object}. Returns silently if access is allowed.
     * Otherwise, throws a SecurityException.
     *
     * <p>
     * 
     * 
     * @param object the object being protected by the guard.
     *
     * @exception SecurityException if access is denied.
     *
     */
    void checkGuard(Object object) throws SecurityException;
}
