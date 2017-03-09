/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
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
 * Unchecked exception thrown when the argument corresponding to the format
 * specifier is of an incompatible type.
 *
 * <p> Unless otherwise specified, passing a <tt>null</tt> argument to any
 * method or constructor in this class will cause a {@link
 * NullPointerException} to be thrown.
 *
 * <p>
 *  当与格式说明符对应的参数是不兼容类型时抛出未检查的异常。
 * 
 *  <p>除非另有说明,否则将<tt> null </tt>参数传递给此类中的任何方法或构造函数都会导致抛出{@link NullPointerException}。
 * 
 * 
 * @since 1.5
 */
public class IllegalFormatConversionException extends IllegalFormatException {

    private static final long serialVersionUID = 17000126L;

    private char c;
    private Class<?> arg;

    /**
     * Constructs an instance of this class with the mismatched conversion and
     * the corresponding argument class.
     *
     * <p>
     *  使用不匹配的转换和相应的参数类构造此类的实例。
     * 
     * 
     * @param  c
     *         Inapplicable conversion
     *
     * @param  arg
     *         Class of the mismatched argument
     */
    public IllegalFormatConversionException(char c, Class<?> arg) {
        if (arg == null)
            throw new NullPointerException();
        this.c = c;
        this.arg = arg;
    }

    /**
     * Returns the inapplicable conversion.
     *
     * <p>
     *  返回不适用的转化。
     * 
     * 
     * @return  The inapplicable conversion
     */
    public char getConversion() {
        return c;
    }

    /**
     * Returns the class of the mismatched argument.
     *
     * <p>
     *  返回不匹配的参数的类。
     * 
     * @return   The class of the mismatched argument
     */
    public Class<?> getArgumentClass() {
        return arg;
    }

    // javadoc inherited from Throwable.java
    public String getMessage() {
        return String.format("%c != %s", c, arg.getName());
    }
}
