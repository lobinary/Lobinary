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

package java.security;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.security.auth.callback.CallbackHandler;

/**
 * This class defines login and logout methods for a provider.
 *
 * <p> While callers may invoke {@code login} directly,
 * the provider may also invoke {@code login} on behalf of callers
 * if it determines that a login must be performed
 * prior to certain operations.
 *
 * <p>
 *  此类定义提供程序的登录和注销方法。
 * 
 *  <p>虽然呼叫者可以直接调用{@code login},但是如果确定必须在某些操作之前执行登录,则提供者也可以代表呼叫者调用{@code login}。
 * 
 * 
 * @since 1.5
 */
public abstract class AuthProvider extends Provider {

    private static final long serialVersionUID = 4197859053084546461L;

    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     *
     * <p>
     *  构造具有指定的名称,版本号和信息的提供程序。
     * 
     * 
     * @param name the provider name.
     * @param version the provider version number.
     * @param info a description of the provider and its services.
     */
    protected AuthProvider(String name, double version, String info) {
        super(name, version, info);
    }

    /**
     * Log in to this provider.
     *
     * <p> The provider relies on a {@code CallbackHandler}
     * to obtain authentication information from the caller
     * (a PIN, for example).  If the caller passes a {@code null}
     * handler to this method, the provider uses the handler set in the
     * {@code setCallbackHandler} method.
     * If no handler was set in that method, the provider queries the
     * <i>auth.login.defaultCallbackHandler</i> security property
     * for the fully qualified class name of a default handler implementation.
     * If the security property is not set,
     * the provider is assumed to have alternative means
     * for obtaining authentication information.
     *
     * <p>
     *  登录到此提供程序。
     * 
     *  <p>供应商依靠{@code CallbackHandler}从呼叫者(例如PIN)获取认证信息。
     * 如果调用者将{@code null}处理程序传递给此方法,则提供程序将使用{@code setCallbackHandler}方法中设置的处理程序。
     * 如果在该方法中未设置处理程序,则提供程序会查询<a> auth.login.defaultCallbackHandler </i>安全属性以获取默认处理程序实现的完全限定类名。
     * 如果未设置安全属性,则假定提供者具有用于获得认证信息的替代装置。
     * 
     * 
     * @param subject the {@code Subject} which may contain
     *          principals/credentials used for authentication,
     *          or may be populated with additional principals/credentials
     *          after successful authentication has completed.
     *          This parameter may be {@code null}.
     * @param handler the {@code CallbackHandler} used by
     *          this provider to obtain authentication information
     *          from the caller, which may be {@code null}
     *
     * @exception LoginException if the login operation fails
     * @exception SecurityException if the caller does not pass a
     *  security check for
     *  {@code SecurityPermission("authProvider.name")},
     *  where {@code name} is the value returned by
     *  this provider's {@code getName} method
     */
    public abstract void login(Subject subject, CallbackHandler handler)
        throws LoginException;

    /**
     * Log out from this provider.
     *
     * <p>
     *  从此提供程序注销。
     * 
     * 
     * @exception LoginException if the logout operation fails
     * @exception SecurityException if the caller does not pass a
     *  security check for
     *  {@code SecurityPermission("authProvider.name")},
     *  where {@code name} is the value returned by
     *  this provider's {@code getName} method
     */
    public abstract void logout() throws LoginException;

    /**
     * Set a {@code CallbackHandler}.
     *
     * <p> The provider uses this handler if one is not passed to the
     * {@code login} method.  The provider also uses this handler
     * if it invokes {@code login} on behalf of callers.
     * In either case if a handler is not set via this method,
     * the provider queries the
     * <i>auth.login.defaultCallbackHandler</i> security property
     * for the fully qualified class name of a default handler implementation.
     * If the security property is not set,
     * the provider is assumed to have alternative means
     * for obtaining authentication information.
     *
     * <p>
     *  设置{@code CallbackHandler}。
     * 
     * <p>如果没有将传递给{@code login}方法,提供程序将使用此处理程序。如果提供者代表调用者调用{@code login},那么提供者也使用这个处理程序。
     * 
     * @param handler a {@code CallbackHandler} for obtaining
     *          authentication information, which may be {@code null}
     *
     * @exception SecurityException if the caller does not pass a
     *  security check for
     *  {@code SecurityPermission("authProvider.name")},
     *  where {@code name} is the value returned by
     *  this provider's {@code getName} method
     */
    public abstract void setCallbackHandler(CallbackHandler handler);
}
