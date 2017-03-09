/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2008, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.remote.security;

import com.sun.jmx.mbeanserver.GetPropertyAction;
import com.sun.jmx.mbeanserver.Util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import javax.management.remote.JMXPrincipal;

import com.sun.jmx.remote.util.ClassLogger;
import com.sun.jmx.remote.util.EnvHelp;
import sun.management.jmxremote.ConnectorBootstrap;

/**
 * This {@link LoginModule} performs file-based authentication.
 *
 * <p> A supplied username and password is verified against the
 * corresponding user credentials stored in a designated password file.
 * If successful then a new {@link JMXPrincipal} is created with the
 * user's name and it is associated with the current {@link Subject}.
 * Such principals may be identified and granted management privileges in
 * the access control file for JMX remote management or in a Java security
 * policy.
 *
 * <p> The password file comprises a list of key-value pairs as specified in
 * {@link Properties}. The key represents a user's name and the value is its
 * associated cleartext password. By default, the following password file is
 * used:
 * <pre>
 *     ${java.home}/lib/management/jmxremote.password
 * </pre>
 * A different password file can be specified via the <code>passwordFile</code>
 * configuration option.
 *
 * <p> This module recognizes the following <code>Configuration</code> options:
 * <dl>
 * <dt> <code>passwordFile</code> </dt>
 * <dd> the path to an alternative password file. It is used instead of
 *      the default password file.</dd>
 *
 * <dt> <code>useFirstPass</code> </dt>
 * <dd> if <code>true</code>, this module retrieves the username and password
 *      from the module's shared state, using "javax.security.auth.login.name"
 *      and "javax.security.auth.login.password" as the respective keys. The
 *      retrieved values are used for authentication. If authentication fails,
 *      no attempt for a retry is made, and the failure is reported back to
 *      the calling application.</dd>
 *
 * <dt> <code>tryFirstPass</code> </dt>
 * <dd> if <code>true</code>, this module retrieves the username and password
 *      from the module's shared state, using "javax.security.auth.login.name"
 *       and "javax.security.auth.login.password" as the respective keys.  The
 *      retrieved values are used for authentication. If authentication fails,
 *      the module uses the CallbackHandler to retrieve a new username and
 *      password, and another attempt to authenticate is made. If the
 *      authentication fails, the failure is reported back to the calling
 *      application.</dd>
 *
 * <dt> <code>storePass</code> </dt>
 * <dd> if <code>true</code>, this module stores the username and password
 *      obtained from the CallbackHandler in the module's shared state, using
 *      "javax.security.auth.login.name" and
 *      "javax.security.auth.login.password" as the respective keys.  This is
 *      not performed if existing values already exist for the username and
 *      password in the shared state, or if authentication fails.</dd>
 *
 * <dt> <code>clearPass</code> </dt>
 * <dd> if <code>true</code>, this module clears the username and password
 *      stored in the module's shared state after both phases of authentication
 *      (login and commit) have completed.</dd>
 * </dl>
 * <p>
 *  此{@link LoginModule}执行基于文件的身份验证。
 * 
 *  <p>提供的用户名和密码与存储在指定密码文件中的相应用户凭证进行验证。如果成功,则使用用户名创建新的{@link JMXPrincipal},并将其与当前{@link主题}相关联。
 * 可以在用于JMX远程管理的访问控制文件中或在Java安全策略中识别和授予这样的主体管理特权。
 * 
 *  <p>密码文件包含在{@link属性}中指定的键值对列表。键表示用户的名称,值是其关联的明文密码。默认情况下,使用以下密码文件：
 * <pre>
 *  $ {java.home} /lib/management/jmxremote.password
 * </pre>
 *  可以通过<code> passwordFile </code>配置选项指定不同的密码文件。
 * 
 *  <p>此模块识别以下<code>配置</code>选项：
 * <dl>
 *  <dt> <code> passwordFile </code> </dt> <dd>替代密码文件的路径。它用于代替默认密码文件。</dd>
 * 
 * <dt> <code> useFirstPass </code> </dt> <dd>如果<code> true </code>,此模块从模块的共享状态检索用户名和密码,使用"javax.securit
 * y.auth.login .name"和"javax.security.auth.login.password"作为相应的键。
 * 检索的值用于认证。如果身份验证失败,则不会尝试重试,并将该失败报告给调用应用程序。</dd>。
 * 
 *  <dt> <code> tryFirstPass </code> </dt> <dd>如果<code> true </code>,此模块从模块的共享状态检索用户名和密码,使用"javax.securi
 * ty.auth.login .name"和"javax.security.auth.login.password"作为相应的键。
 * 检索的值用于认证。如果认证失败,模块使用CallbackHandler检索新的用户名和密码,并进行另一次尝试认证。如果认证失败,则将故障报告回调用的应用程序。</dd>。
 * 
 *  <dt> <code> storePass </code> </dt> <dd>如果<code> true </code>,此模块将从模块的共享状态中存储从CallbackHandler获取的用户名和
 * 密码,使用"javax.security .auth.login.name"和"javax.security.auth.login.password"作为相应的键。
 * 如果共享状态下的用户名和密码已存在,或者认证失败,则不会执行此操作。</dd>。
 * 
 * <dt> <code> clearPass </code> </dt> <dd>如果<code> true </code>,此模块清除存储在模块的共享状态中的用户名和密码, )已完成。</dd>
 * </dl>
 */
