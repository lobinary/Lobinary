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

package java.security;

import java.io.*;
import java.util.*;

import java.security.KeyStore.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;

import javax.security.auth.callback.*;

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code KeyStore} class.
 * All the abstract methods in this class must be implemented by each
 * cryptographic service provider who wishes to supply the implementation
 * of a keystore for a particular keystore type.
 *
 * <p>
 *  此类为{@code KeyStore}类定义了<i>服务提供程序接口</i>(<b> SPI </b>)。该类中的所有抽象方法必须由希望为特定密钥库类型提供密钥库实现的每个加密服务提供者实现。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see KeyStore
 *
 * @since 1.2
 */

public abstract class KeyStoreSpi {

    /**
     * Returns the key associated with the given alias, using the given
     * password to recover it.  The key must have been associated with
     * the alias by a call to {@code setKeyEntry},
     * or by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry} or {@code SecretKeyEntry}.
     *
     * <p>
     *  返回与给定别名相关联的键,使用给定的密码来恢复它。
     * 该键必须已通过调用{@code setKeyEntry}或通过使用{@code PrivateKeyEntry}或{@code SecretKeyEntry}调用{@code setEntry}与别名相
     * 关联。
     *  返回与给定别名相关联的键,使用给定的密码来恢复它。
     * 
     * 
     * @param alias the alias name
     * @param password the password for recovering the key
     *
     * @return the requested key, or null if the given alias does not exist
     * or does not identify a key-related entry.
     *
     * @exception NoSuchAlgorithmException if the algorithm for recovering the
     * key cannot be found
     * @exception UnrecoverableKeyException if the key cannot be recovered
     * (e.g., the given password is wrong).
     */
    public abstract Key engineGetKey(String alias, char[] password)
        throws NoSuchAlgorithmException, UnrecoverableKeyException;

    /**
     * Returns the certificate chain associated with the given alias.
     * The certificate chain must have been associated with the alias
     * by a call to {@code setKeyEntry},
     * or by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry}.
     *
     * <p>
     *  返回与给定别名相关联的证书链。
     * 证书链必须已通过对{@code setKeyEntry}的调用或通过使用{@code PrivateKeyEntry}的{@code setEntry}的调用与别名相关联。
     * 
     * 
     * @param alias the alias name
     *
     * @return the certificate chain (ordered with the user's certificate first
     * and the root certificate authority last), or null if the given alias
     * does not exist or does not contain a certificate chain
     */
    public abstract Certificate[] engineGetCertificateChain(String alias);

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
     * (if a chain exists) is returned.
     *
     * <p>
     *  返回与给定别名相关联的证书。
     * 
     *  <p>如果给定的别名标识了通过调用{@code setCertificateEntry}创建的条目,或通过使用{@code TrustedCertificateEntry}调用{@code setEntry}
     * 创建的条目,则该条目中包含的受信任证书回。
     * 
     *  <p>如果给定的别名标识了通过调用{@code setKeyEntry}创建的条目,或通过使用{@code PrivateKeyEntry}调用{@code setEntry}创建的条目,则证书链的第
     * 一个元素返回该条目(如果存在链)。
     * 
     * 
     * @param alias the alias name
     *
     * @return the certificate, or null if the given alias does not exist or
     * does not contain a certificate.
     */
    public abstract Certificate engineGetCertificate(String alias);

    /**
     * Returns the creation date of the entry identified by the given alias.
     *
     * <p>
     * 返回由给定别名标识的条目的创建日期。
     * 
     * 
     * @param alias the alias name
     *
     * @return the creation date of this entry, or null if the given alias does
     * not exist
     */
    public abstract Date engineGetCreationDate(String alias);

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
     *  将给定的键分配给给定的别名,用给定的密码保护它。
     * 
     *  <p>如果给定的键是{@code java.security.PrivateKey}类型,它必须伴随着证书链,证明相应的公钥。
     * 
     *  <p>如果给定的别名已经存在,与其相关联的密钥库信息将被给定的密钥(以及可能的证书链)覆盖。
     * 
     * 
     * @param alias the alias name
     * @param key the key to be associated with the alias
     * @param password the password to protect the key
     * @param chain the certificate chain for the corresponding public
     * key (only required if the given key is of type
     * {@code java.security.PrivateKey}).
     *
     * @exception KeyStoreException if the given key cannot be protected, or
     * this operation fails for some other reason
     */
    public abstract void engineSetKeyEntry(String alias, Key key,
                                           char[] password,
                                           Certificate[] chain)
        throws KeyStoreException;

