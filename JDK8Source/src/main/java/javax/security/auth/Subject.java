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

package javax.security.auth;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.DomainCombiner;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;
import java.security.ProtectionDomain;
import sun.security.util.ResourcesMgr;

/**
 * <p> A {@code Subject} represents a grouping of related information
 * for a single entity, such as a person.
 * Such information includes the Subject's identities as well as
 * its security-related attributes
 * (passwords and cryptographic keys, for example).
 *
 * <p> Subjects may potentially have multiple identities.
 * Each identity is represented as a {@code Principal}
 * within the {@code Subject}.  Principals simply bind names to a
 * {@code Subject}.  For example, a {@code Subject} that happens
 * to be a person, Alice, might have two Principals:
 * one which binds "Alice Bar", the name on her driver license,
 * to the {@code Subject}, and another which binds,
 * "999-99-9999", the number on her student identification card,
 * to the {@code Subject}.  Both Principals refer to the same
 * {@code Subject} even though each has a different name.
 *
 * <p> A {@code Subject} may also own security-related attributes,
 * which are referred to as credentials.
 * Sensitive credentials that require special protection, such as
 * private cryptographic keys, are stored within a private credential
 * {@code Set}.  Credentials intended to be shared, such as
 * public key certificates or Kerberos server tickets are stored
 * within a public credential {@code Set}.  Different permissions
 * are required to access and modify the different credential Sets.
 *
 * <p> To retrieve all the Principals associated with a {@code Subject},
 * invoke the {@code getPrincipals} method.  To retrieve
 * all the public or private credentials belonging to a {@code Subject},
 * invoke the {@code getPublicCredentials} method or
 * {@code getPrivateCredentials} method, respectively.
 * To modify the returned {@code Set} of Principals and credentials,
 * use the methods defined in the {@code Set} class.
 * For example:
 * <pre>
 *      Subject subject;
 *      Principal principal;
 *      Object credential;
 *
 *      // add a Principal and credential to the Subject
 *      subject.getPrincipals().add(principal);
 *      subject.getPublicCredentials().add(credential);
 * </pre>
 *
 * <p> This {@code Subject} class implements {@code Serializable}.
 * While the Principals associated with the {@code Subject} are serialized,
 * the credentials associated with the {@code Subject} are not.
 * Note that the {@code java.security.Principal} class
 * does not implement {@code Serializable}.  Therefore all concrete
 * {@code Principal} implementations associated with Subjects
 * must implement {@code Serializable}.
 *
 * <p>
 *  <p> {@code Subject}代表单一实体(例如人)的相关资讯分组。这样的信息包括主体的身份以及其安全相关的属性(例如密码和加密密钥)。
 * 
 *  <p>主题可能具有多个身份。每个身份在{@code Subject}中表示为{@code Principal}。主体只需将名称绑定到{@code主题}。
 * 例如,一个恰好是一个人的{@code Subject} Alice可能有两个原则：一个将"Alice Bar"(她的驾驶执照上的名字)绑定到{@code主题},另一个绑定,"999-99-9999",她
 * 的学生证上的号码,到{@code主题}。
 *  <p>主题可能具有多个身份。每个身份在{@code Subject}中表示为{@code Principal}。主体只需将名称绑定到{@code主题}。
 * 两个主体都参考相同的{@code主题},即使每个都有不同的名称。
 * 
 *  <p> {@code Subject}也可能拥有与安全性相关的属性,称为凭证。需要特殊保护的敏感凭证(如私有密钥)存储在私有凭证{@code Set}中。
 * 要共享的凭据(如公钥证书或Kerberos服务器凭单)存储在公用凭证{@code Set}中。访问和修改不同凭据集需要不同的权限。
 * 
 * <p>要检索与{@code Subject}关联的所有主体,请调用{@code getPrincipals}方法。
 * 要检索属于{@code Subject}的所有公共或私有凭证,分别调用{@code getPublicCredentials}方法或{@code getPrivateCredentials}方法。
 * 要修改返回的{@code Set} Principal和凭据,请使用{@code Set}类中定义的方法。例如：。
 * <pre>
 *  主题;主要委托人;对象凭证;
 * 
 *  //将主体和凭证添加到Subject subject.getPrincipals()。add(principal); subject.getPublicCredentials()。
 * add(credential);。
 * </pre>
 * 
 *  <p>此{@code Subject}类实现了{@code Serializable}。虽然与{@code Subject}相关联的主体被序列化,但与{@code Subject}相关联的凭据不是。
 * 请注意,{@code java.security.Principal}类不实现{@code Serializable}。
 * 因此,与主题相关联的所有具体{@code Principal}实现必须实现{@code Serializable}。
 * 
 * 
 * @see java.security.Principal
 * @see java.security.DomainCombiner
 */
public final class Subject implements java.io.Serializable {

    private static final long serialVersionUID = -8308522755600156056L;

    /**
     * A {@code Set} that provides a view of all of this
     * Subject's Principals
     *
     * <p>
     *
     * <p>
     *  一个{@code Set},提供了这个主体的所有主体的视图
     * 
     * <p>
     * 
     * 
     * @serial Each element in this set is a
     *          {@code java.security.Principal}.
     *          The set is a {@code Subject.SecureSet}.
     */
    Set<Principal> principals;

    /**
     * Sets that provide a view of all of this
     * Subject's Credentials
     * <p>
     *  提供所有此主体凭证的视图的集合
     * 
     */
    transient Set<Object> pubCredentials;
    transient Set<Object> privCredentials;

    /**
     * Whether this Subject is read-only
     *
     * <p>
     *  此主题是否为只读
     * 
     * 
     * @serial
     */
    private volatile boolean readOnly = false;

    private static final int PRINCIPAL_SET = 1;
    private static final int PUB_CREDENTIAL_SET = 2;
    private static final int PRIV_CREDENTIAL_SET = 3;

    private static final ProtectionDomain[] NULL_PD_ARRAY
        = new ProtectionDomain[0];