public class FileLoginModule implements LoginModule {

    // Location of the default password file
    private static final String DEFAULT_PASSWORD_FILE_NAME =
        AccessController.doPrivileged(new GetPropertyAction("java.home")) +
        File.separatorChar + "lib" +
        File.separatorChar + "management" + File.separatorChar +
        ConnectorBootstrap.DefaultValues.PASSWORD_FILE_NAME;

    // Key to retrieve the stored username
    private static final String USERNAME_KEY =
        "javax.security.auth.login.name";

    // Key to retrieve the stored password
    private static final String PASSWORD_KEY =
        "javax.security.auth.login.password";

    // Log messages
    private static final ClassLogger logger =
        new ClassLogger("javax.management.remote.misc", "FileLoginModule");

    // Configurable options
    private boolean useFirstPass = false;
    private boolean tryFirstPass = false;
    private boolean storePass = false;
    private boolean clearPass = false;

    // Authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    // Supplied username and password
    private String username;
    private char[] password;
    private JMXPrincipal user;

    // Initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, Object> sharedState;
    private Map<String, ?> options;
    private String passwordFile;
    private String passwordFileDisplayName;
    private boolean userSuppliedPasswordFile;
    private boolean hasJavaHomePermission;
    private Properties userCredentials;

    /**
     * Initialize this <code>LoginModule</code>.
     *
     * <p>
     *  初始化此<code> LoginModule </code>。
     * 
     * 
     * @param subject the <code>Subject</code> to be authenticated.
     * @param callbackHandler a <code>CallbackHandler</code> to acquire the
     *                  user's name and password.
     * @param sharedState shared <code>LoginModule</code> state.
     * @param options options specified in the login
     *                  <code>Configuration</code> for this particular
     *                  <code>LoginModule</code>.
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map<String,?> sharedState,
                           Map<String,?> options)
    {

        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = Util.cast(sharedState);
        this.options = options;

        // initialize any configured options
        tryFirstPass =
                "true".equalsIgnoreCase((String)options.get("tryFirstPass"));
        useFirstPass =
                "true".equalsIgnoreCase((String)options.get("useFirstPass"));
        storePass =
                "true".equalsIgnoreCase((String)options.get("storePass"));
        clearPass =
                "true".equalsIgnoreCase((String)options.get("clearPass"));

        passwordFile = (String)options.get("passwordFile");
        passwordFileDisplayName = passwordFile;
        userSuppliedPasswordFile = true;

        // set the location of the password file
        if (passwordFile == null) {
            passwordFile = DEFAULT_PASSWORD_FILE_NAME;
            userSuppliedPasswordFile = false;
            try {
                System.getProperty("java.home");
                hasJavaHomePermission = true;
                passwordFileDisplayName = passwordFile;
            } catch (SecurityException e) {
                hasJavaHomePermission = false;
                passwordFileDisplayName =
                        ConnectorBootstrap.DefaultValues.PASSWORD_FILE_NAME;
            }
        }
    }

    /**
     * Begin user authentication (Authentication Phase 1).
     *
     * <p> Acquire the user's name and password and verify them against
     * the corresponding credentials from the password file.
     *
     * <p>
     *  开始用户身份验证(身份验证阶段1)。
     * 
     *  <p>获取用户的名称和密码,并根据密码文件中的相应凭据进行验证。
     * 
     * 
     * @return true always, since this <code>LoginModule</code>
     *          should not be ignored.
     * @exception FailedLoginException if the authentication fails.
     * @exception LoginException if this <code>LoginModule</code>
     *          is unable to perform the authentication.
     */
    public boolean login() throws LoginException {

        try {
            loadPasswordFile();
        } catch (IOException ioe) {
            LoginException le = new LoginException(
                    "Error: unable to load the password file: " +
                    passwordFileDisplayName);
            throw EnvHelp.initCause(le, ioe);
        }

        if (userCredentials == null) {
            throw new LoginException
                ("Error: unable to locate the users' credentials.");
        }

        if (logger.debugOn()) {
            logger.debug("login",
                    "Using password file: " + passwordFileDisplayName);
        }

        // attempt the authentication
        if (tryFirstPass) {

            try {
                // attempt the authentication by getting the
                // username and password from shared state
                attemptAuthentication(true);

                // authentication succeeded
                succeeded = true;
                if (logger.debugOn()) {
                    logger.debug("login",
                        "Authentication using cached password has succeeded");
                }
                return true;

            } catch (LoginException le) {
                // authentication failed -- try again below by prompting
                cleanState();
                logger.debug("login",
                    "Authentication using cached password has failed");
            }

        } else if (useFirstPass) {

            try {
                // attempt the authentication by getting the
                // username and password from shared state
                attemptAuthentication(true);

                // authentication succeeded
                succeeded = true;
                if (logger.debugOn()) {
                    logger.debug("login",
                        "Authentication using cached password has succeeded");
                }
                return true;

            } catch (LoginException le) {
                // authentication failed
                cleanState();
                logger.debug("login",
                    "Authentication using cached password has failed");

                throw le;
            }
        }

        if (logger.debugOn()) {
            logger.debug("login", "Acquiring password");
        }

        // attempt the authentication using the supplied username and password
        try {
            attemptAuthentication(false);

            // authentication succeeded
            succeeded = true;
            if (logger.debugOn()) {
                logger.debug("login", "Authentication has succeeded");
            }
            return true;

        } catch (LoginException le) {
            cleanState();
            logger.debug("login", "Authentication has failed");

            throw le;
        }
    }

