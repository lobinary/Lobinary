/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

/**
 * Signals that a data format error has occurred.
 *
 * <p>
 *  表示发生了数据格式错误。
 * 
 * 
 * @author      David Connelly
 */
public
class DataFormatException extends Exception {
    private static final long serialVersionUID = 2219632870893641452L;

    /**
     * Constructs a DataFormatException with no detail message.
     * <p>
     *  构造一个没有详细消息的DataFormatException。
     * 
     */
    public DataFormatException() {
        super();
    }

    /**
     * Constructs a DataFormatException with the specified detail message.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息的DataFormatException。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     */
    public DataFormatException(String s) {
        super(s);
    }
}
