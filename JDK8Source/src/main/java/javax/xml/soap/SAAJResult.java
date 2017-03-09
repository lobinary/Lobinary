/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

import javax.xml.transform.dom.DOMResult;

/**
 * Acts as a holder for the results of a JAXP transformation or a JAXB
 * marshalling, in the form of a SAAJ tree. These results should be accessed
 * by using the {@link #getResult()} method. The {@link DOMResult#getNode()}
 * method should be avoided in almost all cases.
 *
 * <p>
 *  作为JAXP转换或JAXB编组结果的持有者,以SAAJ树的形式。这些结果应通过使用{@link #getResult()}方法访问。
 * 在几乎所有情况下都应避免{@link DOMResult#getNode()}方法。
 * 
 * 
 * @author XWS-Security Development Team
 *
 * @since SAAJ 1.3
 */
public class SAAJResult extends DOMResult {

    /**
     * Creates a <code>SAAJResult</code> that will present results in the form
     * of a SAAJ tree that supports the default (SOAP 1.1) protocol.
     * <p>
     * This kind of <code>SAAJResult</code> is meant for use in situations where the
     * results will be used as a parameter to a method that takes a parameter
     * whose type, such as <code>SOAPElement</code>, is drawn from the SAAJ
     * API. When used in a transformation, the results are populated into the
     * <code>SOAPPart</code> of a <code>SOAPMessage</code> that is created internally.
     * The <code>SOAPPart</code> returned by {@link DOMResult#getNode()}
     * is not guaranteed to be well-formed.
     *
     * <p>
     *  创建将以支持默认(SOAP 1.1)协议的SAAJ树的形式显示结果的<code> SAAJResult </code>。
     * <p>
     *  这种类型的<code> SAAJResult </code>用于在结果将用作参数的方法的情况下,该方法接受一个参数,例如<code> SOAPElement </code> SAAJ API。
     * 在转换中使用时,结果将填充到内部创建的<code> SOAPMessage </code>的<code> SOAPPart </code>中。
     *  {@link DOMResult#getNode()}返回的<code> SOAPPart </code>无法保证格式良好。
     * 
     * 
     * @throws SOAPException if there is a problem creating a <code>SOAPMessage</code>
     *
     * @since SAAJ 1.3
     */
    public SAAJResult() throws SOAPException {
        this(MessageFactory.newInstance().createMessage());
    }

    /**
     * Creates a <code>SAAJResult</code> that will present results in the form
     * of a SAAJ tree that supports the specified protocol. The
     * <code>DYNAMIC_SOAP_PROTOCOL</code> is ambiguous in this context and will
     * cause this constructor to throw an <code>UnsupportedOperationException</code>.
     * <p>
     * This kind of <code>SAAJResult</code> is meant for use in situations where the
     * results will be used as a parameter to a method that takes a parameter
     * whose type, such as <code>SOAPElement</code>, is drawn from the SAAJ
     * API. When used in a transformation the results are populated into the
     * <code>SOAPPart</code> of a <code>SOAPMessage</code> that is created
     * internally. The <code>SOAPPart</code> returned by {@link DOMResult#getNode()}
     * is not guaranteed to be well-formed.
     *
     * <p>
     *  创建将以支持指定协议的SAAJ树的形式显示结果的<code> SAAJResult </code>。
     *  <code> DYNAMIC_SOAP_PROTOCOL </code>在此上下文中是不明确的,并将导致此构造函数抛出一个<code> UnsupportedOperationException </code>
     * 。
     *  创建将以支持指定协议的SAAJ树的形式显示结果的<code> SAAJResult </code>。
     * <p>
     * 这种类型的<code> SAAJResult </code>用于在结果将用作参数的方法的情况下,该方法接受一个参数,例如<code> SOAPElement </code> SAAJ API。
     * 在转换中使用时,结果将填充到内部创建的<code> SOAPMessage </code>的<code> SOAPPart </code>中。
     *  {@link DOMResult#getNode()}返回的<code> SOAPPart </code>无法保证格式良好。
     * 
     * 
     * @param protocol - the name of the SOAP protocol that the resulting SAAJ
     *                      tree should support
     *
     * @throws SOAPException if a <code>SOAPMessage</code> supporting the
     *             specified protocol cannot be created
     *
     * @since SAAJ 1.3
     */
    public SAAJResult(String protocol) throws SOAPException {
        this(MessageFactory.newInstance(protocol).createMessage());
    }

    /**
     * Creates a <code>SAAJResult</code> that will write the results into the
     * <code>SOAPPart</code> of the supplied <code>SOAPMessage</code>.
     * In the normal case these results will be written using DOM APIs and,
     * as a result, the finished <code>SOAPPart</code> will not be guaranteed
     * to be well-formed unless the data used to create it is also well formed.
     * When used in a transformation the validity of the <code>SOAPMessage</code>
     * after the transformation can be guaranteed only by means outside SAAJ
     * specification.
     *
     * <p>
     *  创建将结果写入提供的<code> SOAPMessage </code>的<code> SOAPPart </code>中的<code> SAAJResult </code>。
     * 在正常情况下,这些结果将使用DOM API编写,因此,完成的<code> SOAPPart </code>将不能保证格式良好,除非用于创建它的数据也是正确的。
     * 当在变换中使用时,转换之后的<code> SOAPMessage </code>的有效性可以仅通过SAAJ规范之外的手段来保证。
     * 
     * 
     * @param message - the message whose <code>SOAPPart</code> will be
     *                  populated as a result of some transformation or
     *                  marshalling operation
     *
     * @since SAAJ 1.3
     */
    public SAAJResult(SOAPMessage message) {
        super(message.getSOAPPart());
    }

    /**
     * Creates a <code>SAAJResult</code> that will write the results as a
     * child node of the <code>SOAPElement</code> specified. In the normal
     * case these results will be written using DOM APIs and as a result may
     * invalidate the structure of the SAAJ tree. This kind of
     * <code>SAAJResult</code> should only be used when the validity of the
     * incoming data can be guaranteed by means outside of the SAAJ
     * specification.
     *
     * <p>
     *  创建一个<code> SAAJResult </code>,它将结果作为指定的<code> SOAPElement </code>的子节点写入。
     * 在正常情况下,这些结果将使用DOM API编写,因此可能会使SAAJ树的结构无效。
     * 这种<code> SAAJResult </code>只有在输入数据的有效性可以通过SAAJ规范以外的方式保证时才能使用。
     * 
     * @param rootNode - the root to which the results will be appended
     *
     * @since SAAJ 1.3
     */
    public SAAJResult(SOAPElement rootNode) {
        super(rootNode);
    }


    /**
    /* <p>
    /* 
    /* 
     * @return the resulting Tree that was created under the specified root Node.
     * @since SAAJ 1.3
     */
    public javax.xml.soap.Node getResult() {
        return (javax.xml.soap.Node)super.getNode().getFirstChild();
     }
}
