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

package com.sun.security.auth;

import java.security.CodeSource;
import java.security.PermissionCollection;
import javax.security.auth.Subject;

/**
 * This class represents a default implementation for
 * <code>javax.security.auth.Policy</code>.
 *
 * <p> This object stores the policy for entire Java runtime,
 * and is the amalgamation of multiple static policy
 * configurations that resides in files.
 * The algorithm for locating the policy file(s) and reading their
 * information into this <code>Policy</code> object is:
 *
 * <ol>
 * <li>
 *   Loop through the security properties,
 *   <i>auth.policy.url.1</i>, <i>auth.policy.url.2</i>, ...,
 *   <i>auth.policy.url.X</i>".
 *   Each property value specifies a <code>URL</code> pointing to a
 *   policy file to be loaded.  Read in and load each policy.
 *
 * <li>
 *   The <code>java.lang.System</code> property <i>java.security.auth.policy</i>
 *   may also be set to a <code>URL</code> pointing to another policy file
 *   (which is the case when a user uses the -D switch at runtime).
 *   If this property is defined, and its use is allowed by the
 *   security property file (the Security property,
 *   <i>policy.allowSystemProperty</i> is set to <i>true</i>),
 *   also load that policy.
 *
 * <li>
 *   If the <i>java.security.auth.policy</i> property is defined using
 *   "==" (rather than "="), then ignore all other specified
 *   policies and only load this policy.
 * </ol>
 *
 * Each policy file consists of one or more grant entries, each of
 * which consists of a number of permission entries.
 *
 * <pre>
 *   grant signedBy "<b>alias</b>", codeBase "<b>URL</b>",
 *         principal <b>principalClass</b> "<b>principalName</b>",
 *         principal <b>principalClass</b> "<b>principalName</b>",
 *         ... {
 *
 *     permission <b>Type</b> "<b>name</b> "<b>action</b>",
 *         signedBy "<b>alias</b>";
 *     permission <b>Type</b> "<b>name</b> "<b>action</b>",
 *         signedBy "<b>alias</b>";
 *     ....
 *   };
 * </pre>
 *
 * All non-bold items above must appear as is (although case
 * doesn't matter and some are optional, as noted below).
 * Italicized items represent variable values.
 *
 * <p> A grant entry must begin with the word <code>grant</code>.
 * The <code>signedBy</code> and <code>codeBase</code>
 * name/value pairs are optional.
 * If they are not present, then any signer (including unsigned code)
 * will match, and any codeBase will match.  Note that the
 * <code>principal</code> name/value pair is not optional.
 * This <code>Policy</code> implementation only permits
 * Principal-based grant entries.  Note that the <i>principalClass</i>
 * may be set to the wildcard value, *, which allows it to match
 * any <code>Principal</code> class.  In addition, the <i>principalName</i>
 * may also be set to the wildcard value, *, allowing it to match
 * any <code>Principal</code> name.  When setting the <i>principalName</i>
 * to the *, do not surround the * with quotes.
 *
 * <p> A permission entry must begin with the word <code>permission</code>.
 * The word <code><i>Type</i></code> in the template above is
 * a specific permission type, such as <code>java.io.FilePermission</code>
 * or <code>java.lang.RuntimePermission</code>.
 *
 * <p> The "<i>action</i>" is required for
 * many permission types, such as <code>java.io.FilePermission</code>
 * (where it specifies what type of file access that is permitted).
 * It is not required for categories such as
 * <code>java.lang.RuntimePermission</code>
 * where it is not necessary - you either have the
 * permission specified by the <code>"<i>name</i>"</code>
 * value following the type name or you don't.
 *
 * <p> The <code>signedBy</code> name/value pair for a permission entry
 * is optional. If present, it indicates a signed permission. That is,
 * the permission class itself must be signed by the given alias in
 * order for it to be granted. For example,
 * suppose you have the following grant entry:
 *
 * <pre>
 *   grant principal foo.com.Principal "Duke" {
 *     permission Foo "foobar", signedBy "FooSoft";
 *   }
 * </pre>
 *
 * <p> Then this permission of type <i>Foo</i> is granted if the
 * <code>Foo.class</code> permission has been signed by the
 * "FooSoft" alias, or if <code>Foo.class</code> is a
 * system class (i.e., is found on the CLASSPATH).
 *
 * <p> Items that appear in an entry must appear in the specified order
 * (<code>permission</code>, <i>Type</i>, "<i>name</i>", and
 * "<i>action</i>"). An entry is terminated with a semicolon.
 *
 * <p> Case is unimportant for the identifiers (<code>permission</code>,
 * <code>signedBy</code>, <code>codeBase</code>, etc.) but is
 * significant for the <i>Type</i>
 * or for any string that is passed in as a value. <p>
 *
 * <p> An example of two entries in a policy configuration file is
 * <pre>
 *   // if the code is comes from "foo.com" and is running as "Duke",
 *   // grant it read/write to all files in /tmp.
 *
 *   grant codeBase "foo.com", principal foo.com.Principal "Duke" {
 *              permission java.io.FilePermission "/tmp/*", "read,write";
 *   };
 *
 *   // grant any code running as "Duke" permission to read
 *   // the "java.vendor" Property.
 *
 *   grant principal foo.com.Principal "Duke" {
 *         permission java.util.PropertyPermission "java.vendor";
 * </pre>
 *
 * <p> This <code>Policy</code> implementation supports
 * special handling for PrivateCredentialPermissions.
 * If a grant entry is configured with a
 * <code>PrivateCredentialPermission</code>,
 * and the "Principal Class/Principal Name" for that
 * <code>PrivateCredentialPermission</code> is "self",
 * then the entry grants the specified <code>Subject</code> permission to
 * access its own private Credential.  For example,
 * the following grants the <code>Subject</code> "Duke"
 * access to its own a.b.Credential.
 *
 * <pre>
 *   grant principal foo.com.Principal "Duke" {
 *      permission javax.security.auth.PrivateCredentialPermission
 *              "a.b.Credential self",
 *              "read";
 *    };
 * </pre>
 *
 * The following grants the <code>Subject</code> "Duke"
 * access to all of its own private Credentials:
 *
 * <pre>
 *   grant principal foo.com.Principal "Duke" {
 *      permission javax.security.auth.PrivateCredentialPermission
 *              "* self",
 *              "read";
 *    };
 * </pre>
 *
 * The following grants all Subjects authenticated as a
 * <code>SolarisPrincipal</code> (regardless of their respective names)
 * permission to access their own private Credentials:
 *
 * <pre>
 *   grant principal com.sun.security.auth.SolarisPrincipal * {
 *      permission javax.security.auth.PrivateCredentialPermission
 *              "* self",
 *              "read";
 *    };
 * </pre>
 *
 * The following grants all Subjects permission to access their own
 * private Credentials:
 *
 * <pre>
 *   grant principal * * {
 *      permission javax.security.auth.PrivateCredentialPermission
 *              "* self",
 *              "read";
 *    };
 * </pre>

 * <p>
 *  此类表示<code> javax.security.auth.Policy </code>的默认实现。
 * 
 *  <p>此对象存储整个Java运行时的策略,并且是驻留在文件中的多个静态策略配置的合并。用于定位策略文件并将其信息读入此<code> Policy </code>对象的算法是：
 * 
 * <ol>
 * <li>
 *  循环浏览安全属性<a> auth.policy.url.1 </i>,<a> auth.policy.url.2 </i>,...,<a> auth.policy.url.X </i>"。
 * 每个属性值指定一个指向要加载的策略文件的<code> URL </code>。读入并加载每个策略。
 * 
 * <li>
 *  <code> java.lang.System </code>属性<i> java.security.auth.policy </i>也可以设置为指向另一个策略文件的<code> URL </code>
 * 当用户在运行时使用-D开关的情况)。
 * 如果定义此属性,并且安全属性文件允许其使用(Security属性,<i> policy.allowSystemProperty </i>设置为<i> true </i>),也请加载该策略。
 * 
 * <li>
 *  如果使用"=="(而不是"=")定义<i> java.security.auth.policy </i>属性,则忽略所有其他指定的策略,并且只加载此策略。
 * </ol>
 * 
 *  每个策略文件由一个或多个授权条目组成,每个授权条目由多个权限条目组成。
 * 
 * <pre>
 * </b>"<b> </b>"<b> </b>"<b> </b> </b>"<b> principalName </b>",... {
 * 
 *  </b> </b> <b> </b> <b> <b> </b> "<b> name </b>"<b> action </b>",signedBy"<b>别名</b>"; ....};
 * </pre>
 * 
 *  上面的所有非粗体项目必须按原样显示(虽然大小写不重要,有些是可选的,如下所述)。斜体项表示变量值。
 * 
 *  <p>授权项必须以字<code> grant </code>开头。 <code> signedBy </code>和<code> codeBase </code>名称/值对是可选的。
 * 如果它们不存在,则任何签名者(包括未签名的代码)将匹配,并且任何codeBase将匹配。请注意,<code> principal </code>名称/值对不是可选的。
 * 此<code> Policy </code>实现仅允许基于主体的授权条目。
 * 请注意,<i> principalClass </i>可以设置为通配符值*,它允许它匹配任何<code> Principal </code>类。
 * 此外,<i> principalName </i>也可以设置为通配符值*,允许它匹配任何<code> Principal </code>名称。
 * 将<i> principalName </i>设置为*时,不要用引号括住*。
 * 
 * <p>权限输入必须以<code> permission </code>开头。
 * 上述模板中的<code> <i> Type </i> </code>是特定的权限类型,例如<code> java.io.FilePermission </code>或<code> java.lang.R
 * untimePermission </code>。
 * <p>权限输入必须以<code> permission </code>开头。
 * 
 *  <p>许多权限类型(例如<code> java.io.FilePermission </code>(它指定允许的文件访问类型)需要"<i> action </i>"。
 * 它不是必需的类别,如<code> java.lang.RuntimePermission </code>在这里不必要 - 您有<code>"<i> name </i>"</code >值跟在类型名称后面
 * ,或者不是。
 *  <p>许多权限类型(例如<code> java.io.FilePermission </code>(它指定允许的文件访问类型)需要"<i> action </i>"。
 * 
 *  <p>权限条目的<code> signedBy </code>名称/值对是可选的。如果存在,它表示签名的权限。也就是说,权限类本身必须由给定的别名签名,以便授予它。例如,假设您具有以下授权条目：
 * 
 * <pre>
 *  grant principal foo.com.Principal"Duke"{permission Foo"foobar",signedBy"FooSoft"; }}
 * </pre>
 * 
 *  <p>然后,如果<code> Foo.class </code>权限已由"FooSoft"别名签名,或者如果<code> Foo.class < / code>是一个系统类(即,在CLASSPATH上
 * 找到)。
 * 
 *  <p>条目中显示的项目必须以指定的顺序显示(<code> permission </code>,<i> Type </i>,"<i> name </i>"和"<i>动作</i>")。条目以分号结尾。
 * 
 * <p>案例对于标识符(<code> permission </code>,<code> signedBy </code>,<code> codeBase </code>等)并不重要, / i>或作为值传
 * 递的任何字符串。
 *  <p>。
 * 
 *  <p>策略配置文件中的两个条目的示例为
 * <pre>
 *  //如果代码来自"foo.com"并且运行为"Duke",//授予它对/ tmp中所有文件的读/写。
 * 
 *  授权代码库"foo.com",主体foo.com.Principal"Duke"{permission java.io.FilePermission"/ tmp / *","read,write"; }
 * ;。
 * 
 *  //授予任何运行为"Duke"权限的代码来读取"java.vendor"属性。
 * 
 *  grant principal foo.com.Principal"Duke"{permission java.util.PropertyPermission"java.vendor";
 * </pre>
 * 
 * @deprecated As of JDK&nbsp;1.4, replaced by
 *             <code>sun.security.provider.PolicyFile</code>.
 *             This class is entirely deprecated.
 *
 * @see java.security.CodeSource
 * @see java.security.Permissions
 * @see java.security.ProtectionDomain
 * @see java.security.Security security properties
 */
