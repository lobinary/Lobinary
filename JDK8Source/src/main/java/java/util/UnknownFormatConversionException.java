/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * Unchecked exception thrown when an unknown conversion is given.
 *
 * <p> Unless otherwise specified, passing a <tt>null</tt> argument to
 * any method or constructor in this class will cause a {@link
 * NullPointerException} to be thrown.
 *
 * <p>
 *  在给出未知转换时抛出未经检查的异常。
 * 
 *  <p>除非另有说明,否则将<tt> null </tt>参数传递给此类中的任何方法或构造函数都会导致抛出{@link NullPointerException}。
 * 
 * 
 * @since 1.5
 */
public class UnknownFormatConversionException extends IllegalFormatException {

    private static final long serialVersionUID = 19060418L;

    private String s;

    /**
     * Constructs an instance of this class with the unknown conversion.
     *
     * <p>
     *  构造具有未知转换的此类的实例。
     * 
     * 
     * @param  s
     *         Unknown conversion
     */
    public UnknownFormatConversionException(String s) {
        if (s == null)
            throw new NullPointerException();
        this.s = s;
    }

    /**
     * Returns the unknown conversion.
     *
     * <p>
     *  返回未知转换。
     * 
     * @return  The unknown conversion.
     */
    public String getConversion() {
        return s;
    }

    // javadoc inherited from Throwable.java
    public String getMessage() {
        return String.format("Conversion = '%s'", s);
    }
}
