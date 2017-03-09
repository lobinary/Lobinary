/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * Unchecked exception thrown when the precision is a negative value other than
 * <tt>-1</tt>, the conversion does not support a precision, or the value is
 * otherwise unsupported.
 *
 * <p>
 *  当精度为除<tt> -1 </tt>之外的负值时抛出未检查的异常,转换不支持精度,否则该值不受支持。
 * 
 * 
 * @since 1.5
 */
public class IllegalFormatPrecisionException extends IllegalFormatException {

    private static final long serialVersionUID = 18711008L;

    private int p;

    /**
     * Constructs an instance of this class with the specified precision.
     *
     * <p>
     *  构造具有指定精度的此类的实例。
     * 
     * 
     * @param  p
     *         The precision
     */
    public IllegalFormatPrecisionException(int p) {
        this.p = p;
    }

    /**
     * Returns the precision
     *
     * <p>
     *  返回精度
     * 
     * @return  The precision
     */
    public int getPrecision() {
        return p;
    }

    public String getMessage() {
        return Integer.toString(p);
    }
}
