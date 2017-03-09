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

/**
 * This class is for authentication permissions.
 * An AuthPermission contains a name
 * (also referred to as a "target name")
 * but no actions list; you either have the named permission
 * or you don't.
 *
 * <p> The target name is the name of a security configuration parameter
 * (see below).  Currently the AuthPermission object is used to
 * guard access to the Policy, Subject, LoginContext,
 * and Configuration objects.
 *
 * <p> The possible target names for an Authentication Permission are:
 *
 * <pre>
 *      doAs -                  allow the caller to invoke the
 *                              {@code Subject.doAs} methods.
 *
 *      doAsPrivileged -        allow the caller to invoke the
 *                              {@code Subject.doAsPrivileged} methods.
 *
 *      getSubject -            allow for the retrieval of the
 *                              Subject(s) associated with the
 *                              current Thread.
 *
 *      getSubjectFromDomainCombiner -  allow for the retrieval of the
 *                              Subject associated with the
 *                              a {@code SubjectDomainCombiner}.
 *
 *      setReadOnly -           allow the caller to set a Subject
 *                              to be read-only.
 *
 *      modifyPrincipals -      allow the caller to modify the {@code Set}
 *                              of Principals associated with a
 *                              {@code Subject}
 *
 *      modifyPublicCredentials - allow the caller to modify the
 *                              {@code Set} of public credentials
 *                              associated with a {@code Subject}
 *
 *      modifyPrivateCredentials - allow the caller to modify the
 *                              {@code Set} of private credentials
 *                              associated with a {@code Subject}
 *
 *      refreshCredential -     allow code to invoke the {@code refresh}
 *                              method on a credential which implements
 *                              the {@code Refreshable} interface.
 *
 *      destroyCredential -     allow code to invoke the {@code destroy}
 *                              method on a credential {@code object}
 *                              which implements the {@code Destroyable}
 *                              interface.
 *
 *      createLoginContext.{name} -  allow code to instantiate a
 *                              {@code LoginContext} with the
 *                              specified <i>name</i>.  <i>name</i>
 *                              is used as the index into the installed login
 *                              {@code Configuration}
 *                              (that returned by
 *                              {@code Configuration.getConfiguration()}).
 *                              <i>name</i> can be wildcarded (set to '*')
 *                              to allow for any name.
 *
 *      getLoginConfiguration - allow for the retrieval of the system-wide
 *                              login Configuration.
 *
 *      createLoginConfiguration.{type} - allow code to obtain a Configuration
 *                              object via
 *                              {@code Configuration.getInstance}.
 *
 *      setLoginConfiguration - allow for the setting of the system-wide
 *                              login Configuration.
 *
 *      refreshLoginConfiguration - allow for the refreshing of the system-wide
 *                              login Configuration.
 * </pre>
 *
 * <p> The following target name has been deprecated in favor of
 * {@code createLoginContext.{name}}.
 *
 * <pre>
 *      createLoginContext -    allow code to instantiate a
 *                              {@code LoginContext}.
 * </pre>
 *
 * <p> {@code javax.security.auth.Policy} has been
 * deprecated in favor of {@code java.security.Policy}.
 * Therefore, the following target names have also been deprecated:
 *
 * <pre>
 *      getPolicy -             allow the caller to retrieve the system-wide
 *                              Subject-based access control policy.
 *
 *      setPolicy -             allow the caller to set the system-wide
 *                              Subject-based access control policy.
 *
 *      refreshPolicy -         allow the caller to refresh the system-wide
 *                              Subject-based access control policy.
 * </pre>
 *
 * <p>
 *  此类用于认证权限。 AuthPermission包含名称(也称为"目标名称"),但没有动作列表;你有命名的权限或你不。
 * 
 *  <p>目标名称是安全配置参数的名称(见下文)。目前,AuthPermission对象用于保护对Policy,Subject,LoginContext和Configuration对象的访问。
 * 
 *  <p>身份验证权限的可能目标名称为：
 * 
 * <pre>
 *  doAs  - 允许调用者调用{@code Subject.doAs}方法。
 * 
 *  doAsPrivileged  - 允许调用者调用{@code Subject.doAsPrivileged}方法。
 * 
 *  getSubject  - 允许检索与当前线程相关联的主题。
 * 
 *  getSubjectFromDomainCombiner  - 允许检索与a {@code SubjectDomainCombiner}相关联的主题。
 * 
 *  setReadOnly  - 允许调用者将主题设置为只读。
 * 
 *  modifyPrincipals  - 允许调用者修改与{@code Subject}相关联的{@code Set}
 * 
 *  modifyPublicCredentials  - 允许调用者修改与{@code Subject}相关联的公共凭证的{@code Set}
 * 
 *  modifyPrivateCredentials  - 允许调用者修改与{@code Subject}相关联的私有凭据的{@code Set}
 * 
 * refreshCredential  - 允许代码在实现{@code Refreshable}接口的凭据上调用{@code refresh}方法。
 * 
 *  destroyCredential  - 允许代码调用在实现{@code Destroyable}接口的凭据{@code object}上的{@code destroy}方法。
 * 
 *  createLoginContext。{name}  - 允许代码用指定的<i>名称</i>实例化{@code LoginContext}。
 *  <i> name </i>用作安装的登录{@code Configuration}(由{@code Configuration.getConfiguration()}返回的索引)。
 *  <i>名称</i>可以是通配符(设置为"*")以允许任何名称。
 * 
 *  getLoginConfiguration  - 允许检索系统范围的登录配置。
 * 
 */
public final class AuthPermission extends
java.security.BasicPermission {

    private static final long serialVersionUID = 5806031445061587174L;

    /**
     * Creates a new AuthPermission with the specified name.
     * The name is the symbolic name of the AuthPermission.
     *
     * <p>
     *
     * <p>
     *  createLoginConfiguration {type}  - 允许代码通过{@code Configuration.getInstance}获取一个Configuration对象。
     * 
     *  setLoginConfiguration  - 允许设置系统范围的登录配置。
     * 
     *  refreshLoginConfiguration  - 允许刷新系统范围的登录配置。
     * </pre>
     * 
     *  <p>以下目标名称已被弃用,赞成{@code createLoginContext。{name}}。
     * 
     * <pre>
     *  createLoginContext  - 允许代码实例化{@code LoginContext}。
     * </pre>
     * 
     *  <p> {@code javax.security.auth.Policy}已弃用,赞成{@code java.security.Policy}。因此,以下目标名称也已被弃用：
     * 
     * <pre>
     *  getPolicy  - 允许调用者检索基于系统的基于主题的访问控制策略。
     * 
     * @param name the name of the AuthPermission
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public AuthPermission(String name) {
        // for backwards compatibility --
        // createLoginContext is deprecated in favor of createLoginContext.*
        super("createLoginContext".equals(name) ?
                "createLoginContext.*" : name);
    }

    /**
     * Creates a new AuthPermission object with the specified name.
     * The name is the symbolic name of the AuthPermission, and the
     * actions String is currently unused and should be null.
     *
     * <p>
     *
     * <p>
     * 
     * setPolicy  - 允许调用者设置系统范围的基于主题的访问控制策略。
     * 
     *  refreshPolicy  - 允许调用者刷新系统范围的基于主题的访问控制策略。
     * </pre>
     * 
     * 
     * @param name the name of the AuthPermission <p>
     *
     * @param actions should be null.
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public AuthPermission(String name, String actions) {
        // for backwards compatibility --
        // createLoginContext is deprecated in favor of createLoginContext.*
        super("createLoginContext".equals(name) ?
                "createLoginContext.*" : name, actions);
    }
}
