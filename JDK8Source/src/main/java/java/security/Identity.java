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
import java.util.*;

/**
 * <p>This class represents identities: real-world objects such as people,
 * companies or organizations whose identities can be authenticated using
 * their public keys. Identities may also be more abstract (or concrete)
 * constructs, such as daemon threads or smart cards.
 *
 * <p>All Identity objects have a name and a public key. Names are
 * immutable. Identities may also be scoped. That is, if an Identity is
 * specified to have a particular scope, then the name and public
 * key of the Identity are unique within that scope.
 *
 * <p>An Identity also has a set of certificates (all certifying its own
 * public key). The Principal names specified in these certificates need
 * not be the same, only the key.
 *
 * <p>An Identity can be subclassed, to include postal and email addresses,
 * telephone numbers, images of faces and logos, and so on.
 *
 * <p>
 *  <p>此类表示身份：现实世界的对象,如人,公司或组织,其身份可以使用其公钥进行身份验证。标识也可以是更抽象的(或具体的)结构,例如守护线程或智能卡。
 * 
 *  <p>所有Identity对象都有一个名称和一个公钥。名称是不可变的。标识也可以作为范围。也就是说,如果身份被指定为具有特定范围,则身份的名称和公共密钥在该范围内是唯一的。
 * 
 *  <p>身份还具有一组证书(所有证书都证明其自己的公钥)。这些证书中指定的主体名称不需要相同,只有键。
 * 
 *  <p>身份可以被子类化,包括邮政地址和电子邮件地址,电话号码,面孔和徽标的图像等。
 * 
 * 
 * @see IdentityScope
 * @see Signer
 * @see Principal
 *
 * @author Benjamin Renaud
 * @deprecated This class is no longer used. Its functionality has been
 * replaced by {@code java.security.KeyStore}, the
 * {@code java.security.cert} package, and
 * {@code java.security.Principal}.
 */
@Deprecated
public abstract class Identity implements Principal, Serializable {

    /** use serialVersionUID from JDK 1.1.x for interoperability */
    private static final long serialVersionUID = 3609922007826600659L;

    /**
     * The name for this identity.
     *
     * <p>
     *  此标识的名称。
     * 
     * 
     * @serial
     */
    private String name;

    /**
     * The public key for this identity.
     *
     * <p>
     *  此标识的公钥。
     * 
     * 
     * @serial
     */
    private PublicKey publicKey;

    /**
     * Generic, descriptive information about the identity.
     *
     * <p>
     *  有关身份的通用描述性信息。
     * 
     * 
     * @serial
     */
    String info = "No further information available.";

    /**
     * The scope of the identity.
     *
     * <p>
     *  身份的范围。
     * 
     * 
     * @serial
     */
    IdentityScope scope;

    /**
     * The certificates for this identity.
     *
     * <p>
     *  此身份的证书。
     * 
     * 
     * @serial
     */
    Vector<Certificate> certificates;

    /**
     * Constructor for serialization only.
     * <p>
     *  仅用于序列化的构造函数。
     * 
     */
    protected Identity() {
        this("restoring...");
    }

    /**
     * Constructs an identity with the specified name and scope.
     *
     * <p>
     *  构造具有指定名称和范围的标识。
     * 
     * 
     * @param name the identity name.
     * @param scope the scope of the identity.
     *
     * @exception KeyManagementException if there is already an identity
     * with the same name in the scope.
     */
    public Identity(String name, IdentityScope scope) throws
    KeyManagementException {
        this(name);
        if (scope != null) {
            scope.addIdentity(this);
        }
        this.scope = scope;
    }

    /**
     * Constructs an identity with the specified name and no scope.
     *
     * <p>
     *  构造具有指定名称和无范围的标识。
     * 
     * 
     * @param name the identity name.
     */
    public Identity(String name) {
        this.name = name;
    }

    /**
     * Returns this identity's name.
     *
     * <p>
     *  返回此标识的名称。
     * 
     * 
     * @return the name of this identity.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns this identity's scope.
     *
     * <p>
     *  返回此标识的作用域。
     * 
     * 
     * @return the scope of this identity.
     */
    public final IdentityScope getScope() {
        return scope;
    }

    /**
     * Returns this identity's public key.
     *
     * <p>
     *  返回此标识的公钥。
     * 
     * 
     * @return the public key for this identity.
     *
     * @see #setPublicKey
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Sets this identity's public key. The old key and all of this
     * identity's certificates are removed by this operation.
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "setIdentityPublicKey"}
     * as its argument to see if it's ok to set the public key.
     *
     * <p>
     * 设置此身份的公钥。旧密钥和所有此身份的证书都会被此操作删除。
     * 
     *  <p>首先,如果有一个安全管理器,则使用{@code"setIdentityPublicKey"}作为其参数来调用其{@code checkSecurityAccess}方法,以查看是否可以设置公钥。
     * 
     * 
     * @param key the public key for this identity.
     *
     * @exception KeyManagementException if another identity in the
     * identity's scope has the same public key, or if another exception occurs.
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * setting the public key.
     *
     * @see #getPublicKey
     * @see SecurityManager#checkSecurityAccess
     */
    /* Should we throw an exception if this is already set? */
    public void setPublicKey(PublicKey key) throws KeyManagementException {

        check("setIdentityPublicKey");
        this.publicKey = key;
        certificates = new Vector<Certificate>();
    }

