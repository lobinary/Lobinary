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

package javax.security.auth.kerberos;

import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.security.auth.Destroyable;
import javax.security.auth.DestroyFailedException;

/**
 * This class encapsulates a long term secret key for a Kerberos
 * principal.<p>
 *
 * All Kerberos JAAS login modules that obtain a principal's password and
 * generate the secret key from it should use this class.
 * Sometimes, such as when authenticating a server in
 * the absence of user-to-user authentication, the login module will store
 * an instance of this class in the private credential set of a
 * {@link javax.security.auth.Subject Subject} during the commit phase of the
 * authentication process.<p>
 *
 * A Kerberos service using a keytab to read secret keys should use
 * the {@link KeyTab} class, where latest keys can be read when needed.<p>
 *
 * It might be necessary for the application to be granted a
 * {@link javax.security.auth.PrivateCredentialPermission
 * PrivateCredentialPermission} if it needs to access the KerberosKey
 * instance from a Subject. This permission is not needed when the
 * application depends on the default JGSS Kerberos mechanism to access the
 * KerberosKey. In that case, however, the application will need an
 * appropriate
 * {@link javax.security.auth.kerberos.ServicePermission ServicePermission}.
 *
 * <p>
 *  此类封装了Kerberos主体的长期密钥。<p>
 * 
 *  获取主体密码并从其生成密钥的所有Kerberos JAAS登录模块都应使用此类。
 * 有时,例如在没有用户到用户认证的情况下认证服务器时,登录模块将在{@link javax.security.auth.Subject Subject}的私有凭证集中存储此类的实例,验证过程的提交阶段。
 * <p>。
 * 
 *  使用keytab读取密钥的Kerberos服务应使用{@link KeyTab}类,在需要时可以读取最新的密钥。<p>
 * 
 *  如果需要从主题访问KerberosKey实例,则可能需要向应用程序授予{@link javax.security.auth.PrivateCredentialPermission PrivateCredentialPermission}
 * 。
 * 当应用程序依赖于默认的JGSS Kerberos机制来访问KerberosKey时,不需要此权限。
 * 在这种情况下,应用程序将需要一个适当的{@link javax.security.auth.kerberos.ServicePermission ServicePermission}。
 * 
 * 
 * @author Mayank Upadhyay
 * @since 1.4
 */
public class KerberosKey implements SecretKey, Destroyable {

    private static final long serialVersionUID = -4625402278148246993L;

   /**
     * The principal that this secret key belongs to.
     *
     * <p>
     *  此密钥所属的主体。
     * 
     * 
     * @serial
     */
    private KerberosPrincipal principal;

   /**
     * the version number of this secret key
     *
     * <p>
     *  此密钥的版本号
     * 
     * 
     * @serial
     */
    private int versionNum;

   /**
    * {@code KeyImpl} is serialized by writing out the ASN1 Encoded bytes
    * of the encryption key.
    * The ASN1 encoding is defined in RFC4120 and as  follows:
    * <pre>
    * EncryptionKey   ::= SEQUENCE {
    *           keytype   [0] Int32 -- actually encryption type --,
    *           keyvalue  [1] OCTET STRING
    * }
    * </pre>
    *
    * <p>
    *  {@code KeyImpl}通过写出加密密钥的ASN1编码字节而被序列化。 ASN1编码在RFC4120中定义如下：
    * <pre>
    * EncryptionKey :: = SEQUENCE {keytype [0] Int32  - 实际上是加密类型 - ,keyvalue [1] OCTET STRING}
    * </pre>
    * 
    * 
    * @serial
    */

    private KeyImpl key;
    private transient boolean destroyed = false;

    /**
     * Constructs a KerberosKey from the given bytes when the key type and
     * key version number are known. This can be used when reading the secret
     * key information from a Kerberos "keytab".
     *
     * <p>
     *  当键类型和键版本号已知时,从给定字节构造KerberosKey。这可以在从Kerberos"keytab"读取密钥信息时使用。
     * 
     * 
     * @param principal the principal that this secret key belongs to
     * @param keyBytes the raw bytes for the secret key
     * @param keyType the key type for the secret key as defined by the
     * Kerberos protocol specification.
     * @param versionNum the version number of this secret key
     */
    public KerberosKey(KerberosPrincipal principal,
                       byte[] keyBytes,
                       int keyType,
                       int versionNum) {
        this.principal = principal;
        this.versionNum = versionNum;
        key = new KeyImpl(keyBytes, keyType);
    }

    /**
     * Constructs a KerberosKey from a principal's password.
     *
     * <p>
     *  从主体的密码构造KerberosKey。
     * 
     * 
     * @param principal the principal that this password belongs to
     * @param password the password that should be used to compute the key
     * @param algorithm the name for the algorithm that this key will be
     * used for. This parameter may be null in which case the default
     * algorithm "DES" will be assumed.
     * @throws IllegalArgumentException if the name of the
     * algorithm passed is unsupported.
     */
    public KerberosKey(KerberosPrincipal principal,
                       char[] password,
                       String algorithm) {

        this.principal = principal;
        // Pass principal in for salt
        key = new KeyImpl(principal, password, algorithm);
    }

