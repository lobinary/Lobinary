/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind;

import java.io.PrintWriter;

/**
 * This is the root exception class for all JAXB exceptions.
 *
 * <p>
 *  这是所有JAXB异常的根异常类。
 * 
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li></ul>
 * @see JAXBContext
 * @see Marshaller
 * @see Unmarshaller
 * @since JAXB1.0
 */
public class JAXBException extends Exception {

    /**
     * Vendor specific error code
     *
     * <p>
     *  供应商特定的错误代码
     * 
     */
    private String errorCode;

    /**
     * Exception reference
     *
     * <p>
     *  异常引用
     * 
     */
    private volatile Throwable linkedException;

    static final long serialVersionUID = -5621384651494307979L;

    /**
     * Construct a JAXBException with the specified detail message.  The
     * errorCode and linkedException will default to null.
     *
     * <p>
     *  使用指定的详细消息构造JAXBException。 errorCode和linkedException将默认为null。
     * 
     * 
     * @param message a description of the exception
     */
    public JAXBException(String message) {
        this( message, null, null );
    }

    /**
     * Construct a JAXBException with the specified detail message and vendor
     * specific errorCode.  The linkedException will default to null.
     *
     * <p>
     *  构造具有指定的详细消息和供应商特定的errorCode的JAXBException。 linkedException将默认为null。
     * 
     * 
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     */
    public JAXBException(String message, String errorCode) {
        this( message, errorCode, null );
    }

    /**
     * Construct a JAXBException with a linkedException.  The detail message and
     * vendor specific errorCode will default to null.
     *
     * <p>
     *  使用linkedException构造一个JAXBException。详细消息和供应商特定的errorCode将默认为null。
     * 
     * 
     * @param exception the linked exception
     */
    public JAXBException(Throwable exception) {
        this( null, null, exception );
    }

    /**
     * Construct a JAXBException with the specified detail message and
     * linkedException.  The errorCode will default to null.
     *
     * <p>
     *  构造具有指定的详细消息和linkedException的JAXBException。 errorCode将默认为null。
     * 
     * 
     * @param message a description of the exception
     * @param exception the linked exception
     */
    public JAXBException(String message, Throwable exception) {
        this( message, null, exception );
    }

    /**
     * Construct a JAXBException with the specified detail message, vendor
     * specific errorCode, and linkedException.
     *
     * <p>
     *  构造具有指定的详细消息,供应商特定的errorCode和linkedException的JAXBException。
     * 
     * 
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     * @param exception the linked exception
     */
    public JAXBException(String message, String errorCode, Throwable exception) {
        super( message );
        this.errorCode = errorCode;
        this.linkedException = exception;
    }

    /**
     * Get the vendor specific error code
     *
     * <p>
     *  获取供应商特定的错误代码
     * 
     * 
     * @return a string specifying the vendor specific error code
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * Get the linked exception
     *
     * <p>
     *  获取链接的异常
     * 
     * 
     * @return the linked Exception, null if none exists
     */
    public Throwable getLinkedException() {
        return linkedException;
    }

    /**
     * Add a linked Exception.
     *
     * <p>
     *  添加链接异常。
     * 
     * 
     * @param exception the linked Exception (A null value is permitted and
     *                  indicates that the linked exception does not exist or
     *                  is unknown).
     */
    public void setLinkedException( Throwable exception ) {
        this.linkedException = exception;
    }

    /**
     * Returns a short description of this JAXBException.
     *
     * <p>
     *  返回此JAXBException的简短描述。
     * 
     */
    public String toString() {
        return linkedException == null ?
            super.toString() :
            super.toString() + "\n - with linked exception:\n[" +
                                linkedException.toString()+ "]";
    }

    /**
     * Prints this JAXBException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to the PrintStream.
     *
     * <p>
     *  打印此JAXBException及其堆栈跟踪(包括linkedException的堆栈跟踪,如果它非空)到PrintStream。
     * 
     * 
     * @param s PrintStream to use for output
     */
    public void printStackTrace( java.io.PrintStream s ) {
        super.printStackTrace(s);
    }

    /**
     * Prints this JAXBException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to <tt>System.err</tt>.
     *
     * <p>
     *  打印此JAXBException及其堆栈跟踪(包括linkedException的堆栈跟踪,如果它是非空的)到<tt> System.err </tt>。
     * 
     */
    public void printStackTrace() {
        super.printStackTrace();
    }

    /**
     * Prints this JAXBException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to the PrintWriter.
     *
     * <p>
     *  打印此JAXBException及其堆栈跟踪(包括linkedException的堆栈跟踪,如果非空)到PrintWriter。
     * 
     * @param s PrintWriter to use for output
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public Throwable getCause() {
        return linkedException;
    }
}