    /**
     * Complete user authentication (Authentication Phase 2).
     *
     * <p> This method is called if the LoginContext's
     * overall authentication has succeeded
     * (all the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
     * LoginModules have succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates a
     * <code>JMXPrincipal</code> with the <code>Subject</code> located in the
     * <code>LoginModule</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *  完成用户认证(认证阶段2)。
     * 
     *  <p>如果LoginContext的整体认证已成功(所有相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModule都已成功),则调用此方法。
     * 
     *  <p>如果此LoginModule自己的身份验证尝试成功(通过检索由<code> login </code>方法保存的私有状态进行检查),则此方法将<code> JMXPrincipal </code>
     * 与<code> Subject </code>位于<code> LoginModule </code>中。
     * 如果此LoginModule自己的身份验证尝试失败,则此方法将删除最初保存的任何状态。
     * 
     * 
     * @exception LoginException if the commit fails
     * @return true if this LoginModule's own login and commit
     *          attempts succeeded, or false otherwise.
     */
    public boolean commit() throws LoginException {

        if (succeeded == false) {
            return false;
        } else {
            if (subject.isReadOnly()) {
                cleanState();
                throw new LoginException("Subject is read-only");
            }
            // add Principals to the Subject
            if (!subject.getPrincipals().contains(user)) {
                subject.getPrincipals().add(user);
            }

            if (logger.debugOn()) {
                logger.debug("commit",
                    "Authentication has completed successfully");
            }
        }
        // in any case, clean out state
        cleanState();
        commitSucceeded = true;
        return true;
    }

    /**
     * Abort user authentication (Authentication Phase 2).
     *
     * <p> This method is called if the LoginContext's overall authentication
     * failed (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
     * LoginModules did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> and <code>commit</code> methods),
     * then this method cleans up any state that was originally saved.
     *
     * <p>
     *  中止用户认证(认证阶段2)。
     * 
     *  <p>如果LoginContext的整体身份验证失败(相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules未成功),则调用此方法。
     * 
     * <p>如果此LoginModule自己的身份验证尝试成功(通过检索由<code> login </code>和<code> commit </code>方法保存的私有状态进行检查),则此方法将清除原来保
     * 存。
     * 
     * 
     * @exception LoginException if the abort fails.
     * @return false if this LoginModule's own login and/or commit attempts
     *          failed, and true otherwise.
     */
    public boolean abort() throws LoginException {

        if (logger.debugOn()) {
            logger.debug("abort",
                "Authentication has not completed successfully");
        }

        if (succeeded == false) {
            return false;
        } else if (succeeded == true && commitSucceeded == false) {

            // Clean out state
            succeeded = false;
            cleanState();
            user = null;
        } else {
            // overall authentication succeeded and commit succeeded,
            // but someone else's commit failed
            logout();
        }
        return true;
    }

    /**
     * Logout a user.
     *
     * <p> This method removes the Principals
     * that were added by the <code>commit</code> method.
     *
     * <p>
     *  注销用户。
     * 
     *  <p>此方法删除由<code> commit </code>方法添加的Principal。
     * 
     * 
     * @exception LoginException if the logout fails.
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     */
    public boolean logout() throws LoginException {
        if (subject.isReadOnly()) {
            cleanState();
            throw new LoginException ("Subject is read-only");
        }
        subject.getPrincipals().remove(user);

        // clean out state
        cleanState();
        succeeded = false;
        commitSucceeded = false;
        user = null;

        if (logger.debugOn()) {
            logger.debug("logout", "Subject is being logged out");
        }

        return true;
    }

