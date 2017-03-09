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

package javax.naming;

import java.util.Hashtable;

/**
 * This abstract class is used to represent a referral exception,
 * which is generated in response to a <em>referral</em>
 * such as that returned by LDAP v3 servers.
 * <p>
 * A service provider provides
 * a subclass of <tt>ReferralException</tt> by providing implementations
 * for <tt>getReferralInfo()</tt> and <tt>getReferralContext()</tt> (and appropriate
 * constructors and/or corresponding "set" methods).
 * <p>
 * The following code sample shows how <tt>ReferralException</tt> can be used.
 * <blockquote>{@code
 *      while (true) {
 *          try {
 *              bindings = ctx.listBindings(name);
 *              while (bindings.hasMore()) {
 *                  b = bindings.next();
 *                  ...
 *              }
 *              break;
 *          } catch (ReferralException e) {
 *              ctx = e.getReferralContext();
 *          }
 *      }
 * }</blockquote>
 *<p>
 * <tt>ReferralException</tt> is an abstract class. Concrete implementations
 * determine its synchronization and serialization properties.
 *<p>
 * An environment parameter passed to the <tt>getReferralContext()</tt>
 * method is owned by the caller.
 * The service provider will not modify the object or keep a reference to it,
 * but may keep a reference to a clone of it.
 *
 * <p>
 *  此抽象类用于表示引用异常,该异常是响应于引用(例如LDAP v3服务器返回的)的<em> </em>引用而生成的。
 * <p>
 *  服务提供者通过提供<tt> getReferralInfo()</tt>和<tt> getReferralContext()</tt>(以及适当的构造函数和/或对应的"set")的实现来提供<tt> 
 * ReferralException </tt> " 方法)。
 * <p>
 *  以下代码示例说明了如何使用<tt> ReferralException </tt>。
 *  <blockquote> {@ code while(true){try {bindings = ctx.listBindings(name); while(bindings.hasMore()){b = bindings.next(); ...}
 *  } catch(ReferralException e){ctx = e.getReferralContext(); }}} </blockquote>。
 *  以下代码示例说明了如何使用<tt> ReferralException </tt>。
 * p>
 *  <tt> ReferralException </tt>是一个抽象类。具体实现确定其同步和序列化属性。
 * p>
 *  传递给<tt> getReferralContext()</tt>方法的环境参数由调用者拥有。服务提供程序不会修改对象或保留对它的引用,但可以保留对其的克隆的引用。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 *
 * @since 1.3
 *
 */

public abstract class ReferralException extends NamingException {
    /**
     * Constructs a new instance of ReferralException using the
     * explanation supplied. All other fields are set to null.
     *
     * <p>
     *  使用提供的解释构造ReferralException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param   explanation     Additional detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    protected ReferralException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of ReferralException.
      * All fields are set to null.
      * <p>
      *  构造ReferralException的新实例。所有字段都设置为null。
      * 
      */
    protected ReferralException() {
        super();
    }

    /**
     * Retrieves information (such as URLs) related to this referral.
     * The program may examine or display this information
     * to the user to determine whether to continue with the referral,
     * or to determine additional information needs to be supplied in order
     * to continue with the referral.
     *
     * <p>
     * 检索与此引荐相关的信息(如网址)。程序可以检查或向用户显示该信息以确定是继续转诊还是确定需要提供附加信息以便继续转诊。
     * 
     * 
     * @return Non-null referral information related to this referral.
     */
    public abstract Object getReferralInfo();

