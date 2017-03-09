/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

/**
 * Class PrintException encapsulates a printing-related error condition that
 * occurred while using a Print Service instance. This base class
 * furnishes only a string description of the error. Subclasses furnish more
 * detailed information if applicable.
 *
 * <p>
 *  类PrintException封装了在使用打印服务实例时发生的与打印相关的错误条件。这个基类只提供错误的字符串描述。子类提供更详细的信息(如果适用)。
 * 
 */
public class PrintException extends Exception {


    /**
     * Construct a print exception with no detail message.
     * <p>
     *  构造没有详细消息的打印异常。
     * 
     */
    public PrintException() {
        super();
    }

    /**
     * Construct a print exception with the given detail message.
     *
     * <p>
     *  使用给定的详细消息构造打印异常。
     * 
     * 
     * @param  s  Detail message, or null if no detail message.
     */
    public PrintException (String s) {
        super (s);
    }

    /**
     * Construct a print exception chaining the supplied exception.
     *
     * <p>
     *  构造打印异常链接提供的异常。
     * 
     * 
     * @param  e  Chained exception.
     */
    public PrintException (Exception e) {
        super ( e);
    }

    /**
     * Construct a print exception with the given detail message
     * and chained exception.
     * <p>
     *  使用给定的详细消息和链接异常构造打印异常。
     * 
     * @param  s  Detail message, or null if no detail message.
     * @param  e  Chained exception.
     */
    public PrintException (String s, Exception e) {
        super (s, e);
    }

}
