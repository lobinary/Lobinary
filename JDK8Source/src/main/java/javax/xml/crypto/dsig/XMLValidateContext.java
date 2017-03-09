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
 * $Id: XMLValidateContext.java,v 1.8 2005/05/10 16:03:49 mullan Exp $
 * <p>
 *  $ Id：XMLValidateContext.java,v 1.8 2005/05/10 16:03:49 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.XMLCryptoContext;

/**
 * Contains context information for validating XML Signatures. This interface
 * is primarily intended for type-safety.
 *
 * <p>Note that <code>XMLValidateContext</code> instances can contain
 * information and state specific to the XML signature structure it is
 * used with. The results are unpredictable if an
 * <code>XMLValidateContext</code> is used with different signature structures
 * (for example, you should not use the same <code>XMLValidateContext</code>
 * instance to validate two different {@link XMLSignature} objects).
 * <p>
 * <b><a name="Supported Properties"></a>Supported Properties</b>
 * <p>The following properties can be set by an application using the
 * {@link #setProperty setProperty} method.
 * <ul>
 *   <li><code>javax.xml.crypto.dsig.cacheReference</code>: value must be a
 *      {@link Boolean}. This property controls whether or not the
 *      {@link Reference#validate Reference.validate} method will cache the
 *      dereferenced content and pre-digested input for subsequent retrieval via
 *      the {@link Reference#getDereferencedData Reference.getDereferencedData}
 *      and {@link Reference#getDigestInputStream
 *      Reference.getDigestInputStream} methods. The default value if not
 *      specified is <code>Boolean.FALSE</code>.
 * </ul>
 *
 * <p>
 *  包含用于验证XML签名的上下文信息。此接口主要用于类型安全。
 * 
 *  <p>请注意,<code> XMLValidateContext </code>实例可以包含特定于与其一起使用的XML签名结构的信息和状态。
 * 如果使用具有不同签名结构的<code> XMLValidateContext </code>(例如,您不应使用相同的<code> XMLValidateContext </code>实例验证两个不同的{@link XMLSignature}
 * 对象),结果是不可预测的, 。
 *  <p>请注意,<code> XMLValidateContext </code>实例可以包含特定于与其一起使用的XML签名结构的信息和状态。
 * <p>
 *  <b> <a name="Supported属性"></a>支持的属性</b> <p>应用程序可以使用{@link #setProperty setProperty}方法设置以下属性。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignature#validate(XMLValidateContext)
 * @see Reference#validate(XMLValidateContext)
 */
public interface XMLValidateContext extends XMLCryptoContext {}
