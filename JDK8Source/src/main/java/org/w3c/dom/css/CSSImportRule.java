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

import org.w3c.dom.stylesheets.MediaList;

/**
 *  The <code>CSSImportRule</code> interface represents a @import rule within
 * a CSS style sheet. The <code>@import</code> rule is used to import style
 * rules from other style sheets.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> CSSImportRule </code>接口表示CSS样式表中的@import规则。 <code> @import </code>规则用于从其他样式表导入样式规则。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface CSSImportRule extends CSSRule {
    /**
     *  The location of the style sheet to be imported. The attribute will not
     * contain the <code>"url(...)"</code> specifier around the URI.
     * <p>
     *  要导入的样式表的位置。该属性不会包含URI周围的<code>"url(...)"</code>说明符。
     * 
     */
    public String getHref();

    /**
     *  A list of media types for which this style sheet may be used.
     * <p>
     *  可以使用此样式表的介质类型列表。
     * 
     */
    public MediaList getMedia();

    /**
     * The style sheet referred to by this rule, if it has been loaded. The
     * value of this attribute is <code>null</code> if the style sheet has
     * not yet been loaded or if it will not be loaded (e.g. if the style
     * sheet is for a media type not supported by the user agent).
     * <p>
     *  此规则引用的样式表(如果已加载)。如果样式表尚未加载或者不会加载(例如,如果样式表用于用户代理不支持的媒体类型),则此属性的值为<code> null </code>。
     */
    public CSSStyleSheet getStyleSheet();

}
