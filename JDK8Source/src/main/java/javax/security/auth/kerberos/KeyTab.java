/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.security.AccessControlException;
import java.util.Objects;
import sun.security.krb5.EncryptionKey;
import sun.security.krb5.KerberosSecrets;
import sun.security.krb5.PrincipalName;
import sun.security.krb5.RealmException;

/**
 * This class encapsulates a keytab file.
 * <p>
 * A Kerberos JAAS login module that obtains long term secret keys from a
 * keytab file should use this class. The login module will store
 * an instance of this class in the private credential set of a
 * {@link javax.security.auth.Subject Subject} during the commit phase of the
 * authentication process.
 * <p>
 * If a {@code KeyTab} object is obtained from {@link #getUnboundInstance()}
 * or {@link #getUnboundInstance(java.io.File)}, it is unbound and thus can be
 * used by any service principal. Otherwise, if it's obtained from
 * {@link #getInstance(KerberosPrincipal)} or
 * {@link #getInstance(KerberosPrincipal, java.io.File)}, it is bound to the
 * specific service principal and can only be used by it.
 * <p>
 * Please note the constructors {@link #getInstance()} and
 * {@link #getInstance(java.io.File)} were created when there was no support
 * for unbound keytabs. These methods should not be used anymore. An object
 * created with either of these methods are considered to be bound to an
 * unknown principal, which means, its {@link #isBound()} returns true and
 * {@link #getPrincipal()} returns null.
 * <p>
 * It might be necessary for the application to be granted a
 * {@link javax.security.auth.PrivateCredentialPermission
 * PrivateCredentialPermission} if it needs to access the KeyTab
 * instance from a Subject. This permission is not needed when the
 * application depends on the default JGSS Kerberos mechanism to access the
 * KeyTab. In that case, however, the application will need an appropriate
 * {@link javax.security.auth.kerberos.ServicePermission ServicePermission}.
 * <p>
 * The keytab file format is described at
 * <a href="http://www.ioplex.com/utilities/keytab.txt">
 * http://www.ioplex.com/utilities/keytab.txt</a>.
 * <p>
 * <p>
 *  此类封装了一个keytab文件。
 * <p>
 *  从keytab文件获取长期密钥的Kerberos JAAS登录模块应该使用此类。
 * 登录模块将在认证过程的提交阶段期间将此类的实例存储在{@link javax.security.auth.Subject Subject}的私有凭据集中。
 * <p>
 *  如果从{@link #getUnboundInstance()}或{@link #getUnboundInstance(java.io.File)}获得{@code KeyTab}对象,则它是未绑定的
 * ,因此可以由任何服务主体使用。
 * 否则,如果它从{@link #getInstance(KerberosPrincipal)}或{@link #getInstance(KerberosPrincipal,java.io.File)}获取
 * ,则它绑定到特定服务主体,并且只能由其使用。
 * <p>
 *  请注意,当不支持未绑定的keytab时,创建了构造函数{@link #getInstance()}和{@link #getInstance(java.io.File)}。这些方法不应再使用了。
 * 使用这些方法之一创建的对象被认为绑定到未知的主体,这意味着它的{@link #isBound()}返回true,而{@link #getPrincipal()}返回null。
 * <p>
 * 如果需要从主题访问KeyTab实例,则可能需要向应用程序授予{@link javax.security.auth.PrivateCredentialPermission PrivateCredentialPermission}
 * 。
 * 当应用程序依赖于默认的JGSS Kerberos机制来访问KeyTab时,不需要此权限。
 * 在这种情况下,应用程序将需要一个适当的{@link javax.security.auth.kerberos.ServicePermission ServicePermission}。
 * <p>
 *  keytab文件格式在
 * <a href="http://www.ioplex.com/utilities/keytab.txt">
 *  http://www.ioplex.com/utilities/keytab.txt </a>。
 * <p>
 * 
 * @since 1.7
 */
public final class KeyTab {

    /*
     * Impl notes:
     *
     * This class is only a name, a permanent link to the keytab source
     * (can be missing). Itself has no content. In order to read content,
     * take a snapshot and read from it.
     *
     * The snapshot is of type sun.security.krb5.internal.ktab.KeyTab, which
     * contains the content of the keytab file when the snapshot is taken.
     * Itself has no refresh function and mostly an immutable class (except
     * for the create/add/save methods only used by the ktab command).
     * <p>
     *  Impl注意：
     * 
     *  这个类只是一个名字,一个到keytab源的永久链接(可能丢失)。它本身没有内容。为了读取内容,拍摄快照并从中读取。
     * 
     *  快照的类型为sun.security.krb5.internal.ktab.KeyTab,其中包含拍摄快照时keytab文件的内容。
     * 它自己没有刷新函数,并且大多是一个不可变类(除了ktab命令仅使用的create / add / save方法)。
     * 
     */

