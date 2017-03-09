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
 * Copyright (c) 2000 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.css;

import org.w3c.dom.DOMException;

/**
 *  The <code>CSSCharsetRule</code> interface represents a @charset rule in a
 * CSS style sheet. The value of the <code>encoding</code> attribute does
 * not affect the encoding of text data in the DOM objects; this encoding is
 * always UTF-16. After a stylesheet is loaded, the value of the
 * <code>encoding</code> attribute is the value found in the
 * <code>@charset</code> rule. If there was no <code>@charset</code> in the
 * original document, then no <code>CSSCharsetRule</code> is created. The
 * value of the <code>encoding</code> attribute may also be used as a hint
 * for the encoding used on serialization of the style sheet.
 * <p> The value of the @charset rule (and therefore of the
 * <code>CSSCharsetRule</code>) may not correspond to the encoding the
 * document actually came in; character encoding information e.g. in an HTTP
 * header, has priority (see CSS document representation) but this is not
 * reflected in the <code>CSSCharsetRule</code>.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 * <code> CSSCharsetRule </code>接口表示CSS样式表中的@charset规则。
 *  <code> encoding </code>属性的值不会影响DOM对象中文本数据的编码;此编码始终为UTF-16。
 * 加载样式表后,<code> encoding </code>属性的值是在<code> @charset </code>规则中找到的值。
 * 如果原始文档中没有<code> @charset </code>,则不会创建<code> CSSCharsetRule </code>。
 *  <code> encoding </code>属性的值也可以用作对样式表序列化时使用的编码的提示。
 *  <p> @charset规则的值(因此<code> CSSCharsetRule </code>)可能不对应于文档实际进入的编码;字符编码信息在HTTP头中,具有优先级(参见CSS文档表示),但这不会
 * 反映在<code> CSSCharsetRule </code>中。
 *  <code> encoding </code>属性的值也可以用作对样式表序列化时使用的编码的提示。
 * 
 * @since DOM Level 2
 */
public interface CSSCharsetRule extends CSSRule {
    /**
     *  The encoding information used in this <code>@charset</code> rule.
     * <p>
     *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
     * 
     */
    public String getEncoding();
    /**
     *  The encoding information used in this <code>@charset</code> rule.
     * <p>
     *  在此<code> @charset </code>规则中使用的编码信息。
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the specified encoding value has a syntax error
     *   and is unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this encoding rule is
     *   readonly.
     */
    public void setEncoding(String encoding)
                           throws DOMException;

}
