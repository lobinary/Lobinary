/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.ldap;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import java.util.Hashtable;

/**
 * This interface represents a context in which you can perform
 * operations with LDAPv3-style controls and perform LDAPv3-style
 * extended operations.
 *
 * For applications that do not require such controls or extended
 * operations, the more generic <tt>javax.naming.directory.DirContext</tt>
 * should be used instead.
 *
 * <h3>Usage Details About Controls</h3>
 *
 * This interface provides support for LDAP v3 controls.
 * At a high level, this support allows a user
 * program to set request controls for LDAP operations that are executed
 * in the course of the user program's invocation of
 * <tt>Context</tt>/<tt>DirContext</tt>
 * methods, and read response controls resulting from LDAP operations.
 * At the implementation level, there are some details that developers of
 * both the user program and service providers need to understand in order
 * to correctly use request and response controls.
 *
 * <h3>Request Controls</h3>
 * <p>
 * There are two types of request controls:
 * <ul>
 * <li>Request controls that affect how a connection is created
 * <li>Request controls that affect context methods
 * </ul>
 *
 * The former is used whenever a connection needs to be established or
 * re-established with an LDAP server. The latter is used when all other
 * LDAP operations are sent to the LDAP server.  The reason why a
 * distinction between these two types of request controls is necessary
 * is because JNDI is a high-level API that does not deal directly with
 * connections.  It is the job of service providers to do any necessary
 * connection management. Consequently, a single
 * connection may be shared by multiple context instances, and a service provider
 * is free to use its own algorithms to conserve connection and network
 * usage. Thus, when a method is invoked on the context instance, the service
 * provider might need to do some connection management in addition to
 * performing the corresponding LDAP operations. For connection management,
 * it uses the <em>connection request controls</em>, while for the normal
 * LDAP operations, it uses the <em>context request controls</em>.
 *<p>Unless explicitly qualified, the term "request controls" refers to
 * context request controls.
 *
 * <h4>Context Request Controls</h4>
 * There are two ways in which a context instance gets its request controls:
 * <ol>
 * <li><tt>ldapContext.newInstance(<strong>reqCtls</strong>)</tt>
 * <li><tt>ldapContext.setRequestControls(<strong>reqCtls</strong>)</tt>
 * </ol>
 * where <tt>ldapContext</tt> is an instance of <tt>LdapContext</tt>.
 * Specifying <tt>null</tt> or an empty array for <tt>reqCtls</tt>
 * means no request controls.
 * <tt>newInstance()</tt> creates a new instance of a context using
 * <tt>reqCtls</tt>, while <tt>setRequestControls()</tt>
 * updates an existing context instance's request controls to <tt>reqCtls</tt>.
 * <p>
 * Unlike environment properties, request controls of a context instance
 * <em>are not inherited</em> by context instances that are derived from
 * it.  Derived context instances have <tt>null</tt> as their context
 * request controls.  You must set the request controls of a derived context
 * instance explicitly using <tt>setRequestControls()</tt>.
 * <p>
 * A context instance's request controls are retrieved using
 * the method <tt>getRequestControls()</tt>.
 *
 * <h4>Connection Request Controls</h4>
 * There are three ways in which connection request controls are set:
 * <ol>
 * <li><tt>
 * new InitialLdapContext(env, <strong>connCtls</strong>)</tt>
 * <li><tt>refException.getReferralContext(env, <strong>connCtls</strong>)</tt>
 * <li><tt>ldapContext.reconnect(<strong>connCtls</strong>);</tt>
 * </ol>
 * where <tt>refException</tt> is an instance of
 * <tt>LdapReferralException</tt>, and <tt>ldapContext</tt> is an
 * instance of <tt>LdapContext</tt>.
 * Specifying <tt>null</tt> or an empty array for <tt>connCtls</tt>
 * means no connection request controls.
 * <p>
 * Like environment properties, connection request controls of a context
 * <em>are inherited</em> by contexts that are derived from it.
 * Typically, you initialize the connection request controls using the
 * <tt>InitialLdapContext</tt> constructor or
 * <tt>LdapReferralContext.getReferralContext()</tt>. These connection
 * request controls are inherited by contexts that share the same
 * connection--that is, contexts derived from the initial or referral
 * contexts.
 * <p>
 * Use <tt>reconnect()</tt> to change the connection request controls of
 * a context.
 * Invoking <tt>ldapContext.reconnect()</tt> affects only the
 * connection used by <tt>ldapContext</tt> and any new contexts instances that are
 * derived form <tt>ldapContext</tt>. Contexts that previously shared the
 * connection with <tt>ldapContext</tt> remain unchanged. That is, a context's
 * connection request controls must be explicitly changed and is not
 * affected by changes to another context's connection request
 * controls.
 * <p>
 * A context instance's connection request controls are retrieved using
 * the method <tt>getConnectControls()</tt>.
 *
 * <h4>Service Provider Requirements</h4>
 *
 * A service provider supports connection and context request controls
 * in the following ways.  Context request controls must be associated on
 * a per context instance basis while connection request controls must be
 * associated on a per connection instance basis.  The service provider
 * must look for the connection request controls in the environment
 * property "java.naming.ldap.control.connect" and pass this environment
 * property on to context instances that it creates.
 *
 * <h3>Response Controls</h3>
 *
 * The method <tt>LdapContext.getResponseControls()</tt> is used to
 * retrieve the response controls generated by LDAP operations executed
 * as the result of invoking a <tt>Context</tt>/<tt>DirContext</tt>
 * operation. The result is all of the responses controls generated
 * by the underlying LDAP operations, including any implicit reconnection.
 * To get only the reconnection response controls,
 * use <tt>reconnect()</tt> followed by <tt>getResponseControls()</tt>.
 *
 * <h3>Parameters</h3>
 *
 * A <tt>Control[]</tt> array
 * passed as a parameter to any method is owned by the caller.
 * The service provider will not modify the array or keep a reference to it,
 * although it may keep references to the individual <tt>Control</tt> objects
 * in the array.
 * A <tt>Control[]</tt> array returned by any method is immutable, and may
 * not subsequently be modified by either the caller or the service provider.
 *
 * <p>
 *  此接口代表一个上下文,您可以在其中使用LDAPv3样式的控件执行操作并执行LDAPv3风格的扩展操作。
 * 
 *  对于不需要此类控件或扩展操作的应用程序,应使用更通用的<tt> javax.naming.directory.DirContext </tt>。
 * 
 *  <h3>关于控制的使用细节</h3>
 * 
 *  此接口支持LDAP v3控件。
 * 在高层次上,这种支持允许用户程序为在用户程序调用<tt> Context </tt> / <tt> DirContext </tt>方法的过程中执行的LDAP操作设置请求控制,以及由LDAP操作产生的读
 * 响应控件。
 *  此接口支持LDAP v3控件。在实现级别,有一些细节,用户程序和服务提供者的开发者需要理解以正确使用请求和响应控制。
 * 
 *  <h3>请求控制</h3>
 * <p>
 *  有两种类型的请求控件：
 * <ul>
 *  <li>请求影响连接创建方式的控制<li>请求影响上下文方法的控件
 * </ul>
 * 
 * 前者在需要与LDAP服务器建立或重新建立连接时使用。后者在所有其他LDAP操作发送到LDAP服务器时使用。需要区分这两种类型的请求控件的原因是因为JNDI是不直接处理连接的高级API。
 * 服务提供商的工作是做任何必要的连接管理。因此,单个连接可以由多个上下文实例共享,并且服务提供商可以自由使用其自己的算法来节省连接和网络使用。
 * 因此,当在上下文实例上调用方法时,除了执行相应的LDAP操作之外,服务提供者可能需要进行一些连接管理。
 * 对于连接管理,它使用<em>连接请求控件</em>,而对于正常的LDAP操作,它使用<em>上下文请求控件</em>。 p>除非明确限定,否则术语"请求控件"是指上下文请求控件。
 * 
 *  <h4>上下文请求控件</h4>上下文实例获取其请求控件有两种方式：
 * <ol>
 *  <li> <tt> ldapContext.newInstance(<strong> reqCtls </strong>)</tt> <li> <tt> ldapContext.setRequestC
 * ontrols(<strong> reqCtls </strong>)</tt>。
 * </ol>
 * 其中<tt> ldapContext </tt>是<tt> LdapContext </tt>的实例。为<tt> reqCtls </tt>指定<tt> null </tt>或空数组表示没有请求控件。
 *  <tt> newInstance()</tt>使用<tt> reqCtls </tt>创建上下文的新实例,而<tt> setRequestControls()</tt>将现有上下文实例的请求控件更新为
 * <tt> reqCtls </tt>。
 * <p>
 *  与环境属性不同,上下文实例的请求控件<em>不是从它派生的上下文实例继承的<em> </em>。派生上下文实例具有<tt> null </tt>作为其上下文请求控件。
 * 您必须使用<tt> setRequestControls()</tt>显式设置派生上下文实例的请求控件。
 * <p>
 *  使用方法<tt> getRequestControls()</tt>检索上下文实例的请求控件。
 * 
 *  <h4>连接请求控件</h4>连接请求控件有三种设置方式：
 * <ol>
 *  <li> <tt> new InitialLdapContext(env,<strong> connCtls </strong>)</tt> <li> <tt> refException.getRef
 * erralContext(env,<strong> connCtls </strong>)</tt> < li> <tt> ldapContext.reconnect(<strong> connCtls
 *  </strong>); </tt>。
 * </ol>
 *  其中<tt> refException </tt>是<tt> LdapReferralException </tt>的实例,而<tt> ldapContext </tt>是<tt> LdapConte
 * xt </tt>的一个实例。
 * 为<tt> connCtls </tt>指定<tt> null </tt>或空数组表示没有连接请求控件。
 * <p>
 * 与环境属性一样,上下文的连接请求控件通过从其派生的上下文继承。
 * 通常,您可以使用<tt> InitialLdapContext </tt>构造函数或<tt> LdapReferralContext.getReferralContext()</tt>初始化连接请求控件
 * 。
 * 与环境属性一样,上下文的连接请求控件通过从其派生的上下文继承。这些连接请求控件由共享相同连接的上下文继承 - 即从初始或引用上下文派生的上下文。
 * <p>
 *  使用<tt> reconnect()</tt>更改上下文的连接请求控件。
 * 调用<tt> ldapContext.reconnect()</tt>只影响<tt> ldapContext </tt>使用的连接以及从<tt> ldapContext </tt>派生的任何新上下文实例
 * 。
 *  使用<tt> reconnect()</tt>更改上下文的连接请求控件。以前与<tt> ldapContext </tt>共享连接的上下文保持不变。
 * 也就是说,上下文的连接请求控件必须显式更改,并且不会受到对另一个上下文的连接请求控件的更改的影响。
 * <p>
 *  使用方法<tt> getConnectControls()</tt>检索上下文实例的连接请求控件。
 * 
 *  <h4>服务提供商要求</h4>
 * 
 * 服务提供商通过以下方式支持连接和上下文请求控制。上下文请求控件必须在每个上下文实例的基础上相关联,而连接请求控件必须在每个连接实例的基础上相关联。
 * 服务提供程序必须在environment属性"java.naming.ldap.control.connect"中查找连接请求控件,并将此环境属性传递到其创建的上下文实例。
 * 
 *  <h3>响应控制</h3>
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @author Vincent Ryan
 *
 * @see InitialLdapContext
 * @see LdapReferralException#getReferralContext(java.util.Hashtable,javax.naming.ldap.Control[])
 * @since 1.3
 */

