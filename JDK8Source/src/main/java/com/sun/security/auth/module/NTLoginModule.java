/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.auth.module;

import java.util.*;
import java.io.IOException;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import java.security.Principal;
import com.sun.security.auth.NTUserPrincipal;
import com.sun.security.auth.NTSidUserPrincipal;
import com.sun.security.auth.NTDomainPrincipal;
import com.sun.security.auth.NTSidDomainPrincipal;
import com.sun.security.auth.NTSidPrimaryGroupPrincipal;
import com.sun.security.auth.NTSidGroupPrincipal;
import com.sun.security.auth.NTNumericCredential;

/**
 * <p> This <code>LoginModule</code>
 * renders a user's NT security information as some number of
 * <code>Principal</code>s
 * and associates them with a <code>Subject</code>.
 *
 * <p> This LoginModule recognizes the debug option.
 * If set to true in the login Configuration,
 * debug messages will be output to the output stream, System.out.
 *
 * <p> This LoginModule also recognizes the debugNative option.
 * If set to true in the login Configuration,
 * debug messages from the native component of the module
 * will be output to the output stream, System.out.
 *
 * <p>
 *  <p>此<code> LoginModule </code>将用户的NT安全信息作为一定数量的<code> Principal </code>来呈现,并将它们与<code> Subject </code>
 * 关联。
 * 
 *  <p>此LoginModule识别调试选项。如果在登录配置中设置为true,则调试消息将输出到输出流System.out。
 * 
 *  <p>此LoginModule还识别debugNative选项。如果在登录配置中设置为true,则来自模块的本地组件的调试消息将输出到输出流System.out。
 * 
 * 
 * @see javax.security.auth.spi.LoginModule
 */
@jdk.Exported
public class NTLoginModule implements LoginModule {

    private NTSystem ntSystem;

    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    // configurable option
    private boolean debug = false;
    private boolean debugNative = false;

    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    private NTUserPrincipal userPrincipal;              // user name
    private NTSidUserPrincipal userSID;                 // user SID
    private NTDomainPrincipal userDomain;               // user domain
    private NTSidDomainPrincipal domainSID;             // domain SID
    private NTSidPrimaryGroupPrincipal primaryGroup;    // primary group
    private NTSidGroupPrincipal groups[];               // supplementary groups
    private NTNumericCredential iToken;                 // impersonation token

