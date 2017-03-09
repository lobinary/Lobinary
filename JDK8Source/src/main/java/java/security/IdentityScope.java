/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;

/**
 * <p>This class represents a scope for identities. It is an Identity
 * itself, and therefore has a name and can have a scope. It can also
 * optionally have a public key and associated certificates.
 *
 * <p>An IdentityScope can contain Identity objects of all kinds, including
 * Signers. All types of Identity objects can be retrieved, added, and
 * removed using the same methods. Note that it is possible, and in fact
 * expected, that different types of identity scopes will
 * apply different policies for their various operations on the
 * various types of Identities.
 *
 * <p>There is a one-to-one mapping between keys and identities, and
 * there can only be one copy of one key per scope. For example, suppose
 * <b>Acme Software, Inc</b> is a software publisher known to a user.
 * Suppose it is an Identity, that is, it has a public key, and a set of
 * associated certificates. It is named in the scope using the name
 * "Acme Software". No other named Identity in the scope has the same
 * public  key. Of course, none has the same name as well.
 *
 * <p>
 *  <p>此类表示身份的范围。它是一个标识本身,因此有一个名称,并可以有一个范围。它还可以可选地具有公钥和相关联的证书。
 * 
 *  <p> IdentityScope可以包含各种Identity对象,包括Signers。可以使用相同的方法检索,添加和删除所有类型的Identity对象。
 * 注意,不同类型的身份范围可能并且实际上预期将对各种类型的身份的各种操作应用不同的策略。
 * 
 *  <p>键和标识之间存在一对一映射,每个作用域只能有一个键的一个副本。例如,假设<b> Acme Software,Inc </b>是用户已知的软件发布商。
 * 假设它是一个身份,也就是说,它有一个公钥和一组相关的证书。它在范围中使用名称"Acme Software"命名。在作用域中没有其他命名的Identity具有相同的公钥。当然,没有相同的名字。
 * 
 * 
 * @see Identity
 * @see Signer
 * @see Principal
 * @see Key
 *
 * @author Benjamin Renaud
 *
 * @deprecated This class is no longer used. Its functionality has been
 * replaced by {@code java.security.KeyStore}, the
 * {@code java.security.cert} package, and
 * {@code java.security.Principal}.
 */
@Deprecated
public abstract
class IdentityScope extends Identity {

    private static final long serialVersionUID = -2337346281189773310L;

    /* The system's scope */
    private static IdentityScope scope;

    // initialize the system scope
    private static void initializeSystemScope() {

        String classname = AccessController.doPrivileged(
                                new PrivilegedAction<String>() {
            public String run() {
                return Security.getProperty("system.scope");
            }
        });

        if (classname == null) {
            return;

        } else {

            try {
                Class.forName(classname);
            } catch (ClassNotFoundException e) {
                //Security.error("unable to establish a system scope from " +
                //             classname);
                e.printStackTrace();
            }
        }
    }

    /**
     * This constructor is used for serialization only and should not
     * be used by subclasses.
     * <p>
     *  此构造函数仅用于序列化,不应由子类使用。
     * 
     */
    protected IdentityScope() {
        this("restoring...");
    }

    /**
     * Constructs a new identity scope with the specified name.
     *
     * <p>
     *  使用指定的名称构造新的标识范围。
     * 
     * 
     * @param name the scope name.
     */
    public IdentityScope(String name) {
        super(name);
    }

    /**
     * Constructs a new identity scope with the specified name and scope.
     *
     * <p>
     *  构造具有指定名称和作用域的新身份作用域。
     * 
     * 
     * @param name the scope name.
     * @param scope the scope for the new identity scope.
     *
     * @exception KeyManagementException if there is already an identity
     * with the same name in the scope.
     */
    public IdentityScope(String name, IdentityScope scope)
    throws KeyManagementException {
        super(name, scope);
    }

