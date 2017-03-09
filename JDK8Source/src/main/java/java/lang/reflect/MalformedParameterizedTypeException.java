/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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


package java.lang.reflect;

/**
 * Thrown when a semantically malformed parameterized type is
 * encountered by a reflective method that needs to instantiate it.
 * For example, if the number of type arguments to a parameterized type
 * is wrong.
 *
 * <p>
 *  当需要实例化它的反射方法遇到语义上畸形的参数化类型时抛出。例如,如果参数化类型的类型参数的数量错误。
 * 
 * @since 1.5
 */
public class MalformedParameterizedTypeException extends RuntimeException {
    private static final long serialVersionUID = -5696557788586220964L;
}