    /**
     * Assigns the given key (that has already been protected) to the given
     * alias.
     *
     * <p>If the protected key is of type
     * {@code java.security.PrivateKey},
     * it must be accompanied by a certificate chain certifying the
     * corresponding public key.
     *
     * <p>If the given alias already exists, the keystore information
     * associated with it is overridden by the given key (and possibly
     * certificate chain).
     *
     * <p>
     *  将给定的键(已经被保护)分配给给定的别名。
     * 
     *  <p>如果受保护的密钥类型为{@code java.security.PrivateKey},则必须附带证书链,证明相应的公钥。
     * 
     *  <p>如果给定的别名已经存在,与其相关联的密钥库信息将被给定的密钥(以及可能的证书链)覆盖。
     * 
     * 
     * @param alias the alias name
     * @param key the key (in protected format) to be associated with the alias
     * @param chain the certificate chain for the corresponding public
     * key (only useful if the protected key is of type
     * {@code java.security.PrivateKey}).
     *
     * @exception KeyStoreException if this operation fails.
     */
    public abstract void engineSetKeyEntry(String alias, byte[] key,
                                           Certificate[] chain)
        throws KeyStoreException;

    /**
     * Assigns the given certificate to the given alias.
     *
     * <p> If the given alias identifies an existing entry
     * created by a call to {@code setCertificateEntry},
     * or created by a call to {@code setEntry} with a
     * {@code TrustedCertificateEntry},
     * the trusted certificate in the existing entry
     * is overridden by the given certificate.
     *
     * <p>
     *  将给定的证书分配给给定的别名。
     * 
     *  <p>如果给定别名标识通过调用{@code setCertificateEntry}创建的现有条目,或通过使用{@code TrustedCertificateEntry}调用{@code setEntry}
     * 创建的现有条目,则现有条目中的受信任证书将被覆盖由给定的证书。
     * 
     * 
     * @param alias the alias name
     * @param cert the certificate
     *
     * @exception KeyStoreException if the given alias already exists and does
     * not identify an entry containing a trusted certificate,
     * or this operation fails for some other reason.
     */
    public abstract void engineSetCertificateEntry(String alias,
                                                   Certificate cert)
        throws KeyStoreException;

    /**
     * Deletes the entry identified by the given alias from this keystore.
     *
     * <p>
     *  从此密钥库中删除由给定别名标识的条目。
     * 
     * 
     * @param alias the alias name
     *
     * @exception KeyStoreException if the entry cannot be removed.
     */
    public abstract void engineDeleteEntry(String alias)
        throws KeyStoreException;

    /**
     * Lists all the alias names of this keystore.
     *
     * <p>
     *  列出此密钥库的所有别名。
     * 
     * 
     * @return enumeration of the alias names
     */
    public abstract Enumeration<String> engineAliases();

    /**
     * Checks if the given alias exists in this keystore.
     *
     * <p>
     *  检查此密钥库中是否存在给定别名。
     * 
     * 
     * @param alias the alias name
     *
     * @return true if the alias exists, false otherwise
     */
    public abstract boolean engineContainsAlias(String alias);

    /**
     * Retrieves the number of entries in this keystore.
     *
     * <p>
     *  检索此密钥库中的条目数。
     * 
     * 
     * @return the number of entries in this keystore
     */
    public abstract int engineSize();

    /**
     * Returns true if the entry identified by the given alias
     * was created by a call to {@code setKeyEntry},
     * or created by a call to {@code setEntry} with a
     * {@code PrivateKeyEntry} or a {@code SecretKeyEntry}.
     *
     * <p>
     * 如果由给定别名标识的条目是通过调用{@code setKeyEntry}创建的,或者通过使用{@code PrivateKeyEntry}或{@code SecretKeyEntry}调用{@code setEntry}
     * 创建的条目,则返回true。
     * 
     * 
     * @param alias the alias for the keystore entry to be checked
     *
     * @return true if the entry identified by the given alias is a
     * key-related, false otherwise.
     */
    public abstract boolean engineIsKeyEntry(String alias);