public interface LdapContext extends DirContext {
   /**
    * Performs an extended operation.
    *
    * This method is used to support LDAPv3 extended operations.
    * <p>
    * 
    *  方法<tt> LdapContext.getResponseControls()</tt>用于检索由调用<tt> Context </tt> / <tt> DirContext </tt>操作执行的L
    * DAP操作生成的响应控件。
    * 结果是由底层LDAP操作生成的所有响应控件,包括任何隐式重新连接。
    * 要仅获取重新连接响应控件,请使用<tt> reconnect()</tt>,后跟<tt> getResponseControls()</tt>。
    * 
    *  <h3>参数</h3>
    * 
    *  作为参数传递给任何方法的<tt> Control [] </tt>数组由调用者拥有。
    * 服务提供程序不会修改数组或保留对它的引用,尽管它可能会保留对数组中各个<tt> Control </tt>对象的引用。
    * 任何方法返回的<tt> Control [] </tt>数组都是不可变的,并且可能不会被调用者或服务提供者随后修改。
    * 
    * 
    * @param request The non-null request to be performed.
    * @return The possibly null response of the operation. null means
    * the operation did not generate any response.
    * @throws NamingException If an error occurred while performing the
    * extended operation.
    */
    public ExtendedResponse extendedOperation(ExtendedRequest request)
        throws NamingException;

