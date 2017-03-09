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

import javax.naming.*;
import javax.naming.directory.*;

import java.util.Hashtable;

/**
  * This class is the starting context for performing
  * LDAPv3-style extended operations and controls.
  *<p>
  * See <tt>javax.naming.InitialContext</tt> and
  * <tt>javax.naming.InitialDirContext</tt> for details on synchronization,
  * and the policy for how an initial context is created.
  *
  * <h1>Request Controls</h1>
  * When you create an initial context (<tt>InitialLdapContext</tt>),
  * you can specify a list of request controls.
  * These controls will be used as the request controls for any
  * implicit LDAP "bind" operation performed by the context or contexts
  * derived from the context. These are called <em>connection request controls</em>.
  * Use <tt>getConnectControls()</tt> to get a context's connection request
  * controls.
  *<p>
  * The request controls supplied to the initial context constructor
  * are <em>not</em> used as the context request controls
  * for subsequent context operations such as searches and lookups.
  * Context request controls are set and updated by using
  * <tt>setRequestControls()</tt>.
  *<p>
  * As shown, there can be two different sets of request controls
  * associated with a context: connection request controls and context
  * request controls.
  * This is required for those applications needing to send critical
  * controls that might not be applicable to both the context operation and
  * any implicit LDAP "bind" operation.
  * A typical user program would do the following:
  *<blockquote><pre>
  * InitialLdapContext lctx = new InitialLdapContext(env, critConnCtls);
  * lctx.setRequestControls(critModCtls);
  * lctx.modifyAttributes(name, mods);
  * Controls[] respCtls =  lctx.getResponseControls();
  *</pre></blockquote>
  * It specifies first the critical controls for creating the initial context
  * (<tt>critConnCtls</tt>), and then sets the context's request controls
  * (<tt>critModCtls</tt>) for the context operation. If for some reason
  * <tt>lctx</tt> needs to reconnect to the server, it will use
  * <tt>critConnCtls</tt>. See the <tt>LdapContext</tt> interface for
  * more discussion about request controls.
  *<p>
  * Service provider implementors should read the "Service Provider" section
  * in the <tt>LdapContext</tt> class description for implementation details.
  *
  * <p>
  *  这个类是执行LDAPv3风格的扩展操作和控件的开始上下文。
  * p>
  *  有关同步的详细信息,请参阅<tt> javax.naming.InitialContext </tt>和<tt> javax.naming.InitialDirContext </tt>,以及如何创建
  * 初始上下文的策略。
  * 
  *  <h1>请求控件</h1>创建初始上下文(<tt> InitialLdapContext </tt>)时,可以指定请求控件的列表。
  * 这些控件将用作由上下文或从上下文派生的上下文执行的任何隐式LDAP"绑定"操作的请求控件。这些称为<em>连接请求控件</em>。
  * 使用<tt> getConnectControls()</tt>获取上下文的连接请求控件。
  * p>
  *  提供给初始上下文构造函数的请求控件不是</em>用作后续上下文操作(例如搜索和查找)的上下文请求控件。通过使用<tt> setRequestControls()</tt>设置和更新上下文请求控件。
  * p>
  * 如图所示,可以存在与上下文相关联的两组不同的请求控制：连接请求控制和上下文请求控制。这对于那些需要发送可能不适用于上下文操作和任何隐式LDAP"绑定"操作的关键控件的应用程序都是必需的。
  * 典型的用户程序将执行以下操作：blockquote> <pre> InitialLdapContext lctx = new InitialLdapContext(env,critConnCtls); 
  * lctx.setRequestControls(critModCtls); lctx.modifyAttributes(name,mods); Controls [] respCtls = lctx.g
  * etResponseControls(); / pre> </blockquote>它首先指定了创建初始上下文的关键控件(<tt> critConnCtls </tt>),然后为上下文操作设置上下文的请
  * 求控件(<tt> critModCtls </tt>) 。
  * 如图所示,可以存在与上下文相关联的两组不同的请求控制：连接请求控制和上下文请求控制。这对于那些需要发送可能不适用于上下文操作和任何隐式LDAP"绑定"操作的关键控件的应用程序都是必需的。
  * 如果由于某种原因<tt> lctx </tt>需要重新连接到服务器,它将使用<tt> critConnCtls </tt>。
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @author Vincent Ryan
  *
  * @see LdapContext
  * @see javax.naming.InitialContext
  * @see javax.naming.directory.InitialDirContext
  * @see javax.naming.spi.NamingManager#setInitialContextFactoryBuilder
  * @since 1.3
  */