    // Source, null if using the default one. Note that the default name
    // is maintained in snapshot, this field is never "resolved".
    private final File file;

    // Bound user: normally from the "principal" value in a JAAS krb5
    // login conf. Will be null if it's "*".
    private final KerberosPrincipal princ;

    private final boolean bound;

    // Set up JavaxSecurityAuthKerberosAccess in KerberosSecrets
    static {
        KerberosSecrets.setJavaxSecurityAuthKerberosAccess(
                new JavaxSecurityAuthKerberosAccessImpl());
    }

    private KeyTab(KerberosPrincipal princ, File file, boolean bound) {
        this.princ = princ;
        this.file = file;
        this.bound = bound;
    }

    /**
     * Returns a {@code KeyTab} instance from a {@code File} object
     * that is bound to an unknown service principal.
     * <p>
     * The result of this method is never null. This method only associates
     * the returned {@code KeyTab} object with the file and does not read it.
     * <p>
     * Developers should call {@link #getInstance(KerberosPrincipal,File)}
     * when the bound service principal is known.
     * <p>
     *  从绑定到未知服务主体的{@code File}对象返回{@code KeyTab}实例。
     * <p>
     *  此方法的结果永远不为null。此方法仅将返回的{@code KeyTab}对象与文件相关联,不会读取它。
     * <p>
     *  当绑定服务主体已知时,开发人员应调用{@link #getInstance(KerberosPrincipal,File)}。
     * 
     * 
     * @param file the keytab {@code File} object, must not be null
     * @return the keytab instance
     * @throws NullPointerException if the {@code file} argument is null
     */
    public static KeyTab getInstance(File file) {
        if (file == null) {
            throw new NullPointerException("file must be non null");
        }
        return new KeyTab(null, file, true);
    }

    /**
     * Returns an unbound {@code KeyTab} instance from a {@code File}
     * object.
     * <p>
     * The result of this method is never null. This method only associates
     * the returned {@code KeyTab} object with the file and does not read it.
     * <p>
     *  从{@code File}对象返回未绑定的{@code KeyTab}实例。
     * <p>
     * 此方法的结果永远不为null。此方法仅将返回的{@code KeyTab}对象与文件相关联,不会读取它。
     * 
     * 
     * @param file the keytab {@code File} object, must not be null
     * @return the keytab instance
     * @throws NullPointerException if the file argument is null
     * @since 1.8
     */
    public static KeyTab getUnboundInstance(File file) {
        if (file == null) {
            throw new NullPointerException("file must be non null");
        }
        return new KeyTab(null, file, false);
    }

    /**
     * Returns a {@code KeyTab} instance from a {@code File} object
     * that is bound to the specified service principal.
     * <p>
     * The result of this method is never null. This method only associates
     * the returned {@code KeyTab} object with the file and does not read it.
     * <p>
     *  从绑定到指定服务主体的{@code File}对象返回{@code KeyTab}实例。
     * <p>
     *  此方法的结果永远不为null。此方法仅将返回的{@code KeyTab}对象与文件相关联,不会读取它。
     * 
     * 
     * @param princ the bound service principal, must not be null
     * @param file the keytab {@code File} object, must not be null
     * @return the keytab instance
     * @throws NullPointerException if either of the arguments is null
     * @since 1.8
     */
    public static KeyTab getInstance(KerberosPrincipal princ, File file) {
        if (princ == null) {
            throw new NullPointerException("princ must be non null");
        }
        if (file == null) {
            throw new NullPointerException("file must be non null");
        }
        return new KeyTab(princ, file, true);
    }

    /**
     * Returns the default {@code KeyTab} instance that is bound
     * to an unknown service principal.
     * <p>
     * The result of this method is never null. This method only associates
     * the returned {@code KeyTab} object with the default keytab file and
     * does not read it.
     * <p>
     * Developers should call {@link #getInstance(KerberosPrincipal)}
     * when the bound service principal is known.
     * <p>
     *  返回绑定到未知服务主体的默认{@code KeyTab}实例。
     * <p>
     *  此方法的结果永远不为null。此方法仅将返回的{@code KeyTab}对象与默认keytab文件相关联,并且不会读取它。
     * <p>
     *  当绑定服务主体已知时,开发人员应调用{@link #getInstance(KerberosPrincipal)}。
     * 
     * 
     * @return the default keytab instance.
     */
    public static KeyTab getInstance() {
        return new KeyTab(null, null, true);
    }

