/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth.spi;

import javax.security.auth.Subject;
import javax.security.auth.AuthPermission;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import java.util.Map;

/**
 * <p> {@code LoginModule} describes the interface
 * implemented by authentication technology providers.  LoginModules
 * are plugged in under applications to provide a particular type of
 * authentication.
 *
 * <p> While applications write to the {@code LoginContext} API,
 * authentication technology providers implement the
 * {@code LoginModule} interface.
 * A {@code Configuration} specifies the LoginModule(s)
 * to be used with a particular login application.  Therefore different
 * LoginModules can be plugged in under the application without
 * requiring any modifications to the application itself.
 *
 * <p> The {@code LoginContext} is responsible for reading the
 * {@code Configuration} and instantiating the appropriate
 * LoginModules.  Each {@code LoginModule} is initialized with
 * a {@code Subject}, a {@code CallbackHandler}, shared
 * {@code LoginModule} state, and LoginModule-specific options.
 *
 * The {@code Subject} represents the
 * {@code Subject} currently being authenticated and is updated
 * with relevant Credentials if authentication succeeds.
 * LoginModules use the {@code CallbackHandler} to
 * communicate with users.  The {@code CallbackHandler} may be
 * used to prompt for usernames and passwords, for example.
 * Note that the {@code CallbackHandler} may be null.  LoginModules
 * which absolutely require a {@code CallbackHandler} to authenticate
 * the {@code Subject} may throw a {@code LoginException}.
 * LoginModules optionally use the shared state to share information
 * or data among themselves.
 *
 * <p> The LoginModule-specific options represent the options
 * configured for this {@code LoginModule} by an administrator or user
 * in the login {@code Configuration}.
 * The options are defined by the {@code LoginModule} itself
 * and control the behavior within it.  For example, a
 * {@code LoginModule} may define options to support debugging/testing
 * capabilities.  Options are defined using a key-value syntax,
 * such as <i>debug=true</i>.  The {@code LoginModule}
 * stores the options as a {@code Map} so that the values may
 * be retrieved using the key.  Note that there is no limit to the number
 * of options a {@code LoginModule} chooses to define.
 *
 * <p> The calling application sees the authentication process as a single
 * operation.  However, the authentication process within the
 * {@code LoginModule} proceeds in two distinct phases.
 * In the first phase, the LoginModule's
 * {@code login} method gets invoked by the LoginContext's
 * {@code login} method.  The {@code login}
 * method for the {@code LoginModule} then performs
 * the actual authentication (prompt for and verify a password for example)
 * and saves its authentication status as private state
 * information.  Once finished, the LoginModule's {@code login}
 * method either returns {@code true} (if it succeeded) or
 * {@code false} (if it should be ignored), or throws a
 * {@code LoginException} to specify a failure.
 * In the failure case, the {@code LoginModule} must not retry the
 * authentication or introduce delays.  The responsibility of such tasks
 * belongs to the application.  If the application attempts to retry
 * the authentication, the LoginModule's {@code login} method will be
 * called again.
 *
 * <p> In the second phase, if the LoginContext's overall authentication
 * succeeded (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
 * LoginModules succeeded), then the {@code commit}
 * method for the {@code LoginModule} gets invoked.
 * The {@code commit} method for a {@code LoginModule} checks its
 * privately saved state to see if its own authentication succeeded.
 * If the overall {@code LoginContext} authentication succeeded
 * and the LoginModule's own authentication succeeded, then the
 * {@code commit} method associates the relevant
 * Principals (authenticated identities) and Credentials (authentication data
 * such as cryptographic keys) with the {@code Subject}
 * located within the {@code LoginModule}.
 *
 * <p> If the LoginContext's overall authentication failed (the relevant
 * REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules did not succeed),
 * then the {@code abort} method for each {@code LoginModule}
 * gets invoked.  In this case, the {@code LoginModule} removes/destroys
 * any authentication state originally saved.
 *
 * <p> Logging out a {@code Subject} involves only one phase.
 * The {@code LoginContext} invokes the LoginModule's {@code logout}
 * method.  The {@code logout} method for the {@code LoginModule}
 * then performs the logout procedures, such as removing Principals or
 * Credentials from the {@code Subject} or logging session information.
 *
 * <p> A {@code LoginModule} implementation must have a constructor with
 * no arguments.  This allows classes which load the {@code LoginModule}
 * to instantiate it.
 *
 * <p>
 *  <p> {@code LoginModule}描述了由身份验证技术提供商实现的界面。 LoginModule在应用程序下插入以提供特定类型的身份验证。
 * 
 *  <p>当应用程序写入{@code LoginContext} API时,身份验证技术提供者实现{@code LoginModule}接口。
 *  {@code Configuration}指定要与特定登录应用程序一起使用的LoginModule。因此,可以在应用程序下插入不同的LoginModule,而不需要对应用程序本身进行任何修改。
 * 
 *  <p> {@code LoginContext}负责读取{@code Configuration}并实例化相应的LoginModules。
 * 每个{@code LoginModule}都使用{@code Subject},{@code CallbackHandler},共享的{@code LoginModule}状态和LoginModule特
 * 定的选项初始化。
 *  <p> {@code LoginContext}负责读取{@code Configuration}并实例化相应的LoginModules。
 * 
 * {@code Subject}代表目前正在验证的{@code Subject},如果验证成功,则会更新相关的凭证。
 *  LoginModules使用{@code CallbackHandler}与用户通信。例如,{@code CallbackHandler}可用于提示用户名和密码。
 * 请注意,{@code CallbackHandler}可能为null。
 *  LoginModules绝对需要{@code CallbackHandler}来验证{@code Subject},可能会抛出{@code LoginException}。
 *  LoginModule可选地使用共享状态在它们之间共享信息或数据。
 * 
 *  <p> LoginModule特定的选项表示管理员或用户在登录{@code Configuration}中为此{@code LoginModule}配置的选项。
 * 这些选项由{@code LoginModule}本身定义,并控制其中的行为。例如,{@code LoginModule}可以定义支持调试/测试功能的选项。
 * 选项使用键值语法来定义,例如<i> debug = true </i>。 {@code LoginModule}将选项存储为{@code Map},以便可以使用键检索值。
 * 注意,{@code LoginModule}选择定义的选项数量没有限制。
 * 
 * <p>调用应用程序将认证过程视为单个操作。然而,{@code LoginModule}中的认证过程在两个不同的阶段进行。
 * 在第一阶段,LoginModule的{@code login}方法由LoginContext的{@code login}方法调用。
 *  {@code LoginModule}的{@code login}方法然后执行实际认证(例如提示并验证密码),并将其认证状态保存为私有状态信息。
 * 一旦完成,LoginModule的{@code login}方法返回{@code true}(如果成功)或{@code false}(如果它应该被忽略),或者抛出一个{@code LoginException}
 * 来指定失败。
 *  {@code LoginModule}的{@code login}方法然后执行实际认证(例如提示并验证密码),并将其认证状态保存为私有状态信息。
 * 在失败的情况下,{@code LoginModule}不得重试身份验证或引入延迟。这些任务的责任属于应用程序。
 * 如果应用程序尝试重试身份验证,LoginModule的{@code login}方法将再次调用。
 * 
 * <p>在第二阶段,如果LoginContext的整体认证成功(相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules成功),那么将调用{@code LoginModule}
 * 的{@code commit}方法。
 *  {@code LoginModule}的{@code commit}方法检查其私有保存的状态,以查看其自身的身份验证是否成功。
 * 如果整个{@code LoginContext}身份验证成功并且LoginModule自己的身份验证成功,那么{@code commit}方法将相关主体(已验证身份)和Credentials(身份验证数
 * 据,如加密密钥)与{@code Subject}位于{@code LoginModule}中。
 *  {@code LoginModule}的{@code commit}方法检查其私有保存的状态,以查看其自身的身份验证是否成功。
 * 
 *  <p>如果LoginContext的整体身份验证失败(相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules没有成功),则会调用每个{@code LoginModule}
 * 的{@code abort}方法。
 * 在这种情况下,{@code LoginModule}删除/销毁最初保存的任何身份验证状态。
 * 
 *  <p>注销{@code主题}只涉及一个阶段。 {@code LoginContext}调用LoginModule的{@code logout}方法。
 *  {@code LoginModule}的{@code logout}方法然后执行注销过程,例如从{@code Subject}中删除Principals或Credentials或记录会话信息。
 * 
 * <p> {@code LoginModule}实现必须有一个没有参数的构造函数。这允许加载{@code LoginModule}的类来实例化它。
 * 
 * @see javax.security.auth.login.LoginContext
 * @see javax.security.auth.login.Configuration
 */
