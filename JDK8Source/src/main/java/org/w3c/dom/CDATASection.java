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
 *  (马萨诸塞理工学院,欧洲研究信息学和数学联合会,庆应大学)保留所有权利本作品根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证
 * 
 *  [1] http：// wwww3org / Consortium / Legal / 2002 / copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * CDATA sections are used to escape blocks of text containing characters that
 * would otherwise be regarded as markup. The only delimiter that is
 * recognized in a CDATA section is the "]]&gt;" string that ends the CDATA
 * section. CDATA sections cannot be nested. Their primary purpose is for
 * including material such as XML fragments, without needing to escape all
 * the delimiters.
 * <p>The <code>CharacterData.data</code> attribute holds the text that is
 * contained by the CDATA section. Note that this <em>may</em> contain characters that need to be escaped outside of CDATA sections and
 * that, depending on the character encoding ("charset") chosen for
 * serialization, it may be impossible to write out some characters as part
 * of a CDATA section.
 * <p>The <code>CDATASection</code> interface inherits from the
 * <code>CharacterData</code> interface through the <code>Text</code>
 * interface. Adjacent <code>CDATASection</code> nodes are not merged by use
 * of the <code>normalize</code> method of the <code>Node</code> interface.
 * <p> No lexical check is done on the content of a CDATA section and it is
 * therefore possible to have the character sequence <code>"]]&gt;"</code>
 * in the content, which is illegal in a CDATA section per section 2.7 of [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. The
 * presence of this character sequence must generate a fatal error during
 * serialization or the cdata section must be splitted before the
 * serialization (see also the parameter <code>"split-cdata-sections"</code>
 * in the <code>DOMConfiguration</code> interface).
 * <p ><b>Note:</b> Because no markup is recognized within a
 * <code>CDATASection</code>, character numeric references cannot be used as
 * an escape mechanism when serializing. Therefore, action needs to be taken
 * when serializing a <code>CDATASection</code> with a character encoding
 * where some of the contained characters cannot be represented. Failure to
 * do so would not produce well-formed XML.
 * <p ><b>Note:</b> One potential solution in the serialization process is to
 * end the CDATA section before the character, output the character using a
 * character reference or entity reference, and open a new CDATA section for
 * any further characters in the text node. Note, however, that some code
 * conversion libraries at the time of writing do not return an error or
 * exception when a character is missing from the encoding, making the task
 * of ensuring that data is not corrupted on serialization more difficult.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * CDATA段用于转义包含将被视为标记的字符的文本块在CDATA段中识别的唯一分隔符是"]]>"结束CDATA段的字符串CDATA段不能嵌套它们的主要目的是包含诸如XML片段的材料,而不需要转义所有分隔符
 * <p> <code> CharacterDatadata </code>属性包含的文本CDATA部分请注意,这个<em>可能</em>包含需要在CDATA节之外转义的字符,并且根据为序列化选择的字符编码
 * ("charset"),可能不可能写出一些字符作为CDATA段的一部分<p> <code> CDATASection </code>接口从<code> CharacterData </code>接口继承
 * <code> Text </code>接口相邻的<code> CDATASection </code>节点不会合并使用<code> Node </code>接口的<code> normalize </code>
 * 方法<p>对CDATA段的内容不进行词法检查,因此可能具有字符序列<code >"]]>&gt;"</code>",根据[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
 *  XML]的第27节, 10 </a>]这个字符序列的存在必须在序列化期间生成致命错误,或者在序列化之前必须拆分cdata段(另见<code> DOMConfiguration </code>中的参数<code>
 * "split-cdata-sections"</code>代码>接口)<p> <b>注意：</b>由于在<code> CDATASection </code>中未识别出标记,所以字符数字引用不能用作序列
 */
public interface CDATASection extends Text {
}
