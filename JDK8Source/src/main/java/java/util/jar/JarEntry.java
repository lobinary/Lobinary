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

package java.util.jar;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.security.CodeSigner;
import java.security.cert.Certificate;

/**
 * This class is used to represent a JAR file entry.
 * <p>
 *  此类用于表示JAR文件条目。
 * 
 */
public
class JarEntry extends ZipEntry {
    Attributes attr;
    Certificate[] certs;
    CodeSigner[] signers;

    /**
     * Creates a new <code>JarEntry</code> for the specified JAR file
     * entry name.
     *
     * <p>
     *  为指定的JAR文件条目名称创建新的<code> JarEntry </code>。
     * 
     * 
     * @param name the JAR file entry name
     * @exception NullPointerException if the entry name is <code>null</code>
     * @exception IllegalArgumentException if the entry name is longer than
     *            0xFFFF bytes.
     */
    public JarEntry(String name) {
        super(name);
    }

    /**
     * Creates a new <code>JarEntry</code> with fields taken from the
     * specified <code>ZipEntry</code> object.
     * <p>
     *  使用从指定的<code> ZipEntry </code>对象中创建的字段创建新的<code> JarEntry </code>。
     * 
     * 
     * @param ze the <code>ZipEntry</code> object to create the
     *           <code>JarEntry</code> from
     */
    public JarEntry(ZipEntry ze) {
        super(ze);
    }

    /**
     * Creates a new <code>JarEntry</code> with fields taken from the
     * specified <code>JarEntry</code> object.
     *
     * <p>
     *  使用从指定的<code> JarEntry </code>对象中创建的字段创建新的<code> JarEntry </code>。
     * 
     * 
     * @param je the <code>JarEntry</code> to copy
     */
    public JarEntry(JarEntry je) {
        this((ZipEntry)je);
        this.attr = je.attr;
        this.certs = je.certs;
        this.signers = je.signers;
    }

    /**
     * Returns the <code>Manifest</code> <code>Attributes</code> for this
     * entry, or <code>null</code> if none.
     *
     * <p>
     *  返回此条目的<code> Manifest </code> <code> Attributes </code>,如果没有则返回<code> null </code>。
     * 
     * 
     * @return the <code>Manifest</code> <code>Attributes</code> for this
     * entry, or <code>null</code> if none
     * @throws IOException  if an I/O error has occurred
     */
    public Attributes getAttributes() throws IOException {
        return attr;
    }

    /**
     * Returns the <code>Certificate</code> objects for this entry, or
     * <code>null</code> if none. This method can only be called once
     * the <code>JarEntry</code> has been completely verified by reading
     * from the entry input stream until the end of the stream has been
     * reached. Otherwise, this method will return <code>null</code>.
     *
     * <p>The returned certificate array comprises all the signer certificates
     * that were used to verify this entry. Each signer certificate is
     * followed by its supporting certificate chain (which may be empty).
     * Each signer certificate and its supporting certificate chain are ordered
     * bottom-to-top (i.e., with the signer certificate first and the (root)
     * certificate authority last).
     *
     * <p>
     *  返回此条目的<code> Certificate </code>对象,如果没有,则返回<code> null </code>。
     * 只有通过从条目输入流中读取来完全验证<code> JarEntry </code>,直到到达流的结尾,才能调用此方法。否则,此方法将返回<code> null </code>。
     * 
     *  <p>返回的证书数组包含用于验证此条目的所有签署者证书。每个签署者证书后面是其支持证书链(可能为空)。每个签名者证书及其支持证书链从底部到顶部排序(即,首先是签名者证书,最后是(根)证书授权者)。
     * 
     * 
     * @return the <code>Certificate</code> objects for this entry, or
     * <code>null</code> if none.
     */
    public Certificate[] getCertificates() {
        return certs == null ? null : certs.clone();
    }

    /**
     * Returns the <code>CodeSigner</code> objects for this entry, or
     * <code>null</code> if none. This method can only be called once
     * the <code>JarEntry</code> has been completely verified by reading
     * from the entry input stream until the end of the stream has been
     * reached. Otherwise, this method will return <code>null</code>.
     *
     * <p>The returned array comprises all the code signers that have signed
     * this entry.
     *
     * <p>
     * 返回此条目的<code> CodeSigner </code>对象,如果没有,则返回<code> null </code>。
     * 只有通过从条目输入流中读取来完全验证<code> JarEntry </code>,直到到达流的结尾,才能调用此方法。否则,此方法将返回<code> null </code>。
     * 
     * 
     * @return the <code>CodeSigner</code> objects for this entry, or
     * <code>null</code> if none.
     *
     * @since 1.5
     */
    public CodeSigner[] getCodeSigners() {
        return signers == null ? null : signers.clone();
    }
}
