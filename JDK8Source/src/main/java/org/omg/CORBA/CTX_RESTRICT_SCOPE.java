/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * A flag that can be used as the second parameter to the method
 * <code>Context.get_values</code> to restrict the search scope.
 * When this flag is used, it restricts the search for
 * context values to this particular <code>Context</code> object
 * or to the scope specified in the first parameter to
 * <code>Context.get_values</code>.
 * <P>
 * Usage:
 * <PRE>
 *     NVList props = myContext.get_values("_USER",
 *                     CTX_RESTRICT_SCOPE.value, "id*");
 * </PRE>
 *
 * <p>
 *  可以用作方法<code> Context.get_values </code>的第二个参数的标志,用于限制搜索范围。
 * 当使用此标志时,它将搜索上下文值限制到此特定<code> Context </code>对象或第一个参数中指定的范围为<code> Context.get_values </code>。
 * <P>
 *  用法：
 * <PRE>
 *  NVList props = myContext.get_values("_ USER",CTX_RESTRICT_SCOPE.value,"id *");
 * 
 * @see org.omg.CORBA.Context#get_values(String, int, String)
 * @since   JDK1.2
 */
public interface CTX_RESTRICT_SCOPE {

/**
 * The field containing the <code>int</code> value of a
 * <code>CTX_RESTRICT_SCOPE</code> flag.
 * <p>
 * </PRE>
 * 
 */
  int value = 15;
}