    /**
     * Returns true if the entry identified by the given alias
     * was created by a call to {@code setCertificateEntry},
     * or created by a call to {@code setEntry} with a
     * {@code TrustedCertificateEntry}.
     *
     * <p>
     *  如果由给定别名标识的条目是通过调用{@code setCertificateEntry}创建的,或者通过使用{@code TrustedCertificateEntry}调用{@code setEntry}
     * 创建的条目,则返回true。
     * 
     * 
     * @param alias the alias for the keystore entry to be checked
     *
     * @return true if the entry identified by the given alias contains a
     * trusted certificate, false otherwise.
     */
    public abstract boolean engineIsCertificateEntry(String alias);

    /**
     * Returns the (alias) name of the first keystore entry whose certificate
     * matches the given certificate.
     *
     * <p>This method attempts to match the given certificate with each
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
     *  返回其证书与给定证书匹配的第一个密钥库条目的(别名)名称。
     * 
     *  <p>此方法尝试将给定的证书与每个密钥库条目进行匹配。
     * 如果正在考虑的条目是通过调用{@code setCertificateEntry}创建的,或者通过使用{@code TrustedCertificateEntry}对{@code setEntry}的调
     * 用创建,则将给定的证书与该条目的证书进行比较。
     *  <p>此方法尝试将给定的证书与每个密钥库条目进行匹配。
     * 
     *  <p>如果正在考虑的条目是通过调用{@code setKeyEntry}创建的,或者通过使用{@code PrivateKeyEntry}调用{@code setEntry}创建,那么给定的证书将与第
     * 一个元素该条目的证书链。
     * 
     * 
     * @param cert the certificate to match with.
     *
     * @return the alias name of the first entry with matching certificate,
     * or null if no such entry exists in this keystore.
     */
    public abstract String engineGetCertificateAlias(Certificate cert);

    /**
     * Stores this keystore to the given output stream, and protects its
     * integrity with the given password.
     *
     * <p>
     *  将此密钥库存储到给定的输出流,并使用给定的密码保护其完整性。
     * 
     * 
     * @param stream the output stream to which this keystore is written.
     * @param password the password to generate the keystore integrity check
     *
     * @exception IOException if there was an I/O problem with data
     * @exception NoSuchAlgorithmException if the appropriate data integrity
     * algorithm could not be found
     * @exception CertificateException if any of the certificates included in
     * the keystore data could not be stored
     */
    public abstract void engineStore(OutputStream stream, char[] password)
        throws IOException, NoSuchAlgorithmException, CertificateException;

    /**
     * Stores this keystore using the given
     * {@code KeyStore.LoadStoreParmeter}.
     *
     * <p>
     *  使用给定的{@code KeyStore.LoadStoreParmeter}存储此密钥库。
     * 
     * 
     * @param param the {@code KeyStore.LoadStoreParmeter}
     *          that specifies how to store the keystore,
     *          which may be {@code null}
     *
     * @exception IllegalArgumentException if the given
     *          {@code KeyStore.LoadStoreParmeter}
     *          input is not recognized
     * @exception IOException if there was an I/O problem with data
     * @exception NoSuchAlgorithmException if the appropriate data integrity
     *          algorithm could not be found
     * @exception CertificateException if any of the certificates included in
     *          the keystore data could not be stored
     *
     * @since 1.5
     */
    public void engineStore(KeyStore.LoadStoreParameter param)
                throws IOException, NoSuchAlgorithmException,
                CertificateException {
        throw new UnsupportedOperationException();
    }