@jdk.Exported(false)
@Deprecated
public class PolicyFile extends javax.security.auth.Policy {

    private final sun.security.provider.AuthPolicyFile apf;

    /**
     * Initializes the Policy object and reads the default policy
     * configuration file(s) into the Policy object.
     * <p>
     * 
     *  <p>此<code> Policy </code>实施支持PrivateCredentialPermissions的特殊处理。
     * 如果授权条目配置有<code> PrivateCredentialPermission </code>,并且<code> PrivateCredentialPermission </code>的"主体类
     * /主体名称"是"self",则该条目授予指定的<code>主题</code>访问其自己的私有Credential的权限。
     *  <p>此<code> Policy </code>实施支持PrivateCredentialPermissions的特殊处理。
     * 例如,以下授予<code> Subject </code>"Duke"访问其自己的a.b.Credential。
     * 
     * <pre>
     *  grant principal foo.com.Principal"Duke"{permission javax.security.auth.PrivateCredentialPermission"a.b.Credential self","read"; }
     * ;。
     * </pre>
     * 
     *  以下授予<code>主题</code>"Duke"对其所有私有凭据的访问权限：
     * 
     * <pre>
     * grant principal foo.com.Principal"Duke"{permission javax.security.auth.PrivateCredentialPermission"* self","read"; }
     * ;。
     * </pre>
     * 
     *  以下授予所有经过身份验证为<code> SolarisPrincipal </code>(无论其各自的名称)的主体访问其自己的私有凭据的权限：
     * 
     * <pre>
     *  grant principal com.sun.security.auth.SolarisPrincipal * {permission javax.security.auth.PrivateCredentialPermission"* self","read"; }
     * ;。
     * </pre>
     * 
     *  以下授予所有主体访问他们自己的私人凭据的权限：
     * 
     * <pre>
     *  grant principal * * {permission javax.security.auth.PrivateCredentialPermission"* self","read"; };
     * </pre>
     * 
     */
    public PolicyFile() {
        apf = new sun.security.provider.AuthPolicyFile();
    }

