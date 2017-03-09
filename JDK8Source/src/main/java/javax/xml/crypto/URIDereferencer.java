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
 * ===========================================================================
 *
 * (C) Copyright IBM Corp. 2003 All Rights Reserved.
 *
 * ===========================================================================
 * <p>
 *  ================================================== =======================
 * 
 *  (C)版权所有IBM Corp. 2003保留所有权利。
 * 
 *  ================================================== =======================
 * 
 */
/*
 * $Id: URIDereferencer.java,v 1.5 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：URIDereferencer.java,v 1.5 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

/**
 * A dereferencer of {@link URIReference}s.
 * <p>
 * The result of dereferencing a <code>URIReference</code> is either an
 * instance of {@link OctetStreamData} or {@link NodeSetData}. Unless the
 * <code>URIReference</code> is a <i>same-document reference</i> as defined
 * in section 4.2 of the W3C Recommendation for XML-Signature Syntax and
 * Processing, the result of dereferencing the <code>URIReference</code>
 * MUST be an <code>OctetStreamData</code>.
 *
 * <p>
 *  {@link URIReference}的取消引用。
 * <p>
 *  取消引用<code> URIReference </code>的结果是{@link OctetStreamData}或{@link NodeSetData}的实例。
 * 除非<code> URIReference </code>是用于XML签名语法和处理的W3C推荐的第4.2节中定义的同一文档引用,因此取消引用<code> URIReference </i> / cod
 * e>必须是<code> OctetStreamData </code>。
 * 
 * @author Sean Mullan
 * @author Joyce Leung
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLCryptoContext#setURIDereferencer(URIDereferencer)
 * @see XMLCryptoContext#getURIDereferencer
 */
public interface URIDereferencer {

    /**
     * Dereferences the specified <code>URIReference</code> and returns the
     * dereferenced data.
     *
     * <p>
     *  取消引用<code> URIReference </code>的结果是{@link OctetStreamData}或{@link NodeSetData}的实例。
     * 
     * 
     * @param uriReference the <code>URIReference</code>
     * @param context an <code>XMLCryptoContext</code> that may
     *    contain additional useful information for dereferencing the URI. This
     *    implementation should dereference the specified
     *    <code>URIReference</code> against the context's <code>baseURI</code>
     *    parameter, if specified.
     * @return the dereferenced data
     * @throws NullPointerException if <code>uriReference</code> or
     *    <code>context</code> are <code>null</code>
     * @throws URIReferenceException if an exception occurs while
     *    dereferencing the specified <code>uriReference</code>
     */
    Data dereference(URIReference uriReference, XMLCryptoContext context)
        throws URIReferenceException;
}