public interface LoginModule {

    /**
     * Initialize this LoginModule.
     *
     * <p> This method is called by the {@code LoginContext}
     * after this {@code LoginModule} has been instantiated.
     * The purpose of this method is to initialize this
     * {@code LoginModule} with the relevant information.
     * If this {@code LoginModule} does not understand
     * any of the data stored in {@code sharedState} or
     * {@code options} parameters, they can be ignored.
     *
     * <p>
     *
     * <p>
     * 
     * 
     * @param subject the {@code Subject} to be authenticated. <p>
     *
     * @param callbackHandler a {@code CallbackHandler} for communicating
     *                  with the end user (prompting for usernames and
     *                  passwords, for example). <p>
     *
     * @param sharedState state shared with other configured LoginModules. <p>
     *
     * @param options options specified in the login
     *                  {@code Configuration} for this particular
     *                  {@code LoginModule}.
     */
    void initialize(Subject subject, CallbackHandler callbackHandler,
                    Map<String,?> sharedState,
                    Map<String,?> options);

    /**
     * Method to authenticate a {@code Subject} (phase 1).
     *
     * <p> The implementation of this method authenticates
     * a {@code Subject}.  For example, it may prompt for
     * {@code Subject} information such
     * as a username and password and then attempt to verify the password.
     * This method saves the result of the authentication attempt
     * as private state within the LoginModule.
     *
     * <p>
     *
     * <p>
     *  初始化此LoginModule。
     * 
     *  <p>此方法在{@code LoginModule}实例化之后由{@code LoginContext}调用。此方法的目的是使用相关信息初始化此{@code LoginModule}。
     * 如果{@code LoginModule}不理解{@code sharedState}或{@code options}参数中存储的任何数据,则可以忽略它们。
     * 
     * <p>
     * 
     * 
     * @exception LoginException if the authentication fails
     *
     * @return true if the authentication succeeded, or false if this
     *                  {@code LoginModule} should be ignored.
     */
    boolean login() throws LoginException;