    /**
     * Specifies a general information string for this identity.
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "setIdentityInfo"}
     * as its argument to see if it's ok to specify the information string.
     *
     * <p>
     *  指定此标识的一般信息字符串。
     * 
     *  <p>首先,如果有一个安全管理器,则使用{@code"setIdentityInfo"}作为其参数调用其{@code checkSecurityAccess}方法,以查看是否可以指定信息字符串。
     * 
     * 
     * @param info the information string.
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * setting the information string.
     *
     * @see #getInfo
     * @see SecurityManager#checkSecurityAccess
     */
    public void setInfo(String info) {
        check("setIdentityInfo");
        this.info = info;
    }

    /**
     * Returns general information previously specified for this identity.
     *
     * <p>
     *  返回之前为此标识指定的一般信息。
     * 
     * 
     * @return general information about this identity.
     *
     * @see #setInfo
     */
    public String getInfo() {
        return info;
    }

    /**
     * Adds a certificate for this identity. If the identity has a public
     * key, the public key in the certificate must be the same, and if
     * the identity does not have a public key, the identity's
     * public key is set to be that specified in the certificate.
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "addIdentityCertificate"}
     * as its argument to see if it's ok to add a certificate.
     *
     * <p>
     *  为此标识添加证书。如果身份具有公钥,则证书中的公钥必须相同,并且如果身份没有公钥,则身份的公钥被设置为证书中指定的公钥。
     * 
     *  <p>首先,如果有安全管理器,则使用{@code"addIdentityCertificate"}作为其参数来调用其{@code checkSecurityAccess}方法,以查看是否可以添加证书。
     * 
     * 
     * @param certificate the certificate to be added.
     *
     * @exception KeyManagementException if the certificate is not valid,
     * if the public key in the certificate being added conflicts with
     * this identity's public key, or if another exception occurs.
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * adding a certificate.
     *
     * @see SecurityManager#checkSecurityAccess
     */
    public void addCertificate(Certificate certificate)
    throws KeyManagementException {

        check("addIdentityCertificate");

        if (certificates == null) {
            certificates = new Vector<Certificate>();
        }
        if (publicKey != null) {
            if (!keyEquals(publicKey, certificate.getPublicKey())) {
                throw new KeyManagementException(
                    "public key different from cert public key");
            }
        } else {
            publicKey = certificate.getPublicKey();
        }
        certificates.addElement(certificate);
    }

    private boolean keyEquals(Key aKey, Key anotherKey) {
        String aKeyFormat = aKey.getFormat();
        String anotherKeyFormat = anotherKey.getFormat();
        if ((aKeyFormat == null) ^ (anotherKeyFormat == null))
            return false;
        if (aKeyFormat != null && anotherKeyFormat != null)
            if (!aKeyFormat.equalsIgnoreCase(anotherKeyFormat))
                return false;
        return java.util.Arrays.equals(aKey.getEncoded(),
                                     anotherKey.getEncoded());
    }


    /**
     * Removes a certificate from this identity.
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "removeIdentityCertificate"}
     * as its argument to see if it's ok to remove a certificate.
     *
     * <p>
     *  从此标识中删除证书。
     * 
     *  <p>首先,如果有一个安全管理器,它的{@code checkSecurityAccess}方法将使用{@code"removeIdentityCertificate"}作为其参数来调用,以查看是否可
     * 以删除证书。
     * 
     * 
     * @param certificate the certificate to be removed.
     *
     * @exception KeyManagementException if the certificate is
     * missing, or if another exception occurs.
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * removing a certificate.
     *
     * @see SecurityManager#checkSecurityAccess
     */
    public void removeCertificate(Certificate certificate)
    throws KeyManagementException {
        check("removeIdentityCertificate");
        if (certificates != null) {
            certificates.removeElement(certificate);
        }
    }

    /**
     * Returns a copy of all the certificates for this identity.
     *
     * <p>
     *  返回此标识的所有证书的副本。
     * 
     * 
     * @return a copy of all the certificates for this identity.
     */
    public Certificate[] certificates() {
        if (certificates == null) {
            return new Certificate[0];
        }
        int len = certificates.size();
        Certificate[] certs = new Certificate[len];
        certificates.copyInto(certs);
        return certs;
    }