    /**
     * Returns the default unbound {@code KeyTab} instance.
     * <p>
     * The result of this method is never null. This method only associates
     * the returned {@code KeyTab} object with the default keytab file and
     * does not read it.
     * <p>
     *  返回默认的未绑定{@code KeyTab}实例。
     * <p>
     *  此方法的结果永远不为null。此方法仅将返回的{@code KeyTab}对象与默认keytab文件相关联,并且不会读取它。
     * 
     * 
     * @return the default keytab instance
     * @since 1.8
     */
    public static KeyTab getUnboundInstance() {
        return new KeyTab(null, null, false);
    }

    /**
     * Returns the default {@code KeyTab} instance that is bound
     * to the specified service principal.
     * <p>
     * The result of this method is never null. This method only associates
     * the returned {@code KeyTab} object with the default keytab file and
     * does not read it.
     * <p>
     *  返回绑定到指定服务主体的默认{@code KeyTab}实例。
     * <p>
     *  此方法的结果永远不为null。此方法仅将返回的{@code KeyTab}对象与默认keytab文件相关联,并且不会读取它。
     * 
     * 
     * @param princ the bound service principal, must not be null
     * @return the default keytab instance
     * @throws NullPointerException if {@code princ} is null
     * @since 1.8
     */
    public static KeyTab getInstance(KerberosPrincipal princ) {
        if (princ == null) {
            throw new NullPointerException("princ must be non null");
        }
        return new KeyTab(princ, null, true);
    }

    // Takes a snapshot of the keytab content. This method is called by
    // JavaxSecurityAuthKerberosAccessImpl so no more private
    sun.security.krb5.internal.ktab.KeyTab takeSnapshot() {
        try {
            return sun.security.krb5.internal.ktab.KeyTab.getInstance(file);
        } catch (AccessControlException ace) {
            if (file != null) {
                // It's OK to show the name if caller specified it
                throw ace;
            } else {
                AccessControlException ace2 = new AccessControlException(
                        "Access to default keytab denied (modified exception)");
                ace2.setStackTrace(ace.getStackTrace());
                throw ace2;
            }
        }
    }

    /**
     * Returns fresh keys for the given Kerberos principal.
     * <p>
     * Implementation of this method should make sure the returned keys match
     * the latest content of the keytab file. The result is a newly created
     * copy that can be modified by the caller without modifying the keytab
     * object. The caller should {@link KerberosKey#destroy() destroy} the
     * result keys after they are used.
     * <p>
     * Please note that the keytab file can be created after the
     * {@code KeyTab} object is instantiated and its content may change over
     * time. Therefore, an application should call this method only when it
     * needs to use the keys. Any previous result from an earlier invocation
     * could potentially be expired.
     * <p>
     * If there is any error (say, I/O error or format error)
     * during the reading process of the KeyTab file, a saved result should be
     * returned. If there is no saved result (say, this is the first time this
     * method is called, or, all previous read attempts failed), an empty array
     * should be returned. This can make sure the result is not drastically
     * changed during the (probably slow) update of the keytab file.
     * <p>
     * Each time this method is called and the reading of the file succeeds
     * with no exception (say, I/O error or file format error),
     * the result should be saved for {@code principal}. The implementation can
     * also save keys for other principals having keys in the same keytab object
     * if convenient.
     * <p>
     * Any unsupported key read from the keytab is ignored and not included
     * in the result.
     * <p>
     * If this keytab is bound to a specific principal, calling this method on
     * another principal will return an empty array.
     *
     * <p>
     *  返回给定Kerberos主体的新密钥。
     * <p>
     * 此方法的实现应确保返回的键与keytab文件的最新内容匹配。结果是一个新创建的副本,可以由调用者修改而不修改keytab对象。
     * 调用者应该在使用结果键后{@link KerberosKey#destroy()destroy}。
     * <p>
     *  请注意,在{@code KeyTab}对象实例化后,可以创建keytab文件,其内容可能会随时间而变化。因此,应用程序只有在需要使用密钥时才应调用此方法。先前调用的任何先前结果可能会过期。
     * <p>
     *  如果在KeyTab文件的读取过程中有任何错误(例如,I / O错误或格式错误),则应返回已保存的结果。
     * 如果没有保存的结果(例如,这是第一次调用此方法,或者所有先前的读取尝试失败),则应返回一个空数组。这可以确保在keytab文件(可能较慢)更新期间结果不会急剧更改。
     * <p>
     *  每次调用此方法并且文件读取成功时,没有异常(例如,I / O错误或文件格式错误),结果应保存为{@code principal}。
     * 如果方便,实现还可以为具有相同keytab对象中的键的其他主体保存键。
     * <p>
     *  从密钥表读取的任何不受支持的密钥将被忽略,并且不包括在结果中。
     * <p>
     * 如果此keytab绑定到特定的主体,在另一个主体上调用此方法将返回一个空数组。
     * 
     * 
     * @param principal the Kerberos principal, must not be null.
     * @return the keys (never null, may be empty)
     * @throws NullPointerException if the {@code principal}
     * argument is null
     * @throws SecurityException if a security manager exists and the read
     * access to the keytab file is not permitted
     */
    public KerberosKey[] getKeys(KerberosPrincipal principal) {
        try {
            if (princ != null && !principal.equals(princ)) {
                return new KerberosKey[0];
            }
            PrincipalName pn = new PrincipalName(principal.getName());
            EncryptionKey[] keys = takeSnapshot().readServiceKeys(pn);
            KerberosKey[] kks = new KerberosKey[keys.length];
            for (int i=0; i<kks.length; i++) {
                Integer tmp = keys[i].getKeyVersionNumber();
                kks[i] = new KerberosKey(
                        principal,
                        keys[i].getBytes(),
                        keys[i].getEType(),
                        tmp == null ? 0 : tmp.intValue());
                keys[i].destroy();
            }
            return kks;
        } catch (RealmException re) {
            return new KerberosKey[0];
        }
    }

