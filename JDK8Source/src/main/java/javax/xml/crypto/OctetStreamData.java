/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
/*
 * $Id: OctetStreamData.java,v 1.3 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：OctetStreamData.java,v 1.3 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

import java.io.InputStream;

/**
 * A representation of a <code>Data</code> type containing an octet stream.
 *
 * <p>
 *  包含八位字节流的<code> Data </code>类型的表示形式。
 * 
 * 
 * @since 1.6
 */
public class OctetStreamData implements Data {

    private InputStream octetStream;
    private String uri;
    private String mimeType;

    /**
     * Creates a new <code>OctetStreamData</code>.
     *
     * <p>
     *  创建新的<code> OctetStreamData </code>。
     * 
     * 
     * @param octetStream the input stream containing the octets
     * @throws NullPointerException if <code>octetStream</code> is
     *    <code>null</code>
     */
    public OctetStreamData(InputStream octetStream) {
        if (octetStream == null) {
            throw new NullPointerException("octetStream is null");
        }
        this.octetStream = octetStream;
    }

    /**
     * Creates a new <code>OctetStreamData</code>.
     *
     * <p>
     *  创建新的<code> OctetStreamData </code>。
     * 
     * 
     * @param octetStream the input stream containing the octets
     * @param uri the URI String identifying the data object (may be
     *    <code>null</code>)
     * @param mimeType the MIME type associated with the data object (may be
     *    <code>null</code>)
     * @throws NullPointerException if <code>octetStream</code> is
     *    <code>null</code>
     */
    public OctetStreamData(InputStream octetStream, String uri,
        String mimeType) {
        if (octetStream == null) {
            throw new NullPointerException("octetStream is null");
        }
        this.octetStream = octetStream;
        this.uri = uri;
        this.mimeType = mimeType;
    }

    /**
     * Returns the input stream of this <code>OctetStreamData</code>.
     *
     * <p>
     *  返回此<code> OctetStreamData </code>的输入流。
     * 
     * 
     * @return the input stream of this <code>OctetStreamData</code>.
     */
    public InputStream getOctetStream() {
        return octetStream;
    }

    /**
     * Returns the URI String identifying the data object represented by this
     * <code>OctetStreamData</code>.
     *
     * <p>
     *  返回标识由此<code> OctetStreamData </code>表示的数据对象的URI字符串。
     * 
     * 
     * @return the URI String or <code>null</code> if not applicable
     */
    public String getURI() {
        return uri;
    }

    /**
     * Returns the MIME type associated with the data object represented by this
     * <code>OctetStreamData</code>.
     *
     * <p>
     *  返回与此<code> OctetStreamData </code>表示的数据对象相关联的MIME类型。
     * 
     * @return the MIME type or <code>null</code> if not applicable
     */
    public String getMimeType() {
        return mimeType;
    }
}
