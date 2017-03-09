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

package javax.xml.bind.helpers;

import java.text.MessageFormat;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;

/**
 * Default implementation of the ValidationEvent interface.
 *
 * <p>
 * JAXB providers are allowed to use whatever class that implements
 * the ValidationEvent interface. This class is just provided for a
 * convenience.
 *
 * <p>
 *  ValidationEvent接口的默认实现。
 * 
 * <p>
 *  允许JAXB提供程序使用任何实现ValidationEvent接口的类。这个类只是为了方便。
 * 
 * 
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li></ul>
 * @see javax.xml.bind.Validator
 * @see javax.xml.bind.ValidationEventHandler
 * @see javax.xml.bind.ValidationEvent
 * @see javax.xml.bind.ValidationEventLocator
 * @since JAXB1.0
 */
public class ValidationEventImpl implements ValidationEvent
{

    /**
     * Create a new ValidationEventImpl.
     *
     * <p>
     *  创建一个新的ValidationEventImpl。
     * 
     * 
     * @param _severity The severity value for this event.  Must be one of
     * ValidationEvent.WARNING, ValidationEvent.ERROR, or
     * ValidationEvent.FATAL_ERROR
     * @param _message The text message for this event - may be null.
     * @param _locator The locator object for this event - may be null.
     * @throws IllegalArgumentException if an illegal severity field is supplied
     */
    public ValidationEventImpl( int _severity, String _message,
                                 ValidationEventLocator _locator ) {

        this(_severity,_message,_locator,null);
    }

    /**
     * Create a new ValidationEventImpl.
     *
     * <p>
     *  创建一个新的ValidationEventImpl。
     * 
     * 
     * @param _severity The severity value for this event.  Must be one of
     * ValidationEvent.WARNING, ValidationEvent.ERROR, or
     * ValidationEvent.FATAL_ERROR
     * @param _message The text message for this event - may be null.
     * @param _locator The locator object for this event - may be null.
     * @param _linkedException An optional linked exception that may provide
     * additional information about the event - may be null.
     * @throws IllegalArgumentException if an illegal severity field is supplied
     */
    public ValidationEventImpl( int _severity, String _message,
                                 ValidationEventLocator _locator,
                                 Throwable _linkedException ) {

        setSeverity( _severity );
        this.message = _message;
        this.locator = _locator;
        this.linkedException = _linkedException;
    }

    private int severity;
    private String message;
    private Throwable linkedException;
    private ValidationEventLocator locator;

    public int getSeverity() {
        return severity;
    }


    /**
     * Set the severity field of this event.
     *
     * <p>
     *  设置此事件的严重性字段。
     * 
     * 
     * @param _severity Must be one of ValidationEvent.WARNING,
     * ValidationEvent.ERROR, or ValidationEvent.FATAL_ERROR.
     * @throws IllegalArgumentException if an illegal severity field is supplied
     */
    public void setSeverity( int _severity ) {

        if( _severity != ValidationEvent.WARNING &&
            _severity != ValidationEvent.ERROR &&
            _severity != ValidationEvent.FATAL_ERROR ) {
                throw new IllegalArgumentException(
                    Messages.format( Messages.ILLEGAL_SEVERITY ) );
        }

        this.severity = _severity;
    }

    public String getMessage() {
        return message;
    }
    /**
     * Set the message field of this event.
     *
     * <p>
     *  设置此事件的消息字段。
     * 
     * 
     * @param _message String message - may be null.
     */
    public void setMessage( String _message ) {
        this.message = _message;
    }

    public Throwable getLinkedException() {
        return linkedException;
    }
    /**
     * Set the linked exception field of this event.
     *
     * <p>
     *  设置此事件的链接异常字段。
     * 
     * 
     * @param _linkedException Optional linked exception - may be null.
     */
    public void setLinkedException( Throwable _linkedException ) {
        this.linkedException = _linkedException;
    }

    public ValidationEventLocator getLocator() {
        return locator;
    }
    /**
     * Set the locator object for this event.
     *
     * <p>
     *  设置此事件的定位器对象。
     * 
     * 
     * @param _locator The locator - may be null.
     */
    public void setLocator( ValidationEventLocator _locator ) {
        this.locator = _locator;
    }

    /**
     * Returns a string representation of this object in a format
     * helpful to debugging.
     *
     * <p>
     *  以有助于调试的格式返回此对象的字符串表示形式。
     * 
     * @see Object#equals(Object)
     */
    public String toString() {
        String s;
        switch(getSeverity()) {
        case WARNING:   s="WARNING";break;
        case ERROR: s="ERROR";break;
        case FATAL_ERROR: s="FATAL_ERROR";break;
        default: s=String.valueOf(getSeverity());break;
        }
        return MessageFormat.format("[severity={0},message={1},locator={2}]",
            new Object[]{
                s,
                getMessage(),
                getLocator()
            });
    }
}
