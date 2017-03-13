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

import java.io.*;
import java.net.URI;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;
import javax.crypto.SecretKey;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.callback.*;

import sun.security.util.Debug;

/**
 * This class represents a storage facility for cryptographic
 * keys and certificates.
 *
 * <p> A {@code KeyStore} manages different types of entries.
 * Each type of entry implements the {@code KeyStore.Entry} interface.
 * Three basic {@code KeyStore.Entry} implementations are provided:
 *
 * <ul>
 * <li><b>KeyStore.PrivateKeyEntry</b>
 * <p> This type of entry holds a cryptographic {@code PrivateKey},
 * which is optionally stored in a protected format to prevent
 * unauthorized access.  It is also accompanied by a certificate chain
 * for the corresponding public key.
 *
 * <p> Private keys and certificate chains are used by a given entity for
 * self-authentication. Applications for this authentication include software
 * distribution organizations which sign JAR files as part of releasing
 * and/or licensing software.
 *
 * <li><b>KeyStore.SecretKeyEntry</b>
 * <p> This type of entry holds a cryptographic {@code SecretKey},
 * which is optionally stored in a protected format to prevent
 * unauthorized access.
 *
 * <li><b>KeyStore.TrustedCertificateEntry</b>
 * <p> This type of entry contains a single public key {@code Certificate}
 * belonging to another party. It is called a <i>trusted certificate</i>
 * because the keystore owner trusts that the public key in the certificate
 * indeed belongs to the identity identified by the <i>subject</i> (owner)
 * of the certificate.
 *
 * <p>This type of entry can be used to authenticate other parties.
 * </ul>
 *
 * <p> Each entry in a keystore is identified by an "alias" string. In the
 * case of private keys and their associated certificate chains, these strings
 * distinguish among the different ways in which the entity may authenticate
 * itself. For example, the entity may authenticate itself using different
 * certificate authorities, or using different public key algorithms.
 *
 * <p> Whether aliases are case sensitive is implementation dependent. In order
 * to avoid problems, it is recommended not to use aliases in a KeyStore that
 * only differ in case.
 *
 * <p> Whether keystores are persistent, and the mechanisms used by the
 * keystore if it is persistent, are not specified here. This allows
 * use of a variety of techniques for protecting sensitive (e.g., private or
 * secret) keys. Smart cards or other integrated cryptographic engines
 * (SafeKeyper) are one option, and simpler mechanisms such as files may also
 * be used (in a variety of formats).
 *
 * <p> Typical ways to request a KeyStore object include
 * relying on the default type and providing a specific keystore type.
 *
 * <ul>
 * <li>To rely on the default type:
 * <pre>
 *    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
 * </pre>
 * The system will return a keystore implementation for the default type.
 *
 * <li>To provide a specific keystore type:
 * <pre>
 *      KeyStore ks = KeyStore.getInstance("JKS");
 * </pre>
 * The system will return the most preferred implementation of the
 * specified keystore type available in the environment. <p>
 * </ul>
 *
 * <p> Before a keystore can be accessed, it must be
 * {@link #load(java.io.InputStream, char[]) loaded}.
 * <pre>
 *    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
 *
 *    // get user password and file input stream
 *    char[] password = getPassword();
 *
 *    try (FileInputStream fis = new FileInputStream("keyStoreName")) {
 *        ks.load(fis, password);
 *    }
 * </pre>
 *
 * To create an empty keystore using the above {@code load} method,
 * pass {@code null} as the {@code InputStream} argument.
 *
 * <p> Once the keystore has been loaded, it is possible
 * to read existing entries from the keystore, or to write new entries
 * into the keystore:
 * <pre>
 *    KeyStore.ProtectionParameter protParam =
 *        new KeyStore.PasswordProtection(password);
 *
 *    // get my private key
 *    KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry)
 *        ks.getEntry("privateKeyAlias", protParam);
 *    PrivateKey myPrivateKey = pkEntry.getPrivateKey();
 *
 *    // save my secret key
 *    javax.crypto.SecretKey mySecretKey;
 *    KeyStore.SecretKeyEntry skEntry =
 *        new KeyStore.SecretKeyEntry(mySecretKey);
 *    ks.setEntry("secretKeyAlias", skEntry, protParam);
 *
 *    // store away the keystore
 *    try (FileOutputStream fos = new FileOutputStream("newKeyStoreName")) {
 *        ks.store(fos, password);
 *    }
 * </pre>
 *
 * Note that although the same password may be used to
 * load the keystore, to protect the private key entry,
 * to protect the secret key entry, and to store the keystore
 * (as is shown in the sample code above),
 * different passwords or other protection parameters
 * may also be used.
 *
 * <p> Every implementation of the Java platform is required to support
 * the following standard {@code KeyStore} type:
 * <ul>
 * <li>{@code PKCS12}</li>
 * </ul>
 * This type is described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyStore">
 * KeyStore section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other types are supported.
 *
 * <p>
 *  此类表示用于加密密钥和证书的存储设备
 * 
 *  <p> {@code KeyStore}管理不同类型的条目每种类型的条目都实现{@code KeyStoreEntry}接口提供了三个基本的{@code KeyStoreEntry}实现：
 * 
 * <ul>
 *  <li> <b> KeyStorePrivateKeyEntry </b> <p>此类型的条目保存加密{@code PrivateKey},可选择以受保护的格式存储,以防止未经授权的访问。
 * 它还伴随着相应的证书链公钥。
 * 
 * <p>私钥和证书链由给定实体用于自我认证此认证的应用程序包括软件分发组织,其将JAR文件签署为发布和/或许可软件的一部分
 * 
 *  <li> <b> KeyStoreSecretKeyEntry </b> <p>此类型的条目保存加密{@code SecretKey},可选择以受保护的格式存储,以防止未经授权的访问
 * 
 *  <li> <b> KeyStoreTrustedCertificateEntry </b> <p>此类型的条目包含属于另一方的单个公钥{@code Certificate}它被称为<i>受信任证书</i>
 * ,因为密钥库所有者信任证书中的公钥确实属于由证书的主体</i>(所有者)所标识的身份。
 * 
 * <p>此类型的条目可用于验证其他方
 * </ul>
 * 
 *  <p>密钥库中的每个条目都由"别名"字符串标识。在私钥及其关联的证书链的情况下,这些字符串区分实体可以对其自身进行身份验证的不同方式。
 * 例如,实体可以验证自身使用不同的证书颁发机构,或使用不同的公钥算法。
 * 
 *  <p>别名是区分大小写是否取决于实施为了避免出现问题,建议不要在KeyStore中使用别名,只有大小写不同
 * 
 * <p>密钥库是否持久,以及密钥库使用的机制(如果持久)不在这里指定这允许使用各种技术来保护敏感(例如,私有或秘密)密钥智能卡或其他集成加密引擎(SafeKeyper)是一个选项,并且还可以使用诸如文件的
 * 更简单的机制(以多种格式)。
 * 
 *  <p>请求KeyStore对象的典型方法包括依赖于默认类型并提供特定的密钥库类型
 * 
 * <ul>
 *  <li>要依赖默认类型：
 * <pre>
 *  KeyStore ks = KeyStoregetInstance(KeyStoregetDefaultType());
 * </pre>
 *  系统将返回默认类型的密钥库实现
 * 
 *  <li>要提供特定的密钥库类型：
 * <pre>
 *  KeyStore ks = KeyStoregetInstance("JKS");
 * </pre>
 * 系统将返回环境<p>中可用的指定密钥库类型的最优选实现
 * </ul>
 * 
 *  <p>在访问密钥库之前,必须{@link #load(javaioInputStream,char [])loaded}
 * <pre>
 *  KeyStore ks = KeyStoregetInstance(KeyStoregetDefaultType());
 * 
 *  //获取用户密码和文件输入流char [] password = getPassword();
 * 
 *  try(FileInputStream fis = new FileInputStream("keyStoreName")){ksload(fis,password); }}
 * </pre>
 * 
 *  要使用上述{@code load}方法创建空密钥库,请将{@code null}作为{@code InputStream}参数传递
 * 
 *  <p>加载密钥库后,可以从密钥库读取现有条目,或者将新条目写入密钥库：
 * <pre>
 * KeyStoreProtectionParameter protParam = new KeyStorePasswordProtection(password);
 * 
 *  //获取我的私钥KeyStorePrivateKeyEntry pkEntry =(KeyStorePrivateKeyEntry)ksgetEntry("privateKeyAlias",protP
 * aram); PrivateKey myPrivateKey = pkEntrygetPrivateKey();。
 * 
 *  // save my secret key javaxcryptoSecretKey mySecretKey; KeyStoreSecretKeyEntry skEntry = new KeyStor
 * eSecretKeyEntry(mySecretKey); kssetEntry("secretKeyAlias",skEntry,protParam);。
 * 
 *  //存储密钥库try(FileOutputStream fos = new FileOutputStream("newKeyStoreName")){ksstore(fos,password); }}
 * 。
 * </pre>
 * 
 * 注意,虽然可以使用相同的密码来加载密钥库,保护私钥条目,保护密钥条目以及存储密钥库(如上面的示例代码所示),不同的密码或其他保护参数也可以使用
 * 
 *  <p>每个Java平台的实现都需要支持以下标准{@code KeyStore}类型：
 * <ul>
 *  <li> {@ code PKCS12} </li>
 * </ul>
 *  此类型在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyStore">
 *  Java加密体系结构的KeyStore部分</a>标准算法名称文档请参阅您的实现的发行文档以查看是否支持任何其他类型
 * 
 * 
 * @author Jan Luehe
 *
 * @see java.security.PrivateKey
 * @see javax.crypto.SecretKey
 * @see java.security.cert.Certificate
 *
 * @since 1.2
 */

