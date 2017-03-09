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
 * This interface inherits from <code>CharacterData</code> and represents the
 * content of a comment, i.e., all the characters between the starting '
 * <code>&lt;!--</code>' and ending '<code>--&gt;</code>'. Note that this is
 * the definition of a comment in XML, and, in practice, HTML, although some
 * HTML tools may implement the full SGML comment structure.
 * <p> No lexical check is done on the content of a comment and it is
 * therefore possible to have the character sequence <code>"--"</code>
 * (double-hyphen) in the content, which is illegal in a comment per section
 * 2.5 of [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. The
 * presence of this character sequence must generate a fatal error during
 * serialization.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  此接口继承自<code> CharacterData </code>并表示注释的内容,即起始"<code>&lt; !- </code>"和结束"<code> &gt; </code>'。
 * 请注意,这是XML中注释的定义,实际上是HTML,尽管一些HTML工具可能实现完整的SGML注释结构。
 *  <p>没有对注释的内容进行词法检查,因此可能在内容中具有字符序列<code>" - "</code>(双连字符),这在注释中是非法的根据[<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>
 *  XML 1.0 </a>]的第2.5节。
 */
public interface Comment extends CharacterData {
}
