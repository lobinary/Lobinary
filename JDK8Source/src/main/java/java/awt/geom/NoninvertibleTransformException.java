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

package java.awt.geom;

/**
 * The <code>NoninvertibleTransformException</code> class represents
 * an exception that is thrown if an operation is performed requiring
 * the inverse of an {@link AffineTransform} object but the
 * <code>AffineTransform</code> is in a non-invertible state.
 * <p>
 *  <code> NoninvertibleTransformException </code>类表示如果执行要求{@link AffineTransform}对象的逆,而<code> AffineTra
 * nsform </code>处于不可逆状态的操作时抛出的异常。
 * 
 */

public class NoninvertibleTransformException extends java.lang.Exception {
    /**
     * Constructs an instance of
     * <code>NoninvertibleTransformException</code>
     * with the specified detail message.
     * <p>
     * 
     * @param   s     the detail message
     * @since   1.2
     */
    public NoninvertibleTransformException(String s) {
        super (s);
    }
}