    /**
     * Creates a new instance of this context initialized using request controls.
     *
     * This method is a convenience method for creating a new instance
     * of this context for the purposes of multithreaded access.
     * For example, if multiple threads want to use different context
     * request controls,
     * each thread may use this method to get its own copy of this context
     * and set/get context request controls without having to synchronize with other
     * threads.
     *<p>
     * The new context has the same environment properties and connection
     * request controls as this context. See the class description for details.
     * Implementations might also allow this context and the new context
     * to share the same network connection or other resources if doing
     * so does not impede the independence of either context.
     *
     * <p>
     *  执行扩展操作。
     * 
     *  此方法用于支持LDAPv3扩展操作。
     * 
     * 
     * @param requestControls The possibly null request controls
     * to use for the new context.
     * If null, the context is initialized with no request controls.
     *
     * @return A non-null <tt>LdapContext</tt> instance.
     * @exception NamingException If an error occurred while creating
     * the new instance.
     * @see InitialLdapContext
     */
    public LdapContext newInstance(Control[] requestControls)
        throws NamingException;

    /**
     * Reconnects to the LDAP server using the supplied controls and
     * this context's environment.
     *<p>
     * This method is a way to explicitly initiate an LDAP "bind" operation.
     * For example, you can use this method to set request controls for
     * the LDAP "bind" operation, or to explicitly connect to the server
     * to get response controls returned by the LDAP "bind" operation.
     *<p>
     * This method sets this context's <tt>connCtls</tt>
     * to be its new connection request controls. This context's
     * context request controls are not affected.
     * After this method has been invoked, any subsequent
     * implicit reconnections will be done using <tt>connCtls</tt>.
     * <tt>connCtls</tt> are also used as
     * connection request controls for new context instances derived from this
     * context.
     * These connection request controls are not
     * affected by <tt>setRequestControls()</tt>.
     *<p>
     * Service provider implementors should read the "Service Provider" section
     * in the class description for implementation details.
     * <p>
     * 创建使用请求控件初始化的此上下文的新实例。
     * 
     *  此方法是为多线程访问的目的创建此上下文的新实例的方便方法。
     * 例如,如果多个线程想要使用不同的上下文请求控制,则每个线程可以使用该方法来获得其自己的该上下文的副本和设置/获得上下文请求控制,而不必与其他线程同步。
     * p>
     *  新上下文具有与此上下文相同的环境属性和连接请求控件。有关详细信息,请参阅类描述。实现还可以允许此上下文和新上下文共享相同的网络连接或其他资源,如果这样做不妨碍任一上下文的独立性。
     * 
     * 
     * @param connCtls The possibly null controls to use. If null, no
     * controls are used.
     * @exception NamingException If an error occurred while reconnecting.
     * @see #getConnectControls
     * @see #newInstance
     */
    public void reconnect(Control[] connCtls) throws NamingException;

