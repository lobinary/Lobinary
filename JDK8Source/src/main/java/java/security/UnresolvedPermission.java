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

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.reflect.*;
import java.security.cert.*;

/**
 * The UnresolvedPermission class is used to hold Permissions that
 * were "unresolved" when the Policy was initialized.
 * An unresolved permission is one whose actual Permission class
 * does not yet exist at the time the Policy is initialized (see below).
 *
 * <p>The policy for a Java runtime (specifying
 * which permissions are available for code from various principals)
 * is represented by a Policy object.
 * Whenever a Policy is initialized or refreshed, Permission objects of
 * appropriate classes are created for all permissions
 * allowed by the Policy.
 *
 * <p>Many permission class types
 * referenced by the policy configuration are ones that exist
 * locally (i.e., ones that can be found on CLASSPATH).
 * Objects for such permissions can be instantiated during
 * Policy initialization. For example, it is always possible
 * to instantiate a java.io.FilePermission, since the
 * FilePermission class is found on the CLASSPATH.
 *
 * <p>Other permission classes may not yet exist during Policy
 * initialization. For example, a referenced permission class may
 * be in a JAR file that will later be loaded.
 * For each such class, an UnresolvedPermission is instantiated.
 * Thus, an UnresolvedPermission is essentially a "placeholder"
 * containing information about the permission.
 *
 * <p>Later, when code calls AccessController.checkPermission
 * on a permission of a type that was previously unresolved,
 * but whose class has since been loaded, previously-unresolved
 * permissions of that type are "resolved". That is,
 * for each such UnresolvedPermission, a new object of
 * the appropriate class type is instantiated, based on the
 * information in the UnresolvedPermission.
 *
 * <p> To instantiate the new class, UnresolvedPermission assumes
 * the class provides a zero, one, and/or two-argument constructor.
 * The zero-argument constructor would be used to instantiate
 * a permission without a name and without actions.
 * A one-arg constructor is assumed to take a {@code String}
 * name as input, and a two-arg constructor is assumed to take a
 * {@code String} name and {@code String} actions
 * as input.  UnresolvedPermission may invoke a
 * constructor with a {@code null} name and/or actions.
 * If an appropriate permission constructor is not available,
 * the UnresolvedPermission is ignored and the relevant permission
 * will not be granted to executing code.
 *
 * <p> The newly created permission object replaces the
 * UnresolvedPermission, which is removed.
 *
 * <p> Note that the {@code getName} method for an
 * {@code UnresolvedPermission} returns the
 * {@code type} (class name) for the underlying permission
 * that has not been resolved.
 *
 * <p>
 *  UnresolvedPermission类用于保存在初始化策略时"未解析"的权限未解析的权限是在策略初始化时实际的Permission类不存在的权限(见下文)
 * 
 *  <p> Java运行时策略(指定哪些权限可用于来自不同主体的代码)由策略对象表示每当策略被初始化或刷新时,将为策略允许的所有权限创建适当类的权限对象
 * 
 * <p>策略配置引用的许多权限类类型都是本地存在的(即,可以在CLASSPATH上找到的权限类类型)。
 * 在策略初始化期间可以实例化这些权限的对象例如,可以实例化javaioFilePermission,因为FilePermission类位于CLASSPATH上。
 * 
 *  <p>在策略初始化期间可能还不存在其他权限类。例如,引用的权限类可能在稍后将被加载的JAR文件中。对于每个这样的类,UnresolvedPermission被实例化。
 * 因此,UnresolvedPermission本质上是一个"占位符"包含有关权限的信息。
 * 
 * <p>稍后,当代码对先前未解析的类型的权限调用AccessControllercheckPermission,但之前已加载其类时,该类型的先前未解析的权限为"已解决"。
 * 也就是说,对于每个此类UnresolvedPermission,根据UnresolvedPermission中的信息实例化适当的类类型。
 * 
 * <p>要实例化新类,UnresolvedPermission假定该类提供了一个零,一个和/或两个参数的构造函数。
 * 零参数构造函数将用于实例化没有名称和无动作的权限一个一参数构造函数假设以{@code String}名称作为输入,并且假设一个双参数构造函数接受一个{@code String}名称和{@code String}
 * 动作作为输入UnresolvedPermission可以调用一个带有{@code null }名称和/或操作如果适当的权限构造函数不可用,则忽略UnresolvedPermission,并且不会将相关权
 * 限授予执行代码。
 * <p>要实例化新类,UnresolvedPermission假定该类提供了一个零,一个和/或两个参数的构造函数。
 * 
 *  <p>新创建的权限对象将替换已删除的UnresolvedPermission
 * 
 * <p>请注意,{@code UnresolvedPermission}的{@code getName}方法会返回尚未解决的基础权限的{@code type}(类名称)
 * 
 * 
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.security.Policy
 *
 *
 * @author Roland Schemers
 */