public class InitialLdapContext extends InitialDirContext implements LdapContext {
    private static final String
        BIND_CONTROLS_PROPERTY = "java.naming.ldap.control.connect";

    /**
     * Constructs an initial context using no environment properties or
     * connection request controls.
     * Equivalent to <tt>new InitialLdapContext(null, null)</tt>.
     *
     * <p>
     * 有关请求控件的更多讨论,请参阅<tt> LdapContext </tt>界面。
     * p>
     *  服务提供者实现者应阅读<tt> LdapContext </tt>类描述中的"服务提供者"部分,了解实现细节。
     * 
     * 
     * @throws  NamingException if a naming exception is encountered
     */
    public InitialLdapContext() throws NamingException {
        super(null);
    }

    /**
     * Constructs an initial context
     * using environment properties and connection request controls.
     * See <tt>javax.naming.InitialContext</tt> for a discussion of
     * environment properties.
     *
     * <p> This constructor will not modify its parameters or
     * save references to them, but may save a clone or copy.
     * Caller should not modify mutable keys and values in
     * <tt>environment</tt> after it has been passed to the constructor.
     *
     * <p> <tt>connCtls</tt> is used as the underlying context instance's
     * connection request controls.  See the class description
     * for details.
     *
     * <p>
     *  使用无环境属性或连接请求控件构造初始上下文。等效于<tt> new InitialLdapContext(null,null)</tt>。
     * 
     * 
     * @param environment
     *          environment used to create the initial DirContext.
     *          Null indicates an empty environment.
     * @param connCtls
     *          connection request controls for the initial context.
     *          If null, no connection request controls are used.
     *
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #reconnect
     * @see LdapContext#reconnect
     */
    @SuppressWarnings("unchecked")
    public InitialLdapContext(Hashtable<?,?> environment,
                              Control[] connCtls)
            throws NamingException {
        super(true); // don't initialize yet

        // Clone environment since caller owns it.
        Hashtable<Object,Object> env = (environment == null)
            ? new Hashtable<>(11)
            : (Hashtable<Object,Object>)environment.clone();

        // Put connect controls into environment.  Copy them first since
        // caller owns the array.
        if (connCtls != null) {
            Control[] copy = new Control[connCtls.length];
            System.arraycopy(connCtls, 0, copy, 0, connCtls.length);
            env.put(BIND_CONTROLS_PROPERTY, copy);
        }
        // set version to LDAPv3
        env.put("java.naming.ldap.version", "3");

        // Initialize with updated environment
        init(env);
    }

    /**
     * Retrieves the initial LDAP context.
     *
     * <p>
     *  使用环境属性和连接请求控件构造初始上下文。有关环境属性的讨论,请参见<tt> javax.naming.InitialContext </tt>。
     * 
     * <p>此构造函数不会修改其参数或保存对它们的引用,但可以保存克隆或副本。调用者在传递给构造函数后,不应在<tt>环境</tt>中修改可变键和值。
     * 
     *  <p> <tt> connCtls </tt>用作基础上下文实例的连接请求控件。有关详细信息,请参阅类描述。
     * 
     * @return The non-null cached initial context.
     * @exception NotContextException If the initial context is not an
     * instance of <tt>LdapContext</tt>.
     * @exception NamingException If a naming exception was encountered.
     */
    private LdapContext getDefaultLdapInitCtx() throws NamingException{
        Context answer = getDefaultInitCtx();

        if (!(answer instanceof LdapContext)) {
            if (answer == null) {
                throw new NoInitialContextException();
            } else {
                throw new NotContextException(
                    "Not an instance of LdapContext");
            }
        }
        return (LdapContext)answer;
    }

// LdapContext methods
// Most Javadoc is deferred to the LdapContext interface.

    public ExtendedResponse extendedOperation(ExtendedRequest request)
            throws NamingException {
        return getDefaultLdapInitCtx().extendedOperation(request);
    }

    public LdapContext newInstance(Control[] reqCtls)
        throws NamingException {
            return getDefaultLdapInitCtx().newInstance(reqCtls);
    }

    public void reconnect(Control[] connCtls) throws NamingException {
        getDefaultLdapInitCtx().reconnect(connCtls);
    }

    public Control[] getConnectControls() throws NamingException {
        return getDefaultLdapInitCtx().getConnectControls();
    }

    public void setRequestControls(Control[] requestControls)
        throws NamingException {
            getDefaultLdapInitCtx().setRequestControls(requestControls);
    }

    public Control[] getRequestControls() throws NamingException {
        return getDefaultLdapInitCtx().getRequestControls();
    }

    public Control[] getResponseControls() throws NamingException {
        return getDefaultLdapInitCtx().getResponseControls();
    }
}