    /**
     * Method to commit the authentication process (phase 2).
     *
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * {@code login} method), then this method associates relevant
     * Principals and Credentials with the {@code Subject} located in the
     * {@code LoginModule}.  If this LoginModule's own
     * authentication attempted failed, then this method removes/destroys
     * any state that was originally saved.
     *
     * <p>
     *
     * <p>
     *  验证{@code Subject}(阶段1)的方法。
     * 
     *  <p>此方法的实现验证{@code主题}。例如,它可能提示输入{@code Subject}信息,例如用户名和密码,然后尝试验证密码。
     * 此方法将认证尝试的结果作为私有状态保存在LoginModule中。
     * 
     * <p>
     * 
     * 
     * @exception LoginException if the commit fails
     *
     * @return true if this method succeeded, or false if this
     *                  {@code LoginModule} should be ignored.
     */
    boolean commit() throws LoginException;

    /**
     * Method to abort the authentication process (phase 2).
     *
     * <p> This method is called if the LoginContext's
     * overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * {@code login} method), then this method cleans up any state
     * that was originally saved.
     *
     * <p>
     *
     * <p>
     *  提交认证过程的方法(阶段2)。
     * 
     *  <p>如果LoginContext的整体认证成功(相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules成功),则调用此方法。
     * 
     * <p>如果此LoginModule自己的身份验证尝试成功(通过检索由{@code login}方法保存的私有状态进行检查),则此方法将相关Principals和Credentials与位于{@code LoginModule中的{@code Subject}
     *  }。
     * 如果此LoginModule自己的身份验证尝试失败,则此方法将删除/销毁最初保存的任何状态。
     * 
     * <p>
     * 
     * 
     * @exception LoginException if the abort fails
     *
     * @return true if this method succeeded, or false if this
     *                  {@code LoginModule} should be ignored.
     */
    boolean abort() throws LoginException;

    /**
     * Method which logs out a {@code Subject}.
     *
     * <p>An implementation of this method might remove/destroy a Subject's
     * Principals and Credentials.
     *
     * <p>
     *
     * <p>
     *  中止认证过程的方法(阶段2)。
     * 
     *  <p>如果LoginContext的整体身份验证失败,则会调用此方法。 (相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules没有成功)。
     * 
     *  <p>如果此LoginModule自己的身份验证尝试成功(通过检索{@code login}方法保存的私有状态进行检查),则此方法将清除最初保存的任何状态。
     * 
     * <p>
     * 
     * 
     * @exception LoginException if the logout fails
     *
     * @return true if this method succeeded, or false if this
     *                  {@code LoginModule} should be ignored.
     */
    boolean logout() throws LoginException;
}