    /**
     * Create an instance of a {@code Subject}
     * with an empty {@code Set} of Principals and empty
     * Sets of public and private credentials.
     *
     * <p> The newly constructed Sets check whether this {@code Subject}
     * has been set read-only before permitting subsequent modifications.
     * The newly created Sets also prevent illegal modifications
     * by ensuring that callers have sufficient permissions.
     *
     * <p> To modify the Principals Set, the caller must have
     * {@code AuthPermission("modifyPrincipals")}.
     * To modify the public credential Set, the caller must have
     * {@code AuthPermission("modifyPublicCredentials")}.
     * To modify the private credential Set, the caller must have
     * {@code AuthPermission("modifyPrivateCredentials")}.
     * <p>
     *  使用空的{@code Set}主体和空集的公共和专用凭据创建{@code Subject}的实例。
     * 
     * <p>新构建的集合会检查此{@code Subject}是否已设置为只读,然后才允许后续修改。新创建的集还通过确保调用者具有足够的权限来防止非法修改。
     * 
     *  <p>要修改主体集,调用者必须具有{@code AuthPermission("modifyPrincipals")}。
     * 要修改公用凭证集,调用者必须具有{@code AuthPermission("modifyPublicCredentials")}。
     * 要修改私有凭据集,调用者必须具有{@code AuthPermission("modifyPrivateCredentials")}。
     * 
     */
    public Subject() {

        this.principals = Collections.synchronizedSet
                        (new SecureSet<Principal>(this, PRINCIPAL_SET));
        this.pubCredentials = Collections.synchronizedSet
                        (new SecureSet<Object>(this, PUB_CREDENTIAL_SET));
        this.privCredentials = Collections.synchronizedSet
                        (new SecureSet<Object>(this, PRIV_CREDENTIAL_SET));
    }

    /**
     * Create an instance of a {@code Subject} with
     * Principals and credentials.
     *
     * <p> The Principals and credentials from the specified Sets
     * are copied into newly constructed Sets.
     * These newly created Sets check whether this {@code Subject}
     * has been set read-only before permitting subsequent modifications.
     * The newly created Sets also prevent illegal modifications
     * by ensuring that callers have sufficient permissions.
     *
     * <p> To modify the Principals Set, the caller must have
     * {@code AuthPermission("modifyPrincipals")}.
     * To modify the public credential Set, the caller must have
     * {@code AuthPermission("modifyPublicCredentials")}.
     * To modify the private credential Set, the caller must have
     * {@code AuthPermission("modifyPrivateCredentials")}.
     * <p>
     *
     * <p>
     *  使用Principals和凭证创建{@code Subject}的实例。
     * 
     *  <p>来自指定集合的​​主体和凭证将复制到新构建的集合中。这些新创建的集将检查此{@code Subject}是否已设置为只读,然后才允许后续修改。
     * 新创建的集还通过确保调用者具有足够的权限来防止非法修改。
     * 
     *  <p>要修改主体集,调用者必须具有{@code AuthPermission("modifyPrincipals")}。
     * 要修改公用凭证集,调用者必须具有{@code AuthPermission("modifyPublicCredentials")}。
     * 要修改私有凭据集,调用者必须具有{@code AuthPermission("modifyPrivateCredentials")}。
     * <p>
     * 
     * 
     * @param readOnly true if the {@code Subject} is to be read-only,
     *          and false otherwise. <p>
     *
     * @param principals the {@code Set} of Principals
     *          to be associated with this {@code Subject}. <p>
     *
     * @param pubCredentials the {@code Set} of public credentials
     *          to be associated with this {@code Subject}. <p>
     *
     * @param privCredentials the {@code Set} of private credentials
     *          to be associated with this {@code Subject}.
     *
     * @exception NullPointerException if the specified
     *          {@code principals}, {@code pubCredentials},
     *          or {@code privCredentials} are {@code null}.
     */
    public Subject(boolean readOnly, Set<? extends Principal> principals,
                   Set<?> pubCredentials, Set<?> privCredentials)
    {

        if (principals == null ||
            pubCredentials == null ||
            privCredentials == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.input.s."));

        this.principals = Collections.synchronizedSet(new SecureSet<Principal>
                                (this, PRINCIPAL_SET, principals));
        this.pubCredentials = Collections.synchronizedSet(new SecureSet<Object>
                                (this, PUB_CREDENTIAL_SET, pubCredentials));
        this.privCredentials = Collections.synchronizedSet(new SecureSet<Object>
                                (this, PRIV_CREDENTIAL_SET, privCredentials));
        this.readOnly = readOnly;
    }

    /**
     * Set this {@code Subject} to be read-only.
     *
     * <p> Modifications (additions and removals) to this Subject's
     * {@code Principal} {@code Set} and
     * credential Sets will be disallowed.
     * The {@code destroy} operation on this Subject's credentials will
     * still be permitted.
     *
     * <p> Subsequent attempts to modify the Subject's {@code Principal}
     * and credential Sets will result in an
     * {@code IllegalStateException} being thrown.
     * Also, once a {@code Subject} is read-only,
     * it can not be reset to being writable again.
     *
     * <p>
     *
     * <p>
     *  将此{@code Subject}设置为只读。
     * 
     * <p>将不允许修改(添加和删除)此主题的{@code Principal} {@code Set}和凭据集。仍然允许对此主体的凭据执行{@code destroy}操作。
     * 
     *  <p>后续尝试修改主题的{@code Principal}和凭据集将导致{@code IllegalStateException}被抛出。
     * 此外,一旦{@code Subject}为只读,它不能重置为可再次写入。
     * 
     * <p>
     * 
     * 
     * @exception SecurityException if the caller does not have permission
     *          to set this {@code Subject} to be read-only.
     */
    public void setReadOnly() {
        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(AuthPermissionHolder.SET_READ_ONLY_PERMISSION);
        }

        this.readOnly = true;
    }

    /**
     * Query whether this {@code Subject} is read-only.
     *
     * <p>
     *
     * <p>
     *  查询此{@code Subject}是否为只读。
     * 
     * <p>
     * 
     * 
     * @return true if this {@code Subject} is read-only, false otherwise.
     */
    public boolean isReadOnly() {
        return this.readOnly;
    }

    /**
     * Get the {@code Subject} associated with the provided
     * {@code AccessControlContext}.
     *
     * <p> The {@code AccessControlContext} may contain many
     * Subjects (from nested {@code doAs} calls).
     * In this situation, the most recent {@code Subject} associated
     * with the {@code AccessControlContext} is returned.
     *
     * <p>
     *
     * <p>
     *  获取与提供的{@code AccessControlContext}相关联的{@code Subject}。
     * 
     *  <p> {@code AccessControlContext}可能包含许多主题(来自嵌套的{@code doAs}调用)。
     * 在这种情况下,会返回与{@code AccessControlContext}关联的最近的{@code Subject}。
     * 
     * <p>
     * 
     * 
     * @param  acc the {@code AccessControlContext} from which to retrieve
     *          the {@code Subject}.
     *
     * @return  the {@code Subject} associated with the provided
     *          {@code AccessControlContext}, or {@code null}
     *          if no {@code Subject} is associated
     *          with the provided {@code AccessControlContext}.
     *
     * @exception SecurityException if the caller does not have permission
     *          to get the {@code Subject}. <p>
     *
     * @exception NullPointerException if the provided
     *          {@code AccessControlContext} is {@code null}.
     */
    public static Subject getSubject(final AccessControlContext acc) {

        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(AuthPermissionHolder.GET_SUBJECT_PERMISSION);
        }