    EncryptionKey[] getEncryptionKeys(PrincipalName principal) {
        return takeSnapshot().readServiceKeys(principal);
    }

    /**
     * Checks if the keytab file exists. Implementation of this method
     * should make sure that the result matches the latest status of the
     * keytab file.
     * <p>
     * The caller can use the result to determine if it should fallback to
     * another mechanism to read the keys.
     * <p>
     *  检查keytab文件是否存在。实现此方法应该确保结果匹配keytab文件的最新状态。
     * <p>
     *  调用者可以使用结果来确定是否应该回退到另一种读取密钥的机制。
     * 
     * 
     * @return true if the keytab file exists; false otherwise.
     * @throws SecurityException if a security manager exists and the read
     * access to the keytab file is not permitted
     */
    public boolean exists() {
        return !takeSnapshot().isMissing();
    }

    public String toString() {
        String s = (file == null) ? "Default keytab" : file.toString();
        if (!bound) return s;
        else if (princ == null) return s + " for someone";
        else return s + " for " + princ;
    }

    /**
     * Returns a hashcode for this KeyTab.
     *
     * <p>
     *  返回此KeyTab的哈希码。
     * 
     * 
     * @return a hashCode() for the {@code KeyTab}
     */
    public int hashCode() {
        return Objects.hash(file, princ, bound);
    }

    /**
     * Compares the specified Object with this KeyTab for equality.
     * Returns true if the given object is also a
     * {@code KeyTab} and the two
     * {@code KeyTab} instances are equivalent.
     *
     * <p>
     *  将指定的对象与此KeyTab比较以确保相等。如果给定对象也是{@code KeyTab}并且两个{@code KeyTab}实例是等效的,则返回true。
     * 
     * 
     * @param other the Object to compare to
     * @return true if the specified object is equal to this KeyTab
     */
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (! (other instanceof KeyTab)) {
            return false;
        }

        KeyTab otherKtab = (KeyTab) other;
        return Objects.equals(otherKtab.princ, princ) &&
                Objects.equals(otherKtab.file, file) &&
                bound == otherKtab.bound;
    }

    /**
     * Returns the service principal this {@code KeyTab} object
     * is bound to. Returns {@code null} if it's not bound.
     * <p>
     * Please note the deprecated constructors create a KeyTab object bound for
     * some unknown principal. In this case, this method also returns null.
     * User can call {@link #isBound()} to verify this case.
     * <p>
     *  返回此{@code KeyTab}对象绑定到的服务主体。如果没有绑定,则返回{@code null}。
     * <p>
     *  请注意,不推荐的构造函数创建一个绑定到某个未知主体的KeyTab对象。在这种情况下,此方法也返回null。用户可以调用{@link #isBound()}来验证这种情况。
     * 
     * 
     * @return the service principal
     * @since 1.8
     */
    public KerberosPrincipal getPrincipal() {
        return princ;
    }

    /**
     * Returns if the keytab is bound to a principal
     * <p>
     * 
     * @return if the keytab is bound to a principal
     * @since 1.8
     */
    public boolean isBound() {
        return bound;
    }
}
