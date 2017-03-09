/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.remote;

import java.security.BasicPermission;

/**
 * <p>Permission required by an authentication identity to perform
 * operations on behalf of an authorization identity.</p>
 *
 * <p>A SubjectDelegationPermission contains a name (also referred
 * to as a "target name") but no actions list; you either have the
 * named permission or you don't.</p>
 *
 * <p>The target name is the name of the authorization principal
 * classname followed by a period and the authorization principal
 * name, that is
 * <code>"<em>PrincipalClassName</em>.<em>PrincipalName</em>"</code>.</p>
 *
 * <p>An asterisk may appear by itself, or if immediately preceded
 * by a "." may appear at the end of the target name, to signify a
 * wildcard match.</p>
 *
 * <p>For example, "*", "javax.management.remote.JMXPrincipal.*" and
 * "javax.management.remote.JMXPrincipal.delegate" are valid target
 * names. The first one denotes any principal name from any principal
 * class, the second one denotes any principal name of the concrete
 * principal class <code>javax.management.remote.JMXPrincipal</code>
 * and the third one denotes a concrete principal name
 * <code>delegate</code> of the concrete principal class
 * <code>javax.management.remote.JMXPrincipal</code>.</p>
 *
 * <p>
 *  <p>身份验证身份代表授权身份执行操作所需的权限。</p>
 * 
 *  <p> SubjectDelegationPermission包含名称(也称为"目标名称"),但没有操作列表;您可以拥有指定的权限,也可以不指定权限。</p>
 * 
 *  <p>目标名称是授权主体类名称的名称,后跟一个句点和授权主体名称,即<code>"<em> PrincipalClassName </em>。<em> PrincipalName </em>代码>。
 * </p>。
 * 
 *  <p>星号可以单独显示,或者紧挨着一个"。"。可能会出现在目标名称的末尾,表示通配符匹配。</p>
 * 
 *  <p>例如,"*","javax.management.remote.JMXPrincipal。
 * *"和"javax.management.remote.JMXPrincipal.delegate"是有效的目标名称。
 * 
 * @since 1.5
 */
public final class SubjectDelegationPermission extends BasicPermission {

    private static final long serialVersionUID = 1481618113008682343L;

    /**
     * Creates a new SubjectDelegationPermission with the specified name.
     * The name is the symbolic name of the SubjectDelegationPermission.
     *
     * <p>
     * 第一个表示任何主类的任何主体名称,第二个表示具体主体类的任何主体名称<code> javax.management.remote.JMXPrincipal </code>,第三个表示具体主体名称<code>
     * 具体主体类的代理</code> <code> javax.management.remote.JMXPrincipal </p>。
     * *"和"javax.management.remote.JMXPrincipal.delegate"是有效的目标名称。</p>。
     * 
     * 
     * @param name the name of the SubjectDelegationPermission
     *
     * @throws NullPointerException if <code>name</code> is
     * <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */
    public SubjectDelegationPermission(String name) {
        super(name);
    }

    /**
     * Creates a new SubjectDelegationPermission object with the
     * specified name.  The name is the symbolic name of the
     * SubjectDelegationPermission, and the actions String is
     * currently unused and must be null.
     *
     * <p>
     *  创建具有指定名称的新SubjectDelegationPermission。名称是SubjectDelegationPermission的符号名称。
     * 
     * 
     * @param name the name of the SubjectDelegationPermission
     * @param actions must be null.
     *
     * @throws NullPointerException if <code>name</code> is
     * <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty
     * or <code>actions</code> is not null.
     */
    public SubjectDelegationPermission(String name, String actions) {
        super(name, actions);

        if (actions != null)
            throw new IllegalArgumentException("Non-null actions");
    }
}