    /**
     * Retrieves the context at which to continue the method.
     * Regardless of whether a referral is encountered directly during a
     * context operation, or indirectly, for example, during a search
     * enumeration, the referral exception should provide a context
     * at which to continue the operation. The referral context is
     * created using the environment properties of the context
     * that threw the ReferralException.
     *
     *<p>
     * To continue the operation, the client program should re-invoke
     * the method using the same arguments as the original invocation.
     *
     * <p>
     *  检索继续该方法的上下文。不管在上下文操作期间直接遇到引用还是例如在搜索枚举期间间接遇到引用,引用异常应提供继续操作的上下文。
     * 引用上下文是使用抛出ReferralException的上下文的环境属性创建的。
     * 
     * p>
     *  要继续操作,客户端程序应使用与原始调用相同的参数重新调用该方法。
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
     * environment properties.
     * Regardless of whether a referral is encountered directly during a
     * context operation, or indirectly, for example, during a search
     * enumeration, the referral exception should provide a context
     * at which to continue the operation.
     *<p>
     * The referral context is created using <tt>env</tt> as its environment
     * properties.
     * This method should be used instead of the no-arg overloaded form
     * when the caller needs to use different environment properties for
     * the referral context. It might need to do this, for example, when
     * it needs to supply different authentication information to the referred
     * server in order to create the referral context.
     *<p>
     * To continue the operation, the client program should re-invoke
     * the method using the same arguments as the original invocation.
     *
     * <p>
     *  检索使用环境属性继续方法的上下文。不管在上下文操作期间直接遇到引用还是例如在搜索枚举期间间接遇到引用,引用异常应提供继续操作的上下文。
     * p>
     * 引用上下文是使用<tt> env </tt>作为其环境属性创建的。当调用者需要为引用上下文使用不同的环境属性时,应该使用此方法而不是无参数重载的形式。
     * 它可能需要这样做,例如,当它需要提供不同的认证信息到引用的服务器,以创建引用上下文。
     * p>
     *  要继续操作,客户端程序应使用与原始调用相同的参数重新调用该方法。
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
     * Discards the referral about to be processed.
     * A call to this method should be followed by a call to
     * <code>getReferralContext</code> to allow the processing of
     * other referrals to continue.
     * The following code fragment shows a typical usage pattern.
     * <blockquote><pre>
     *  } catch (ReferralException e) {
     *      if (!shallIFollow(e.getReferralInfo())) {
     *          if (!e.skipReferral()) {
     *              return;
     *          }
     *      }
     *      ctx = e.getReferralContext();
     *  }
     * </pre></blockquote>
     *
     * <p>
     *  丢弃要处理的引荐。调用此方法后应紧接着调用<code> getReferralContext </code>,以允许其他引用的处理继续。以下代码片段显示了典型的使用模式。
     *  <blockquote> <pre>} catch(ReferralException e){if(！shallIFollow(e.getReferralInfo())){if(！e.skipReferral()){return; }
     * } ctx = e.getReferralContext(); } </pre> </blockquote>。
     *  丢弃要处理的引荐。调用此方法后应紧接着调用<code> getReferralContext </code>,以允许其他引用的处理继续。以下代码片段显示了典型的使用模式。
     * 
     * 
     * @return true If more referral processing is pending; false otherwise.
     */
    public abstract boolean skipReferral();

    /**
     * Retries the referral currently being processed.
     * A call to this method should be followed by a call to
     * <code>getReferralContext</code> to allow the current
     * referral to be retried.
     * The following code fragment shows a typical usage pattern.
     * <blockquote><pre>
     *  } catch (ReferralException e) {
     *      while (true) {
     *          try {
     *              ctx = e.getReferralContext(env);
     *              break;
     *          } catch (NamingException ne) {
     *              if (! shallIRetry()) {
     *                  return;
     *              }
     *              // modify environment properties (env), if necessary
     *              e.retryReferral();
     *          }
     *      }
     *  }
     * </pre></blockquote>
     *
     * <p>
     *  重试正在处理的引荐。调用此方法后应紧接着调用<code> getReferralContext </code>以允许重试当前的引用。以下代码片段显示了典型的使用模式。
     *  <blockquote> <pre>} catch(ReferralException e){while(true){try {ctx = e.getReferralContext(env);打破; }
     *  catch(NamingException ne){if(！shallIRetry()){return; } //修改环境属性(env),如果必要e.retryReferral(); }}} </pre>
     *  </blockquote>。
     *  重试正在处理的引荐。调用此方法后应紧接着调用<code> getReferralContext </code>以允许重试当前的引用。以下代码片段显示了典型的使用模式。
     */
    public abstract void retryReferral();

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     * 
     */
    private static final long serialVersionUID = -2881363844695698876L;
}