    /**
     * Returns the system's identity scope.
     *
     * <p>
     *  返回系统的身份范围。
     * 
     * 
     * @return the system's identity scope, or {@code null} if none has been
     *         set.
     *
     * @see #setSystemScope
     */
    public static IdentityScope getSystemScope() {
        if (scope == null) {
            initializeSystemScope();
        }
        return scope;
    }


    /**
     * Sets the system's identity scope.
     *
     * <p>First, if there is a security manager, its
     * {@code checkSecurityAccess}
     * method is called with {@code "setSystemScope"}
     * as its argument to see if it's ok to set the identity scope.
     *
     * <p>
     *  设置系统的身份范围。
     * 
     * <p>首先,如果有一个安全管理器,它的{@code checkSecurityAccess}方法被调用与{@code"setSystemScope"}作为其参数,看看是否可以设置身份范围。
     * 
     * 
     * @param scope the scope to set.
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * setting the identity scope.
     *
     * @see #getSystemScope
     * @see SecurityManager#checkSecurityAccess
     */
    protected static void setSystemScope(IdentityScope scope) {
        check("setSystemScope");
        IdentityScope.scope = scope;
    }

    /**
     * Returns the number of identities within this identity scope.
     *
     * <p>
     *  返回此标识范围内的标识数。
     * 
     * 
     * @return the number of identities within this identity scope.
     */
    public abstract int size();

    /**
     * Returns the identity in this scope with the specified name (if any).
     *
     * <p>
     *  返回此范围中具有指定名称(如果有)的标识。
     * 
     * 
     * @param name the name of the identity to be retrieved.
     *
     * @return the identity named {@code name}, or null if there are
     * no identities named {@code name} in this scope.
     */
    public abstract Identity getIdentity(String name);

    /**
     * Retrieves the identity whose name is the same as that of the
     * specified principal. (Note: Identity implements Principal.)
     *
     * <p>
     *  检索其名称与指定主体的名称相同的标识。 (注：Identity implements Principal。)
     * 
     * 
     * @param principal the principal corresponding to the identity
     * to be retrieved.
     *
     * @return the identity whose name is the same as that of the
     * principal, or null if there are no identities of the same name
     * in this scope.
     */
    public Identity getIdentity(Principal principal) {
        return getIdentity(principal.getName());
    }

    /**
     * Retrieves the identity with the specified public key.
     *
     * <p>
     *  使用指定的公钥检索身份。
     * 
     * 
     * @param key the public key for the identity to be returned.
     *
     * @return the identity with the given key, or null if there are
     * no identities in this scope with that key.
     */
    public abstract Identity getIdentity(PublicKey key);

    /**
     * Adds an identity to this identity scope.
     *
     * <p>
     *  向此身份作用域添加标识。
     * 
     * 
     * @param identity the identity to be added.
     *
     * @exception KeyManagementException if the identity is not
     * valid, a name conflict occurs, another identity has the same
     * public key as the identity being added, or another exception
     * occurs. */
    public abstract void addIdentity(Identity identity)
    throws KeyManagementException;

    /**
     * Removes an identity from this identity scope.
     *
     * <p>
     *  从此身份作用域删除身份。
     * 
     * 
     * @param identity the identity to be removed.
     *
     * @exception KeyManagementException if the identity is missing,
     * or another exception occurs.
     */
    public abstract void removeIdentity(Identity identity)
    throws KeyManagementException;

    /**
     * Returns an enumeration of all identities in this identity scope.
     *
     * <p>
     *  返回此身份作用域中所有标识的枚举。
     * 
     * 
     * @return an enumeration of all identities in this identity scope.
     */
    public abstract Enumeration<Identity> identities();

    /**
     * Returns a string representation of this identity scope, including
     * its name, its scope name, and the number of identities in this
     * identity scope.
     *
     * <p>
     *  返回此身份作用域的字符串表示形式,包括其名称,作用域名称和此身份作用域中的身份数。
     * 
     * @return a string representation of this identity scope.
     */
    public String toString() {
        return super.toString() + "[" + size() + "]";
    }

    private static void check(String directive) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSecurityAccess(directive);
        }
    }

}
