/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javadoc;

/**
/* <p>
/* 
 * Represents an @param documentation tag.
 * Stores the name and comment parts of the parameter tag.
 * An @param tag may represent either a method or constructor parameter,
 * or a type parameter.
 *
 * @author Robert Field
 *
 */
public interface ParamTag extends Tag {

    /**
     * Return the name of the parameter or type parameter
     * associated with this <code>ParamTag</code>.
     * The angle brackets delimiting a type parameter are not part of
     * its name.
     *
     * <p>
     *  返回与此<code> ParamTag </code>关联的参数或类型参数的名称。定界类型参数的尖括号不是其名称的一部分。
     * 
     * 
     * @return the parameter name.
     */
    String parameterName();

    /**
     * Return the parameter comment
     * associated with this <code>ParamTag</code>.
     *
     * <p>
     *  返回与此<code> ParamTag </code>相关联的参数注释。
     * 
     * 
     * @return the parameter comment.
     */
    String parameterComment();

    /**
     * Return true if this <code>ParamTag</code> corresponds to a type
     * parameter.  Return false if it corresponds to an ordinary parameter
     * of a method or constructor.
     *
     * <p>
     *  如果此<code> ParamTag </code>对应于类型参数,则返回true。如果它对应于方法或构造函数的普通参数,则返回false。
     * 
     * @return true if this <code>ParamTag</code> corresponds to a type
     * parameter.
     * @since 1.5
     */
    boolean isTypeParameter();
}