    /**
     * Tests for equality between the specified object and this identity.
     * This first tests to see if the entities actually refer to the same
     * object, in which case it returns true. Next, it checks to see if
     * the entities have the same name and the same scope. If they do,
     * the method returns true. Otherwise, it calls
     * {@link #identityEquals(Identity) identityEquals}, which subclasses should
     * override.
     *
     * <p>
     * 测试指定对象与此标识之间的相等性。这首先测试看看实体是否实际引用相同的对象,在这种情况下它返回true。接下来,它检查实体是否具有相同的名称和范围。如果他们这样做,该方法返回true。
     * 否则,它调用{@link #identityEquals(Identity)identityEquals},这些子类应该覆盖。
     * 
     * 
     * @param identity the object to test for equality with this identity.
     *
     * @return true if the objects are considered equal, false otherwise.
     *
     * @see #identityEquals
     */
    public final boolean equals(Object identity) {

        if (identity == this) {
            return true;
        }

        if (identity instanceof Identity) {
            Identity i = (Identity)identity;
            if (this.fullName().equals(i.fullName())) {
                return true;
            } else {
                return identityEquals(i);
            }
        }
        return false;
    }

    /**
     * Tests for equality between the specified identity and this identity.
     * This method should be overriden by subclasses to test for equality.
     * The default behavior is to return true if the names and public keys
     * are equal.
     *
     * <p>
     *  测试指定的身份和此身份之间的相等性。这个方法应该被子类覆盖以测试相等性。如果名称和公钥相等,默认行为是返回true。
     * 
     * 
     * @param identity the identity to test for equality with this identity.
     *
     * @return true if the identities are considered equal, false
     * otherwise.
     *
     * @see #equals
     */
    protected boolean identityEquals(Identity identity) {
        if (!name.equalsIgnoreCase(identity.name))
            return false;

        if ((publicKey == null) ^ (identity.publicKey == null))
            return false;

        if (publicKey != null && identity.publicKey != null)
            if (!publicKey.equals(identity.publicKey))
                return false;

        return true;

    }

    /**
     * Returns a parsable name for identity: identityName.scopeName
     * <p>
     *  返回identity：identityName.scopeName的可解析名称
     * 
     */
    String fullName() {
        String parsable = name;
        if (scope != null) {
            parsable += "." + scope.getName();
        }
        return parsable;
    }

    /**
     * Returns a short string describing this identity, telling its
     * name and its scope (if any).
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "printIdentity"}
     * as its argument to see if it's ok to return the string.
     *
     * <p>
     *  返回描述此标识的短字符串,告诉其名称及其范围(如果有)。
     * 
     *  <p>首先,如果有安全管理器,则使用{@code"printIdentity"}作为其参数来调用其{@code checkSecurityAccess}方法,以查看是否可以返回字符串。
     * 
     * 
     * @return information about this identity, such as its name and the
     * name of its scope (if any).
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * returning a string describing this identity.
     *
     * @see SecurityManager#checkSecurityAccess
     */
    public String toString() {
        check("printIdentity");
        String printable = name;
        if (scope != null) {
            printable += "[" + scope.getName() + "]";
        }
        return printable;
    }

    /**
     * Returns a string representation of this identity, with
     * optionally more details than that provided by the
     * {@code toString} method without any arguments.
     *
     * <p>First, if there is a security manager, its {@code checkSecurityAccess}
     * method is called with {@code "printIdentity"}
     * as its argument to see if it's ok to return the string.
     *
     * <p>
     *  返回此标识的字符串表示形式,其中可选地包含{@code toString}方法提供的没有任何参数的详细信息。
     * 
     *  <p>首先,如果有安全管理器,则使用{@code"printIdentity"}作为其参数来调用其{@code checkSecurityAccess}方法,以查看是否可以返回字符串。
     * 
     * 
     * @param detailed whether or not to provide detailed information.
     *
     * @return information about this identity. If {@code detailed}
     * is true, then this method returns more information than that
     * provided by the {@code toString} method without any arguments.
     *
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkSecurityAccess} method doesn't allow
     * returning a string describing this identity.
     *
     * @see #toString
     * @see SecurityManager#checkSecurityAccess
     */
    public String toString(boolean detailed) {
        String out = toString();
        if (detailed) {
            out += "\n";
            out += printKeys();
            out += "\n" + printCertificates();
            if (info != null) {
                out += "\n\t" + info;
            } else {
                out += "\n\tno additional information available.";
            }
        }
        return out;
    }

    String printKeys() {
        String key = "";
        if (publicKey != null) {
            key = "\tpublic key initialized";
        } else {
            key = "\tno public key";
        }
        return key;
    }

    String printCertificates() {
        String out = "";
        if (certificates == null) {
            return "\tno certificates";
        } else {
            out += "\tcertificates: \n";

            int i = 1;
            for (Certificate cert : certificates) {
                out += "\tcertificate " + i++ +
                    "\tfor  : " + cert.getPrincipal() + "\n";
                out += "\t\t\tfrom : " +
                    cert.getGuarantor() + "\n";
            }
        }
        return out;
    }

    /**
     * Returns a hashcode for this identity.
     *
     * <p>
     * 
     * @return a hashcode for this identity.
     */
    public int hashCode() {
        return name.hashCode();
    }

    private static void check(String directive) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSecurityAccess(directive);
        }
    }
}
