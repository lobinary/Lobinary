/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

import javax.naming.ReferralException;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * This abstract class is used to represent an LDAP referral exception.
 * It extends the base <tt>ReferralException</tt> by providing a
 * <tt>getReferralContext()</tt> method that accepts request controls.
 * LdapReferralException is an abstract class. Concrete implementations of it
 * determine its synchronization and serialization properties.
 *<p>
 * A <tt>Control[]</tt> array passed as a parameter to
 * the <tt>getReferralContext()</tt> method is owned by the caller.
 * The service provider will not modify the array or keep a reference to it,
 * although it may keep references to the individual <tt>Control</tt> objects
 * in the array.
 *
 * <p>
 *  此抽象类用于表示LDAP引用异常。它通过提供接受请求控件的<tt> getReferralContext()</tt>方法来扩展基本<tt> ReferralException </tt>。
 *  LdapReferralException是一个抽象类。它的具体实现确定其同步和序列化属性。
 * p>
 *  作为参数传递给<tt> getReferralContext()</tt>方法的<tt> Control [] </tt>数组由调用者拥有。
 * 服务提供程序不会修改数组或保留对它的引用,尽管它可能会保留对数组中各个<tt> Control </tt>对象的引用。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @author Vincent Ryan
 * @since 1.3
 */

public abstract class LdapReferralException extends ReferralException {
    /**
     * Constructs a new instance of LdapReferralException using the
     * explanation supplied. All other fields are set to null.
     *
     * <p>
     *  使用提供的解释构造LdapReferralException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param   explanation     Additional detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    protected LdapReferralException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of LdapReferralException.
      * All fields are set to null.
      * <p>
      *  构造LdapReferralException的新实例。所有字段都设置为null。
      * 
      */
    protected LdapReferralException() {
        super();
    }

    /**
     * Retrieves the context at which to continue the method using the
     * context's environment and no controls.
     * The referral context is created using the environment properties of
     * the context that threw the <tt>ReferralException</tt> and no controls.
     *<p>
     * This method is equivalent to
     *<blockquote><pre>
     * getReferralContext(ctx.getEnvironment(), null);
     *</pre></blockquote>
     * where <tt>ctx</tt> is the context that threw the <tt>ReferralException.</tt>
     *<p>
     * It is overridden in this class for documentation purposes only.
     * See <tt>ReferralException</tt> for how to use this method.
     *
     * <p>
     *  检索使用上下文的环境并且没有控件来继续方法的上下文。引用上下文是使用抛出<tt> ReferralException </tt>且没有控件的上下文的环境属性创建的。
     * p>
     *  这个方法相当于blockquote> <pre> getReferralContext(ctx.getEnvironment(),null); / pre> </blockquote>其中<tt> c
     * tx </tt>是抛出<tt> ReferralException的上下文。
     * </tt>。
     * p>
     * 它在此类中被覆盖,仅用于文档目的。有关如何使用此方法,请参阅<tt> ReferralException </tt>。
     * 
     * 
     * @return The non-null context at which to continue the method.
     * @exception NamingException If a naming exception was encountered.
     * Call either <tt>retryReferral()</tt> or <tt>skipReferral()</tt>
     * to continue processing referrals.
     */
    public abstract Context getReferralContext() throws NamingException;

    /**
     * Retrieves the context at which to continue the method using
     * environment properties and no controls.
     * The referral context is created using <tt>env</tt> as its environment
     * properties and no controls.
     *<p>
     * This method is equivalent to
     *<blockquote><pre>
     * getReferralContext(env, null);
     *</pre></blockquote>
     *<p>
     * It is overridden in this class for documentation purposes only.
     * See <tt>ReferralException</tt> for how to use this method.
     *
     * <p>
     *  检索使用环境属性而不使用控件继续方法的上下文。引用上下文是使用<tt> env </tt>作为其环境属性并且没有控件创建的。
     * p>
     *  这个方法等价于blockquote> <pre> getReferralContext(env,null); / pre> </blockquote>
     * p>
     *  它在此类中被覆盖,仅用于文档目的。有关如何使用此方法,请参阅<tt> ReferralException </tt>。
     * 
     * 
     * @param env The possibly null environment to use when retrieving the
     *          referral context. If null, no environment properties will be used.
     *
     * @return The non-null context at which to continue the method.
     * @exception NamingException If a naming exception was encountered.
     * Call either <tt>retryReferral()</tt> or <tt>skipReferral()</tt>
     * to continue processing referrals.
     */
    public abstract Context
        getReferralContext(Hashtable<?,?> env)
        throws NamingException;

    /**
     * Retrieves the context at which to continue the method using
     * request controls and environment properties.
     * Regardless of whether a referral is encountered directly during a
     * context operation, or indirectly, for example, during a search
     * enumeration, the referral exception should provide a context
     * at which to continue the operation.
     * To continue the operation, the client program should re-invoke
     * the method using the same arguments as the original invocation.
     *<p>
     * <tt>reqCtls</tt> is used when creating the connection to the referred
     * server. These controls will be used as the connection request controls for
     * the context and context instances
     * derived from the context.
     * <tt>reqCtls</tt> will also be the context's request controls for
     * subsequent context operations. See the <tt>LdapContext</tt> class
     * description for details.
     *<p>
     * This method should be used instead of the other two overloaded forms
     * when the caller needs to supply request controls for creating
     * the referral context. It might need to do this, for example, when
     * it needs to supply special controls relating to authentication.
     *<p>
     * Service provider implementors should read the "Service Provider" section
     * in the <tt>LdapContext</tt> class description for implementation details.
     *
     * <p>
     *  检索使用请求控件和环境属性继续方法的上下文。不管在上下文操作期间直接遇到引用还是例如在搜索枚举期间间接遇到引用,引用异常应提供继续操作的上下文。
     * 要继续操作,客户端程序应使用与原始调用相同的参数重新调用该方法。
     * p>
     *  <tt> reqCtls </tt>用于创建与引用服务器的连接。这些控件将用作从上下文派生的上下文实例的连接请求控件。 <tt> reqCtls </tt>也将是上下文对后续上下文操作的请求控件。
     * 有关详细信息,请参阅<tt> LdapContext </tt>类描述。
     * 
     * @param reqCtls The possibly null request controls to use for the new context.
     * If null or the empty array means use no request controls.
     * @param env The possibly null environment properties to use when
     * for the new context. If null, the context is initialized with no environment
     * properties.
     * @return The non-null context at which to continue the method.
     * @exception NamingException If a naming exception was encountered.
     * Call either <tt>retryReferral()</tt> or <tt>skipReferral()</tt>
     * to continue processing referrals.
     */
    public abstract Context
        getReferralContext(Hashtable<?,?> env,
                           Control[] reqCtls)
        throws NamingException;

    private static final long serialVersionUID = -1668992791764950804L;
}
