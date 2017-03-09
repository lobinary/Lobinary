/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform.stax;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;

/**
 * <p>Acts as a holder for an XML {@link Result} in the
 * form of a StAX writer,i.e.
 * {@link XMLStreamWriter} or {@link XMLEventWriter}.
 * <code>StAXResult</code> can be used in all cases that accept
 * a <code>Result</code>, e.g. {@link javax.xml.transform.Transformer},
 * {@link javax.xml.validation.Validator} which accept
 * <code>Result</code> as input.
 *
 * <p>
 *  <p>以StAX作者的形式作为XML {@link Result}的持有者,即{@link XMLStreamWriter}或{@link XMLEventWriter}。
 *  <code> StAXResult </code>可用于所有接受<code> Result </code>的情况,例如{@link javax.xml.transform.Transformer},{@link javax.xml.validation.Validator}
 * ,它接受​​<code> Result </code>作为输入。
 *  <p>以StAX作者的形式作为XML {@link Result}的持有者,即{@link XMLStreamWriter}或{@link XMLEventWriter}。
 * 
 * 
 * @author <a href="mailto:Neeraj.Bajaj@Sun.com">Neeraj Bajaj</a>
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 *
 * @see <a href="http://jcp.org/en/jsr/detail?id=173">
 *  JSR 173: Streaming API for XML</a>
 * @see XMLStreamWriter
 * @see XMLEventWriter
 *
 * @since 1.6
 */
public class StAXResult implements Result {
    /** If {@link javax.xml.transform.TransformerFactory#getFeature(String name)}
     * returns true when passed this value as an argument,
     * the Transformer supports Result output of this type.
     * <p>
     *  当传递此值作为参数时返回true,Transformer支持此类型的Result输出。
     * 
     */
    public static final String FEATURE =
        "http://javax.xml.transform.stax.StAXResult/feature";

    /**
     * <p><code>XMLEventWriter</code> to be used for
     * <code>Result</code> output.</p>
     * <p>
     *  <p> <code> XMLEventWriter </code>用于<code> Result </code>输出。</p>
     * 
     */
    private XMLEventWriter xmlEventWriter = null;

    /**
     * <p><code>XMLStreamWriter</code> to be used for
     * <code>Result</code> output.</p>
     * <p>
     *  <p> <code> XMLStreamWriter </code>用于<code> Result </code>输出。</p>
     * 
     */
    private XMLStreamWriter xmlStreamWriter = null;

    /** <p>System identifier for this <code>StAXResult</code>.<p> */
    private String systemId = null;

    /**
     * <p>Creates a new instance of a <code>StAXResult</code>
     * by supplying an {@link XMLEventWriter}.</p>
     *
     * <p><code>XMLEventWriter</code> must be a
     * non-<code>null</code> reference.</p>
     *
     * <p>
     *  <p>通过提供{@link XMLEventWriter}来创建<code> StAXResult </code>的新实例。</p>
     * 
     *  <p> <code> XMLEventWriter </code>必须是非<code> null </code>参考。</p>
     * 
     * 
     * @param xmlEventWriter <code>XMLEventWriter</code> used to create
     *   this <code>StAXResult</code>.
     *
     * @throws IllegalArgumentException If <code>xmlEventWriter</code> ==
     *   <code>null</code>.
     */
    public StAXResult(final XMLEventWriter xmlEventWriter) {

        if (xmlEventWriter == null) {
            throw new IllegalArgumentException(
                    "StAXResult(XMLEventWriter) with XMLEventWriter == null");
        }

        this.xmlEventWriter = xmlEventWriter;
    }