    /**
     * Loads the keystore from the given input stream.
     *
     * <p>A password may be given to unlock the keystore
     * (e.g. the keystore resides on a hardware token device),
     * or to check the integrity of the keystore data.
     * If a password is not given for integrity checking,
     * then integrity checking is not performed.
     *
     * <p>
     *  从给定的输入流加载密钥库。
     * 
     * <p>可以给出密码以解锁密钥库(例如,密钥库驻留在硬件令牌设备上),或者检查密钥库数据的完整性。如果未给出完整性检查的密码,则不执行完整性检查。
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
    public abstract void engineLoad(InputStream stream, char[] password)
        throws IOException, NoSuchAlgorithmException, CertificateException;

    /**
     * Loads the keystore using the given
     * {@code KeyStore.LoadStoreParameter}.
     *
     * <p> Note that if this KeyStore has already been loaded, it is
     * reinitialized and loaded again from the given parameter.
     *
     * <p>
     *  使用给定的{@code KeyStore.LoadStoreParameter}加载密钥库。
     * 
     *  <p>请注意,如果此KeyStore已加载,则会重新初始化并从给定参数重新加载。
     * 
     * 
     * @param param the {@code KeyStore.LoadStoreParameter}
     *          that specifies how to load the keystore,
     *          which may be {@code null}
     *
     * @exception IllegalArgumentException if the given
     *          {@code KeyStore.LoadStoreParameter}
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
    public void engineLoad(KeyStore.LoadStoreParameter param)
                throws IOException, NoSuchAlgorithmException,
                CertificateException {

        if (param == null) {
            engineLoad((InputStream)null, (char[])null);
            return;
        }

        if (param instanceof KeyStore.SimpleLoadStoreParameter) {
            ProtectionParameter protection = param.getProtectionParameter();
            char[] password;
            if (protection instanceof PasswordProtection) {
                password = ((PasswordProtection)protection).getPassword();
            } else if (protection instanceof CallbackHandlerProtection) {
                CallbackHandler handler =
                    ((CallbackHandlerProtection)protection).getCallbackHandler();
                PasswordCallback callback =
                    new PasswordCallback("Password: ", false);
                try {
                    handler.handle(new Callback[] {callback});
                } catch (UnsupportedCallbackException e) {
                    throw new NoSuchAlgorithmException
                        ("Could not obtain password", e);
                }
                password = callback.getPassword();
                callback.clearPassword();
                if (password == null) {
                    throw new NoSuchAlgorithmException
                        ("No password provided");
                }
            } else {
                throw new NoSuchAlgorithmException("ProtectionParameter must"
                    + " be PasswordProtection or CallbackHandlerProtection");
            }
            engineLoad(null, password);
            return;
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Gets a {@code KeyStore.Entry} for the specified alias
     * with the specified protection parameter.
     *
     * <p>
     *  为指定的具有指定保护参数的别名获取{@code KeyStore.Entry}。
     * 
     * 
     * @param alias get the {@code KeyStore.Entry} for this alias
     * @param protParam the {@code ProtectionParameter}
     *          used to protect the {@code Entry},
     *          which may be {@code null}
     *
     * @return the {@code KeyStore.Entry} for the specified alias,
     *          or {@code null} if there is no such entry
     *
     * @exception KeyStoreException if the operation failed
     * @exception NoSuchAlgorithmException if the algorithm for recovering the
     *          entry cannot be found
     * @exception UnrecoverableEntryException if the specified
     *          {@code protParam} were insufficient or invalid
     * @exception UnrecoverableKeyException if the entry is a
     *          {@code PrivateKeyEntry} or {@code SecretKeyEntry}
     *          and the specified {@code protParam} does not contain
     *          the information needed to recover the key (e.g. wrong password)
     *
     * @since 1.5
     */
    public KeyStore.Entry engineGetEntry(String alias,
                        KeyStore.ProtectionParameter protParam)
                throws KeyStoreException, NoSuchAlgorithmException,
                UnrecoverableEntryException {

        if (!engineContainsAlias(alias)) {
            return null;
        }

        if (protParam == null) {
            if (engineIsCertificateEntry(alias)) {
                return new KeyStore.TrustedCertificateEntry
                                (engineGetCertificate(alias));
            } else {
                throw new UnrecoverableKeyException
                        ("requested entry requires a password");
            }
        }

        if (protParam instanceof KeyStore.PasswordProtection) {
            if (engineIsCertificateEntry(alias)) {
                throw new UnsupportedOperationException
                    ("trusted certificate entries are not password-protected");
            } else if (engineIsKeyEntry(alias)) {
                KeyStore.PasswordProtection pp =
                        (KeyStore.PasswordProtection)protParam;
                char[] password = pp.getPassword();

                Key key = engineGetKey(alias, password);
                if (key instanceof PrivateKey) {
                    Certificate[] chain = engineGetCertificateChain(alias);
                    return new KeyStore.PrivateKeyEntry((PrivateKey)key, chain);
                } else if (key instanceof SecretKey) {
                    return new KeyStore.SecretKeyEntry((SecretKey)key);
                }
            }
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Saves a {@code KeyStore.Entry} under the specified alias.
     * The specified protection parameter is used to protect the
     * {@code Entry}.
     *
     * <p> If an entry already exists for the specified alias,
     * it is overridden.
     *
     * <p>
     *  在指定的别名下保存{@code KeyStore.Entry}。指定的保护参数用于保护{@code Entry}。
     * 
     *  <p>如果指定别名的某个条目已存在,则将覆盖该条目。
     * 
     * 
     * @param alias save the {@code KeyStore.Entry} under this alias
     * @param entry the {@code Entry} to save
     * @param protParam the {@code ProtectionParameter}
     *          used to protect the {@code Entry},
     *          which may be {@code null}
     *
     * @exception KeyStoreException if this operation fails
     *
     * @since 1.5
     */
    public void engineSetEntry(String alias, KeyStore.Entry entry,
                        KeyStore.ProtectionParameter protParam)
                throws KeyStoreException {

        // get password
        if (protParam != null &&
            !(protParam instanceof KeyStore.PasswordProtection)) {
            throw new KeyStoreException("unsupported protection parameter");
        }
        KeyStore.PasswordProtection pProtect = null;
        if (protParam != null) {
            pProtect = (KeyStore.PasswordProtection)protParam;
        }

        // set entry
        if (entry instanceof KeyStore.TrustedCertificateEntry) {
            if (protParam != null && pProtect.getPassword() != null) {
                // pre-1.5 style setCertificateEntry did not allow password
                throw new KeyStoreException
                    ("trusted certificate entries are not password-protected");
            } else {
                KeyStore.TrustedCertificateEntry tce =
                        (KeyStore.TrustedCertificateEntry)entry;
                engineSetCertificateEntry(alias, tce.getTrustedCertificate());
                return;
            }
        } else if (entry instanceof KeyStore.PrivateKeyEntry) {
            if (pProtect == null || pProtect.getPassword() == null) {
                // pre-1.5 style setKeyEntry required password
                throw new KeyStoreException
                    ("non-null password required to create PrivateKeyEntry");
            } else {
                engineSetKeyEntry
                    (alias,
                    ((KeyStore.PrivateKeyEntry)entry).getPrivateKey(),
                    pProtect.getPassword(),
                    ((KeyStore.PrivateKeyEntry)entry).getCertificateChain());
                return;
            }
        } else if (entry instanceof KeyStore.SecretKeyEntry) {
            if (pProtect == null || pProtect.getPassword() == null) {
                // pre-1.5 style setKeyEntry required password
                throw new KeyStoreException
                    ("non-null password required to create SecretKeyEntry");
            } else {
                engineSetKeyEntry
                    (alias,
                    ((KeyStore.SecretKeyEntry)entry).getSecretKey(),
                    pProtect.getPassword(),
                    (Certificate[])null);
                return;
            }
        }

        throw new KeyStoreException
                ("unsupported entry type: " + entry.getClass().getName());
    }

    /**
     * Determines if the keystore {@code Entry} for the specified
     * {@code alias} is an instance or subclass of the specified
     * {@code entryClass}.
     *
     * <p>
     * 
     * @param alias the alias name
     * @param entryClass the entry class
     *
     * @return true if the keystore {@code Entry} for the specified
     *          {@code alias} is an instance or subclass of the
     *          specified {@code entryClass}, false otherwise
     *
     * @since 1.5
     */
    public boolean
        engineEntryInstanceOf(String alias,
                              Class<? extends KeyStore.Entry> entryClass)
    {
        if (entryClass == KeyStore.TrustedCertificateEntry.class) {
            return engineIsCertificateEntry(alias);
        }
        if (entryClass == KeyStore.PrivateKeyEntry.class) {
            return engineIsKeyEntry(alias) &&
                        engineGetCertificate(alias) != null;
        }
        if (entryClass == KeyStore.SecretKeyEntry.class) {
            return engineIsKeyEntry(alias) &&
                        engineGetCertificate(alias) == null;
        }
        return false;
    }
}