        if (acc == null) {
            throw new NullPointerException(ResourcesMgr.getString
                ("invalid.null.AccessControlContext.provided"));
        }

        // return the Subject from the DomainCombiner of the provided context
        return AccessController.doPrivileged
            (new java.security.PrivilegedAction<Subject>() {
            public Subject run() {
                DomainCombiner dc = acc.getDomainCombiner();
                if (!(dc instanceof SubjectDomainCombiner))
                    return null;
                SubjectDomainCombiner sdc = (SubjectDomainCombiner)dc;
                return sdc.getSubject();
            }
        });
    }

    /**
     * Perform work as a particular {@code Subject}.
     *
     * <p> This method first retrieves the current Thread's
     * {@code AccessControlContext} via
     * {@code AccessController.getContext},
     * and then instantiates a new {@code AccessControlContext}
     * using the retrieved context along with a new
     * {@code SubjectDomainCombiner} (constructed using
     * the provided {@code Subject}).
     * Finally, this method invokes {@code AccessController.doPrivileged},
     * passing it the provided {@code PrivilegedAction},
     * as well as the newly constructed {@code AccessControlContext}.
     *
     * <p>
     *
     * <p>
     *  以特定的{@code Subject}执行工作。
     * 
     *  <p>此方法首先通过{@code AccessController.getContext}检索当前线程的{@code AccessControlContext},然后使用检索的上下文和新的{@code SubjectDomainCombiner}
     * (使用提供的{@code Subject})。
     * 最后,这个方法调用{@code AccessController.doPrivileged},传递它提供的{@code PrivilegedAction},以及新构造的{@code AccessControlContext}
     * 。
     * 
     * <p>
     * 
     * 
     * @param subject the {@code Subject} that the specified
     *                  {@code action} will run as.  This parameter
     *                  may be {@code null}. <p>
     *
     * @param <T> the type of the value returned by the PrivilegedAction's
     *                  {@code run} method.
     *
     * @param action the code to be run as the specified
     *                  {@code Subject}. <p>
     *
     * @return the value returned by the PrivilegedAction's
     *                  {@code run} method.
     *
     * @exception NullPointerException if the {@code PrivilegedAction}
     *                  is {@code null}. <p>
     *
     * @exception SecurityException if the caller does not have permission
     *                  to invoke this method.
     */
    public static <T> T doAs(final Subject subject,
                        final java.security.PrivilegedAction<T> action) {

        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
        }
        if (action == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.action.provided"));

        // set up the new Subject-based AccessControlContext
        // for doPrivileged
        final AccessControlContext currentAcc = AccessController.getContext();

        // call doPrivileged and push this new context on the stack
        return java.security.AccessController.doPrivileged
                                        (action,
                                        createContext(subject, currentAcc));
    }

    /**
     * Perform work as a particular {@code Subject}.
     *
     * <p> This method first retrieves the current Thread's
     * {@code AccessControlContext} via
     * {@code AccessController.getContext},
     * and then instantiates a new {@code AccessControlContext}
     * using the retrieved context along with a new
     * {@code SubjectDomainCombiner} (constructed using
     * the provided {@code Subject}).
     * Finally, this method invokes {@code AccessController.doPrivileged},
     * passing it the provided {@code PrivilegedExceptionAction},
     * as well as the newly constructed {@code AccessControlContext}.
     *
     * <p>
     *
     * <p>
     *  以特定的{@code Subject}执行工作。
     * 
     * <p>此方法首先通过{@code AccessController.getContext}检索当前线程的{@code AccessControlContext},然后使用检索的上下文和新的{@code SubjectDomainCombiner}
     * (使用提供的{@code Subject})。
     * 最后,这个方法调用{@code AccessController.doPrivileged},传递它提供的{@code PrivilegedExceptionAction},以及新构造的{@code AccessControlContext}
     * 。
     * 
     * <p>
     * 
     * 
     * @param subject the {@code Subject} that the specified
     *                  {@code action} will run as.  This parameter
     *                  may be {@code null}. <p>
     *
     * @param <T> the type of the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     *
     * @param action the code to be run as the specified
     *                  {@code Subject}. <p>
     *
     * @return the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     *
     * @exception PrivilegedActionException if the
     *                  {@code PrivilegedExceptionAction.run}
     *                  method throws a checked exception. <p>
     *
     * @exception NullPointerException if the specified
     *                  {@code PrivilegedExceptionAction} is
     *                  {@code null}. <p>
     *
     * @exception SecurityException if the caller does not have permission
     *                  to invoke this method.
     */
    public static <T> T doAs(final Subject subject,
                        final java.security.PrivilegedExceptionAction<T> action)
                        throws java.security.PrivilegedActionException {

        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
        }

        if (action == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.action.provided"));

        // set up the new Subject-based AccessControlContext for doPrivileged
        final AccessControlContext currentAcc = AccessController.getContext();

        // call doPrivileged and push this new context on the stack
        return java.security.AccessController.doPrivileged
                                        (action,
                                        createContext(subject, currentAcc));
    }

    /**
     * Perform privileged work as a particular {@code Subject}.
     *
     * <p> This method behaves exactly as {@code Subject.doAs},
     * except that instead of retrieving the current Thread's
     * {@code AccessControlContext}, it uses the provided
     * {@code AccessControlContext}.  If the provided
     * {@code AccessControlContext} is {@code null},
     * this method instantiates a new {@code AccessControlContext}
     * with an empty collection of ProtectionDomains.
     *
     * <p>
     *
     * <p>
     *  以特定的{@code Subject}执行特权工作。
     * 
     *  <p>此方法的行为与{@code Subject.doAs}完全一样,只是它不是检索当前线程的{@code AccessControlContext},而是使用提供的{@code AccessControlContext}
     * 。
     * 如果提供的{@code AccessControlContext}是{@code null},此方法将实例化一个新的{@code AccessControlContext}与一个空的Protection
     * Domains集合。
     * 
     * <p>
     * 
     * 
     * @param subject the {@code Subject} that the specified
     *                  {@code action} will run as.  This parameter
     *                  may be {@code null}. <p>
     *
     * @param <T> the type of the value returned by the PrivilegedAction's
     *                  {@code run} method.
     *
     * @param action the code to be run as the specified
     *                  {@code Subject}. <p>
     *
     * @param acc the {@code AccessControlContext} to be tied to the
     *                  specified <i>subject</i> and <i>action</i>. <p>
     *
     * @return the value returned by the PrivilegedAction's
     *                  {@code run} method.
     *
     * @exception NullPointerException if the {@code PrivilegedAction}
     *                  is {@code null}. <p>
     *
     * @exception SecurityException if the caller does not have permission
     *                  to invoke this method.
     */
    public static <T> T doAsPrivileged(final Subject subject,
                        final java.security.PrivilegedAction<T> action,
                        final java.security.AccessControlContext acc) {

        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
        }

        if (action == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.action.provided"));

        // set up the new Subject-based AccessControlContext
        // for doPrivileged
        final AccessControlContext callerAcc =
                (acc == null ?
                new AccessControlContext(NULL_PD_ARRAY) :
                acc);

        // call doPrivileged and push this new context on the stack
        return java.security.AccessController.doPrivileged
                                        (action,
                                        createContext(subject, callerAcc));
    }

    /**
     * Perform privileged work as a particular {@code Subject}.
     *
     * <p> This method behaves exactly as {@code Subject.doAs},
     * except that instead of retrieving the current Thread's
     * {@code AccessControlContext}, it uses the provided
     * {@code AccessControlContext}.  If the provided
     * {@code AccessControlContext} is {@code null},
     * this method instantiates a new {@code AccessControlContext}
     * with an empty collection of ProtectionDomains.
     *
     * <p>
     *
     * <p>
     *  以特定的{@code Subject}执行特权工作。
     * 
     *  <p>此方法的行为与{@code Subject.doAs}完全一样,只是它不是检索当前线程的{@code AccessControlContext},而是使用提供的{@code AccessControlContext}
     * 。
     * 如果提供的{@code AccessControlContext}是{@code null},此方法将实例化一个新的{@code AccessControlContext}与一个空的Protection
     * Domains集合。
     * 
     * <p>
     * 
     * 
     * @param subject the {@code Subject} that the specified
     *                  {@code action} will run as.  This parameter
     *                  may be {@code null}. <p>
     *
     * @param <T> the type of the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     *
     * @param action the code to be run as the specified
     *                  {@code Subject}. <p>
     *
     * @param acc the {@code AccessControlContext} to be tied to the
     *                  specified <i>subject</i> and <i>action</i>. <p>
     *
     * @return the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     *
     * @exception PrivilegedActionException if the
     *                  {@code PrivilegedExceptionAction.run}
     *                  method throws a checked exception. <p>
     *
     * @exception NullPointerException if the specified
     *                  {@code PrivilegedExceptionAction} is
     *                  {@code null}. <p>
     *
     * @exception SecurityException if the caller does not have permission
     *                  to invoke this method.
     */
    public static <T> T doAsPrivileged(final Subject subject,
                        final java.security.PrivilegedExceptionAction<T> action,
                        final java.security.AccessControlContext acc)
                        throws java.security.PrivilegedActionException {

        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
        }

        if (action == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.action.provided"));

        // set up the new Subject-based AccessControlContext for doPrivileged
        final AccessControlContext callerAcc =
                (acc == null ?
                new AccessControlContext(NULL_PD_ARRAY) :
                acc);

        // call doPrivileged and push this new context on the stack
        return java.security.AccessController.doPrivileged
                                        (action,
                                        createContext(subject, callerAcc));
    }

    private static AccessControlContext createContext(final Subject subject,
                                        final AccessControlContext acc) {


        return java.security.AccessController.doPrivileged
            (new java.security.PrivilegedAction<AccessControlContext>() {
            public AccessControlContext run() {
                if (subject == null)
                    return new AccessControlContext(acc, null);
                else
                    return new AccessControlContext
                                        (acc,
                                        new SubjectDomainCombiner(subject));
            }
        });
    }

    /**
     * Return the {@code Set} of Principals associated with this
     * {@code Subject}.  Each {@code Principal} represents
     * an identity for this {@code Subject}.
     *
     * <p> The returned {@code Set} is backed by this Subject's
     * internal {@code Principal} {@code Set}.  Any modification
     * to the returned {@code Set} affects the internal
     * {@code Principal} {@code Set} as well.
     *
     * <p>
     *
     * <p>
     *  返回与此{@code Subject}相关联的{@code Set}主体。每个{@code Principal}表示此{@code主题}的身份。
     * 
     * <p>返回的{@code Set}由此主题的内部{@code Principal} {@code Set}提供支持。
     * 对返回的{@code Set}的任何修改都会影响内部{@code Principal} {@code Set}。
     * 
     * <p>
     * 
     * 
     * @return  The {@code Set} of Principals associated with this
     *          {@code Subject}.
     */
    public Set<Principal> getPrincipals() {

        // always return an empty Set instead of null
        // so LoginModules can add to the Set if necessary
        return principals;
    }

    /**
     * Return a {@code Set} of Principals associated with this
     * {@code Subject} that are instances or subclasses of the specified
     * {@code Class}.
     *
     * <p> The returned {@code Set} is not backed by this Subject's
     * internal {@code Principal} {@code Set}.  A new
     * {@code Set} is created and returned for each method invocation.
     * Modifications to the returned {@code Set}
     * will not affect the internal {@code Principal} {@code Set}.
     *
     * <p>
     *
     * <p>
     *  返回与此{@code Subject}相关联的{@code Set}的{@code Set}主体,它们是指定的{@code Class}的实例或子类。
     * 
     *  <p>返回的{@code Set}不受此主题的内部{@code Principal} {@code Set}支持。为每个方法调用创建并返回一个新的{@code Set}。
     * 对返回的{@code Set}的修改不会影响内部{@code Principal} {@code Set}。
     * 
     * <p>
     * 
     * 
     * @param <T> the type of the class modeled by {@code c}
     *
     * @param c the returned {@code Set} of Principals will all be
     *          instances of this class.
     *
     * @return a {@code Set} of Principals that are instances of the
     *          specified {@code Class}.
     *
     * @exception NullPointerException if the specified {@code Class}
     *                  is {@code null}.
     */
    public <T extends Principal> Set<T> getPrincipals(Class<T> c) {

        if (c == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.Class.provided"));

        // always return an empty Set instead of null
        // so LoginModules can add to the Set if necessary
        return new ClassSet<T>(PRINCIPAL_SET, c);
    }

    /**
     * Return the {@code Set} of public credentials held by this
     * {@code Subject}.
     *
     * <p> The returned {@code Set} is backed by this Subject's
     * internal public Credential {@code Set}.  Any modification
     * to the returned {@code Set} affects the internal public
     * Credential {@code Set} as well.
     *
     * <p>
     *
     * <p>
     *  返回此{@code Subject}持有的{@code Set}公开凭证。
     * 
     *  <p>返回的{@code Set}由该主体的内部公共凭据{@code Set}提供支持。
     * 对返回的{@code Set}的任何修改也会影响内部public Credential {@code Set}。
     * 
     * <p>
     * 
     * 
     * @return  A {@code Set} of public credentials held by this
     *          {@code Subject}.
     */
    public Set<Object> getPublicCredentials() {

        // always return an empty Set instead of null
        // so LoginModules can add to the Set if necessary
        return pubCredentials;
    }

    /**
     * Return the {@code Set} of private credentials held by this
     * {@code Subject}.
     *
     * <p> The returned {@code Set} is backed by this Subject's
     * internal private Credential {@code Set}.  Any modification
     * to the returned {@code Set} affects the internal private
     * Credential {@code Set} as well.
     *
     * <p> A caller requires permissions to access the Credentials
     * in the returned {@code Set}, or to modify the
     * {@code Set} itself.  A {@code SecurityException}
     * is thrown if the caller does not have the proper permissions.
     *
     * <p> While iterating through the {@code Set},
     * a {@code SecurityException} is thrown
     * if the caller does not have permission to access a
     * particular Credential.  The {@code Iterator}
     * is nevertheless advanced to next element in the {@code Set}.
     *
     * <p>
     *
     * <p>
     *  返回此{@code Subject}持有的{@code Set}私人凭证。
     * 
     *  <p>返回的{@code Set}由此主体的内部私人凭据{@code Set}提供支持。对返回的{@code Set}的任何修改也会影响内部私有Credential {@code Set}。
     * 
     *  <p>呼叫方需要有权限访问返回的{@code Set}中的凭据,或者修改{@code Set}本身。如果调用者没有正确的权限,则会抛出{@code SecurityException}。
     * 
     * <p>在迭代{@code Set}时,如果调用者没有访问特定凭据的权限,则会抛出{@code SecurityException}。
     *  {@code Iterator}仍然会进入{@code Set}中的next元素。
     * 
     * <p>
     * 
     * 
     * @return  A {@code Set} of private credentials held by this
     *          {@code Subject}.
     */
    public Set<Object> getPrivateCredentials() {

        // XXX
        // we do not need a security check for
        // AuthPermission(getPrivateCredentials)
        // because we already restrict access to private credentials
        // via the PrivateCredentialPermission.  all the extra AuthPermission
        // would do is protect the set operations themselves
        // (like size()), which don't seem security-sensitive.

        // always return an empty Set instead of null
        // so LoginModules can add to the Set if necessary
        return privCredentials;
    }

    /**
     * Return a {@code Set} of public credentials associated with this
     * {@code Subject} that are instances or subclasses of the specified
     * {@code Class}.
     *
     * <p> The returned {@code Set} is not backed by this Subject's
     * internal public Credential {@code Set}.  A new
     * {@code Set} is created and returned for each method invocation.
     * Modifications to the returned {@code Set}
     * will not affect the internal public Credential {@code Set}.
     *
     * <p>
     *
     * <p>
     *  返回与此{@code Subject}相关联的{@code Set}的公开凭证,它们是指定的{@code Class}的实例或子类。
     * 
     *  <p>返回的{@code Set}不受此主体的内部公共凭据{@code Set}的支持。为每个方法调用创建并返回一个新的{@code Set}。
     * 对返回的{@code Set}的修改不会影响内部公共凭据{@code Set}。
     * 
     * <p>
     * 
     * 
     * @param <T> the type of the class modeled by {@code c}
     *
     * @param c the returned {@code Set} of public credentials will all be
     *          instances of this class.
     *
     * @return a {@code Set} of public credentials that are instances
     *          of the  specified {@code Class}.
     *
     * @exception NullPointerException if the specified {@code Class}
     *          is {@code null}.
     */
    public <T> Set<T> getPublicCredentials(Class<T> c) {

        if (c == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.Class.provided"));

        // always return an empty Set instead of null
        // so LoginModules can add to the Set if necessary
        return new ClassSet<T>(PUB_CREDENTIAL_SET, c);
    }

    /**
     * Return a {@code Set} of private credentials associated with this
     * {@code Subject} that are instances or subclasses of the specified
     * {@code Class}.
     *
     * <p> The caller must have permission to access all of the
     * requested Credentials, or a {@code SecurityException}
     * will be thrown.
     *
     * <p> The returned {@code Set} is not backed by this Subject's
     * internal private Credential {@code Set}.  A new
     * {@code Set} is created and returned for each method invocation.
     * Modifications to the returned {@code Set}
     * will not affect the internal private Credential {@code Set}.
     *
     * <p>
     *
     * <p>
     *  返回与此{@code Subject}相关联的{@code Set}私有凭据,这些凭据是指定的{@code Class}的实例或子类。
     * 
     *  <p>调用者必须具有访问所有请求的凭据的权限,否则将抛出{@code SecurityException}。
     * 
     *  <p>返回的{@code Set}不受此主体的内部私人凭据{@code Set}的支持。为每个方法调用创建并返回一个新的{@code Set}。
     * 对返回的{@code Set}的修改不会影响内部私有凭据{@code Set}。
     * 
     * <p>
     * 
     * 
     * @param <T> the type of the class modeled by {@code c}
     *
     * @param c the returned {@code Set} of private credentials will all be
     *          instances of this class.
     *
     * @return a {@code Set} of private credentials that are instances
     *          of the  specified {@code Class}.
     *
     * @exception NullPointerException if the specified {@code Class}
     *          is {@code null}.
     */
    public <T> Set<T> getPrivateCredentials(Class<T> c) {

        // XXX
        // we do not need a security check for
        // AuthPermission(getPrivateCredentials)
        // because we already restrict access to private credentials
        // via the PrivateCredentialPermission.  all the extra AuthPermission
        // would do is protect the set operations themselves
        // (like size()), which don't seem security-sensitive.

        if (c == null)
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.Class.provided"));

        // always return an empty Set instead of null
        // so LoginModules can add to the Set if necessary
        return new ClassSet<T>(PRIV_CREDENTIAL_SET, c);
    }

    /**
     * Compares the specified Object with this {@code Subject}
     * for equality.  Returns true if the given object is also a Subject
     * and the two {@code Subject} instances are equivalent.
     * More formally, two {@code Subject} instances are
     * equal if their {@code Principal} and {@code Credential}
     * Sets are equal.
     *
     * <p>
     *
     * <p>
     * 将指定的对象与此{@code Subject}比较以实现相等。如果给定对象也是Subject,并且两个{@code Subject}实例是等效的,则返回true。
     * 更正式地说,如果两个{@code Subject}实例的{@code Principal}和{@code Credential}集相等,则它们是相等的。
     * 
     * <p>
     * 
     * 
     * @param o Object to be compared for equality with this
     *          {@code Subject}.
     *
     * @return true if the specified Object is equal to this
     *          {@code Subject}.
     *
     * @exception SecurityException if the caller does not have permission
     *          to access the private credentials for this {@code Subject},
     *          or if the caller does not have permission to access the
     *          private credentials for the provided {@code Subject}.
     */
    public boolean equals(Object o) {

        if (o == null)
            return false;

        if (this == o)
            return true;

        if (o instanceof Subject) {

            final Subject that = (Subject)o;

            // check the principal and credential sets
            Set<Principal> thatPrincipals;
            synchronized(that.principals) {
                // avoid deadlock from dual locks
                thatPrincipals = new HashSet<Principal>(that.principals);
            }
            if (!principals.equals(thatPrincipals)) {
                return false;
            }

            Set<Object> thatPubCredentials;
            synchronized(that.pubCredentials) {
                // avoid deadlock from dual locks
                thatPubCredentials = new HashSet<Object>(that.pubCredentials);
            }
            if (!pubCredentials.equals(thatPubCredentials)) {
                return false;
            }

            Set<Object> thatPrivCredentials;
            synchronized(that.privCredentials) {
                // avoid deadlock from dual locks
                thatPrivCredentials = new HashSet<Object>(that.privCredentials);
            }
            if (!privCredentials.equals(thatPrivCredentials)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Return the String representation of this {@code Subject}.
     *
     * <p>
     *
     * <p>
     *  返回此{@code Subject}的String表示形式。
     * 
     * <p>
     * 
     * 
     * @return the String representation of this {@code Subject}.
     */
    public String toString() {
        return toString(true);
    }

    /**
     * package private convenience method to print out the Subject
     * without firing off a security check when trying to access
     * the Private Credentials
     * <p>
     *  包私人便利方法打印出主体,而无需在尝试访问私人凭据时触发安全检查
     * 
     */
    String toString(boolean includePrivateCredentials) {

        String s = ResourcesMgr.getString("Subject.");
        String suffix = "";

        synchronized(principals) {
            Iterator<Principal> pI = principals.iterator();
            while (pI.hasNext()) {
                Principal p = pI.next();
                suffix = suffix + ResourcesMgr.getString(".Principal.") +
                        p.toString() + ResourcesMgr.getString("NEWLINE");
            }
        }

        synchronized(pubCredentials) {
            Iterator<Object> pI = pubCredentials.iterator();
            while (pI.hasNext()) {
                Object o = pI.next();
                suffix = suffix +
                        ResourcesMgr.getString(".Public.Credential.") +
                        o.toString() + ResourcesMgr.getString("NEWLINE");
            }
        }

        if (includePrivateCredentials) {
            synchronized(privCredentials) {
                Iterator<Object> pI = privCredentials.iterator();
                while (pI.hasNext()) {
                    try {
                        Object o = pI.next();
                        suffix += ResourcesMgr.getString
                                        (".Private.Credential.") +
                                        o.toString() +
                                        ResourcesMgr.getString("NEWLINE");
                    } catch (SecurityException se) {
                        suffix += ResourcesMgr.getString
                                (".Private.Credential.inaccessible.");
                        break;
                    }
                }
            }
        }
        return s + suffix;
    }

    /**
     * Returns a hashcode for this {@code Subject}.
     *
     * <p>
     *
     * <p>
     *  返回此{@code Subject}的哈希码。
     * 
     * <p>
     * 
     * 
     * @return a hashcode for this {@code Subject}.
     *
     * @exception SecurityException if the caller does not have permission
     *          to access this Subject's private credentials.
     */
    public int hashCode() {

        /**
         * The hashcode is derived exclusive or-ing the
         * hashcodes of this Subject's Principals and credentials.
         *
         * If a particular credential was destroyed
         * ({@code credential.hashCode()} throws an
         * {@code IllegalStateException}),
         * the hashcode for that credential is derived via:
         * {@code credential.getClass().toString().hashCode()}.
         * <p>
         *  散列码是独立的或者该主体的主体和凭证的散列码。
         * 
         *  如果一个特定的凭据被破坏({@code credential.hashCode()}抛出一个{@code IllegalStateException}),那个凭证的哈希码是通过{@code credential.getClass()。
         * toString()。hashCode }。
         * 
         */

        int hashCode = 0;

        synchronized(principals) {
            Iterator<Principal> pIterator = principals.iterator();
            while (pIterator.hasNext()) {
                Principal p = pIterator.next();
                hashCode ^= p.hashCode();
            }
        }

        synchronized(pubCredentials) {
            Iterator<Object> pubCIterator = pubCredentials.iterator();
            while (pubCIterator.hasNext()) {
                hashCode ^= getCredHashCode(pubCIterator.next());
            }
        }
        return hashCode;
    }

    /**
     * get a credential's hashcode
     * <p>
     *  获取凭据的哈希码
     * 
     */
    private int getCredHashCode(Object o) {
        try {
            return o.hashCode();
        } catch (IllegalStateException ise) {
            return o.getClass().toString().hashCode();
        }
    }

    /**
     * Writes this object out to a stream (i.e., serializes it).
     * <p>
     *  将此对象写入流(即,将其序列化)。
     * 
     */
    private void writeObject(java.io.ObjectOutputStream oos)
                throws java.io.IOException {
        synchronized(principals) {
            oos.defaultWriteObject();
        }
    }

    /**
     * Reads this object from a stream (i.e., deserializes it)
     * <p>
     *  从流中读取此对象(即,对其进行反序列化)
     * 
     */
    @SuppressWarnings("unchecked")
    private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {

        ObjectInputStream.GetField gf = s.readFields();

        readOnly = gf.get("readOnly", false);

        Set<Principal> inputPrincs = (Set<Principal>)gf.get("principals", null);

        // Rewrap the principals into a SecureSet
        if (inputPrincs == null) {
            throw new NullPointerException
                (ResourcesMgr.getString("invalid.null.input.s."));
        }
        try {
            principals = Collections.synchronizedSet(new SecureSet<Principal>
                                (this, PRINCIPAL_SET, inputPrincs));
        } catch (NullPointerException npe) {
            // Sometimes people deserialize the principals set only.
            // Subject is not accessible, so just don't fail.
            principals = Collections.synchronizedSet
                        (new SecureSet<Principal>(this, PRINCIPAL_SET));
        }

        // The Credential {@code Set} is not serialized, but we do not
        // want the default deserialization routine to set it to null.
        this.pubCredentials = Collections.synchronizedSet
                        (new SecureSet<Object>(this, PUB_CREDENTIAL_SET));
        this.privCredentials = Collections.synchronizedSet
                        (new SecureSet<Object>(this, PRIV_CREDENTIAL_SET));
    }

    /**
     * Prevent modifications unless caller has permission.
     *
     * <p>
     *  除非来电者有权限,否则禁止修改。
     * 
     * 
     * @serial include
     */
    private static class SecureSet<E>
        extends AbstractSet<E>
        implements java.io.Serializable {

        private static final long serialVersionUID = 7911754171111800359L;

        /**
        /* <p>
        /* 
         * @serialField this$0 Subject The outer Subject instance.
         * @serialField elements LinkedList The elements in this set.
         */
        private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("this$0", Subject.class),
            new ObjectStreamField("elements", LinkedList.class),
            new ObjectStreamField("which", int.class)
        };

        Subject subject;
        LinkedList<E> elements;

        /**
        /* <p>
        /* 
         * @serial An integer identifying the type of objects contained
         *      in this set.  If {@code which == 1},
         *      this is a Principal set and all the elements are
         *      of type {@code java.security.Principal}.
         *      If {@code which == 2}, this is a public credential
         *      set and all the elements are of type {@code Object}.
         *      If {@code which == 3}, this is a private credential
         *      set and all the elements are of type {@code Object}.
         */
        private int which;

        SecureSet(Subject subject, int which) {
            this.subject = subject;
            this.which = which;
            this.elements = new LinkedList<E>();
        }

        SecureSet(Subject subject, int which, Set<? extends E> set) {
            this.subject = subject;
            this.which = which;
            this.elements = new LinkedList<E>(set);
        }

        public int size() {
            return elements.size();
        }

        public Iterator<E> iterator() {
            final LinkedList<E> list = elements;
            return new Iterator<E>() {
                ListIterator<E> i = list.listIterator(0);

                public boolean hasNext() {return i.hasNext();}

                public E next() {
                    if (which != Subject.PRIV_CREDENTIAL_SET) {
                        return i.next();
                    }

                    SecurityManager sm = System.getSecurityManager();
                    if (sm != null) {
                        try {
                            sm.checkPermission(new PrivateCredentialPermission
                                (list.get(i.nextIndex()).getClass().getName(),
                                subject.getPrincipals()));
                        } catch (SecurityException se) {
                            i.next();
                            throw (se);
                        }
                    }
                    return i.next();
                }

                public void remove() {

                    if (subject.isReadOnly()) {
                        throw new IllegalStateException(ResourcesMgr.getString
                                ("Subject.is.read.only"));
                    }

                    java.lang.SecurityManager sm = System.getSecurityManager();
                    if (sm != null) {
                        switch (which) {
                        case Subject.PRINCIPAL_SET:
                            sm.checkPermission(AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
                            break;
                        case Subject.PUB_CREDENTIAL_SET:
                            sm.checkPermission(AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
                            break;
                        default:
                            sm.checkPermission(AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
                            break;
                        }
                    }
                    i.remove();
                }
            };
        }

        public boolean add(E o) {

            if (subject.isReadOnly()) {
                throw new IllegalStateException
                        (ResourcesMgr.getString("Subject.is.read.only"));
            }

            java.lang.SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                switch (which) {
                case Subject.PRINCIPAL_SET:
                    sm.checkPermission(AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
                    break;
                case Subject.PUB_CREDENTIAL_SET:
                    sm.checkPermission(AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
                    break;
                default:
                    sm.checkPermission(AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
                    break;
                }
            }

            switch (which) {
            case Subject.PRINCIPAL_SET:
                if (!(o instanceof Principal)) {
                    throw new SecurityException(ResourcesMgr.getString
                        ("attempting.to.add.an.object.which.is.not.an.instance.of.java.security.Principal.to.a.Subject.s.Principal.Set"));
                }
                break;
            default:
                // ok to add Objects of any kind to credential sets
                break;
            }

            // check for duplicates
            if (!elements.contains(o))
                return elements.add(o);
            else
                return false;
        }

        public boolean remove(Object o) {

            final Iterator<E> e = iterator();
            while (e.hasNext()) {
                E next;
                if (which != Subject.PRIV_CREDENTIAL_SET) {
                    next = e.next();
                } else {
                    next = java.security.AccessController.doPrivileged
                        (new java.security.PrivilegedAction<E>() {
                        public E run() {
                            return e.next();
                        }
                    });
                }

                if (next == null) {
                    if (o == null) {
                        e.remove();
                        return true;
                    }
                } else if (next.equals(o)) {
                    e.remove();
                    return true;
                }
            }
            return false;
        }

        public boolean contains(Object o) {
            final Iterator<E> e = iterator();
            while (e.hasNext()) {
                E next;
                if (which != Subject.PRIV_CREDENTIAL_SET) {
                    next = e.next();
                } else {

                    // For private credentials:
                    // If the caller does not have read permission for
                    // for o.getClass(), we throw a SecurityException.
                    // Otherwise we check the private cred set to see whether
                    // it contains the Object

                    SecurityManager sm = System.getSecurityManager();
                    if (sm != null) {
                        sm.checkPermission(new PrivateCredentialPermission
                                                (o.getClass().getName(),
                                                subject.getPrincipals()));
                    }
                    next = java.security.AccessController.doPrivileged
                        (new java.security.PrivilegedAction<E>() {
                        public E run() {
                            return e.next();
                        }
                    });
                }

                if (next == null) {
                    if (o == null) {
                        return true;
                    }
                } else if (next.equals(o)) {
                    return true;
                }
            }
            return false;
        }

        public boolean removeAll(Collection<?> c) {
            Objects.requireNonNull(c);
            boolean modified = false;
            final Iterator<E> e = iterator();
            while (e.hasNext()) {
                E next;
                if (which != Subject.PRIV_CREDENTIAL_SET) {
                    next = e.next();
                } else {
                    next = java.security.AccessController.doPrivileged
                        (new java.security.PrivilegedAction<E>() {
                        public E run() {
                            return e.next();
                        }
                    });
                }

                Iterator<?> ce = c.iterator();
                while (ce.hasNext()) {
                    Object o = ce.next();
                    if (next == null) {
                        if (o == null) {
                            e.remove();
                            modified = true;
                            break;
                        }
                    } else if (next.equals(o)) {
                        e.remove();
                        modified = true;
                        break;
                    }
                }
            }
            return modified;
        }

        public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c);
            boolean modified = false;
            boolean retain = false;
            final Iterator<E> e = iterator();
            while (e.hasNext()) {
                retain = false;
                E next;
                if (which != Subject.PRIV_CREDENTIAL_SET) {
                    next = e.next();
                } else {
                    next = java.security.AccessController.doPrivileged
                        (new java.security.PrivilegedAction<E>() {
                        public E run() {
                            return e.next();
                        }
                    });
                }

                Iterator<?> ce = c.iterator();
                while (ce.hasNext()) {
                    Object o = ce.next();
                    if (next == null) {
                        if (o == null) {
                            retain = true;
                            break;
                        }
                    } else if (next.equals(o)) {
                        retain = true;
                        break;
                    }
                }

                if (!retain) {
                    e.remove();
                    retain = false;
                    modified = true;
                }
            }
            return modified;
        }

        public void clear() {
            final Iterator<E> e = iterator();
            while (e.hasNext()) {
                E next;
                if (which != Subject.PRIV_CREDENTIAL_SET) {
                    next = e.next();
                } else {
                    next = java.security.AccessController.doPrivileged
                        (new java.security.PrivilegedAction<E>() {
                        public E run() {
                            return e.next();
                        }
                    });
                }
                e.remove();
            }
        }

        /**
         * Writes this object out to a stream (i.e., serializes it).
         *
         * <p>
         *
         * <p>
         *  将此对象写入流(即,将其序列化)。
         * 
         * <p>
         * 
         * 
         * @serialData If this is a private credential set,
         *      a security check is performed to ensure that
         *      the caller has permission to access each credential
         *      in the set.  If the security check passes,
         *      the set is serialized.
         */
        private void writeObject(java.io.ObjectOutputStream oos)
                throws java.io.IOException {

            if (which == Subject.PRIV_CREDENTIAL_SET) {
                // check permissions before serializing
                Iterator<E> i = iterator();
                while (i.hasNext()) {
                    i.next();
                }
            }
            ObjectOutputStream.PutField fields = oos.putFields();
            fields.put("this$0", subject);
            fields.put("elements", elements);
            fields.put("which", which);
            oos.writeFields();
        }

        @SuppressWarnings("unchecked")
        private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException
        {
            ObjectInputStream.GetField fields = ois.readFields();
            subject = (Subject) fields.get("this$0", null);
            which = fields.get("which", 0);

            LinkedList<E> tmp = (LinkedList<E>) fields.get("elements", null);
            if (tmp.getClass() != LinkedList.class) {
                elements = new LinkedList<E>(tmp);
            } else {
                elements = tmp;
            }
        }
    }

    /**
     * This class implements a {@code Set} which returns only
     * members that are an instance of a specified Class.
     * <p>
     */
    private class ClassSet<T> extends AbstractSet<T> {

        private int which;
        private Class<T> c;
        private Set<T> set;

        ClassSet(int which, Class<T> c) {
            this.which = which;
            this.c = c;
            set = new HashSet<T>();

            switch (which) {
            case Subject.PRINCIPAL_SET:
                synchronized(principals) { populateSet(); }
                break;
            case Subject.PUB_CREDENTIAL_SET:
                synchronized(pubCredentials) { populateSet(); }
                break;
            default:
                synchronized(privCredentials) { populateSet(); }
                break;
            }
        }

        @SuppressWarnings("unchecked")     /*To suppress warning from line 1374*/
        private void populateSet() {
            final Iterator<?> iterator;
            switch(which) {
            case Subject.PRINCIPAL_SET:
                iterator = Subject.this.principals.iterator();
                break;
            case Subject.PUB_CREDENTIAL_SET:
                iterator = Subject.this.pubCredentials.iterator();
                break;
            default:
                iterator = Subject.this.privCredentials.iterator();
                break;
            }

            // Check whether the caller has permisson to get
            // credentials of Class c

            while (iterator.hasNext()) {
                Object next;
                if (which == Subject.PRIV_CREDENTIAL_SET) {
                    next = java.security.AccessController.doPrivileged
                        (new java.security.PrivilegedAction<Object>() {
                        public Object run() {
                            return iterator.next();
                        }
                    });
                } else {
                    next = iterator.next();
                }
                if (c.isAssignableFrom(next.getClass())) {
                    if (which != Subject.PRIV_CREDENTIAL_SET) {
                        set.add((T)next);
                    } else {
                        // Check permission for private creds
                        SecurityManager sm = System.getSecurityManager();
                        if (sm != null) {
                            sm.checkPermission(new PrivateCredentialPermission
                                                (next.getClass().getName(),
                                                Subject.this.getPrincipals()));
                        }
                        set.add((T)next);
                    }
                }
            }
        }

        public int size() {
            return set.size();
        }

        public Iterator<T> iterator() {
            return set.iterator();
        }

        public boolean add(T o) {

            if (!o.getClass().isAssignableFrom(c)) {
                MessageFormat form = new MessageFormat(ResourcesMgr.getString
                        ("attempting.to.add.an.object.which.is.not.an.instance.of.class"));
                Object[] source = {c.toString()};
                throw new SecurityException(form.format(source));
            }

            return set.add(o);
        }
    }

    static class AuthPermissionHolder {
        static final AuthPermission DO_AS_PERMISSION =
            new AuthPermission("doAs");

        static final AuthPermission DO_AS_PRIVILEGED_PERMISSION =
            new AuthPermission("doAsPrivileged");

        static final AuthPermission SET_READ_ONLY_PERMISSION =
            new AuthPermission("setReadOnly");

        static final AuthPermission GET_SUBJECT_PERMISSION =
            new AuthPermission("getSubject");

        static final AuthPermission MODIFY_PRINCIPALS_PERMISSION =
            new AuthPermission("modifyPrincipals");

        static final AuthPermission MODIFY_PUBLIC_CREDENTIALS_PERMISSION =
            new AuthPermission("modifyPublicCredentials");

        static final AuthPermission MODIFY_PRIVATE_CREDENTIALS_PERMISSION =
            new AuthPermission("modifyPrivateCredentials");
    }
}