public class KeyStore {

    private static final Debug pdebug =
                        Debug.getInstance("provider", "Provider");
    private static final boolean skipDebug =
        Debug.isOn("engine=") && !Debug.isOn("keystore");

    /*
     * Constant to lookup in the Security properties file to determine
     * the default keystore type.
     * In the Security properties file, the default keystore type is given as:
     * <pre>
     * keystore.type=jks
     * </pre>
     * <p>
     * 在安全属性文件中查找以确定默认密钥库类型的常量在安全属性文件中,默认密钥库类型如下所示：
     * <pre>
     *  keystoretype = jks
     * </pre>
     */
    private static final String KEYSTORE_TYPE = "keystore.type";

    // The keystore type
    private String type;

    // The provider
    private Provider provider;

    // The provider implementation
    private KeyStoreSpi keyStoreSpi;

    // Has this keystore been initialized (loaded)?
    private boolean initialized = false;

    /**
     * A marker interface for {@code KeyStore}
     * {@link #load(KeyStore.LoadStoreParameter) load}
     * and
     * {@link #store(KeyStore.LoadStoreParameter) store}
     * parameters.
     *
     * <p>
     *  {@code KeyStore} {@link #load(KeyStoreLoadStoreParameter)load}和{@link #store(KeyStoreLoadStoreParameter)store}
     * 参数的标记界面。
     * 
     * 
     * @since 1.5
     */
    public static interface LoadStoreParameter {
        /**
         * Gets the parameter used to protect keystore data.
         *
         * <p>
         *  获取用于保护密钥库数据的参数
         * 
         * 
         * @return the parameter used to protect keystore data, or null
         */
        public ProtectionParameter getProtectionParameter();
    }

    /**
     * A marker interface for keystore protection parameters.
     *
     * <p> The information stored in a {@code ProtectionParameter}
     * object protects the contents of a keystore.
     * For example, protection parameters may be used to check
     * the integrity of keystore data, or to protect the
     * confidentiality of sensitive keystore data
     * (such as a {@code PrivateKey}).
     *
     * <p>
     *  密钥库保护参数的标记界面
     * 
     *  <p>存储在{@code ProtectionParameter}对象中的信息保护密钥库的内容例如,保护参数可用于检查密钥库数据的完整性,或保护敏感的密钥库数据的机密性(例如{ @code PrivateKey}
     * )。
     * 
     * 
     * @since 1.5
     */
    public static interface ProtectionParameter { }

    /**
     * A password-based implementation of {@code ProtectionParameter}.
     *
     * <p>
     *  基于密码的{@code ProtectionParameter}
     * 
     * 
     * @since 1.5
     */
    public static class PasswordProtection implements
                ProtectionParameter, javax.security.auth.Destroyable {

        private final char[] password;
        private final String protectionAlgorithm;
        private final AlgorithmParameterSpec protectionParameters;
        private volatile boolean destroyed = false;

        /**
         * Creates a password parameter.
         *
         * <p> The specified {@code password} is cloned before it is stored
         * in the new {@code PasswordProtection} object.
         *
         * <p>
         * 创建密码参数
         * 
         *  <p>指定的{@code password}已克隆,然后存储在新的{@code PasswordProtection}对象中
         * 
         * 
         * @param password the password, which may be {@code null}
         */
        public PasswordProtection(char[] password) {
            this.password = (password == null) ? null : password.clone();
            this.protectionAlgorithm = null;
            this.protectionParameters = null;
        }

        /**
         * Creates a password parameter and specifies the protection algorithm
         * and associated parameters to use when encrypting a keystore entry.
         * <p>
         * The specified {@code password} is cloned before it is stored in the
         * new {@code PasswordProtection} object.
         *
         * <p>
         *  创建密码参数,并指定在加密密钥库条目时使用的保护算法和相关参数
         * <p>
         *  指定的{@code password}在被存储在新的{@code PasswordProtection}对象之前被克隆
         * 
         * 
         * @param password the password, which may be {@code null}
         * @param protectionAlgorithm the encryption algorithm name, for
         *     example, {@code PBEWithHmacSHA256AndAES_256}.
         *     See the Cipher section in the <a href=
         * "{@docRoot}/../technotes/guides/security/StandardNames.html#Cipher">
         * Java Cryptography Architecture Standard Algorithm Name
         * Documentation</a>
         *     for information about standard encryption algorithm names.
         * @param protectionParameters the encryption algorithm parameter
         *     specification, which may be {@code null}
         * @exception NullPointerException if {@code protectionAlgorithm} is
         *     {@code null}
         *
         * @since 1.8
         */
        public PasswordProtection(char[] password, String protectionAlgorithm,
            AlgorithmParameterSpec protectionParameters) {
            if (protectionAlgorithm == null) {
                throw new NullPointerException("invalid null input");
            }
            this.password = (password == null) ? null : password.clone();
            this.protectionAlgorithm = protectionAlgorithm;
            this.protectionParameters = protectionParameters;
        }

        /**
         * Gets the name of the protection algorithm.
         * If none was set then the keystore provider will use its default
         * protection algorithm. The name of the default protection algorithm
         * for a given keystore type is set using the
         * {@code 'keystore.<type>.keyProtectionAlgorithm'} security property.
         * For example, the
         * {@code keystore.PKCS12.keyProtectionAlgorithm} property stores the
         * name of the default key protection algorithm used for PKCS12
         * keystores. If the security property is not set, an
         * implementation-specific algorithm will be used.
         *
         * <p>
         * 获取保护算法的名称如果没有设置,则密钥库提供程序将使用其默认保护算法。
         * 给定密钥库类型的默认保护算法的名称使用{@code'keystore <type> keyProtectionAlgorithm'}安全属性例如,{@code keystorePKCS12keyProtectionAlgorithm}
         * 属性存储用于PKCS12密钥库的默认密钥保护算法的名称如果未设置安全属性,将使用实现特定的算法。
         * 获取保护算法的名称如果没有设置,则密钥库提供程序将使用其默认保护算法。
         * 
         * 
         * @return the algorithm name, or {@code null} if none was set
         *
         * @since 1.8
         */
        public String getProtectionAlgorithm() {
            return protectionAlgorithm;
        }

        /**
         * Gets the parameters supplied for the protection algorithm.
         *
         * <p>
         *  获取为保护算法提供的参数
         * 
         * 
         * @return the algorithm parameter specification, or {@code  null},
         *     if none was set
         *
         * @since 1.8
         */
        public AlgorithmParameterSpec getProtectionParameters() {
            return protectionParameters;
        }

        /**
         * Gets the password.
         *
         * <p>Note that this method returns a reference to the password.
         * If a clone of the array is created it is the caller's
         * responsibility to zero out the password information
         * after it is no longer needed.
         *
         * <p>
         *  获取密码
         * 
         * <p>请注意,此方法返回对密码的引用如果创建了数组的克隆,则调用者有责任在不再需要密码信息后清除密码信息
         * 
         * 
         * @see #destroy()
         * @return the password, which may be {@code null}
         * @exception IllegalStateException if the password has
         *              been cleared (destroyed)
         */
        public synchronized char[] getPassword() {
            if (destroyed) {
                throw new IllegalStateException("password has been cleared");
            }
            return password;
        }

        /**
         * Clears the password.
         *
         * <p>
         *  清除密码
         * 
         * 
         * @exception DestroyFailedException if this method was unable
         *      to clear the password
         */
        public synchronized void destroy() throws DestroyFailedException {
            destroyed = true;
            if (password != null) {
                Arrays.fill(password, ' ');
            }
        }

        /**
         * Determines if password has been cleared.
         *
         * <p>
         *  确定密码是否已清除
         * 
         * 
         * @return true if the password has been cleared, false otherwise
         */
        public synchronized boolean isDestroyed() {
            return destroyed;
        }
    }

    /**
     * A ProtectionParameter encapsulating a CallbackHandler.
     *
     * <p>
     *  封装了CallbackHandler的ProtectionParameter
     * 
     * 
     * @since 1.5
     */
    public static class CallbackHandlerProtection
            implements ProtectionParameter {

        private final CallbackHandler handler;

        /**
         * Constructs a new CallbackHandlerProtection from a
         * CallbackHandler.
         *
         * <p>
         *  从CallbackHandler构造一个新的CallbackHandlerProtection
         * 
         * 
         * @param handler the CallbackHandler
         * @exception NullPointerException if handler is null
         */
        public CallbackHandlerProtection(CallbackHandler handler) {
            if (handler == null) {
                throw new NullPointerException("handler must not be null");
            }
            this.handler = handler;
        }

        /**
         * Returns the CallbackHandler.
         *
         * <p>
         *  返回CallbackHandler
         * 
         * 
         * @return the CallbackHandler.
         */
        public CallbackHandler getCallbackHandler() {
            return handler;
        }

    }

