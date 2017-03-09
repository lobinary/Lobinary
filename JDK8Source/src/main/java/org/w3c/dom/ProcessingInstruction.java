/***** Lobxxx Translate Finished ******/
/*
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
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * The <code>ProcessingInstruction</code> interface represents a "processing
 * instruction", used in XML as a way to keep processor-specific information
 * in the text of the document.
 * <p> No lexical check is done on the content of a processing instruction and
 * it is therefore possible to have the character sequence
 * <code>"?&gt;"</code> in the content, which is illegal a processing
 * instruction per section 2.6 of [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. The
 * presence of this character sequence must generate a fatal error during
 * serialization.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  <code> ProcessingInstruction </code>接口表示"处理指令",在XML中用作在文档的文本中保持处理器特定信息的方式。
 *  <p>对处理指令的内容不进行词法检查,因此可能在内容中具有字符序列<code>"?>"</code>,这是违反每个部分2.6的处理指令[<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>
 *  XML 1.0 </a>]。
 *  <code> ProcessingInstruction </code>接口表示"处理指令",在XML中用作在文档的文本中保持处理器特定信息的方式。此字符序列的存在必须在序列化期间生成致命错误。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 */
public interface ProcessingInstruction extends Node {
    /**
     * The target of this processing instruction. XML defines this as being
     * the first token following the markup that begins the processing
     * instruction.
     * <p>
     *  此处理指令的目标。 XML将此定义为开始处理指令的标记之后的第一个令牌。
     * 
     */
    public String getTarget();

    /**
     * The content of this processing instruction. This is from the first non
     * white space character after the target to the character immediately
     * preceding the <code>?&gt;</code>.
     * <p>
     * 此处理指令的内容。这是从目标之后的第一个非空格字符到紧邻<code>?&gt; </code>之前的字符。
     * 
     */
    public String getData();
    /**
     * The content of this processing instruction. This is from the first non
     * white space character after the target to the character immediately
     * preceding the <code>?&gt;</code>.
     * <p>
     *  此处理指令的内容。这是从目标之后的第一个非空格字符到紧邻<code>?&gt; </code>之前的字符。
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     */
    public void setData(String data)
                                   throws DOMException;

}