    /**
     * Returns the principal that this key belongs to.
     *
     * <p>
     *  返回此键所属的主体。
     * 
     * 
     * @return the principal this key belongs to.
     */
    public final KerberosPrincipal getPrincipal() {
        if (destroyed)
            throw new IllegalStateException("This key is no longer valid");
        return principal;
    }

    /**
     * Returns the key version number.
     *
     * <p>
     *  返回密钥版本号。
     * 
     * 
     * @return the key version number.
     */
    public final int getVersionNumber() {
        if (destroyed)
            throw new IllegalStateException("This key is no longer valid");
        return versionNum;
    }

    /**
     * Returns the key type for this long-term key.
     *
     * <p>
     *  返回此长期密钥的密钥类型。
     * 
     * 
     * @return the key type.
     */
    public final int getKeyType() {
        if (destroyed)
            throw new IllegalStateException("This key is no longer valid");
        return key.getKeyType();
    }

    /*
     * Methods from java.security.Key
     * <p>
     *  java.security.Key中的方法
     * 
     */

    /**
     * Returns the standard algorithm name for this key. For
     * example, "DES" would indicate that this key is a DES key.
     * See Appendix A in the <a href=
     * "../../../../../technotes/guides/security/crypto/CryptoSpec.html#AppA">
     * Java Cryptography Architecture API Specification &amp; Reference
     * </a>
     * for information about standard algorithm names.
     *
     * <p>
     *  返回此键的标准算法名称。例如,"DES"将指示该密钥是DES密钥。请参阅<a href =中的附录A.
     * "../../../../../technotes/guides/security/crypto/CryptoSpec.html#AppA">
     *  Java加密架构API规范&amp;参考
     * </a>
     *  有关标准算法名称的信息。
     * 
     * 
     * @return the name of the algorithm associated with this key.
     */
    public final String getAlgorithm() {
        if (destroyed)
            throw new IllegalStateException("This key is no longer valid");
        return key.getAlgorithm();
    }

    /**
     * Returns the name of the encoding format for this secret key.
     *
     * <p>
     *  返回此密钥的编码格式的名称。
     * 
     * 
     * @return the String "RAW"
     */
    public final String getFormat() {
        if (destroyed)
            throw new IllegalStateException("This key is no longer valid");
        return key.getFormat();
    }

    /**
     * Returns the key material of this secret key.
     *
     * <p>
     *  返回此密钥的密钥材料。
     * 
     * 
     * @return the key material
     */
    public final byte[] getEncoded() {
        if (destroyed)
            throw new IllegalStateException("This key is no longer valid");
        return key.getEncoded();
    }

    /**
     * Destroys this key. A call to any of its other methods after this
     * will cause an  IllegalStateException to be thrown.
     *
     * <p>
     *  销毁此键。此后调用任何其他方法将导致抛出IllegalStateException。
     * 
     * 
     * @throws DestroyFailedException if some error occurs while destorying
     * this key.
     */
    public void destroy() throws DestroyFailedException {
        if (!destroyed) {
            key.destroy();
            principal = null;
            destroyed = true;
        }
    }


    /** Determines if this key has been destroyed.*/
    public boolean isDestroyed() {
        return destroyed;
    }

    public String toString() {
        if (destroyed) {
            return "Destroyed Principal";
        }
        return "Kerberos Principal " + principal.toString() +
                "Key Version " + versionNum +
                "key "  + key.toString();
    }

    /**
     * Returns a hashcode for this KerberosKey.
     *
     * <p>
     *  返回此KerberosKey的哈希码。
     * 
     * 
     * @return a hashCode() for the {@code KerberosKey}
     * @since 1.6
     */
    public int hashCode() {
        int result = 17;
        if (isDestroyed()) {
            return result;
        }
        result = 37 * result + Arrays.hashCode(getEncoded());
        result = 37 * result + getKeyType();
        if (principal != null) {
            result = 37 * result + principal.hashCode();
        }
        return result * 37 + versionNum;
    }

    /**
     * Compares the specified Object with this KerberosKey for equality.
     * Returns true if the given object is also a
     * {@code KerberosKey} and the two
     * {@code KerberosKey} instances are equivalent.
     *
     * <p>
     *  将指定的对象与此KerberosKey进行比较以实现相等性。如果给定对象也是{@code KerberosKey},而且两个{@code KerberosKey}实例是等效的,则返回true。
     * 
     * @param other the Object to compare to
     * @return true if the specified object is equal to this KerberosKey,
     * false otherwise. NOTE: Returns false if either of the KerberosKey
     * objects has been destroyed.
     * @since 1.6
     */
    public boolean equals(Object other) {

        if (other == this)
            return true;

        if (! (other instanceof KerberosKey)) {
            return false;
        }

        KerberosKey otherKey = ((KerberosKey) other);
        if (isDestroyed() || otherKey.isDestroyed()) {
            return false;
        }

        if (versionNum != otherKey.getVersionNumber() ||
                getKeyType() != otherKey.getKeyType() ||
                !Arrays.equals(getEncoded(), otherKey.getEncoded())) {
            return false;
        }

        if (principal == null) {
            if (otherKey.getPrincipal() != null) {
                return false;
            }
        } else {
            if (!principal.equals(otherKey.getPrincipal())) {
                return false;
            }
        }

        return true;
    }
}