    /**
     * A marker interface for {@code KeyStore} entry types.
     *
     * <p>
     *  {@code KeyStore}条目类型的标记界面
     * 
     * 
     * @since 1.5
     */
    public static interface Entry {

        /**
         * Retrieves the attributes associated with an entry.
         * <p>
         * The default implementation returns an empty {@code Set}.
         *
         * <p>
         *  检索与条目关联的属性
         * <p>
         *  默认实现返回一个空的{@code Set}
         * 
         * 
         * @return an unmodifiable {@code Set} of attributes, possibly empty
         *
         * @since 1.8
         */
        public default Set<Attribute> getAttributes() {
            return Collections.<Attribute>emptySet();
        }

        /**
         * An attribute associated with a keystore entry.
         * It comprises a name and one or more values.
         *
         * <p>
         *  与密钥库条目关联的属性它包含名称和一个或多个值
         * 
         * 
         * @since 1.8
         */
        public interface Attribute {
            /**
             * Returns the attribute's name.
             *
             * <p>
             *  返回属性的名称
             * 
             * 
             * @return the attribute name
             */
            public String getName();

            /**
             * Returns the attribute's value.
             * Multi-valued attributes encode their values as a single string.
             *
             * <p>
             *  返回属性的值多值属性将其值作为单个字符串进行编码
             * 
             * 
             * @return the attribute value
             */
            public String getValue();
        }
    }

    /**
     * A {@code KeyStore} entry that holds a {@code PrivateKey}
     * and corresponding certificate chain.
     *
     * <p>
     * 保存{@code PrivateKey}和相应证书链的{@code KeyStore}条目
     * 
     * 
     * @since 1.5
     */
    public static final class PrivateKeyEntry implements Entry {

        private final PrivateKey privKey;
        private final Certificate[] chain;
        private final Set<Attribute> attributes;

        /**
         * Constructs a {@code PrivateKeyEntry} with a
         * {@code PrivateKey} and corresponding certificate chain.
         *
         * <p> The specified {@code chain} is cloned before it is stored
         * in the new {@code PrivateKeyEntry} object.
         *
         * <p>
         *  使用{@code PrivateKey}和相应的证书链构造{@code PrivateKeyEntry}
         * 
         *  <p>指定的{@code chain}在被存储在新的{@code PrivateKeyEntry}对象之前被克隆
         * 
         * 
         * @param privateKey the {@code PrivateKey}
         * @param chain an array of {@code Certificate}s
         *      representing the certificate chain.
         *      The chain must be ordered and contain a
         *      {@code Certificate} at index 0
         *      corresponding to the private key.
         *
         * @exception NullPointerException if
         *      {@code privateKey} or {@code chain}
         *      is {@code null}
         * @exception IllegalArgumentException if the specified chain has a
         *      length of 0, if the specified chain does not contain
         *      {@code Certificate}s of the same type,
         *      or if the {@code PrivateKey} algorithm
         *      does not match the algorithm of the {@code PublicKey}
         *      in the end entity {@code Certificate} (at index 0)
         */
        public PrivateKeyEntry(PrivateKey privateKey, Certificate[] chain) {
            this(privateKey, chain, Collections.<Attribute>emptySet());
        }

        /**
         * Constructs a {@code PrivateKeyEntry} with a {@code PrivateKey} and
         * corresponding certificate chain and associated entry attributes.
         *
         * <p> The specified {@code chain} and {@code attributes} are cloned
         * before they are stored in the new {@code PrivateKeyEntry} object.
         *
         * <p>
         *  使用{@code PrivateKey}和相应的证书链和相关条目属性构造{@code PrivateKeyEntry}
         * 
         *  <p>指定的{@code chain}和{@code属性}在存储到新的{@code PrivateKeyEntry}对象之前进行克隆
         * 
         * 
         * @param privateKey the {@code PrivateKey}
         * @param chain an array of {@code Certificate}s
         *      representing the certificate chain.
         *      The chain must be ordered and contain a
         *      {@code Certificate} at index 0
         *      corresponding to the private key.
         * @param attributes the attributes
         *
         * @exception NullPointerException if {@code privateKey}, {@code chain}
         *      or {@code attributes} is {@code null}
         * @exception IllegalArgumentException if the specified chain has a
         *      length of 0, if the specified chain does not contain
         *      {@code Certificate}s of the same type,
         *      or if the {@code PrivateKey} algorithm
         *      does not match the algorithm of the {@code PublicKey}
         *      in the end entity {@code Certificate} (at index 0)
         *
         * @since 1.8
         */
        public PrivateKeyEntry(PrivateKey privateKey, Certificate[] chain,
           Set<Attribute> attributes) {

            if (privateKey == null || chain == null || attributes == null) {
                throw new NullPointerException("invalid null input");
            }
            if (chain.length == 0) {
                throw new IllegalArgumentException
                                ("invalid zero-length input chain");
            }

            Certificate[] clonedChain = chain.clone();
            String certType = clonedChain[0].getType();
            for (int i = 1; i < clonedChain.length; i++) {
                if (!certType.equals(clonedChain[i].getType())) {
                    throw new IllegalArgumentException
                                ("chain does not contain certificates " +
                                "of the same type");
                }
            }
            if (!privateKey.getAlgorithm().equals
                        (clonedChain[0].getPublicKey().getAlgorithm())) {
                throw new IllegalArgumentException
                                ("private key algorithm does not match " +
                                "algorithm of public key in end entity " +
                                "certificate (at index 0)");
            }
            this.privKey = privateKey;

            if (clonedChain[0] instanceof X509Certificate &&
                !(clonedChain instanceof X509Certificate[])) {

                this.chain = new X509Certificate[clonedChain.length];
                System.arraycopy(clonedChain, 0,
                                this.chain, 0, clonedChain.length);
            } else {
                this.chain = clonedChain;
            }

            this.attributes =
                Collections.unmodifiableSet(new HashSet<>(attributes));
        }

        /**
         * Gets the {@code PrivateKey} from this entry.
         *
         * <p>
         *  从此条目获取{@code PrivateKey}
         * 
         * 
         * @return the {@code PrivateKey} from this entry
         */
        public PrivateKey getPrivateKey() {
            return privKey;
        }

        /**
         * Gets the {@code Certificate} chain from this entry.
         *
         * <p> The stored chain is cloned before being returned.
         *
         * <p>
         *  从此条目获取{@code Certificate}链
         * 
         *  <p>存储的链在被返回之前被克隆
         * 
         * 
         * @return an array of {@code Certificate}s corresponding
         *      to the certificate chain for the public key.
         *      If the certificates are of type X.509,
         *      the runtime type of the returned array is
         *      {@code X509Certificate[]}.
         */
        public Certificate[] getCertificateChain() {
            return chain.clone();
        }

        /**
         * Gets the end entity {@code Certificate}
         * from the certificate chain in this entry.
         *
         * <p>
         * 从此条目中的证书链中获取终端实体{@code Certificate}
         * 
         * 
         * @return the end entity {@code Certificate} (at index 0)
         *      from the certificate chain in this entry.
         *      If the certificate is of type X.509,
         *      the runtime type of the returned certificate is
         *      {@code X509Certificate}.
         */
        public Certificate getCertificate() {
            return chain[0];
        }

        /**
         * Retrieves the attributes associated with an entry.
         * <p>
         *
         * <p>
         *  检索与条目关联的属性
         * <p>
         * 
         * 
         * @return an unmodifiable {@code Set} of attributes, possibly empty
         *
         * @since 1.8
         */
        @Override
        public Set<Attribute> getAttributes() {
            return attributes;
        }

        /**
         * Returns a string representation of this PrivateKeyEntry.
         * <p>
         *  返回此PrivateKeyEntry的字符串表示形式
         * 
         * 
         * @return a string representation of this PrivateKeyEntry.
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Private key entry and certificate chain with "
                + chain.length + " elements:\r\n");
            for (Certificate cert : chain) {
                sb.append(cert);
                sb.append("\r\n");
            }
            return sb.toString();
        }

    }

    /**
     * A {@code KeyStore} entry that holds a {@code SecretKey}.
     *
     * <p>
     *  保存{@code SecretKey}的{@code KeyStore}条目
     * 
     * 
     * @since 1.5
     */
    public static final class SecretKeyEntry implements Entry {

        private final SecretKey sKey;
        private final Set<Attribute> attributes;

        /**
         * Constructs a {@code SecretKeyEntry} with a
         * {@code SecretKey}.
         *
         * <p>
         *  用{@code SecretKey}构造一个{@code SecretKeyEntry}
         * 
         * 
         * @param secretKey the {@code SecretKey}
         *
         * @exception NullPointerException if {@code secretKey}
         *      is {@code null}
         */
        public SecretKeyEntry(SecretKey secretKey) {
            if (secretKey == null) {
                throw new NullPointerException("invalid null input");
            }
            this.sKey = secretKey;
            this.attributes = Collections.<Attribute>emptySet();
        }

