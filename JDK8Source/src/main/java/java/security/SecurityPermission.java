/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.security.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This class is for security permissions.
 * A SecurityPermission contains a name (also referred to as a "target name")
 * but no actions list; you either have the named permission
 * or you don't.
 * <P>
 * The target name is the name of a security configuration parameter (see below).
 * Currently the SecurityPermission object is used to guard access
 * to the Policy, Security, Provider, Signer, and Identity
 * objects.
 * <P>
 * The following table lists all the possible SecurityPermission target names,
 * and for each provides a description of what the permission allows
 * and a discussion of the risks of granting code the permission.
 *
 * <table border=1 cellpadding=5 summary="target name,what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 *   <td>createAccessControlContext</td>
 *   <td>Creation of an AccessControlContext</td>
 *   <td>This allows someone to instantiate an AccessControlContext
 * with a {@code DomainCombiner}.  Extreme care must be taken when
 * granting this permission. Malicious code could create a DomainCombiner
 * that augments the set of permissions granted to code, and even grant the
 * code {@link java.security.AllPermission}.</td>
 * </tr>
 *
 * <tr>
 *   <td>getDomainCombiner</td>
 *   <td>Retrieval of an AccessControlContext's DomainCombiner</td>
 *   <td>This allows someone to retrieve an AccessControlContext's
 * {@code DomainCombiner}.  Since DomainCombiners may contain
 * sensitive information, this could potentially lead to a privacy leak.</td>
 * </tr>
 *
 * <tr>
 *   <td>getPolicy</td>
 *   <td>Retrieval of the system-wide security policy (specifically, of the
 * currently-installed Policy object)</td>
 *   <td>This allows someone to query the policy via the
 * {@code getPermissions} call,
 * which discloses which permissions would be granted to a given CodeSource.
 * While revealing the policy does not compromise the security of
 * the system, it does provide malicious code with additional information
 * which it may use to better aim an attack. It is wise
 * not to divulge more information than necessary.</td>
 * </tr>
 *
 * <tr>
 *   <td>setPolicy</td>
 *   <td>Setting of the system-wide security policy (specifically,
 * the Policy object)</td>
 *   <td>Granting this permission is extremely dangerous, as malicious
 * code may grant itself all the necessary permissions it needs
 * to successfully mount an attack on the system.</td>
 * </tr>
 *
 * <tr>
 *   <td>createPolicy.{policy type}</td>
 *   <td>Getting an instance of a Policy implementation from a provider</td>
 *   <td>Granting this permission enables code to obtain a Policy object.
 * Malicious code may query the Policy object to determine what permissions
 * have been granted to code other than itself. </td>
 * </tr>
 *
 * <tr>
 *   <td>getProperty.{key}</td>
 *   <td>Retrieval of the security property with the specified key</td>
 *   <td>Depending on the particular key for which access has
 * been granted, the code may have access to the list of security
 * providers, as well as the location of the system-wide and user
 * security policies.  while revealing this information does not
 * compromise the security of the system, it does provide malicious
 * code with additional information which it may use to better aim
 * an attack.
</td>
 * </tr>
 *
 * <tr>
 *   <td>setProperty.{key}</td>
 *   <td>Setting of the security property with the specified key</td>
 *   <td>This could include setting a security provider or defining
 * the location of the system-wide security policy.  Malicious
 * code that has permission to set a new security provider may
 * set a rogue provider that steals confidential information such
 * as cryptographic private keys. In addition, malicious code with
 * permission to set the location of the system-wide security policy
 * may point it to a security policy that grants the attacker
 * all the necessary permissions it requires to successfully mount
 * an attack on the system.