    /**
     * Retrieves the connection request controls in effect for this context.
     * The controls are owned by the JNDI implementation and are
     * immutable. Neither the array nor the controls may be modified by the
     * caller.
     *
     * <p>
     *  使用提供的控件和此上下文的环境重新连接到LDAP服务器。
     * p>
     *  此方法是一种显式启动LDAP"绑定"操作的方法。例如,您可以使用此方法为LDAP"绑定"操作设置请求控件,或者显式连接到服务器以获取LDAP"绑定"操作返回的响应控件。
     * p>
     * 此方法将此上下文的<tt> connCtls </tt>设置为其新的连接请求控件。此上下文的上下文请求控件不受影响。
     * 调用此方法后,将使用<tt> connCtls </tt>执行任何后续的隐式重新连接。 <tt> connCtls </tt>也用作从此上下文派生的新上下文实例的连接请求控件。
     * 这些连接请求控件不受<tt> setRequestControls()</tt>的影响。
     * p>
     *  服务提供者实现者应阅读类描述中的"服务提供者"部分以了解实现细节。
     * 
     * 
     * @return A possibly-null array of controls. null means no connect controls
     * have been set for this context.
     * @exception NamingException If an error occurred while getting the request
     * controls.
     */
    public Control[] getConnectControls() throws NamingException;

