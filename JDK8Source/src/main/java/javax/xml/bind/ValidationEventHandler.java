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
 * A basic event handler interface for validation errors.
 *
 * <p>
 * If an application needs to implement customized event handling, it must
 * implement this interface and then register it with either the
 * {@link Unmarshaller#setEventHandler(ValidationEventHandler) Unmarshaller},
 * the {@link Validator#setEventHandler(ValidationEventHandler) Validator}, or
 * the {@link Marshaller#setEventHandler(ValidationEventHandler) Marshaller}.
 * The JAXB Provider will then report validation errors and warnings encountered
 * during the unmarshal, marshal, and validate operations to these event
 * handlers.
 *
 * <p>
 * If the <tt>handleEvent</tt> method throws an unchecked runtime exception,
 * the JAXB Provider must treat that as if the method returned false, effectively
 * terminating whatever operation was in progress at the time (unmarshal,
 * validate, or marshal).
 *
 * <p>
 * Modifying the Java content tree within your event handler is undefined
 * by the specification and may result in unexpected behaviour.
 *
 * <p>
 * Failing to return false from the <tt>handleEvent</tt> method after
 * encountering a fatal error is undefined by the specification and may result
 * in unexpected behavior.
 *
 * <p>
 * <b>Default Event Handler</b>
 * <blockquote>
 *    See: <a href="Validator.html#defaulthandler">Validator javadocs</a>
 * </blockquote>
 *
 * <p>
 *  用于验证错误的基本事件处理程序接口。
 * 
 * <p>
 *  如果应用程序需要实现定制事件处理,则它必须实现此接口,然后使用{@link Unmarshaller#setEventHandler(ValidationEventHandler)Unmarshaller}
 * ,{@link Validator#setEventHandler(ValidationEventHandler)Validator}或{ @link Marshaller#setEventHandler(ValidationEventHandler)Marshaller}
 * 。
 * 然后,JAXB提供程序将在解组,元组和验证操作期间遇到的验证错误和警告报告给这些事件处理程序。
 * 
 * <p>
 *  如果<tt> handleEvent </tt>方法抛出未经检查的运行时异常,则JAXB提供程序必须对待该方法,如同该方法返回false,有效终止当时正在进行的任何操作(取消组合,验证或marshal
 * )。
 * 
 * <p>
 *  修改事件处理程序中的Java内容树未被规范定义,可能会导致意外的行为。
 * 
 * <p>
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @see Unmarshaller
 * @see Validator
 * @see Marshaller
 * @see ValidationEvent
 * @see javax.xml.bind.util.ValidationEventCollector
 * @since JAXB1.0
 */
public interface ValidationEventHandler {
    /**
     * Receive notification of a validation warning or error.
     *
     * The ValidationEvent will have a
     * {@link ValidationEventLocator ValidationEventLocator} embedded in it that
     * indicates where the error or warning occurred.
     *
     * <p>
     * If an unchecked runtime exception is thrown from this method, the JAXB
     * provider will treat it as if the method returned false and interrupt
     * the current unmarshal, validate, or marshal operation.
     *
     * <p>
     *  未能在遇到致命错误后从<tt> handleEvent </tt>方法返回false,因此规范未定义,可能会导致意外的行为。
     * 
     * <p>
     *  <b>默认事件处理程序</b>
     * <blockquote>
     *  请参阅：<a href="Validator.html#defaulthandler"> Validator Javadocs </a>
     * </blockquote>
     * 
     * 
     * @param event the encapsulated validation event information.  It is a
     * provider error if this parameter is null.
     * @return true if the JAXB Provider should attempt to continue the current
     *         unmarshal, validate, or marshal operation after handling this
     *         warning/error, false if the provider should terminate the current
     *         operation with the appropriate <tt>UnmarshalException</tt>,
     *         <tt>ValidationException</tt>, or <tt>MarshalException</tt>.
     * @throws IllegalArgumentException if the event object is null.
     */
    public boolean handleEvent( ValidationEvent event );

}