</td>
 * </tr>
 *
 * <tr>
 *   <td>insertProvider</td>
 *   <td>Addition of a new provider</td>
 *   <td>This would allow somebody to introduce a possibly
 * malicious provider (e.g., one that discloses the private keys passed
 * to it) as the highest-priority provider. This would be possible
 * because the Security object (which manages the installed providers)
 * currently does not check the integrity or authenticity of a provider
 * before attaching it. The "insertProvider" permission subsumes the
 * "insertProvider.{provider name}" permission (see the section below for
 * more information).
 * </td>
 * </tr>
 *
 * <tr>
 *   <td>removeProvider.{provider name}</td>
 *   <td>Removal of the specified provider</td>
 *   <td>This may change the behavior or disable execution of other
 * parts of the program. If a provider subsequently requested by the
 * program has been removed, execution may fail. Also, if the removed
 * provider is not explicitly requested by the rest of the program, but
 * it would normally be the provider chosen when a cryptography service
 * is requested (due to its previous order in the list of providers),
 * a different provider will be chosen instead, or no suitable provider
 * will be found, thereby resulting in program failure.</td>
 * </tr>
 *
 * <tr>
 *   <td>clearProviderProperties.{provider name}</td>
 *   <td>"Clearing" of a Provider so that it no longer contains the properties
 * used to look up services implemented by the provider</td>
 *   <td>This disables the lookup of services implemented by the provider.
 * This may thus change the behavior or disable execution of other
 * parts of the program that would normally utilize the Provider, as
 * described under the "removeProvider.{provider name}" permission.</td>
 * </tr>
 *
 * <tr>
 *   <td>putProviderProperty.{provider name}</td>
 *   <td>Setting of properties for the specified Provider</td>
 *   <td>The provider properties each specify the name and location
 * of a particular service implemented by the provider. By granting
 * this permission, you let code replace the service specification
 * with another one, thereby specifying a different implementation.</td>
 * </tr>
 *
 * <tr>
 *   <td>removeProviderProperty.{provider name}</td>
 *   <td>Removal of properties from the specified Provider</td>
 *   <td>This disables the lookup of services implemented by the
 * provider. They are no longer accessible due to removal of the properties
 * specifying their names and locations. This
 * may change the behavior or disable execution of other
 * parts of the program that would normally utilize the Provider, as
 * described under the "removeProvider.{provider name}" permission.</td>
 * </tr>
 *
 * </table>
 *
 * <P>
 * The following permissions have been superseded by newer permissions or are
 * associated with classes that have been deprecated: {@link Identity},
 * {@link IdentityScope}, {@link Signer}. Use of them is discouraged. See the
 * applicable classes for more information.
 *
 * <table border=1 cellpadding=5 summary="target name,what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 *   <td>insertProvider.{provider name}</td>
 *   <td>Addition of a new provider, with the specified name</td>
 *   <td>Use of this permission is discouraged from further use because it is
 * possible to circumvent the name restrictions by overriding the
 * {@link java.security.Provider#getName} method. Also, there is an equivalent
 * level of risk associated with granting code permission to insert a provider
 * with a specific name, or any name it chooses. Users should use the
 * "insertProvider" permission instead.
 * <p>This would allow somebody to introduce a possibly
 * malicious provider (e.g., one that discloses the private keys passed
 * to it) as the highest-priority provider. This would be possible
 * because the Security object (which manages the installed providers)
 * currently does not check the integrity or authenticity of a provider
 * before attaching it.</td>
 * </tr>
 *
 * <tr>
 *   <td>setSystemScope</td>
 *   <td>Setting of the system identity scope</td>
 *   <td>This would allow an attacker to configure the system identity scope with
 * certificates that should not be trusted, thereby granting applet or
 * application code signed with those certificates privileges that
 * would have been denied by the system's original identity scope.</td>
 * </tr>
 *
 * <tr>
 *   <td>setIdentityPublicKey</td>
 *   <td>Setting of the public key for an Identity</td>
 *   <td>If the identity is marked as "trusted", this allows an attacker to
 * introduce a different public key (e.g., its own) that is not trusted
 * by the system's identity scope, thereby granting applet or
 * application code signed with that public key privileges that
 * would have been denied otherwise.</td>
 * </tr>
 *
 * <tr>
 *   <td>setIdentityInfo</td>
 *   <td>Setting of a general information string for an Identity</td>
 *   <td>This allows attackers to set the general description for
 * an identity.  This may trick applications into using a different
 * identity than intended or may prevent applications from finding a
 * particular identity.</td>
 * </tr>
 *
 * <tr>
 *   <td>addIdentityCertificate</td>
 *   <td>Addition of a certificate for an Identity</td>
 *   <td>This allows attackers to set a certificate for
 * an identity's public key.  This is dangerous because it affects
 * the trust relationship across the system. This public key suddenly
 * becomes trusted to a wider audience than it otherwise would be.</td>
 * </tr>
 *
 * <tr>
 *   <td>removeIdentityCertificate</td>
 *   <td>Removal of a certificate for an Identity</td>
 *   <td>This allows attackers to remove a certificate for
 * an identity's public key. This is dangerous because it affects
 * the trust relationship across the system. This public key suddenly
 * becomes considered less trustworthy than it otherwise would be.</td>
 * </tr>
 *
 * <tr>
 *  <td>printIdentity</td>
 *  <td>Viewing the name of a principal
 * and optionally the scope in which it is used, and whether
 * or not it is considered "trusted" in that scope</td>
 *  <td>The scope that is printed out may be a filename, in which case
 * it may convey local system information. For example, here's a sample
 * printout of an identity named "carol", who is
 * marked not trusted in the user's identity database:<br>
 *   carol[/home/luehe/identitydb.obj][not trusted]</td>
 *</tr>
 *
 * <tr>
 *   <td>getSignerPrivateKey</td>
 *   <td>Retrieval of a Signer's private key</td>
 *   <td>It is very dangerous to allow access to a private key; private
 * keys are supposed to be kept secret. Otherwise, code can use the
 * private key to sign various files and claim the signature came from
 * the Signer.</td>
 * </tr>
 *
 * <tr>
 *   <td>setSignerKeyPair</td>
 *   <td>Setting of the key pair (public key and private key) for a Signer</td>
 *   <td>This would allow an attacker to replace somebody else's (the "target's")
 * keypair with a possibly weaker keypair (e.g., a keypair of a smaller
 * keysize).  This also would allow the attacker to listen in on encrypted
 * communication between the target and its peers. The target's peers
 * might wrap an encryption session key under the target's "new" public
 * key, which would allow the attacker (who possesses the corresponding
 * private key) to unwrap the session key and decipher the communication
 * data encrypted under that session key.</td>
 * </tr>
 *
 * </table>
 *
 * <p>
 *  此类是用于安全权限。 SecurityPermission包含名称(也称为"目标名称"),但没有操作列表;你有命名的权限或你不。
 * <P>
 *  目标名称是安全配置参数的名称(见下文)。目前,SecurityPermission对象用于保护对策略,安全性,提供程序,签名者和身份对象的访问。
 * <P>
 *  下表列出了所有可能的SecurityPermission目标名称,并为每个提供了权限允许的描述以及授予代码权限的风险的讨论。
 * 
 * <table border=1 cellpadding=5 summary="target name,what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td> createAccessControlContext </td> <td>创建AccessControlContext </td> <td>这允许某人用{@code DomainCombiner}
 * 实例化一个AccessControlContext。
 * 授予此权限时必须格外小心。恶意代码可以创建一个DomainCombiner,以增加授予代码的权限集,甚至授予代码{@link java.security.AllPermission}。</td>。
 * </tr>
 * 
 * <tr>
 * <td> getDomainCombiner </td> <td>检索AccessControlContext的DomainCombiner </td> <td>这允许某人检索AccessControl
 * Context的{@code DomainCombiner}。
 * 由于DomainCombiner可能包含敏感信息,因此可能会导致隐私泄露。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> getPolicy </td> <td>检索系统范围的安全策略(特别是当前安装的Policy对象)</td> <td>这允许某人通过{@code getPermissions }调用,它公开
 * 了授予给定CodeSource的哪些权限。
 * 虽然揭示策略不会危及系统的安全性,但它确实为恶意代码提供了可用于更好地针对攻击的额外信息。明智的做法是不要泄露所需的更多信息。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> setPolicy </td> <td>设置系统范围的安全策略(具体来说,Policy对象)</td> <td>授予此权限是非常危险的,因为恶意代码可能会授予自己所有必要的权限它需要成功地对
 * 系统进行攻击。
 * </td>。
 * </tr>
 * 
 * <tr>
 *  <td> createPolicy。{policy type} </td> <td>从提供者获取策略实现的实例</td> <td>授予此权限允许代码获取一个Policy对象。
 * 恶意代码可以查询Policy对象以确定已授予给除本身以外的代码的哪些权限。 </td>。
 * </tr>
 * 
 * <tr>
 * <td> getProperty。{key} </td> <td>使用指定键检索安全属性</td> <td>根据授予访问权限的特定键,代码可能会访问安全提供程序列表,以及系统范围和用户安全策略的位置。
 * 同时揭示此信息不会危及系统的安全性,它确实为恶意代码提供可用于更好地瞄准攻击的附加信息。
 * </td>
 * </tr>
 * 
 * <tr>
 *  <td> setProperty。{key} </td> <td>使用指定键设置安全属性</td> <td>这可能包括设置安全提供程序或定义系统范围安全策略的位置。
 * 有权设置新安全提供程序的恶意代码可能会设置盗取机密信息(如加密私钥)的恶意提供程序。
 * 此外,具有设置系统范围安全策略位置的权限的恶意代码可以将其指向一种安全策略,该策略授予攻击者成功地在系统上安装攻击所需的所有必要的权限。
 * </td>
 * </tr>
 * 
 * <tr>
 * <td> insertProvider </td> <td>添加新的提供者</td> <td>这将允许某人引入一个可能是恶意的提供者(例如,公开传递给它的私钥的提供者)优先级提供者。
 * 这是可能的,因为安全对象(管理已安装的提供程序)当前不会在连接之前检查提供程序的完整性或真实性。 "insertProvider"权限包含"insertProvider。
 * {provider name}"权限(有关详细信息,请参阅下面的部分)。
 * </td>
 * </tr>
 * 
 * <tr>
 *  <td> removeProvider。{provider name} </td> <td>删除指定的提供程序</td> <td>这可能会更改行为或禁用程序其他部分的执行。
 * 如果随后由程序请求的提供程序已被删除,则执行可能失败。
 * 此外,如果删除的提供者没有被程序的其余部分明确请求,但是它通常是在请求加密服务时选择的提供者(由于其在提供者列表中的先前顺序),将选择不同的提供者而不会找到合适的提供程序,从而导致程序失败。
 * </td>。
 * </tr>
 * 
 * <tr>
 * <td> clearProviderProperties。
 * {provider name} </td> <td>"清除"提供程序,以使其不再包含用于查找提供程序实施的服务的属性</td> <td>这将禁用查找的服务。
 * 这可以因此改变行为或禁用执行通常使用提供者的程序的其他部分,如"removeProvider. {provider name}"权限所述。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> putProviderProperty。{provider name} </td> <td>指定提供者的属性设置</td> <td>提供者属性每个都指定提供者实现的特定服务的名称和位置。
 * 通过授予此权限,您可以让代码将服务规范替换为另一个,从而指定不同的实现。</td>。
 * </tr>
 * 
 * 
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 *
 * @author Marianne Mueller
 * @author Roland Schemers
 */

public final class SecurityPermission extends BasicPermission {

    private static final long serialVersionUID = 5236109936224050470L;

    /**
     * Creates a new SecurityPermission with the specified name.
     * The name is the symbolic name of the SecurityPermission. An asterisk
     * may appear at the end of the name, following a ".", or by itself, to
     * signify a wildcard match.
     *
     * <p>
     * <tr>
     *  <td> removeProviderProperty。{provider name} </td> <td>从指定提供程序中删除属性</td> <td>这将禁用对提供程序实现的服务的查找。
     * 由于删除了指定其名称和位置的属性,它们不再可访问。这可能会更改行为或禁用执行通常使用提供程序的程序的其他部分,如"removeProvider。{provider name}"权限下所述。</td>。
     * </tr>
     * 
     * </table>
     * 
     * <P>
     * 以下权限已被较新的权限取代,或与已弃用的类相关联：{@link Identity},{@link IdentityScope},{@link Signer}。不鼓励使用它们。
     * 有关详细信息,请参阅适用的类。
     * 
     * <table border=1 cellpadding=5 summary="target name,what the permission allows, and associated risks">
     * <tr>
     *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
     * </tr>
     * 
     * <tr>
     *  <td> insertProvider。
     * {provider name} </td> <td>添加具有指定名称的新提供程序</td> <td>不建议使用此权限进一步使用,因为可能绕过名称覆盖{@link java.security.Provider#getName}
     * 方法的限制。
     *  <td> insertProvider。此外,存在与授予代码权限以插入具有特定名称的提供程序或其选择的任何名称相关联的等同风险级别。用户应该使用"insertProvider"权限。
     *  <p>这将允许某人引入可能是恶意的提供者(例如,公开传递给它的私钥的提供者)作为最高优先级的提供者。这是可能的,因为安全对象(管理已安装的提供程序)目前不会在连接之前检查提供程序的完整性或真实性。
     * </td>。
     * </tr>
     * 
     * <tr>
     * <td> setSystemScope </td> <td>系统标识范围的设置</td> <td>这将允许攻击者使用不应受信任的证书配置系统标识范围,从而授予applet或应用程序代码签名以及系统原始身
     * 份作用域拒绝的那些证书权限。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> setIdentityPublicKey </td> <td>身份的公钥的设置</td> <td>如果身份被标记为"受信任",则这允许攻击者引入不同的公钥自己),从而授予使用那些否则将被拒绝
     * 的公钥特权签署的applet或应用程序代码。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> setIdentityInfo </td> <td>身份的一般信息字符串的设置</td> <td>这允许攻击者设置身份的一般描述。
     * 
     * @param name the name of the SecurityPermission
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public SecurityPermission(String name)
    {
        super(name);
    }

    /**
     * Creates a new SecurityPermission object with the specified name.
     * The name is the symbolic name of the SecurityPermission, and the
     * actions String is currently unused and should be null.
     *
     * <p>
     * 这可能会诱使应用程序使用与预期不同的身份,或可能阻止应用程序找到特定的身份。</td>。
     * </tr>
     * 
     * <tr>
     *  <td> addIdentityCertificate </td> <td>为身份添加证书</td> <td>这允许攻击者为身份的公钥设置证书。这是危险的,因为它影响整个系统的信任关系。
     * 这个公共密钥突然变得比其他地方更广泛的受众信任。</td>。
     * </tr>
     * 
     * <tr>
     * <td> removeIdentityCertificate </td> <td>删除身份证书</td> <td>这允许攻击者删除身份的公钥的证书。这是危险的,因为它影响整个系统的信任关系。
     * 这个公钥突然变得被认为不是那么可信的。</td>。
     * </tr>
     * 
     * <tr>
     *  <td> printIdentity </td> <td>查看主体的名称以及可选的主体使用范围,以及该主体是否被认为是"受信任的"</td> <td>打印输出可以是文件名,在这种情况下,它可以传送本地
     * 系统信息。
     * 例如,以下是名为"carol"的标识的示例打印输出,在用户的身份数据库中被标记为不可信：<br> carol [/home/luehe/identitydb.obj] [不信任] </td>。
     * /tr>
     * 
     * 
     * @param name the name of the SecurityPermission
     * @param actions should be null.
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public SecurityPermission(String name, String actions)
    {
        super(name, actions);
    }
}
