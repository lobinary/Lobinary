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
 * Unchecked exception thrown when an unknown flag is given.
 *
 * <p> Unless otherwise specified, passing a <tt>null</tt> argument to any
 * method or constructor in this class will cause a {@link
 * NullPointerException} to be thrown.
 *
 * <p>
 *  当给出未知标志时抛出未经检查的异常。
 * 
 *  <p>除非另有说明,否则将<tt> null </tt>参数传递给此类中的任何方法或构造函数都会导致抛出{@link NullPointerException}。
 * 
 * 
 * @since 1.5
 */
public class UnknownFormatFlagsException extends IllegalFormatException {

    private static final long serialVersionUID = 19370506L;

    private String flags;

    /**
     * Constructs an instance of this class with the specified flags.
     *
     * <p>
     *  构造具有指定标志的此类的实例。
     * 
     * 
     * @param  f
     *         The set of format flags which contain an unknown flag
     */
    public UnknownFormatFlagsException(String f) {
        if (f == null)
            throw new NullPointerException();
        this.flags = f;
    }

    /**
     * Returns the set of flags which contains an unknown flag.
     *
     * <p>
     *  返回包含未知标志的标志集。
     * 
     * @return  The flags
     */
    public String getFlags() {
        return flags;
    }

    // javadoc inherited from Throwable.java
    public String getMessage() {
        return "Flags = " + flags;
    }
}