    /**
     * Initialize this <code>LoginModule</code>.
     *
     * <p>
     *
     * <p>
     *  初始化此<code> LoginModule </code>。
     * 
     * <p>
     * 
     * 
     * @param subject the <code>Subject</code> to be authenticated. <p>
     *
     * @param callbackHandler a <code>CallbackHandler</code> for communicating
     *          with the end user (prompting for usernames and
     *          passwords, for example). This particular LoginModule only
     *          extracts the underlying NT system information, so this
     *          parameter is ignored.<p>
     *
     * @param sharedState shared <code>LoginModule</code> state. <p>
     *
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
        this.sharedState = sharedState;
        this.options = options;

        // initialize any configured options
        debug = "true".equalsIgnoreCase((String)options.get("debug"));
        debugNative="true".equalsIgnoreCase((String)options.get("debugNative"));

        if (debugNative == true) {
            debug = true;
        }
    }

    /**
     * Import underlying NT system identity information.
     *
     * <p>
     *
     * <p>
     *  导入底层NT系统标识信息。
     * 
     * <p>
     * 
     * 
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     *
     * @exception FailedLoginException if the authentication fails. <p>
     *
     * @exception LoginException if this <code>LoginModule</code>
     *          is unable to perform the authentication.
     */
    public boolean login() throws LoginException {

        succeeded = false; // Indicate not yet successful

        ntSystem = new NTSystem(debugNative);
        if (ntSystem == null) {
            if (debug) {
                System.out.println("\t\t[NTLoginModule] " +
                                   "Failed in NT login");
            }
            throw new FailedLoginException
                ("Failed in attempt to import the " +
                 "underlying NT system identity information");
        }

        if (ntSystem.getName() == null) {
            throw new FailedLoginException
                ("Failed in attempt to import the " +
                 "underlying NT system identity information");
        }
        userPrincipal = new NTUserPrincipal(ntSystem.getName());
        if (debug) {
            System.out.println("\t\t[NTLoginModule] " +
                               "succeeded importing info: ");
            System.out.println("\t\t\tuser name = " +
                userPrincipal.getName());
        }

        if (ntSystem.getUserSID() != null) {
            userSID = new NTSidUserPrincipal(ntSystem.getUserSID());
            if (debug) {
                System.out.println("\t\t\tuser SID = " +
                        userSID.getName());
            }
        }
        if (ntSystem.getDomain() != null) {
            userDomain = new NTDomainPrincipal(ntSystem.getDomain());
            if (debug) {
                System.out.println("\t\t\tuser domain = " +
                        userDomain.getName());
            }
        }
        if (ntSystem.getDomainSID() != null) {
            domainSID =
                new NTSidDomainPrincipal(ntSystem.getDomainSID());
            if (debug) {
                System.out.println("\t\t\tuser domain SID = " +
                        domainSID.getName());
            }
        }
        if (ntSystem.getPrimaryGroupID() != null) {
            primaryGroup =
                new NTSidPrimaryGroupPrincipal(ntSystem.getPrimaryGroupID());
            if (debug) {
                System.out.println("\t\t\tuser primary group = " +
                        primaryGroup.getName());
            }
        }
        if (ntSystem.getGroupIDs() != null &&
            ntSystem.getGroupIDs().length > 0) {

            String groupSIDs[] = ntSystem.getGroupIDs();
            groups = new NTSidGroupPrincipal[groupSIDs.length];
            for (int i = 0; i < groupSIDs.length; i++) {
                groups[i] = new NTSidGroupPrincipal(groupSIDs[i]);
                if (debug) {
                    System.out.println("\t\t\tuser group = " +
                        groups[i].getName());
                }
            }
        }
        if (ntSystem.getImpersonationToken() != 0) {
            iToken = new NTNumericCredential(ntSystem.getImpersonationToken());
            if (debug) {
                System.out.println("\t\t\timpersonation token = " +
                        ntSystem.getImpersonationToken());
            }
        }

        succeeded = true;
        return succeeded;
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates some
     * number of various <code>Principal</code>s
     * with the <code>Subject</code> located in the
     * <code>LoginModuleContext</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *
     * <p>
     *  <p>如果LoginContext的整体认证成功(相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules成功),则调用此方法。
     * 
     *  <p>如果此LoginModule自己的身份验证尝试成功(通过检索由<code> login </code>方法保存的私有状态进行检查),则此方法将某些数量的各种<code> Principal </code>
     *  <code> Subject </code>位于<code> LoginModuleContext </code>中。
     * 如果此LoginModule自己的身份验证尝试失败,则此方法将删除最初保存的任何状态。
     * 
     * <p>
     * 
     * 
     * @exception LoginException if the commit fails.
     *
     * @return true if this LoginModule's own login and commit
     *          attempts succeeded, or false otherwise.
     */
    public boolean commit() throws LoginException {
        if (succeeded == false) {
            if (debug) {
                System.out.println("\t\t[NTLoginModule]: " +
                    "did not add any Principals to Subject " +
                    "because own authentication failed.");
            }
            return false;
        }
        if (subject.isReadOnly()) {
            throw new LoginException ("Subject is ReadOnly");
        }
        Set<Principal> principals = subject.getPrincipals();

        // we must have a userPrincipal - everything else is optional
        if (!principals.contains(userPrincipal)) {
            principals.add(userPrincipal);
        }
        if (userSID != null && !principals.contains(userSID)) {
            principals.add(userSID);
        }

        if (userDomain != null && !principals.contains(userDomain)) {
            principals.add(userDomain);
        }
        if (domainSID != null && !principals.contains(domainSID)) {
            principals.add(domainSID);
        }

        if (primaryGroup != null && !principals.contains(primaryGroup)) {
            principals.add(primaryGroup);
        }
        for (int i = 0; groups != null && i < groups.length; i++) {
            if (!principals.contains(groups[i])) {
                principals.add(groups[i]);
            }
        }

        Set<Object> pubCreds = subject.getPublicCredentials();
        if (iToken != null && !pubCreds.contains(iToken)) {
            pubCreds.add(iToken);
        }
        commitSucceeded = true;
        return true;
    }


    /**
     * <p> This method is called if the LoginContext's
     * overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> and <code>commit</code> methods),
     * then this method cleans up any state that was originally saved.
     *
     * <p>
     *
     * <p>
     * <p>如果LoginContext的整体身份验证失败,则会调用此方法。 (相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules没有成功)。
     * 
     *  <p>如果此LoginModule自己的身份验证尝试成功(通过检索由<code> login </code>和<code> commit </code>方法保存的私有状态进行检查),则此方法将清除原来
     * 保存。
     * 
     * <p>
     * 
     * 
     * @exception LoginException if the abort fails.
     *
     * @return false if this LoginModule's own login and/or commit attempts
     *          failed, and true otherwise.
     */
    public boolean abort() throws LoginException {
        if (debug) {
            System.out.println("\t\t[NTLoginModule]: " +
                "aborted authentication attempt");
        }

        if (succeeded == false) {
            return false;
        } else if (succeeded == true && commitSucceeded == false) {
            ntSystem = null;
            userPrincipal = null;
            userSID = null;
            userDomain = null;
            domainSID = null;
            primaryGroup = null;
            groups = null;
            iToken = null;
            succeeded = false;
        } else {
            // overall authentication succeeded and commit succeeded,
            // but someone else's commit failed
            logout();
        }
        return succeeded;
    }

    /**
     * Logout the user.
     *
     * <p> This method removes the <code>NTUserPrincipal</code>,
     * <code>NTDomainPrincipal</code>, <code>NTSidUserPrincipal</code>,
     * <code>NTSidDomainPrincipal</code>, <code>NTSidGroupPrincipal</code>s,
     * and <code>NTSidPrimaryGroupPrincipal</code>
     * that may have been added by the <code>commit</code> method.
     *
     * <p>
     *
     * <p>
     *  注销用户。
     * 
     *  <p>此方法移除<code> NTUserPrincipal </code>,<code> NTDomainPrincipal </code>,<code> NTSidUserPrincipal </code>
     * ,<code> NTSidDomainPrincipal </code>,<code> NTSidGroupPrincipal </code > s和可能已通过<code> commit </code>
     * 
     * @exception LoginException if the logout fails.
     *
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     */
    public boolean logout() throws LoginException {

        if (subject.isReadOnly()) {
            throw new LoginException ("Subject is ReadOnly");
        }
        Set<Principal> principals = subject.getPrincipals();
        if (principals.contains(userPrincipal)) {
            principals.remove(userPrincipal);
        }
        if (principals.contains(userSID)) {
            principals.remove(userSID);
        }
        if (principals.contains(userDomain)) {
            principals.remove(userDomain);
        }
        if (principals.contains(domainSID)) {
            principals.remove(domainSID);
        }
        if (principals.contains(primaryGroup)) {
            principals.remove(primaryGroup);
        }
        for (int i = 0; groups != null && i < groups.length; i++) {
            if (principals.contains(groups[i])) {
                principals.remove(groups[i]);
            }
        }

        Set<Object> pubCreds = subject.getPublicCredentials();
        if (pubCreds.contains(iToken)) {
            pubCreds.remove(iToken);
        }

        succeeded = false;
        commitSucceeded = false;
        userPrincipal = null;
        userDomain = null;
        userSID = null;
        domainSID = null;
        groups = null;
        primaryGroup = null;
        iToken = null;
        ntSystem = null;

        if (debug) {
                System.out.println("\t\t[NTLoginModule] " +
                                "completed logout processing");
        }
        return true;
    }
}