public final class UnresolvedPermission extends Permission
implements java.io.Serializable
{

    private static final long serialVersionUID = -4821973115467008846L;

    private static final sun.security.util.Debug debug =
        sun.security.util.Debug.getInstance
        ("policy,access", "UnresolvedPermission");

    /**
     * The class name of the Permission class that will be
     * created when this unresolved permission is resolved.
     *
     * <p>
     *  解析此未解析权限时将创建的Permission类的类名
     * 
     * 
     * @serial
     */
    private String type;

    /**
     * The permission name.
     *
     * <p>
     *  权限名称
     * 
     * 
     * @serial
     */
    private String name;

    /**
     * The actions of the permission.
     *
     * <p>
     *  权限的操作
     * 
     * 
     * @serial
     */
    private String actions;

    private transient java.security.cert.Certificate certs[];

    /**
     * Creates a new UnresolvedPermission containing the permission
     * information needed later to actually create a Permission of the
     * specified class, when the permission is resolved.
     *
     * <p>
     *  创建一个新的UnresolvedPermission包含稍后需要的权限信息,以实际创建指定类的Permission,当权限被解决
     * 
     * 
     * @param type the class name of the Permission class that will be
     * created when this unresolved permission is resolved.
     * @param name the name of the permission.
     * @param actions the actions of the permission.
     * @param certs the certificates the permission's class was signed with.
     * This is a list of certificate chains, where each chain is composed of a
     * signer certificate and optionally its supporting certificate chain.
     * Each chain is ordered bottom-to-top (i.e., with the signer certificate
     * first and the (root) certificate authority last). The signer
     * certificates are copied from the array. Subsequent changes to
     * the array will not affect this UnsolvedPermission.
     */
    public UnresolvedPermission(String type,
                                String name,
                                String actions,
                                java.security.cert.Certificate certs[])
    {
        super(type);

        if (type == null)
                throw new NullPointerException("type can't be null");

        this.type = type;
        this.name = name;
        this.actions = actions;
        if (certs != null) {
            // Extract the signer certs from the list of certificates.
            for (int i=0; i<certs.length; i++) {
                if (!(certs[i] instanceof X509Certificate)) {
                    // there is no concept of signer certs, so we store the
                    // entire cert array
                    this.certs = certs.clone();
                    break;
                }
            }

            if (this.certs == null) {
                // Go through the list of certs and see if all the certs are
                // signer certs.
                int i = 0;
                int count = 0;
                while (i < certs.length) {
                    count++;
                    while (((i+1) < certs.length) &&
                           ((X509Certificate)certs[i]).getIssuerDN().equals(
                               ((X509Certificate)certs[i+1]).getSubjectDN())) {
                        i++;
                    }
                    i++;
                }
                if (count == certs.length) {
                    // All the certs are signer certs, so we store the entire
                    // array
                    this.certs = certs.clone();
                }

                if (this.certs == null) {
                    // extract the signer certs
                    ArrayList<java.security.cert.Certificate> signerCerts =
                        new ArrayList<>();
                    i = 0;
                    while (i < certs.length) {
                        signerCerts.add(certs[i]);
                        while (((i+1) < certs.length) &&
                            ((X509Certificate)certs[i]).getIssuerDN().equals(
                              ((X509Certificate)certs[i+1]).getSubjectDN())) {
                            i++;
                        }
                        i++;
                    }
                    this.certs =
                        new java.security.cert.Certificate[signerCerts.size()];
                    signerCerts.toArray(this.certs);
                }
            }
        }
    }


    private static final Class[] PARAMS0 = { };
    private static final Class[] PARAMS1 = { String.class };
    private static final Class[] PARAMS2 = { String.class, String.class };

    /**
     * try and resolve this permission using the class loader of the permission
     * that was passed in.
     * <p>
     *  请尝试使用传入的权限的类装入器解析此权限
     * 
     */
    Permission resolve(Permission p, java.security.cert.Certificate certs[]) {
        if (this.certs != null) {
            // if p wasn't signed, we don't have a match
            if (certs == null) {
                return null;
            }

            // all certs in this.certs must be present in certs
            boolean match;
            for (int i = 0; i < this.certs.length; i++) {
                match = false;
                for (int j = 0; j < certs.length; j++) {
                    if (this.certs[i].equals(certs[j])) {
                        match = true;
                        break;
                    }
                }
                if (!match) return null;
            }
        }
        try {
            Class<?> pc = p.getClass();

            if (name == null && actions == null) {
                try {
                    Constructor<?> c = pc.getConstructor(PARAMS0);
                    return (Permission)c.newInstance(new Object[] {});
                } catch (NoSuchMethodException ne) {
                    try {
                        Constructor<?> c = pc.getConstructor(PARAMS1);
                        return (Permission) c.newInstance(
                              new Object[] { name});
                    } catch (NoSuchMethodException ne1) {
                        Constructor<?> c = pc.getConstructor(PARAMS2);
                        return (Permission) c.newInstance(
                              new Object[] { name, actions });
                    }
                }
            } else {
                if (name != null && actions == null) {
                    try {
                        Constructor<?> c = pc.getConstructor(PARAMS1);
                        return (Permission) c.newInstance(
                              new Object[] { name});
                    } catch (NoSuchMethodException ne) {
                        Constructor<?> c = pc.getConstructor(PARAMS2);
                        return (Permission) c.newInstance(
                              new Object[] { name, actions });
                    }
                } else {
                    Constructor<?> c = pc.getConstructor(PARAMS2);
                    return (Permission) c.newInstance(
                          new Object[] { name, actions });
                }
            }
        } catch (NoSuchMethodException nsme) {
            if (debug != null ) {
                debug.println("NoSuchMethodException:\n  could not find " +
                        "proper constructor for " + type);
                nsme.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            if (debug != null ) {
                debug.println("unable to instantiate " + name);
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * This method always returns false for unresolved permissions.
     * That is, an UnresolvedPermission is never considered to
     * imply another permission.
     *
     * <p>
     *  此方法对未解析的权限始终返回false。也就是说,UnresolvedPermission不会被认为暗示另一个权限
     * 
     * 
     * @param p the permission to check against.
     *
     * @return false.
     */
    public boolean implies(Permission p) {
        return false;
    }

    /**
     * Checks two UnresolvedPermission objects for equality.
     * Checks that <i>obj</i> is an UnresolvedPermission, and has
     * the same type (class) name, permission name, actions, and
     * certificates as this object.
     *
     * <p> To determine certificate equality, this method only compares
     * actual signer certificates.  Supporting certificate chains
     * are not taken into consideration by this method.
     *
     * <p>
     * 检查两个UnresolvedPermission对象是否相等检查<i> obj </i>是否为UnresolvedPermission,并且具有与此对象相同的类型(类)名称,权限名称,操作和证书
     * 
     *  <p>要确定证书等同性,此方法仅比较实际签署者证书此方法不考虑支持证书链
     * 
     * 
     * @param obj the object we are testing for equality with this object.
     *
     * @return true if obj is an UnresolvedPermission, and has the same
     * type (class) name, permission name, actions, and
     * certificates as this object.
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (! (obj instanceof UnresolvedPermission))
            return false;
        UnresolvedPermission that = (UnresolvedPermission) obj;

        // check type
        if (!this.type.equals(that.type)) {
            return false;
        }

        // check name
        if (this.name == null) {
            if (that.name != null) {
                return false;
            }
        } else if (!this.name.equals(that.name)) {
            return false;
        }

        // check actions
        if (this.actions == null) {
            if (that.actions != null) {
                return false;
            }
        } else {
            if (!this.actions.equals(that.actions)) {
                return false;
            }
        }

        // check certs
        if ((this.certs == null && that.certs != null) ||
            (this.certs != null && that.certs == null) ||
            (this.certs != null && that.certs != null &&
                this.certs.length != that.certs.length)) {
            return false;
        }

        int i,j;
        boolean match;

        for (i = 0; this.certs != null && i < this.certs.length; i++) {
            match = false;
            for (j = 0; j < that.certs.length; j++) {
                if (this.certs[i].equals(that.certs[j])) {
                    match = true;
                    break;
                }
            }
            if (!match) return false;
        }

        for (i = 0; that.certs != null && i < that.certs.length; i++) {
            match = false;
            for (j = 0; j < this.certs.length; j++) {
                if (that.certs[i].equals(this.certs[j])) {
                    match = true;
                    break;
                }
            }
            if (!match) return false;
        }
        return true;
    }

    /**
     * Returns the hash code value for this object.
     *
     * <p>
     *  返回此对象的哈希码值
     * 
     * 
     * @return a hash code value for this object.
     */

    public int hashCode() {
        int hash = type.hashCode();
        if (name != null)
            hash ^= name.hashCode();
        if (actions != null)
            hash ^= actions.hashCode();
        return hash;
    }

    /**
     * Returns the canonical string representation of the actions,
     * which currently is the empty string "", since there are no actions for
     * an UnresolvedPermission. That is, the actions for the
     * permission that will be created when this UnresolvedPermission
     * is resolved may be non-null, but an UnresolvedPermission
     * itself is never considered to have any actions.
     *
     * <p>
     *  返回操作的规范字符串表示形式,目前为空字符串"",因为没有对UnresolvedPermission的操作。
     * 也就是说,当此UnresolvedPermission被解析时创建的权限的操作可能是非空的,但是UnresolvedPermission本身永远不会被认为有任何动作。
     * 
     * 
     * @return the empty string "".
     */
    public String getActions()
    {
        return "";
    }

    /**
     * Get the type (class name) of the underlying permission that
     * has not been resolved.
     *
     * <p>
     * 获取尚未解析的基础权限的类型(类名称)
     * 
     * 
     * @return the type (class name) of the underlying permission that
     *  has not been resolved
     *
     * @since 1.5
     */
    public String getUnresolvedType() {
        return type;
    }

    /**
     * Get the target name of the underlying permission that
     * has not been resolved.
     *
     * <p>
     *  获取尚未解决的基础权限的目标名称
     * 
     * 
     * @return the target name of the underlying permission that
     *          has not been resolved, or {@code null},
     *          if there is no target name
     *
     * @since 1.5
     */
    public String getUnresolvedName() {
        return name;
    }

    /**
     * Get the actions for the underlying permission that
     * has not been resolved.
     *
     * <p>
     *  获取尚未解决的基础权限的操作
     * 
     * 
     * @return the actions for the underlying permission that
     *          has not been resolved, or {@code null}
     *          if there are no actions
     *
     * @since 1.5
     */
    public String getUnresolvedActions() {
        return actions;
    }

    /**
     * Get the signer certificates (without any supporting chain)
     * for the underlying permission that has not been resolved.
     *
     * <p>
     *  获取尚未解决的基础权限的签署者证书(没有任何支持链)
     * 
     * 
     * @return the signer certificates for the underlying permission that
     * has not been resolved, or null, if there are no signer certificates.
     * Returns a new array each time this method is called.
     *
     * @since 1.5
     */
    public java.security.cert.Certificate[] getUnresolvedCerts() {
        return (certs == null) ? null : certs.clone();
    }

    /**
     * Returns a string describing this UnresolvedPermission.  The convention
     * is to specify the class name, the permission name, and the actions, in
     * the following format: '(unresolved "ClassName" "name" "actions")'.
     *
     * <p>
     *  返回描述此UnresolvedPermission的字符串约定是以以下格式指定类名,权限名称和操作：'(未解析的"ClassName""name""actions")'
     * 
     * 
     * @return information about this UnresolvedPermission.
     */
    public String toString() {
        return "(unresolved " + type + " " + name + " " + actions + ")";
    }

    /**
     * Returns a new PermissionCollection object for storing
     * UnresolvedPermission  objects.
     * <p>
     * <p>
     *  返回用于存储UnresolvedPermission对象的新PermissionCollection对象
     * <p>
     * 
     * @return a new PermissionCollection object suitable for
     * storing UnresolvedPermissions.
     */

    public PermissionCollection newPermissionCollection() {
        return new UnresolvedPermissionCollection();
    }

    /**
     * Writes this object out to a stream (i.e., serializes it).
     *
     * <p>
     *  将此对象写出到流(即,序列化它)
     * 
     * 
     * @serialData An initial {@code String} denoting the
     * {@code type} is followed by a {@code String} denoting the
     * {@code name} is followed by a {@code String} denoting the
     * {@code actions} is followed by an {@code int} indicating the
     * number of certificates to follow
     * (a value of "zero" denotes that there are no certificates associated
     * with this object).
     * Each certificate is written out starting with a {@code String}
     * denoting the certificate type, followed by an
     * {@code int} specifying the length of the certificate encoding,
     * followed by the certificate encoding itself which is written out as an
     * array of bytes.
     */
    private void writeObject(java.io.ObjectOutputStream oos)
        throws IOException
    {
        oos.defaultWriteObject();

        if (certs==null || certs.length==0) {
            oos.writeInt(0);
        } else {
            // write out the total number of certs
            oos.writeInt(certs.length);
            // write out each cert, including its type
            for (int i=0; i < certs.length; i++) {
                java.security.cert.Certificate cert = certs[i];
                try {
                    oos.writeUTF(cert.getType());
                    byte[] encoded = cert.getEncoded();
                    oos.writeInt(encoded.length);
                    oos.write(encoded);
                } catch (CertificateEncodingException cee) {
                    throw new IOException(cee.getMessage());
                }
            }
        }
    }

    /**
     * Restores this object from a stream (i.e., deserializes it).
     * <p>
     *  从流中恢复此对象(即,对其进行反序列化)
     */
    private void readObject(java.io.ObjectInputStream ois)
        throws IOException, ClassNotFoundException
    {
        CertificateFactory cf;
        Hashtable<String, CertificateFactory> cfs = null;

        ois.defaultReadObject();

        if (type == null)
                throw new NullPointerException("type can't be null");

        // process any new-style certs in the stream (if present)
        int size = ois.readInt();
        if (size > 0) {
            // we know of 3 different cert types: X.509, PGP, SDSI, which
            // could all be present in the stream at the same time
            cfs = new Hashtable<String, CertificateFactory>(3);
            this.certs = new java.security.cert.Certificate[size];
        }

        for (int i=0; i<size; i++) {
            // read the certificate type, and instantiate a certificate
            // factory of that type (reuse existing factory if possible)
            String certType = ois.readUTF();
            if (cfs.containsKey(certType)) {
                // reuse certificate factory
                cf = cfs.get(certType);
            } else {
                // create new certificate factory
                try {
                    cf = CertificateFactory.getInstance(certType);
                } catch (CertificateException ce) {
                    throw new ClassNotFoundException
                        ("Certificate factory for "+certType+" not found");
                }
                // store the certificate factory so we can reuse it later
                cfs.put(certType, cf);
            }
            // parse the certificate
            byte[] encoded=null;
            try {
                encoded = new byte[ois.readInt()];
            } catch (OutOfMemoryError oome) {
                throw new IOException("Certificate too big");
            }
            ois.readFully(encoded);
            ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
            try {
                this.certs[i] = cf.generateCertificate(bais);
            } catch (CertificateException ce) {
                throw new IOException(ce.getMessage());
            }
            bais.close();
        }
    }
}
