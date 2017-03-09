/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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


package javax.naming.spi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;

/**
  * This interface represents an "intermediate context" for name resolution.
  *<p>
  * The Resolver interface contains methods that are implemented by contexts
  * that do not support subtypes of Context, but which can act as
  * intermediate contexts for resolution purposes.
  *<p>
  * A <tt>Name</tt> parameter passed to any method is owned
  * by the caller.  The service provider will not modify the object
  * or keep a reference to it.
  * A <tt>ResolveResult</tt> object returned by any
  * method is owned by the caller.  The caller may subsequently modify it;
  * the service provider may not.
  *
  * <p>
  *  此接口表示名称解析的"中间上下文"。
  * p>
  *  Resolver接口包含由不支持上下文子类型但可以作为中间上下文用于解析目的的上下文实现的方法。
  * p>
  *  传递给任何方法的<tt> Name </tt>参数由调用者拥有。服务提供程序不会修改对象或保留对它的引用。由任何方法返回的<tt> ResolveResult </tt>对象由调用者拥有。
  * 调用者可以随后修改它;服务提供商可能不会。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public interface Resolver {

    /**
     * Partially resolves a name.  Stops at the first
     * context that is an instance of a given subtype of
     * <code>Context</code>.
     *
     * <p>
     * 
     * @param name
     *          the name to resolve
     * @param contextType
     *          the type of object to resolve.  This should
     *          be a subtype of <code>Context</code>.
     * @return  the object that was found, along with the unresolved
     *          suffix of <code>name</code>.  Cannot be null.
     *
     * @throws  javax.naming.NotContextException
     *          if no context of the appropriate type is found
     * @throws  NamingException if a naming exception was encountered
     *
     * @see #resolveToClass(String, Class)
     */
    public ResolveResult resolveToClass(Name name,
                                        Class<? extends Context> contextType)
            throws NamingException;

    /**
     * Partially resolves a name.
     * See {@link #resolveToClass(Name, Class)} for details.
     *
     * <p>
     *  部分解析名称。停止在第一个上下文,它是<code> Context </code>的给定子类型的实例。
     * 
     * 
     * @param name
     *          the name to resolve
     * @param contextType
     *          the type of object to resolve.  This should
     *          be a subtype of <code>Context</code>.
     * @return  the object that was found, along with the unresolved
     *          suffix of <code>name</code>.  Cannot be null.
     *
     * @throws  javax.naming.NotContextException
     *          if no context of the appropriate type is found
     * @throws  NamingException if a naming exception was encountered
     */
    public ResolveResult resolveToClass(String name,
                                        Class<? extends Context> contextType)
            throws NamingException;
};