    /**
     * Sets the request controls for methods subsequently
     * invoked on this context.
     * The request controls are owned by the JNDI implementation and are
     * immutable. Neither the array nor the controls may be modified by the
     * caller.
     * <p>
     * This removes any previous request controls and adds
     * <tt>requestControls</tt>
     * for use by subsequent methods invoked on this context.
     * This method does not affect this context's connection request controls.
     *<p>
     * Note that <tt>requestControls</tt> will be in effect until the next
     * invocation of <tt>setRequestControls()</tt>. You need to explicitly
     * invoke <tt>setRequestControls()</tt> with <tt>null</tt> or an empty
     * array to clear the controls if you don't want them to affect the
     * context methods any more.
     * To check what request controls are in effect for this context, use
     * <tt>getRequestControls()</tt>.
     * <p>
     *  检索对此上下文有效的连接请求控件。控件由JNDI实现拥有并且是不可变的。调用者不能修改数组和控件。
     * 
     * 
     * @param requestControls The possibly null controls to use. If null, no
     * controls are used.
     * @exception NamingException If an error occurred while setting the
     * request controls.
     * @see #getRequestControls
     */
    public void setRequestControls(Control[] requestControls)
        throws NamingException;

    /**
     * Retrieves the request controls in effect for this context.
     * The request controls are owned by the JNDI implementation and are
     * immutable. Neither the array nor the controls may be modified by the
     * caller.
     *
     * <p>
     *  设置随后在此上下文中调用的方法的请求控件。请求控件由JNDI实现拥有并且是不可变的。调用者不能修改数组和控件。
     * <p>
     *  此操作会删除任何先前的请求控件,并添加<tt> requestControls </tt>以供在此上下文中调用的后续方法使用。此方法不会影响此上下文的连接请求控件。
     * p>
     * 请注意,<tt> requestControls </tt>将在下次调用<tt> setRequestControls()</tt>之前有效。
     * 您需要使用<tt> null </tt>或空数组显式调用<tt> setRequestControls()</tt>,以清除控件,如果您不想让它们影响上下文方法。
     * 要检查对此上下文有效的请求控件,请使用<tt> getRequestControls()</tt>。
     * 
     * 
     * @return A possibly-null array of controls. null means no request controls
     * have been set for this context.
     * @exception NamingException If an error occurred while getting the request
     * controls.
     * @see #setRequestControls
     */
    public Control[] getRequestControls() throws NamingException;

    /**
     * Retrieves the response controls produced as a result of the last
     * method invoked on this context.
     * The response controls are owned by the JNDI implementation and are
     * immutable. Neither the array nor the controls may be modified by the
     * caller.
     *<p>
     * These response controls might have been generated by a successful or
     * failed operation.
     *<p>
     * When a context method that may return response controls is invoked,
     * response controls from the previous method invocation are cleared.
     * <tt>getResponseControls()</tt> returns all of the response controls
     * generated by LDAP operations used by the context method in the order
     * received from the LDAP server.
     * Invoking <tt>getResponseControls()</tt> does not
     * clear the response controls. You can call it many times (and get
     * back the same controls) until the next context method that may return
     * controls is invoked.
     *<p>
     * <p>
     *  检索对此上下文有效的请求控件。请求控件由JNDI实现拥有并且是不可变的。调用者不能修改数组和控件。
     * 
     * 
     * @return A possibly null array of controls. If null, the previous
     * method invoked on this context did not produce any controls.
     * @exception NamingException If an error occurred while getting the response
     * controls.
     */
    public Control[] getResponseControls() throws NamingException;

    /**
     * Constant that holds the name of the environment property
     * for specifying the list of control factories to use. The value
     * of the property should be a colon-separated list of the fully
     * qualified class names of factory classes that will create a control
     * given another control. See
     * <tt>ControlFactory.getControlInstance()</tt> for details.
     * This property may be specified in the environment, an applet
     * parameter, a system property, or one or more resource files.
     *<p>
     * The value of this constant is "java.naming.factory.control".
     *
     * <p>
     *  检索作为在此上下文上调用的最后一个方法的结果产生的响应控件。响应控件由JNDI实现拥有并且是不可变的。调用者不能修改数组和控件。
     * p>
     *  这些响应控件可能由成功或失败的操作生成。
     * p>
     *  当调用可能返回响应控件的上下文方法时,清除先前方法调用的响应控件。
     *  <tt> getResponseControls()</tt>返回从LDAP服务器接收到的顺序中由上下文方法使用的LDAP操作生成的所有响应控件。
     * 调用<tt> getResponseControls()</tt>不会清除响应控件。你可以调用它多次(并获得相同的控件),直到下一个上下文方法可能返回控件被调用。
     * 
     * @see ControlFactory
     * @see javax.naming.Context#addToEnvironment
     * @see javax.naming.Context#removeFromEnvironment
     */
    static final String CONTROL_FACTORIES = "java.naming.factory.control";
}
