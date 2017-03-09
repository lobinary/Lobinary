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
 * $Id: XMLSignContext.java,v 1.8 2005/05/10 16:03:48 mullan Exp $
 * <p>
 *  $ Id：XMLSignContext.java,v 1.8 2005/05/10 16:03:48 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.XMLCryptoContext;

/**
 * Contains context information for generating XML Signatures. This interface
 * is primarily intended for type-safety.
 *
 * <p>Note that <code>XMLSignContext</code> instances can contain
 * information and state specific to the XML signature structure it is
 * used with. The results are unpredictable if an
 * <code>XMLSignContext</code> is used with different signature structures
 * (for example, you should not use the same <code>XMLSignContext</code>
 * instance to sign two different {@link XMLSignature} objects).
 * <p>
 * <b><a name="Supported Properties"></a>Supported Properties</b>
 * <p>The following properties can be set using the
 * {@link #setProperty setProperty} method.
 * <ul>
 *   <li><code>javax.xml.crypto.dsig.cacheReference</code>: value must be a
 *      {@link Boolean}. This property controls whether or not the digested
 *      {@link Reference} objects will cache the dereferenced content and
 *      pre-digested input for subsequent retrieval via the
 *      {@link Reference#getDereferencedData Reference.getDereferencedData} and
 *      {@link Reference#getDigestInputStream Reference.getDigestInputStream}
 *      methods. The default value if not specified is
 *      <code>Boolean.FALSE</code>.
 * </ul>
 *
 * <p>
 *  包含用于生成XML签名的上下文信息。此接口主要用于类型安全。
 * 
 *  <p>请注意,<code> XMLSignContext </code>实例可以包含特定于与其一起使用的XML签名结构的信息和状态。
 * 如果使用具有不同签名结构的<code> XMLSignContext </code>(例如,不应使用相同的<code> XMLSignContext </code>实例来签署两个不同的{@link XMLSignature}
 * 对象),结果是不可预测的, 。
 *  <p>请注意,<code> XMLSignContext </code>实例可以包含特定于与其一起使用的XML签名结构的信息和状态。
 * <p>
 *  <b> <a name="Supported属性"></a>支持的属性</b> <p>以下属性可以使用{@link #setProperty setProperty}方法设置。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignature#sign(XMLSignContext)
 */
public interface XMLSignContext extends XMLCryptoContext {}