    /**
     * Attempt authentication
     *
     * <p>
     *  尝试认证
     * 
     * 
     * @param usePasswdFromSharedState a flag to tell this method whether
     *          to retrieve the password from the sharedState.
     */
    @SuppressWarnings("unchecked")  // sharedState used as Map<String,Object>
    private void attemptAuthentication(boolean usePasswdFromSharedState)
        throws LoginException {

        // get the username and password
        getUsernamePassword(usePasswdFromSharedState);

        String localPassword;

        // userCredentials is initialized in login()
        if (((localPassword = userCredentials.getProperty(username)) == null) ||
            (! localPassword.equals(new String(password)))) {

            // username not found or passwords do not match
            if (logger.debugOn()) {
                logger.debug("login", "Invalid username or password");
            }
            throw new FailedLoginException("Invalid username or password");
        }

        // Save the username and password in the shared state
        // only if authentication succeeded
        if (storePass &&
            !sharedState.containsKey(USERNAME_KEY) &&
            !sharedState.containsKey(PASSWORD_KEY)) {
            sharedState.put(USERNAME_KEY, username);
            sharedState.put(PASSWORD_KEY, password);
        }

        // Create a new user principal
        user = new JMXPrincipal(username);

        if (logger.debugOn()) {
            logger.debug("login",
                "User '" + username + "' successfully validated");
        }
    }

    /*
     * Read the password file.
     * <p>
     *  读取密码文件。
     * 
     */
    private void loadPasswordFile() throws IOException {
        FileInputStream fis;
        try {
            fis = new FileInputStream(passwordFile);
        } catch (SecurityException e) {
            if (userSuppliedPasswordFile || hasJavaHomePermission) {
                throw e;
            } else {
                final FilePermission fp =
                        new FilePermission(passwordFileDisplayName, "read");
                AccessControlException ace = new AccessControlException(
                        "access denied " + fp.toString());
                ace.setStackTrace(e.getStackTrace());
                throw ace;
            }
        }
        try {
            final BufferedInputStream bis = new BufferedInputStream(fis);
            try {
                userCredentials = new Properties();
                userCredentials.load(bis);
            } finally {
                bis.close();
            }
        } finally {
            fis.close();
        }
    }

    /**
     * Get the username and password.
     * This method does not return any value.
     * Instead, it sets global name and password variables.
     *
     * <p> Also note that this method will set the username and password
     * values in the shared state in case subsequent LoginModules
     * want to use them via use/tryFirstPass.
     *
     * <p>
     *  获取用户名和密码。此方法不返回任何值。相反,它设置全局名称和密码变量。
     * 
     *  <p>另请注意,此方法将在共享状态下设置用户名和密码值,以防后续LoginModules想通过use / tryFirstPass使用它们。
     * 
     * 
     * @param usePasswdFromSharedState boolean that tells this method whether
     *          to retrieve the password from the sharedState.
     */
    private void getUsernamePassword(boolean usePasswdFromSharedState)
        throws LoginException {

        if (usePasswdFromSharedState) {
            // use the password saved by the first module in the stack
            username = (String)sharedState.get(USERNAME_KEY);
            password = (char[])sharedState.get(PASSWORD_KEY);
            return;
        }

        // acquire username and password
        if (callbackHandler == null)
            throw new LoginException("Error: no CallbackHandler available " +
                "to garner authentication information from the user");

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("username");
        callbacks[1] = new PasswordCallback("password", false);

        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback)callbacks[0]).getName();
            char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
            password = new char[tmpPassword.length];
            System.arraycopy(tmpPassword, 0,
                                password, 0, tmpPassword.length);
            ((PasswordCallback)callbacks[1]).clearPassword();

        } catch (IOException ioe) {
            LoginException le = new LoginException(ioe.toString());
            throw EnvHelp.initCause(le, ioe);
        } catch (UnsupportedCallbackException uce) {
            LoginException le = new LoginException(
                                    "Error: " + uce.getCallback().toString() +
                                    " not available to garner authentication " +
                                    "information from the user");
            throw EnvHelp.initCause(le, uce);
        }
    }

    /**
     * Clean out state because of a failed authentication attempt
     * <p>
     */
    private void cleanState() {
        username = null;
        if (password != null) {
            Arrays.fill(password, ' ');
            password = null;
        }

        if (clearPass) {
            sharedState.remove(USERNAME_KEY);
            sharedState.remove(PASSWORD_KEY);
        }
    }
}
