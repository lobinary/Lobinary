/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform;

/**
 * <p>An object that implements this interface contains the information
 * needed to build a transformation result tree.</p>
 *
 * <p>
 *  <p>实现此接口的对象包含构建转换结果树所需的信息。</p>
 * 
 * 
 * @author <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public interface Result {

    /**
     * The name of the processing instruction that is sent if the
     * result tree disables output escaping.
     *
     * <p>Normally, result tree serialization escapes & and < (and
     * possibly other characters) when outputting text nodes.
     * This ensures that the output is well-formed XML. However,
     * it is sometimes convenient to be able to produce output that is
     * almost, but not quite well-formed XML; for example,
     * the output may include ill-formed sections that will
     * be transformed into well-formed XML by a subsequent non-XML aware
     * process. If a processing instruction is sent with this name,
     * serialization should be output without any escaping. </p>
     *
     * <p>Result DOM trees may also have PI_DISABLE_OUTPUT_ESCAPING and
     * PI_ENABLE_OUTPUT_ESCAPING inserted into the tree.</p>
     *
     * <p>
     *  如果结果树禁用输出转义,则发送的处理指令的名称。
     * 
     *  <p>通常,在输出文本节点时,结果树序列化转义&和<(可能还有其他字符)。这确保输出是格式良好的XML。
     * 然而,有时候能够生成几乎是但不是很好地形成的XML的输出是方便的;例如,输出可以包括将通过随后的非XML感知过程转换为良好形成的XML的不成形部分。
     * 如果使用此名称发送处理指令,则应输出序列化而不进行任何转义。 </p>。
     * 
     *  <p>结果DOM树也可以在树中插入PI_DISABLE_OUTPUT_ESCAPING和PI_ENABLE_OUTPUT_ESCAPING。</p>
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#disable-output-escaping">disable-output-escaping in XSLT Specification</a>
     */
    public static final String PI_DISABLE_OUTPUT_ESCAPING =
        "javax.xml.transform.disable-output-escaping";

    /**
     * The name of the processing instruction that is sent
     * if the result tree enables output escaping at some point after having
     * received a PI_DISABLE_OUTPUT_ESCAPING processing instruction.
     *
     * <p>
     *  如果结果树在接收到PI_DISABLE_OUTPUT_ESCAPING处理指令后在某个点启用输出转义,则发送的处理指令的名称。
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#disable-output-escaping">disable-output-escaping in XSLT Specification</a>
     */
    public static final String PI_ENABLE_OUTPUT_ESCAPING =
        "javax.xml.transform.enable-output-escaping";

    /**
     * Set the system identifier for this Result.
     *
     * <p>If the Result is not to be written to a file, the system identifier is optional.
     * The application may still want to provide one, however, for use in error messages
     * and warnings, or to resolve relative output identifiers.</p>
     *
     * <p>
     *  设置此结果的系统标识符。
     * 
     *  <p>如果结果不写入文件,则系统标识符是可选的。应用程序可能仍然希望提供一个用于错误消息和警告,或者解析相对输出标识符。</p>
     * 
     * 
     * @param systemId The system identifier as a URI string.
     */
    public void setSystemId(String systemId);

    /**
     * Get the system identifier that was set with setSystemId.
     *
     * <p>
     * 
     * @return The system identifier that was set with setSystemId,
     * or null if setSystemId was not called.
     */
    public String getSystemId();
}
