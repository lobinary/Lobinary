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

package java.security.cert;

import java.io.ByteArrayInputStream;
import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * An immutable sequence of certificates (a certification path).
 * <p>
 * This is an abstract class that defines the methods common to all
 * {@code CertPath}s. Subclasses can handle different kinds of
 * certificates (X.509, PGP, etc.).
 * <p>
 * All {@code CertPath} objects have a type, a list of
 * {@code Certificate}s, and one or more supported encodings. Because the
 * {@code CertPath} class is immutable, a {@code CertPath} cannot
 * change in any externally visible way after being constructed. This
 * stipulation applies to all public fields and methods of this class and any
 * added or overridden by subclasses.
 * <p>
 * The type is a {@code String} that identifies the type of
 * {@code Certificate}s in the certification path. For each
 * certificate {@code cert} in a certification path {@code certPath},
 * {@code cert.getType().equals(certPath.getType())} must be
 * {@code true}.
 * <p>
 * The list of {@code Certificate}s is an ordered {@code List} of
 * zero or more {@code Certificate}s. This {@code List} and all
 * of the {@code Certificate}s contained in it must be immutable.
 * <p>
 * Each {@code CertPath} object must support one or more encodings
 * so that the object can be translated into a byte array for storage or
 * transmission to other parties. Preferably, these encodings should be
 * well-documented standards (such as PKCS#7). One of the encodings supported
 * by a {@code CertPath} is considered the default encoding. This
 * encoding is used if no encoding is explicitly requested (for the
 * {@link #getEncoded() getEncoded()} method, for instance).
 * <p>
 * All {@code CertPath} objects are also {@code Serializable}.
 * {@code CertPath} objects are resolved into an alternate
 * {@link CertPathRep CertPathRep} object during serialization. This allows
 * a {@code CertPath} object to be serialized into an equivalent
 * representation regardless of its underlying implementation.
 * <p>
 * {@code CertPath} objects can be created with a
 * {@code CertificateFactory} or they can be returned by other classes,
 * such as a {@code CertPathBuilder}.
 * <p>
 * By convention, X.509 {@code CertPath}s (consisting of
 * {@code X509Certificate}s), are ordered starting with the target
 * certificate and ending with a certificate issued by the trust anchor. That
 * is, the issuer of one certificate is the subject of the following one. The
 * certificate representing the {@link TrustAnchor TrustAnchor} should not be
 * included in the certification path. Unvalidated X.509 {@code CertPath}s
 * may not follow these conventions. PKIX {@code CertPathValidator}s will
 * detect any departure from these conventions that cause the certification
 * path to be invalid and throw a {@code CertPathValidatorException}.
 *
 * <p> Every implementation of the Java platform is required to support the
 * following standard {@code CertPath} encodings:
 * <ul>
 * <li>{@code PKCS7}</li>
 * <li>{@code PkiPath}</li>
 * </ul>
 * These encodings are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
 * CertPath Encodings section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other encodings are supported.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * All {@code CertPath} objects must be thread-safe. That is, multiple
 * threads may concurrently invoke the methods defined in this class on a
 * single {@code CertPath} object (or more than one) with no
 * ill effects. This is also true for the {@code List} returned by
 * {@code CertPath.getCertificates}.
 * <p>
 * Requiring {@code CertPath} objects to be immutable and thread-safe
 * allows them to be passed around to various pieces of code without worrying
 * about coordinating access.  Providing this thread-safety is
 * generally not difficult, since the {@code CertPath} and
 * {@code List} objects in question are immutable.
 *
 * <p>
 *  不可变的证书序列(认证路径)。
 * <p>
 *  这是一个抽象类,定义所有{@code CertPath}的通用方法。子类可以处理不同类型的证书(X.509,PGP等)。
 * <p>
 *  所有{@code CertPath}对象都有一个类型,一个{@code Certificate}列表和一个或多个支持的编码。
 * 因为{@code CertPath}类是不可变的,所以在构造之后,{@code CertPath}不能以任何外部可见的方式改变。这一规定适用于该类的所有公共领域和方法,以及任何由子类添加或覆盖的。
 * <p>
 *  类型是{@code String},用于标识认证路径中的{@code Certificate}类型。
 * 对于认证路径{@code certPath}中的每个证书{@code cert},{@code cert.getType()。
 * equals(certPath.getType())}必须为{@code true}。
 * <p>
 *  {@code Certificate}的列表是一个包含零个或多个{@code Certificate}的有序{@code List}。
 * 此{@code List}和其中包含的所有{@code Certificate}必须是不可变的。
 * <p>
 * 每个{@code CertPath}对象必须支持一个或多个编码,以便对象可以转换为字节数组,以存储或传输给其他方。优选地,这些编码应当是良好记录的标准(例如PKCS#7)。
 *  {@code CertPath}支持的编码之一被认为是默认编码。如果没有显式请求编码(例如{@link #getEncoded()getEncoded()}方法),则使用此编码。
 * <p>
 *  所有{@code CertPath}对象也都是{@code Serializable}。
 * 在序列化期间,{@code CertPath}对象将解析为替代的{@link CertPathRep CertPathRep}对象。
 * 这允许{@code CertPath}对象被序列化为等价表示,而不管其底层实现。
 * <p>
 *  {@code CertPath}对象可以使用{@code CertificateFactory}创建,也可以由其他类(如{@code CertPathBuilder})返回。
 * <p>
 * 按照惯例,X.509 {@code CertPath}(由{@code X509Certificate}组成)从目标证书开始排序,并以信任锚发出的证书结束。
 * 也就是说,一个证书的颁发者是以下一个证书的主题。表示{@link TrustAnchor TrustAnchor}的证书不应包含在认证路径中。
 * 未验证的X.509 {@code CertPath}不能遵循这些约定。
 *  PKIX {@code CertPathValidator}将检测任何偏离这些约定,导致认证路径无效并抛出一个{@code CertPathValidatorException}。
 * 
 *  <p>每个Java平台的实施都需要支持以下标准{@code CertPath}编码：
 * <ul>
 *  <li> {@ code PKCS7} </li> <li> {@ code PkiPath} </li>
 * </ul>
 *  这些编码在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
 *  Java密码术体系结构标准算法名称文档的CertPath编码部分</a>。请查看您的实现的发行版文档,以了解是否支持任何其他编码。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  所有{@code CertPath}对象必须是线程安全的。也就是说,多个线程可以在单个{@code CertPath}对象(或多个线程)上同时调用此类中定义的方法,没有不良影响。
 * 对于{@code CertPath.getCertificates}返回的{@code List}也是如此。
 * <p>
 * 要求{@code CertPath}对象是不可变的并且线程安全的允许它们传递到各种代码段,而不必担心协调访问。
 * 提供这个线程安全通常不难,因为{@code CertPath}和{@code List}对象是不可变的。
 * 
 * 
 * @see CertificateFactory
 * @see CertPathBuilder
 *
 * @author      Yassir Elley
 * @since       1.4
 */
public abstract class CertPath implements Serializable {

    private static final long serialVersionUID = 6068470306649138683L;

    private String type;        // the type of certificates in this chain

    /**
     * Creates a {@code CertPath} of the specified type.
     * <p>
     * This constructor is protected because most users should use a
     * {@code CertificateFactory} to create {@code CertPath}s.
     *
     * <p>
     *  创建指定类型的{@code CertPath}。
     * <p>
     *  这个构造函数是受保护的,因为大多数用户应该使用{@code CertificateFactory}来创建{@code CertPath}。
     * 
     * 
     * @param type the standard name of the type of
     * {@code Certificate}s in this path
     */
    protected CertPath(String type) {
        this.type = type;
    }

    /**
     * Returns the type of {@code Certificate}s in this certification
     * path. This is the same string that would be returned by
     * {@link java.security.cert.Certificate#getType() cert.getType()}
     * for all {@code Certificate}s in the certification path.
     *
     * <p>
     *  返回此认证路径中的{@code Certificate}的类型。
     * 这是与认证路径中所有{@code Certificate}的{@link java.security.cert.Certificate#getType()cert.getType()}返回的字符串相同。
     *  返回此认证路径中的{@code Certificate}的类型。
     * 
     * 
     * @return the type of {@code Certificate}s in this certification
     * path (never null)
     */
    public String getType() {
        return type;
    }

    /**
     * Returns an iteration of the encodings supported by this certification
     * path, with the default encoding first. Attempts to modify the returned
     * {@code Iterator} via its {@code remove} method result in an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  返回此认证路径支持的编码的迭代,首先使用默认编码。
     * 尝试通过其{@code remove}方法修改返回的{@code Iterator}会导致{@code UnsupportedOperationException}。
     * 
     * 
     * @return an {@code Iterator} over the names of the supported
     *         encodings (as Strings)
     */
    public abstract Iterator<String> getEncodings();

    /**
     * Compares this certification path for equality with the specified
     * object. Two {@code CertPath}s are equal if and only if their
     * types are equal and their certificate {@code List}s (and by
     * implication the {@code Certificate}s in those {@code List}s)
     * are equal. A {@code CertPath} is never equal to an object that is
     * not a {@code CertPath}.
     * <p>
     * This algorithm is implemented by this method. If it is overridden,
     * the behavior specified here must be maintained.
     *
     * <p>
     *  将此认证路径与指定对象进行比较。
     * 当且仅当它们的类型相等,并且它们的证书{@code List}(并且意味着{@code List})中的{@code Certificate}是相等的时,两个{@code CertPath}是相等的。
     *  {@code CertPath}永远不等于不是{@code CertPath}的对象。
     * <p>
     *  该算法由该方法实现。如果覆盖,必须保留此处指定的行为。
     * 
     * 
     * @param other the object to test for equality with this certification path
     * @return true if the specified object is equal to this certification path,
     * false otherwise
     */
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (! (other instanceof CertPath))
            return false;

        CertPath otherCP = (CertPath) other;
        if (! otherCP.getType().equals(type))
            return false;

        List<? extends Certificate> thisCertList = this.getCertificates();
        List<? extends Certificate> otherCertList = otherCP.getCertificates();
        return(thisCertList.equals(otherCertList));
    }

    /**
     * Returns the hashcode for this certification path. The hash code of
     * a certification path is defined to be the result of the following
     * calculation:
     * <pre>{@code
     *  hashCode = path.getType().hashCode();
     *  hashCode = 31*hashCode + path.getCertificates().hashCode();
     * }</pre>
     * This ensures that {@code path1.equals(path2)} implies that
     * {@code path1.hashCode()==path2.hashCode()} for any two certification
     * paths, {@code path1} and {@code path2}, as required by the
     * general contract of {@code Object.hashCode}.
     *
     * <p>
     * 返回此认证路径的哈希码。认证路径的哈希码被定义为以下计算的结果：<pre> {@ code hashCode = path.getType()。
     * hashCode(); hashCode = 31 * hashCode + path.getCertificates()。
     * hashCode(); } </pre>这确保{@code path1.equals(path2)}意味着对于任何两个认证路径{@code path1}和{@ code path}的{@code path1.hashCode()== path2.hashCode代码path2}
     * ,根据{@code Object.hashCode}的一般合同的要求。
     * hashCode(); hashCode = 31 * hashCode + path.getCertificates()。
     * 
     * 
     * @return the hashcode value for this certification path
     */
    public int hashCode() {
        int hashCode = type.hashCode();
        hashCode = 31*hashCode + getCertificates().hashCode();
        return hashCode;
    }

    /**
     * Returns a string representation of this certification path.
     * This calls the {@code toString} method on each of the
     * {@code Certificate}s in the path.
     *
     * <p>
     *  返回此认证路径的字符串表示形式。这会调用路径中每个{@code Certificate}的{@code toString}方法。
     * 
     * 
     * @return a string representation of this certification path
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator<? extends Certificate> stringIterator =
                                        getCertificates().iterator();

        sb.append("\n" + type + " Cert Path: length = "
            + getCertificates().size() + ".\n");
        sb.append("[\n");
        int i = 1;
        while (stringIterator.hasNext()) {
            sb.append("=========================================="
                + "===============Certificate " + i + " start.\n");
            Certificate stringCert = stringIterator.next();
            sb.append(stringCert.toString());
            sb.append("\n========================================"
                + "=================Certificate " + i + " end.\n\n\n");
            i++;
        }

        sb.append("\n]");
        return sb.toString();
    }

    /**
     * Returns the encoded form of this certification path, using the default
     * encoding.
     *
     * <p>
     *  使用默认编码返回此认证路径的编码形式。
     * 
     * 
     * @return the encoded bytes
     * @exception CertificateEncodingException if an encoding error occurs
     */
    public abstract byte[] getEncoded()
        throws CertificateEncodingException;

    /**
     * Returns the encoded form of this certification path, using the
     * specified encoding.
     *
     * <p>
     *  使用指定的编码返回此认证路径的编码形式。
     * 
     * 
     * @param encoding the name of the encoding to use
     * @return the encoded bytes
     * @exception CertificateEncodingException if an encoding error occurs or
     *   the encoding requested is not supported
     */
    public abstract byte[] getEncoded(String encoding)
        throws CertificateEncodingException;

    /**
     * Returns the list of certificates in this certification path.
     * The {@code List} returned must be immutable and thread-safe.
     *
     * <p>
     *  返回此认证路径中的证书列表。返回的{@code List}必须是不可变的和线程安全的。
     * 
     * 
     * @return an immutable {@code List} of {@code Certificate}s
     *         (may be empty, but not null)
     */
    public abstract List<? extends Certificate> getCertificates();

    /**
     * Replaces the {@code CertPath} to be serialized with a
     * {@code CertPathRep} object.
     *
     * <p>
     *  替换要用{@code CertPathRep}对象序列化的{@code CertPath}。
     * 
     * 
     * @return the {@code CertPathRep} to be serialized
     *
     * @throws ObjectStreamException if a {@code CertPathRep} object
     * representing this certification path could not be created
     */
    protected Object writeReplace() throws ObjectStreamException {
        try {
            return new CertPathRep(type, getEncoded());
        } catch (CertificateException ce) {
            NotSerializableException nse =
                new NotSerializableException
                    ("java.security.cert.CertPath: " + type);
            nse.initCause(ce);
            throw nse;
        }
    }

    /**
     * Alternate {@code CertPath} class for serialization.
     * <p>
     *  替代{@code CertPath}类以进行序列化。
     * 
     * 
     * @since 1.4
     */
    protected static class CertPathRep implements Serializable {

        private static final long serialVersionUID = 3015633072427920915L;

        /** The Certificate type */
        private String type;
        /** The encoded form of the cert path */
        private byte[] data;

        /**
         * Creates a {@code CertPathRep} with the specified
         * type and encoded form of a certification path.
         *
         * <p>
         *  使用指定的类型和认证路径的编码形式创建{@code CertPathRep}。
         * 
         * 
         * @param type the standard name of a {@code CertPath} type
         * @param data the encoded form of the certification path
         */
        protected CertPathRep(String type, byte[] data) {
            this.type = type;
            this.data = data;
        }

        /**
         * Returns a {@code CertPath} constructed from the type and data.
         *
         * <p>
         *  返回从类型和数据构造的{@code CertPath}。
         * 
         * @return the resolved {@code CertPath} object
         *
         * @throws ObjectStreamException if a {@code CertPath} could not
         * be constructed
         */
        protected Object readResolve() throws ObjectStreamException {
            try {
                CertificateFactory cf = CertificateFactory.getInstance(type);
                return cf.generateCertPath(new ByteArrayInputStream(data));
            } catch (CertificateException ce) {
                NotSerializableException nse =
                    new NotSerializableException
                        ("java.security.cert.CertPath: " + type);
                nse.initCause(ce);
                throw nse;
            }
        }
    }
}