    /**
     * Refreshes the policy object by re-reading all the policy files.
     *
     * <p>
     *
     * <p>
     *  初始化Policy对象并将默认策略配置文件读入Policy对象。
     * 
     * 
     * @exception SecurityException if the caller doesn't have permission
     *          to refresh the <code>Policy</code>.
     */
    @Override
    public void refresh() {
        apf.refresh();
    }

    /**
     * Examines this <code>Policy</code> and returns the Permissions granted
     * to the specified <code>Subject</code> and <code>CodeSource</code>.
     *
     * <p> Permissions for a particular <i>grant</i> entry are returned
     * if the <code>CodeSource</code> constructed using the codebase and
     * signedby values specified in the entry <code>implies</code>
     * the <code>CodeSource</code> provided to this method, and if the
     * <code>Subject</code> provided to this method contains all of the
     * Principals specified in the entry.
     *
     * <p> The <code>Subject</code> provided to this method contains all
     * of the Principals specified in the entry if, for each
     * <code>Principal</code>, "P1", specified in the <i>grant</i> entry
     * one of the following two conditions is met:
     *
     * <p>
     * <ol>
     * <li> the <code>Subject</code> has a
     *      <code>Principal</code>, "P2", where
     *      <code>P2.getClass().getName()</code> equals the
     *      P1's class name, and where
     *      <code>P2.getName()</code> equals the P1's name.
     *
     * <li> P1 implements
     *      <code>com.sun.security.auth.PrincipalComparator</code>,
     *      and <code>P1.implies</code> the provided <code>Subject</code>.
     * </ol>
     *
     * <p> Note that this <code>Policy</code> implementation has
     * special handling for PrivateCredentialPermissions.
     * When this method encounters a <code>PrivateCredentialPermission</code>
     * which specifies "self" as the <code>Principal</code> class and name,
     * it does not add that <code>Permission</code> to the returned
     * <code>PermissionCollection</code>.  Instead, it builds
     * a new <code>PrivateCredentialPermission</code>
     * for each <code>Principal</code> associated with the provided
     * <code>Subject</code>.  Each new <code>PrivateCredentialPermission</code>
     * contains the same Credential class as specified in the
     * originally granted permission, as well as the Class and name
     * for the respective <code>Principal</code>.
     *
     * <p>
     *
     * <p>
     *  通过重新读取所有策略文件刷新策略对象。
     * 
     * <p>
     * 
     * 
     * @param subject the Permissions granted to this <code>Subject</code>
     *          and the additionally provided <code>CodeSource</code>
     *          are returned. <p>
     *
     * @param codesource the Permissions granted to this <code>CodeSource</code>
     *          and the additionally provided <code>Subject</code>
     *          are returned.
     *
     * @return the Permissions granted to the provided <code>Subject</code>
     *          <code>CodeSource</code>.
     */
    @Override
    public PermissionCollection getPermissions(final Subject subject,
                                               final CodeSource codesource) {
        return apf.getPermissions(subject, codesource);
    }
}
