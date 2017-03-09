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
 * As of JAXB 2.0, this class is deprecated and optional.
 * <p>
 * The <tt>Validator</tt> class is responsible for controlling the validation
 * of content trees during runtime.
 *
 * <p>
 * <a name="validationtypes"></a>
 * <b>Three Forms of Validation</b><br>
 * <blockquote>
 *    <dl>
 *        <dt><b>Unmarshal-Time Validation</b></dt>
 *        <dd>This form of validation enables a client application to receive
 *            information about validation errors and warnings detected while
 *            unmarshalling XML data into a Java content tree and is completely
 *            orthogonal to the other types of validation.  To enable or disable
 *            it, see the javadoc for
 *            {@link Unmarshaller#setValidating(boolean) Unmarshaller.setValidating}.
 *            All JAXB 1.0 Providers are required to support this operation.
 *        </dd>
 *
 *        <dt><b>On-Demand Validation</b></dt>
 *        <dd> This form of validation enables a client application to receive
 *             information about validation errors and warnings detected in the
 *             Java content tree.  At any point, client applications can call
 *             the {@link Validator#validate(Object) Validator.validate} method
 *             on the Java content tree (or any sub-tree of it).  All JAXB 1.0
 *             Providers are required to support this operation.
 *        </dd>
 *
 *        <dt><b>Fail-Fast Validation</b></dt>
 *        <dd> This form of validation enables a client application to receive
 *             immediate feedback about modifications to the Java content tree
 *             that violate type constraints on Java Properties as defined in
 *             the specification.  JAXB Providers are not required support
 *             this type of validation.  Of the JAXB Providers that do support
 *             this type of validation, some may require you to decide at schema
 *             compile time whether or not a client application will be allowed
 *             to request fail-fast validation at runtime.
 *        </dd>
 *    </dl>
 * </blockquote>
 *
 * <p>
 * The <tt>Validator</tt> class is responsible for managing On-Demand Validation.
 * The <tt>Unmarshaller</tt> class is responsible for managing Unmarshal-Time
 * Validation during the unmarshal operations.  Although there is no formal
 * method of enabling validation during the marshal operations, the
 * <tt>Marshaller</tt> may detect errors, which will be reported to the
 * <tt>ValidationEventHandler</tt> registered on it.
 *
 * <p>
 * <a name="defaulthandler"></a>
 * <b>Using the Default EventHandler</b><br>
 * <blockquote>
 *   If the client application does not set an event handler on their
 *   <tt>Validator</tt>, <tt>Unmarshaller</tt>, or <tt>Marshaller</tt> prior to
 *   calling the validate, unmarshal, or marshal methods, then a default event
 *   handler will receive notification of any errors or warnings encountered.
 *   The default event handler will cause the current operation to halt after
 *   encountering the first error or fatal error (but will attempt to continue
 *   after receiving warnings).
 * </blockquote>
 *
 * <p>
 * <a name="handlingevents"></a>
 * <b>Handling Validation Events</b><br>
 * <blockquote>
 *   There are three ways to handle events encountered during the unmarshal,
 *   validate, and marshal operations:
 *    <dl>
 *        <dt>Use the default event handler</dt>
 *        <dd>The default event handler will be used if you do not specify one
 *            via the <tt>setEventHandler</tt> API's on <tt>Validator</tt>,
 *            <tt>Unmarshaller</tt>, or <tt>Marshaller</tt>.
 *        </dd>
 *
 *        <dt>Implement and register a custom event handler</dt>
 *        <dd>Client applications that require sophisticated event processing
 *            can implement the <tt>ValidationEventHandler</tt> interface and
 *            register it with the <tt>Unmarshaller</tt> and/or
 *            <tt>Validator</tt>.
 *        </dd>
 *
 *        <dt>Use the {@link javax.xml.bind.util.ValidationEventCollector ValidationEventCollector}
 *            utility</dt>
 *        <dd>For convenience, a specialized event handler is provided that
 *            simply collects any <tt>ValidationEvent</tt> objects created
 *            during the unmarshal, validate, and marshal operations and
 *            returns them to the client application as a
 *            <tt>java.util.Collection</tt>.
 *        </dd>
 *    </dl>
 * </blockquote>
 *
 * <p>
 * <b>Validation and Well-Formedness</b><br>
 * <blockquote>
 * <p>
 * Validation events are handled differently depending on how the client
 * application is configured to process them as described in the previous
 * section.  However, there are certain cases where a JAXB Provider indicates
 * that it is no longer able to reliably detect and report errors.  In these
 * cases, the JAXB Provider will set the severity of the ValidationEvent to
 * FATAL_ERROR to indicate that the unmarshal, validate, or marshal operations
 * should be terminated.  The default event handler and
 * <tt>ValidationEventCollector</tt> utility class must terminate processing
 * after being notified of a fatal error.  Client applications that supply their
 * own <tt>ValidationEventHandler</tt> should also terminate processing after
 * being notified of a fatal error.  If not, unexpected behaviour may occur.
 * </blockquote>
 *
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * There currently are not any properties required to be supported by all
 * JAXB Providers on Validator.  However, some providers may support
 * their own set of provider specific properties.
 * </blockquote>
 *
 *
 * <p>
 *  从JAXB 2.0开始,此类已被弃用并且是可选的。
 * <p>
 *  <tt> Validator </tt>类负责在运行时控制内容树的验证。
 * 
 * <p>
 *  <a name="validationtypes"> </a> <b>三种验证形式</b> <br>
 * <blockquote>
 * <dl>
 *  <dt> <b>解除时间验证</b> </dt> <dd>此形式的验证使客户端应用程序能够接收有关在将XML数据解组到Java内容树中时检测到的验证错误和警告的信息,与其他类型的验证正交。
 * 要启用或禁用它,请参阅{@link Unmarshaller#setValidating(boolean)Unmarshaller.setValidating}的javadoc。
 * 所有JAXB 1.0提供程序都需要支持此操作。
 * </dd>
 * 
 *  <dt> <b>按需验证</b> </dt> <dd>此形式的验证使客户端应用程序能够接收有关在Java内容树中检测到的验证错误和警告的信息。
 * 在任何时候,客户端应用程序可以调用Java内容树(或其任何子树)上的{@link Validator#validate(Object)Validator.validate}方法。
 * 所有JAXB 1.0提供程序都需要支持此操作。
 * </dd>
 * 
 * <dt> <b>失败快速验证</b> </dt> <dd>此形式的验证使客户端应用程序能够立即收到有关修改Java内容树的反馈,违反了Java属性的类型约束规格。
 *  JAXB提供程序不需要支持这种类型的验证。在支持此类型验证的JAXB提供程序中,有些可能需要您在模式编译时决定是否允许客户端应用程序在运行时请求故障快速验证。
 * </dd>
 * </dl>
 * </blockquote>
 * 
 * <p>
 *  <tt>验证程序</tt>类负责管理按需验证。 <tt> Unmarshaller </tt>类负责在解组操作期间管理"解字时间验证"。
 * 虽然没有在元组操作期间启用验证的正式方法,但<tt> Marshaller </tt>可能会检测到错误,这些错误将报告给注册它的<tt> ValidationEventHandler </tt>。
 * 
 * <p>
 *  <a name="defaulthandler"> </a> <b>使用默认EventHandler </b> <br>
 * <blockquote>
 *  如果客户端应用程序在调用validate,unmarshal或marshal方法之前未在其<tt> Validator </tt>,<tt> Unmarshaller </tt>或<tt> Marsh
 * aller </tt> ,则默认事件处理程序将接收到遇到的任何错误或警告的通知。
 * 默认事件处理程序将导致当前操作在遇到第一个错误或致命错误后停止(但将在接收到警告后尝试继续)。
 * </blockquote>
 * 
 * <p>
 * <a name="handlingevents"> </a> <b>处理验证事件</b> <br>
 * <blockquote>
 *  有三种方法来处理在解组,验证和组合操作期间遇到的事件：
 * <dl>
 *  <dt>使用默认事件处理程序</dt> <dd>如果您未通过<tt> Validator </tt>,<tt>的<tt> setEventHandler </tt> API指定< tt> Unmars
 * haller </tt>或<tt> Marshaller </tt>。
 * </dd>
 * 
 *  <dt>实现和注册自定义事件处理程序</dt> <dd>需要复杂事件处理的客户端应用程序可以实现<tt> ValidationEventHandler </tt>接口,并将其注册到<tt> Unmar
 * shaller </tt> /或<tt>验证程序</tt>。
 * </dd>
 * 
 *  <dt>使用{@link javax.xml.bind.util.ValidationEventCollector ValidationEventCollector}实用程序</dt> <dd>为方便
 * 起见,我们提供了一个专门的事件处理程序,它只是收集所创建的任何<tt> ValidationEvent </tt>对象在解组,验证和组合操作期间,并将它们作为<tt> java.util.Collect
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @see JAXBContext
 * @see Unmarshaller
 * @see ValidationEventHandler
 * @see ValidationEvent
 * @see javax.xml.bind.util.ValidationEventCollector
 * @since JAXB1.0
 * @deprecated since JAXB 2.0
 */
public interface Validator {

    /**
     * Allow an application to register a validation event handler.
     * <p>
     * The validation event handler will be called by the JAXB Provider if any
     * validation errors are encountered during calls to
     * {@link #validate(Object) validate}.  If the client application does not
     * register a validation event handler before invoking the validate method,
     * then validation events will be handled by the default event handler which
     * will terminate the validate operation after the first error or fatal error
     * is encountered.
     * <p>
     * Calling this method with a null parameter will cause the Validator
     * to revert back to the default default event handler.
     *
     * <p>
     * ion </tt>返回到客户端应用程序。
     * </dd>
     * </dl>
     * </blockquote>
     * 
     * <p>
     *  <b>验证和成功</b> <br>
     * <blockquote>
     * <p>
     * 验证事件的处理方式有所不同,具体取决于客户端应用程序如何配置为如上一节所述处理它们。但是,在某些情况下,JAXB提供程序指示它不再能够可靠地检测和报告错误。
     * 在这些情况下,JAXB提供程序将将ValidationEvent的严重性设置为FATAL_ERROR,以指示应该终止解组,验证或marshal操作。
     * 默认事件处理程序和<tt> ValidationEventCollector </tt>实用程序类必须在通知致命错误后终止处理。
     * 提供自己的<tt> ValidationEventHandler </tt>的客户端应用程序也应在通知致命错误后终止处理。如果不是,可能会发生意外的行为。
     * </blockquote>
     * 
     * <p>
     *  <a name="supportedProps"> </a> <b>支持的属性</b> <br>
     * <blockquote>
     * <p>
     *  目前验证器上的所有JAXB提供程序都不需要支持任何属性。然而,一些提供者可以支持他们自己的提供者特定属性集合。
     * </blockquote>
     * 
     * 
     * @param handler the validation event handler
     * @throws JAXBException if an error was encountered while setting the
     *         event handler
     * @deprecated since JAXB2.0
     */
    public void setEventHandler( ValidationEventHandler handler )
        throws JAXBException;

    /**
     * Return the current event handler or the default event handler if one
     * hasn't been set.
     *
     * <p>
     *  允许应用程序注册验证事件处理程序。
     * <p>
     * 如果在调用{@link #validate(Object)validate}期间遇到任何验证错误,JAXB Provider将调用验证事件处理程序。
     * 如果客户端应用程序在调用validate方法之前未注册验证事件处理程序,则验证事件将由默认事件处理程序处理,在遇到第一个错误或致命错误后,将终止验证操作。
     * <p>
     *  使用null参数调用此方法将导致Validator恢复为默认的默认事件处理程序。
     * 
     * 
     * @return the current ValidationEventHandler or the default event handler
     *         if it hasn't been set
     * @throws JAXBException if an error was encountered while getting the
     *         current event handler
     * @deprecated since JAXB2.0
     */
    public ValidationEventHandler getEventHandler()
        throws JAXBException;

    /**
     * Validate the Java content tree starting at <tt>subrootObj</tt>.
     * <p>
     * Client applications can use this method to validate Java content trees
     * on-demand at runtime.  This method can be used to validate any arbitrary
     * subtree of the Java content tree.  Global constraint checking <b>will not
     * </b> be performed as part of this operation (i.e. ID/IDREF constraints).
     *
     * <p>
     *  返回当前事件处理程序或默认事件处理程序(如果尚未设置)。
     * 
     * 
     * @param subrootObj the obj to begin validation at
     * @throws JAXBException if any unexpected problem occurs during validation
     * @throws ValidationException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Validator</tt> is unable to validate the content tree rooted
     *     at <tt>subrootObj</tt>
     * @throws IllegalArgumentException
     *      If the subrootObj parameter is null
     * @return true if the subtree rooted at <tt>subrootObj</tt> is valid, false
     *         otherwise
     * @deprecated since JAXB2.0
     */
    public boolean validate( Object subrootObj ) throws JAXBException;

    /**
     * Validate the Java content tree rooted at <tt>rootObj</tt>.
     * <p>
     * Client applications can use this method to validate Java content trees
     * on-demand at runtime.  This method is used to validate an entire Java
     * content tree.  Global constraint checking <b>will</b> be performed as
     * part of this operation (i.e. ID/IDREF constraints).
     *
     * <p>
     *  验证从<tt> subrootObj </tt>开始的Java内容树。
     * <p>
     *  客户端应用程序可以使用此方法在运行时按需验证Java内容树。此方法可用于验证Java内容树的任意子树。全局约束检查<b>不会</b>作为此操作的一部分(即ID / IDREF约束)执行。
     * 
     * 
     * @param rootObj the root obj to begin validation at
     * @throws JAXBException if any unexpected problem occurs during validation
     * @throws ValidationException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Validator</tt> is unable to validate the content tree rooted
     *     at <tt>rootObj</tt>
     * @throws IllegalArgumentException
     *      If the rootObj parameter is null
     * @return true if the tree rooted at <tt>rootObj</tt> is valid, false
     *         otherwise
     * @deprecated since JAXB2.0
     */
    public boolean validateRoot( Object rootObj ) throws JAXBException;

    /**
     * Set the particular property in the underlying implementation of
     * <tt>Validator</tt>.  This method can only be used to set one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to set an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * <p>
     *  验证以<tt> rootObj </tt>为根的Java内容树。
     * <p>
     *  客户端应用程序可以使用此方法在运行时按需验证Java内容树。此方法用于验证整个Java内容树。全局约束检查<b>将</b>作为此操作的一部分(即ID / IDREF约束)执行。
     * 
     * 
     * @param name the name of the property to be set. This value can either
     *              be specified using one of the constant fields or a user
     *              supplied string.
     * @param value the value of the property to be set
     *
     * @throws PropertyException when there is an error processing the given
     *                            property or value
     * @throws IllegalArgumentException
     *      If the name parameter is null
     * @deprecated since JAXB2.0
     */
    public void setProperty( String name, Object value )
        throws PropertyException;

    /**
     * Get the particular property in the underlying implementation of
     * <tt>Validator</tt>.  This method can only be used to get one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to get an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * <p>
     * 在<tt> Validator </tt>的底层实现中设置特定属性。此方法只能用于设置上面的标准JAXB定义属性或特定于提供程序的属性。
     * 尝试设置未定义的属性将导致抛出PropertyException。请参见<a href="#supportedProps">支持的属性</a>。
     * 
     * 
     * @param name the name of the property to retrieve
     * @return the value of the requested property
     *
     * @throws PropertyException
     *      when there is an error retrieving the given property or value
     *      property name
     * @throws IllegalArgumentException
     *      If the name parameter is null
     * @deprecated since JAXB2.0
     */
    public Object getProperty( String name ) throws PropertyException;

}
