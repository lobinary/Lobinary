/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * Parameter information.
 * This includes a parameter type and parameter name.
 *
 * <p>
 *  参数信息。这包括参数类型和参数名称。
 * 
 * 
 * @author Robert Field
 */
public interface Parameter {

    /**
     * Get the type of this parameter.
     * <p>
     *  获取此参数的类型。
     * 
     */
    Type type();

    /**
     * Get local name of this parameter.
     * For example if parameter is the short 'index', returns "index".
     * <p>
     *  获取此参数的本地名称。例如,如果参数是短的'index',返回"index"。
     * 
     */
    String name();

    /**
     * Get type name of this parameter.
     * For example if parameter is the short 'index', returns "short".
     * <p>
     * This method returns a complete string
     * representation of the type, including the dimensions of arrays and
     * the type arguments of parameterized types.  Names are qualified.
     * <p>
     *  获取此参数的类型名称。例如,如果参数是短的"索引",则返回"short"。
     * <p>
     *  此方法返回类型的完整字符串表示形式,包括数组的维数和参数化类型的类型参数。名称是合格的。
     * 
     */
    String typeName();

    /**
     * Returns a string representation of the parameter.
     * <p>
     * For example if parameter is the short 'index', returns "short index".
     *
     * <p>
     *  返回参数的字符串表示形式。
     * <p>
     *  例如,如果参数是短的'索引',返回"短索引"。
     * 
     * 
     * @return type and parameter name of this parameter.
     */
    String toString();

    /**
     * Get the annotations of this parameter.
     * Return an empty array if there are none.
     *
     * <p>
     * 
     * @return the annotations of this parameter.
     * @since 1.5
     */
    AnnotationDesc[] annotations();
}
