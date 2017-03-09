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

/**
 * This exception indicates that an error has occurred while performing
 * a validate operation.
 *
 * <p>
 * The <tt>ValidationEventHandler</tt> can cause this exception to be thrown
 * during the validate operations.  See
 * {@link ValidationEventHandler#handleEvent(ValidationEvent)
 * ValidationEventHandler.handleEvent(ValidationEvent)}.
 *
 * <p>
 *  此异常指示执行验证操作时发生错误。
 * 
 * <p>
 *  <tt> ValidationEventHandler </tt>可能会导致在验证操作期间抛出此异常。
 * 请参见{@link ValidationEventHandler#handleEvent(ValidationEvent)ValidationEventHandler.handleEvent(ValidationEvent)}
 * 。
 *  <tt> ValidationEventHandler </tt>可能会导致在验证操作期间抛出此异常。
 * 
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li></ul>
 * @see JAXBException
 * @see Validator
 * @since JAXB1.0
 */
public class ValidationException extends JAXBException {

    /**
     * Construct an ValidationException with the specified detail message.  The
     * errorCode and linkedException will default to null.
     *
     * <p>
     *  使用指定的详细消息构造ValidationException。 errorCode和linkedException将默认为null。
     * 
     * 
     * @param message a description of the exception
     */
    public ValidationException(String message) {
        this( message, null, null );
    }

    /**
     * Construct an ValidationException with the specified detail message and vendor
     * specific errorCode.  The linkedException will default to null.
     *
     * <p>
     *  构造具有指定的详细消息和供应商特定的errorCode的ValidationException。 linkedException将默认为null。
     * 
     * 
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     */
    public ValidationException(String message, String errorCode) {
        this( message, errorCode, null );
    }

    /**
     * Construct an ValidationException with a linkedException.  The detail message and
     * vendor specific errorCode will default to null.
     *
     * <p>
     *  用一个linkedException构造一个ValidationException。详细消息和供应商特定的errorCode将默认为null。
     * 
     * 
     * @param exception the linked exception
     */
    public ValidationException(Throwable exception) {
        this( null, null, exception );
    }

    /**
     * Construct an ValidationException with the specified detail message and
     * linkedException.  The errorCode will default to null.
     *
     * <p>
     *  构造具有指定的详细消息和linkedException的ValidationException。 errorCode将默认为null。
     * 
     * 
     * @param message a description of the exception
     * @param exception the linked exception
     */
    public ValidationException(String message, Throwable exception) {
        this( message, null, exception );
    }

    /**
     * Construct an ValidationException with the specified detail message, vendor
     * specific errorCode, and linkedException.
     *
     * <p>
     *  构造具有指定的详细消息,供应商特定的errorCode和linkedException的ValidationException。
     * 
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     * @param exception the linked exception
     */
    public ValidationException(String message, String errorCode, Throwable exception) {
        super( message, errorCode, exception );
    }

}