    /**
     * <p>Creates a new instance of a <code>StAXResult</code>
     * by supplying an {@link XMLStreamWriter}.</p>
     *
     * <p><code>XMLStreamWriter</code> must be a
     * non-<code>null</code> reference.</p>
     *
     * <p>
     *  <p>通过提供{@link XMLStreamWriter}来创建<code> StAXResult </code>的新实例。</p>
     * 
     *  <p> <code> XMLStreamWriter </code>必须是非<code> null </code>参考。</p>
     * 
     * 
     * @param xmlStreamWriter <code>XMLStreamWriter</code> used to create
     *   this <code>StAXResult</code>.
     *
     * @throws IllegalArgumentException If <code>xmlStreamWriter</code> ==
     *   <code>null</code>.
     */
    public StAXResult(final XMLStreamWriter xmlStreamWriter) {

        if (xmlStreamWriter == null) {
            throw new IllegalArgumentException(
                    "StAXResult(XMLStreamWriter) with XMLStreamWriter == null");
        }

        this.xmlStreamWriter = xmlStreamWriter;
    }

    /**
     * <p>Get the <code>XMLEventWriter</code> used by this
     * <code>StAXResult</code>.</p>
     *
     * <p><code>XMLEventWriter</code> will be <code>null</code>
     * if this <code>StAXResult</code> was created with a
     * <code>XMLStreamWriter</code>.</p>
     *
     * <p>
     *  <p>取得此<code> StAXResult </code>使用的<code> XMLEventWriter </code>。</p>
     * 
     *  如果使用<code> XMLStreamWriter </code>创建了<code> StAXResult </code>,则</p> <code> XMLEventWriter </code>
     * 
     * 
     * @return <code>XMLEventWriter</code> used by this
     *   <code>StAXResult</code>.
     */
    public XMLEventWriter getXMLEventWriter() {

        return xmlEventWriter;
    }

    /**
     * <p>Get the <code>XMLStreamWriter</code> used by this
     * <code>StAXResult</code>.</p>
     *
     * <p><code>XMLStreamWriter</code> will be <code>null</code>
     * if this <code>StAXResult</code> was created with a
     * <code>XMLEventWriter</code>.</p>
     *
     * <p>
     *  <p>获取此<code> StAXResult </code>使用的<code> XMLStreamWriter </code>。</p>
     * 
     * 如果<code> StAXResult </code>是使用<code> XMLEventWriter </code>创建的</p>,则XMLStreamWriter </code>将为<code> n
     * ull </code>。
     * 
     * 
     * @return <code>XMLStreamWriter</code> used by this
     *   <code>StAXResult</code>.
     */
    public XMLStreamWriter getXMLStreamWriter() {

        return xmlStreamWriter;
    }

    /**
     * <p>In the context of a <code>StAXResult</code>, it is not appropriate
     * to explicitly set the system identifier.
     * The <code>XMLEventWriter</code> or <code>XMLStreamWriter</code>
     * used to construct this <code>StAXResult</code> determines the
     * system identifier of the XML result.</p>
     *
     * <p>An {@link UnsupportedOperationException} is <strong>always</strong>
     * thrown by this method.</p>
     *
     * <p>
     *  <p>在<code> StAXResult </code>的上下文中,不适合显式设置系统标识符。
     * 用于构造此<code> StAXResult </code>的<code> XMLEventWriter </code>或<code> XMLStreamWriter </code>确定XML结果的系统
     * 标识符。
     *  <p>在<code> StAXResult </code>的上下文中,不适合显式设置系统标识符。</p>。
     * 
     * 
     * @param systemId Ignored.
     *
     * @throws UnsupportedOperationException Is <strong>always</strong>
     *   thrown by this method.
     */
    public void setSystemId(final String systemId) {

        throw new UnsupportedOperationException(
                "StAXResult#setSystemId(systemId) cannot set the "
                + "system identifier for a StAXResult");
    }

    /**
     * <p>The returned system identifier is always <code>null</code>.</p>
     *
     * <p>
     *  <p>此方法会导致{@link UnsupportedOperationException} <strong>始终</strong>。</p>
     * 
     * 
     * @return The returned system identifier is always <code>null</code>.
     */
    public String getSystemId() {

        return null;
    }
}