        /**
         * Constructs a {@code SecretKeyEntry} with a {@code SecretKey} and
         * associated entry attributes.
         *
         * <p> The specified {@code attributes} is cloned before it is stored
         * in the new {@code SecretKeyEntry} object.
         *
         * <p>
         *  使用{@code SecretKey}和相关的条目属性构造{@code SecretKeyEntry}
         * 
         *  <p>指定的{@code attributes}在被存储在新的{@code SecretKeyEntry}对象之前被克隆
         * 
         * 
         * @param secretKey the {@code SecretKey}
         * @param attributes the attributes
         *
         * @exception NullPointerException if {@code secretKey} or
         *     {@code attributes} is {@code null}
         *
         * @since 1.8
         */
        public SecretKeyEntry(SecretKey secretKey, Set<Attribute> attributes) {

            if (secretKey == null || attributes == null) {
                throw new NullPointerException("invalid null input");
            }
            this.sKey = secretKey;
            this.attributes =
                Collections.unmodifiableSet(new HashSet<>(attributes));
        }

        /**
         * Gets the {@code SecretKey} from this entry.
         *
         * <p>
         *  从此条目获取{@code SecretKey}
         * 
         * 
         * @return the {@code SecretKey} from this entry
         */
        public SecretKey getSecretKey() {
            return sKey;
        }

        /**
         * Retrieves the attributes associated with an entry.
         * <p>
         *
         * <p>
         *  检索与条目关联的属性
         * <p>
         * 
         * 
         * @return an unmodifiable {@code Set} of attributes, possibly empty
         *
         * @since 1.8
         */
        @Override
        public Set<Attribute> getAttributes() {
            return attributes;
        }

        /**
         * Returns a string representation of this SecretKeyEntry.
         * <p>
         *  返回此SecretKeyEntry的字符串表示形式
         * 
         * 
         * @return a string representation of this SecretKeyEntry.
         */
        public String toString() {
            return "Secret key entry with algorithm " + sKey.getAlgorithm();
        }
    }

    /**
     * A {@code KeyStore} entry that holds a trusted
     * {@code Certificate}.
     *
     * <p>
     *  保存受信任的{@code Certificate}的{@code KeyStore}条目
     * 
     * 
     * @since 1.5
     */
    public static final class TrustedCertificateEntry implements Entry {

        private final Certificate cert;
        private final Set<Attribute> attributes;

        /**
         * Constructs a {@code TrustedCertificateEntry} with a
         * trusted {@code Certificate}.
         *
         * <p>
         * 使用受信任的{@code Certificate}构造{@code TrustedCertificateEntry}
         * 
         * 
         * @param trustedCert the trusted {@code Certificate}
         *
         * @exception NullPointerException if
         *      {@code trustedCert} is {@code null}
         */
        public TrustedCertificateEntry(Certificate trustedCert) {
            if (trustedCert == null) {
                throw new NullPointerException("invalid null input");
            }
            this.cert = trustedCert;
            this.attributes = Collections.<Attribute>emptySet();
        }

        /**
         * Constructs a {@code TrustedCertificateEntry} with a
         * trusted {@code Certificate} and associated entry attributes.
         *
         * <p> The specified {@code attributes} is cloned before it is stored
         * in the new {@code TrustedCertificateEntry} object.
         *
         * <p>
         *  使用受信任的{@code Certificate}和相关条目属性构造{@code TrustedCertificateEntry}
         * 
         *  <p>指定的{@code属性}在存储到新的{@code TrustedCertificateEntry}对象之前进行克隆
         * 
         * 
         * @param trustedCert the trusted {@code Certificate}
         * @param attributes the attributes
         *
         * @exception NullPointerException if {@code trustedCert} or
         *     {@code attributes} is {@code null}
         *
         * @since 1.8
         */
        public TrustedCertificateEntry(Certificate trustedCert,
           Set<Attribute> attributes) {
            if (trustedCert == null || attributes == null) {
                throw new NullPointerException("invalid null input");
            }
            this.cert = trustedCert;
            this.attributes =
                Collections.unmodifiableSet(new HashSet<>(attributes));
        }

        /**
         * Gets the trusted {@code Certficate} from this entry.
         *
         * <p>
         *  从此条目获取受信任的{@code Certficate}
         * 
         * 
         * @return the trusted {@code Certificate} from this entry
         */
        public Certificate getTrustedCertificate() {
            return cert;
        }

        /**
         * Retrieves the attributes associated with an entry.
         * <p>
         *
         * <p>
         *  检索与条目关联的属性
         * <p>
         * 
         * 
         * @return an unmodifiable {@code Set} of attributes, possibly empty
         *
         * @since 1.8
         */
        @Override
        public Set<Attribute> getAttributes() {
            return attributes;
        }

        /**
         * Returns a string representation of this TrustedCertificateEntry.
         * <p>
         *  返回此TrustedCertificateEntry的字符串表示形式
         * 
         * 
         * @return a string representation of this TrustedCertificateEntry.
         */
        public String toString() {
            return "Trusted certificate entry:\r\n" + cert.toString();
        }
    }

    /**
     * Creates a KeyStore object of the given type, and encapsulates the given
     * provider implementation (SPI object) in it.
     *
     * <p>
     *  创建给定类型的KeyStore对象,并将给定的提供程序实现(SPI对象)封装在其中
     * 
     * 
     * @param keyStoreSpi the provider implementation.
     * @param provider the provider.
     * @param type the keystore type.
     */
    protected KeyStore(KeyStoreSpi keyStoreSpi, Provider provider, String type)
    {
        this.keyStoreSpi = keyStoreSpi;
        this.provider = provider;
        this.type = type;

        if (!skipDebug && pdebug != null) {
            pdebug.println("KeyStore." + type.toUpperCase() + " type from: " +
                this.provider.getName());
        }
    }

    /**
     * Returns a keystore object of the specified type.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new KeyStore object encapsulating the
     * KeyStoreSpi implementation from the first
     * Provider that supports the specified type is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回指定类型的密钥库对象
     * 
     * <p>此方法遍历注册的安全提供程序列表,从最优选的提供程序A开始,新的KeyStore对象封装了来自支持指定类型的第一个提供程序的KeyStoreSpi实现
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()SecuritygetProviders()}方法检索注册提供商的列表
     * 
     * 
     * @param type the type of keystore.
     * See the KeyStore section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyStore">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard keystore types.
     *
     * @return a keystore object of the specified type.
     *
     * @exception KeyStoreException if no Provider supports a
     *          KeyStoreSpi implementation for the
     *          specified type.
     *
     * @see Provider
     */
    public static KeyStore getInstance(String type)
        throws KeyStoreException
    {
        try {
            Object[] objs = Security.getImpl(type, "KeyStore", (String)null);
            return new KeyStore((KeyStoreSpi)objs[0], (Provider)objs[1], type);
        } catch (NoSuchAlgorithmException nsae) {
            throw new KeyStoreException(type + " not found", nsae);
        } catch (NoSuchProviderException nspe) {
            throw new KeyStoreException(type + " not found", nspe);
        }
    }

    /**
     * Returns a keystore object of the specified type.
     *
     * <p> A new KeyStore object encapsulating the
     * KeyStoreSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回指定类型的密钥库对象
     * 
     *  <p>返回一个新的KeyStore对象,用于封装来自指定提供者的KeyStoreSpi实现。指定的提供者必须在安全提供者列表中注册
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()SecuritygetProviders()}方法检索注册提供商的列表
     * 
     * 
     * @param type the type of keystore.
     * See the KeyStore section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyStore">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard keystore types.
     *
     * @param provider the name of the provider.
     *
     * @return a keystore object of the specified type.
     *
     * @exception KeyStoreException if a KeyStoreSpi
     *          implementation for the specified type is not
     *          available from the specified provider.
     *
     * @exception NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the provider name is null
     *          or empty.
     *
     * @see Provider
     */
    public static KeyStore getInstance(String type, String provider)
        throws KeyStoreException, NoSuchProviderException
    {
        if (provider == null || provider.length() == 0)
            throw new IllegalArgumentException("missing provider");
        try {
            Object[] objs = Security.getImpl(type, "KeyStore", provider);
            return new KeyStore((KeyStoreSpi)objs[0], (Provider)objs[1], type);
        } catch (NoSuchAlgorithmException nsae) {
            throw new KeyStoreException(type + " not found", nsae);
        }
    }

    /**
     * Returns a keystore object of the specified type.
     *
     * <p> A new KeyStore object encapsulating the
     * KeyStoreSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     * 返回指定类型的密钥库对象
     * 
     *  <p>返回一个新的KeyStore对象,该对象封装了来自指定Provider对象的KeyStoreSpi实现。注意,指定的Provider对象不必在提供程序列表中注册
     * 
     * 
     * @param type the type of keystore.
     * See the KeyStore section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyStore">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard keystore types.
     *
     * @param provider the provider.
     *
     * @return a keystore object of the specified type.
     *
     * @exception KeyStoreException if KeyStoreSpi
     *          implementation for the specified type is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the specified provider is null.
     *
     * @see Provider
     *
     * @since 1.4
     */
    public static KeyStore getInstance(String type, Provider provider)
        throws KeyStoreException
    {
        if (provider == null)
            throw new IllegalArgumentException("missing provider");
        try {
            Object[] objs = Security.getImpl(type, "KeyStore", provider);
            return new KeyStore((KeyStoreSpi)objs[0], (Provider)objs[1], type);
        } catch (NoSuchAlgorithmException nsae) {
            throw new KeyStoreException(type + " not found", nsae);
        }
    }

