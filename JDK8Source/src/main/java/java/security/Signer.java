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

import java.io.*;

/**
 * This class is used to represent an Identity that can also digitally
 * sign data.
 *
 * <p>The management of a signer's private keys is an important and
 * sensitive issue that should be handled by subclasses as appropriate
 * to their intended use.
 *
 * <p>
 *  此类用于表示还可以对数据进行数字签名的标识。
 * 
 *  <p>签署人私钥的管理是一个重要而敏感的问题,应由子类根据其预期用途进行处理。
 * 
 * 
 * @see Identity
 *
 * @author Benjamin Renaud
 *
 * @deprecated This class is no longer used. Its functionality has been
 * replaced by {@code java.security.KeyStore}, the
 * {@code java.security.cert} package, and
 * {@code java.security.Principal}.
 */
@Deprecated
public abstract class Signer extends Identity {

    private static final long serialVersionUID = -1763464102261361480L;

    /**
     * The signer's private key.
     *
     * <p>
     *  签名者的私钥。
     * 
     * 
     * @serial
     */
    private PrivateKey privateKey;

    /**
     * Creates a signer. This constructor should only be used for
     * serialization.
     * <p>
     *  创建签名者。此构造函数只应用于序列化。
     * 
     */
    protected Signer() {
        super();
    }


    /**
     * Creates a signer with the specified identity name.
     *
     * <p>
     *  创建具有指定标识名称的签名者。
     * 
     * 
     * @param name the identity name.
     */
    public Signer(String name) {
        super(name);
    }

    /**
     * Creates a signer with the specified identity name and scope.
     *
     * <p>
     *  创建具有指定的标识名和范围的签名者。
     * 
     * 
     * @param name the identity name.
     *
     * @param scope the scope of the identity.
     *
     * @exception KeyManagementException if there is already an identity
     * with the same name in the scope.
     */
    public Signer(String name, IdentityScope scope)
    throws KeyManagementException {
        super(name, scope);
    }

    /**
     * Returns this signer's private key.
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "getSignerPrivateKey"}
     * as its argument to see if it's ok to return the private key.
     *
     * <p>
     *  返回此签名者的私钥。
     * 
     *  <p>首先,如果有一个安全管理器,则使用{@code"getSignerPrivateKey"}作为其参数来调用其{@code checkSecurityAccess}方法,以查看是否可以返回私钥。
     * 
     * 
     * @return this signer's private key, or null if the private key has
     * not yet been set.
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * returning the private key.
     *
     * @see SecurityManager#checkSecurityAccess
     */
    public PrivateKey getPrivateKey() {
        check("getSignerPrivateKey");
        return privateKey;
    }

   /**
     * Sets the key pair (public key and private key) for this signer.
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "setSignerKeyPair"}
     * as its argument to see if it's ok to set the key pair.
     *
     * <p>
     *  设置此签名者的密钥对(公钥和私钥)。
     * 
     *  <p>首先,如果有一个安全管理器,它的{@code checkSecurityAccess}方法被调用与{@code"setSignerKeyPair"}作为其参数,看看是否可以设置密钥对。
     * 
     * 
     * @param pair an initialized key pair.
     *
     * @exception InvalidParameterException if the key pair is not
     * properly initialized.
     * @exception KeyException if the key pair cannot be set for any
     * other reason.
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * setting the key pair.
     *
     * @see SecurityManager#checkSecurityAccess
     */
    public final void setKeyPair(KeyPair pair)
    throws InvalidParameterException, KeyException {
        check("setSignerKeyPair");
        final PublicKey pub = pair.getPublic();
        PrivateKey priv = pair.getPrivate();

        if (pub == null || priv == null) {
            throw new InvalidParameterException();
        }
        try {
            AccessController.doPrivileged(
                new PrivilegedExceptionAction<Void>() {
                public Void run() throws KeyManagementException {
                    setPublicKey(pub);
                    return null;
                }
            });
        } catch (PrivilegedActionException pae) {
            throw (KeyManagementException) pae.getException();
        }
        privateKey = priv;
    }

    String printKeys() {
        String keys = "";
        PublicKey publicKey = getPublicKey();
        if (publicKey != null && privateKey != null) {
            keys = "\tpublic and private keys initialized";

        } else {
            keys = "\tno keys";
        }
        return keys;
    }

    /**
     * Returns a string of information about the signer.
     *
     * <p>
     * 
     * @return a string of information about the signer.
     */
    public String toString() {
        return "[Signer]" + super.toString();
    }

    private static void check(String directive) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSecurityAccess(directive);
        }
    }

}