    /**
     * Returns the default keystore type as specified by the
     * {@code keystore.type} security property, or the string
     * {@literal "jks"} (acronym for {@literal "Java keystore"})
     * if no such property exists.
     *
     * <p>The default keystore type can be used by applications that do not
     * want to use a hard-coded keystore type when calling one of the
     * {@code getInstance} methods, and want to provide a default keystore
     * type in case a user does not specify its own.
     *
     * <p>The default keystore type can be changed by setting the value of the
     * {@code keystore.type} security property to the desired keystore type.
     *
     * <p>
     *  如果没有此类属性,则返回由{@code keystoretype}安全属性指定的默认密钥库类型,或返回字符串{@literal"jks"}({@literal"Java keystore"}的缩写)。
     * 
     *  <p>当调用{@code getInstance}方法之一时,并且希望提供默认密钥库类型以防用户不使用硬编码密钥库类型的应用程序时,可以使用默认密钥库类型指定自己的
     * 
     * <p>可以通过将{@code keystoretype}安全属性的值设置为所需的密钥库类型来更改默认密钥库类型
     * 
     * 
     * @return the default keystore type as specified by the
     * {@code keystore.type} security property, or the string {@literal "jks"}
     * if no such property exists.
     * @see java.security.Security security properties
     */
    public final static String getDefaultType() {
        String kstype;
        kstype = AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return Security.getProperty(KEYSTORE_TYPE);
            }
        });
        if (kstype == null) {
            kstype = "jks";
        }
        return kstype;
    }

    /**
     * Returns the provider of this keystore.
     *
     * <p>
     *  返回此密钥库的提供程序
     * 
     * 
     * @return the provider of this keystore.
     */
    public final Provider getProvider()
    {
        return this.provider;
    }

    /**
     * Returns the type of this keystore.
     *
     * <p>
     *  返回此密钥库的类型
     * 
     * 
     * @return the type of this keystore.
     */
    public final String getType()
    {
        return this.type;
    }

    /**
     * Returns the key associated with the given alias, using the given
     * password to recover it.  The key must have been associated with
     * the alias by a call to {@code setKeyEntry},
     * or by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry} or {@code SecretKeyEntry}.
     *
     * <p>
     *  返回与给定别名相关联的键,使用给定的密码来恢复密钥键必须已经通过调用{@code setKeyEntry}与别名相关联,或通过调用{@code setEntry}与{@code PrivateKeyEntry}
     * 或{@code SecretKeyEntry}。
     * 
     * 
     * @param alias the alias name
     * @param password the password for recovering the key
     *
     * @return the requested key, or null if the given alias does not exist
     * or does not identify a key-related entry.
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     * @exception NoSuchAlgorithmException if the algorithm for recovering the
     * key cannot be found
     * @exception UnrecoverableKeyException if the key cannot be recovered
     * (e.g., the given password is wrong).
     */
    public final Key getKey(String alias, char[] password)
        throws KeyStoreException, NoSuchAlgorithmException,
            UnrecoverableKeyException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineGetKey(alias, password);
    }

    /**
     * Returns the certificate chain associated with the given alias.
     * The certificate chain must have been associated with the alias
     * by a call to {@code setKeyEntry},
     * or by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry}.
     *
     * <p>
     *  返回与给定别名相关联的证书链证书链必须已通过对{@code setKeyEntry}的调用或通过调用{@code setEntry}与{@code PrivateKeyEntry}的别名相关联,
     * 
     * 
     * @param alias the alias name
     *
     * @return the certificate chain (ordered with the user's certificate first
     * followed by zero or more certificate authorities), or null if the given alias
     * does not exist or does not contain a certificate chain
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final Certificate[] getCertificateChain(String alias)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineGetCertificateChain(alias);
    }

    /**
     * Returns the certificate associated with the given alias.
     *
     * <p> If the given alias name identifies an entry
     * created by a call to {@code setCertificateEntry},
     * or created by a call to {@code setEntry} with a
     * {@code TrustedCertificateEntry},
     * then the trusted certificate contained in that entry is returned.
     *
     * <p> If the given alias name identifies an entry
     * created by a call to {@code setKeyEntry},
     * or created by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry},
     * then the first element of the certificate chain in that entry
     * is returned.
     *
     * <p>
     *  返回与给定别名相关联的证书
     * 
     * <p>如果给定的别名标识了通过调用{@code setCertificateEntry}创建的条目,或者通过使用{@code TrustedCertificateEntry}调用{@code setEntry}
     * 创建的条目,则该条目中包含的受信任证书回。
     * 
     *  <p>如果给定的别名标识了通过调用{@code setKeyEntry}创建的条目,或通过使用{@code PrivateKeyEntry}调用{@code setEntry}创建的条目,则证书链的第
     * 一个元素返回该条目。
     * 
     * 
     * @param alias the alias name
     *
     * @return the certificate, or null if the given alias does not exist or
     * does not contain a certificate.
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final Certificate getCertificate(String alias)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineGetCertificate(alias);
    }

    /**
     * Returns the creation date of the entry identified by the given alias.
     *
     * <p>
     *  返回由给定别名标识的条目的创建日期
     * 
     * 
     * @param alias the alias name
     *
     * @return the creation date of this entry, or null if the given alias does
     * not exist
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final Date getCreationDate(String alias)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineGetCreationDate(alias);
    }

    /**
     * Assigns the given key to the given alias, protecting it with the given
     * password.
     *
     * <p>If the given key is of type {@code java.security.PrivateKey},
     * it must be accompanied by a certificate chain certifying the
     * corresponding public key.
     *
     * <p>If the given alias already exists, the keystore information
     * associated with it is overridden by the given key (and possibly
     * certificate chain).
     *
     * <p>
     *  将给定的键分配给给定的别名,用给定的密码保护它
     * 
     *  <p>如果给定的键是{@code javasecurityPrivateKey}类型,它必须伴随证书链,证明相应的公钥
     * 
     * <p>如果给定的别名已经存在,则与其关联的密钥库信息将被给定的密钥(以及可能的证书链)覆盖,
     * 
     * 
     * @param alias the alias name
     * @param key the key to be associated with the alias
     * @param password the password to protect the key
     * @param chain the certificate chain for the corresponding public
     * key (only required if the given key is of type
     * {@code java.security.PrivateKey}).
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded), the given key cannot be protected, or this operation fails
     * for some other reason
     */
    public final void setKeyEntry(String alias, Key key, char[] password,
                                  Certificate[] chain)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        if ((key instanceof PrivateKey) &&
            (chain == null || chain.length == 0)) {
            throw new IllegalArgumentException("Private key must be "
                                               + "accompanied by certificate "
                                               + "chain");
        }
        keyStoreSpi.engineSetKeyEntry(alias, key, password, chain);
    }

    /**
     * Assigns the given key (that has already been protected) to the given
     * alias.
     *
     * <p>If the protected key is of type
     * {@code java.security.PrivateKey}, it must be accompanied by a
     * certificate chain certifying the corresponding public key. If the
     * underlying keystore implementation is of type {@code jks},
     * {@code key} must be encoded as an
     * {@code EncryptedPrivateKeyInfo} as defined in the PKCS #8 standard.
     *
     * <p>If the given alias already exists, the keystore information
     * associated with it is overridden by the given key (and possibly
     * certificate chain).
     *
     * <p>
     *  将给定的键(已经被保护)分配给给定的别名
     * 
     *  <p>如果受保护的密钥是{@code javasecurityPrivateKey}类型,则必须附带证书链证明相应的公钥。
     * 如果基本密钥库实现的类型为{@code jks},{@code key}必须编码为如在PKCS#8标准中定义的{@code EncryptedPrivateKeyInfo}。
     * 
     *  <p>如果给定的别名已经存在,则与其关联的密钥库信息将被给定的密钥(以及可能的证书链)覆盖,
     * 
     * 
     * @param alias the alias name
     * @param key the key (in protected format) to be associated with the alias
     * @param chain the certificate chain for the corresponding public
     *          key (only useful if the protected key is of type
     *          {@code java.security.PrivateKey}).
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded), or if this operation fails for some other reason.
     */
    public final void setKeyEntry(String alias, byte[] key,
                                  Certificate[] chain)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        keyStoreSpi.engineSetKeyEntry(alias, key, chain);
    }

    /**
     * Assigns the given trusted certificate to the given alias.
     *
     * <p> If the given alias identifies an existing entry
     * created by a call to {@code setCertificateEntry},
     * or created by a call to {@code setEntry} with a
     * {@code TrustedCertificateEntry},
     * the trusted certificate in the existing entry
     * is overridden by the given certificate.
     *
     * <p>
     *  将给定的可信证书分配给给定的别名
     * 
     * <p>如果给定别名标识通过调用{@code setCertificateEntry}创建的现有条目,或通过使用{@code TrustedCertificateEntry}调用{@code setEntry}
     * 创建的现有条目,则现有条目中的受信任证书将被覆盖由给定的证书。
     * 
     * 
     * @param alias the alias name
     * @param cert the certificate
     *
     * @exception KeyStoreException if the keystore has not been initialized,
     * or the given alias already exists and does not identify an
     * entry containing a trusted certificate,
     * or this operation fails for some other reason.
     */
    public final void setCertificateEntry(String alias, Certificate cert)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        keyStoreSpi.engineSetCertificateEntry(alias, cert);
    }

    /**
     * Deletes the entry identified by the given alias from this keystore.
     *
     * <p>
     *  从此密钥库中删除由给定别名标识的条目
     * 
     * 
     * @param alias the alias name
     *
     * @exception KeyStoreException if the keystore has not been initialized,
     * or if the entry cannot be removed.
     */
    public final void deleteEntry(String alias)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        keyStoreSpi.engineDeleteEntry(alias);
    }

    /**
     * Lists all the alias names of this keystore.
     *
     * <p>
     *  列出此密钥库的所有别名
     * 
     * 
     * @return enumeration of the alias names
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final Enumeration<String> aliases()
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineAliases();
    }

    /**
     * Checks if the given alias exists in this keystore.
     *
     * <p>
     *  检查此密钥库中是否存在给定别名
     * 
     * 
     * @param alias the alias name
     *
     * @return true if the alias exists, false otherwise
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final boolean containsAlias(String alias)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineContainsAlias(alias);
    }

    /**
     * Retrieves the number of entries in this keystore.
     *
     * <p>
     *  检索此密钥库中的条目数
     * 
     * 
     * @return the number of entries in this keystore
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final int size()
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineSize();
    }

    /**
     * Returns true if the entry identified by the given alias
     * was created by a call to {@code setKeyEntry},
     * or created by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry} or a {@code SecretKeyEntry}.
     *
     * <p>
     *  如果给定别名标识的条目是通过调用{@code setKeyEntry}创建的,或者通过使用{@code PrivateKeyEntry}或{@code SecretKeyEntry}调用{@code setEntry}
     * 创建的,。
     * 
     * 
     * @param alias the alias for the keystore entry to be checked
     *
     * @return true if the entry identified by the given alias is a
     * key-related entry, false otherwise.
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final boolean isKeyEntry(String alias)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineIsKeyEntry(alias);
    }

    /**
     * Returns true if the entry identified by the given alias
     * was created by a call to {@code setCertificateEntry},
     * or created by a call to {@code setEntry} with a
     * {@code TrustedCertificateEntry}.
     *
     * <p>
     * 如果由给定别名标识的条目是通过调用{@code setCertificateEntry}创建的,或者通过使用{@code TrustedCertificateEntry}调用{@code setEntry}
     * 创建的,。
     * 
     * 
     * @param alias the alias for the keystore entry to be checked
     *
     * @return true if the entry identified by the given alias contains a
     * trusted certificate, false otherwise.
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final boolean isCertificateEntry(String alias)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineIsCertificateEntry(alias);
    }

    /**
     * Returns the (alias) name of the first keystore entry whose certificate
     * matches the given certificate.
     *
     * <p> This method attempts to match the given certificate with each
     * keystore entry. If the entry being considered was
     * created by a call to {@code setCertificateEntry},
     * or created by a call to {@code setEntry} with a
     * {@code TrustedCertificateEntry},
     * then the given certificate is compared to that entry's certificate.
     *
     * <p> If the entry being considered was
     * created by a call to {@code setKeyEntry},
     * or created by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry},
     * then the given certificate is compared to the first
     * element of that entry's certificate chain.
     *
     * <p>
     *  返回其证书与给定证书匹配的第一个密钥库条目的(别名)名称
     * 
     *  <p>此方法尝试将给定证书与每个密钥库条目进行匹配如果正在考虑的条目是通过调用{@code setCertificateEntry}创建的,或者通过使用{@code TrustedCertificateEntry}
     * 调用{@code setEntry} ,则将给定证书与该条目的证书进行比较。
     * 
     * <p>如果正在考虑的条目是通过调用{@code setKeyEntry}创建的,或者通过使用{@code PrivateKeyEntry}调用{@code setEntry}创建,那么给定的证书将与第一
     * 个元素该条目的证书链。
     * 
     * 
     * @param cert the certificate to match with.
     *
     * @return the alias name of the first entry with a matching certificate,
     * or null if no such entry exists in this keystore.
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     */
    public final String getCertificateAlias(Certificate cert)
        throws KeyStoreException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineGetCertificateAlias(cert);
    }

    /**
     * Stores this keystore to the given output stream, and protects its
     * integrity with the given password.
     *
     * <p>
     *  将此密钥库存储到给定的输出流,并使用给定的密码保护其完整性
     * 
     * 
     * @param stream the output stream to which this keystore is written.
     * @param password the password to generate the keystore integrity check
     *
     * @exception KeyStoreException if the keystore has not been initialized
     * (loaded).
     * @exception IOException if there was an I/O problem with data
     * @exception NoSuchAlgorithmException if the appropriate data integrity
     * algorithm could not be found
     * @exception CertificateException if any of the certificates included in
     * the keystore data could not be stored
     */
    public final void store(OutputStream stream, char[] password)
        throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException
    {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        keyStoreSpi.engineStore(stream, password);
    }

    /**
     * Stores this keystore using the given {@code LoadStoreParameter}.
     *
     * <p>
     *  使用给定的{@code LoadStoreParameter}存储此密钥库
     * 
     * 
     * @param param the {@code LoadStoreParameter}
     *          that specifies how to store the keystore,
     *          which may be {@code null}
     *
     * @exception IllegalArgumentException if the given
     *          {@code LoadStoreParameter}
     *          input is not recognized
     * @exception KeyStoreException if the keystore has not been initialized
     *          (loaded)
     * @exception IOException if there was an I/O problem with data
     * @exception NoSuchAlgorithmException if the appropriate data integrity
     *          algorithm could not be found
     * @exception CertificateException if any of the certificates included in
     *          the keystore data could not be stored
     *
     * @since 1.5
     */
    public final void store(LoadStoreParameter param)
                throws KeyStoreException, IOException,
                NoSuchAlgorithmException, CertificateException {
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        keyStoreSpi.engineStore(param);
    }

    /**
     * Loads this KeyStore from the given input stream.
     *
     * <p>A password may be given to unlock the keystore
     * (e.g. the keystore resides on a hardware token device),
     * or to check the integrity of the keystore data.
     * If a password is not given for integrity checking,
     * then integrity checking is not performed.
     *
     * <p>In order to create an empty keystore, or if the keystore cannot
     * be initialized from a stream, pass {@code null}
     * as the {@code stream} argument.
     *
     * <p> Note that if this keystore has already been loaded, it is
     * reinitialized and loaded again from the given input stream.
     *
     * <p>
     *  从给定的输入流加载此KeyStore
     * 
     *  <p>可以给出密码以解锁密钥库(例如,密钥库驻留在硬件令牌设备上),或者检查密钥库数据的完整性。如果没有给出完整性检查的密码,则不执行完整性检查
     * 
     * <p>要创建空密钥库,或者无法从流初始化密钥库,请将{@code null}作为{@code stream}参数传递
     * 
     *  <p>请注意,如果此密钥库已加载,则会重新初始化,并从给定的输入流重新加载
     * 
     * 
     * @param stream the input stream from which the keystore is loaded,
     * or {@code null}
     * @param password the password used to check the integrity of
     * the keystore, the password used to unlock the keystore,
     * or {@code null}
     *
     * @exception IOException if there is an I/O or format problem with the
     * keystore data, if a password is required but not given,
     * or if the given password was incorrect. If the error is due to a
     * wrong password, the {@link Throwable#getCause cause} of the
     * {@code IOException} should be an
     * {@code UnrecoverableKeyException}
     * @exception NoSuchAlgorithmException if the algorithm used to check
     * the integrity of the keystore cannot be found
     * @exception CertificateException if any of the certificates in the
     * keystore could not be loaded
     */
    public final void load(InputStream stream, char[] password)
        throws IOException, NoSuchAlgorithmException, CertificateException
    {
        keyStoreSpi.engineLoad(stream, password);
        initialized = true;
    }

    /**
     * Loads this keystore using the given {@code LoadStoreParameter}.
     *
     * <p> Note that if this KeyStore has already been loaded, it is
     * reinitialized and loaded again from the given parameter.
     *
     * <p>
     *  使用给定的{@code LoadStoreParameter}加载此密钥库
     * 
     *  <p>请注意,如果此KeyStore已加载,则会重新初始化并从给定参数重新加载
     * 
     * 
     * @param param the {@code LoadStoreParameter}
     *          that specifies how to load the keystore,
     *          which may be {@code null}
     *
     * @exception IllegalArgumentException if the given
     *          {@code LoadStoreParameter}
     *          input is not recognized
     * @exception IOException if there is an I/O or format problem with the
     *          keystore data. If the error is due to an incorrect
     *         {@code ProtectionParameter} (e.g. wrong password)
     *         the {@link Throwable#getCause cause} of the
     *         {@code IOException} should be an
     *         {@code UnrecoverableKeyException}
     * @exception NoSuchAlgorithmException if the algorithm used to check
     *          the integrity of the keystore cannot be found
     * @exception CertificateException if any of the certificates in the
     *          keystore could not be loaded
     *
     * @since 1.5
     */
    public final void load(LoadStoreParameter param)
                throws IOException, NoSuchAlgorithmException,
                CertificateException {

        keyStoreSpi.engineLoad(param);
        initialized = true;
    }

    /**
     * Gets a keystore {@code Entry} for the specified alias
     * with the specified protection parameter.
     *
     * <p>
     *  为指定的具有指定保护参数的别名获取密钥库{@code Entry}
     * 
     * 
     * @param alias get the keystore {@code Entry} for this alias
     * @param protParam the {@code ProtectionParameter}
     *          used to protect the {@code Entry},
     *          which may be {@code null}
     *
     * @return the keystore {@code Entry} for the specified alias,
     *          or {@code null} if there is no such entry
     *
     * @exception NullPointerException if
     *          {@code alias} is {@code null}
     * @exception NoSuchAlgorithmException if the algorithm for recovering the
     *          entry cannot be found
     * @exception UnrecoverableEntryException if the specified
     *          {@code protParam} were insufficient or invalid
     * @exception UnrecoverableKeyException if the entry is a
     *          {@code PrivateKeyEntry} or {@code SecretKeyEntry}
     *          and the specified {@code protParam} does not contain
     *          the information needed to recover the key (e.g. wrong password)
     * @exception KeyStoreException if the keystore has not been initialized
     *          (loaded).
     * @see #setEntry(String, KeyStore.Entry, KeyStore.ProtectionParameter)
     *
     * @since 1.5
     */
    public final Entry getEntry(String alias, ProtectionParameter protParam)
                throws NoSuchAlgorithmException, UnrecoverableEntryException,
                KeyStoreException {

        if (alias == null) {
            throw new NullPointerException("invalid null input");
        }
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineGetEntry(alias, protParam);
    }

    /**
     * Saves a keystore {@code Entry} under the specified alias.
     * The protection parameter is used to protect the
     * {@code Entry}.
     *
     * <p> If an entry already exists for the specified alias,
     * it is overridden.
     *
     * <p>
     *  在指定的别名下保存密钥库{@code Entry}保护参数用于保护{@code Entry}
     * 
     *  <p>如果指定别名的某个条目已存在,则将覆盖该条目
     * 
     * 
     * @param alias save the keystore {@code Entry} under this alias
     * @param entry the {@code Entry} to save
     * @param protParam the {@code ProtectionParameter}
     *          used to protect the {@code Entry},
     *          which may be {@code null}
     *
     * @exception NullPointerException if
     *          {@code alias} or {@code entry}
     *          is {@code null}
     * @exception KeyStoreException if the keystore has not been initialized
     *          (loaded), or if this operation fails for some other reason
     *
     * @see #getEntry(String, KeyStore.ProtectionParameter)
     *
     * @since 1.5
     */
    public final void setEntry(String alias, Entry entry,
                        ProtectionParameter protParam)
                throws KeyStoreException {
        if (alias == null || entry == null) {
            throw new NullPointerException("invalid null input");
        }
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        keyStoreSpi.engineSetEntry(alias, entry, protParam);
    }

    /**
     * Determines if the keystore {@code Entry} for the specified
     * {@code alias} is an instance or subclass of the specified
     * {@code entryClass}.
     *
     * <p>
     * 确定指定的{@code alias}的密钥库{@code Entry}是否是指定的{@code entryClass}的实例或子类,
     * 
     * 
     * @param alias the alias name
     * @param entryClass the entry class
     *
     * @return true if the keystore {@code Entry} for the specified
     *          {@code alias} is an instance or subclass of the
     *          specified {@code entryClass}, false otherwise
     *
     * @exception NullPointerException if
     *          {@code alias} or {@code entryClass}
     *          is {@code null}
     * @exception KeyStoreException if the keystore has not been
     *          initialized (loaded)
     *
     * @since 1.5
     */
    public final boolean
        entryInstanceOf(String alias,
                        Class<? extends KeyStore.Entry> entryClass)
        throws KeyStoreException
    {

        if (alias == null || entryClass == null) {
            throw new NullPointerException("invalid null input");
        }
        if (!initialized) {
            throw new KeyStoreException("Uninitialized keystore");
        }
        return keyStoreSpi.engineEntryInstanceOf(alias, entryClass);
    }

    /**
     * A description of a to-be-instantiated KeyStore object.
     *
     * <p>An instance of this class encapsulates the information needed to
     * instantiate and initialize a KeyStore object. That process is
     * triggered when the {@linkplain #getKeyStore} method is called.
     *
     * <p>This makes it possible to decouple configuration from KeyStore
     * object creation and e.g. delay a password prompt until it is
     * needed.
     *
     * <p>
     *  对要实例化的KeyStore对象的描述
     * 
     *  <p>此类的实例封装了实例化和初始化KeyStore对象所需的信息当调用{@linkplain #getKeyStore}方法时触发该过程
     * 
     *  <p>这使得可以从KeyStore对象创建中解除配置,例如延迟密码提示,直到需要它
     * 
     * 
     * @see KeyStore
     * @see javax.net.ssl.KeyStoreBuilderParameters
     * @since 1.5
     */
    public static abstract class Builder {

        // maximum times to try the callbackhandler if the password is wrong
        static final int MAX_CALLBACK_TRIES = 3;

        /**
         * Construct a new Builder.
         * <p>
         *  构建一个新的Builder
         * 
         */
        protected Builder() {
            // empty
        }

        /**
         * Returns the KeyStore described by this object.
         *
         * <p>
         *  返回此对象描述的KeyStore
         * 
         * 
         * @return the {@code KeyStore} described by this object
         * @exception KeyStoreException if an error occurred during the
         *   operation, for example if the KeyStore could not be
         *   instantiated or loaded
         */
        public abstract KeyStore getKeyStore() throws KeyStoreException;

        /**
         * Returns the ProtectionParameters that should be used to obtain
         * the {@link KeyStore.Entry Entry} with the given alias.
         * The {@code getKeyStore} method must be invoked before this
         * method may be called.
         *
         * <p>
         *  返回应用于获取带有给定别名的{@link KeyStoreEntry Entry}的ProtectionParameters在调用此方法之前,必须调用{@code getKeyStore}方法
         * 
         * 
         * @return the ProtectionParameters that should be used to obtain
         *   the {@link KeyStore.Entry Entry} with the given alias.
         * @param alias the alias of the KeyStore entry
         * @throws NullPointerException if alias is null
         * @throws KeyStoreException if an error occurred during the
         *   operation
         * @throws IllegalStateException if the getKeyStore method has
         *   not been invoked prior to calling this method
         */
        public abstract ProtectionParameter getProtectionParameter(String alias)
            throws KeyStoreException;

        /**
         * Returns a new Builder that encapsulates the given KeyStore.
         * The {@linkplain #getKeyStore} method of the returned object
         * will return {@code keyStore}, the {@linkplain
         * #getProtectionParameter getProtectionParameter()} method will
         * return {@code protectionParameters}.
         *
         * <p> This is useful if an existing KeyStore object needs to be
         * used with Builder-based APIs.
         *
         * <p>
         * 返回一个封装了给定KeyStore的新Builder生成的返回对象的{@linkplain #getKeyStore}方法将返回{@code keyStore},{@linkplain #getProtectionParameter getProtectionParameter()}
         * 方法将返回{@code protectionParameters}。
         * 
         *  <p>如果现有的KeyStore对象需要与基于Builder的API一起使用,这将非常有用
         * 
         * 
         * @return a new Builder object
         * @param keyStore the KeyStore to be encapsulated
         * @param protectionParameter the ProtectionParameter used to
         *   protect the KeyStore entries
         * @throws NullPointerException if keyStore or
         *   protectionParameters is null
         * @throws IllegalArgumentException if the keyStore has not been
         *   initialized
         */
        public static Builder newInstance(final KeyStore keyStore,
                final ProtectionParameter protectionParameter) {
            if ((keyStore == null) || (protectionParameter == null)) {
                throw new NullPointerException();
            }
            if (keyStore.initialized == false) {
                throw new IllegalArgumentException("KeyStore not initialized");
            }
            return new Builder() {
                private volatile boolean getCalled;

                public KeyStore getKeyStore() {
                    getCalled = true;
                    return keyStore;
                }

                public ProtectionParameter getProtectionParameter(String alias)
                {
                    if (alias == null) {
                        throw new NullPointerException();
                    }
                    if (getCalled == false) {
                        throw new IllegalStateException
                            ("getKeyStore() must be called first");
                    }
                    return protectionParameter;
                }
            };
        }

        /**
         * Returns a new Builder object.
         *
         * <p>The first call to the {@link #getKeyStore} method on the returned
         * builder will create a KeyStore of type {@code type} and call
         * its {@link KeyStore#load load()} method.
         * The {@code inputStream} argument is constructed from
         * {@code file}.
         * If {@code protection} is a
         * {@code PasswordProtection}, the password is obtained by
         * calling the {@code getPassword} method.
         * Otherwise, if {@code protection} is a
         * {@code CallbackHandlerProtection}, the password is obtained
         * by invoking the CallbackHandler.
         *
         * <p>Subsequent calls to {@link #getKeyStore} return the same object
         * as the initial call. If the initial call to failed with a
         * KeyStoreException, subsequent calls also throw a
         * KeyStoreException.
         *
         * <p>The KeyStore is instantiated from {@code provider} if
         * non-null. Otherwise, all installed providers are searched.
         *
         * <p>Calls to {@link #getProtectionParameter getProtectionParameter()}
         * will return a {@link KeyStore.PasswordProtection PasswordProtection}
         * object encapsulating the password that was used to invoke the
         * {@code load} method.
         *
         * <p><em>Note</em> that the {@link #getKeyStore} method is executed
         * within the {@link AccessControlContext} of the code invoking this
         * method.
         *
         * <p>
         *  返回一个新的Builder对象
         * 
         * <p>对返回的构建器上的{@link #getKeyStore}方法的第一次调用将创建类型为{@code type}的KeyStore并调用其{@link KeyStore#load load()}方法
         * {@code inputStream}参数从{@code file}构造如果{@code protection}是{@code PasswordProtection},则通过调用{@code getPassword}
         * 方法获取密码否则,如果{@code protection}是{@code CallbackHandlerProtection },密码是通过调用CallbackHandler获得的。
         * 
         *  <p>对{@link #getKeyStore}的后续调用返回与初始调用相同的对象如果使用KeyStoreException的初始调用失败,后续调用也会抛出一个KeyStoreException
         * 
         * <p>如果非null,则KeyStore将从{@code provider}实例化。否则,将搜索所有已安装的提供程序
         * 
         *  <p>调用{@link #getProtectionParameter getProtectionParameter()}会返回一个{@link KeyStorePasswordProtection PasswordProtection}
         * 对象,其中包含用于调用{@code load}方法的密码。
         * 
         *  <p> <em>注意</em> {@link #getKeyStore}方法在调用此方法的代码的{@link AccessControlContext}中执行
         * 
         * 
         * @return a new Builder object
         * @param type the type of KeyStore to be constructed
         * @param provider the provider from which the KeyStore is to
         *   be instantiated (or null)
         * @param file the File that contains the KeyStore data
         * @param protection the ProtectionParameter securing the KeyStore data
         * @throws NullPointerException if type, file or protection is null
         * @throws IllegalArgumentException if protection is not an instance
         *   of either PasswordProtection or CallbackHandlerProtection; or
         *   if file does not exist or does not refer to a normal file
         */
        public static Builder newInstance(String type, Provider provider,
                File file, ProtectionParameter protection) {
            if ((type == null) || (file == null) || (protection == null)) {
                throw new NullPointerException();
            }
            if ((protection instanceof PasswordProtection == false) &&
                (protection instanceof CallbackHandlerProtection == false)) {
                throw new IllegalArgumentException
                ("Protection must be PasswordProtection or " +
                 "CallbackHandlerProtection");
            }
            if (file.isFile() == false) {
                throw new IllegalArgumentException
                    ("File does not exist or it does not refer " +
                     "to a normal file: " + file);
            }
            return new FileBuilder(type, provider, file, protection,
                AccessController.getContext());
        }

        private static final class FileBuilder extends Builder {

            private final String type;
            private final Provider provider;
            private final File file;
            private ProtectionParameter protection;
            private ProtectionParameter keyProtection;
            private final AccessControlContext context;

            private KeyStore keyStore;

            private Throwable oldException;

            FileBuilder(String type, Provider provider, File file,
                    ProtectionParameter protection,
                    AccessControlContext context) {
                this.type = type;
                this.provider = provider;
                this.file = file;
                this.protection = protection;
                this.context = context;
            }

            public synchronized KeyStore getKeyStore() throws KeyStoreException
            {
                if (keyStore != null) {
                    return keyStore;
                }
                if (oldException != null) {
                    throw new KeyStoreException
                        ("Previous KeyStore instantiation failed",
                         oldException);
                }
                PrivilegedExceptionAction<KeyStore> action =
                        new PrivilegedExceptionAction<KeyStore>() {
                    public KeyStore run() throws Exception {
                        if (protection instanceof CallbackHandlerProtection == false) {
                            return run0();
                        }
                        // when using a CallbackHandler,
                        // reprompt if the password is wrong
                        int tries = 0;
                        while (true) {
                            tries++;
                            try {
                                return run0();
                            } catch (IOException e) {
                                if ((tries < MAX_CALLBACK_TRIES)
                                        && (e.getCause() instanceof UnrecoverableKeyException)) {
                                    continue;
                                }
                                throw e;
                            }
                        }
                    }
                    public KeyStore run0() throws Exception {
                        KeyStore ks;
                        if (provider == null) {
                            ks = KeyStore.getInstance(type);
                        } else {
                            ks = KeyStore.getInstance(type, provider);
                        }
                        InputStream in = null;
                        char[] password = null;
                        try {
                            in = new FileInputStream(file);
                            if (protection instanceof PasswordProtection) {
                                password =
                                ((PasswordProtection)protection).getPassword();
                                keyProtection = protection;
                            } else {
                                CallbackHandler handler =
                                    ((CallbackHandlerProtection)protection)
                                    .getCallbackHandler();
                                PasswordCallback callback = new PasswordCallback
                                    ("Password for keystore " + file.getName(),
                                    false);
                                handler.handle(new Callback[] {callback});
                                password = callback.getPassword();
                                if (password == null) {
                                    throw new KeyStoreException("No password" +
                                                                " provided");
                                }
                                callback.clearPassword();
                                keyProtection = new PasswordProtection(password);
                            }
                            ks.load(in, password);
                            return ks;
                        } finally {
                            if (in != null) {
                                in.close();
                            }
                        }
                    }
                };
                try {
                    keyStore = AccessController.doPrivileged(action, context);
                    return keyStore;
                } catch (PrivilegedActionException e) {
                    oldException = e.getCause();
                    throw new KeyStoreException
                        ("KeyStore instantiation failed", oldException);
                }
            }

            public synchronized ProtectionParameter
                        getProtectionParameter(String alias) {
                if (alias == null) {
                    throw new NullPointerException();
                }
                if (keyStore == null) {
                    throw new IllegalStateException
                        ("getKeyStore() must be called first");
                }
                return keyProtection;
            }
        }

        /**
         * Returns a new Builder object.
         *
         * <p>Each call to the {@link #getKeyStore} method on the returned
         * builder will return a new KeyStore object of type {@code type}.
         * Its {@link KeyStore#load(KeyStore.LoadStoreParameter) load()}
         * method is invoked using a
         * {@code LoadStoreParameter} that encapsulates
         * {@code protection}.
         *
         * <p>The KeyStore is instantiated from {@code provider} if
         * non-null. Otherwise, all installed providers are searched.
         *
         * <p>Calls to {@link #getProtectionParameter getProtectionParameter()}
         * will return {@code protection}.
         *
         * <p><em>Note</em> that the {@link #getKeyStore} method is executed
         * within the {@link AccessControlContext} of the code invoking this
         * method.
         *
         * <p>
         * 
         * @return a new Builder object
         * @param type the type of KeyStore to be constructed
         * @param provider the provider from which the KeyStore is to
         *   be instantiated (or null)
         * @param protection the ProtectionParameter securing the Keystore
         * @throws NullPointerException if type or protection is null
         */
        public static Builder newInstance(final String type,
                final Provider provider, final ProtectionParameter protection) {
            if ((type == null) || (protection == null)) {
                throw new NullPointerException();
            }
            final AccessControlContext context = AccessController.getContext();
            return new Builder() {
                private volatile boolean getCalled;
                private IOException oldException;

                private final PrivilegedExceptionAction<KeyStore> action
                        = new PrivilegedExceptionAction<KeyStore>() {

                    public KeyStore run() throws Exception {
                        KeyStore ks;
                        if (provider == null) {
                            ks = KeyStore.getInstance(type);
                        } else {
                            ks = KeyStore.getInstance(type, provider);
                        }
                        LoadStoreParameter param = new SimpleLoadStoreParameter(protection);
                        if (protection instanceof CallbackHandlerProtection == false) {
                            ks.load(param);
                        } else {
                            // when using a CallbackHandler,
                            // reprompt if the password is wrong
                            int tries = 0;
                            while (true) {
                                tries++;
                                try {
                                    ks.load(param);
                                    break;
                                } catch (IOException e) {
                                    if (e.getCause() instanceof UnrecoverableKeyException) {
                                        if (tries < MAX_CALLBACK_TRIES) {
                                            continue;
                                        } else {
                                            oldException = e;
                                        }
                                    }
                                    throw e;
                                }
                            }
                        }
                        getCalled = true;
                        return ks;
                    }
                };

                public synchronized KeyStore getKeyStore()
                        throws KeyStoreException {
                    if (oldException != null) {
                        throw new KeyStoreException
                            ("Previous KeyStore instantiation failed",
                             oldException);
                    }
                    try {
                        return AccessController.doPrivileged(action, context);
                    } catch (PrivilegedActionException e) {
                        Throwable cause = e.getCause();
                        throw new KeyStoreException
                            ("KeyStore instantiation failed", cause);
                    }
                }

                public ProtectionParameter getProtectionParameter(String alias)
                {
                    if (alias == null) {
                        throw new NullPointerException();
                    }
                    if (getCalled == false) {
                        throw new IllegalStateException
                            ("getKeyStore() must be called first");
                    }
                    return protection;
                }
            };
        }

    }

    static class SimpleLoadStoreParameter implements LoadStoreParameter {

        private final ProtectionParameter protection;

        SimpleLoadStoreParameter(ProtectionParameter protection) {
            this.protection = protection;
        }

        public ProtectionParameter getProtectionParameter() {
            return protection;
        }
    }

}
